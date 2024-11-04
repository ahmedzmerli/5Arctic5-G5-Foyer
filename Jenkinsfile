pipeline {
    agent any

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
                    sh 'docker build -t sny445/foyer-testBuild-SelimLandolsi-5Arctic5:latest .'
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USER')]) {
                    sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USER --password-stdin"
                    sh 'docker push sny445/foyer-testBuild-SelimLandolsi-5Arctic5:latest'
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

    }
}

}