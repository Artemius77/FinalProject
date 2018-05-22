<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8"  %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="language.text" />

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><fmt:message key="index.enter"/> </title>
    <link href="${pageContext.request.contextPath}/resources/css/style_index.css" rel="stylesheet" type="text/css">
</head>

<body>

<div class="main">

    <div class="content">
        <p class="title"><span class="text"><img src="${pageContext.request.contextPath}/resources/images/lib.png"
                                                 width="76" height="77" hspace="10" vspace="10" align="middle"></span>
        </p>
        <p class="title"><fmt:message key="index.title"/> </p>
        <p class="text"><fmt:message key="index.welcome"/> </p>
        <p>&nbsp;</p>
    </div>

    <form action="/action/index">
        <label for="language"><fmt:message key="index.lang"/> </label>
        <select id="language" name="language" onchange="submit()">
            <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
            <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        </select>
    </form>

    <div class="login_div">
        <p style="color: red">${error}</p>
        <p style="color: green">${success}</p>
         <fmt:message key="index.sign"/>
        <form class="login_form" name="username" action="<c:url value="/action/main"/>" method="POST">
            <input  id="Button" style="background-color:#2979FF" type="submit" value=<fmt:message key="index.login"/>    />
        </form>
        <p class="title">
            <a href="<c:url value="/pages/loginPage.html"/>" >
                <fmt:message key="index.signup"/>
            </a>
        </p>
    </div>

    <div class="footer">
        <fmt:message key="index.footer" />
    </div>

</div>


</body>
</html>
