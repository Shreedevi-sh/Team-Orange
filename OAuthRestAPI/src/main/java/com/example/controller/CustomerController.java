package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Customer;
import com.example.repo.CustomerRepository;

@RestController
public class CustomerController {
     @Autowired
	private CustomerRepository custRepo;
     
     @RequestMapping("/test")
 	public String test() {
 		return "Hello World";
 	}
	
	@RequestMapping(value="/customers")
	public List<Customer> getAllCustomers()
	{
		List<Customer> customer = new ArrayList<Customer>();  
		custRepo.findAll().forEach(cust1 -> customer.add(cust1));
		return customer;
		//must fetch from database
	}
	
	@RequestMapping( value="/viewcustomer/{custId}")  
	private Customer getCustomer(@PathVariable("custId") long custId)   
	{  
	return custRepo.findOne(custId);  
	} 
	
	@RequestMapping(value = "/addcustomer", method = RequestMethod.POST)
	public Customer createCustomer(@RequestBody Customer customer)
	{
		return custRepo.save(customer) ;
		
	}
	
	@RequestMapping(value="/updatecustomer/{id}",method = RequestMethod.PUT)
	public ResponseEntity<Customer> updateCustomer(@PathVariable(value="id")  long custId,@RequestBody Customer customer) throws ResourceNotFoundException
	{
		Customer cust=null;
		cust=custRepo.findOne(custId);
				//.orElseThrow(()->new ResourceNotFoundException("Customer not found"+custId));
		cust.setName(customer.getName());
		cust.setEmailId(customer.getEmailId());
		
		cust.setPhoneNo(customer.getPhoneNo());
		cust.setCarModel(customer.getCarModel());
		Customer updatedCustomer=custRepo.save(cust);
		return ResponseEntity.ok(updatedCustomer);
		
	}
	
	@RequestMapping(value="/deletecustomer/{id}",method = RequestMethod.DELETE)
	public Map<String,Boolean> deleteCustomer(@PathVariable(value="id")  long custId) throws ResourceNotFoundException
	{
		Customer cust=null;
		cust=custRepo.findOne(custId);
				//.orElseThrow(()->new ResourceNotFoundException("Customer not found"+custId));
		custRepo.delete(cust);
		Map<String,Boolean> response=new HashMap();
		response.put("Yes deleted", Boolean.TRUE);
		return response;
	}
	 
}
