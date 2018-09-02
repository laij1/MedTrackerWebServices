package com.clinic.anhe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	
	public Iterable<Patient> findByDayAndShift(String day, String shift);
	
	public Iterable<Patient> findByDay(String day);
}
