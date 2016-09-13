<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %><%--
  Created by IntelliJ IDEA.
  User: Alex
  Date: 10-Sep-16
  Time: 7:14 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmp"
uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
    <title>Meal List</title>
    <style>
        .normal{color: green;}
        .exceeded {color: red;}
    </style>
</head>
<body>

<h2><a href="index.html">Home</a></h2>
<h3>Meal list</h3>
<a href="meals?action=create">Add Meal</a>

<table border="1" cellpadding="8" cellspacing="0">
    <thead>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </thead>
    <c:forEach items="${mealList}" var="item">
        <jsp:useBean id="item" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed" />
        <tr class="${item.exceed ? 'exceeded' : 'normal'}">
            <td><%=TimeUtil.toString(item.getDateTime())%></td>
            <td><c:out value="${item.description}" /></td>
            <td><c:out value="${item.calories}" /></td>
            <td><a href="meals?action=update&id=${item.id}">Upadte</a></td>
            <td><a href="meals?action=delete&id=${item.id}">Delete</a></td>

        </tr>

    </c:forEach>
</table>

</body>
</html>
