<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="bean" required="true" rtexprvalue="true"   type="java.lang.Object" %>



<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language.text" />
<li><label style="font-size:x-large ; color: red;"><fmt:message key="add.uptitle"/> </label>
    <label><fmt:message key="add.title"/> </label>
    <input  value="${bean.name}"  type="text" name="name" class="field-divided" />&nbsp; ${error['bookName']}

    <label><fmt:message key="main.page"/>: </label>
    <input value="${bean.pageCount}" type="text" name="pageCount" class="field-divided" />&nbsp; ${error['pageCount']}

    <label><fmt:message key="main.date"/>: </label>
    <input value="${bean.publishDate}" type="text" name="publishDate" class="field-divided" /> ${error['publishYear']}
</li>
<li>
    <label>ISBN </label>
    <input value="${bean.isbn}" type="text" name="isbn" class="field-long" /> ${error['isbn']}
</li>