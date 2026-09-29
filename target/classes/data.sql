INSERT INTO bases (id, name, location) VALUES (1, 'North Base', 'North Region') ON DUPLICATE KEY UPDATE name = VALUES(name), location = VALUES(location);
INSERT INTO bases (id, name, location) VALUES (2, 'South Base', 'South Region') ON DUPLICATE KEY UPDATE name = VALUES(name), location = VALUES(location);
INSERT INTO bases (id, name, location) VALUES (3, 'East Base', 'East Region') ON DUPLICATE KEY UPDATE name = VALUES(name), location = VALUES(location);
INSERT INTO bases (id, name, location) VALUES (4, 'West Base', 'West Region') ON DUPLICATE KEY UPDATE name = VALUES(name), location = VALUES(location);

INSERT INTO equipment_types (id, name, category) VALUES (1, 'M4 Carbine', 'WEAPON') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (2, 'AK-47', 'WEAPON') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (3, 'AK-74', 'WEAPON') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (4, 'Assault Rifle', 'WEAPON') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (5, 'Sniper Rifle', 'WEAPON') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (6, 'Machine Gun', 'WEAPON') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (7, 'Handgun', 'WEAPON') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (8, 'Humvee', 'VEHICLE') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (9, 'Military Truck', 'VEHICLE') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (10, 'Armored Vehicle', 'VEHICLE') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (11, '5.56mm Ammo Box', 'AMMUNITION') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (12, '7.62mm Ammo Box', 'AMMUNITION') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (13, 'Radio Set', 'GEAR') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (14, 'Night Vision Device', 'GEAR') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (15, 'Protective Helmet', 'GEAR') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);
INSERT INTO equipment_types (id, name, category) VALUES (16, 'Body Armor', 'GEAR') ON DUPLICATE KEY UPDATE name = VALUES(name), category = VALUES(category);

-- Passwords are 'password' encoded with BCrypt
INSERT INTO users (id, name, email, password_hash, role, base_id) 
VALUES (1, 'Admin User', 'admin@mams.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HCGFGLwZi3O52eY2a758m', 'ADMIN', NULL)
ON DUPLICATE KEY UPDATE
name = VALUES(name), email = VALUES(email), password_hash = VALUES(password_hash), role = VALUES(role), base_id = VALUES(base_id);

INSERT INTO users (id, name, email, password_hash, role, base_id) 
VALUES (2, 'Cmdr Alpha', 'cmdr.alpha@mams.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HCGFGLwZi3O52eY2a758m', 'BASE_COMMANDER', 1)
ON DUPLICATE KEY UPDATE
name = VALUES(name), email = VALUES(email), password_hash = VALUES(password_hash), role = VALUES(role), base_id = VALUES(base_id);

INSERT INTO users (id, name, email, password_hash, role, base_id) 
VALUES (3, 'Logistics Beta', 'log.beta@mams.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HCGFGLwZi3O52eY2a758m', 'LOGISTICS_OFFICER', 2)
ON DUPLICATE KEY UPDATE
name = VALUES(name), email = VALUES(email), password_hash = VALUES(password_hash), role = VALUES(role), base_id = VALUES(base_id);

INSERT INTO personnel_units (id, name) VALUES (1, 'Captain Arjun Sharma — Punjab Regiment') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (2, 'Lieutenant Rahul Verma — Madras Regiment') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (3, 'Major Vikram Singh — The Grenadiers') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (4, 'Captain Aditya Rao — Maratha Light Infantry') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (5, 'Lieutenant Karan Mehta — Rajputana Rifles') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (6, 'Havildar Rajesh Kumar — Sikh Regiment') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (7, 'Naik Amit Yadav — Gorkha Rifles') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (8, 'Lieutenant Rohit Sharma — Ladakh Scouts') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (9, 'Punjab Regiment') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (10, 'Madras Regiment') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (11, 'The Grenadiers') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (12, 'Maratha Light Infantry') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (13, 'Rajputana Rifles') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (14, 'Sikh Regiment') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (15, 'Gorkha Rifles') ON DUPLICATE KEY UPDATE name = VALUES(name);
INSERT INTO personnel_units (id, name) VALUES (16, 'Ladakh Scouts') ON DUPLICATE KEY UPDATE name = VALUES(name);
