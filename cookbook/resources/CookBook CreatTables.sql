CREATE SCHEMA `cookbook_new` ;

CREATE TABLE `cookbook_new`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userName` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `favouriteRecipes` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));
  
ALTER TABLE `cookbook_new`.`users` 
DROP COLUMN `favouriteRecipes`;
  
CREATE TABLE `cookbook_new`.`recipes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(1000) NOT NULL,
  `ingredients` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));
  
ALTER TABLE `cookbook_new`.`recipes` 
DROP COLUMN `ingredients`;
  
CREATE TABLE `cookbook_new`.`units_name` (
  `name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`name`));

CREATE TABLE `cookbook_new`.`units` (
  `name` VARCHAR(45) NOT NULL,
  `units_name_id` VARCHAR(45) NOT NULL,
  `quantities` DECIMAL(4) NOT NULL,
  PRIMARY KEY (`name`),
  INDEX `unit_name_id_fk_idx` (`units_name_id` ASC) VISIBLE,
  CONSTRAINT `unit_name_id_fk`
    FOREIGN KEY (`units_name_id`)
    REFERENCES `cookbook_new`.`units_name` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `cookbook_new`.`groups` (
  `group_id` INT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`group_id`));
  
CREATE TABLE `cookbook_new`.`ingredient` (
  `name` VARCHAR(45) NOT NULL,
  `quantity` INT NOT NULL,
  `unit` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`name`),
  INDEX `unit_fk_idx` (`unit` ASC) VISIBLE,
  CONSTRAINT `unit_fk`
    FOREIGN KEY (`unit`)
    REFERENCES `cookbook_new`.`units` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

    
CREATE TABLE `cookbook_new`.`shopping_list` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantities` INT NOT NULL,
  `ingredient_list_id` INT NOT NULL,
  `group_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `ingredient_list_id_fk_idx` (`ingredient_list_id` ASC) VISIBLE,
  INDEX `group_id_fk_idx` (`group_id` ASC) VISIBLE,
  CONSTRAINT `ingredient_list_id_fk`
    FOREIGN KEY (`ingredient_list_id`)
    REFERENCES `cookbook_new`.`ingredientlist` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `group_id_fk`
    FOREIGN KEY (`group_id`)
    REFERENCES `cookbook_new`.`groups` (`group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
ALTER TABLE `cookbook_new`.`users` 
ADD COLUMN `shopping_list_id` INT NULL AFTER `password`,
ADD INDEX `shopping_list_id_fk_idx` (`shopping_list_id` ASC) VISIBLE;
;
ALTER TABLE `cookbook_new`.`users` 
ADD CONSTRAINT `shopping_list_id_fk`
  FOREIGN KEY (`shopping_list_id`)
  REFERENCES `cookbook_new`.`shopping_list` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `cookbook_new`.`groups` 
ADD CONSTRAINT `shoping_list_id_fk`
  FOREIGN KEY (`shopping_list_id`)
  REFERENCES `cookbook_new`.`shopping_list` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `cookbook_new`.`users` 
ADD COLUMN `favorite_recipes` VARCHAR(500) NULL AFTER `shopping_list_id`;

ALTER TABLE `cookbook_new`.`ingredient` 
ADD COLUMN `id` INT NOT NULL AUTO_INCREMENT AFTER `unit`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`);
;


ALTER TABLE `cookbook_new`.`shopping_list` 
DROP FOREIGN KEY `group_id_fk`;
ALTER TABLE `cookbook_new`.`shopping_list` 
ADD COLUMN `user_id` INT NULL AFTER `group_id`,
CHANGE COLUMN `group_id` `group_id` INT NULL ,
ADD INDEX `user_id_fk_idx` (`user_id` ASC) VISIBLE;
;
ALTER TABLE `cookbook_new`.`shopping_list` 
ADD CONSTRAINT `group_id_fk`
  FOREIGN KEY (`group_id`)
  REFERENCES `cookbook_new`.`groups` (`group_id`),
ADD CONSTRAINT `user_id_fk`
  FOREIGN KEY (`user_id`)
  REFERENCES `cookbook_new`.`users` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `cookbook_new`.`users` 
DROP FOREIGN KEY `shopping_list_id_fk`;
ALTER TABLE `cookbook_new`.`users` 
DROP COLUMN `shopping_list_id`,
DROP INDEX `shopping_list_id_fk_idx` ;
;

ALTER TABLE `cookbook_new`.`shopping_list` 
ADD COLUMN `ingredient_name` VARCHAR(45) NULL AFTER `user_id`,
CHANGE COLUMN `quantities` `quantities` INT NULL ,
CHANGE COLUMN `ingredient_list_id` `ingredient_list_id` INT NULL ;



ALTER TABLE `cookbook_new`.`shopping_list` 
CHANGE COLUMN `ingredient_list_id` `ingredient_list` VARCHAR(45) NULL DEFAULT NULL ;


ALTER TABLE `cookbook_new`.`ingredient` 
CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT FIRST;

ALTER TABLE `cookbook_new`.`ingredient` 
ADD CONSTRAINT `recipe_identification_fk`
  FOREIGN KEY (`recipe_id`)
  REFERENCES `cookbook_new`.`recipes` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `cookbook_new`.`shopping_list` 
DROP COLUMN `ingredient_name`,
DROP COLUMN `quantities`;

ALTER TABLE `cookbook_new`.`groups` 
DROP FOREIGN KEY `shoping_list_id_fk`;
ALTER TABLE `cookbook_new`.`groups` 
DROP COLUMN `shopping_list_id`,
DROP INDEX `shopping_list_id_fk_idx` ;
;

ALTER TABLE `cookbook_new`.`recipes` 
CHANGE COLUMN `name` `name` VARCHAR(500) NOT NULL;

ALTER TABLE `cookbook_new`.`recipes` 
ADD COLUMN `ingredients_list` VARCHAR(1000) NOT NULL AFTER `description`;

ALTER TABLE `cookbook_new`.`users` 
ADD COLUMN `shoppingList` VARCHAR(1000) NULL AFTER `favorite_recipes`;

ALTER TABLE `cookbook_new`.`groups` 
ADD COLUMN `shoppingList` VARCHAR(1000) NULL AFTER `group_name`;

DROP TABLE `cookbook_new`.`shopping_list`;

ALTER TABLE `cookbook_new`.`recipes` 
ADD COLUMN `category` VARCHAR(45) NOT NULL AFTER `ingredients_list`;

ALTER TABLE `cookbook_new`.`recipes` 
CHANGE COLUMN `category` `category` VARCHAR(500) NOT NULL ;

CREATE TABLE `cookbook_new`.`favorite_recipes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `recipe_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `name_fk_idx` (`recipe_id` ASC) VISIBLE,
  INDEX `user_id_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `recipe_id_fk`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `cookbook_new`.`recipes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `cookbook_new`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `cookbook_new`.`users` 
DROP COLUMN `favorite_recipes`;

CREATE TABLE `cookbook_new`.`shopping_list` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` INT NULL,
  `group` INT NULL,
  `description` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_fl_idx` (`user` ASC) VISIBLE,
  INDEX `group_fk_idx` (`group` ASC) VISIBLE,
  CONSTRAINT `user_fk`
    FOREIGN KEY (`user`)
    REFERENCES `cookbook_new`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `group_fk`
    FOREIGN KEY (`group`)
    REFERENCES `cookbook_new`.`groups` (`group_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `cookbook_new`.`users` 
DROP COLUMN `shoppingList`;

  
