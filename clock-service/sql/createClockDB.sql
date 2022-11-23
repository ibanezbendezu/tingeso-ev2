DROP SCHEMA IF EXISTS clock;

CREATE SCHEMA IF NOT EXISTS clock;

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
