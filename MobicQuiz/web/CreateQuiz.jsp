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
        <title>My Account | MOBIC Quiz</title>
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
                                <c:when test="${usertype=='student'}">
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
                                    <div>
                                        <form role="form" action="CreateQuiz" method="get">
                                            <div class="form-group">

                                                <label for="subject">
                                                    Subject
                                                </label>
                                                <select class="form-control" name="subject">
                                                    <option value="thai">Thai</option>
                                                    <option value="science">Science</option>
                                                    <option value="social">Social</option>
                                                </select>
                                            </div>
                                            <div class="form-group">

                                                <label for="name">
                                                    Title
                                                </label>
                                                <input type="text" class="form-control" name="title" value="${param.title}" />
                                            </div>
                                            <div class="form-group">

                                                <label for="time">
                                                    Time
                                                </label>
                                                <input type="number" class="form-control" name="time" min="0" value="${param.time}"/>

                                            </div>
                                            <div class="form-group">

                                                <label for="time">
                                                    Score
                                                </label>
                                                <input type="number" class="form-control" name="score" min="0" value="${param.score}"/>

                                            </div>


                                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalLong">
                                                Create Question
                                            </button>
                                            <br><br>
                                            <button type="submit" class="btn btn-primary">
                                                Submit
                                            </button>

                                        </form>
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
                                                    <form role="form">
                                                        <div class="form-group">

                                                            <label for="subject">
                                                                Question Name
                                                            </label>
                                                            <input type="text" class="form-control" id="subject" />
                                                        </div>

                                                        <div class="form-group">

                                                            <label for="time">
                                                                Choice
                                                            </label>
                                                            <input type="text" class="form-control" placeholder="Answer 1" name="que1" required><br>
                                                            <input type="text" class="form-control" placeholder="Answer 2" name="que2" required><br>
                                                            <input type="text" class="form-control" placeholder="Answer 3" name="que3" required><br>
                                                            <input type="text" class="form-control" placeholder="Answer 4" name="que4" required><br>

                                                            <label for="email"><b>Correct Answer</b></label><br>
                                                            <input type="text" class="form-control" placeholder="Answer" name="ans" required>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                            <button type="submit" class="btn btn-primary">Add</button>
                                                        </div>
                                                    </form>
                                                </div>

                                            </div>
                                        </div>
                                    </div>

                                </div>

                                
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
    </body>
</html>
