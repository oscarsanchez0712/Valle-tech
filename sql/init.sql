-- ============================================
-- ValleTech - Script de Base de Datos
-- Ejecutar en MySQL Workbench o Docker
-- ============================================

CREATE DATABASE IF NOT EXISTS valletech_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE valletech_db;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    nombre_completo VARCHAR(100),
    activo TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de categorías
CREATE TABLE IF NOT EXISTS categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE
);

-- Tabla de productos
CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(80) NOT NULL,
    precio DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    stock INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Datos de prueba: usuarios
INSERT INTO usuarios (usuario, contrasena, nombre_completo) VALUES
('admin', 'admin123', 'Administrador ValleTech'),
('oscar', 'oscar123', 'Oscar Valle');

-- Datos de prueba: categorías
INSERT INTO categorias (nombre) VALUES
('Electrónica'),
('Ropa'),
('Alimentos'),
('Hogar'),
('Tecnología');

-- Datos de prueba: productos
INSERT INTO productos (nombre, categoria, precio, stock) VALUES
('Laptop HP 15"', 'Tecnología', 2500.00, 10),
('Mouse Inalámbrico', 'Tecnología', 45.00, 50),
('Teclado Mecánico', 'Tecnología', 150.00, 30),
('Monitor 24"', 'Tecnología', 800.00, 15),
('Auriculares Bluetooth', 'Electrónica', 200.00, 25),
('Polo Deportivo', 'Ropa', 35.00, 100),
('Arroz Premium 1kg', 'Alimentos', 5.50, 200),
('Silla Ergonómica', 'Hogar', 450.00, 8);
