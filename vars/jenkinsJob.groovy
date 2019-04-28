def call(){
    if(env.JOB_NAME.contains("deploy")){
        addProperties()
    } else if (env.JOB_NAME.contains("upstream")) {
        addCIBuildTrigger()
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

def addProperties(){
    properties([
        parameters([
            string(name: 'example_param1', defaultValue: 'hello word'),
            string(name: 'example_param2', defaultValue: ''),
            string(name: 'example_param3', defaultValue: '')
        ]),
        pipelineTriggers([
            upstream(upstreamProjects: 'upstream', threshold:
            hudson.model.Result.SUCCESS)
        ]),  
    ])
}

def addCIBuildTrigger(){
    properties([
        pipelineTriggers([
            [$class: 'CIBuildTrigger',
                noSquash: true,
                providerData: [
                    $class: 'ActiveMQSubscriberProviderData',
                    name: 'Red Hat UMB',
                    selector: "method = 'build' AND new = 'CLOSED'",
 
                ]  
            ]
        ]), 
    ])
}

def packageArtifact(){
    stage("Package artifact") {
        sh "echo this is it"
        println("properties:${params.example_param1}")
        println("test")
    }
}

def buildAndTest(){
    stage("Backend tests"){
        sh "echo I can deliver"
    }
}
