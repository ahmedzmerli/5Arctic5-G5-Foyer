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

    }
}
