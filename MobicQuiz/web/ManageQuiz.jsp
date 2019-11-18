<%-- 
    Document   : ManageQuiz
    Created on : Nov 14, 2019, 12:51:37 PM
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
        <h1>Manage Quiz</h1>
        <h2>Welcome : ${sessionScope.user.name}</h2>
        <button>Create Quiz</button>
        <br>
        <br>
        <h3><a href="CreateQuiz.jsp">Create Quiz</a></h3>
        <table border="1">
            <thead>
                <tr>
                    <th>Quiz</th>
                    <th>Name</th>
                    <th>Subject</th>
                    <th>Level</th>
                    <th>Time</th>
                    <th>Remove</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${quizs}" var="q"><tr>
                        <td>${q.quizno}</td>
                        <td>${q.title}</td>
                        <td>${q.subjectno.subject}</td>
                        <td>${q.levelno.level}</td>
                        <td>${q.time}</td>
                        <td><a href="Remove?remove=${q.quizno}">
                                Remove
                            </a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>
