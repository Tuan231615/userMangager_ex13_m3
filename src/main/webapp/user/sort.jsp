<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 07/03/2023
  Time: 10:00 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sort by name</title>
</head>
<body>
<center>
    <h1>User Management</h1>
    <h2>
        <a href="users?action=users">List All Users</a>
    </h2>
</center>
<form method="post">
    <caption>
        <h2>sort user</h2>
    </caption>
    <table border="2">
        <tr>
            <td>Id</td>
            <td>Name</td>
            <td>Email</td>
            <td>Country</td>
        </tr>

        <c:forEach var="user" items="${user}">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.country}"/></td>
            </tr>
        </c:forEach>
    </table>
</form>
</body>
</html>
