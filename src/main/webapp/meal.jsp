<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <title>Meal</title>
    </head>

    <body>
        <h3><a href="index.html">Домой</a></h3>
        <hr>
        <h3><a href="meals">Список еды</a></h3>


        <form action="meals" method="POST">
            <input type="hidden" name="id" value="${meal.id}">

            <section class="content">
                <table class="meals_table">
                    <thead>
                        <tr>
                            <th>Дата</th>
                            <th>Описание</th>
                            <th>Количество калорий</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr>
                            <td><input type="datetime-local" name="date" value="${meal.dateTime}" required/></td>
                            <td><input type="text" name="description" value="${meal.description}" required></td>
                            <td><input type="text" name="calories" value="${meal.calories}" required></td>
                        </tr>
                    </tbody>
                </table>

                <div class="action_buttons">
                    <button type="submit">Сохранить</button>
                    <button type="button" onclick="window.history.back()">Отмена</button>
                </div>
            </section>
        </form>
    </body>
</html>
