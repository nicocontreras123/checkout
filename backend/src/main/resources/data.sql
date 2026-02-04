-- Productos chilenos (precios en CLP)
INSERT INTO products (sku, name, price, category, image_url) VALUES
('CL-001', 'Leche Colun Entera 1L', 1290, 'Lácteos', 'https://i5.walmartimages.cl/asr/fe1f2b98-4a4d-4b66-8d1a-254379263fe7.339d76f2e92a97ec30887a003dba3a41.jpeg?null=&odnHeight=612&odnWidth=612&odnBg=FFFFFF'),
('CL-002', 'Pan Marraqueta 1kg', 1490, 'Panadería', 'https://i5.walmartimages.cl/asr/c09e0e1f-e092-493a-b07c-a22255a12c6b.3fdecc24976f2836cc1cf5c7589a6fd0.jpeg?null=&odnHeight=612&odnWidth=612&odnBg=FFFFFF'),
('CL-003', 'Huevos Grande Blanco 12 Un', 3990, 'Lácteos', 'https://i5.walmartimages.cl/asr/06c472bb-628f-4cf6-bcc4-2ebca69286c6.bef7902e5494893d2baabc116fee3d73.jpeg?odnHeight=612&odnWidth=612&odnBg=FFFFFF'),
('CL-004', 'Palta Hass 1kg', 4990, 'Frutas y Verduras', 'https://i5.walmartimages.cl/asr/c1231a68-55a7-4c9e-9f6f-35163325a1d0.a02a9ad32ba8f2ea5afbffbeafce3d6d.jpeg?odnHeight=612&odnWidth=612&odnBg=FFFFFF'),
('CL-005', 'Coca-Cola Original 1.5L', 1890, 'Bebidas', 'https://i5.walmartimages.cl/asr/c1034351-f944-4889-a288-58aa4aff1ad8.d82719da9bcba65a74f28b5cac718ba7.jpeg?odnHeight=612&odnWidth=612&odnBg=FFFFFF'),
('CL-006', 'Papas Fritas Lays Clásicas 250g', 2490, 'Snacks', 'https://i5.walmartimages.cl/asr/d3546919-22c1-4a1c-bf1d-834355e824b2.660b8eb1f17ef8a5463f9e095eceed2c.jpeg?null=&odnHeight=612&odnWidth=612&odnBg=FFFFFF'),
('CL-007', 'Arroz Tucapel Largo 1kg', 1590, 'Abarrotes', 'https://i5.walmartimages.cl/asr/25b97caf-cf9f-4453-90e5-3023945d7b7a.04502baff883ca19e8906f6e77c3b066.jpeg?odnHeight=612&odnWidth=612&odnBg=FFFFFF'),
('CL-008', 'Aceite Maravilla 1L', 2890, 'Abarrotes', 'https://i5.walmartimages.cl/asr/b75c5479-ba96-4666-a2dc-21f2aa8d79f6.2ec597014bf1c4c7905366f35537c481.jpeg?odnHeight=612&odnWidth=612&odnBg=FFFFFF'),
('CL-009', 'Café Nescafé Tradición 170g', 5990, 'Abarrotes', 'https://i5.walmartimages.cl/asr/d6c8b44f-12ab-4e15-a42b-b5f522a6bc46.da6881276255ccb63df93243baf9da90.jpeg?null=&odnHeight=612&odnWidth=612&odnBg=FFFFFF'),
('CL-010', 'Vino Casillero del Diablo Cabernet 750ml', 5490, 'Bebidas', 'https://i5.walmartimages.cl/asr/dec82ccd-efee-4416-a29f-81ef9c63e565.8fd855560ff786917c509d08070724bc.jpeg?null=&odnHeight=612&odnWidth=612&odnBg=FFFFFF');

-- Métodos de pago chilenos con descuentos
INSERT INTO payment_methods (code, name, discount_percentage, description, active) VALUES
('DEBITO', 'Tarjeta de Débito', 10.00, '10% de descuento en compras', true),
('CREDITO', 'Tarjeta de Crédito', 0.00, 'Pago estándar sin descuento adicional', true),
('WALMART_LIDER', 'Tarjeta Lider MasterCard', 5.00, '5% de descuento en todas las compras', true),
('EFECTIVO', 'Efectivo', 0.00, 'Pago en caja', true),
('TRANSFERENCIA', 'Transferencia Bancaria', 3.00, '3% de descuento por transferencia', true);

-- Promociones
INSERT INTO promotions (code, name, type, discount_value, min_purchase_amount, applicable_category, applicable_product_id, start_date, end_date, active) VALUES
('LACTEOS15', '15% Dcto en Lácteos', 'PERCENTAGE', 15.00, NULL, 'Lácteos', NULL, '2024-01-01', '2025-12-31', true),
('SNACKS10', '10% Dcto en Snacks', 'PERCENTAGE', 10.00, NULL, 'Snacks', NULL, '2024-01-01', '2025-12-31', true),
('AHORRA5000', '$5.000 Dcto en compras sobre $30.000', 'FIXED_AMOUNT', 5000.00, 30000.00, NULL, NULL, '2024-01-01', '2025-12-31', true),
('BEBIDAS20', '20% Dcto en Bebidas', 'PERCENTAGE', 20.00, NULL, 'Bebidas', NULL, '2024-01-01', '2025-12-31', true);
