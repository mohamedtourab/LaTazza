package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

import it.polito.latazza.data.Transaction.TransactionType;

public class TestTransaction {
	
	@Test
	public void TestEnum() {
		// TODO Auto-generated method stub
		TransactionType.values();
	}
	
	@Test
	public void TestGetDate(){
		Date date=new Date(System.currentTimeMillis());
		Transaction transaction=new Transaction(date, 100);
		Date gDate=transaction.getDate();
		assertEquals(date, gDate);		
	}
	
	@Test
	public void TestGetAmount() {
		Transaction transaction=new Transaction(new Date(System.currentTimeMillis()), 100);
		Integer amount=transaction.getAmount();
		assertEquals(100, amount.intValue());
	}
}
