package com.clinic.anhe;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller    // This means that this class is a Controller
@RequestMapping(path="/anho") 
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
	
	@Autowired
	private InventoryRepository inventoryRepository;
	

	
	@PostMapping(path="/medicine/add")
	public @ResponseBody String addMedicine(
						@RequestBody Medicine medicine) {	
		
		Medicine m = medicineRepository.save(medicine);
		return String.valueOf(m.getMid());
	}
	
	@GetMapping(path="/medicine")
	public @ResponseBody Iterable<Medicine> getMedincineByCategory(@RequestParam String category) {
		return medicineRepository.findByCategory(category);
	}
	
	@GetMapping(path="/medicine/all")
	public @ResponseBody Iterable<Medicine> getAllMedincine() {
		return medicineRepository.findAll();
	}
	
	@GetMapping(path="/medicine/update")
	public @ResponseBody String updateMedicineStock(@RequestParam Integer mid, @RequestParam Integer stock) {
		Medicine med = medicineRepository.findByMid(mid);
		int currentStock = med.getStock();
		currentStock += stock.intValue();
		med.setStock(currentStock);
		medicineRepository.save(med);
		return "saved";
	}
	
	
	@PostMapping(path="/record/addlist")
	public @ResponseBody String addMedicineRecordList(@RequestBody List<MedicineRecord> recordList) {
		medicineRecordRepository.saveAll(recordList);
	    //TODO: after saving all, we need to update stock in medicine
		for(MedicineRecord m: recordList) {
			Medicine med = medicineRepository.findByMid(m.getMid());
			int currentStock = med.getStock() - m.getQuantity();
			med.setStock(currentStock);
			medicineRepository.save(med);
		}
		return "saved";
	}
	
	
	@GetMapping(path="/record/add")
	public @ResponseBody String addMedicineRecord(
			@RequestParam String name, @RequestParam Integer mid,
		    @RequestParam Integer pid, @RequestParam String pname, 
			@RequestParam Integer create_by,@RequestParam Integer subtotal) {
		java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
	
		MedicineRecord medicineRecord = new MedicineRecord();
		medicineRecord.setMedicineName(name);
		medicineRecord.setMid(mid);
		medicineRecord.setQuantity(1);
		medicineRecord.setPayment("CASH");
		medicineRecord.setPid(pid);
		medicineRecord.setPatientName(pname);
		medicineRecord.setCreateBy(create_by);
		medicineRecord.setChargeBy(create_by);
		medicineRecord.setChargeAt(today);
		medicineRecord.setSubtotal(subtotal);
		
		medicineRecordRepository.save(medicineRecord);
		
		return "saved";
	}
	
	@GetMapping(path="/record/unpaid")
	public @ResponseBody Iterable<MedicineRecord> getUnpaidMedicineRecord() {
		return medicineRecordRepository.findByChargeAtIsNullOrderByCreateAtAsc();
	}
	
	@GetMapping(path="/record/pid/unpaid")
	public @ResponseBody Iterable<MedicineRecord> getUnpaidMedicineRecordByPid(@RequestParam Integer pid) {
		return medicineRecordRepository.findByPidAndChargeAtIsNullOrderByCreateAtAsc(pid);
	}
	
	@GetMapping(path="/record/createdate")
	public @ResponseBody Iterable<MedicineRecord> getMedicineRecordByCreateDate(@RequestParam Integer pid, @RequestParam String start) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = format.parse(start);
		return medicineRecordRepository.findByPidAndCreateAtGreaterThanEqualOrderByCreateAtAsc(pid, date);
	}
	
	@GetMapping(path="/record/pid/rangedate")
	public @ResponseBody Iterable<MedicineRecord> getMedicineRecordByPidAndRangeDate(
			@RequestParam Integer pid, @RequestParam String start, @RequestParam String end) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date startDate = format.parse(start);
		Date endDate = format.parse(end);
		return medicineRecordRepository.findByPidAndCreateAtGreaterThanEqualAndCreateAtLessThanEqualOrderByCreateAtAsc
				(pid, startDate, endDate);
	}
	
	@GetMapping(path="/record/charged/rangedate")
	public @ResponseBody Iterable<MedicineRecord> getChargedMedicineRecordByRangeDate(
			 @RequestParam String start, @RequestParam String end) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date startDate = format.parse(start);
		Date endDate = format.parse(end);
		return medicineRecordRepository.findByChargeAtGreaterThanEqualAndChargeAtLessThanEqualOrderByCreateAtAsc(startDate, endDate);
				
	}
	
	@GetMapping(path="/record/medname/rangedate")
	public @ResponseBody Iterable<MedicineRecord> getMedicineRecordByMedNameAndRangeDate(
			@RequestParam String medname, @RequestParam String start, @RequestParam String end) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date startDate = format.parse(start);
		Date endDate = format.parse(end);
		return medicineRecordRepository.findByMedicineNameAndCreateAtGreaterThanEqualAndCreateAtLessThanEqualOrderByCreateAtAsc(medname, startDate, endDate);
	}
	
	@GetMapping(path="/record/update")
	public @ResponseBody String updateMedicineRecordByRid(@RequestParam Integer rid, @RequestParam Integer chargeBy) {
		 MedicineRecord medicineRecord = medicineRecordRepository.findByRid(rid);
		 medicineRecord.setChargeBy(chargeBy);
		 medicineRecord.setChargeAt(java.sql.Date.valueOf(LocalDate.now()));
		 medicineRecordRepository.save(medicineRecord);
		 return "saved";
	}
	
	@GetMapping(path="/record/chargedate")
	public @ResponseBody Iterable<MedicineRecord> getMedicineRecordByChargeDate( @RequestParam String start) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = format.parse(start);
		return medicineRecordRepository.findByChargeAt(date);
	}
	
	
	@GetMapping(path="/record/actualcash")
	public @ResponseBody Iterable<MedicineRecord> insertActualCash(@RequestParam Integer cash, @RequestParam Integer diff) {
		List<MedicineRecord> result = new ArrayList();
		java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
		MedicineRecord actual = new MedicineRecord();
		actual.setMedicineName("實際金額");
		actual.setMid(2);
		actual.setCreateBy(1);
		actual.setPid(1);
		actual.setQuantity(1);
		actual.setPatientName("帳本");
		actual.setPayment("ACTUAL");
		actual.setSubtotal(cash);
		actual.setChargeAt(today);
		//actual.setCreateAt(today);
		actual.setChargeBy(1);
		result.add(medicineRecordRepository.save(actual));
		
//		if(diff != 0) {
			MedicineRecord d = new MedicineRecord();
			d.setMedicineName("正負金額");
			d.setMid(2);
			d.setCreateBy(1);
			d.setPid(1);
			d.setQuantity(1);
			d.setPatientName("帳本");
			d.setPayment("DIFF");
			d.setSubtotal(diff);
			d.setChargeAt(today);
			//d.setCreateAt(today);
			d.setChargeBy(1);
			result.add(medicineRecordRepository.save(d));
		
//		}
		
		return result;
	}
	
	@GetMapping(path="/patient/name")
	public @ResponseBody Iterable<Patient> getPatientByName(@RequestParam String name) {
		
		return patientRepository.findByName(name);
	}
	
	@GetMapping(path="/patient/add")
	public @ResponseBody String addPatient(@RequestParam String name, @RequestParam String ic, @RequestParam String day, @RequestParam String shift) {
		Patient p = new Patient();
		p.setName(name);
		p.setIc(ic);
		p.setDay(day);
		p.setShift(shift);
		patientRepository.save(p);
		return "saved";
	}
	
	
	@GetMapping(path="/patient")
	public @ResponseBody Iterable<Patient> getPatientByDayAndShift(@RequestParam String day, @RequestParam String shift) {
		
		return patientRepository.findByDayAndShift(day, shift);
	}
	
	@GetMapping(path="/patient/all")
	public @ResponseBody Iterable<Patient> getPatientAll() {
		
		return patientRepository.findAll();
	}
	
	@GetMapping(path="/patient/day")
	public @ResponseBody Iterable<Patient> getPatientByDayAndShift(@RequestParam String day) {
		
		return patientRepository.findByDay(day);
	}
	
	@GetMapping(path="/employee/all")
	public @ResponseBody Iterable<Employee> getAllEmployee() {
		return employeeRepository.findAll();
	}
	
	@GetMapping(path="/employee/add")
	public @ResponseBody Employee AddEmployee(@RequestParam String name, @RequestParam String position) {
		Employee e = new Employee();
		e.setName(name);
		e.setPosition(position);
		return employeeRepository.save(e);
	}
	
	
	@PostMapping(path="/shiftrecord/addlist")
	public @ResponseBody String addShiftRecordList(@RequestBody List<ShiftRecord> recordList) {
		String nurse = recordList.get(0).getNurse();
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//		String date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//		Date date = format.parse();
//		shiftRecordRepository.removeByNurseAndCreateAt(nurse, new Date());
		shiftRecordRepository.saveAll(recordList);
		return "saved";
	}
	
	@GetMapping(path="/shiftrecord")
	public @ResponseBody Iterable<ShiftRecord> getAllShiftRecord(@RequestParam String createAt) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = format.parse(createAt);
		return shiftRecordRepository.findByCreateAt(date);
	}
	
	@GetMapping(path="/shiftrecord/patient")
	public @ResponseBody Iterable<ShiftRecord> getShiftRecordByPatient(@RequestParam String patient, @RequestParam String createAt) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = format.parse(createAt);
		return shiftRecordRepository.findByPatientAndCreateAt(patient, date);
	}
	
	@GetMapping(path="/shiftrecord/delete")
	public @ResponseBody Iterable<ShiftRecord> deleteFromShiftRecordByNurseAndCreateAt(@RequestParam String patient,@RequestParam String nurse,
			@RequestParam String createAt, @RequestParam String shift)  throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = format.parse(createAt);
		return shiftRecordRepository.removeByPatientAndNurseAndShiftAndCreateAt(patient, nurse, shift, date);
	}
	
	
	@PostMapping(path="/inventory/add")
	public @ResponseBody String addInventory(@RequestBody Inventory inventory) {
//		String nurse = recordList.get(0).getNurse();
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//		String date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//		Date date = format.parse();
//		shiftRecordRepository.removeByNurseAndCreateAt(nurse, new Date());
		inventoryRepository.save(inventory);
		return "saved";
	}
	
	@GetMapping(path="/inventory/medname/rangedate")
	public @ResponseBody Iterable<Inventory> getInventory (
		@RequestParam String medname, @RequestParam String start, @RequestParam String end) throws ParseException {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date startDate = format.parse(start);
			Date endDate = format.parse(end);
			return inventoryRepository.findByMedicineAndCreateAtGreaterThanEqualAndCreateAtLessThanEqual(medname, startDate, endDate);
	
	}
	
	@GetMapping(path="/shiftrecord/reminder")
	public @ ResponseBody List<String> getReminder(String shift, String today) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date startDate = format.parse(today);
		Iterable<ShiftRecord> result = shiftRecordRepository.findByShiftAndCreateAt(shift, startDate);
		List<String> resultList = new ArrayList();

		Iterable<MedicineRecord> mResult = medicineRecordRepository.findByCreateAt(startDate);
		boolean inList = false;
		Iterator it = result.iterator();
		while(it.hasNext()) {
			inList =false;
			ShiftRecord s = (ShiftRecord) it.next();
			for (MedicineRecord m : mResult) {
				if(m.getCreateBy() == s.getEid()) {
					inList = true;
					break;
				}
			}
			if(inList == false && !resultList.contains(s.getNurse())) {
				resultList.add(s.getNurse());
			}
		}
		
		return resultList;
	}
	
}
