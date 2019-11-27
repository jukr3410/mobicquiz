<%-- 
    Document   : TestQuizList
    Created on : Nov 27, 2019, 10:40:07 PM
    Author     : Student
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <c:forEach items="${myquizs}" var="q">
            ${q.title}<br>
            ${q.fullscore}<br>
            ${q.subjectno.subject}<br>
            ${q.levelno.level}<br>
            <a href="Exam?quizno=${q.quizno}">Start</a><br><br>
        </c:forEach>
    </body>
</html>
