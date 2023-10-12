DROP TABLE IF EXISTS `comment`;

CREATE TABLE `cookbook_new`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `recipe_id` INT NULL,
  `content` VARCHAR(300),
  PRIMARY KEY (`id`),
  INDEX `user_id_foreign_idx` (`user_id` ASC) VISIBLE,
  INDEX `recipe_id_foreign_idx` (`recipe_id` ASC) VISIBLE,
  CONSTRAINT `user_id_foreign`
    FOREIGN KEY (`user_id`)
    REFERENCES `cookbook_new`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `recipe_id_foreign`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `cookbook_new`.`recipes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

# ALTER TABLE `cookbook_new`.`recipes` 
# ADD COLUMN `short_description` VARCHAR(200) NOT NULL AFTER `category`;