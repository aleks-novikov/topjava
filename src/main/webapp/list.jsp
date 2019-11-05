<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <title>Meals</title>
    </head>

    <body>
        <h3><a href="index.html">Home</a></h3>
        <hr>
        <h2 class="meals_header">Meals</h2>

        <section class="content">
            <table class="meals_table">
                <thead>
                    <tr>
                        <th>Дата</th>
                        <th>Описание</th>
                        <th>Количество калорий</th>
                        <th>Превышен лимит за день</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="meal" items="${mealsList}">
                        <c:set var="meal_color" value="${meal.excess ? 'meal_red' : 'meal_green'}"/>

                        <tr class="${meal_color}">
                            <td>${meal.strDate}</td>
                            <td>${meal.description}</td>
                            <td>${meal.calories}</td>
                            <td>${meal.excess ? "Да" : "Нет"}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>
    </body>
</html>
