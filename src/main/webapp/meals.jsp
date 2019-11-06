<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <title>Список еды</title>
    </head>

    <body>
        <h3><a href="index.html">Домой</a></h3>
        <hr>
        <h2 class="meals_header">Список еды</h2>

        <section class="content">
            <table class="meals_table">
                <thead>
                    <tr>
                        <th>Дата</th>
                        <th>Описание</th>
                        <th>Количество калорий</th>
                        <th>Превышен лимит за день</th>
                        <th></th>
                        <th></th>
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
                            <td><a href="meals?action=update&id=${meal.id}">Обновить</a></td>
                            <td><a href="meals?action=delete&id=${meal.id}">Удалить</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="action_buttons">
                <input type="button" value="Добавить" onclick="location.href='meals?action=add'">
            </div>

        </section>
    </body>
</html>
