<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đăng ký</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<main class="card">
    <h1>Đăng ký tài khoản</h1>

    <%-- form:form tự thêm CSRF token (nhờ RequestDataValueProcessor của Spring Security) --%>
    <form:form modelAttribute="registerForm" action="${pageContext.request.contextPath}/register" method="post">
        <label>Tên đăng nhập
            <form:input path="username"/>
            <form:errors path="username" cssClass="field-error" element="span"/>
        </label>
        <label>Họ và tên
            <form:input path="fullName"/>
            <form:errors path="fullName" cssClass="field-error" element="span"/>
        </label>
        <label>Email
            <form:input path="email" type="email"/>
            <form:errors path="email" cssClass="field-error" element="span"/>
        </label>
        <label>Số điện thoại (không bắt buộc)
            <form:input path="phone"/>
            <form:errors path="phone" cssClass="field-error" element="span"/>
        </label>
        <label>Mật khẩu
            <form:password path="password"/>
            <form:errors path="password" cssClass="field-error" element="span"/>
        </label>
        <label>Nhập lại mật khẩu
            <form:password path="confirmPassword"/>
            <form:errors path="confirmPassword" cssClass="field-error" element="span"/>
        </label>
        <button type="submit">Đăng ký</button>
    </form:form>
    <p>Đã có tài khoản? <a href="<c:url value='/login'/>">Đăng nhập</a></p>
</main>
</body>
</html>
