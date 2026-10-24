Here's a practical example of a Jenkins declarative pipeline that uses Groovy scripting for a typical Java/Maven build and deploy flow.

## Declarative Pipeline (Jenkinsfile)

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-17'
    }

    environment {
        APP_NAME     = 'order-service'
        REGISTRY     = 'registry.example.com'
        IMAGE_TAG    = "${env.BUILD_NUMBER}"
        DEPLOY_ENV   = "${env.BRANCH_NAME == 'main' ? 'prod' : 'dev'}"
    }

    options {
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }

    parameters {
        booleanParam(name: 'SKIP_TESTS', defaultValue: false, description: 'Skip unit tests')
        choice(name: 'TARGET_ENV', choices: ['dev', 'qa', 'prod'], description: 'Deployment target')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                script {
                    // Groovy: capture git commit info
                    env.GIT_COMMIT_SHORT = sh(
                        script: 'git rev-parse --short HEAD',
                        returnStdout: true
                    ).trim()
                    echo "Building commit ${env.GIT_COMMIT_SHORT} on branch ${env.BRANCH_NAME}"
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile -B'
            }
        }

        stage('Test') {
            when {
                expression { return !params.SKIP_TESTS }
            }
            steps {
                sh 'mvn test -B'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests -B'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    def image = docker.build("${REGISTRY}/${APP_NAME}:${IMAGE_TAG}")
                    docker.withRegistry("https://${REGISTRY}", 'registry-credentials') {
                        image.push()
                        image.push('latest')
                    }
                }
            }
        }

        stage('Parallel Checks') {
            parallel {
                stage('Static Analysis') {
                    steps { sh 'mvn checkstyle:check -B' }
                }
                stage('Security Scan') {
                    steps { sh 'mvn dependency-check:check -B' }
                }
            }
        }

        stage('Approval') {
            when {
                expression { params.TARGET_ENV == 'prod' }
            }
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    input message: 'Deploy to production?', ok: 'Deploy'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    deployApp(params.TARGET_ENV, IMAGE_TAG)
                }
            }
        }
    }

    post {
        success {
            echo "Build ${env.BUILD_NUMBER} succeeded"
        }
        failure {
            script {
                notifyFailure()
            }
        }
        cleanup {
            cleanWs()
        }
    }
}

// ---- Groovy helper functions ----

def deployApp(String envName, String tag) {
    def namespaces = [dev: 'dev-ns', qa: 'qa-ns', prod: 'prod-ns']
    def ns = namespaces[envName]

    withCredentials([file(credentialsId: "kubeconfig-${envName}", variable: 'KUBECONFIG')]) {
        sh """
            kubectl set image deployment/${APP_NAME} \
                ${APP_NAME}=${REGISTRY}/${APP_NAME}:${tag} -n ${ns}
            kubectl rollout status deployment/${APP_NAME} -n ${ns} --timeout=120s
        """
    }
}

def notifyFailure() {
    mail to: 'team@example.com',
         subject: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
         body: "Check console output: ${env.BUILD_URL}"
}
```

## Key Groovy Features Used

- **`script { }` blocks**: allow arbitrary Groovy inside declarative pipelines (variables, loops, conditionals).
- **GString interpolation**: `"${env.BUILD_NUMBER}"` works only in double-quoted strings.
- **Maps and functions**: `namespaces[envName]` and the `deployApp` helper.
- **`returnStdout: true`**: captures shell output into a Groovy variable.
- **`when { expression { } }`**: conditional stage execution using Groovy logic.

## Scripted Pipeline Equivalent (shorter example)

```groovy
node {
    def mvnHome = tool 'Maven-3.9'
    def services = ['order-service', 'payment-service', 'inventory-service']

    try {
        stage('Checkout') {
            checkout scm
        }

        stage('Build All') {
            // Dynamic parallel stages with a Groovy loop
            def builds = [:]
            for (svc in services) {
                def name = svc // capture loop variable for the closure
                builds[name] = {
                    dir(name) {
                        sh "${mvnHome}/bin/mvn clean package -B"
                    }
                }
            }
            parallel builds
        }

        stage('Archive') {
            archiveArtifacts artifacts: '**/target/*.jar'
        }
    } catch (err) {
        currentBuild.result = 'FAILURE'
        echo "Pipeline failed: ${err.getMessage()}"
        throw err
    }
}
```

## Reusable Shared Library Example

`vars/buildJavaApp.groovy` in a shared library repo:

```groovy
def call(Map config = [:]) {
    def goals = config.goals ?: 'clean package'
    def jdk   = config.jdk   ?: 'JDK-17'

    pipeline {
        agent any
        tools { jdk jdk; maven 'Maven-3.9' }
        stages {
            stage('Build') {
                steps { sh "mvn ${goals} -B" }
            }
        }
    }
}
```

Usage in a Jenkinsfile:

```groovy
@Library('my-shared-lib') _

buildJavaApp(goals: 'clean verify', jdk: 'JDK-21')
```

## Common Gotchas

- Use **single quotes** for `sh` commands containing secrets (`'echo $PASSWORD'`) so Groovy doesn't interpolate them into logs.
- Variables from closures in loops need to be copied to a local `def` (as shown above) to avoid capturing the last value.
- Jenkins uses a CPS-transformed Groovy, so some methods (like `.each` with closures or `.collect`) can behave unexpectedly; prefer plain `for` loops. Use `@NonCPS` on helper methods that need non-serializable operations.

---

I'm assuming you mean **closures**. A closure in Groovy is an anonymous block of code that can take parameters, return a value, and be assigned to a variable or passed around like an object. It is an instance of `groovy.lang.Closure`.

## Basic syntax

```groovy
def greet = { name -> "Hello, ${name}" }

