# AccountApp – Spring + JDBC + SQL Server (JDK 17)

Chức năng: **đăng ký**, **đăng nhập**, **đăng xuất**, phân quyền **ADMIN / USER**, lưu thông tin người dùng trong SQL Server.

## Công nghệ
- Java 17, Maven, Spring Boot 3.3.5 (Spring MVC, Spring Security 6)
- Spring JDBC (`JdbcTemplate`) – không dùng JPA/Hibernate
- SQL Server (driver `mssql-jdbc`), JSP + JSTL (Tomcat nhúng, `tomcat-embed-jasper`)

## Yêu cầu
1. JDK 17 và Maven 3.9+ (hoặc dùng Maven trong extension *Extension Pack for Java*).
2. SQL Server đã bật TCP/IP, cổng 1433 (SQL Server Configuration Manager → Protocols → TCP/IP → Enabled).
   Hoặc chạy bằng Docker:
   ```
   docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=YourStrong@Passw0rd" -p 1433:1433 -d mcr.microsoft.com/mssql/server:2022-latest
   ```
3. VS Code + extension: Extension Pack for Java, Spring Boot Extension Pack, SQL Server (mssql).

## Cách chạy
1. Mở thư mục `account-app` trong VS Code.
2. Chạy `database/schema.sql` (extension mssql: *Connect* → *Execute Query*) để tạo database `AccountDB`, bảng `roles`, `users`.
3. Sửa `src/main/resources/application.properties` (username/password SQL Server nếu khác).
4. Chạy: `mvn spring-boot:run` (hoặc F5 / Spring Boot Dashboard). Với JSP, hãy chạy từ thư mục gốc project để Tomcat nhận `src/main/webapp`.
5. Mở http://localhost:8080

Tài khoản admin mặc định (tạo lúc khởi động nếu chưa có): `admin` / `Admin@123` – hãy đổi trong `application.properties`.

## Cấu trúc
```
database/schema.sql                      # tạo DB + bảng + role mặc định
src/main/java/com/example/accountapp/
  config/SecurityConfig.java             # phân quyền URL, form login, logout, BCrypt
  config/DataInitializer.java            # tạo role + admin mặc định
  controller/                            # Home, Auth (đăng ký), Profile, Admin
  dto/RegisterForm.java                  # form + validation
  model/User.java
  repository/UserRepository.java         # JdbcTemplate
  repository/RoleRepository.java
  service/UserService.java               # logic đăng ký
  service/CustomUserDetailsService.java  # nạp user từ DB cho Spring Security
src/main/webapp/WEB-INF/views/           # JSP: index, login, register, profile, admin/users, error/403, fragments/navbar
```

## Luồng xử lý
- **Đăng ký**: `POST /register` → validate (`RegisterForm`) → kiểm tra trùng username/email → mã hóa BCrypt → `INSERT` với role `USER`.
- **Đăng nhập**: `POST /login` do Spring Security xử lý → `CustomUserDetailsService.loadUserByUsername` → so khớp BCrypt → thành công chuyển tới `/profile`.
- **Đăng xuất**: `POST /logout` (có CSRF token, form trong navbar) → hủy session, xóa cookie `JSESSIONID` → chuyển tới `/login?logout`.
- **Phân quyền**: `/admin/**` yêu cầu `ROLE_ADMIN`; các trang còn lại (trừ `/`, `/login`, `/register`, `/css/**`, `/error`) yêu cầu đăng nhập. User thường vào `/admin/users` sẽ được chuyển tới `/403` (trang `error/403.jsp`, HTTP 403).

## Cấu hình JSP (tóm tắt)
- `pom.xml`: `<packaging>war</packaging>`, thêm `tomcat-embed-jasper`, JSTL (`jakarta.servlet.jsp.jstl-api` + `org.glassfish.web:jakarta.servlet.jsp.jstl`), `spring-security-taglibs`.
- `application.properties`: `spring.mvc.view.prefix=/WEB-INF/views/`, `spring.mvc.view.suffix=.jsp`.
- Controller trả về tên view (ví dụ `"admin/users"`) → `/WEB-INF/views/admin/users.jsp`.
- Taglib Jakarta: `uri="jakarta.tags.core"` (không còn `http://java.sun.com/jsp/jstl/core` như Java EE cũ).
- Chạy: `mvn spring-boot:run`, F5 trong VS Code, hoặc `java -jar target/account-app-0.0.1-SNAPSHOT.war`.
  Lưu ý: Spring Boot **không hỗ trợ JSP khi đóng gói `jar`** – phải dùng `war`.
