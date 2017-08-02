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

CREATE TABLE binaryProductCategory(
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

CREATE TABLE binaryProduct(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	name VARCHAR(100), 
	binaryProductCategoryId INTEGER, 
	FOREIGN KEY(binaryProductCategoryId) REFERENCES binaryProductCategory(id)
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
	portions INTEGER NOT NULL,
	difficultyLevel INTEGER NOT NULL,
	FOREIGN KEY(mealTypeId) REFERENCES mealType(id), 
	FOREIGN KEY(cousineTypeId) REFERENCES cousineType(id)
);

CREATE TABLE liquidProductRecipe (
	liquidProductId INTEGER NOT NULL,
	recipeId INTEGER NOT NULL,
	volume DOUBLE, 
	PRIMARY KEY(liquidProductId,recipeId),
	FOREIGN KEY(liquidProductId) REFERENCES liquidProduct(id),
	FOREIGN KEY(recipeId) REFERENCES recipe(id)
);

CREATE TABLE countableProductRecipe (
	countableProductId INTEGER NOT NULL,
	recipeId INTEGER NOT NULL,
	volume DOUBLE, 
	PRIMARY KEY(countableProductId,recipeId),
	FOREIGN KEY(countableProductId) REFERENCES countableProduct(id),
	FOREIGN KEY(recipeId) REFERENCES recipe(id)
);

CREATE TABLE uncountableProductRecipe (
	uncountableProductId INTEGER NOT NULL,
	recipeId INTEGER NOT NULL,
	volume DOUBLE, 
	PRIMARY KEY(uncountableProductId,recipeId),
	FOREIGN KEY(uncountableProductId) REFERENCES uncountableProduct(id),
	FOREIGN KEY(recipeId) REFERENCES recipe(id)
);

CREATE TABLE binaryProductRecipe (
	binaryProductId INTEGER NOT NULL,
	recipeId INTEGER NOT NULL,
	PRIMARY KEY(binaryProductId,recipeId),
	FOREIGN KEY(binaryProductId) REFERENCES binaryProduct(id),
	FOREIGN KEY(recipeId) REFERENCES recipe(id)
);

CREATE TABLE liquidProductHousehold (
	liquidProductId INTEGER NOT NULL,
	householdId INTEGER NOT NULL,
	volume DOUBLE, 
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
	weight DOUBLE, 
	PRIMARY KEY(uncountableProductId,householdId),
	FOREIGN KEY(uncountableProductId) REFERENCES uncountableProduct(id),
	FOREIGN KEY(householdId) REFERENCES household(id)
);

CREATE TABLE binaryProductHousehold(
	binaryProductId INTEGER NOT NULL,
	householdId INTEGER NOT NULL,
	availability BOOLEAN, 
	PRIMARY KEY(uncountableProductId,householdId),
	FOREIGN KEY(uncountableProductId) REFERENCES uncountableProduct(id),
	FOREIGN KEY(householdId) REFERENCES household(id)
);

INSERT INTO countableProductCategory VALUES(default, 'Fruits');
INSERT INTO countableProductCategory VALUES(default, 'Vegetagles');
INSERT INTO countableProductCategory VALUES(default, 'Dairy');
INSERT INTO countableProductCategory VALUES(default, 'Bread');

INSERT INTO uncountableProductCategory VALUES(default, 'Meats');
INSERT INTO uncountableProductCategory VALUES(default, 'Fish');
INSERT INTO uncountableProductCategory VALUES(default, 'Seafood');
INSERT INTO uncountableProductCategory VALUES(default, 'Grain products');
INSERT INTO uncountableProductCategory VALUES(default, 'Dairy');

INSERT INTO liquidProductCategory VALUES(default, 'Dairy');
INSERT INTO liquidProductCategory VALUES(default, 'Oils');
INSERT INTO liquidProductCategory VALUES(default, 'Alcohols');

INSERT INTO binaryProductCategory VALUES(default, 'Spices');

INSERT INTO household VALUES(default,'domKlaudii','haslo');

INSERT INTO countableProduct VALUES(default, 'tomato', 2)
INSERT INTO countableProduct VALUES(default, 'carrot', 2)
INSERT INTO countableProduct VALUES(default, 'parsley', 2)
INSERT INTO countableProduct VALUES(default, 'celery', 2)
INSERT INTO countableProduct VALUES(default, 'onion', 2)
INSERT INTO countableProduct VALUES(default, 'garlic', 2)
INSERT INTO countableProduct VALUES(default, 'cabbage', 2)
INSERT INTO countableProduct VALUES(default, 'egg', 3)
INSERT INTO countableProduct VALUES(default, 'roll', 4)
INSERT INTO countableProduct VALUES(default, ' ', )
INSERT INTO countableProduct VALUES(default, ' ', )
INSERT INTO countableProduct VALUES(default, ' ', )

INSERT INTO uncountableProduct VALUES(default,'pasta spaghetti', 4);
INSERT INTO uncountableProduct VALUES(default, 'beef', 1)
INSERT INTO uncountableProduct VALUES(default, 'cheese Grana Padano', 5)
INSERT INTO uncountableProduct VALUES(default, 'rice', 4)
INSERT INTO uncountableProduct VALUES(default, 'butter', 5)
INSERT INTO uncountableProduct VALUES(default, 'cheese Cheddar', 5)
INSERT INTO uncountableProduct VALUES(default, 'chicken', 1)
INSERT INTO uncountableProduct VALUES(default, 'pork chop', 1)
INSERT INTO uncountableProduct VALUES(default, '', )
INSERT INTO uncountableProduct VALUES(default, ' ', )
INSERT INTO uncountableProduct VALUES(default, ' ', )
INSERT INTO uncountableProduct VALUES(default, ' ', )

INSERT INTO liquidProduct VALUES(default, 'olive oil', 2)
INSERT INTO liquidProduct VALUES(default, 'red wine', 3)
INSERT INTO liquidProduct VALUES(default, 'white wine', 3)
INSERT INTO liquidProduct VALUES(default, ' ', )
INSERT INTO liquidProduct VALUES(default, ' ', )
INSERT INTO liquidProduct VALUES(default, ' ', )
INSERT INTO liquidProduct VALUES(default, ' ', )
INSERT INTO liquidProduct VALUES(default, ' ', )
INSERT INTO liquidProduct VALUES(default, ' ', )
INSERT INTO liquidProduct VALUES(default, ' ', )
INSERT INTO liquidProduct VALUES(default, ' ', )
INSERT INTO liquidProduct VALUES(default, ' ', )
INSERT INTO liquidProduct VALUES(default, ' ', )

INSERT INTO binaryProduct VALUES(default, 'bay leaf', 1)
INSERT INTO binaryProduct VALUES(default, 'black pepper', 1)
INSERT INTO binaryProduct VALUES(default, 'salt', 1)
INSERT INTO binaryProduct VALUES(default, 'allspice', 1)
INSERT INTO binaryProduct VALUES(default, 'basil', 1)
INSERT INTO binaryProduct VALUES(default, ' ', )
INSERT INTO binaryProduct VALUES(default, ' ', )

INSERT INTO liquidProductHousehold VALUES(1,1,0.3);



