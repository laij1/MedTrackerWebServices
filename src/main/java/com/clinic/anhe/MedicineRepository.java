package com.clinic.anhe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.clinic.anhe.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

	public Iterable<Medicine> findByCategory(String category);
	
}
