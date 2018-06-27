node {
    def dockerHome = tool name: "docker"
    env.PATH = "${dockerHome}/bin:${env.PATH}"

    stage("Checking out") {
        checkout scm
    }

    stage("Docker pull") {
        sh "docker pull maven:alpine"
    }

    stage("Build & Deploy") {
        withDockerContainer(image: "maven:alpine",
                toolName: "docker",
                args: "-v ${JENKINS_HOME}/.m2:/root/.m2") {

            sh "./build.sh"
        }
        withDockerRegistry([credentialsId: "dockerhub-loick"]) {
            def repo = "loick111/cocktailize"
            def tag = BRANCH_NAME.replaceAll("/","-")

            sh "docker tag ${repo} ${repo}:${tag}"
            sh "docker push ${repo}:${tag}"

            if (BRANCH_NAME == "master") {
                sh "docker tag ${repo} ${repo}:latest"
                sh "docker push ${repo}:latest"
            }
        }
    }
}
