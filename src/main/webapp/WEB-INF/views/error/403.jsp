<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>403 - Không có quyền truy cập</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<main class="card">
    <h1>403 - Không có quyền truy cập</h1>
    <p>Tài khoản của bạn không đủ quyền để xem trang này.</p>
    <p><a href="<c:url value='/'/>">Về trang chủ</a></p>
</main>
</body>
</html>
