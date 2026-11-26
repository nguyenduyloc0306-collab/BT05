<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<main class="card">
    <h1>Đăng nhập</h1>

    <c:if test="${registered}">
        <p class="alert success">Đăng ký thành công! Hãy đăng nhập.</p>
    </c:if>
    <c:if test="${not empty param.logout}">
        <p class="alert success">Bạn đã đăng xuất.</p>
    </c:if>
    <c:if test="${not empty param.error}">
        <p class="alert error">Sai tên đăng nhập hoặc mật khẩu, hoặc tài khoản đã bị khóa.</p>
    </c:if>

    <form action="<c:url value='/login'/>" method="post">
        <sec:csrfInput/>
        <label>Tên đăng nhập
            <input type="text" name="username" required autofocus>
        </label>
        <label>Mật khẩu
            <input type="password" name="password" required>
        </label>
        <button type="submit">Đăng nhập</button>
    </form>
    <p>Chưa có tài khoản? <a href="<c:url value='/register'/>">Đăng ký</a></p>
</main>
</body>
</html>
