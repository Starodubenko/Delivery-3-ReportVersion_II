<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${locale}"/>

<fmt:bundle basename="i18n.messages">
    <html>
    <t:gHead>
        <title><fmt:message key="navigation.aboutus"/></title>
        <link rel="stylesheet" href="<c:url value="/style/about.css"/>">
    </t:gHead>
    <t:gbody>
        <h1 class="about-header">"Доставка"</h1>

        <div class="about-content">
            Проект "Доставка" создан на примере фирм, которые занимаются продажей и доставкой бутилированной воды, а
            также устройств,
            способствующих её охлаждению, нагреву и распределению. Проект позволяет удобно и быстро создавать
            заказы, а также контролировать историю,
            созданных заказов в личном кабинете. Со стороны обслуживающего персонала проект предоставляет удобные
            инструменты
            для продвижения всех заказов дальше на склад...
        </div>
    </t:gbody>
    </html>
</fmt:bundle>
