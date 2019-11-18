<%-- 
    Document   : Homepage
    Created on : Nov 14, 2019, 1:13:43 AM
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
        <title>Create</title>

        <style>
            body {font-family: Arial, Helvetica, sans-serif;}
            * {box-sizing: border-box;}

            /* Button used to open the contact form - fixed at the bottom of the page */
            .open-button {
                background-color: #555;
                color: white;
                padding: 16px 20px;
                border: none;
                cursor: pointer;
                opacity: 0.8;
                position: fixed;
                bottom: 23px;
                right: 28px;
                width: 280px;
            }

            /* The popup form - hidden by default */
            .form-popup {
                display: none;
                position: fixed;
                bottom: 0;
                right: 15px;
                border: 3px solid #f1f1f1;
                z-index: 9;
            }

            /* Add styles to the form container */
            .form-container {
                max-width: 600px;
                max-height: 800px;
                padding: 10;
                background-color: white;
            }

            /* Full-width input fields */
            .form-container input[type=text], .form-container input[type=password] {
                width: 100%;
                padding: 15px;
                margin: 5px 0 22px 0;
                border: none;
                background: #f1f1f1;
            }

            /* When the inputs get focus, do something */
            .form-container input[type=text]:focus, .form-container input[type=password]:focus {
                background-color: #ddd;
                outline: none;
            }

            /* Set a style for the submit/login button */
            .form-container .btn {
                background-color: #4CAF50;
                color: white;
                padding: 16px 20px;
                border: none;
                cursor: pointer;
                width: 100%;
                margin-bottom:10px;
                opacity: 0.8;
            }

            /* Add a red background color to the cancel button */
            .form-container .cancel {
                background-color: red;
            }

            /* Add some hover effects to buttons */
            .form-container .btn:hover, .open-button:hover {
                opacity: 1;
            }
        </style>

    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <form role="form">
                        <div class="form-group">

                            <label for="subject">
                                Subject
                            </label>
                            <select class="form-control">
                                <option value="thai">Thai</option>
                                <option value="science">Science</option>
                                <option value="social">Social</option>
                            </select>
                        </div>
                        <div class="form-group">

                            <label for="name">
                                Name
                            </label>
                            <input type="text" class="form-control" name="name" value="${param.name}" />
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

                <!--                <div class="col-md-5">
                                    <button class="open-button" onclick="openForm()">Open Form</button>
                
                                    <div class="form-popup" id="myForm">
                                        <form action="/action_page.php" class="form-container">
                                            <h1>Login</h1>
                
                                            <label for="email"><b>Question Name</b></label>
                                            <input type="text" placeholder="Enter Email" name="email" required>
                
                                            <label for="psw"><b>Choice</b></label>
                                            <input type="text" placeholder="Answer 1" name="que" required>
                                            <input type="text" placeholder="Answer 2" name="que" required>
                                            <input type="text" placeholder="Answer 3" name="que" required>
                                            <input type="text" placeholder="Answer 4" name="que" required>
                
                                            <label for="email"><b>Correct Answer</b></label>
                                            <input type="text" placeholder="Answer" name="ans" required>
                
                                            <button type="submit" class="btn">Add</button>
                                            <button type="button" class="btn cancel" onclick="closeForm()">Close</button>
                                        </form>
                                    </div>
                
                                    <script>
                                        function openForm() {
                                            document.getElementById("myForm").style.display = "block";
                                        }
                
                                        function closeForm() {
                                            document.getElementById("myForm").style.display = "none";
                                        }
                                    </script>
                                </div>-->
            </div>
        </div>
</html>
