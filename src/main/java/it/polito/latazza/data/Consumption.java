package it.polito.latazza.data;

import java.util.Date;

public class Consumption extends Transaction {
	
	boolean isCash;
	Integer capsuleId;
	Integer capsulesNumber;
	Integer employeeId;
	boolean isVisitor;
	
	public Consumption (Date date, Integer amount, boolean isCash, Integer capsuleId, Integer capsulesNumber, Integer employeeId, boolean isVisitor) {
		super(date, amount);
		this.isCash = isCash;
		this.capsuleId = capsuleId;
		this.capsulesNumber = capsulesNumber;
		this.employeeId = employeeId;
		this.isVisitor = isVisitor;
	}
	
		
	public boolean getIsCash(){
		return this.isCash;
	}
	
	public Integer getCapsuleId() {
		return capsuleId;
	}
	
	public Integer getCapsulesNumber() {
		return capsulesNumber;
	}
	
	public Integer getEmployeeId() {
		return this.employeeId;
	}

	public boolean getIsVisitor() {
		return this.isVisitor;
	}
}