# auth-wizard

This application contains simple user auth system with spring boot. 

##Requirements
1. ###Install postgresql 
    * `brew install postgres` (Mac OsX command)
    * `brew services start postgresql` (start postgres)
2. ###Setting up user and database with postgres
    * `create user root with encrypted password 'admin';` (create use)
    * `create database auth_wizard; (create database);`
    * `grant all privileges on database auth_wizard to root;` (grant permission to the user on database auth_wizard)
        
3. ###Deployment
    * clone this repository
    * `cd auth-wizard`
    * `mvn clean install` -- build project
    * populate properties file values
    * `mvn spring-boot:run` -- command to deploy this project
4. ###Verification
    * After deployment, open swagger-ui using endpoint (localhost:8080/auth-wizard/swagger-ui.html)
    * In swagger-UI, there are two endpoint one for signup and one for login
    * signup endpoint provides functionality to register and login is for login purpose
              