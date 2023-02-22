pipeline {
    agent any

    tools {
        gradle "gradle jenkins"
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Artdianic94/PracticalTask.git'

                // Run Gradle on a Unix agent.
                 sh "gradle clean test"

            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
        stage('reports') {
            steps {
                script {
                    allure([
                            includeProperties: false,
                            jdk: '',
                            properties: [],
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'allure-results']]
                    ])
                }
            }
        }
    }
}
