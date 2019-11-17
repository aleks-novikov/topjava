<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>

    <input type="hidden" name="id" value="${meal.id}"/>

    <h3>Filter Meals</h3>

    <form action="meals" method="get">
        <div>
            <label for="startTime">From</label>
            <%--IMPORTANT! Get parameter from Servlet using param.paramName--%>
            <input type="time" name="startTime" id="startTime" value="${param.startTime}">

            <label for="endTime">To</label>
            <input type="time" name="endTime" id="endTime" value="${param.endTime}">
        </div>

        <div>
            <label for="startDate">From</label>
            <input type="date" name="startDate" id="startDate" value="${param.startDate}">

            <label for="endDate">To</label>
            <input type="date" name="endDate" id="endDate" value="${param.endDate}">
        </div>

        <button type="submit" class="filter_btn">Filter</button>
    </form>

    <hr/>

    <a href="meals?action=create">Add Meal</a>
    <br/>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>