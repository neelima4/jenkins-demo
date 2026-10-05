pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t jenkins-demo:latest .'
            }
        }

        stage('Stop Old Container') {
            steps {
                sh '''
                    docker stop jenkins-demo-container || true
                    docker rm jenkins-demo-container || true
                '''
            }
        }

        stage('Run Docker Container') {
            steps {
                sh '''
                    docker run -d \
                    --name jenkins-demo-container \
                    -p 8080:8080 \
                    jenkins-demo:latest
                '''
            }
        }
    }
}

