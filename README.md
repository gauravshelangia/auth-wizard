# auth-wizard

This application contains simple user auth system with spring boot as multi-module project. 

##Requirements
1. ###Install postgresql 
    * `brew install postgres` (Mac OsX command)
    * `brew services start postgresql` (start postgres)
2. ###Setting up user and database with postgres
    * `create user root with encrypted password 'admin';` (create use)
    * `create database auth_wizard; (create database);`
    * `grant all privileges on database auth_wizard to root;` (grant permission to the user on database auth_wizard)
        
      