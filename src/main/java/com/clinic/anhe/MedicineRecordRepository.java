package com.clinic.anhe;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRecordRepository extends JpaRepository<MedicineRecord, Long>{

	
	
	public Iterable<MedicineRecord> findByPidAndChargeAtIsNullOrderByCreateAtAsc(Integer pid);
	
	public MedicineRecord findByRid(Integer rid);
	
	public Iterable<MedicineRecord> findByPidAndCreateAtGreaterThanEqualOrderByCreateAtAsc(Integer pid, Date createAt);
	
	public Iterable<MedicineRecord> findByPidAndCreateAtGreaterThanEqualAndCreateAtLessThanEqualOrderByCreateAtAsc(
			Integer pid, Date startAt, Date endAt );
	
	public Iterable<MedicineRecord> findByMedicineNameAndCreateAtGreaterThanEqualAndCreateAtLessThanEqualOrderByCreateAtAsc(
			String medname, Date startAt, Date endAt );
	
	public Iterable<MedicineRecord> findByChargeAtIsNullOrderByCreateAtAsc();
	
	public Iterable<MedicineRecord> findByChargeAtGreaterThanEqualAndChargeAtLessThanEqualOrderByCreateAtAsc(
			 Date startAt, Date endAt );
	
	public Iterable<MedicineRecord> findByChargeAt(Date chargeAt);
	
	public Iterable<MedicineRecord> findByCreateAt(Date createAt);
}
