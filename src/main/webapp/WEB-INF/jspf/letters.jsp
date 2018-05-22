<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="letters">

    <c:forEach var="letter" items="${applicationScope.alphabet}">
        <a href="main?letter=${letter}" class="${selectedLetter eq letter ? 'selected' : ''}"> ${letter} </a>
    </c:forEach>

</div>
