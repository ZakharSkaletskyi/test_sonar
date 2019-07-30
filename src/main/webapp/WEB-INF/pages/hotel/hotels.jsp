<%--
  Created by IntelliJ IDEA.
  User: Nazar
  Date: 29.07.2019
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title> Hotels </title>
    <jsp:include page="../modules/_header.jsp"/>
</head>
<body>
<div style="padding-left: 15px">
    <h2>Hotels</h2>
    <form action="/hotel" method="get">
        <select name="name" style="
    width: auto;
    height: 25px;
    border-radius: 5px;
    display: block;
    border: 1px solid #aaa;">
            <c:forEach var="hotel" items="${hotels}">
                <option>${hotel.name}</option>
            </c:forEach>
        </select>
        <button type="submit" style=" margin-top: 5px;
    background-color: lightgray;
    width: auto;
    height: 25px;
    border-radius: 5px;
    border: 2px solid gray;">Відкрити
        </button>
    </form>
</div>
</body>
</html>
