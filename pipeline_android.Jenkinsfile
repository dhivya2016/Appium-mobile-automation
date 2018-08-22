props = null

def loadProperties() {
    script {
        props = readProperties file: 'AppiumPOC/jenkins_android.properties'
    }
}

pipeline {
    agent {
        label 'appium-poc'
    }

    stages {

        stage('Test') {
            steps {
                 script {
                    loadProperties()
                    withEnv([
                        'APPIUM_TESTS_APP_PATH=' + env.WORKSPACE + props["APPIUM_TESTS_APP_PATH"],
                        'APPIUM_TESTS_APP_PLATFORM=' + props["APPIUM_TESTS_APP_PLATFORM"],
                        'APPIUM_BINARY_PATH=' + props["APPIUM_BINARY_PATH"],
                        'APPIUM_TESTS_PORT=' + props["APPIUM_TESTS_PORT"],
                        'APPIUM_TESTS_HOST=' + props["APPIUM_TESTS_HOST"],
                        'CUCUMBER_REPORT=' + env.WORKSPACE + props["CUCUMBER_REPORT"],
                        'CUCUMBER_RERUN_REPORT=' + env.WORKSPACE + props["CUCUMBER_RERUN_REPORT"]
                    ]) {
                    sh 'env'
                        dir('AppiumPOC'){
                            sh('./gradlew clean test')
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            script {
                loadProperties()
                withEnv([
                    'CUCUMBER_REPORT=' + env.WORKSPACE + props["CUCUMBER_REPORT"],
                    'CUCUMBER_RERUN_REPORT=' + env.WORKSPACE + props["CUCUMBER_RERUN_REPORT"]
                ]) {
                sh 'env'
                    dir('AppiumPOC'){
                        sh('java -jar merge-reports-all-1.0-SNAPSHOT.jar')
                    }
                }
            }
            step([
                $class: 'CucumberReportPublisher',
                jsonReportDirectory: 'AppiumPOC/build/cucumber-json-report',
                fileIncludePattern: 'cucumber.json',
                fileExcludePattern: '',
                ignoreFailedTests: false,
                jenkinsBasePath: '',
                missingFails: false,
                parallelTesting: false,
                pendingFails: false,
                skippedFails: false,
                undefinedFails: false
            ])
            script {
              if(manager.logContains(".*\"error_message\":.*") || manager.logContains("Missing report result - report was not successfully completed")) {
                manager.buildFailure()
              }
            }
        }
    }


}