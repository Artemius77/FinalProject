
<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 08.04.2018
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  %>
<div class="container">

    <div class="header">
        <div class="logo">
            <img src="/resources/images/library.png" alt="image" name="logo" />
        </div>
        <div class="descr">
            <h3><fmt:message key="index.title"/></h3>
        </div>
        <div class="welcome">
            <h5><fmt:message key="header.title"/>, ${sessionScope.user.id} !</h5>
            <h5>${userError} ${success}</h5>
            <a href="index?logout=notNull"><fmt:message key="header.exit"/></a>
        </div>
        <div class="search_form">
            <form name="search_form" method="GET" action="main">
                <input type="text" name="search_String"  value="${search}" size="110" />
                <input class="search_button" type="submit" value="OK" />
                <select name="search_option">
                    <option value="Author" ${option == 'author' ? 'selected' : ''}><fmt:message key="main.author"/></option>
                    <option value="Book Name" ${option == 'title' ? 'selected' : ''}><fmt:message key="add.title"/></option>
                </select>
            </form>
        </div>
    </div>
