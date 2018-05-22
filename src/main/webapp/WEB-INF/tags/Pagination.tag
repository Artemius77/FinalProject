<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="action" required="true" rtexprvalue="true" %>


<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language.text" />
<div class="pagination">
    <c:forEach begin="1" end="${noOfPages}" var="i">
        <c:choose>
            <c:when test="${currentPage eq i}">
                <a  style="color:red"> ${i} </a>
            </c:when>
            <c:otherwise>
                <a class="page-link" href="${action}?currentPage=${i}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</div>

<form action="${action}">
    <label><fmt:message key="pagination.page"/> </label>
    <select name="booksPerPage"  title="Books per page">
        <option ${recordsPerPage == '3' ? 'selected' : ''} >3</option>
        <option ${recordsPerPage == '5' ? 'selected' : ''}>5</option>
        <option ${recordsPerPage == '7' ? 'selected' : ''}>7</option>
    </select>
    <input type="submit" value="OK!">
</form>