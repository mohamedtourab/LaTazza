package it.polito.latazza.data;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import it.polito.latazza.data.*;
import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;

public class TestScenarios {
	DataImpl dImpl;
	
	public void setUp() {
		 dImpl=new DataImpl();
	}
	
	public void tearDown() {
		dImpl.reset();
	}
	
	@Test
	public void TestScenario1() {
		setUp();
		Integer bid, numCapsules = 10, eid;
		try {
			bid = dImpl.createBeverage("Caffè", 20, 20);
			eid=dImpl.createEmployee("Marco", "Rossi");
			dImpl.rechargeAccount(eid, 1000);
			dImpl.buyBoxes(bid, 1);
			Integer amount= 1* numCapsules;
			Integer previousNum=dImpl.getBeverageCapsules(bid);
			Integer previousEmployeeAmount=dImpl.getEmployeeBalance(eid);
			dImpl.sellCapsules(bid, numCapsules, numCapsules, true);
			Integer actualNum=dImpl.getBeverageCapsules(bid);
			Integer actualEmployeeAmount=dImpl.getEmployeeBalance(eid);
			assertEquals(actualNum.intValue(), previousNum - numCapsules);
			assertEquals(actualEmployeeAmount.intValue(), previousEmployeeAmount - amount);
		} catch (BeverageException | NotEnoughBalance | NotEnoughCapsules | EmployeeException e) {
			
		}
		
		tearDown();
	}
	
	@Test
	public void TestScenario2() {
		setUp();
		Integer bid, numCapsules = 10, eid;
		try {
			bid = dImpl.createBeverage("Caffè", 20, 20);
			eid=dImpl.createEmployee("Marco", "Rossi");
			dImpl.buyBoxes(bid, 1);
			Integer amount= 1* numCapsules;
			Integer previousNum=dImpl.getBeverageCapsules(bid);
			Integer previousEmployeeAmount=dImpl.getEmployeeBalance(eid);
			dImpl.sellCapsules(bid, numCapsules, numCapsules, true);
			Integer actualNum=dImpl.getBeverageCapsules(bid);
			Integer actualEmployeeAmount=dImpl.getEmployeeBalance(eid);
			assertEquals(actualNum.intValue(), previousNum - numCapsules);
			assertEquals(actualEmployeeAmount.intValue(), previousEmployeeAmount - amount);
			assertTrue(actualEmployeeAmount < 0);
		} catch (BeverageException | NotEnoughBalance | NotEnoughCapsules | EmployeeException e) {
			
		}
		
		tearDown();
	}
	
	@Test
	public void TestScenario3() {
		setUp();
		Integer bid, numCapsules = 10;
		try {
			bid = dImpl.createBeverage("Caffè", 20, 20);
			dImpl.buyBoxes(bid, 1);
			Integer amount= 1* numCapsules;
			Integer previousNum=dImpl.getBeverageCapsules(bid);
			Integer previousAmount=dImpl.getBalance();
			dImpl.sellCapsulesToVisitor(bid, numCapsules);
			Integer actualNum=dImpl.getBeverageCapsules(bid);
			Integer actualAmount=dImpl.getBalance();
			assertEquals(actualNum.intValue(), previousNum - numCapsules);
			assertEquals(actualAmount.intValue(), previousAmount + amount);
		} catch (BeverageException | NotEnoughBalance | NotEnoughCapsules e) {
			
		}
		
		tearDown();
	}
	
	@Test
	public void TestScenario4() {
		setUp();
		Integer bid, numCapsules = 100;
		try {
			bid=dImpl.createBeverage("Caffè", 20, 20);
			dImpl.buyBoxes(bid, 1);
			dImpl.sellCapsulesToVisitor(bid, numCapsules);
			fail();
		} catch(BeverageException | NotEnoughCapsules | NotEnoughBalance e) {
			
		}
		tearDown();
	}
	
