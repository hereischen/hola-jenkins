def createDeploymentJob(jobName, repoUrl) {
    pipelineJob(jobName) {
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(repoUrl)
                        }
                        branches('master')
                        extensions {
                            cleanBeforeCheckout()
                        }
                    }
                }
                scriptPath("jobs/deploy.groovy")
            }
        }
    }
}

def createTestJob(jobName, repoUrl) {
    multibranchPipelineJob(jobName) {
        branchSources {
            git {
                remote(repoUrl)
                includes('*')
            }
        }
        triggers {
            cron("H/5 * * * *")
        }
    }
}

def buildPipelineJobs() {
    def repo = "https://github.com/hereischen/hola-jenkins"
    def repoUrl = repo + ".git"
    def deployName = jobName + "_deploy"
    // def testName = jobName + "_test"
    def upstream = "upstream"

    createDeploymentJob(deployName, repoUrl)
    createDeploymentJob(upstream, repoUrl)
    // createTestJob(testName, repoUrl)
    queue(deployName)
    if (!jenkins.model.Jenkins.instance.getItemByFullName(upstream)) {
    queue(upstream)
    }
}

buildPipelineJobs()
