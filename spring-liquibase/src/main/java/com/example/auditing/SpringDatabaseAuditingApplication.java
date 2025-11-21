package com.example.auditing;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

/*
 * here we have annotated Employee table with @Audited
 * so now every operation in this table will be audited in employee_aud table
 * 
 * for programmatically getting current revision number
 * 
 * @Autowired
 * private EntityManager entityManager;
 * 
 * AuditReader auditReader = AuditReaderFactory.get(entityManager);
 * RevInfo revisionEntity = auditReader.getCurrentRevision(RevInfo.class, true);
 * Long currentRevisionNumber = revisionEntity.getRev();
 */

@SpringBootApplication
@RestController
public class SpringDatabaseAuditingApplication {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/saveEmployee")
    public void saveEmployee(@RequestBody Employee employee) {
	employeeRepository.save(employee);
    }

    @PostMapping("/updateEmployee")
    public void updateEmployee(@RequestBody Employee employee) {
	Employee employeeDb = employeeRepository.findById(employee.getId()).get();
	employeeDb.setName(employee.getName());
	employeeRepository.save(employeeDb);
    }

    public static void main(String[] args) {
	SpringApplication.run(SpringDatabaseAuditingApplication.class, args);
    }

}

@Data
@Entity
@Table(name = "employee")
@Audited
class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_generator")
    @SequenceGenerator(name = "employee_id_generator", sequenceName = "employee_seq", allocationSize = 1)
    private Long id;
    private String name;
}

@Repository
interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

@Data
@Entity
@RevisionEntity
@Table(name = "revinfo")
class RevInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revinfo_id_generator")
    @SequenceGenerator(name = "revinfo_id_generator", sequenceName = "revinfo_seq", allocationSize = 1)
    @RevisionNumber
    private Long rev;

    @RevisionTimestamp
    private Date revtstmp;
}