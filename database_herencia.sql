-- =========================================
-- SCRIPT SQL PARA HERENCIA Y POLIMORFISMO
-- =========================================

USE shop;

-- 1. Agregar columna discriminadora (tipo de producto)
ALTER TABLE productos 
ADD COLUMN IF NOT EXISTS tipo_producto VARCHAR(50) DEFAULT 'REMERA';

-- 2. Agregar campos específicos para REMERA
ALTER TABLE productos 
ADD COLUMN IF NOT EXISTS material VARCHAR(100);

-- 3. Agregar campos específicos para ZAPATILLA
ALTER TABLE productos 
ADD COLUMN IF NOT EXISTS numero_calzado INT,
ADD COLUMN IF NOT EXISTS tipo_deporte VARCHAR(100);

-- 4. Agregar campos específicos para PELOTA
ALTER TABLE productos 
ADD COLUMN IF NOT EXISTS deporte VARCHAR(100),
ADD COLUMN IF NOT EXISTS tamanio VARCHAR(50);

-- 5. Actualizar productos existentes (asumimos que son remeras)
UPDATE productos 
SET tipo_producto = 'REMERA' 
WHERE tipo_producto IS NULL OR tipo_producto = '';

-- 6. Ver la estructura final
DESCRIBE productos;

-- 7. Insertar datos de ejemplo para cada tipo

-- Remeras
INSERT INTO productos (nombre, precio, tipo_producto, marca, talle, material)
VALUES 
('Remera Nike Dri-Fit', 8500.00, 'REMERA', 'Nike', 42, 'Poliéster'),
('Remera Adidas Clásica', 6500.00, 'REMERA', 'Adidas', 40, 'Algodón');

-- Zapatillas
INSERT INTO productos (nombre, precio, tipo_producto, marca, numero_calzado, tipo_deporte)
VALUES 
('Zapatillas Nike Air Max', 25000.00, 'ZAPATILLA', 'Nike', 42, 'Running'),
('Botines Adidas Predator', 35000.00, 'ZAPATILLA', 'Adidas', 41, 'Fútbol');

-- Pelotas
INSERT INTO productos (nombre, precio, tipo_producto, deporte, tamanio)
VALUES 
('Pelota Wilson NBA', 12000.00, 'PELOTA', 'Básquet', '7'),
('Pelota Adidas Tango', 15000.00, 'PELOTA', 'Fútbol', '5');

-- 8. Consultar todos los productos con sus precios finales (simulando polimorfismo)
SELECT 
    id,
    tipo_producto AS tipo,
    nombre,
    precio AS precio_original,
    CASE 
        WHEN tipo_producto = 'REMERA' THEN precio * 0.9
        WHEN tipo_producto = 'ZAPATILLA' THEN precio * 0.85
        ELSE precio
    END AS precio_final,
    CASE 
        WHEN tipo_producto = 'REMERA' THEN CONCAT(marca, ' - Talle ', talle, IFNULL(CONCAT(' - ', material), ''))
        WHEN tipo_producto = 'ZAPATILLA' THEN CONCAT(marca, ' - Nº ', numero_calzado, IFNULL(CONCAT(' - ', tipo_deporte), ''))
        WHEN tipo_producto = 'PELOTA' THEN CONCAT(deporte, ' - Tamaño ', tamanio)
    END AS detalle
FROM productos;

-- 9. Ver solo remeras
SELECT * FROM productos WHERE tipo_producto = 'REMERA';

-- 10. Ver solo zapatillas
SELECT * FROM productos WHERE tipo_producto = 'ZAPATILLA';

-- 11. Ver solo pelotas
SELECT * FROM productos WHERE tipo_producto = 'PELOTA';
