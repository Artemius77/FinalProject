<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="name" required="true" rtexprvalue="true" %>
<%@ attribute name="id" required="true" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language.text" />
<div class="modal fade" id="${id}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">${name} add</h4>
            </div>
            <div class="modal-body" style="position: relative;top: 80px;right: 450px">
                <form action="<c:url value="/action/manage"/>" method="post" id="form${id}">
                    <div class="form-group">
                        <label for="input"><fmt:message key="modal.name"/> : </label>
                        <input  placeholder="<fmt:message key="tag.input"/> ${name}" class="form-control"
                                type="text" id="input" name="${name}"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="main.close"/> </button>
                <%-- <input type="submit" class="btn btn-primary" value="Save changes" form="form1">--%>
                <input type="submit" class="btn btn-primary"  form="form${id}" value="OK">
            </div>
        </div>
    </div>
</div>