CREATE DATABASE cookidea;

CREATE TABLE uncountableProduct (name VARCHAR(100), weight DECIMAL);
CREATE TABLE liquidProduct( name VARCHAR(100), volume DECIMAL);
CREATE TABLE countableProduct( name VARCHAR(100), amount INTEGER);

INSERT INTO countableProduct VALUES ('apple',2);
