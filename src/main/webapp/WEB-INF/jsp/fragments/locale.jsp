<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<li class="nav-item dropdown">
    <a class="dropdown-toggle nav-link" data-toggle="dropdown"><spring:message code="app.locale"/></a>
    <div class="dropdown-menu">
        <a class="dropdown-item" href="<%=request.getAttribute("javax.servlet.forward.request_uri")%>?lang=ru">Rus</a>
        <a class="dropdown-item" href="<%=request.getAttribute("javax.servlet.forward.request_uri")%>?lang=en">Eng</a>
    </div>
</li>