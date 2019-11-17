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
        <link rel="stylesheet" href="style.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>


        <title>Exam</title>
    </head>
    <body onload="countdown();">
        <br>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">

                    <div>
                        <form action="FinishExam" method="get" id="exam">
                            <input type="submit" class="btn btn-danger float-right" value="Finished">      

                            <div class="header clearfix">
                                <h3>MOBIC QUIZ</h3>
                            </div>
                            <br>

                            <div class="jumbotron" style="font-size: medium">
                                 
                                <div style="font-size: medium"> <h3>Time :                                       
                                   <input id="minutes" type="text" style="width: 40px; text-align: right"disabled><font size="5"> : 
                                        </font> 
                                        <input id="seconds" type="text" style="width: 40px;" disabled> minutes</h3>
                                    </div> 
                                   
                                <div class="swiper-container">
                                    <div class="swiper-wrapper">
                                        <c:forEach items="${questions}" var="que" varStatus="theCount">
                                            <div class="swiper-slide">
                                                <div class="" style="width: 100%; text-align: left;padding: 35px"><br>
                                                    ${theCount.count}. ${que.question}
                                                    <br><hr>
                                                    <div >
                                                        <label><input type="checkbox" class="radio" value="${que.ans1}" name="${que.questionno}" /> 1. ${que.ans1}</label><br>
                                                        <label><input type="checkbox" class="radio" value="${que.ans2}" name="${que.questionno}" /> 2. ${que.ans2}</label><br>
                                                        <label><input type="checkbox" class="radio" value="${que.ans3}" name="${que.questionno}" /> 3. ${que.ans3}</label><br>
                                                        <label><input type="checkbox" class="radio" value="${que.ans4}" name="${que.questionno}" /> 4. ${que.ans4}</label><br>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>                                                      
                                    <div class="">                                                                  
                                        <div class="swiper-pagination"></div>                                                                                                
                                    </div>
                                </div>
                            </div>

                        </form>
                    </div>

                </div>
            </div>
            <hr>
            <footer class="footer text-center ">
                <p>© Mobicquiz 2019</p>
            </footer>
        </div>

        <script src='https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.0.7/js/swiper.min.js'></script>
        <!--                            <script  src="./script.js"></script>-->

        <script>

            var menu = new Array();
            for (let i = 0; i < 10; i++)
            {
                var j = i + 1;
                menu.push(j.toString());
            }
            var mySwiper = new Swiper('.swiper-container', {
                // If we need pagination
                pagination: {
                    el: '.swiper-pagination',
                    clickable: true,
                    renderBullet: function (index, className) {
                        return '<span class="' + className + '">' + (menu[index]) + '</span>';
                    }
                },
            });

            // the selector will match all input controls of type :checkbox
            // and attach a click event handler 
            $("input:checkbox").on('click', function () {
                // in the handler, 'this' refers to the box clicked on
                var $box = $(this);
                if ($box.is(":checked")) {
                    // the name of the box is retrieved using the .attr() method
                    // as it is assumed and expected to be immutable
                    var group = "input:checkbox[name='" + $box.attr("name") + "']";
                    // the checked state of the group/box on the other hand will change
                    // and the current value is retrieved using .prop() method
                    $(group).prop("checked", false);
                    $box.prop("checked", true);
                } else {
                    $box.prop("checked", false);
                }
            });


        var mins = parseInt(${sessionScope.quiz.time}); 
        var secs = mins * 60; 
  
        //countdown function is evoked when page is loaded 
        function countdown() { 
            setTimeout('Decrement()', 60); 
        } 
  
        //Decrement function decrement the value. 
        function Decrement() { 
            if (document.getElementById) { 
                minutes = document.getElementById("minutes"); 
                seconds = document.getElementById("seconds"); 
  
                //if less than a minute remaining 
                //Display only seconds value. 
                if (seconds < 59) { 
                    seconds.value = secs; 
                } 
  
                //Display both minutes and seconds 
                //getminutes and getseconds is used to 
                //get minutes and seconds 
                else { 
                    minutes.value = getminutes(); 
                    seconds.value = getseconds(); 
                } 
                //when less than a minute remaining 
                //colour of the minutes and seconds 
                //changes to red 
                if (mins < 1) { 
                    minutes.style.color = "red"; 
                    seconds.style.color = "red"; 
                } 
                //if seconds becomes zero, 
                //then page alert time up 
                if (mins < 0) { 
                    alert('time up'); 
                    document.getElementById("exam").submit();
                    minutes.value = 0; 
                    seconds.value = 0; 
                } 
                //if seconds > 0 then seconds is decremented 
                else { 
                    secs--; 
                    setTimeout('Decrement()', 1000); 
                } 
            } 
        } 
  
        function getminutes() { 
            //minutes is seconds divided by 60, rounded down 
            mins = Math.floor(secs / 60); 
            return mins; 
        } 
  
        function getseconds() { 
            //take minutes remaining (as seconds) away  
            //from total seconds remaining 
            return secs - Math.round(mins * 60); 
        } 
    </script> 
        
    </body>
</html>
