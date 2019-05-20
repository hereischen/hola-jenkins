#!/usr/bin/env groovy

def buildPipelineJobs(){
    String jobName = "composeTrigger"
    String repoUrl = "https://github.com/hereischen/hola-jenkins.git"
    pipelineJob(jobName) {
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(repoUrl)
                        }
                        branches('new-imp')
                        extensions {
                            cleanBeforeCheckout()
                        }
                    }
                }
                scriptPath("vars/jobs/${jobName}.groovy")
            }
        }
        // trigger jobs when the code base changes. 
        triggers {
            gitlabPush()
        }
        // build jobs to make their properties effective
        queue(jobName)
    }
}

buildPipelineJobs()