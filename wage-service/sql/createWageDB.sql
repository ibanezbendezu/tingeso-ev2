DROP SCHEMA IF EXISTS wages;

CREATE SCHEMA IF NOT EXISTS wages;

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
