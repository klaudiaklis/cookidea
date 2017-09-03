USE cookidea;
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

CREATE TABLE difficultyLevel (
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
	difficultyLevelId INTEGER NOT NULL,
	FOREIGN KEY(mealTypeId) REFERENCES mealType(id), 
	FOREIGN KEY(cousineTypeId) REFERENCES cousineType(id),
	FOREIGN KEY(difficultyLevelId) REFERENCES difficultyLevel(id)
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
	amount DOUBLE, 
	PRIMARY KEY(countableProductId,recipeId),
	FOREIGN KEY(countableProductId) REFERENCES countableProduct(id),
	FOREIGN KEY(recipeId) REFERENCES recipe(id)
);

CREATE TABLE uncountableProductRecipe (
	uncountableProductId INTEGER NOT NULL,
	recipeId INTEGER NOT NULL,
	weight DOUBLE, 
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
	PRIMARY KEY(binaryProductId,householdId),
	FOREIGN KEY(binaryProductId) REFERENCES binaryProduct(id),
	FOREIGN KEY(householdId) REFERENCES household(id)
);

select 'This is a comment' AS '';

INSERT INTO household VALUES(default, 'dom', 'haslo');

INSERT INTO countableProductCategory VALUES(default, 'Fruits');
INSERT INTO countableProductCategory VALUES(default, 'Vegetables');
INSERT INTO countableProductCategory VALUES(default, 'Dairy');
INSERT INTO countableProductCategory VALUES(default, 'Bread');
INSERT INTO countableProductCategory VALUES(default, 'Sweets');

INSERT INTO uncountableProductCategory VALUES(default, 'Meats');
INSERT INTO uncountableProductCategory VALUES(default, 'Fish');
INSERT INTO uncountableProductCategory VALUES(default, 'Seafood');
INSERT INTO uncountableProductCategory VALUES(default, 'Grain products');
INSERT INTO uncountableProductCategory VALUES(default, 'Dairy');
INSERT INTO uncountableProductCategory VALUES(default, 'Sweets');

INSERT INTO liquidProductCategory VALUES(default, 'Dairy');
INSERT INTO liquidProductCategory VALUES(default, 'Oils');
INSERT INTO liquidProductCategory VALUES(default, 'Alcohols');

INSERT INTO binaryProductCategory VALUES(default, 'Spices');
INSERT INTO binaryProductCategory VALUES(default, 'Sauce');

INSERT INTO countableProduct VALUES(default, 'pomidory', 2);
INSERT INTO countableProduct VALUES(default, 'marchewka', 2);
INSERT INTO countableProduct VALUES(default, 'pietruszka', 2);
INSERT INTO countableProduct VALUES(default, 'seler', 2);
INSERT INTO countableProduct VALUES(default, 'cebula', 2);
INSERT INTO countableProduct VALUES(default, 'czosnek', 2);
INSERT INTO countableProduct VALUES(default, 'kapusta', 2);
INSERT INTO countableProduct VALUES(default, 'jajka', 3);
INSERT INTO countableProduct VALUES(default, 'bulki', 4);
INSERT INTO countableProduct VALUES(default, 'salata lodowa', 2);
INSERT INTO countableProduct VALUES(default, 'ogorki', 2);
INSERT INTO countableProduct VALUES(default, 'gorzka czekolada', 5);
INSERT INTO countableProduct VALUES(default, 'biala czekolada', 5);
INSERT INTO countableProduct VALUES(default, 'banany', 1);
INSERT INTO countableProduct VALUES(default, 'kiwi', 1);
INSERT INTO countableProduct VALUES(default, 'truskawki', 1);
INSERT INTO countableProduct VALUES(default, 'jablka', 1);
INSERT INTO countableProduct VALUES(default, 'pomarancze', 1);
INSERT INTO countableProduct VALUES(default, 'cytryny', 1);
INSERT INTO countableProduct VALUES(default, 'maliny', 1);

