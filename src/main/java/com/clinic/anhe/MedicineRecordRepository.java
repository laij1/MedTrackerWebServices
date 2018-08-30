package com.clinic.anhe;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRecordRepository extends JpaRepository<MedicineRecord, Long>{

	public Iterable<MedicineRecord> findByChargeAtIsNull();
	
	public Iterable<MedicineRecord> findByPidAndChargeAtIsNull(Integer pid);
	
	public MedicineRecord findByRid(Integer rid);
}
