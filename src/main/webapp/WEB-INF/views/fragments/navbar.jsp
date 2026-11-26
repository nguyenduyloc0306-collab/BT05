<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar">
    <a class="brand" href="<c:url value='/'/>">AccountApp</a>

    <sec:authorize access="!isAuthenticated()">
        <a href="<c:url value='/login'/>">Đăng nhập</a>
        <a href="<c:url value='/register'/>">Đăng ký</a>
    </sec:authorize>

    <sec:authorize access="isAuthenticated()">
        <span>Xin chào, <strong><sec:authentication property="name"/></strong></span>
        <a href="<c:url value='/profile'/>">Hồ sơ</a>
    </sec:authorize>

    <sec:authorize access="hasRole('ADMIN')">
        <a href="<c:url value='/admin/users'/>">Quản lý người dùng</a>
    </sec:authorize>

    <sec:authorize access="isAuthenticated()">
        <form action="<c:url value='/logout'/>" method="post">
            <sec:csrfInput/>
            <button type="submit">Đăng xuất</button>
        </form>
    </sec:authorize>
</nav>
