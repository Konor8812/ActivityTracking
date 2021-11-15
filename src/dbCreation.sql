-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema FPDatabase
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `FPDatabase` ;

-- -----------------------------------------------------
-- Schema FPDatabase
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `FPDatabase` DEFAULT CHARACTER SET utf8 ;
USE `FPDatabase` ;

-- -----------------------------------------------------
-- Table `FPDatabase`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FPDatabase`.`user` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `login` VARCHAR(20) NOT NULL,
    `role` VARCHAR(45) NOT NULL DEFAULT 'user',
    `password` VARCHAR(20) NOT NULL,
    `total_points` DECIMAL(10) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) INVISIBLE)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FPDatabase`.`activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FPDatabase`.`activity` (
                                                       `id` INT NOT NULL AUTO_INCREMENT,
                                                       `name` VARCHAR(50) NOT NULL,
    `duration` VARCHAR(45) NOT NULL,
    `reward` double(4,1) NOT NULL,
    `description` VARCHAR(200) NULL,
    `status` TINYINT(1) NOT NULL DEFAULT 0,
    UNIQUE INDEX `activity_UNIQUE` (`name` ASC) VISIBLE,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FPDatabase`.`user_has_activity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FPDatabase`.`user_has_activity` (
                                                                `user_id` INT NOT NULL,
                                                                `activity_id` INT NOT NULL,
                                                                PRIMARY KEY (`user_id`, `activity_id`),
    INDEX `fk_user_has_activity_activity1_idx` (`activity_id` ASC) VISIBLE,
    INDEX `fk_user_has_activity_user_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_has_activity_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `FPDatabase`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_user_has_activity_activity1`
    FOREIGN KEY (`activity_id`)
    REFERENCES `FPDatabase`.`activity` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
    ENGINE = InnoDB;

INSERT INTO user (login, role, password, total_points) VALUES ('admin', 'admin', 'admin', 0);
INSERT INTO user (login, password) VALUES ('user', 'userPass');
INSERT INTO user (login, password) VALUES ('Ivan', 'ivanPass');
INSERT INTO user (login, password) VALUES ('Alex', 'alexPass');

INSERT INTO activity (name, duration, reward, description) VALUES ('reading a book', '3 hours', '3', 'studying, entertainment, free');
INSERT INTO activity (name, duration, reward, description) VALUES ('watching a film', '1.5 hours', '1.5', 'entertainment, free');
INSERT INTO activity (name, duration, reward, description) VALUES ('cooking', '1 hour', '3', 'other, money');
INSERT INTO activity (name, duration, reward, description) VALUES ('java programming', '8 hours', '9', 'studying, working, free');
INSERT INTO activity (name, duration, reward, description) VALUES ('bicycling', '2 hours', '5.5', 'entertainment, sport, bike');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


