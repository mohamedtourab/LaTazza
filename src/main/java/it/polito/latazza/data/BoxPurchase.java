package it.polito.latazza.data;

import java.util.Date;

public class BoxPurchase extends Transaction {

	private Integer quantity;
	private Integer capsuleType;
	
	public BoxPurchase(Date date, Integer amount, Integer quantity, Integer capsuleType) {
		super(date, amount);
		this.quantity = quantity;
		this.capsuleType = capsuleType;
	}
	
	
	public Integer getQuantity() {
		return this.quantity;
	}
		
	public Integer getCapsuleType() {
		return this.capsuleType;		
	}
	
}
