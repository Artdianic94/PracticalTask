def maxParallelForks = "${params.maxParallelForks}" ?: 1

pipeline {
    agent any

    environment {
        task_branch = "${TEST_BRANCH_NAME}"
        branch_cutted = task_branch.contains("origin") ? task_branch.split('/')[1] : task_branch.trim()
        base_git_url = "https://github.com/Artdianic94/PracticalTask.git"
    }

    stages {
        stage('Checkout Branch') {
            when {
                expression { !branch_cutted.contains("master") }
            }
            steps {
                script {
                    try {
                        getProject(base_git_url, branch_cutted)
                    } catch (err) {
                        echo "Failed get branch $branch_cutted"
                        throw "${err}"
                    }
                }
            }
        }

        stage('Test') {
            when {
                expression { !branch_cutted.contains("master") }
            }
            steps {
                withCredentials([usernamePassword(credentialsId: 'credentials-id', usernameVariable: 'Username', passwordVariable: 'Password')]) {
                    script {
                        if (params.BROWSER == "remote") {
                            def REMOTE_BROWSER = params.REMOTE_BROWSER
                            sh "./gradlew clean test -DBROWSER=${params.BROWSER} -DREMOTE_BROWSER=${REMOTE_BROWSER}"
                        } else {
                            sh "./gradlew clean test -DBROWSER=${params.BROWSER}"
                        }
                    }
                }
            }
        }

        stage('Allure') {
            when {
                expression { !branch_cutted.contains("master") }
            }
            steps {
                script {
                    generateAllure()
                }
            }
        }
    }
}

def getProject(String repo, String branch) {
    cleanWs()
    checkout scm: [
        $class: 'GitSCM', branches: [[name: branch]],
        userRemoteConfigs: [[url: repo]]
    ]
}

def generateAllure() {
    allure([
        includeProperties: true,
        jdk: '',
        properties: [],
        reportBuildPolicy: 'ALWAYS',
        results: [[path: 'build/allure-results']]
    ])
}
