<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 01.04.2018
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language.text" />
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="//cdn.jsdelivr.net/jquery/1/jquery.min.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-growl/1.0.0/jquery.bootstrap-growl.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/style_main.css" rel="stylesheet" type="text/css">
    <title><fmt:message key="main.title"/> </title>
</head>
<body>



<%@include file="../WEB-INF/jspf/left_menu.jsp"%>
<%@include file="../WEB-INF/jspf/header.jsp"%>
<%@include file="../WEB-INF/jspf/letters.jsp"%>


<c:choose>
    <c:when test="${(sessionScope.user.role eq 'ADMIN')}">
        <div class="add_book">
            <a href="<c:url value="/pages/add.jsp"/>"><fmt:message key="main.add"/> </a>
        </div>
        <div class="add_book">
            <a href="info"><fmt:message key="main.admin"/></a>
        </div>
    </c:when>
    <c:when test="${sessionScope.user.role eq 'USER'}">
        <div class="user">
            <fmt:message key="main.order"/> :
            <button   type="button"  data-toggle="modal" data-target="#userForm">
                <fmt:message key="index.register"/>
        </button>
        </div>
    </c:when>
    <c:when test="${sessionScope.user.role eq 'READER'}">
        <div class="add_book">
            <a href="info?user=${sessionScope.user.id}"><fmt:message key="main.reader"/> </a>
        </div>
    </c:when>
</c:choose>






<div class="book_list">

    <h5 style="text-align: left; margin-top:20px;"><fmt:message key="main.search"/> ${requestScope.size} </h5>
    <c:forEach var="book" items="${requestScope.books}" >

        <div class="book_info">
            <div class="book_title">
                <p>  ${book.name}</p>
            </div>
            <div class="book_image">
                <img src="data:image/jpeg;base64,${book.image}" height="250" width="190" alt="picture"/>
            </div>
            <div class="book_details">
                <br><strong>ISBN: </strong> ${book.isbn}
                <br><strong><fmt:message key="main.publish"/> : </strong> ${book.publisher.name}
                <br><strong><fmt:message key="main.genre"/>: </strong> ${book.genre.name}
                <br><strong><fmt:message key="main.page"/>: </strong> ${book.pageCount}
                <br><strong><fmt:message key="main.date"/>: </strong> ${book.publishDate}
                <br><strong><fmt:message key="main.author"/>: </strong> ${book.author.name}
                <p style="margin:10px;">
                    <form method="post" action="take" id="readerForm">
                         <input value="${book.id}" type="text" name="bookId" hidden>
                        <c:choose>
                            <c:when test="${sessionScope.user.role eq 'USER' && empty book.user}">
                                <button   type="button" class="unavailable">
                                    <fmt:message key="main.unavailable"/>
                                </button>
                            </c:when>
                            <c:when test="${(sessionScope.user.role eq 'READER' || sessionScope.user.role eq 'ADMIN')
                            &&  empty book.user}">
                                <button type="submit"  class="take">
                                    <fmt:message key="main.take"/>
                                </button>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="main.late" />
                            </c:otherwise>
                        </c:choose>

                     </form>

                    <c:if test="${(sessionScope.user.role eq 'ADMIN')}">
                        <a style="margin:10px;" href="add?id=${book.id}"><fmt:message key="main.update"/> </a>
                        <a style="margin:10px; color: red" href="add?deleteId=${book.id}"
                           onclick="return confirm('are you sure?');"><fmt:message key="main.delete"/> </a>
                    </c:if>

                </p>

            </div>

        </div>


    </c:forEach>

</div>


</div><!-- end .container -->


<myTags:Pagination action="main" />


<script>
    $(".take").click(function(){
        $.bootstrapGrowl('<fmt:message key="main.succes"/>',{
            type: 'success',
            delay: 2000
        });
    });

    $(".unavailable").click(function(){
        $.bootstrapGrowl('<fmt:message key="main.danger"/>' ,{
            type: 'danger',
            delay: 4000,
            placement: {
                align: 'center'
            }
        });
    });
</script>


<div class="modal fade" id="userForm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel"><fmt:message key="main.contact"/> : </h4>
            </div>
            <div class="modal-body">
                <form action="take" method="post" id="confirm">
                    <div class="form-group">
                        <label for="inputEmail"><fmt:message key="main.email"/> : </label>
                        <input   placeholder="<fmt:message key="main.fillemail"/>" class="form-control"
                                 type="text" id="inputEmail" name="email"/>
                        <label for="inputPhone"><fmt:message key="main.phone"/> : </label>
                        <input  placeholder="<fmt:message key="main.fillphone"/>" class="form-control"
                                type="text" id="inputPhone" name="phone"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="take1">
                    <fmt:message key="main.close"/>
                </button>
                <input type="submit" class="btn btn-primary" form="confirm"  value="OK">
            </div>
        </div>
    </div>
</div>

</body>
</html>
