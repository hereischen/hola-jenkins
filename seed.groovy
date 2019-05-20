#!/usr/bin/env groovy
import groovy.io.FileType

String jobDir = "${WORKSPACE}/vars/jobs"


def retrieveJobs (String jobDir) {
    def list = []
    def dir = new File(jobDir)
    dir.eachFileRecurse (FileType.FILES) { file ->
        list << file.getName()
    }
    return list
}

def buildPipelineJobs(String jobName){
    String repoUrl = "https://github.com/hereischen/hola-jenkins.git"
    println "Seeding job ${jobName}."
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
                scriptPath("vars/jobs/${jobName}")
            }
        }
        // build jobs to make their properties effective
        queue(jobName)
    }
}

def jobList = retrieveJobs (jobDir)
jobList.each {
    buildPipelineJobs(it)
}
