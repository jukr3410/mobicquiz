<%-- 
    Document   : MyAccount
    Created on : Nov 14, 2019, 11:42:52 PM
    Author     : Wine.N
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

        <title>My Account | MOBIC Quiz</title>
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
                            <a href="Homepage.jsp"><button class="btn btn-primary float-right">
                                    Back
                                </button></a>
                            <div class="header clearfix">
                                <h3 class="">MOBIC QUIZ</h3>
                            </div>
                            <div class="jumbotron">
                                <h1 class="text-muted text-center">My Account</h1>

                                <br>
                                <div class="col-md-12"><center>
                                        <img src="https://maxcdn.icons8.com/Share/icon/Users//user_male_circle_filled1600.png" width="170" height="170" class="rounded-circle" />
                                    </center></div>

                                <div class="form-group">

                                    ID<input type="text" name="name" class="form-control" value="${sessionScope.user.studentno}" readonly />   
                                    Name<input type="text" name="id" class="form-control" value="${sessionScope.user.name}" readonly />                                
                                    Email<input type="email" name="email" class="form-control" value="${sessionScope.user.email}" readonly /><br>

                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalLong">
                                        Edit
                                    </button>

                                </div>
                                <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLongTitle">Edit Your Account</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form role="form" action="MyAccount.jsp" method="post">                                                    
                                                    <div class="form-group">
                                                        
                                                        Name<input type="text" class="form-control" value="${sessionScope.user.name}" name="name" required><br><hr>
                                                        <div class="text-center" style="font-weight:bold ">
                                                            Change Password
                                                        </div>
                                                        Current Password<input type="password" class="form-control" name="password"><br>
                                                        New Password<input type="password" class="form-control" name="newpassword"><br>
                                                        Confirm New Password<input type="password" class="form-control" name="confirmnewpassword"><br>
                                                    </div>                                              
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                        <button type="submit" class="btn btn-primary">Edit</button>
                                                    </div>
                                                </form>

                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2">
                            </div>
                        </div>
                        <hr>

                    </div>
                    <hr>
                    <footer class="footer text-center">
                        <p>© Mobicquiz 2019</p>
                    </footer>
                </div>

            </div>
    </body>
</html>
