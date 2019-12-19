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
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

        <title>Manage Quiz | MOBIC Quiz</title>
    </head>
    <body>

        <br>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="header clearfix">
                        <nav>
                            <ul class="nav nav-pills float-right">
                                <li class="nav-item text-center">
                                    <h5>${sessionScope.user.name}</h5>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="Logout"><button class="btn btn-danger float-right">
                                            Sign out
                                        </button></a>
                                </li>

                            </ul>
                        </nav>
                        <h3 class="">MOBIC QUIZ</h3>
                    </div>
                    <br>

                    <div class="row">
                        <div class="col-md-2 text-center ">
                            <a class="nav-link" href="Homepage.jsp">Home</a> 
                            <c:choose>
                                <c:when test="${sessionScope.usertype=='student'}">
                                    <a class="nav-link" href="QuizList">My Quiz</a> 
                                </c:when>
                                <c:otherwise>
                                    <a class="nav-link" href="ManageQuiz">Manage Quiz</a> 
                                </c:otherwise>
                            </c:choose>
                            <a class="nav-link" href="History">History</a>
                            <a class="nav-link" href="MyAccount">My Account</a>
                        </div>
                        <div class="col-md-10">

                            <!--    !! write content in this    -->
                            <div class="jumbotron">

                                <h1 class="text-muted text-center">Manage Quiz</h1>
                                <div>

                                    <a href="CreateQuiz" class="btn btn-success btn-lg align-middle">
                                        Create Quiz
                                    </a><br>

                                    <br>
                                    <p style="color: red">
                                        ${errorremove}
                                    </p>
                                    <p style="color: red">
                                        ${errorstatus}
                                    </p>
                                    <table class="table table-hover text-center" style="background-color: azure">
                                        <thead>
                                            <tr>
                                                <th>Quiz</th>
                                                <th>Name</th>
                                                <th>Subject</th>
                                                <th>Level</th>
                                                <th>Time</th>
                                                <th></th>

                                            </tr>
                                        </thead>
                                        <tbody class="table-sm" style="background-color: #ffffff">
                                            <c:forEach items="${quizs}" var="q" varStatus="count"><tr>
                                                    <td>${count.count}</td>
                                                    <td>${q.title}</td>
                                                    <td>${q.subjectno.subject}</td>
                                                    <td>${q.levelno.level}</td>
                                                    <td>${q.time}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${q.status==null}">
                                                                <a href="StatusQuiz?enablequiz=${q.quizno}" class="btn btn-lg align-middle btn-secondary">
                                                                    Turn On
                                                                </a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a href="StatusQuiz?disablequiz=${q.quizno}" class="btn btn-lg align-middle btn-primary">
                                                                    Turn Off
                                                                </a>
                                                            </c:otherwise>
                                                        </c:choose>


                                                        <a href="Remove?removequiz=${q.quizno}" class="btn btn-danger btn-lg align-middle ">
                                                            Remove
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <hr>
            <footer class="footer text-center ">
                <p>© Mobicquiz 2019</p>
            </footer>
        </div>



    </body>
</html>
