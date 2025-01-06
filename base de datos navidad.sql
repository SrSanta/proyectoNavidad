CREATE DATABASE IF NOT EXISTS tienda CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE tienda;

CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    password VARCHAR(128) NOT NULL,
    rol VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT
);

CREATE TABLE IF NOT EXISTS productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    descripcion TEXT,
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id_categoria) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    fecha_pedido VARCHAR(100),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS pedido_productos (
    id_pedido INT,
    id_producto INT,
    cantidad INT DEFAULT 1,
    PRIMARY KEY (id_pedido, id_producto),
    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto) ON DELETE CASCADE
);

INSERT INTO categorias (id_categoria, nombre, descripcion) VALUES
(1 , 'Dragon Ball', 'Figuras, coleccionables y productos relacionados con Dragon Ball.'),
(2 , 'Naruto', 'Figuras y coleccionables de la serie Naruto y Naruto Shippuden.'),usuarios
(3 , 'One Piece', 'Figuras y artículos relacionados con One Piece y sus personajes.'),
(4 , 'My Hero Academia', 'Productos coleccionables de la serie My Hero Academia.'),
(5 , 'Attack on Titan', 'Figuras y mercancía de Attack on Titan.'),
(6 , 'Sailor Moon', 'Figuras y artículos coleccionables de la serie Sailor Moon.'),
(7 , 'Pokémon', 'Productos y figuras coleccionables del universo Pokémon.'),
(8 , 'Demon Slayer', 'Figuras y productos basados en Demon Slayer.'),
(9 , 'Fullmetal Alchemist', 'Artículos y figuras coleccionables de Fullmetal Alchemist.'),
(10 , 'Death Note', 'Productos relacionados con el anime Death Note.');

INSERT INTO productos (id_producto, nombre, precio, descripcion, categoria_id) VALUES
(1, 'Figura Goku Super Saiyan', 100.00, 'Figura detallada de Goku en su forma Super Saiyan.', 1),
(2, 'Figura Vegeta Super Saiyan', 95.00, 'Figura de alta calidad de Vegeta en su forma Super Saiyan.', 1),
(3, 'Figura Goku Black', 120.00, 'Figura de Goku Black, el villano de Dragon Ball Super.', 1),
(4, 'Figura Freezer', 80.00, 'Figura del temible Freezer, uno de los villanos más conocidos de Dragon Ball.', 1),
(5, 'Figura Naruto Uzumaki', 85.00, 'Figura de Naruto Uzumaki en su traje de Hokage.', 2),
(6, 'Figura Sasuke Uchiha', 90.00, 'Figura de Sasuke Uchiha con su Sharingan activado.', 2),
(7, 'Figura Luffy', 95.00, 'Figura de Monkey D. Luffy con su gorro de paja.', 3),
(8, 'Figura Zoro', 80.00, 'Figura de Roronoa Zoro con sus espadas.', 3),
(9, 'Figura Deku', 100.00, 'Figura de Izuku Midoriya (Deku) en su traje de héroe.', 4),
(10, 'Figura Eren Yeager', 90.00, 'Figura de Eren Yeager de Attack on Titan, con su equipo de maniobras tridimensionales.', 5),
(11, 'Figura Sailor Moon', 75.00, 'Figura de la heroína Sailor Moon con su traje clásico.', 6),
(12, 'Figura Pikachu', 40.00, 'Figura de Pikachu, el Pokémon más icónico.', 7);

INSERT INTO usuarios (id_usuario, nombre, password, rol) 
VALUES (1, 'Admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Admin');
