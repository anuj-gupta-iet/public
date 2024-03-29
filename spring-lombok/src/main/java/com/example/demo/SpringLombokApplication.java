package com.example.demo;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import lombok.extern.java.Log;

// reference
// https://medium.com/javarevisited/all-the-16-lombok-annotations-explained-in-a-4-minute-article-926f71934ec6

@SpringBootApplication
public class SpringLombokApplication {

	@Autowired
	private MyComponent myComponent;

	@PostConstruct
	private void init() {
		myComponent.doSomething("PapaG");
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringLombokApplication.class, args);

		ClassA classA = new ClassA();
		// gives exception - arg is marked non-null but is null
		// classA.doSomething(null);

		ClassB classB = new ClassB();
		classB.getName();
		classB.setName("papaG");

		ClassC classC = new ClassC();
		classC.setFirstName("Anuj");
		classC.setLastName("Gupta");
		// ClassC(firstName=Anuj, lastName=Gupta)
		System.out.println(classC);

		// if we remove @NoArgsConstructor then below line will not compile
		ClassD classD = new ClassD();
		classD = new ClassD("Anuj", "Gupta");

		ClassE classE = new ClassE("Anuj", "Gupta");
		classE.equals(null);
		classE.hashCode();

		ClassF classF = new ClassF();
		// below will give error
		// ClassF classF = new ClassF("Anuj", "Gupta");
		classF.setFirstName("papaG");
		classF.getFirstName();
		classF.toString();
		classF.equals(null);
		classF.hashCode();
		classF.toString();

		// possible
		ClassG classG = new ClassG(1, "Anuj", new Date());
		// step by step building
		ClassG classG2 = ClassG.builder().id(2).name("papaG").dob(new Date())
				.build();

		// not possible
		// ClassH classH = new ClassH();

		ClassH classH = new ClassH("Anuj", "Gupta");
		// not possible
		// classH.firstName;
		// classH.setFirstName("Anuj");

		// only getters
		classH.getFirstName();
	}

}

@Component
@Log
class MyComponent {
	void doSomething(String arg) {
		// this log variable is generated by @Log annotation
		log.info("Logging msg: " + arg);
		// prints [main] INFO c.e.d.MyComponent - Logging msg: PapaG
	}
}

@Value
// make class Immutable
// all the fields will be declared private and final, and will be required by
// the constructor. Additionally, the toString(), hascode() and equals() methods
// will be overridden
class ClassH {
	String firstName;
	String lastName;
}

@Builder
class ClassG {
	private Integer id;
	private String name;
	private Date dob;
}

@Data
// equivalent to @Getters, @Setters, @ToString, @EqualsAndHashCode, and
// @ToString
class ClassF {
	private String firstName;
	private String lastName;
}

@EqualsAndHashCode
@AllArgsConstructor
class ClassE {
	private String firstName;
	private String lastName;
}

@NoArgsConstructor
@AllArgsConstructor
class ClassD {
	private String firstName;
	private String lastName;
}

@ToString
@Setter
class ClassC {
	private String firstName;
	private String lastName;
}

@Getter
@Setter
class ClassB {
	private String name;
}

class ClassA {
	void doSomething(@NonNull String arg) {
		System.out.println(1111);
	}
}