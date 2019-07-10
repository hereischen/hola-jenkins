# Setting up seed job with the Job DSL


## Prerequisites
1. [Download and run Jenkins](https://jenkins.io/doc/pipeline/tour/getting-started/)
    1. Download Jenkins.
    2. Open up a terminal in the download directory.
    3. Run `java -jar jenkins.war --httpPort=8080`.
    4. Browse to `http://localhost:8080`.
    5. Follow the instructions to complete the installation.
2. Familiarity with Jenkins Pipeline.

## Proof of concept
Now that the prerequisites are out of the way, the first thing we are going to do is set up a  simple proof of concept **Freestyle project** (`seedJob`) in Jenkins. This job will be used to generate other jobs you want to on-board into Jenkins.


```
graph TD;
    A-->B;
    A-->C;
    B-->D;
    C-->D;
```

