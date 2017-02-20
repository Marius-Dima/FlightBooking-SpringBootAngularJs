## Flight Bookings Application
- Provides easy management of flight booking operations
- Built using Spring Boot REST, Data JPA and Caching
- In-Memory H2 database which is also saved locally to "bookings.db"
- AngularJs WebUI to consume the API endpoints for basic CRUD operations 


### How to run
Generate the executable uber-jar file by running in the project folder:

        mvn clean package
Run the resulting spring-angular-1.0.0.jar file from inside the target folder (will lose the database file correlation if ran from another folder...)
        
        java -jar spring-angular-1.0.0.jar
       
### Access the App UI AngularJs front-end
  
    http://localhost:8080
    

### Interactive REST Api Endpoint Documentation is available (can also be tested here): 
    http://localhost:8080/jsondoc-ui.html?url=jsondoc#