<%-- 
    Document   : Homepage
    Created on : Nov 14, 2019, 1:13:43 AM
    Author     : Jn
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
        <title>Home</title>
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
                                        </button>
                                    </a>
                                </li>
                                <li class="nav-item">

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
                                <c:when test="${usertype=='student'}">
                                    <a class="nav-link" href="QuizList">My Quiz</a> 
                                </c:when>
                                <c:otherwise>
                                    <a class="nav-link" href="ManageQuiz">Manage Quiz</a> 
                                </c:otherwise>
                            </c:choose>
                            <a class="nav-link" href="History">History</a>
                            <a class="nav-link" href="MyAccount.jsp">My Account</a>
                        </div>
                        <div class="col-md-10">


                            <div class="jumbotron ">
                                <!--    !! write content in this    -->
                                <h1 class="text-muted text-center">Student Exam History</h1><br>
                                <div style="font-size: larger; background-color: white; padding: 10px">
                                    Title: ${quiz.title}<br>
                                    Subject: ${quiz.subjectno.subject}<br>
                                    Level: ${quiz.levelno.level}
                                </div>
                                
                                <div>
                                    <c:choose>
                                        <c:when test="${historysstudent!=null}">                                     
                                            <table class="table table-hover text-center" style="background-color: azure">
                                                <thead>
                                                    <tr>
                                                        <th>Date</th>
                                                        <th>No</th>
                                                        <th>Name</th>                                                        
                                                        <th>Score</th>
                                                    </tr>
                                                </thead>

                                                <tbody class="table-sm" style="background-color: white">
                                                    <c:forEach items="${historysstudent}" var="h" varStatus="theCount">
                                                        <tr>
                                                            <td>${h.date}</td> 
                                                            <td>${h.studentno.studentno}</td>
                                                            <td>${h.studentno.name}</td>                                                                                                                      
                                                            <td>${h.score}</td>
                                                        </tr>

                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="text-center">
                                                <h1 style="color: tomato">No History of Students.</h1>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
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
