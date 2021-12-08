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
DROP TABLE IF EXISTS `FPDatabase`.`user` ;

CREATE TABLE IF NOT EXISTS `FPDatabase`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `role` VARCHAR(45) NOT NULL DEFAULT 'user',
  `password` VARCHAR(20) NOT NULL,
  `total_points` DECIMAL(10) NOT NULL DEFAULT 0,
  `activities_amount` INT NOT NULL DEFAULT 0,
  `status` VARCHAR(10) NOT NULL DEFAULT 'available',
  `requests_amount` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) INVISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FPDatabase`.`activity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `FPDatabase`.`activity` ;

CREATE TABLE IF NOT EXISTS `FPDatabase`.`activity` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `duration` VARCHAR(45) NOT NULL,
  `reward` DOUBLE NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  `taken_by` INT(10) NOT NULL DEFAULT 0,
  UNIQUE INDEX `activity_UNIQUE` (`name` ASC) VISIBLE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FPDatabase`.`user_has_activity`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `FPDatabase`.`user_has_activity` ;

CREATE TABLE IF NOT EXISTS `FPDatabase`.`user_has_activity` (
  `user_id` INT NOT NULL,
  `activity_id` INT NOT NULL,
  `status` VARCHAR(12) NOT NULL DEFAULT 'requested',
  `time_spent` BIGINT(100) NOT NULL,
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


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
