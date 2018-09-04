package com.clinic.anhe;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRecordRepository extends JpaRepository<ShiftRecord, Long>{

	public Iterable<ShiftRecord> findByCreateAt(Date createAt);
	
	public Iterable<ShiftRecord> findByPatientAndCreateAt(String patient, Date createAt);
}
