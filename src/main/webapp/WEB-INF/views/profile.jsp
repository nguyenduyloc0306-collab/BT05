<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Hồ sơ</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<main class="card">
    <h1>Thông tin tài khoản</h1>
    <table>
        <tr><th>Tên đăng nhập</th><td><c:out value="${user.username}"/></td></tr>
        <tr><th>Họ và tên</th><td><c:out value="${user.fullName}"/></td></tr>
        <tr><th>Email</th><td><c:out value="${user.email}"/></td></tr>
        <tr><th>Số điện thoại</th><td><c:out value="${empty user.phone ? '—' : user.phone}"/></td></tr>
        <tr><th>Vai trò</th>
            <td><span class="badge ${user.roleName == 'ADMIN' ? 'admin' : ''}"><c:out value="${user.roleName}"/></span></td></tr>
        <tr><th>Ngày tạo</th><td><c:out value="${user.createdAtDisplay}"/></td></tr>
    </table>
</main>
</body>
</html>
