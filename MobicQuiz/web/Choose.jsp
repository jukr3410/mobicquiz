<%-- 
    Document   : Choose
    Created on : Nov 15, 2019, 3:29:31 PM
    Author     : Student
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

        <title>JSP Page</title>
    </head>
    <body>

        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <h1>Mobic Quiz</h1>
                    <h4>Welcome : </h4>
                    <select>
                        <option value="thsi">Thai</option>
                        <option value="science">Science</option>
                        <option value="social">Social</option>

                    </select>
                    <br>
                    <br>
                    <table border="1">
                        <thead>
                            <tr>
                                <th></th>
                                <th>Subject</th>
                                <th>Name</th>
                                <th>Time</th>
                                <th>Score</th>
                                <th>Ready</th>

                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${quizs}" var="q">
                                <tr>
                                    <td></td>
                                    <td>${q.subjectno}</td>
                                    <td>${q.title}</td>
                                    <td>${q.time}</td>
                                    <td>${q.fullscore}</td>
                                    <td></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>


                </div>
            </div>
        </div>
    </body>
</html>
