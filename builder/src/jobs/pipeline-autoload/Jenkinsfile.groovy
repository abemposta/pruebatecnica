pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                git 'example.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
                junit 'target/surefire-reports/*.xml'
            }
        }
        stage('Static Code Analysis') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }
        stage('Acceptance Test') {
            steps {
                sh 'mvn verify -P acceptance-tests'
            }
        }
        stage('Deploy') {
            steps {
                sh './deploy.sh'
            }
        }
        stage('Notify') {
            steps {
                slackSend color: 'good', message: "Build ${env.BUILD_ID} deployed successfully!"
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}
