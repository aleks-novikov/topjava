<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Users</title>
</head>

<body>
<h3><a href="index.html">Home</a></h3>
<hr/>
<h2>Users</h2>

<section>
    <a href="users?action=create">Add User</a>
    <br><br>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Password</th>
            <th>Roles</th>
            <th>Registered date</th>
            <th>Calories per a day</th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.password}</td>
                <td>${user.strRoles}</td>
                <td>${user.registered}</td>
                <td>${user.caloriesPerDay}</td>
                <td><a href="users?action=update&id=${user.id}">Update</a></td>
                <td><a href="users?action=delete&id=${user.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>

</body>
</html>