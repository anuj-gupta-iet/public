package com.example.liquibase.changeset.generator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import liquibase.change.ColumnConfig;
import liquibase.change.core.CreateTableChange;
import liquibase.change.core.DeleteDataChange;
import liquibase.change.core.InsertDataChange;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.serializer.ChangeLogSerializer;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;

@SpringBootApplication
public class SpringLiquibaseChangesetGeneratorApplication {

    @PostConstruct
    private void init() throws FileNotFoundException, IOException {
	System.out.println("=================Start=================");
	DatabaseChangeLog databaseChangeLog = new DatabaseChangeLog();
	addInsertIntoTableChangeSet(databaseChangeLog);
	addCreateTableChangeSet(databaseChangeLog);

	try (OutputStream os = new FileOutputStream("F:\\tmp\\mychangelog.xml")) {
	    ChangeLogSerializer changeLogSerializer = new XMLChangeLogSerializer();
	    List<ChangeSet> changeSets = databaseChangeLog.getChangeSets();
	    changeLogSerializer.write(changeSets, os);
	    os.flush();
	}
	System.out.println("=================End=================");
    }

    private void addInsertIntoTableChangeSet(DatabaseChangeLog databaseChangeLog) {
	ChangeSet changeSet = new ChangeSet("id", "author", false, false, "filePath", null, null, null,
		databaseChangeLog);

	DeleteDataChange deleteDataChange = new DeleteDataChange();
	deleteDataChange.setTableName("tableName");
	deleteDataChange.setWhere("id in (123, 456)");
	changeSet.addChange(deleteDataChange);

	InsertDataChange insertDataChange = new InsertDataChange();
	insertDataChange.setTableName("tableName");
	insertDataChange.addColumn(new ColumnConfig().setName("colName").setValue("colValue"));
	insertDataChange.addColumn(new ColumnConfig().setName("colName").setValueNumeric(1111));
	changeSet.addChange(insertDataChange);

	databaseChangeLog.addChangeSet(changeSet);
    }

    private void addCreateTableChangeSet(DatabaseChangeLog databaseChangeLog) {
	ChangeSet changeSet = new ChangeSet("id", "author", false, false, "filePath", null, null, null,
		databaseChangeLog);
	CreateTableChange createTableChange = new CreateTableChange();
	createTableChange.setTableName("tableName");
	ColumnConfig columnConfig = new ColumnConfig();
	columnConfig.setName("colName");
	columnConfig.setType("colType");
	createTableChange.addColumn(columnConfig);

	changeSet.addChange(createTableChange);
	databaseChangeLog.addChangeSet(changeSet);
    }

    public static void main(String[] args) {
	ConfigurableApplicationContext ctx = SpringApplication.run(SpringLiquibaseChangesetGeneratorApplication.class,
		args);
	ctx.close();
    }

}
