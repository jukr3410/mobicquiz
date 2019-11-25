<%-- 
    Document   : HistoryTeacher
    Created on : Nov 19, 2019, 1:28:18 AM
    Author     : Jn
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History | MOBIC Quiz</title>
    </head>
    <body>
        <h1>History For Teacher !</h1>
            
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Date</th>
                        <th>level</th>
                        <th>Subject</th>
                        <th>Teacher</th>
                        
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${tquizs}" var="tq">
                    <tr>
                        <td>${tq.quizno}</td>
                        <td>${tq.title}</td>
                        <td>${tq.levelno.level}</td>
                        <th>${tq.subjectno.subject}</th>
                        <td>${tq.teacherno.name}</td>
                    </tr>
                     </c:forEach>
                </tbody>
            </table>
        
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Date</th>
                        <th>Student</th>
                        <th>Score</th>
                        <th>name</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${historys}" var="h">
                    <tr>
                        <td>${h.historyno}</td>
                        <td>${h.date}</td>
                        <td>${h.subject}</td>
                        <th>${h.score}</th>
                        <th>${h.title}</th>
                    </tr>
                     </c:forEach>
                </tbody>
            </table>

       
    </body>
</html>
