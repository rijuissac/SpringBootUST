package com.example.EMS_UST;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;

@RestController
public class EMPUSTController 
{
	
	
	AtomicInteger counter=new AtomicInteger();
	
	
	@Autowired
	EMSRepository repository;
	
	@Autowired
	EMSAddService emsService;
	
	@PostMapping("/addEmployee")
	public ResponseEntity<AddResponse> addEmployeeImplementation(@RequestBody EMS ems)
	{
		AddResponse add = new AddResponse();
		HttpHeaders header = new HttpHeaders();
		
		String id = ems.geteNAME()+counter.getAndIncrement();
		
		if (!emsService.checkEMPAlreadyExists(id))
		{
			ems.seteID(id);
			repository.save(ems);
			add.setId(id);
			add.setMsg("Success : Employee is added");
			header.add("Unique", id);
		
			return new ResponseEntity<AddResponse>(add,header,HttpStatus.CREATED); 
		}
		else
		{
			add.setId(id);
			add.setMsg("Employee Already Exists");
			header.add("Unique", id);
			return new ResponseEntity<AddResponse>(add,header,HttpStatus.ACCEPTED);
		}
	}
	
	@GetMapping("/getEmployee/{id}")
	public Object getEmployeeById(@PathVariable(value="id") String id) 
	{
		try 
		{
			EMS ems = repository.findById(id).get();
			return ems;
		}
		catch (Exception e) {
			return new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getEmployee")
	public List<EMS> getAllEmployeeByName(@RequestParam(value="EmployeeName") String employeeName) {
		
		return repository.findAllByeName(employeeName);
	}

	@PutMapping("/updateEmployee/{id}")
	public ResponseEntity<EMS> updateEmployee(@PathVariable(value="id") String id, @RequestBody EMS ems) 
	{
		
		EMS empRecord = repository.findById(id).get();
		empRecord.seteNAME(ems.geteNAME());
		empRecord.seteSAL(ems.geteSAL());
		repository.save(empRecord);
		
		return new ResponseEntity<EMS>(empRecord,HttpStatus.OK);
		
	}
	@DeleteMapping("/deleteEmployee")
	public ResponseEntity<String> deleteEmployeeByID(@RequestBody EMS ems) 
	{
		
		EMS delRec = repository.findById(ems.geteID()).get();
		repository.delete(delRec);
		return new ResponseEntity<>("Employee Deleted Sucessfully",HttpStatus.CREATED);
	}
	
	

}
