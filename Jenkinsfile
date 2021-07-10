pipeline {
  
  agent any
  
  triggers {
        cron('H */8 * * *') //regular builds
        pollSCM('H * * * *') //polling for changes, here once a minute
    }
   stages {
        stage('Unit & Integration Tests') {
            steps {
              echo 'Compile project'
              sh "chmod +x gradlew"
              sh "./gradlew clean build --no-daemon"
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
