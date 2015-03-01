<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tImage" tagdir="/WEB-INF/tags/image" %>
<%@ attribute name="paginatedList" type="com.epam.star.dao.util.PaginatedList" %>

<fmt:bundle basename="i18n.messages">
    <table class="table table-hover" ID="payCardTable">
        <input type="hidden" id="pageNumber" value="${paginatedList.getPageNumber()}"/>
        <thead>
        <th><fmt:message key="clients.message.ID"/></th>
        <th><fmt:message key="message.filename"/></th>
        <th><fmt:message key="image.message.image"/></th>
        <th><fmt:message key="message.deleted"/></th>
        <th></th>
        <th><fmt:message key="message.edit"/></th>
        </thead>
        <tImage:imageRow image="${paginatedList}"/>
    </table>
</fmt:bundle>