INSERT INTO uncountableProduct VALUES(default,'makaron spaghetti', 4);
INSERT INTO uncountableProduct VALUES(default, 'wolowina', 1);
INSERT INTO uncountableProduct VALUES(default, 'ser Grana Padano', 5);
INSERT INTO uncountableProduct VALUES(default, 'ryz', 4);
INSERT INTO uncountableProduct VALUES(default, 'maslo', 5);
INSERT INTO uncountableProduct VALUES(default, 'ser Cheddar', 5);
INSERT INTO uncountableProduct VALUES(default, 'kurczak', 1);
INSERT INTO uncountableProduct VALUES(default, 'schab', 1);
INSERT INTO uncountableProduct VALUES(default, 'boczek', 1);
INSERT INTO uncountableProduct VALUES(default, 'cukier', 6);
INSERT INTO uncountableProduct VALUES(default, 'maka pszenna', 4);
INSERT INTO uncountableProduct VALUES(default, 'serek Mascarpone', 5);

INSERT INTO liquidProduct VALUES(default, 'oliwa z oliwek', 2);
INSERT INTO liquidProduct VALUES(default, 'czerwone wino', 3);
INSERT INTO liquidProduct VALUES(default, 'biale wino', 3);
INSERT INTO liquidProduct VALUES(default, 'maslanka', 1);
INSERT INTO liquidProduct VALUES(default, 'mleko', 1);

INSERT INTO binaryProduct VALUES(default, 'lisc laurowy', 1);
INSERT INTO binaryProduct VALUES(default, 'pieprz ziarnisty', 1);
INSERT INTO binaryProduct VALUES(default, 'sol', 1);
INSERT INTO binaryProduct VALUES(default, 'galka muszkatalowa', 1);
INSERT INTO binaryProduct VALUES(default, 'bazylia', 1);
INSERT INTO binaryProduct VALUES(default, 'oregano', 1);
INSERT INTO binaryProduct VALUES(default, 'ketchup', 2);
INSERT INTO binaryProduct VALUES(default, 'Barbecue sos', 2);
INSERT INTO binaryProduct VALUES(default, 'Passata pomidorowa', 2);

INSERT INTO mealType VALUES(default, 'Breakfast');
INSERT INTO mealType VALUES(default, 'Lunch');
INSERT INTO mealType VALUES(default, 'Dinner');
INSERT INTO mealType VALUES(default, 'Dessert');
INSERT INTO mealType VALUES(default, 'Supper');

INSERT INTO cousineType VALUES(default, 'Polish');
INSERT INTO cousineType VALUES(default, 'American');
INSERT INTO cousineType VALUES(default, 'Italian');
INSERT INTO cousineType VALUES(default, 'Czech');
INSERT INTO cousineType VALUES(default, 'French');
INSERT INTO cousineType VALUES(default, 'Asian');

INSERT INTO difficultyLevel VALUES(default, 'Easy');
INSERT INTO difficultyLevel VALUES(default, 'Medium');
INSERT INTO difficultyLevel VALUES(default, 'Hard');

