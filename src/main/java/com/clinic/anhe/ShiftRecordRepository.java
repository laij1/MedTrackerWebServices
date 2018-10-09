package com.clinic.anhe;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ShiftRecordRepository extends JpaRepository<ShiftRecord, Long>{

	public Iterable<ShiftRecord> findByCreateAt(Date createAt);
	
	public Iterable<ShiftRecord> findByShiftAndCreateAt(String shift, Date createAt);
	
	public Iterable<ShiftRecord> findByPatientAndCreateAt(String patient, Date createAt);
	
	@Transactional
	public Iterable<ShiftRecord> removeByPatientAndNurseAndShiftAndCreateAt(String patient, String nurse, String shift, Date createAt);
	
}
