pipeline {
  
  agent any
  
  triggers {
        cron('H */8 * * *') //regular builds
        pollSCM('* * * * *') //polling for changes, here once a minute
    }
   stages {
      stage 'build_Project'
     node {
       if(isUnix()) {
         sh 'gradle build --info'
       }
       else {
         bat 'gradle build --info'
       }
     }
   } 
}