INSERT INTO recipe VALUES(default, 'Spaghetti bolognese','Na patelnię wlewamy oliwę.
Od razu dodajemy warzywa: pokrojoną w kostkę marchewkę, cebulę, seler naciowy oraz przeciśnięty przez praskę czosnek.
Podsmażamy na niewielkim ogniu, co chwilę mieszając.
Następnie dodajemy mięso mielone i smażymy do zarumienienia.
Dolewamy wino i gotujemy, aż alkohol odparuje.
Następnie dodajemy pomidory pelati.
Doprawiamy solą, pieprzem, niewielką ilością cukru oraz porwanymi listkami bazylii.
W dużym garnku zagotowujemy wodę.
Do gotującej się wody dodajemy sól i makaron.
Gotujemy al dente według wskazań na opakowaniu.
Makaron odcedzamy przez sito.
Niewielką ilość wody, w której gotował się makaron, wlewamy do sosu.
Ugotowany makaron dokładnie mieszamy z sosem.
Makaron z sosem przekładamy na talerz.
Wierzch posypujemy startym parmezanem.
Dekorujemy listkami świeżej bazylii.', 60,3, 3, 4, 2);
INSERT INTO recipe VALUES(default, 'Risotto','Dymkę (bez szczypioru) drobno siekamy i szklimy na rozgrzanym maśle.
Do cebuli dodajemy suchy ryż, całość mieszamy.
Wino mieszamy z musztardą i wlewamy na patelnię.
Smażymy na dużym ogniu, aż ryż wchłonie wino, nieustannie mieszając.
Do ryżu dodajemy szklankę ugotowanego bulionu z kurczaka, mieszamy.
Gdy ryż wchłonie płyn, dodajemy kolejną szklankę bulionu.
Czynność powtarzamy, aż cały bulion zostanie dodany.
Gdy ryż będzie już lekko ugotowany, dodajemy starty na tarce cheddar i mieszamy, aż ser się roztopi.
Przyprawiamy do smaku solą i pieprzem.',60, 3, 3, 4, 2);
INSERT INTO recipe VALUES(default, 'Golabki', 'Mięso włożyć do większej miski.
Ryż ugotować, przelać zimną wodą na sicie, odsączyć i wystudzony dodać do mięsa.
Cebulę obrać, zetrzeć na tarce, dodać do mięsa z ryżem.
Doprawić solą (około pół łyżeczki), pieprzem (1/4 łyżeczki).
Wszystko wymieszać i dobrze wyrobić dłonią.
Uformować niewielkie podłużne kotlety.
Czytaj dalej...',90, 3, 1, 6, 3);
INSERT INTO recipe VALUES(default, 'Burgery', 'Mięso zmielić, wymieszać z cebulą i doprawić solą i pieprzem.
Uformować kotlety i smażyć razem z boczkiem na patelni grilowej przez 20min.
Pozostałe warzywa pokroić.
Na podgrzane bułki ułożyć kolejno mięso, warzywa i udekorować sosem', 30,2, 2, 6, 1);
INSERT INTO recipe VALUES(default, 'Brownie', 'Rozpuścić masło, czekoladę i cukier w kąpieli wodnej.
Jajka roztrzep w misce jak na jajecznicę, dodaj mąkę, wymieszaj rózgą lub mikserem.
Cały czas mieszając, wolnym strumieniem wlewaj do jajek czekoladę.
Piecz 25 minut w temperaturze 180 stopni.',40, 4, 2, 6, 1);
INSERT INTO recipe VALUES(default, 'Pancakes', 'W blenderze zmiksować wszystkie składniki na gładką masę o konsystencji gęstej śmietany.
Rozgrzać patelnię i na średnim ogniu smażyć pancakes z dwóch stron.
Podawać z owocami',20, 4, 2, 24, 1);
INSERT INTO recipe VALUES(default, 'Salatka owocowa', 'Wszystkie owoce umyć, obrać i pokroić w kostkę.
Wydusić sok z jednej limonki, dodac do owoców i wymieszać delikatnie.
Schłodzić przed podaniem.
',10, 5, 1, 2, 1);


INSERT INTO countableProductRecipe VALUES(2, 1, 2);
INSERT INTO countableProductRecipe VALUES(4, 1, 1);
INSERT INTO countableProductRecipe VALUES(6, 1, 3);
INSERT INTO countableProductRecipe VALUES(5, 1, 1);
INSERT INTO uncountableProductRecipe VALUES(8, 1, 500);
INSERT INTO uncountableProductRecipe VALUES(3, 1, 200);
INSERT INTO liquidProductRecipe VALUES(2, 1, 125);
INSERT INTO liquidProductRecipe VALUES(1, 1, 20);
INSERT INTO binaryProductRecipe VALUES(9, 1);
INSERT INTO binaryProductRecipe VALUES(2, 1);
INSERT INTO binaryProductRecipe VALUES(3, 1);
INSERT INTO binaryProductRecipe VALUES(5, 1);

INSERT INTO countableProductRecipe VALUES(5, 2, 3);
INSERT INTO uncountableProductRecipe VALUES(4, 2, 500);
INSERT INTO uncountableProductRecipe VALUES(7, 2, 500);
INSERT INTO uncountableProductRecipe VALUES(5, 2, 50);
INSERT INTO uncountableProductRecipe VALUES(6, 2, 250);
INSERT INTO liquidProductRecipe VALUES(3, 2, 250);
INSERT INTO binaryProductRecipe VALUES(2, 2);
INSERT INTO binaryProductRecipe VALUES(3, 2);

INSERT INTO countableProductRecipe VALUES(5, 3, 2);
INSERT INTO countableProductRecipe VALUES(7, 3, 1);
INSERT INTO uncountableProductRecipe VALUES(8, 3, 800);
INSERT INTO uncountableProductRecipe VALUES(4, 3, 100);
INSERT INTO binaryProductRecipe VALUES(9, 3);
INSERT INTO binaryProductRecipe VALUES(2, 3);
INSERT INTO binaryProductRecipe VALUES(3, 3);

INSERT INTO countableProductRecipe VALUES(1, 4, 1);
INSERT INTO countableProductRecipe VALUES(5, 4, 1);
INSERT INTO countableProductRecipe VALUES(9, 4, 6);
INSERT INTO countableProductRecipe VALUES(10, 4, 1);
INSERT INTO uncountableProductRecipe VALUES(2, 4, 500);
INSERT INTO uncountableProductRecipe VALUES(9, 4, 200);
INSERT INTO binaryProductRecipe VALUES(8, 4);
INSERT INTO binaryProductRecipe VALUES(2, 4);
INSERT INTO binaryProductRecipe VALUES(3, 4);

INSERT INTO countableProductRecipe VALUES(12, 5, 3);
INSERT INTO countableProductRecipe VALUES(8, 5, 3);
INSERT INTO uncountableProductRecipe VALUES(10, 5, 240);
INSERT INTO uncountableProductRecipe VALUES(11, 5, 80);
INSERT INTO uncountableProductRecipe VALUES(5, 5, 280);
INSERT INTO binaryProductRecipe VALUES(3, 5);

INSERT INTO countableProductRecipe VALUES(8, 6, 2);
INSERT INTO uncountableProductRecipe VALUES(10, 6, 150);
INSERT INTO uncountableProductRecipe VALUES(11, 6, 200);
INSERT INTO liquidProductRecipe VALUES(4, 6, 250);
INSERT INTO binaryProductRecipe VALUES(3, 6);
INSERT INTO binaryProductRecipe VALUES(4, 6);

INSERT INTO countableProductRecipe VALUES(14, 7, 1);
INSERT INTO countableProductRecipe VALUES(15, 7, 10);
INSERT INTO countableProductRecipe VALUES(16, 7, 1);
INSERT INTO countableProductRecipe VALUES(17, 7, 1);
INSERT INTO countableProductRecipe VALUES(18, 7, 1);
INSERT INTO countableProductRecipe VALUES(19, 7, 1);


INSERT INTO countableProductHousehold VALUES(1, 1, 5);
INSERT INTO countableProductHousehold VALUES(2, 1, 1);
INSERT INTO countableProductHousehold VALUES(4, 1, 2);
INSERT INTO countableProductHousehold VALUES(5, 1, 5);
INSERT INTO countableProductHousehold VALUES(8, 1, 10);
INSERT INTO countableProductHousehold VALUES(9, 1, 3);
INSERT INTO countableProductHousehold VALUES(12, 1, 2);
INSERT INTO countableProductHousehold VALUES(14, 1, 2);
INSERT INTO countableProductHousehold VALUES(15, 1, 2);
INSERT INTO countableProductHousehold VALUES(16, 1, 20);
INSERT INTO countableProductHousehold VALUES(18, 1, 1);

INSERT INTO uncountableProductHousehold VALUES(1, 1, 5);
INSERT INTO uncountableProductHousehold VALUES(2, 1, 1);
INSERT INTO uncountableProductHousehold VALUES(4, 1, 2);
INSERT INTO uncountableProductHousehold VALUES(5, 1, 5);
INSERT INTO uncountableProductHousehold VALUES(8, 1, 10);
INSERT INTO uncountableProductHousehold VALUES(9, 1, 3);
INSERT INTO uncountableProductHousehold VALUES(12, 1, 2);

INSERT INTO liquidProductHousehold VALUES(1, 1, 1);
INSERT INTO liquidProductHousehold VALUES(2, 1, 500);
INSERT INTO liquidProductHousehold VALUES(3, 1, 150);
INSERT INTO liquidProductHousehold VALUES(4, 1, 200);
INSERT INTO liquidProductHousehold VALUES(5, 1, 250);

INSERT INTO binaryProductHousehold VALUES(1, 1);
INSERT INTO binaryProductHousehold VALUES(2, 1);
INSERT INTO binaryProductHousehold VALUES(3, 1);
INSERT INTO binaryProductHousehold VALUES(4, 1);
INSERT INTO binaryProductHousehold VALUES(5, 1);
INSERT INTO binaryProductHousehold VALUES(6, 1);
INSERT INTO binaryProductHousehold VALUES(7, 1);
INSERT INTO binaryProductHousehold VALUES(8, 1);
INSERT INTO binaryProductHousehold VALUES(9, 1);






