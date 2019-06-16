package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class TestBoxPurchase {
	
	@Test
	public void TestGetCapsuleType() {
		Integer bid=new Integer(0);
		BoxPurchase box=new BoxPurchase(new Date(System.currentTimeMillis()), 100,100, bid);
		Integer gBid=box.getCapsuleType();
		assertEquals(bid, gBid);
	}
	
	@Test
	public void TestGetQuantity() {
		Integer quantity=new Integer(100);
		BoxPurchase box=new BoxPurchase(new Date(System.currentTimeMillis()), 100,quantity, 0);
		Integer gQuantity=box.getQuantity();
		assertEquals(quantity, gQuantity);
	}

}
