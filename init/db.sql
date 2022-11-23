DROP SCHEMA IF EXISTS employees;
DROP SCHEMA IF EXISTS justifications;
DROP SCHEMA IF EXISTS approvals;
DROP SCHEMA IF EXISTS clock;
DROP SCHEMA IF EXISTS wages;

CREATE SCHEMA IF NOT EXISTS employees;
CREATE SCHEMA IF NOT EXISTS justifications;
CREATE SCHEMA IF NOT EXISTS approvals;
CREATE SCHEMA IF NOT EXISTS clock;
CREATE SCHEMA IF NOT EXISTS wages;


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


USE justifications;
CREATE TABLE justification
(
    id                 int PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
    justification_date date                   NOT NULL,
    details            varchar(200),
    status             bool,
    employee_rut       varchar(30)             NOT NULL
);


USE approvals;
CREATE TABLE approval
(
    id                 int PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
    approval_date      date                   NOT NULL,
    details            varchar(200),
    employee_rut       varchar(30)             NOT NULL
);


USE clock;
CREATE TABLE worked_day
(
    id           int PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
    date         date,
    clock_in     time,
    clock_out    time,
    overtime     double,
    minutes_late int,
    employee_rut varchar(30)            NOT NULL
);


USE wages;
CREATE TABLE wage
(
    id           int PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
    date         date                   NOT NULL,
    employee_rut varchar(30)            NOT NULL
);

CREATE TABLE wage_detail
(
    id      int PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
    name    varchar(30)            NOT NULL,
    type    varchar(30)            NOT NULL,
    amount  bigint                 NOT NULL,
    wage_id int                    NOT NULL,
    FOREIGN KEY (wage_id) REFERENCES wages.wage (id)
);

CREATE TABLE working_days
(
    id     int PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
    year   int                    NOT NULL,
    month  int                    NOT NULL,
    amount int                    NOT NULL
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

INSERT INTO justification(justification_date, details, status, employee_rut)
VALUES ('2022-11-05', 'justification', true, '6.591.049-7');

INSERT INTO approval(approval_date, details, employee_rut)
VALUES ('2022-11-05', 'approval', '14.707.441-7');

INSERT INTO working_days(year, month, amount)
VALUES (2022, 1, 21),
       (2022, 2, 20),
       (2022, 3, 23),
       (2022, 4, 20),
       (2022, 5, 22),
       (2022, 6, 22),
       (2022, 7, 21),
       (2022, 8, 22),
       (2022, 9, 21),
       (2022, 10, 19),
       (2022, 11, 21),
       (2022, 12, 21);
