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
         stage('Build Docker Image') {
                    steps {
                        echo 'Building Docker Image: ';
                        sh 'docker build -t yourdockerhubusername/tp-foyer:1.0.0 .';
}
}
}
}