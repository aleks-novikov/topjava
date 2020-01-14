####Запросы для тестирования MealRestController

*get, delete*:
http://localhost:8080/topjava/rest/profile/meals/100003

*createWithLocation:*

"dateTime":"2015-06-01T18:00:00","description":"Созданный ужин","calories":300

Body:
    {
        "dateTime":
        "2015-06-01T18:00:00",
        "description":"Созданный ужин",
        "calories":300
    }

*update:*

http://localhost:8080/topjava/rest/profile/meals/100003

Body:
    {
        "id":100003,
        "dateTime":"2015-05-30T10:00:00",
        "description":"Обновленный завтрак",
        "calories":200
    }

*getAll*:
http://localhost:8080/topjava/rest/profile/meals/

*filter*:
http://localhost:8080/topjava/rest/profile/meals/filter?startDate&startTime=18%3A00&endDate&endTime=20%3A00