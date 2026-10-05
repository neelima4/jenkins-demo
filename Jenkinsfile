pipeline {
    agent any

    environment {
        DOCKER_IMAGE = '<neelima4>/jenkins-demo'
    }

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
                sh 'docker build -t $DOCKER_IMAGE:${BUILD_NUMBER} .'
            }
        }

        stage('Docker Login and Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-credentials',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login \
                        -u "$DOCKER_USER" \
                        --password-stdin

                        docker push $DOCKER_IMAGE:${BUILD_NUMBER}
                    '''
                }
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

        stage('Deploy Container') {
            steps {
                sh '''
                    docker run -d \
                    --name jenkins-demo-container \
                    -p 8081:8080 \
                    $DOCKER_IMAGE:${BUILD_NUMBER}
                '''
            }
        }
    }
}
