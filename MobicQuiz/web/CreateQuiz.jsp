<%-- 
    Document   : CreateQuiz
    Created on : Nov 14, 2019, 3:10:18 PM
    Author     : Student
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Mobic Quiz</h1>
        <h2>Create Quiz</h2>
        <form action="Create" method="get">
            Subject<br><br>
            <input type="text" name="subject" required value="${param.subject}"/><br><br>
            Name<br><br>
            <input type="text" name="name" required value="${param.name}"/><br><br>
            Time<br><br>
            <input type="number" name="time" required value="${param.time}"/><br><br>
            Full Score<br><br>
            <input type="number" name="time" required value="${param.fullscore}"/><br><br>
            <input type="button" value="Create">
            <input type="submit" value="Submit">
                
        </form>
    </body>
</html>
