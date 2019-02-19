package com.clinic.anhe;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{

	public Iterable<Inventory> findByMedicineAndCreateAtGreaterThanEqualAndCreateAtLessThanEqual(
			String name, Date startAt, Date endAt);
}
