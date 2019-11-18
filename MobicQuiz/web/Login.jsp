<%-- 
    Document   : Login
    Created on : Nov 13, 2019, 2:16:45 PM
    Author     : Jn
--%>

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
        
        <title>MobicLogin </title>
    </head>
    <body>

        <br>



        <div class="container">
            <div class="header clearfix">
                <h3 class="">MOBIC QUIZ</h3>
            </div>
            <div class="row">
                <div class="col-md-1">
                </div>

                <div class="col-md-5">
                </div>
                <div class="col-md-5">

                    <div class="jumbotron text-center">
                        <h1 class="display-3">Sign in</h1>
                        <div style="color: red">
                            ${errorlogin}
                        </div>
                        <br>
                        <form role="form" action="Login" method="post">
                            <div class="form-group">
                                <input type="text" name="id" class="form-control" placeholder="ID or Email" value="${param.id}" minlength="4" maxlength="30" required/>
                            </div>
                            <div class="form-group">                               
                                <input type="password" name="password" class="form-control" placeholder="Password" minlength="4" required/>
                            </div>

                            <div class="radio">
                                <label>
                                    <input type="radio" name="usertype" value="student" checked/> Student
                                    <input type="radio" name="usertype" value="teacher"/> Teacher
                                </label>
                            </div> 
                            <button type="submit" class="btn btn-primary form-control">
                                Sign in
                            </button>
                        </form><br>
                        <div class="text-center">
                            <a href="ForgotPassword.jsp">Forgot your password?</a>
                        </div>
                        <hr><br>
                        <a href="Register"><button class="btn btn-success">
                            Sign up
                        </button></a>
                    </div>
                </div>
                <div class="col-md-1">
                </div>
            </div>

            <hr>
            <footer class="footer text-center">
                <p>© Mobicquiz 2019</p>
            </footer>

        </div> 
    </body>
</html>
