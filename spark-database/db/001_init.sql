-- Setup  postgres storage

CREATE ROLE tester WITH
        LOGIN
        NOSUPERUSER
        NOCREATEDB
        NOCREATEROLE
        INHERIT
        NOREPLICATION
        CONNECTION LIMIT -1
        PASSWORD 'spark3';

CREATE DATABASE spark_db
        WITH 
        OWNER = tester
        ENCODING = 'UTF8'
        CONNECTION LIMIT = -1;