	@Test
	public void TestScenario5() {
		setUp();
		Integer emp_id, bev_id, previous_amount, new_amount;
		try {
			bev_id = dImpl.createBeverage("Coffee", 20, 20);
			previous_amount=dImpl.getBalance();
			emp_id = dImpl.createEmployee("Giovanni", "Rossi");
			dImpl.rechargeAccount(emp_id, 1000);
			new_amount=dImpl.getBalance();			
			assertEquals(new Integer(1000), dImpl.getEmployeeBalance(emp_id));		
			assertEquals(previous_amount + 1000, new_amount.intValue() );
		}
		catch (BeverageException|EmployeeException e) {
			fail();
		}
		tearDown();
	}
	@Test
	public void TestScenario6() {
		setUp();

		Integer beverageId, previousQuantity, newQuantity;
		try {
			beverageId = dImpl.createBeverage("Coca-cola", 50, 50);
		} catch (BeverageException be) {
			beverageId = 0;
		}

		try {
			previousQuantity = dImpl.getBeverageCapsules(beverageId);
			dImpl.getLaTazzaAccount().setAmount(5000);
			dImpl.buyBoxes(beverageId, 1);
			newQuantity = dImpl.getBeverageCapsules(beverageId);
			assertEquals(newQuantity.intValue(), previousQuantity + dImpl.getBeverageCapsulesPerBox(beverageId));
		} catch (BeverageException e) {
			fail();

		} catch (NotEnoughBalance e) {
			fail();
		}
		tearDown();
		
	}
	
	@Test
	public void TestScenario7() {
		setUp();
		Integer emp_id, bev_id;
		List<String> rep = new ArrayList<String>();
		try {
			Date start_date = new Date (System.currentTimeMillis());
			Thread.sleep(3000);
			bev_id = dImpl.createBeverage("Coffee", 20, 20);	
			emp_id = dImpl.createEmployee("Giovanni", "Rossi");
			String rech = "RECHARGE Giovanni Rossi 10.00 \u20ac";
			dImpl.rechargeAccount(emp_id, 1000);
			dImpl.buyBoxes(bev_id, 1);	
			dImpl.sellCapsules(emp_id, bev_id, 5, true);
			Date end_date = new Date(System.currentTimeMillis()+10000);
			String sell = "BALANCE Giovanni Rossi Coffee 5";
			rep = dImpl.getEmployeeReport(emp_id, start_date , end_date);
			String rep1 = rep.get(0);
			String rep2 = rep.get(1);
			assertTrue(rep1.contains(rech));
			assertTrue(rep2.contains(sell));
		}
		catch (BeverageException|EmployeeException|NotEnoughCapsules|NotEnoughBalance|DateException | InterruptedException e) {
			fail();
		}
		tearDown();
		
	}
	
	@Test
	public void TestScenario8() {
		setUp();
		Integer emp_id, bev_id;
		String trs;
		List<String> rep = new ArrayList<String>();
		List<String> mtch = new ArrayList<String>();
		try {
			Date start_date = new Date (System.currentTimeMillis());
			Thread.sleep(3000);
			bev_id = dImpl.createBeverage("Coffee", 20, 20);
			String buy = "BUY Coffee 1";
			emp_id = dImpl.createEmployee("Giovanni", "Rossi");
			String rech =  "RECHARGE Giovanni Rossi 10.00 \u20ac";
			dImpl.rechargeAccount(emp_id, 1000);
			dImpl.buyBoxes(bev_id, 1);
			dImpl.sellCapsules(emp_id, bev_id, 5, true);
			Date end_date = new Date(System.currentTimeMillis()+10000);
			String sell = "BALANCE Giovanni Rossi Coffee 5";
			rep = dImpl.getReport(start_date, end_date);
			mtch.add(rech);
			mtch.add(buy);
			mtch.add(sell);
			assertTrue(rep.size() == 3);
			for(int i = 0; i<rep.size(); i++) {			
				trs = rep.get(i);
				assertTrue(trs.contains(mtch.get(i)));
			}
		}
		catch (BeverageException|EmployeeException|NotEnoughCapsules|NotEnoughBalance|DateException | InterruptedException e) {
			fail();
		}
		tearDown();
		
	}	

