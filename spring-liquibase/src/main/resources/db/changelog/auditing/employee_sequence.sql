-- liquibase formatted sql
-- changeset author-anuj:employee_seq
create sequence employee_seq 
	increment by 1
	minvalue 1
	start 1
;