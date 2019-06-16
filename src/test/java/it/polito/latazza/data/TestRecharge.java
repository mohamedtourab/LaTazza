package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class TestRecharge {

	
	@Test
	public void TestGetEmployeeId() {
		Integer eid=new Integer(0);
		Recharge r=new Recharge(new Date(System.currentTimeMillis()), 100, eid);
		Integer gEid=r.getEmployeeId();
		assertEquals(eid, gEid);
	}
	
}
