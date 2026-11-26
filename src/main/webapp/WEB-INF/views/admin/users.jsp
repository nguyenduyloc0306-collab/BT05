<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý người dùng</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<main class="card wide">
    <h1>Danh sách người dùng</h1>
    <table>
        <thead>
        <tr><th>ID</th><th>Tên đăng nhập</th><th>Họ tên</th><th>Email</th><th>Vai trò</th><th>Trạng thái</th><th>Ngày tạo</th></tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${users}">
            <tr>
                <td><c:out value="${u.id}"/></td>
                <td><c:out value="${u.username}"/></td>
                <td><c:out value="${u.fullName}"/></td>
                <td><c:out value="${u.email}"/></td>
                <td><span class="badge ${u.roleName == 'ADMIN' ? 'admin' : ''}"><c:out value="${u.roleName}"/></span></td>
                <td>${u.enabled ? 'Hoạt động' : 'Bị khóa'}</td>
                <td><c:out value="${u.createdAtDisplay}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>
</body>
</html>
