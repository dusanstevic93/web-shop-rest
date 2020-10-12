# web-shop-rest
The back-end for an online shop application that provides a REST API.
The application is deployed on [Heroku cloud platform.](https://web-shop-rest.herokuapp.com/docs)
### Running the application locally with maven command line
   git clone `https://github.com/spring-petclinic/spring-petclinic-rest.git`  
   cd spring-petclinic-rest  
   ./mvnw spring-boot:run

You can then access Swagger UI at `http://localhost:8080/docs`
### Screenshot of Swagger UI
![swagger ui screenshoot](https://res.cloudinary.com/de3ijp4bt/image/upload/v1602459718/swagger_ss_rtpqca.png)
### Database configuration
Configuration is provided for MySQL and PostgreSQL databases.   
     
For MySQL database, it is needed to set active profile to mysql in application.properties.   
spring.profiles.active = mysql  
   
Before starting the application, it would be good to check properties defined in application-mysql.properties    
spring.datasource.url=jdbc:mysql://localhost:3306/webshop?createDatabaseIfNotExist=true  
spring.datasource.username=root   
spring.datasource.password=root   
spring.jpa.hibernate.ddl-auto= create-drop   
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect  

For PostgreSQL database, it is needed to set active profile to postgresql in application.properties.  
spring.profiles.active = postgresql  
  
Before starting the application, it would be good to check properties defined in application-postgresql.properties   
spring.datasource.url=jdbc:postgresql://localhost:5432/webshop  
spring.datasource.username=postgres   
spring.datasource.password=root   
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL9Dialect  
spring.jpa.hibernate.ddl-auto= create-drop

