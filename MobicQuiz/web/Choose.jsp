<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <br>
    <br>
    <div>
        <c:choose>
            <c:when test="${myaquiz!=null}">
                <select>
                    <option value="thsi">Thai</option>
                    <option value="science">Science</option>
                    <option value="social">Social</option>
                </select>
                <table class="table table-hover text-center">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Subject</th>
                            <th>Title</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${quiz}" var="q">
                        <tr>
                            <td></td>
                            <td>Math</td>
                            <td>Calculus</td>
                            <td>
                                <div>                           
                                    <button type="button" class="btn btn-info btn-lg align-middle" data-toggle="modal" data-target="#myModal">View</button>
                                </div>
                            </td>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="text-center">
                    <h1 style="color: tomato">On Quiz.</h1>
                </div>
            </c:otherwise>
        </c:choose>

        <!-- Modal -->
        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>

                    </div>
                    <div class="modal-body">
                        <p><center>Subject</center></p>

                        <p><center>Name</center></p>

                        <p><center>Time</center></p>

                        <p><center>Score</center></p>
                    </div>
                    <div class="modal-footer">
                        <a href="Homepage.jsp"><button type="button" class="btn btn-success">Start</button></a>
                    </div>
                </div>

            </div>
        </div>
    </div>


</div>
