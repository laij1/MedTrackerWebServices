package com.clinic.anhe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.clinic.anhe.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
