<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tShoppingCart" tagdir="/WEB-INF/tags/shoppingCart" %>
<fmt:setLocale value="${locale}"/>

<fmt:bundle basename="i18n.messages">
    <html>
    <t:gHead>
        <title><fmt:message key="farm.cart"/></title>
        <link rel='stylesheet' href='<c:url value="/style/viewShoppingCart.css"/>'>
    </t:gHead>
    <t:gbody>
        <div class="client-info">
            <t:payment/>
        </div>

        <div class="goods-list panel panel-default">
            <div class="goods">
                <c:forEach var="entry" items="${shoppingCart.getGoods()}">
                    <tShoppingCart:good entry="${entry}"/>
                </c:forEach>
            </div>
                <div class="clear"></div>
            <div class="total-sum goods-font">
                <fmt:message key="message.total.cost"/>:<label class="total">${shoppingCart.getTotalSum()}</label>
            </div>
            <button class="button-continue-order">
                <fmt:message key="button.cont.order"/>
            </button>
        </div>

        <script src="<c:url value="/script/viewShoppingCart.js"/>"></script>
    </t:gbody>
    </html>
</fmt:bundle>