-- Chạy file này trong SSMS / Azure Data Studio / extension "SQL Server (mssql)" của VS Code
IF DB_ID(N'AccountDB') IS NULL
    CREATE DATABASE AccountDB;
GO

USE AccountDB;
GO

IF OBJECT_ID(N'dbo.roles', N'U') IS NULL
CREATE TABLE dbo.roles (
    id   INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(30) NOT NULL UNIQUE          -- ADMIN, USER, ...
);
GO

IF OBJECT_ID(N'dbo.users', N'U') IS NULL
CREATE TABLE dbo.users (
    id         BIGINT IDENTITY(1,1) PRIMARY KEY,
    username   NVARCHAR(50)  NOT NULL UNIQUE,
    password   NVARCHAR(100) NOT NULL,         -- BCrypt hash (60 ký tự)
    full_name  NVARCHAR(100) NOT NULL,
    email      NVARCHAR(100) NOT NULL UNIQUE,
    phone      NVARCHAR(20)  NULL,
    enabled    BIT           NOT NULL DEFAULT 1,
    role_id    INT           NOT NULL,
    created_at DATETIME2     NOT NULL DEFAULT SYSDATETIME(),
    CONSTRAINT FK_users_roles FOREIGN KEY (role_id) REFERENCES dbo.roles(id)
);
GO

IF NOT EXISTS (SELECT 1 FROM dbo.roles WHERE name = N'ADMIN') INSERT INTO dbo.roles(name) VALUES (N'ADMIN');
IF NOT EXISTS (SELECT 1 FROM dbo.roles WHERE name = N'USER')  INSERT INTO dbo.roles(name) VALUES (N'USER');
GO
