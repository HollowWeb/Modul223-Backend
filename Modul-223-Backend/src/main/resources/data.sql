INSERT INTO roles (role_name) SELECT 'ADMIN' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'ADMIN');
INSERT INTO roles (role_name) SELECT 'EDITOR' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'EDITOR');
INSERT INTO roles (role_name) SELECT 'VIEWER' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'VIEWER');
INSERT INTO roles (role_name) SELECT 'CONTRIBUTOR' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'CONTRIBUTOR');
INSERT INTO roles (role_name) SELECT 'USER' WHERE NOT EXISTS (SELECT 1 FROM roles WHERE role_name = 'USER');

