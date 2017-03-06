### Test REST controllers
> For windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/topjava/rest/admin/users --user admin@gmail.com:admin`

#### get Users 100001
`curl -s http://localhost:8080/topjava/rest/admin/users/100001 --user admin@gmail.com:admin`

#### get All Meals
`curl -s http://localhost:8080/topjava/rest/profile/meals --user user@yandex.ru:password`

#### get Meals 100003
`curl -s http://localhost:8080/topjava/rest/profile/meals/100003  --user user@yandex.ru:password`

#### get Meals between
`curl -s "http://localhost:8080/topjava/rest/profile/meals/between?startDateTime=2015-05-30T08:00:00&endDateTime=2015-05-30T16:00:00" --user user@yandex.ru:password`

#### get Meals not found
`curl -s -v http://localhost:8080/rest/meals/100008 --user user@yandex.ru:password`

#### delete Meals
`curl -s -X DELETE http://localhost:8080/rest/meals/100002 --user user@yandex.ru:password`

#### create Meals
`curl -s -X POST -d '{"dateTime":"2015-06-01T12:00","description":"Created lunch","calories":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/profile/meals --user user@yandex.ru:password`

#### update Meals
`curl -s -X PUT -d '{"dateTime":"2015-05-30T07:00", "description":"Updated breakfast", "calories":200}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/profile/meals/100003 --user user@yandex.ru:password`