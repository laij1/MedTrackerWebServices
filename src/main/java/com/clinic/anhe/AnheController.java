package com.clinic.anhe;

import java.time.LocalDate;
import java.util.List;

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
	
	@Autowired
	private ShiftRecordRepository shiftRecordRepository;

	
	@PostMapping(path="/medicine/add")
	public @ResponseBody String addMedicine(
						@RequestBody Medicine medicine) {	
		medicineRepository.save(medicine);
		//articleRepository.save(a);
		return "Saved";
	}
	
	@GetMapping(path="/medicine")
	public @ResponseBody Iterable<Medicine> getMedincineByCategory(@RequestParam String category) {
		return medicineRepository.findByCategory(category);
	}
	
	@GetMapping(path="/medicine/all")
	public @ResponseBody Iterable<Medicine> getAllMedincine() {
		return medicineRepository.findAll();
	}
	
	@PostMapping(path="record/addlist")
	public @ResponseBody String addMedicineRecordList(@RequestBody List<MedicineRecord> recordList) {
		medicineRecordRepository.saveAll(recordList);
		return "saved";
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
	
	@GetMapping(path="/record/unpaid")
	public @ResponseBody Iterable<MedicineRecord> getUnpaidMedicineRecord() {
		return medicineRecordRepository.findByChargeAtIsNull();
	}
	
	@GetMapping(path="/record/pid/unpaid")
	public @ResponseBody Iterable<MedicineRecord> getUnpaidMedicineRecordByPid(@RequestParam Integer pid) {
		return medicineRecordRepository.findByPidAndChargeAtIsNull(pid);
	}
	
	@GetMapping(path="/record/update")
	public @ResponseBody String updateMedicineRecordByRid(@RequestParam Integer rid, @RequestParam Integer chargeBy) {
		 MedicineRecord medicineRecord = medicineRecordRepository.findByRid(rid);
		 medicineRecord.setChargeBy(chargeBy);
		 medicineRecord.setChargeAt(java.sql.Date.valueOf(LocalDate.now()));
		 medicineRecordRepository.save(medicineRecord);
		 return "saved";
	}
	
	
	@GetMapping(path="/patient")
	public @ResponseBody Iterable<Patient> getPatientByDayAndShift(@RequestParam String day, @RequestParam String shift) {
		
		return patientRepository.findByDayAndShift(day, shift);
	}
	
	@GetMapping(path="/patient/day")
	public @ResponseBody Iterable<Patient> getPatientByDayAndShift(@RequestParam String day) {
		
		return patientRepository.findByDay(day);
	}
	
	@GetMapping(path="/employee/all")
	public @ResponseBody Iterable<Employee> getAllEmployee() {
		return employeeRepository.findAll();
	}
	
	@PostMapping(path="/shift/record/addlist")
	public @ResponseBody String addShiftRecordList(@RequestBody List<ShiftRecord> recordList) {
		shiftRecordRepository.saveAll(recordList);
		return "saved";
	}
	
}
