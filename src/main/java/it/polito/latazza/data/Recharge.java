package it.polito.latazza.data;

import java.util.Date;

//Design modification: added the attribute Account and methods 
public class Recharge extends Transaction {
	

	Integer employeeId;
	
	public Recharge(Date date, Integer amount, Integer employeeId) {
		super(date, amount);	
		this.employeeId = employeeId;
	}
	
	public Integer getEmployeeId() {
		return this.employeeId;
	}

}
