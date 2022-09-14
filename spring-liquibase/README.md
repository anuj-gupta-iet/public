# Getting Started

### Setup Liquibase

* Add starter dependency liquibase Migration and Spring Data JPA (added H2 as well because we need in memory DB) 
* next add this property in application.properties file - spring.liquibase.change-log=classpath:db/changelog/master.xml
* next put respective SQL files in DDL/DML folders
* Note: every changeset file should have one occurance of this comment at the top -- liquibase formatted sql
* Note: one changeset file consists of multiple changesets which are started like this -- changeset author-anuj:change-set-id-1  

* h2-console url - http://localhost:8080/h2-console/

* changeset execution details can be found in this table SELECT * FROM DATABASECHANGELOG