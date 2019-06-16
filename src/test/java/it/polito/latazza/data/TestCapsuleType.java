package it.polito.latazza.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;



import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;

public class TestCapsuleType {
	
	CapsuleType ct = new CapsuleType("Coffee", 20, 40);
	DataImpl dImpl = new DataImpl();
	
	@Test
	public void TestAddBoxPurchase() {
		Date date = new Date(System.currentTimeMillis());
		BoxPurchase bp = new BoxPurchase(date, 40, 50, 20);
		ct.addBoxPurchase(bp);
		assertFalse(ct.getBoxPurchases().isEmpty());
		assertTrue(ct.getBoxPurchases().contains(bp));
		bp = null;
		ct.addBoxPurchase(bp);
		assertFalse(ct.getBoxPurchases().contains(bp));
	}
	
	
	@Test
	public void TestSetBoxPrice() {
		ct.setBoxPrice(30);
		assertEquals(new Integer(30), ct.getBoxPrice());
		ct.setBoxPrice(0);
		assertEquals(new Integer(30), ct.getBoxPrice());
		ct.setBoxPrice(-1);
		assertEquals(new Integer(30), ct.getBoxPrice());
		ct.setBoxPrice(1);
		assertEquals(new Integer(1), ct.getBoxPrice());	
	}
	
	@Test
	public void TestSetCapsulesPerBox() {
		ct.setCapsulesPerBox(30);
		assertEquals(new Integer(30), ct.getCapsulesPerBox());
		ct.setCapsulesPerBox(-1);
		assertEquals(new Integer(30), ct.getCapsulesPerBox());
		ct.setCapsulesPerBox(0);
		assertEquals(new Integer(30), ct.getCapsulesPerBox());
		ct.setCapsulesPerBox(1);
		assertEquals(new Integer(1), ct.getCapsulesPerBox());
	}
	
	@Test
	public void TestSetName() {
		ct.setName("Tea");
		assertEquals("Tea", ct.getName());
		ct.setName(null);
		assertEquals("Tea", ct.getName());
	}
	
	@Test
	public void TestSetNumBeverage() {
		ct.setNumBeverage(30);
		assertEquals(new Integer(30), ct.getNumBeverage());
		ct.setNumBeverage(-1);
		assertEquals(new Integer(30), ct.getNumBeverage());
		ct.setNumBeverage(0);
		assertEquals(new Integer(30), ct.getNumBeverage());
		ct.setNumBeverage(1);
		assertEquals(new Integer(1), ct.getNumBeverage());	
	}
	
	@Test
	public void TestSetPrice() {
		ct.setPrice(30);
		assertEquals(new Integer(30), ct.getPrice());
		ct.setPrice(-1);
		assertEquals(new Integer(30), ct.getPrice());
		ct.setPrice(0);
		assertEquals(new Integer(30), ct.getPrice());
		ct.setPrice(1);
		assertEquals(new Integer(1), ct.getPrice());
	}
	
	@Test
	public void TestSetQuantity() {
		ct.setQuantity(30);
		assertEquals(new Integer(30), ct.getQuantity());
		ct.setQuantity(-1);
		assertEquals(new Integer(30), ct.getQuantity());
		ct.setQuantity(0);
		assertEquals(new Integer(0), ct.getQuantity());
		ct.setQuantity(1);
		assertEquals(new Integer(1), ct.getQuantity());
		
		//white Box
		try {
			dImpl.createBeverage("Milk", 20, 40);
			ct.setQuantity(5);
		}
		catch (BeverageException e) {
			fail();
		}
		dImpl.reset();
		try{ 
			dImpl.createBeverage("Coffee", 20, 40);
			dImpl.createBeverage("Tea", 20, 40);
			ct.setQuantity(30);
			assertEquals(new Integer(30), ct.getQuantity());
		}
		catch (BeverageException e) {
			fail();
		}
	
		
	}
	
	//added 15/06
	@Test
	public void TestSetNewQuantity() {
		ct.setNewQuantity(20);
		assertEquals(new Integer(20), ct.getNewQuantity());
		ct.setNewQuantity(-5);
		assertEquals(new Integer(20), ct.getNewQuantity());
	}
	
}
