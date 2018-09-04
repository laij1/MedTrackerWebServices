package com.clinic.anhe;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.clinic.anhe.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	public Iterable<Employee> findByEid(Integer eid);
}
