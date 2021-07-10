pipeline {
  
  agent any
  
  triggers {
        cron('H */8 * * *') //regular builds
        pollSCM('* * * * *') //polling for changes, here once a minute
    }
   stages {
        stage('Checkout') {
            steps { //Checking out the repo
                checkout changelog: true, poll: true, scm: [$class: 'GitSCM', branches: [[name: '*/master']], browser: [$class: 'GitSCM', repoUrl: 'https://github.com/anantshesh/EDLProject'], doGenerateSubmoduleConfigurations: false, userRemoteConfigs: [[credentialsId: 'SHA256:ZLys2iTmaNXYEuD/ILGtg2tuQ5R+ApvYwxR0JCPDA64', url: 'ssh://https://github.com/anantshesh/EDLProject.git']]]
            }
        }
        stage('Unit & Integration Tests') {
            steps {
                script {
                    try {
                        sh './gradlew clean test --no-daemon' //run a gradle task
                    } finally {
                        junit '**/build/test-results/test/*.xml' //make the junit test results available in any case (success & failure)
                    }
                }
            }
        }
   } 
}
