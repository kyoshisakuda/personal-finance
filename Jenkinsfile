node {
    def mvnHome
    stage('Preparation') {
        slackSend color: '#439FE0', message: "Build started: ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
        git url: 'https://github.com/kyoshisakuda/personal-finance.git', branch: 'development'
        mvnHome = tool 'localMaven'
    }
    stage('Build') {
        if (isUnix()) {
            sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
        } else {
            bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
        }
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archiveArtifacts 'target/*.jar'
    }
}