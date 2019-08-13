<%--
  Created by IntelliJ IDEA.
  User: Alex
  Date: 31.07.2019
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>The Sun - Profile</title>
    <link rel="stylesheet" href="../../../res/css/profile.css"/>
    <jsp:include page="../modules/_header.jsp"/></head>
<body>

<div class="profileContent" >
    <p>Select the client</p>
    <form action="/profile" method="Post" class="listClient">
        <select name="clientId">
            <c:forEach var="client" items="${clients}">
                <option value="${client.id}">${client.firstName} ${client.lastName}</option>
            </c:forEach>
        </select>
        <div class="submitClient">
            <button type="submit" class="buttonBack">Open</button>
        </div>
    </form>
</div>
</body>
</html>
