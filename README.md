[![Dependency Status](https://dependencyci.com/github/denis-yakovenko/VotingSystem/badge)](https://dependencyci.com/github/denis-yakovenko/VotingSystem)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/83bf2899f27345d58200d178c1a3fbc9)](https://www.codacy.com/app/maximka1945/VotingSystem?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=denis-yakovenko/VotingSystem&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/denis-yakovenko/VotingSystem.svg?branch=master)](https://travis-ci.org/denis-yakovenko/VotingSystem)
## VotingSystem
JSON API using Hibernate/Spring/Spring-Boot without frontend  
The task is:  
Build a voting system for deciding where to have lunch.  
2 types of users: admin and regular users  
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)  
Menu changes each day (admins do the updates)  
Users can vote on which restaurant they want to have lunch at  
Only one vote counted per user  
If user votes again the same day:  
If it is before 11:00 we asume that he changed his mind.  
If it is after 11:00 then it is too late, vote can't be changed  
Each restaurant provides new menu each day.  
## Installation
`mvn install`
## Usage
Database schema will be updated automatically when application started.  
User with role "Admin" will be created automatically when application started.  
Launching application: `java -jar target/VotingSystem-0.0.1-SNAPSHOT.jar`
## Possible curl commands
For windows use `Git Bash`
#### Admin can manipulate users
insert  `curl -s -S -u admin:admin -X POST -H "Content-Type: application/json" -d '{"name":"user", "password":"user", "role":"ROLE_USER"}' http://localhost:8080/api/v1/users`  
get all `curl -s -S -u admin:admin -X GET http://localhost:8080/api/v1/users`  
update  `curl -s -S -u admin:admin -X POST -H "Content-Type: application/json" -d '{"id":1, "name":"user updated", "password":"password updated"}' http://localhost:8080/api/v1/users`  
get     `curl -s -S -u admin:admin -X GET http://localhost:8080/api/v1/users/1`  
delete  `curl -s -S -u admin:admin -X DELETE http://localhost:8080/api/v1/users/1`  
#### Admin can manipulate restaurants
insert  `curl -s -S -u admin:admin -X POST -H "Content-Type: application/json" -d '{"name":"Restaurant 1"}' http://localhost:8080/api/v1/restaurants`  
get all `curl -s -S -u admin:admin -X GET http://localhost:8080/api/v1/restaurants`  
update  `curl -s -S -u admin:admin -X POST -H "Content-Type: application/json" -d '{"id":1, "name":"Restaurant 1 updated"}' http://localhost:8080/api/v1/restaurants`  
get     `curl -s -S -u admin:admin -X GET http://localhost:8080/api/v1/restaurants/1`  
delete  `curl -s -S -u admin:admin -X DELETE http://localhost:8080/api/v1/restaurants/1`
#### Admin can manipulate dishes
insert  `curl -s -S -u admin:admin -X POST -H "Content-Type: application/json" -d '{"name":"Dish 1", "price":12.34}' http://localhost:8080/api/v1/dishes`  
get all `curl -s -S -u admin:admin -X GET http://localhost:8080/api/v1/dishes`  
update  `curl -s -S -u admin:admin -X POST -H "Content-Type: application/json" -d '{"id":1, "name":"Dish 1 updated", "price":99.99}' http://localhost:8080/api/v1/dishes`  
get     `curl -s -S -u admin:admin -X GET http://localhost:8080/api/v1/dishes/1`  
delete  `curl -s -S -u admin:admin -X DELETE http://localhost:8080/api/v1/dishes/1`
#### Admin can manipulate menus
insert  `curl -s -S -u admin:admin -X POST -H "Content-Type: application/json" -d '{"restaurantId":1,"date":"2017-03-06","dishes":[1,3,4]}' http://localhost:8080/api/v1/menus`  
get all `curl -s -S -u admin:admin -X GET http://localhost:8080/api/v1/menus?date=2017-03-06`  
delete  `curl -s -S -u admin:admin -X DELETE http://localhost:8080/api/v1/menus/1`
#### Any authenticated user can get a menu for today or any other date
get a menu for today     `curl -s -S -u admin:admin -X GET http://localhost:8080/api/v1/menus`  
get a menu for some date `curl -s -S -u user:user -X GET http://localhost:8080/api/v1/menus?date=2017-03-06`
#### Any authenticated user can vote for any restaurant by id
vote for restaurant with id 1 `curl -s -S -u admin:admin -X POST http://localhost:8080/api/v1/votes/1`
#### Any authenticated user can get vote results
get vote results `curl -s -S -u user:user -X GET http://localhost:8080/api/v1/results`