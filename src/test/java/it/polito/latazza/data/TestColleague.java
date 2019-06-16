package it.polito.latazza.data;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.EmployeeException;

public class TestColleague {
	
	Colleague colleague = new Colleague("Ran", "Pir");
	DataImpl dImpl = new DataImpl();
	
	@Test
	public void TestBuyCapsules() {
		Transaction transaction = colleague.buyCapsules(10, 10, 2, true);
		assertEquals(new Integer (10*10/100),transaction.getAmount());
		transaction = colleague.buyCapsules(0, 10, 2, true);
		assertEquals(null, transaction);
		transaction = colleague.buyCapsules(-1, 10, 2, true);
		assertEquals(null, transaction);
		transaction = colleague.buyCapsules(10, 0, 2, true);
		assertEquals(null, transaction);
		transaction = colleague.buyCapsules(10, -1, 2, true);
		assertEquals(null, transaction);
		transaction = colleague.buyCapsules(10, 10, -1, true);
		assertEquals(null, transaction);
		transaction = colleague.buyCapsules(10, 10, 0, true);
		assertEquals(new Integer(10*10/100), transaction.getAmount());
		//added 15/06
		transaction = colleague.buyCapsules(-10, 10, 1, 10, 5, true);
		assertEquals(null, transaction);
		transaction = colleague.buyCapsules(10, -10, 1, 10, 5, true);
		assertEquals(null, transaction);
		transaction = colleague.buyCapsules(10, 10, 1, 10, -5, true);
		assertEquals(null, transaction);
	}
	
	@Test
	public void TestSetAccount() {
		Account account = new Account(1, 20);
		colleague.setAccount(account);
		assertEquals(account, colleague.getAccount());
		colleague.setAccount(null);
		assertEquals(account, colleague.getAccount());	
	}
	
	@Test
	public void TestSetName() {
		colleague.setName("Gianni");
		assertEquals("Gianni", colleague.getName());
		colleague.setName(null);
		assertEquals("Gianni", colleague.getName());
	}

	
	@Test
	public void TestSetNumColleague() {
		colleague.setNumColleague(5);
		assertEquals(new Integer(5),colleague.getNumColleague());
		colleague.setNumColleague(-1);
		assertEquals(new Integer(5),colleague.getNumColleague());
		colleague.setNumColleague(1);
		assertEquals(new Integer(1),colleague.getNumColleague());
	
	}
	
	@Test
	public void TestSurname() {
		colleague.setSurname("Rossi");
		assertEquals("Rossi", colleague.getSurname());
		colleague.setSurname(null);
		assertEquals("Rossi", colleague.getSurname());
		
	}
	
	
	@Test
	public void TestRechargeAccount() {
		Transaction transaction = colleague.rechargeAccount(10);
		assertEquals(new Integer(10), transaction.getAmount());
		transaction = colleague.rechargeAccount(0);
		assertEquals(null, transaction);
		transaction = colleague.rechargeAccount(-1);
		assertEquals(null, transaction);
		transaction = colleague.rechargeAccount(1);
		assertEquals(new Integer(1), transaction.getAmount());		
	}
	
	@Test
	//WhiteBox
	public void TestUpdateJsonAccount() {
		dImpl.reset();
		colleague.updateJsonAccount();
		try {
			dImpl.createEmployee("Ran", "Pir");
			colleague.updateJsonAccount();
		}
		catch (EmployeeException e) {
		}
		dImpl.reset();
		try {
			dImpl.createEmployee("Ran", "Pir");
			dImpl.createEmployee("Rany", "Pirr");
			colleague.updateJsonAccount();
		}
		catch (EmployeeException e) {
		}
		
	
	}

}
