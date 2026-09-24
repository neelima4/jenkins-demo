pipeline {
    agent any

    environment {
        APP_NAME = 'jenkins-demo'
        APP_ENV = 'dev'
    }

    stages {

        stage('Build') {
            steps {
                echo "Building ${APP_NAME}"
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo 'Creating JAR...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Deploy') {
            steps {
                echo "Deploying ${APP_NAME} to ${APP_ENV}"
                sh '''
                    mkdir -p /tmp/jenkins-deploy
                    cp target/*.jar /tmp/jenkins-deploy/
                    ls -lh /tmp/jenkins-deploy/
                '''
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: 'target/*.jar'
            echo 'Pipeline completed successfully!'
        }

        failure {
            echo 'Pipeline failed!'
        }
    }
}
