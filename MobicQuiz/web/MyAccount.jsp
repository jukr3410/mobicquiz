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


                            <div class="jumbotron ">


                                <!--    !! write content in this    -->

                                <h1 class="text-muted text-center">My Account</h1>

                                <br>
                                <div class="col-md-12"><center>
                                        <c:choose>
                                            <c:when test="${usertype == 'student'}">
                                                <img src="images/S${sessionScope.user.studentno}.jpg" 
                                                     onerror="this.src='https://maxcdn.icons8.com/Share/icon/Users//user_male_circle_filled1600.png'" 
                                                     width="170" height="170" class="rounded-circle"/>
                                            </c:when>
                                            <c:when test="${usertype == 'teacher'}">
                                                <img src="images/T${sessionScope.user.teacherno}.jpg" 
                                                     onerror="this.src='https://maxcdn.icons8.com/Share/icon/Users//user_male_circle_filled1600.png'" 
                                                     width="170" height="170" class="rounded-circle"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img src="https://maxcdn.icons8.com/Share/icon/Users//user_male_circle_filled1600.png" width="170" height="170" class="rounded-circle" />
                                            </c:otherwise>
                                        </c:choose>
                                    </center>
                                </div>

                                <div class="form-group">
                                    <c:choose>
                                        <c:when test="${usertype=='student'}">
                                            ID<input type="text" name="name" class="form-control" value="${sessionScope.user.studentno}" readonly />   
                                        </c:when>
                                        <c:otherwise>
                                            ID<input type="text" name="name" class="form-control" value="${sessionScope.user.teacherno}" readonly />   
                                        </c:otherwise>
                                    </c:choose>

                                    Name<input type="text" name="id" class="form-control" value="${sessionScope.user.name}" readonly />                                
                                    Email<input type="email" name="email" class="form-control" value="${sessionScope.user.email}" readonly /><br>

                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalLong1">
                                        Change Image
                                    </button>
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalLong">
                                        Change Password
                                    </button>

                                </div>
                                <div class="modal fade" id="exampleModalLong1" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLongTitle">Change Your Image</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form role="form" action="MyAccount" method="post" enctype='multipart/form-data'>
                                                    <div class="input-group">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
                                                        </div>
                                                        <div class="custom-file">
                                                            <input type="file" class="input-group-text" id="inputGroupFile01" 
                                                                   accept="image/*" aria-describedby="inputGroupFileAddon01"
                                                                   name="upload" onchange="validateFileType()" />
                                                        </div>
                                                    </div><br>
                                                    <script type="text/javascript">
                                                        function validateFileType() {
                                                            var fileName = document.getElementById("inputGroupFile01").value;
                                                            var idxDot = fileName.lastIndexOf(".") + 1;
                                                            var extFile = fileName.substr(idxDot, fileName.length).toLowerCase();
                                                            if (extFile == "jpg" || extFile == "jpeg" || extFile == "png") {
                                                                //TO DO
                                                            } else {
                                                                alert("Only jpg/jpeg and png files are allowed!");
                                                            }
                                                        }
                                                    </script>                                        
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                        <button type="submit" class="btn btn-primary">Save</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLongTitle">Change Your Password</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>

                                            <div class="modal-body">

                                                <form role="form" action="MyAccount" method="post">
                                                    <div class="form-group">

                                                        Current Password<input type="password" class="form-control" name="password" 
                                                                               id="password" minlength="4" required /><br>
                                                        New Password<input type="password" class="form-control" name="newpassword" 
                                                                           id="newpassword" minlength="4" required /><br>
                                                        Confirm New Password<input type="password" class="form-control" name="confirmnewpassword" 
                                                                                   id="confirm_newpassword" minlength="4" required />
                                                        <span id='message' /><br />
                                                    </div>                                              
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                        <button type="submit" class="btn btn-primary">Edit</button>
                                                    </div>

                                                    <script>
                                                        var session_pw = ${sessionScope.user.password};
                                                        
                                                        var newpassword = document.getElementById("newpassword")
                                                                , confirm_newpassword = document.getElementById("confirm_newpassword")
                                                                , password = document.getElementById("password");
                                                        function validatePassword() {
                                                            if (newpassword.value !== confirm_newpassword.value) {
                                                                document.getElementById('message').style.color = 'red';
                                                                document.getElementById('message').innerHTML = 'NOT Matching';
                                                                confirm_newpassword.setCustomValidity("Passwords Don't Match");
                                                            } else {
                                                                document.getElementById('message').style.color = 'green';
                                                                document.getElementById('message').innerHTML = 'Matching';
                                                                confirm_newpassword.setCustomValidity('');
                                                            }
                                                            if (password.value === newpassword.value) {
                                                                newpassword.setCustomValidity('Change New Password');
                                                            } else {
                                                                newpassword.setCustomValidity('');
                                                            }
                                                        }
                                                        function checkPassword(){
                                                            var n = password.value.toString().localeCompare(session_pw.toString());
                                                            if(n === 0){
                                                                password.setCustomValidity('');
                                                            }else {
                                                                password.setCustomValidity("Current Password Incorrect.");
                                                            }
                                                        }
                                                        password.onkeyup = checkPassword;
                                                        newpassword.onkeyup = validatePassword;
                                                        confirm_newpassword.onkeyup = validatePassword;
                                                    </script>

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
        <hr>
        <footer class="footer text-center ">
            <p>© Mobicquiz 2019</p>
        </footer>
    </body>
</html>
