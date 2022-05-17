SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `gestionasignaturas` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `gestionasignaturas` ;

-- -----------------------------------------------------
-- Table `gestionasignaturas`.`ALUMNO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gestionasignaturas`.`ALUMNO` (
  `DNI` VARCHAR(16) NOT NULL ,
  `N_Mat` VARCHAR(8) NOT NULL ,
  `Nombre` VARCHAR(32) NOT NULL ,
  `Apellidos` VARCHAR(64) NOT NULL ,
  PRIMARY KEY (`DNI`) ,
  UNIQUE INDEX `DNI_UNIQUE` (`DNI` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestionasignaturas`.`GRUPO_CLASE`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gestionasignaturas`.`GRUPO_CLASE` (
  `Cod_GC` VARCHAR(16) NOT NULL ,
  PRIMARY KEY (`Cod_GC`) ,
  UNIQUE INDEX `Cod_GC_UNIQUE` (`Cod_GC` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestionasignaturas`.`PROFESOR`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gestionasignaturas`.`PROFESOR` (
  `Cod_P` INT NOT NULL ,
  `Nombre` VARCHAR(32) NOT NULL ,
  `Apellidos` VARCHAR(64) NOT NULL ,
  `activo` BIT NULL ,
  PRIMARY KEY (`Cod_P`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestionasignaturas`.`CURSO`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gestionasignaturas`.`CURSO` (
  `Cod_CURSO` INT NOT NULL ,
  `actual` BIT NULL ,
  PRIMARY KEY (`Cod_CURSO`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestionasignaturas`.`imparte`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gestionasignaturas`.`imparte` (
  `PROFESOR_Cod_P` INT NOT NULL ,
  `GRUPO_CLASE_Cod_GC` VARCHAR(16) NOT NULL ,
  `CURSO_Cod_CURSO` INT NOT NULL ,
  PRIMARY KEY (`PROFESOR_Cod_P`, `GRUPO_CLASE_Cod_GC`) ,
  INDEX `fk_PROFESOR_has_GRUPO_CLASE_GRUPO_CLASE1` (`GRUPO_CLASE_Cod_GC` ASC) ,
  INDEX `fk_imparte_CURSO1` (`CURSO_Cod_CURSO` ASC) ,
  CONSTRAINT `fk_PROFESOR_has_GRUPO_CLASE_PROFESOR1`
    FOREIGN KEY (`PROFESOR_Cod_P` )
    REFERENCES `gestionasignaturas`.`PROFESOR` (`Cod_P` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_PROFESOR_has_GRUPO_CLASE_GRUPO_CLASE1`
    FOREIGN KEY (`GRUPO_CLASE_Cod_GC` )
    REFERENCES `gestionasignaturas`.`GRUPO_CLASE` (`Cod_GC` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_imparte_CURSO1`
    FOREIGN KEY (`CURSO_Cod_CURSO` )
    REFERENCES `gestionasignaturas`.`CURSO` (`Cod_CURSO` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestionasignaturas`.`GRUPO_PRACTICA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gestionasignaturas`.`GRUPO_PRACTICA` (
  `Cod_GP` INT NOT NULL ,
  `activo` BIT NULL ,
  PRIMARY KEY (`Cod_GP`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestionasignaturas`.`PRACTICA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gestionasignaturas`.`PRACTICA` (
  `Cod_Practica` VARCHAR(32) NOT NULL ,
  PRIMARY KEY (`Cod_Practica`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestionasignaturas`.`TIPO_EVALUACION`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gestionasignaturas`.`TIPO_EVALUACION` (
  `Ev_Continua` TINYINT(1)  NOT NULL ,
  `Peso_Ex` FLOAT NOT NULL ,
  `Peso_Pr` FLOAT NOT NULL ,
  `Peso_Ev_C` FLOAT NOT NULL ,
  PRIMARY KEY (`Ev_Continua`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestionasignaturas`.`EXAMEN`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gestionasignaturas`.`EXAMEN` (
  `Cod_Ex` VARCHAR(32) NOT NULL ,
  PRIMARY KEY (`Cod_Ex`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestionasignaturas`.`CONVOCATORIA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gestionasignaturas`.`CONVOCATORIA` (
  `idCONVOCATORIA` VARCHAR(16) NOT NULL ,
  `actual` BIT NULL ,
  PRIMARY KEY (`idCONVOCATORIA`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestionasignaturas`.`EVALUACION`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gestionasignaturas`.`EVALUACION` (
  `Cod_Evaluacion` INT NOT NULL ,
  `ALUMNO_DNI` VARCHAR(16) NOT NULL ,
  `GRUPO_PRACTICA_Cod_GP` INT NULL ,
  `TIPO_EVALUACION_Ev_Continua` TINYINT(1)  NOT NULL ,
  `PRACTICA_Cod_Practica` VARCHAR(32) NOT NULL ,
  `EXAMEN_Cod_Ex` VARCHAR(32) NOT NULL ,
  `Nota_Pr` FLOAT NULL ,
  `Nota_Ev_C` FLOAT NULL ,
  `Nota_P1` FLOAT NULL ,
  `Nota_P2` FLOAT NULL ,
  `Nota_P3` FLOAT NULL ,
  `Nota_P4` FLOAT NULL ,
  `Nota_Final` FLOAT NULL ,
  `CURSO_Cod_CURSO` INT NOT NULL ,
  `CONVOCATORIA_idCONVOCATORIA` VARCHAR(16) NOT NULL ,
  `Practica_convalidada` TINYINT(1)  NOT NULL ,
  `Examen_convalidado` TINYINT(1)  NOT NULL ,
  INDEX `fk_EVALUACION_ALUMNO1` (`ALUMNO_DNI` ASC) ,
  INDEX `fk_EVALUACION_GRUPO_PRACTICA1` (`GRUPO_PRACTICA_Cod_GP` ASC) ,
  INDEX `fk_EVALUACION_PRACTICA1` (`PRACTICA_Cod_Practica` ASC) ,
  INDEX `fk_EVALUACION_TIPO_EVALUACION1` (`TIPO_EVALUACION_Ev_Continua` ASC) ,
  INDEX `fk_EVALUACION_EXAMEN1` (`EXAMEN_Cod_Ex` ASC) ,
  PRIMARY KEY (`Cod_Evaluacion`) ,
  INDEX `fk_EVALUACION_CURSO1` (`CURSO_Cod_CURSO` ASC) ,
  INDEX `fk_EVALUACION_CONVOCATORIA1` (`CONVOCATORIA_idCONVOCATORIA` ASC) ,
  CONSTRAINT `fk_EVALUACION_ALUMNO1`
    FOREIGN KEY (`ALUMNO_DNI` )
    REFERENCES `gestionasignaturas`.`ALUMNO` (`DNI` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EVALUACION_GRUPO_PRACTICA1`
    FOREIGN KEY (`GRUPO_PRACTICA_Cod_GP` )
    REFERENCES `gestionasignaturas`.`GRUPO_PRACTICA` (`Cod_GP` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EVALUACION_PRACTICA1`
    FOREIGN KEY (`PRACTICA_Cod_Practica` )
    REFERENCES `gestionasignaturas`.`PRACTICA` (`Cod_Practica` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EVALUACION_TIPO_EVALUACION1`
    FOREIGN KEY (`TIPO_EVALUACION_Ev_Continua` )
    REFERENCES `gestionasignaturas`.`TIPO_EVALUACION` (`Ev_Continua` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EVALUACION_EXAMEN1`
    FOREIGN KEY (`EXAMEN_Cod_Ex` )
    REFERENCES `gestionasignaturas`.`EXAMEN` (`Cod_Ex` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EVALUACION_CURSO1`
    FOREIGN KEY (`CURSO_Cod_CURSO` )
    REFERENCES `gestionasignaturas`.`CURSO` (`Cod_CURSO` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EVALUACION_CONVOCATORIA1`
    FOREIGN KEY (`CONVOCATORIA_idCONVOCATORIA` )
    REFERENCES `gestionasignaturas`.`CONVOCATORIA` (`idCONVOCATORIA` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestionasignaturas`.`MATRICULA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gestionasignaturas`.`MATRICULA` (
  `Cod_MATRICULA` INT NOT NULL ,
  `ALUMNO_DNI` VARCHAR(16) NOT NULL ,
  `GRUPO_CLASE_Cod_GC` VARCHAR(16) NOT NULL ,
  `CURSO_Cod_CURSO` INT NOT NULL ,
  PRIMARY KEY (`Cod_MATRICULA`) ,
  INDEX `fk_MATRICULA_ALUMNO1` (`ALUMNO_DNI` ASC) ,
  INDEX `fk_MATRICULA_GRUPO_CLASE1` (`GRUPO_CLASE_Cod_GC` ASC) ,
  INDEX `fk_MATRICULA_CURSO1` (`CURSO_Cod_CURSO` ASC) ,
  CONSTRAINT `fk_MATRICULA_ALUMNO1`
    FOREIGN KEY (`ALUMNO_DNI` )
    REFERENCES `gestionasignaturas`.`ALUMNO` (`DNI` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_MATRICULA_GRUPO_CLASE1`
    FOREIGN KEY (`GRUPO_CLASE_Cod_GC` )
    REFERENCES `gestionasignaturas`.`GRUPO_CLASE` (`Cod_GC` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_MATRICULA_CURSO1`
    FOREIGN KEY (`CURSO_Cod_CURSO` )
    REFERENCES `gestionasignaturas`.`CURSO` (`Cod_CURSO` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gestionasignaturas`.`TUTORIA`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `gestionasignaturas`.`TUTORIA` (
  `Cod_TUTORIA` INT NOT NULL ,
  `PROFESOR_Cod_P` INT NOT NULL ,
  `GRUPO_PRACTICA_Cod_GP` INT NOT NULL ,
  `CURSO_Cod_CURSO` INT NOT NULL ,
  `CONVOCATORIA_idCONVOCATORIA` VARCHAR(16) NOT NULL ,
  PRIMARY KEY (`Cod_TUTORIA`) ,
  INDEX `fk_TUTORIA_PROFESOR1` (`PROFESOR_Cod_P` ASC) ,
  INDEX `fk_TUTORIA_GRUPO_PRACTICA1` (`GRUPO_PRACTICA_Cod_GP` ASC) ,
  INDEX `fk_TUTORIA_CURSO1` (`CURSO_Cod_CURSO` ASC) ,
  INDEX `fk_TUTORIA_CONVOCATORIA1` (`CONVOCATORIA_idCONVOCATORIA` ASC) ,
  CONSTRAINT `fk_TUTORIA_PROFESOR1`
    FOREIGN KEY (`PROFESOR_Cod_P` )
    REFERENCES `gestionasignaturas`.`PROFESOR` (`Cod_P` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_TUTORIA_GRUPO_PRACTICA1`
    FOREIGN KEY (`GRUPO_PRACTICA_Cod_GP` )
    REFERENCES `gestionasignaturas`.`GRUPO_PRACTICA` (`Cod_GP` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_TUTORIA_CURSO1`
    FOREIGN KEY (`CURSO_Cod_CURSO` )
    REFERENCES `gestionasignaturas`.`CURSO` (`Cod_CURSO` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_TUTORIA_CONVOCATORIA1`
    FOREIGN KEY (`CONVOCATORIA_idCONVOCATORIA` )
    REFERENCES `gestionasignaturas`.`CONVOCATORIA` (`idCONVOCATORIA` )
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
