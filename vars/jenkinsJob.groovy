def call(){
    if(env.JOB_NAME.contains("deploy")){
        properties()
    }
    node {
        stage('Checkout') {
            checkout scm
        }
        // Execute different stages depending on the job
        if(env.JOB_NAME.contains("deploy")){
            packageArtifact()
        } else if(env.JOB_NAME.contains("test")) {
            buildAndTest()
        }
    }
}

def properties(){
    properties([
        parameters([
            string(name: 'submodule', defaultValue: 'hello word'),
            string(name: 'submodule_branch', defaultValue: ''),
            string(name: 'commit_sha', defaultValue: ''),
        ]),
        pipelineTriggers([
            upstream(upstreamProjects: 'upstream', threshold:
            hudson.model.Result.SUCCESS)
        ]),  
    ])
}

def packageArtifact(){
    stage("Package artifact") {
        sh "echo this is it"
        println("properties:${params.submodule}")
    }
}

def buildAndTest(){
    stage("Backend tests"){
        sh "echo I can deliver"
    }
}
