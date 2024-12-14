package com.example.demo;

import javax.annotation.PostConstruct;

import org.jooq.Configuration;
import org.jooq.CreateSchemaFinalStep;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SqlQueryBuilderJooqApplication {

    @PostConstruct
    private void init() {
	insert1();
	insert2();
    }

    private void insert2() {
	System.out.println("==Insert2==");
	Table<Record> myTable = DSL.table("MY_TABLE");
	Field<Integer> idField = DSL.field("ID", Integer.class);
	Field<String> nameField = DSL.field("NAME", String.class);

	CreateSchemaFinalStep mySchema = DSL.createSchema("MY_SCHEMA");

	Configuration config = new DefaultConfiguration();
	Settings settings = new Settings().withRenderSchema(true);
	config.set(settings);

	DSLContext dslContext = DSL.using(config);
	dslContext.setSchema("ABC");

	String sql = dslContext.insertInto(myTable, idField, nameField).values(1, "Anuj Gupta")
		.getSQL(ParamType.NAMED_OR_INLINED);
	// String sql = DSL.using(config).insertInto(myTable, idField,
	// nameField).values(1, "Anuj Gupta")
	// .getSQL(ParamType.INLINED);
	System.out.println(sql);
    }

    private void insert1() {
	System.out.println("==Insert1==");
	Table<Record> myTable = DSL.table("MY_TABLE");
	Field<Integer> idField = DSL.field("ID", Integer.class);
	Field<String> nameField = DSL.field("NAME", String.class);
	String sql = DSL.using(SQLDialect.POSTGRES).insertInto(myTable, idField, nameField).values(1, "Anuj Gupta")
		.getSQL(ParamType.INLINED);
	System.out.println(sql);
    }

    public static void main(String[] args) {
	SpringApplication.run(SqlQueryBuilderJooqApplication.class, args);
    }

}
