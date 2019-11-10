<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>User</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>${param.action == 'create' ? 'Create user' : 'Edit user'}</h2>

    <jsp:useBean id="user" type="ru.javawebinar.topjava.model.User" scope="request"/>

    <form method="post" action="users">
        <input type="hidden" name="id" value="${user.id}">

        <dl>
            <dt>Role</dt>
            <dd>
                <label>
                    <select name="userRoles" multiple style="width: 180px;">
                        <label>${role.name}

                            <c:forEach var="role" items="${availableRoles}">
                            <c:if test="${role.name == 'User'}">
                            <option value="${role.name}" selected>
                                </c:if>

                                <c:if test="${role.name != 'User'}">
                            <option value="${role.name}">
                                </c:if>

                                    ${role.name}
                            </option>
                            </c:forEach>
                    </select>
                </label>

                <%--                <c:if test="${role.name == 'User'}">--%>
                <%--                            <input type="checkbox" name="userRoles" value="${role.name}" checked>--%>
                <%--                        </c:if>--%>
                <%--                        <c:if test="${role.name == 'User'}">--%>
                <%--                            <input type="checkbox" name="userRoles" value="${role.name}"/>--%>
                <%--                        </c:if>--%>
                <%--                    </label>--%>

            </dd>
        </dl>

        <dl>
            <dt>Name</dt>
            <dd><input type="text" value="${user.name}" name="name" required></dd>
        </dl>

        <dl>
            <dt>Password</dt>
            <dd><input type="text" value="${user.password}" name="password" required></dd>
        </dl>

        <dl>
            <dt>Email</dt>
            <dd><input type="text" value="${user.email}" name="email" required></dd>
        </dl>

        <dl>
            <dt>Calories per a day</dt>
            <dd><input type="text" value="${user.caloriesPerDay}" name="calories" required></dd>
        </dl>

        <dl>
            <dt>Registration date</dt>
            <dd><input type="text" value="${user.registered}" name="registered" required disabled></dd>
        </dl>

        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>
