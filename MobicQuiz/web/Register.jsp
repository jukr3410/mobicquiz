<%-- 
    Document   : Register
    Created on : Nov 14, 2019, 2:44:16 AM
    Author     : Jn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

        <title>JSP Page</title>
    </head>
    <body>

        <div class="container-fluid">
            <br><br>
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-2">
                        </div>
                        <div class="col-md-8">
                            <a href="Login"><button class="btn btn-success float-right">
                                    Sign in
                                </button></a>
                            <div class="header clearfix">
                                
                                <a href="Login"><h3 class="text-muted">MOBIC QUIZ</h3></a>
                                
                            </div>
                            <div class="jumbotron">
                                <h1 class="text-muted text-center">Sign up</h1>
                                <form role="form" action="Register" method="post">
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="usertype" value="student" checked/> Student
                                            <input type="radio" name="usertype" value="teacher"/> Teacher
                                        </label>
                                    </div> 

                                    <div class="form-group">
                                        Your Name(firstname lastname) <input type="text" name="name" class="form-control" placeholder="Name" required/>   
                                        Your ID<input type="text" name="id" class="form-control" placeholder="ID" required/>                                                                                                   
                                        Password<input type="password" name="password" class="form-control" placeholder="Password" required/>
                                        Confirm Password<input type="password" name="confirmpassword" class="form-control" placeholder="Confirm Password" required/>
                                        Email<input type="email" name="email" class="form-control" placeholder="Email" required/><br>
                                        Grade
                                        <div class="radio">
                                        <label>
                                            <input type="radio" name="grade" value="1"/> M.1
                                            <input type="radio" name="grade" value="2"/> M.2
                                            <input type="radio" name="grade" value="3"/> M.3
                                            <input type="radio" name="grade" value="4"/> M.4
                                            <input type="radio" name="grade" value="5"/> M.5
                                            <input type="radio" name="grade" value="6"/> M.6
                                        </label>
                                    </div> 
                            </div>
                            

                                <button type="submit" class="btn btn-primary">
                                    Sign up
                                </button>
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
            </div>
        </div>
    </body>
</html>
