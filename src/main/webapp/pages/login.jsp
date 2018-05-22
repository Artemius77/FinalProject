<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 25.04.2018
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language.text" />
<html>
<head>
    <title>Login Page</title>
    <link href="${pageContext.request.contextPath}/resources/css/style_main.css" rel="stylesheet" type="text/css">
</head>
<body>

<h2 style="text-align: center"><fmt:message key="login.title"/> </h2>
<br><br>
<form action="j_security_check" method=post class="login">
    <p><strong><fmt:message key="login.user"/> </strong>
        <input type="text" name="j_username" size="25">
    <p><p><strong><fmt:message key="login.password"/>: </strong>
    <input type="password" size="15" name="j_password">
    <p><p>
    <input type="submit" value="OK">
    <input type="reset" value="<fmt:message key="login.reset"/>">
</form>

</body>
</html>
