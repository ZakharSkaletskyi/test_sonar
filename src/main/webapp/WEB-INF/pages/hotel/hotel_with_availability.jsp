<%--
  Created by IntelliJ IDEA.
  User: Nazar
  Date: 29.07.2019
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title> ${hotelDto.hotelName} </title>
    <jsp:include page="../modules/_header.jsp"/>
</head>
<body>
<div style="padding-left: 15px">
<h2>Hotel "${hotelDto.hotelName}"</h2>

<p style="font-size: 12px;">
    Доступні кімнати в період <b>${hotelDto.startDate}</b> до <b>${hotelDto.endDate}</b> :
    <c:forEach var="room" items="${hotelDto.availableRooms}">
        <c:out value="${room.number}"/><p>
    </c:forEach>
</p>

<form action="/hotel/stat" method="get">
    <p>Cтатистика</p>
    <p>З: <input type="date" name="startDateStat" value= ${hotelDto.currentDate}>
        до: <input type="date" name="endDateStat" value= ${hotelDto.currentDate}>
    </p>
    <input type="hidden" name="hotelName" value="${hotelDto.hotelName}"/>
    <button type="submit">Отримати</button>
</form>
</div>
</body>
</html>
