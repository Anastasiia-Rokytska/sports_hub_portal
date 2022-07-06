# SportHubPortal

Website about different sports and these leagues

## The website is powered off:
 - Backend: Spring Framework
 - Frontend: Angular
 - Database: MySQL

## Algoritm to clone and configure the project
1. To clone the project to your local folder write next in terminal:   
    ``` git clone  https://github.com/Anastasiia-Rokytska/sports_hub_portal.git  ```
2. Create the database ```sporthubportal``` in your MySQL workbench\terminal
3. Configure your ```application.properties``` file by next:
    
    a) ```server.port``` - launch port of website, default it is ```8080``` or ```8000```
   
    b) ``` spring.datasource.username ``` - your username in MySQL workbench\terminal
    
    c) ``` spring.datasource.password ``` - your password in MySQL workbench\terminal
    
4. Create the spring boot configuration in your Java IDE
5. Run the project in your IDE
6. View the website by the http://localhost:8000    (or another port, which you've written in application.properties)

## Algoritm to run project using Docker
1. If you don`t have Docker installed , you can do it from link:
https://www.docker.com/get-started/
2. Open project and run next command in terminal:
``docker-compose up``
3. View the website by the http://localhost:8000

Don`t forget to remove the containers with the following command:
``docker-compose down``
