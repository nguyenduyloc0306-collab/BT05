<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Trang chủ</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<main class="card">
    <h1>Chào mừng đến với AccountApp</h1>
    <sec:authorize access="!isAuthenticated()">
        <p>Hãy <a href="<c:url value='/login'/>">đăng nhập</a> hoặc <a href="<c:url value='/register'/>">đăng ký</a> để tiếp tục.</p>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <p>Bạn đang đăng nhập. Xem <a href="<c:url value='/profile'/>">hồ sơ cá nhân</a>.</p>
    </sec:authorize>
</main>
</body>
</html>
