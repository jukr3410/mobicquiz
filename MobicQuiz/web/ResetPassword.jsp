<%-- 
    Document   : Activate
    Created on : Nov 14, 2019, 9:09:18 PM
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

        <title>Password Reset | MOBIC Quiz</title>
    </head>
    <body>
        <div class="container-fluid">
            <br><br>
            <div class="row">
                <div class="col-md-2">
                </div>
                <div class="col-md-8">
                    <a href="Login"><button class="btn btn-success float-right">
                            Sign in
                        </button></a>
                    <div class="header clearfix">
                        <h3 class="">MOBIC QUIZ</h3>
                    </div>
                    <div class="jumbotron">
                        <h1 class="text-muted text-center">Set New Password</h1>

                        <div style="font-weight: bold; color: green">
                            ${statusreset}
                        </div>
                        <c:if test="${param.idforreset==null}">
                            <div style="font-weight: bold; color: red">
                                Please click on the link to set a new password from your email.
                            </div>
                        </c:if>
                        You Account
                        <br>
                        <form role="form" action="ResetPassword" method="post">

                            <div class="form-group text-center">   

                                <input type="text" name="idforreset" id="idforreset" class="form-control" required readonly value="${param.idforreset}"/><br>
                                <input type="text" name="newpassword" id="password" class="form-control" placeholder="New Password" style="width: " required/><br>
                                <input type="text" name="confirmnewpassword" id="confirm_password" class="form-control" placeholder="Confirm New Password" style="width: " required/>

                                <br>
                                <button type="submit" class="btn btn-primary">
                                    Confirm
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="col-md-2">
                </div>
            </div>
            <hr>
            <footer class="footer text-center">
                <p>© Mobicquiz 2019</p>
            </footer>
        </div>

        <script>
            var password = document.getElementById("password")
                    , confirm_password = document.getElementById("confirm_password");
            function validatePassword() {
                if (password.value !== confirm_password.value) {
                    confirm_password.setCustomValidity("Passwords Don't Match");
                } else {
                    confirm_password.setCustomValidity('');
                }
            }
            password.onchange = validatePassword;
            confirm_password.onkeyup = validatePassword;
        </script>
    </body>
</html>
