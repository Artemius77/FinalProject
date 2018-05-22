<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>

<div class="sidebar1">
    <h4><fmt:message key="menu.genre"/></h4>
    <ul class="nav">
        <jsp:useBean id="genreList" class="com.library.servicelayer.serviceimpl.OutputServiceImpl"
                     type="com.library.servicelayer.serviceinterface.OutputService" scope="application"/>

        <c:set value="All" var="all" scope="page"/>
        <li><a href="main?id=0" class="${selected eq all ? 'selected' : ''}">Все книги</a></li>
        <c:forEach items="${genreList.allGenres()}" var="genre">
            <li><a href="main?id=${genre.id}&name=${genre.name}"
                   class="${selected eq genre.name ? 'selected' : ''}">${genre.name}</a></li>
        </c:forEach>

    </ul>
</div>

