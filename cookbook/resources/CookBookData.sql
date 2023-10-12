use cook_bookdb;
INSERT INTO users VALUES ('TestUser1', 'Smith', 'Dark Theme', 'abcd1234');
INSERT INTO users VALUES ('TestUser2', 'Brown', 'Light theme', 'ajsfuoaohvo8');

INSERT INTO ingredient VALUES ('Meat');
INSERT INTO ingredient VALUES ('Potato');
INSERT INTO ingredient VALUES ('Gravey');

INSERT INTO recipe VALUES ('Potato & Meat', 'Delicious meal with potatoes meat and gravey');

INSERT INTO ingredient_recipe VALUES ('Gravey', 'Potato & Meat');
INSERT INTO ingredient_recipe VALUES ('Meat', 'Potato & Meat');
INSERT INTO ingredient_recipe VALUES ('Potato', 'Potato & Meat');
