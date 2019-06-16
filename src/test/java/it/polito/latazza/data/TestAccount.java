package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import it.polito.latazza.data.Transaction.TransactionType;

public class TestAccount { 

	@Test
	 public void TestSetAmount() {
		Account account=new Account(10, 0);
		account.setAmount(200);
		Integer newAmount=account.getAmount();
		assertEquals(200, newAmount.intValue());
		account.setAmount(-10);
		newAmount=account.getAmount();
		assertEquals(-10, newAmount.intValue());
	}
	
	
	@Test
	 public void getEmployeeId() {
		Account account=new Account(10, 0);
		Integer employeeId = account.getEmployeeId();
		assertEquals(10, (int)employeeId);
	}
	@Test
	 public void TestsetTransaction() {
		Transaction transaction = new Transaction(new Date(System.currentTimeMillis()), 10);
		Account account=new Account(10, 0);
		account.setTransaction(transaction);
		assertEquals(account.getTransactions().get(0), transaction);
		
	}
	
	@Test
	 public void TestSetNewTransaction() {
		Account account=new Account(10, 0);
		
		
		Transaction newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.RECHARGE, 10 , 20, false);
	    assertEquals(true, account.getTransactions().contains(newTransaction));
		
	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.RECHARGE, 10 , null, false);
	    assertEquals(true, account.getTransactions().contains(newTransaction));
	    
	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.RECHARGE, null , 10, false);
	    assertEquals(true, account.getTransactions().contains(newTransaction));
	    
	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.RECHARGE, -10 , null , false);
	    assertEquals(true, account.getTransactions().contains(newTransaction));
	    	    
	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.RECHARGE, null , -10 , false);
	    assertEquals(true, account.getTransactions().contains(newTransaction));

	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.CONSUMPTION, 10 ,20,  false);
	    assertEquals(true, account.getTransactions().contains(newTransaction));
	    
	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.CONSUMPTION, -10 , -10 , false);
	    assertEquals(null,newTransaction);
	    
	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.CONSUMPTION, null , null , false);
	    assertEquals(null,newTransaction);
	    
	    newTransaction= account.setNewTransaction(-10, new Date(System.currentTimeMillis()), TransactionType.CONSUMPTION, 10 , -10 , false);
	    assertEquals(null,newTransaction);
	    
	    newTransaction= account.setNewTransaction(-10, new Date(System.currentTimeMillis()), TransactionType.CONSUMPTION, -10 , 10 , false);
	    assertEquals(null,newTransaction);
	    
	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.CONSUMPTION, 10 , -10 , false);
	    assertEquals(null,newTransaction);
	    
	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.CONSUMPTION, -10 , 10 , false);
	    assertEquals(null,newTransaction);

	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.CONSUMPTION, 10 , null , false);
	    assertEquals(null,newTransaction);
	    
	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.CONSUMPTION, null , 10 , false);
	    assertEquals(null,newTransaction);
	    
	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.CONSUMPTION, null , -10 , false);
	    assertEquals(null,newTransaction);
	    
	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.CONSUMPTION, -10 , null , false);
	    assertEquals(null,newTransaction);
	    
	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.RECHARGE, -10 , null , true);
	    assertEquals(true, account.getTransactions().contains(newTransaction));
	    

	    
	    newTransaction= account.setNewTransaction(10, new Date(System.currentTimeMillis()), TransactionType.BOXPURCHASE, null , 10 , false);
	    assertEquals(null,newTransaction);

	    SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
	    
	    try {
			newTransaction= account.setNewTransaction(-10, formatter.parse("01/01/2000"), TransactionType.CONSUMPTION, 10 , 20 , false);
			assertEquals(null,newTransaction);
		    newTransaction= account.setNewTransaction(-10, formatter.parse("01/01/2100"), TransactionType.CONSUMPTION, 10 , 20 , false);
		    assertEquals(null,newTransaction);
		    newTransaction= account.setNewTransaction(10, formatter.parse("01/01/2100"), TransactionType.RECHARGE, 10 , 20 , false);
		    assertEquals(null,newTransaction);
		    newTransaction= account.setNewTransaction(10, formatter.parse("01/01/2000"), TransactionType.RECHARGE, 10 , 20 , false);
		    assertEquals(null,newTransaction);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    
	}
}