	@Test
	public void TestScenario9() {
		DataImpl dataImpl = new DataImpl();
		dataImpl.reset();
		Integer bcpb = new Integer(35);
		Integer bp = new Integer(70);
		Integer beverageId;
		try {
			beverageId = dataImpl.createBeverage("coca", 50, 100);
			dataImpl.updateBeverage(beverageId, "coffee", bcpb, bp);
			assertEquals("coffee", dataImpl.getBeverageName(beverageId));
			assertEquals(bcpb, dataImpl.getBeverageCapsulesPerBox(beverageId));
			assertEquals(bp, dataImpl.getBeverageBoxPrice(beverageId));
		} catch (BeverageException e) {
			fail();
		}
	}
	
	@Test
	public void TestScenario10() {
		DataImpl dataImpl = new DataImpl();
		dataImpl.reset();
		Integer employeeId = null;
		try {
			
			employeeId = dataImpl.createEmployee("Mohamed", "Mamdouh");

		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			fail();
			e.printStackTrace();
		}
		try {
			dataImpl.updateEmployee(employeeId, "Mohamed", "Tourab"); //test Loop 1 -> just one employee in the database
			assertTrue("MohamedTourab".equals(dataImpl.getEmployeeName(employeeId) + dataImpl.getEmployeeSurname(employeeId)));
		} catch (EmployeeException e) {
			fail();
		}
		
	}
	
	@Test
	public void TestPerformance() throws BeverageException, NotEnoughBalance, EmployeeException, NotEnoughCapsules, DateException{
		setUp();
		tearDown();
		String s=new String();
		Integer bid, eid;
		long begin, end, totaltime=0, begin_2, end_2, totaltime_2=0;
		double avgtime;
		Date startDate=new Date(System.currentTimeMillis());
		CapsuleType.num_beverages=0;
		for (int i=0; i<100; i++) {
			s="bev" + i;
			begin=System.currentTimeMillis();
			bid=dImpl.createBeverage(s, 100, 10);
			end=System.currentTimeMillis();
			begin_2=System.currentTimeMillis();
			dImpl.updateBeverage(bid, s, 100, 5);
			end_2=System.currentTimeMillis();
			totaltime=end-begin;
			totaltime_2=end_2-begin_2;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		avgtime=totaltime_2/100.0;
		assertTrue(avgtime < 500);
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		
		totaltime=0;
		totaltime_2=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			eid=dImpl.createEmployee("name", "surname");
			end=System.currentTimeMillis();
			begin_2=System.currentTimeMillis();
			dImpl.updateEmployee(eid, "name2", "surname2");
			end_2=System.currentTimeMillis();
			totaltime=end-begin;
			totaltime_2=end_2-begin_2;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		avgtime=totaltime_2/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.rechargeAccount(i, 100);
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.buyBoxes(i, 1);
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		totaltime_2=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.sellCapsulesToVisitor(i, 10);
			end=System.currentTimeMillis();
			begin_2=System.currentTimeMillis();
			dImpl.sellCapsules(i, i, 10, true);
			end_2=System.currentTimeMillis();
			totaltime=end-begin;
			totaltime_2=end_2-begin_2;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		avgtime=totaltime_2/100.0;
		assertTrue(avgtime < 500);
		
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getBalance();
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getBeverageBoxPrice(i);
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getBeverageCapsules(i);
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getBeverageCapsulesPerBox(i);
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getBeverageName(i);
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getBeverages();
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getBeveragesId();
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getEmployeeBalance(i);
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getEmployeeName(i);
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		Date endDate=new Date(System.currentTimeMillis() + 1000000);
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getEmployeeReport(i, startDate, endDate);
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getReport( startDate, endDate);
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getEmployees();
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getEmployeesId();
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getEmployeeSurname(i);
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		totaltime=0;
		for (int i=0; i<100; i++) {
			begin=System.currentTimeMillis();
			dImpl.getLaTazzaAccount();
			end=System.currentTimeMillis();
			totaltime=end-begin;
		}
		avgtime=totaltime/100.0;
		assertTrue(avgtime < 500);
		
		
				
		tearDown();
	}

}

