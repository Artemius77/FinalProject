<%@ taglib prefix="myTags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 16.04.2018
  Time: 20:48
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
    <link href="/resources/css/add.css" rel="stylesheet" type="text/css">
    <title><fmt:message key="add.uptitle"/> </title>
</head>
<body>

<jsp:useBean id="generalDAO" class="com.library.servicelayer.serviceimpl.OutputServiceImpl"/>
<jsp:useBean id="book" scope="request" class="com.library.model.entities.Book" />
<jsp:useBean id="formBean" scope="request" class="com.library.controller.utils.classes.FormBean" />

<myTags:home/>

<form action="<c:url value="/action/add"/>" method="post" enctype="multipart/form-data">


    <input value="${book.id}" type="text" name="hiddenID" hidden>
    <ul class="form-style-1">
        <c:choose>
            <c:when test="${not empty book.id}">
                <myTags:FormTag bean="${book}"/>
            </c:when>
            <c:otherwise>
                <myTags:FormTag bean="${formBean}"/>
            </c:otherwise>
        </c:choose>
        <li>
            <label><fmt:message key="main.author"/>: </label>  ${error['author']}
            <select name="author" class="field-select">
                <option value="${book.author.id}" >${book.author.name}</option>
                <c:forEach items="${generalDAO.allAuthors()}" var="author">
                     <option value="${author.id}" >${author.name}</option>
                </c:forEach>
            </select>
            <c:if test="${empty book.id}">
                <button   type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#1">
                    <fmt:message key="add.author"/>
                </button>
            </c:if>
        </li>
        <li>
            <label><fmt:message key="main.genre"/></label> ${error['genre']}
            <select name="genre" class="field-select">
                <option value="${book.genre.id}" >${book.genre.name}</option>
                <c:forEach items="${generalDAO.allGenres()}" var="genre">
                    <option value="${genre.id}">${genre.name}</option>
                </c:forEach>
            </select>
            <c:if test="${empty book.id}">
                <button   type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#2">
                    <fmt:message key="add.genre"/>
                </button>
            </c:if>
        </li>
        <li>
            <label><fmt:message key="main.publish"/></label> ${error['publisher']}
            <select name="publisher" class="field-select">
                <option value="${book.publisher.id}">${book.publisher.name}</option>
                <c:forEach items="${generalDAO.allPublishers()}" var="publisher">
                    <option value="${publisher.id}">${publisher.name}</option>
                </c:forEach>
            </select>
            <c:if test="${empty book.id}">
                <button   type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#3">
                    <fmt:message key="add.publisher"/>
                </button>
            </c:if>
        </li>
        <li>
            <label><fmt:message key="add.content"/></label>
            <input type="file"  name="content"  class="field-divided" />&nbsp; ${contentError}
            <input type="file" name="image"  class="field-divided"/> ${imageError}
           <%-- <img src="data:image/jpeg;base64,${book.image}" height="170" width="120" alt="picture"/>--%>
        </li>
        <li>
            <input type="submit" value="OK" />
        </li>
    </ul>
</form>

<myTags:ModalAdd id="1" name="Author" />
<myTags:ModalAdd id="2" name="Genre" />
<myTags:ModalAdd id="3" name="Publisher" />

</body>
</html>
