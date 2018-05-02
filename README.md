# ConfigServiceRest
Config Service REST api using Spring Boot

##Summary:

    This is a simple application to demostrate Spring Boot for REST API
    
    Task #1
    Create a Config Service REST api using Spring Boot that implements below apis to return and update JSON documents representing an application’s config properties.
    
    The application needs to store the JSON documents in an embedded SQL database (H2) using Spring Data JPA.
    • GET /api/{appCode}/config/{version} – return JSON document for specified appCode and version
    • POST /api/{appCode}/config/{version} – add new or update existing JSON document for specified appCode and version. JSON document should be included in the request body
    • GET /api/{appCode}/config – return list of available versions in JSON sorted by last modified date in descending order

    Task #2
    Global Exception Handling - handle web service related exception with returning error in json format
    
    Task #3
    Centralize the logging by implementing spring aop which includes performance and error logs.    
    
    Task #4 
    Create unit tests for controller, service and repository layers.  This is only for demostration purposes, so the test cases cover most but not all cases


##Build:

    using Gradle build file build.gradle
    build command: ./gradlew clean build

##IDE:

    Initially developed with IntelliJ IDEA, but Eclipse or Spring IDE will be fine.
    
    Note for IntelliJ Version 2018.1 :
    	in order to process annotation for Lombok, please ensure the following setting is configured:
    	IntelliJ IDEA -> Preferences -> Build,Excution,Deployment -> Compiler -> Annotation Processors
    		checked - Enable annotation processing
    		selected - Obtain processors from project classpath
