-- liquibase formatted sql
-- changeset author-anuj:revinfo
create table revinfo (
	rev int primary key, 
	revtstmp timestamp
);