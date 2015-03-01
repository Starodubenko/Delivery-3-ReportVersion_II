<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="goods" type="java.util.List" %>
<fmt:setLocale value="${locale}"/>

<fmt:bundle basename="i18n.messages">
    <c:forEach var="entity" items="${goods}">
        <form id="entity-line${entity.getId()}">
            <tr>
                <td>
                    <label class="field">${entity.getId()}</label>
                </td>
                <td>
                    <div class="image-block">
                        <img class="image panel panel-default" src="/image/${entity.getImage().getId()}/${entity.getImage().getFilename()}">
                    </div>
                    <label class="field" for="image${entity.getId()}">${entity.getImage().getFilename()}</label>
                    <input id="image${entity.getId()}" name="image" type="text" class="form-control edit-field" style="display: none;" value="${entity.getImage().getFilename()}"/>
                </td>
                <td>
                    <label class="field" for="goods_name${entity.getId()}">${entity.getGoodsName()}</label>
                    <input id="goods_name${entity.getId()}" name="goods_name" type="text" class="form-control edit-field" style="display: none;" value="${entity.getGoodsName()}"/>
                </td>
                <td>
                    <label class="field" for="price${entity.getId()}">${entity.getPrice()}</label>
                    <input id="price${entity.getId()}" name="price" type="text" class="form-control edit-field" style="display: none;" value="${entity.getPrice()}"/>
                </td>
                <td>
                    <label class="field" for="deleted${entity.getId()}">
                        <c:choose>
                            <c:when test="${entity.isDeleted() eq true}"><fmt:message key="message.yes"/></c:when>
                            <c:otherwise><fmt:message key="message.no"/></c:otherwise>
                        </c:choose>
                    </label>
                    <input id="deleted${entity.getId()}" name="deleted" type="checkbox" class="field" style="display: none;" value="${entity.isDeleted()}"/>
                </td>
                <td>
                    <button type="button" class="btn btn-primary delete" name="id" value="${entity.getId()}">
                        <fmt:message key="button.delete"/>
                    </button>
                </td>
                <td>
                    <a href="<c:url value="/do/editGoods?goodsId=${entity.getId()}"/>"><fmt:message key="client.edit.goods"/></a>
                    <input type="checkbox" class="checkbox edit" id="${entity.getId()}"/>
                    <label class="checkbox-label" for="${entity.getId()}"></label>
                </td>
            </tr>
        </form>
    </c:forEach>
    <form id="saveForm">
        <tr>
            <td></td>
            <td><input name="goods_name" type="text" class="form-control edit-field" style="display: none;"/></td>
            <td><input name="price" type="text" class="form-control edit-field" style="display: none;"/></td>
            <td><input name="image" type="text" class="form-control edit-field" style="display: none;"/></td>
            <td></td>
            <td>
                <button type="button" class="btn btn-primary" id="add"><fmt:message key="button.add"/>
                </button>
                <button type="button" class="btn btn-primary" id="save" style="display: none;">
                    <fmt:message key="button.save"/>
                </button>
            </td>
        </tr>
    </form>
</fmt:bundle>