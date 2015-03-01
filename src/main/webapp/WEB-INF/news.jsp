<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${locale}"/>

<fmt:bundle basename="i18n.messages">
    <html>
    <t:gHead>
        <title><fmt:message key="navigation.news"/></title>
        <link rel="stylesheet" href="<c:url value="/style/news.css"/>">
    </t:gHead>
    <t:gbody>
        <h1 class="news-header">Новости</h1>

        <div class="news-block panel panel-default">
            <div class="news-block-header text-center">
                Проект закончен !
            </div>
            <hr class="hr-header">
            <div class="news-block-content panel panel-default">
                Проект "Live water" "закончен" !
                <%--Стародубенко Родион--%>
                <%--Викторович--%>
                <%--защищает проект 5.01.2015г.--%>
            </div>
            <a class="news-article-link" href="#news-article">Статья</a>

            <div class="clear"></div>
        </div>
        <div class="news-block panel panel-default">
            <div class="news-block-header text-center">
                Проект закончен !
            </div>
            <hr class="hr-header">
            <div class="news-block-content panel panel-default">
                Проект "Live water" после долгого и крапотливого труда наконец-то "закончен".
                <%--Стародубенко Родион--%>
                <%--Викторович--%>
                <%--защищает проект 5.01.2015г.--%>
            </div>
            <a class="news-article-link" href="#news-article">Статья</a>

            <div class="clear"></div>
        </div>
        <div class="news-block panel panel-default">
            <div class="news-block-header text-center">
                Проект закончен !
            </div>
            <hr class="hr-header">
            <div class="news-block-content panel panel-default">
                Проект "Live water" после долгого и крапотливого труда наконец-то "закончен".
                <%--Стародубенко Родион  Викторович защищает проект 5.01.2015г.--%>
            </div>
            <a class="news-article-link" href="#news-article">Статья</a>

            <div class="clear"></div>
        </div>
    </t:gbody>
    </html>
</fmt:bundle>