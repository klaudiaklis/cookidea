DROP DATABASE cookidea;

CREATE DATABASE cookidea;
USE cookidea;

CREATE TABLE liquidProductCategory(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	name VARCHAR(20)
);

CREATE TABLE countableProductCategory(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	name VARCHAR(20)
);

CREATE TABLE uncountableProductCategory(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	name VARCHAR(20)
);

CREATE TABLE uncountableProduct (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	name VARCHAR(100), 
	uncountableProductCategoryId INTEGER, 
	FOREIGN KEY(uncountableProductCategoryId) REFERENCES uncountableProductCategory(id)
); 

CREATE TABLE liquidProduct(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	name VARCHAR(100), 
	liquidProductCategoryId INTEGER, 
	FOREIGN KEY(liquidProductCategoryId) REFERENCES liquidProductCategory(id)
); 

CREATE TABLE countableProduct(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	name VARCHAR(100), 
	countableProductCategoryId INTEGER, 
	FOREIGN KEY(countableProductCategoryId) REFERENCES countableProductCategory(id)
);

CREATE TABLE household(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	login VARCHAR(100), 
	password VARCHAR(100)
);

CREATE TABLE mealType (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	name VARCHAR(50)
);

CREATE TABLE cousineType (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	name VARCHAR(50)
);

CREATE TABLE recipe (
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	name VARCHAR(500), 
	description TEXT, 
	duration INTEGER, 
	mealTypeId INTEGER NOT NULL, 
	cousineTypeId INTEGER NOT NULL,  
	FOREIGN KEY(mealTypeId) REFERENCES mealType(id), 
	FOREIGN KEY(cousineTypeId) REFERENCES cousineType(id)
);

CREATE TABLE liquidProductHousehold (
	liquidProductId INTEGER NOT NULL,
	householdId INTEGER NOT NULL,
	volume DECIMAL, 
	PRIMARY KEY(liquidProductId,householdId),
	FOREIGN KEY(liquidProductId) REFERENCES liquidProduct(id),
	FOREIGN KEY(householdId) REFERENCES household(id)
);

CREATE TABLE countableProductHousehold(
	countableProductId INTEGER NOT NULL,
	householdId INTEGER NOT NULL,
	amount INTEGER, 
	PRIMARY KEY(countableProductId,householdId),
	FOREIGN KEY(countableProductId) REFERENCES countableProduct(id),
	FOREIGN KEY(householdId) REFERENCES household(id)
);

CREATE TABLE uncountableProductHousehold(
	uncountableProductId INTEGER NOT NULL,
	householdId INTEGER NOT NULL,
	weight DECIMAL, 
	PRIMARY KEY(uncountableProductId,householdId),
	FOREIGN KEY(uncountableProductId) REFERENCES uncountableProduct(id),
	FOREIGN KEY(householdId) REFERENCES household(id)
);

INSERT INTO liquidProductCategory VALUES(1,'sosy');
INSERT INTO household VALUES(1,'domKlaudii','haslo');
INSERT INTO liquidProduct VALUES(1,'sojowy',1);
INSERT INTO liquidProductHousehold VALUES(1,1,0.3);



