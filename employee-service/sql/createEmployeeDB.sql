SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS employees;

CREATE SCHEMA IF NOT EXISTS employees;

USE employees;

CREATE TABLE category
(
    id                 int PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
    type               char                   NOT NULL,
    fixed_monthly_wage int                    NOT NULL
);

CREATE TABLE employee
(
    id          int PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
    rut         varchar(30)            NOT NULL,
    last_names  varchar(30)            NOT NULL,
    first_names varchar(30)            NOT NULL,
    birth_date  date                   NOT NULL,
    hire_date   date                   NOT NULL,
    category_id int                    NOT NULL,
    FOREIGN KEY (category_id) REFERENCES employees.category (id)
);


INSERT INTO category(type, fixed_monthly_wage)
VALUES ('A', 1700000),
       ('B', 1200000),
       ('C', 800000);

INSERT INTO employee(rut, last_names, first_names, birth_date, hire_date, category_id)
VALUES ('14.707.441-7', 'Ibanez Bendezu', 'Aaron Andre', '1996-01-29', '2022-09-06',
        (SELECT id FROM employees.category WHERE type = 'A')),
       ('6.591.049-7', 'Gonzalez Reyes', 'Boris Leonardo', '1952-11-03', '1987-04-22',
        (SELECT id FROM employees.category WHERE type = 'C'));
