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
        <title>Create Quiz</title>
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

                            <div class="jumbotron">
                                <h2 class="text-muted text-center">Create Quiz</h2>
                                <div>
                                    <form role="form" action="CreateQuiz" method="get">
                                        <div>

                                            <div class="form-group">

                                                <label for="subject">
                                                    Subject
                                                </label>
                                                <select class="form-control" name="subject">
                                                    <option value="thai">Thai</option>
                                                    <option value="science">Science</option>
                                                    <option value="socia l">Social</option>
                                                </select>
                                            </div>
                                            <div class="form-group">

                                                <label for="title">
                                                    Title
                                                </label>
                                                <input type="text" class="form-control" name="title" value="${sessionScope.newquiz.title}" placeholder="Title"/>
                                            </div>
                                            <div class="form-group">

                                                <label for="time">
                                                    Time
                                                </label>
                                                <input type="number" class="form-control" name="time" min="0" value="${sessionScope.newquiz.time}" placeholder="Time of Exam"/>

                                            </div>
                                            <div class="form-group">

                                                <label for="fullscore">
                                                    Full Score
                                                </label>
                                                <input type="number" class="form-control" name="fullscore" min="0" value="${sessionScope.newquiz.fullscore}" placeholder="Full Score"/>

                                            </div>


                                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalLong">
                                                Add Question
                                            </button>
                                            <br><br>
                                            <button type="submit" class="btn btn-success">
                                                Submit
                                            </button>


                                        </div>

                                        <!-- Button trigger modal -->


                                        <!-- Modal -->
                                        <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                                            <div class="modal-dialog" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="exampleModalLongTitle">Create</h5>

                                                    </div>
                                                    <div class="modal-body">

                                                        <div class="form-group">

                                                            <label for="subject">
                                                                Question
                                                            </label>
                                                            <input type="text" class="form-control" id="question" name="question" placeholder="Question ?" required/>
                                                        </div>

                                                        <div class="form-group">

                                                            <label for="time">
                                                                Choice
                                                            </label>
                                                            <input type="text" class="form-control" placeholder="Answer 1" name="ans1" required><br>
                                                            <input type="text" class="form-control" placeholder="Answer 2" name="ans2" required><br>
                                                            <input type="text" class="form-control" placeholder="Answer 3" name="ans3" required><br>
                                                            <input type="text" class="form-control" placeholder="Answer 4" name="ans4" required><br>

                                                            <label for="correctans"><b>Correct Answer</b></label><br>
                                                            <input type="text" class="form-control" placeholder="Insert Same Answer." name="correctans" required>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                            <button type="submit" class="btn btn-primary">Add</button>
                                                        </div>

                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div>
                                    <c:forEach items="${newquestions}" var="nq" varStatus="count">
                                        ${count.count}. ${nq.question}<br>
                                    </c:forEach>
                                </div>

                            </div>

                        </div>
                    </div>
                    <hr>

                </div>

            </div>
            <footer class="footer text-center ">
                <p>© Mobicquiz 2019</p>
            </footer>
        </div>
    </body>
</html>
