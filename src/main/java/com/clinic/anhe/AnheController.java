package com.clinic.anhe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller    // This means that this class is a Controller
@RequestMapping(path="/anhe") 
public class AnheController {

	@Autowired
	private MedicineRepository medicineRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private MedicineRecordRepository medicineRecordRepository;

	
	@PostMapping(path="/medicine/add")
	public @ResponseBody String addMedicine(
						@RequestBody Medicine medicine) {	
		medicineRepository.save(medicine);
		//articleRepository.save(a);
		return "Saved";
	}
	
	@GetMapping(path="/medicine/all")
	public @ResponseBody Iterable<Medicine> getAllMedincine() {
		// This returns a JSON or XML with the users
		//return medicineRepository.findByCategory(category);
		return medicineRepository.findAll();
	}
	
	@GetMapping(path="/record/add")
	public @ResponseBody String addMedicineRecord(
			@RequestParam String name, @RequestParam Integer mid, @RequestParam Integer quantity,
			@RequestParam String payment, @RequestParam Integer pid, @RequestParam Integer create_by,
			@RequestParam Integer subtotal){
		
		MedicineRecord medicineRecord = new MedicineRecord();
		medicineRecord.setMedicineName(name);
		medicineRecord.setMid(mid);
		medicineRecord.setQuantity(quantity);
		medicineRecord.setPayment(payment);
		medicineRecord.setPid(pid);
		medicineRecord.setCreateBy(create_by);
		medicineRecord.setSubtotal(subtotal);
		
		medicineRecordRepository.save(medicineRecord);
		
		return "saved";
	}
	
	@GetMapping(path="/patient")
	public @ResponseBody Iterable<Patient> getPatientByDayAndShift(@RequestParam String day, @RequestParam String shift) {

		return patientRepository.findByDayAndShift(day, shift);
	}
	
}
