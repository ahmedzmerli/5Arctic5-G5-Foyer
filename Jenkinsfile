pipeline {
    agent any
    environment {
                IMAGE_NAME = "${DOCKER_IMAGE}"  // Using the global variable defined in Jenkins
            }
    parameters {
                choice(name: 'DEPLOYMENT_METHOD', choices: ['Docker Compose', 'Kubernetes'], description: 'Choose deployment method')
    }
    stages {
        stage('Git') {
            steps {
                echo 'Github Checkout : ';
                git branch : 'SelimLandolsi-5Arctic5-G5',
                    credentialsId: 'git_cred',
                    url :'https://github.com/ahmedzmerli/5Arctic5-G5-Foyer.git';
            }
        }

        stage('MVN Clean & Compile') {
            steps {
                echo 'Nettoyage du Projet : ';
                sh 'mvn clean compile';
            }
        }

        stage('MVN Test') {
            steps {
                echo 'Test du Projet : ';
                sh 'mvn test';
            }
        }
        stage('JaCoCo Report') {
            steps {
                sh 'mvn jacoco:report'
            
            }
        }
       stage('SonarQube Analysis') {
            steps {
                script {
                    def scannerHome = tool 'SQ scanner'
                    withSonarQubeEnv {
                        sh "${scannerHome}/bin/sonar-scanner -X"
                
                    }
                }
            }
       }
        stage('Build Application') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Building Docker images') {
            steps {
                script {
                    sh 'docker build -t sny445/foyer-test_build-selim_landolsi-5arctic5:latest .'
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USER')]) {
                    sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USER --password-stdin"
                    sh 'docker push sny445/foyer-test_build-selim_landolsi-5arctic5:latest'
                    }
                }
            }
            }
        stage('Deploy to Nexus') {
            steps {
                
                script {
                    echo 'Deploying to Nexus...'
                    sh '''
                        mvn deploy:deploy-file \
                        -DgroupId=tn.esprit.spring \
                        -DartifactId=tp-foyer \
                        -Dversion=1.0.0-SNAPSHOT \
                        -Dpackaging=jar \
                        -Dfile=target/tp-foyer-1.0.0-SNAPSHOT.jar \
                        -DrepositoryId=deploymentRepo \
                        -Durl=http://192.168.33.10:8081/repository/maven-snapshots/ \
                        -s /usr/share/maven/conf/settings.xml
                    '''
                }
            }
        }
        /*stage('Deploy via Docker Compose') {
                 steps {
                        script {

                            sh 'docker stop springbootapp-devops_project || true'
                            sh 'docker rm springbootapp-devops_project || true'

                            // Run the new container using Docker Compose
                            sh 'docker-compose up -d'
                        }
                 }
        }*/

    stage('Deploy') {
         steps {
              script {
                   if (params.DEPLOYMENT_METHOD == 'Docker Compose') {
                   echo "Deploying with Docker Compose..."
                   sh """
                   docker-compose down
                   IMAGE_NAME=${IMAGE_NAME} docker-compose up -d
                   """
                   } else if (params.DEPLOYMENT_METHOD == 'Kubernetes') {
                   echo "Deploying to Kubernetes..."
                   sh """
                   kubectl set image deployment/foyer-deployment foyer-container=${IMAGE_NAME} --record
                   kubectl rollout status deployment/foyer-deployment
                   """
                            }
                        }
                    }
                }




    }
    post {
        always {
            
            archiveArtifacts artifacts: 'target/site/jacoco/**/*.html', allowEmptyArchive: true
            // Optionally, you can also archive the exec file
            archiveArtifacts artifacts: 'target/jacoco.exec', allowEmptyArchive: true
            jacoco()
            sh "docker rmi ${IMAGE_NAME} || true"
        }
    }
}

