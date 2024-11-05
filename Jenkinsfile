pipeline {
    agent any

    stages {
        stage('Git') {
            steps {
                echo 'Github Checkout : ';
                git branch : 'ArchenKharbouche-5Arctic5-G5',
                    credentialsId: 'GitToken',
                    url :'https://github.com/ahmedzmerli/5Arctic5-G5-Foyer.git';
            }
        }

        stage('MVN Clean & Compile') {
            steps {
                echo 'Nettoyage du Projet : ';
                sh 'mvn clean compile';
            }
        }

        stage('Maven Deploy') {
            steps {
                echo 'Deploying to Nexus Repository : ';
                sh 'mvn deploy -DaltDeploymentRepository=deploymentRepo::default::http://192.168.50.4:8081/repository/maven-releases/';
            }
        }
    }
}
