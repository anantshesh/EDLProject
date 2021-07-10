pipeline {
  
  agent any
  
  triggers {
        cron('H */8 * * *') //regular builds
        pollSCM('H * * * *') //polling for changes, here once a minute
    }
   stages {
        stage('Build') {
          steps{
            repositories {
              google()
              jcenter()
            }
            dependencies {
              classpath "com.android.tools.build:gradle:4.1.3"
              classpath 'com.google.gms:google-services:4.3.8'
            }
            allprojects {
              repositories {
                google()
                jcenter()
              }
            }
            task clean(type: Delete) {
              delete rootProject.buildDir
            }
          }
        }
   }
}
