DROP SCHEMA IF EXISTS justifications;

CREATE SCHEMA IF NOT EXISTS justifications;

USE justifications;

CREATE TABLE justification
(
    id                 int PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
    justification_date date                   NOT NULL,
    details            varchar(200),
    status             bool,
    employee_rut       varchar(30)             NOT NULL
);

INSERT INTO justification(justification_date, details, status, employee_rut)
VALUES ('2022-11-05', 'justification', true, '6.591.049-7');
