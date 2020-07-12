INSERT INTO user_role(id, name) VALUES(0, 'test role');
INSERT INTO user_profile(id, username, password, first_name, last_name, user_role_id, email)
VALUES(0, 'test username', 'test password', 'test first name', 'test last name', 0, 'test email');
INSERT INTO customer(id, phone, street, zip_code, city) VALUES (0, '123', 'test street', '123', 'test city');
INSERT INTO product_category(id, name) VALUES(0, 'test category');
INSERT INTO product_brand(id, name) VALUES(0, 'test brand');
INSERT INTO product(id, name, price, quantity, short_description, weight, product_brand_id, product_category_id, version)
VALUES(0, 'test product', 1, 1, 'description', 1, 0, 0, 1);
INSERT INTO product_details(product_id, description) VALUES(0, 'long description');
INSERT INTO customer_order(id, creation_date, value, status, shipping_street, shipping_zip_code, shipping_city,
billing_street, billing_zip_code, billing_city, customer_id, total_weight)
VALUES(0, '2020-06-20', 100.99, 'NEW', 'test', 'test', 'test', 'test', 'test', 'test', 0, 5.00);
INSERT INTO order_item(order_id, product_id, price, weight, quantity) VALUES(0, 0, 100.99, 5.00, 1);