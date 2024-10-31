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
                        -DrepositoryId=nexus \
                        -Dusername=admin \
                        -Dpassword=nexus \
                        -Durl=http://192.168.33.10:8081/repository/maven-snapshots/ \
                        -s /usr/share/maven/conf/settings.xml
                    '''
                }
            }
        }

    }
}

