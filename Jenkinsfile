task_branch = "${TEST_BRANCH_NAME}"
def branch_cutted = task_branch.contains("origin") ? task_branch.split('/')[1] : task_branch.trim()
currentBuild.displayName = "$branch_cutted"
base_git_url = "https://github.com/Artdianic94/PracticalTask.git"
def maxParallelForks = "${params.maxParallelForks}" ?: 1
node {
    withEnv(["branch=${branch_cutted}", "base_url=${base_git_url}"]) {
        stage("Checkout Branch") {
            if (!"$branch_cutted".contains("master")) {
                try {
                    getProject("$base_git_url", "$branch_cutted")
                } catch (err) {
                    echo "Failed get branch $branch_cutted"
                    throw ("${err}")
                }
            } else {
                echo "Current branch is master"
            }
        }
        try {
          stage("Test") {
            withCredentials([usernamePassword(credentialsId: 'credentials-id', usernameVariable: 'Username', passwordVariable: 'Password')]) {
              if (params.BROWSER == "remote") {
                def REMOTE_BROWSER = params.REMOTE_BROWSER
                sh "./gradlew clean test -DBROWSER=${params.BROWSER} -DREMOTE_BROWSER=${REMOTE_BROWSER}"
              } else {
                sh "./gradlew clean test -DBROWSER=${params.BROWSER}"
              }
            }
          }
        } finally {
            stage("Allure") {
                generateAllure()
            }
        }
    }
}
def getProject(String repo, String branch) {
    cleanWs()
    checkout scm: [
            $class           : 'GitSCM', branches: [[name: branch]],
            userRemoteConfigs: [[
                                        url: repo
                                ]]
    ]
}

def generateAllure() {
    allure([
            includeProperties: true,
            jdk              : '',
            properties       : [],
            reportBuildPolicy: 'ALWAYS',
            results          : [[path: 'build/allure-results']]
    ])
}




