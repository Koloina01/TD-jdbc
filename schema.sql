/*The answer for de question 2*/

CREATE TABLE Product (
    id int PRIMARY KEY,
    name VARCHAR NOT NULL,
    price NUMERIC NOT NULL,
    creation_datetime TIMESTAMP NOT NULL
);

CREATE TABLE Product_category(
    id int PRIMARY KEY,
    name VARCHAR NOT NULL,
    product_id int REFERENCES Product(id)
);
