package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class TestLaTazzaAccount {
	
	@Test
	public void TestAddTransaction() {
		LaTazzaAccount account=new LaTazzaAccount();
		
		Integer previousSizeList= account.getTransactions().size();
		account.addTransaction(new Consumption(new Date(System.currentTimeMillis()), 100, true, 0, 10, null, true));
		Integer newSizeList=account.getTransactions().size();
		assertEquals(previousSizeList + 1, newSizeList.intValue());
		
		previousSizeList= account.getTransactions().size();
		account.addTransaction(new Consumption(new Date(System.currentTimeMillis()), 100, false, 0, 10, null, false));
		newSizeList=account.getTransactions().size();
		assertEquals(previousSizeList + 1, newSizeList.intValue());
		
		previousSizeList= account.getTransactions().size();
		account.addTransaction(new Recharge(new Date(System.currentTimeMillis()), 100, 0));
		 newSizeList=account.getTransactions().size();
		assertEquals(previousSizeList + 1, newSizeList.intValue());
		
		previousSizeList= account.getTransactions().size();
		account.addTransaction(new BoxPurchase(new Date(System.currentTimeMillis()), 100, 10, 0));
		 newSizeList=account.getTransactions().size();
		assertEquals(previousSizeList + 1, newSizeList.intValue());
		

		previousSizeList= account.getTransactions().size();
		account.addTransaction(null);
		 newSizeList=account.getTransactions().size();
		assertEquals(previousSizeList.intValue(), newSizeList.intValue());
		
	}
	
	
	@Test
	public void TestSetAmount() {
		LaTazzaAccount account=new LaTazzaAccount();
	
		
		Integer previousAmount, newAmount;
		 previousAmount=account.getAmount();
		account.setAmount(previousAmount + previousAmount * 2);
		newAmount=account.getAmount();
		assertEquals(previousAmount + previousAmount * 2, newAmount.intValue());
		previousAmount=account.getAmount();
		account.setAmount(-200);
		newAmount=account.getAmount();
		assertEquals(newAmount.intValue(), previousAmount.intValue());

		account.setAmount(200);
		newAmount=account.getAmount();
		assertEquals(200, newAmount.intValue());
	}
	
	
}
