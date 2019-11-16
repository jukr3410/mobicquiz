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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
        <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.0.7/css/swiper.min.css'>
        <link rel="stylesheet" href="./style.css">

        <title>Home</title>
    </head>
    <body>
        <br>
        <div class="container">
            <div class="row">
                <div>
                    <div class="header clearfix">
                        <nav>
                            <ul class="nav nav-pills float-right">

                                <li class="nav-item">
                                    <a class="nav-link" href="Logout"><button class="btn btn-danger float-right">
                                            Finished
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


                        <div class="jumbotron text-center" style="height: 600px; width: 1000px">
                            
                            <div class="swiper-container">
                                
                                <div class="swiper-wrapper">
                                    
                                    <div class="swiper-slide">Slide 1</div>
                                    <div class="swiper-slide">Slide 2</div>
                                    <div class="swiper-slide">Slide 3</div>
                                </div>
                                
                                <div class="swiper-pagination"></div>

                                
                                <div class="swiper-button-prev"></div>
                                <div class="swiper-button-next"></div>
                            </div>
                            
                            <script src='https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.0.7/js/swiper.min.js'></script><script  src="./script.js"></script>

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
