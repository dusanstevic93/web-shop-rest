INSERT INTO product_category(id, name, parent_id) VALUES((SELECT nextval('product_category_id_sequence')), 'subcategory 1', 0);
INSERT INTO product_category(id, name, parent_id) VALUES((SELECT nextval('product_category_id_sequence')), 'subcategory 2', 0);