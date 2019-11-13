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
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="page-header">
                        <h1>
                            MOBIC QUIZ!
                        </h1>
                    </div>
                    <form role="form">
                        <div class="form-group">

                            <label for="exampleInputEmail1">
                                Email address
                            </label>
                            <input type="email" class="form-control" id="exampleInputEmail1" />
                        </div>
                        <div class="form-group">

                            <label for="exampleInputPassword1">
                                Password
                            </label>
                            <input type="password" class="form-control" id="exampleInputPassword1" />
                        </div>
                        
                        <div class="radio">

                            <label>
                                <input type="radio" name="usertype" value="student" checked/> Student
                                <input type="radio" name="usertype" value="teacher"/> Teacher
                            </label>
                        </div> 
                        <button type="submit" class="btn btn-primary">
                            Submit
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
