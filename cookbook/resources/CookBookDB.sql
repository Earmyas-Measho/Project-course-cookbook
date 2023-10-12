create schema Cook_BookDB;
use Cook_BookDB;
#drop table recipe;
#drop table ingredient;
CREATE TABLE recipe(
	Rname varchar(50),
    Rdescr varchar(1000),
    primary key (Rname)
);

CREATE TABLE ingredient(
Iname varchar(30),
primary key (Iname)
);

CREATE TABLE ingredient_recipe(
IngredientName varchar(30) NOT NULL,
RecipeName varchar(50) NOT NULL,
primary key(IngredientName,RecipeName),
foreign key(IngredientName) references ingredient(Iname),
foreign key(RecipeName) references recipe(Rname)

);

CREATE TABLE users(
userName VARCHAR(20),
diplayName varchar(20),
personalisation varchar(25),
pass varchar(30),
primary key (userName)

);