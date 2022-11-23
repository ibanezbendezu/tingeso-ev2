DROP SCHEMA IF EXISTS approvals;

CREATE SCHEMA IF NOT EXISTS approvals;

USE approvals;

CREATE TABLE approval
(
    id                 int PRIMARY KEY UNIQUE NOT NULL AUTO_INCREMENT,
    approval_date      date                   NOT NULL,
    details            varchar(200),
    employee_rut       varchar(30)             NOT NULL
);

INSERT INTO approval(approval_date, details, employee_rut)
VALUES ('2022-11-05', 'approval', '14.707.441-7');
