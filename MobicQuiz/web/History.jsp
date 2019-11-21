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
                                <h1 class="text-muted text-center">My History</h1>

                                <!--    !! write content in this    -->

                                <br>
                                <br>
                                <div>
                                    <c:choose>
                                        <c:when test="${historys!=null}">


                                            <select class="form-control">
                                                <option value="thsi">Thai</option>
                                                <option value="science">Science</option>
                                                <option value="social">Social</option>
                                            </select><br>
                                            <c:choose>
                                                <c:when test="${usertype=='student'}">
                                                    <table class="table table-hover text-center" style="background-color: azure">
                                                        <thead>
                                                            <tr>
                                                                <th>No</th>
                                                                <th>Date</th>
                                                                <th>Subject</th>
                                                                <th>Title</th>
                                                                <th>Score</th>
                                                            </tr>
                                                        </thead>

                                                        <tbody class="table-sm" style="background-color: white">

                                                            <c:forEach items="${historys}" var="h" varStatus="theCount">
                                                                <tr>
                                                                    <td>${theCount.count}</td>
                                                                    <td>${h.date}</td>
                                                                    <td>${h.quizno.subjectno.subject}</td>
                                                                    <td>${h.quizno.title}</td>
                                                                    <td>${h.score}</td>
                                                                </tr>

                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </c:when>
                                                <c:otherwise>
                                                    <table class="table table-hover text-center" style="background-color: azure">
                                                        <thead>
                                                            <tr>
                                                                <th>No</th>
                                                                <th>Title</th>
                                                                <th>Level</th>
                                                                <th>Subject</th>
                                                                <th></th>
                                                            </tr>
                                                        </thead>
                                                        <tbody class="table-sm" style="background-color: white">
                                                            <c:forEach items="${historys}" var="h" varStatus="theCount">
                                                                <tr>
                                                                    <td>${theCount.count}</td>
                                                                    <td>${h.title}</td>
                                                                    <td>${h.levelno.level}</td>
                                                                    <td>${h.subjectno.subject}</td>

                                                                    <td>
                                                                        <div>                           
                                                                            <a href="History?viewstudent=${h.quizno}" class="btn btn-info btn-lg align-middle">View</a>
                                                                            
                                                                        </div>
                                                                    </td>
                                                                </tr>

                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:when>
                                        <c:otherwise>
                                            <div class="text-center">
                                                <h1 style="color: tomato">No History.</h1>
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