println greet('Raja')        // Hello, Raja
println greet.call('Raja')   // same thing
```

## Parameters

```groovy
def add = { int a, int b -> a + b }       // explicit params
def square = { it * it }                  // implicit single param: 'it'
def hello = { -> println 'no params' }    // explicitly no params

println add(2, 3)     // 5
println square(4)     // 16
```

## Passing closures to methods

This is where closures show up most, especially in collection methods:

```groovy
def nums = [1, 2, 3, 4, 5]

nums.each { println it }
def evens   = nums.findAll { it % 2 == 0 }    // [2, 4]
def doubled = nums.collect { it * 2 }         // [2, 4, 6, 8, 10]
def total   = nums.inject(0) { sum, n -> sum + n }  // 15
```

When a closure is the last argument, it can go outside the parentheses.

## Capturing variables

A closure can read and modify variables from the scope where it was defined:

```groovy
def counter = 0
def increment = { counter++ }

increment()
increment()
println counter   // 2
```

## `delegate`, `owner`, and `this`

Inside a closure:
- `this` is the enclosing class
- `owner` is the enclosing object (a class or another closure)
- `delegate` is the object the closure's unresolved method and property calls go to, and it can be changed

```groovy
class Config {
    String name
    int port
}

def configure = { 
    name = 'order-service'
    port = 8080
}

def cfg = new Config()
configure.delegate = cfg
configure.resolveStrategy = Closure.DELEGATE_FIRST
configure()

println "${cfg.name}:${cfg.port}"   // order-service:8080
```

This delegate mechanism is how Groovy DSLs work, including Gradle build scripts and Jenkins pipelines.

## Currying

```groovy
def multiply = { a, b -> a * b }
def triple = multiply.curry(3)

println triple(5)   // 15
```

## Closures in Jenkins pipelines

Almost every pipeline step you write is a closure passed to a method:

```groovy
stage('Build') {            // closure passed to stage()
    steps {                 // closure passed to steps()
        script {            // closure passed to script()
            sh 'mvn clean package'
        }
    }
}

timeout(time: 10, unit: 'MINUTES') {   // closure as last argument
    sh 'long-running-task.sh'
}
```

The earlier scripted example used closures as map values for parallel execution:

```groovy
builds[name] = {
    dir(name) { sh 'mvn package' }
}
parallel builds
```

One Jenkins caveat: closures inside `.each` or `.collect` can misbehave under CPS transformation, so a plain `for` loop is often safer in pipeline code.

---
First, a note: `buildJavaApp` in my earlier example was something I made up to illustrate shared libraries. It doesn't exist unless you create it. If you're seeing it in a real Jenkinsfile at work, here's how to track it down.

## Where it's implemented

A call like `buildJavaApp(...)` is a **global variable** from a Jenkins Shared Library. Each file in the library's `vars/` folder becomes a callable step, so the implementation is in:

```
<shared-library-repo>/
├── vars/
│   ├── buildJavaApp.groovy   ← implementation, its call() method is what runs
│   └── buildJavaApp.txt      ← optional help/documentation
├── src/                      ← helper classes (org/company/Utils.groovy)
└── resources/                ← static files loaded with libraryResource
```

**Steps to find the repo:**

1. Look at the top of the Jenkinsfile for `@Library('some-lib-name') _`. That name identifies the library.
2. In Jenkins, go to **Manage Jenkins → System → Global Pipeline Libraries** (or in the folder's **Configure → Pipeline Libraries**, since libraries can be defined at folder level too). Find the entry with that name. It shows the Git repo URL and default branch or version.
3. Clone that repo and open `vars/buildJavaApp.groovy`.

You can also check a build's console output. Near the top, Jenkins prints a line like `Loading library some-lib-name@main`, which tells you the exact version used.

## How to know the parameters

Open the file and look at the `call` method signature:

```groovy
// Style 1: Map of named options
def call(Map config = [:]) {
    def goals = config.goals ?: 'clean package'   // optional, with default
    def jdk   = config.jdk                        // read as config.jdk
    ...
}
```
Called as `buildJavaApp(goals: 'clean verify', jdk: 'JDK-21')`. The valid keys are whatever the code reads from `config`, so search the file for `config.`.

```groovy
// Style 2: Positional parameters
def call(String appName, String jdkVersion = '17') { ... }
```
Called as `buildJavaApp('order-service', '21')`.

```groovy
// Style 3: Closure-based DSL
def call(Closure body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    ...
}
```
Called as `buildJavaApp { goals = 'verify'; jdk = 'JDK-21' }`.

## Other places to look

- **`vars/buildJavaApp.txt` or `.md`**: if the library authors documented it, this is rendered in Jenkins at **`<job-url>/pipeline-syntax/globals`** (the "Global Variable Reference" page). It's the quickest way to see docs without cloning anything.
- **The library's README**, or other Jenkinsfiles in your org that already call `buildJavaApp`, which show real usage examples.
- **Library `src/` classes**, since `call()` often delegates to helpers there that read additional config keys.
