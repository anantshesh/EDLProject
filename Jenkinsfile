pipeline {
  
  agent any
  
  triggers {
        cron('H */8 * * *') //regular builds
        pollSCM('* * * * *') //polling for changes, here once a minute
    }
   stages {
       stage('Gradle Build') {
    if (isUnix()) {
        sh './gradlew clean build'
    } else {
        bat 'gradlew.bat clean build'
    }
}
   } 
}
