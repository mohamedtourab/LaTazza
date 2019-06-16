package it.polito.latazza.data;

import java.util.Date; 


//Design modification; LaTazzaAccount removed from the attributes.
public class Transaction {

	private Date date;
	private Integer amount;

	
	enum TransactionType {
		RECHARGE,
		CONSUMPTION,
		BOXPURCHASE
	}
	
	public Transaction(Date date,Integer amount) {
		this.date = date;
		this.amount = amount;
	}
	
	public Date getDate() {
		return this.date;
	}
	
	
	public Integer getAmount() {
		return this.amount;
	}
	
	

}
