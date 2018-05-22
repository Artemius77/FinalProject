<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 25.04.2018
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language.text" />
<html>
<head>
    <title>Error page</title>
</head>
<body>
<c:url var="url" value="/pages/index.jsp"/>
<h2><fmt:message key="error.title"/></h2>

<fmt:message key="error.click"/> <a href="${url}"><fmt:message key="error.again"/></a></p>
</body>
</html>
