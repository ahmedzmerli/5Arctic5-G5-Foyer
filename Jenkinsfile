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
                        -Dversion=1.0.0 \
                        -Dpackaging=jar \
                        -Dfile=target/tp-foyer-1.0.0.jar \
                        -DrepositoryId=deploymentRepo \
                        -Durl=http://localhost:8081/repository/maven-releases/ \
                        -s /usr/share/maven/conf/settings.xml
                    '''
                }
            }
        }
}
}