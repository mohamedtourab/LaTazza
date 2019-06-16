package it.polito.latazza.data;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class TestConsumption {
	
	@Test
	public void TestGetCapsuleId() {
		Integer bid=new Integer(0);
		Consumption c=new Consumption(new Date(System.currentTimeMillis()), 100, true, bid, 50, 0, false);
		Integer gBid=c.getCapsuleId();
		assertEquals(bid, gBid);
	}
	
	@Test
	public void TestGetCapsulesNumber() {
		Integer capsulesN=new Integer(100);
		Consumption c=new Consumption(new Date(System.currentTimeMillis()), 100, true, 0, capsulesN,  0, false);
		Integer gCapsulesN=c.getCapsulesNumber();
		assertEquals(capsulesN, gCapsulesN);
	}
	
	@Test
	public void TestGetEmployeeId() {
		Integer eid=new Integer(100);
		Consumption c=new Consumption(new Date(System.currentTimeMillis()), 100, true, 0, 100,  eid, false);
		Integer gEid=c.getEmployeeId();
		assertEquals(eid, gEid);
	}
	
	@Test
	public void TestGetIsCash() {
		Boolean cash=true;
		Consumption c=new Consumption(new Date(System.currentTimeMillis()), 100, cash, 0, 100,  0, false);
		Boolean gCash=c.getIsCash();
		assertEquals(cash, gCash);
	}
	
	@Test
	public void TestGetIsVisitor() {
		Boolean visitor=true;
		Consumption c=new Consumption(new Date(System.currentTimeMillis()), 100, true, 0, 100,  null, visitor);
		Boolean gVisitor=c.getIsVisitor();
		assertEquals(visitor, gVisitor);
	}
	
}
