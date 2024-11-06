pipeline {
  agent { label 'dev' }

  environment {
    PROJECT_VERSION="1.0.6"
    PORT=4001
    SPRING_DB_NAME="prod"
    SPRING_DB_HOST="prod_db"
    SPRING_DB_USER="root"
    SPRING_DB_PASSWORD="root"
  }

  stages {
    stage("Connectivity") {
      steps {
        sh 'ping -c3 nexus'
        sh 'ping -c3 sonarq'
      }
    }

    stage("Dependencies") {
      agent {
        docker { 
          image 'maven:3.9-amazoncorretto-21-alpine' 
          args '-u root -v /etc/hosts:/etc/hosts'
        } 
      }
      steps {
        sh 'mvn dependency:resolve -Dmaven.repo.local=./lib -Dproject_version=$PROJECT_VERSION'
      }
    }

    stage("Clean docker") {
      steps {
        sh 'docker-compose down'
      }
    }

    stage("Setup test db") {
      steps {
        sh 'docker run -p3306:3306 -e MYSQL_ROOT_PASSWORD=$SPRING_DB_PASSWORD --name db -d mysql'
      }
    }

    stage("Test") {
      agent {
        docker { 
          image 'maven:3.9-amazoncorretto-21-alpine' 
          args '-u root -v /etc/hosts:/etc/hosts'
        } 
      }
      steps {
        sh 'mvn -Dspring.profiles.active=dev -Dmaven.repo.local=./lib -Dproject_version=$PROJECT_VERSION test'
      }
    }

    stage("Clean test") {
      steps {
        sh 'docker stop db'
        sh 'docker rm db'
      }
    }

    stage("Code quality check") {
      agent {
        docker {
          image 'maven:3.9-amazoncorretto-21-alpine' 
          args '-u root -v /etc/hosts:/etc/hosts'
        }
      }
      environment {
        TOKEN=credentials('sonarq_token')
      }
      steps {
        sh 'mvn -Dmaven.test.skip=true sonar:sonar -Dsonar.jdbc.url=jdbc:postgresql://sonarq:5432/sonar -Dsonar.host.url=http://sonarq:9000 -Dsonar.token=$TOKEN -Dproject_version=$PROJECT_VERSION -Dspring.profiles.active=prod'
      }
    }

    stage("Build") {
      agent {
        docker { 
          image 'maven:3.9-amazoncorretto-21-alpine' 
          args '-u root -v /etc/hosts:/etc/hosts'
        } 
      }
      steps {
        sh 'mvn -DskipTests -Dspring.profiles.active=prod -Dproject_version=$PROJECT_VERSION -Dmaven.repo.local=./lib package'
      }
      post {
        always {
          archiveArtifacts artifacts: "target/tp-foyer-${env.PROJECT_VERSION}.jar", fingerprint: true
        }
      }
    }

    stage("Deploy jar to nexus") {
      agent {
        docker { 
          image 'maven:3.9-amazoncorretto-21-alpine' 
          args '-u root -v /etc/hosts:/etc/hosts'
        } 
      }
      steps {
        withCredentials([usernamePassword(credentialsId: 'nexus_login', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
          sh 'mvn deploy -Dproject_version=$PROJECT_VERSION -Dmaven.repo.local=./lib -DskipTests -s settings.xml -Dnexus.user=$USERNAME -Dnexus.password=$PASSWORD'
        }
      }
    }

    stage("Docker login") {
      steps {
        withCredentials([usernamePassword(credentialsId: 'nexus_login', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
          sh 'docker login nexus:8082 -u $USERNAME -p $PASSWORD'
        }

        withCredentials([usernamePassword(credentialsId: 'dockerhub_login', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
          sh 'docker login -u $USERNAME -p $PASSWORD'
        }
      }
    }

    stage("Docker build & publish") {
      parallel {
        stage("nexus") {
          steps {
            step([
              $class: 'CopyArtifact',
              filter: "target/tp-foyer-${env.PROJECT_VERSION}.jar",
              fingerprintArtifacts: true,
              optional: true,
              projectName: env.JOB_NAME,
              selector: [$class: 'SpecificBuildSelector',
              buildNumber: env.BUILD_NUMBER]
            ])
            sh 'docker build . -t nexus:8082/devops:$PROJECT_VERSION --build-arg PROJECT_VERSION=$PROJECT_VERSION --build-arg PORT=$PORT'
            sh 'docker push nexus:8082/devops:$PROJECT_VERSION'
          }
          post {
            always {
              node("dev") {
                sh 'mosquitto_pub -t "jenkins" -m "Docker build & publish Nexus done"'
              }
            }
          }
        }
        stage("dockerhub") {
          steps {
            step([
              $class: 'CopyArtifact',
              filter: "target/tp-foyer-${env.PROJECT_VERSION}.jar",
              fingerprintArtifacts: true,
              optional: true,
              projectName: env.JOB_NAME,
              selector: [$class: 'SpecificBuildSelector',
              buildNumber: env.BUILD_NUMBER]
            ])
            withCredentials([usernamePassword(credentialsId: 'dockerhub_login', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
              sh 'docker build . -t $USERNAME/devops:$PROJECT_VERSION --build-arg PROJECT_VERSION=$PROJECT_VERSION'
              sh 'docker push $USERNAME/devops:$PROJECT_VERSION'
            }
          }
        }
      }
    }

    stage("Docker compose") {
      steps {
        sh 'docker-compose up -d'
      }
    }
  }
  post {
    success {
      node("dev") {
        sh 'mosquitto_pub -t "jenkins" -m "Build $BUILD_NUMBER success"'
      }
    }
    failure {
      node("dev") {
        sh 'mosquitto_pub -t "jenkins" -m "Build $BUILD_NUMBER failed"'
      }
    }
  }
}
