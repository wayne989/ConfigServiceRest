# ConfigServiceRest
Config Service REST api using Spring Boot

Summary:

    This is a simple application to demostrate Spring Boot for REST API
    
    Task #1
    Create a Config Service REST api using Spring Boot that implements below apis to return and update JSON documents representing an application’s config properties.
    
    The application needs to store the JSON documents in an embedded SQL database (H2, HSQLDB, etc.) using Spring Data JPA (or any other framework of your choice).
    • GET /api/{appCode}/config/{version} – return JSON document for specified appCode and version
    • POST /api/{appCode}/config/{version} – add new or update existing JSON document for specified appCode and version. JSON document should be included in the request body
    • GET /api/{appCode}/config – return list of available versions in JSON sorted by last modified date in descending order
    
    Task #2
    
    Create unit tests for task #1 (controller, service and repository layers).

Build:

    using Gradle build file build.gradle
    build command: ./gradlew clean build

IDE:

    Initially developed with IntelliJ IDEA, but Eclipse or Spring IDE will be fine.
