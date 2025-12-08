--1/ Create database,give permission to user
CREATE DATABASE product_management_db;
CREATE USER product_manager_user WITH PASSWORD '123456';
GRANT CONNECT ON DATABASE product_management_db TO product_manager_user;
GRANT CREATE ON SCHEMA public TO product_manager_user;
GRANT SELECT,INSERT,UPDATE,DELETE ON ALL TABLES IN SCHEMA public TO product_manager_user;