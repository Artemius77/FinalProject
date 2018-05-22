<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 04.05.2018
  Time: 23:49
  To change this template use File | Settings | File Templates.
--%>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language.text" />
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/resources/css/style_main.css" rel="stylesheet" type="text/css">
    <title><fmt:message key="info.uptitl"/> </title>
</head>
<body>

<c:choose>
    <c:when test="${(sessionScope.user.role eq 'ADMIN')}">
        <h2><fmt:message key="info.admin"/></h2>
    </c:when>
    <c:when test="${sessionScope.user.role eq 'READER'}">
       <h2><fmt:message key="info.title"/> </h2>
    </c:when>
</c:choose>

<div class="book_list2">
    <%--<c:set scope="session" var="sessionBooks" value="${requestScope.books}"/>--%>

    <%-- <c:set var="index" value="0" scope="page"/>--%>
    <h5 style="text-align: left; margin-top:20px;"><fmt:message key="main.search"/> ${requestScope.size} </h5>
    <c:forEach var="book" items="${requestScope.books}" >

        <div class="book_info">
            <div class="book_title">
                <p> ${book.name}</p>
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
                <br><strong style="color: red"><fmt:message key="info.date"/>: </strong> ${book.expireDate}
                <p>
                    <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                        <form method="post" action="manage">
                            <input type="text" value="${book.id}" name="releaseId" hidden>
                            <button  style="color: red" type="submit" onclick="return confirm('are you sure?');">
                                <fmt:message key="main.delete"/>
                            </button>
                        </form>
                    </c:if>

                    <a href="pdf?id=${book.id}" class="${(sessionScope.user.role eq 'ADMIN') ? 'read2' : 'read'}"
                       target="_blank"> <fmt:message key="info.read"/></a>

                </p>

            <c:if test="${sessionScope.user.role eq 'ADMIN'}">
                <div class="take_info">
                 <fmt:message key="info.user"/>:<span style="font-weight:bold"> ${book.user.id} </span>
                  EMAIL:  ${book.user.email}
                    <fmt:message key="info.phone"/>:${book.user.phonenumber}
                </div>
            </c:if>

            </div>
        </div>


        <%--<c:set var="index" value="${index + 1}" scope="page"/>--%>
    </c:forEach>

</div>

<myTags:Pagination action="info"/>
<myTags:home/>

</body>
</html>
