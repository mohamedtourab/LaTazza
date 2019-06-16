package it.polito.latazza.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


import java.io.File;
import java.io.FileWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.print.attribute.standard.Sides;
import javax.xml.ws.FaultAction;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;


public class TestDataImpl {

	@Test
	public void TestDataImplConstructor() {
		File fp = null;
		boolean a = true;
		try {	if ((fp = new File("./db/Account.json")).exists()) {
			a = fp.delete();
		}
		if ((fp = new File("./db/Colleague.json")).exists()) {
			a = fp.delete();
		}
		if ((fp = new File("./db/BoxPurchase.json")).exists()) {
			a = fp.delete();
		}   
		if ((fp = new File("./db/CapsuleType.json")).exists()) {
			fp.delete();
		}
		if ((fp = new File("./db/Consumption.json")).exists()) {
			a = fp.delete();
		}
		if ((fp = new File("./db/LaTazzaAccount.json")).exists()) {
			a = fp.delete();
		}
		if ((fp = new File("./db/Recharge.json")).exists()) {
			a = fp.delete();
		}
	} catch (Exception e ) {
		fail();
	}
		
		DataImpl di = new DataImpl();
		di.reset();
		Integer eid = null, bid = null;
		try {
			eid = di.createEmployee("Nino", "Vitale");
			bid = di.createBeverage("Coffee", 50, 25);
			di.createBeverage("Tea", 50, 50);
			di.getLaTazzaAccount().setAmount(50);
			di.buyBoxes(bid, 1);
			di.rechargeAccount(eid, 5000);
			di.sellCapsules(eid, bid, 2, true);
			di.sellCapsules(eid, bid, 2, false);
			di.sellCapsulesToVisitor(bid, 2);
			di = new DataImpl();
		} catch (EmployeeException | BeverageException | NotEnoughBalance | NotEnoughCapsules e) {
			// TODO Auto-generated catch block
			fail();
		}
	}
	
	@Test
	public void TestBuyBoxes()  {
		DataImpl dataImpl=new DataImpl();
		dataImpl.reset();
		Integer id1, previousQuantity, newQuantity;
		try {
			id1 = dataImpl.createBeverage("Caffè", 50, 30);
		} catch (BeverageException be) {
			id1 = 0;
		}

		try {
			previousQuantity = dataImpl.getBeverageCapsules(id1);
			dataImpl.getLaTazzaAccount().setAmount(5000);
			dataImpl.buyBoxes(id1, 1);
			newQuantity = dataImpl.getBeverageCapsules(id1);
			assertEquals(newQuantity.intValue(), previousQuantity + dataImpl.getBeverageCapsulesPerBox(id1));
		} catch (BeverageException be) {
			fail();

		} catch (NotEnoughBalance nb) {
			fail();
		}

		try {
			dataImpl.buyBoxes(-5, 1);
			fail();
		} catch (BeverageException | NotEnoughBalance e) {
		}

		try {
			dataImpl.buyBoxes(-5, -12);
			fail();
		} catch (BeverageException | NotEnoughBalance e) {
		}

		try {
			previousQuantity = dataImpl.getBeverageCapsules(id1);
			dataImpl.buyBoxes(id1, -12);
			newQuantity = dataImpl.getBeverageCapsules(id1);
			assertEquals(previousQuantity.intValue(), newQuantity.intValue());
		} catch (BeverageException | NotEnoughBalance e) {

		}
 		dataImpl.reset();
 		
 		dataImpl.getLaTazzaAccount().setAmount(1);
 		try {
			id1 = dataImpl.createBeverage("Coffee", 50, 25);
		} catch (BeverageException e1) {
			fail();
		}
 		try {
			dataImpl.buyBoxes(id1, 1);
			fail();
		} catch (BeverageException | NotEnoughBalance e) {
			// OK
		}

	}

	@Test
	public void TestCreateBeverage() {
		DataImpl dImpl = new DataImpl();
		Integer tcpb = new Integer(1);
		Integer tbp = new Integer(50);
		Integer id1, id2;
		dImpl.reset();
		try {
			Integer id = dImpl.createBeverage("name", tcpb, tbp);
			// test if the id is greater than 0
			assertTrue(id >= 0);
			// test if the methods related to the creation are consistent
			assertEquals("name", dImpl.getBeverageName(id));
			assertEquals(tcpb, dImpl.getBeverageCapsulesPerBox(id));
			assertEquals(tbp, dImpl.getBeverageBoxPrice(id));
		} catch (BeverageException e) {
			fail();
		}
		dImpl.reset();
		try {
			// test if creating two new beverages results in two different ids
			id1 = dImpl.createBeverage("Coffee", 50, 100);
			id2 = dImpl.createBeverage("Coke", 50, 100);
			assertNotEquals(id1, id2);
		} catch (BeverageException e) {
			fail();
		}
		dImpl.reset();
		try {
			//test if creating two new beverages with the same name is possible (black box)
			id1 = dImpl.createBeverage("Coffee", 50, 100);
			id2 = dImpl.createBeverage("Coffee", 60, 120);
			fail();
		} catch (BeverageException e) {

		}
		// Test sign and type of inputs
		dImpl.reset();
		try {
			dImpl.createBeverage(null, 50, 100);
			fail();
		} catch (BeverageException e) {
		}
		dImpl.reset();
		try {
			dImpl.createBeverage("name", 50, -1);
			fail();
		} catch (BeverageException e) {
		}
		dImpl.reset();
		try {
			dImpl.createBeverage("name", -1, 50);
			fail();
		} catch (BeverageException e) {
		}
		// Added 28/05 
		// test if creating a beverage with an empty string is possibile
		try {
		dImpl.createBeverage("", 20, 40);
			fail();
		}
		catch (BeverageException e) {
		}
		// Added 28/05 
		try {
		dImpl.createBeverage("", null, 40);
			fail();
		}
		catch (BeverageException e) {
		}
		// Added 28/05 
		try {
		dImpl.createBeverage("", 20, null);
			fail();
		}
		catch (BeverageException e) {
		}
		
	}
 
	@Test
	public void TestCreateEmployee() {
		DataImpl di = new DataImpl();
		di.reset();
		try {
			Integer employeeId = di.createEmployee("not null", "not null");
			assertTrue(employeeId >= 0);
		} catch (EmployeeException e) {
			fail();
		}

		try {
			di.createEmployee(null, "not null");
			fail();
		} catch (EmployeeException e) {
			// OK
		}

		try {
			di.createEmployee("not null", null);
			fail();
		} catch (EmployeeException e) {
			// OK
		}

		try {
			di.createEmployee(null, null);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		//Added 29/05
		try {
			di.createEmployee("", "Surname");
			fail();
		} catch (EmployeeException e) {
			// OK
		} 
		//Added 29/05
		try {
			di.createEmployee("Name", "");
			fail();
		} catch (EmployeeException e) {
			// OK
		}
	}

	@Test
	public void TestGetBalance() {
		DataImpl dImpl = new DataImpl();
		dImpl.reset();
		Integer blnc = new Integer(0);
		assertEquals(blnc, dImpl.getBalance());
	}

	@Test
	public void TestGetBeverageBoxPrice() {
		DataImpl dImpl = new DataImpl();
		dImpl.reset();
		try {
			dImpl.getBeverageBoxPrice(5);
			fail();
		} catch (BeverageException e) {

		}
	}

	@Test
	public void TestGetBeverageCapsules() {
		DataImpl dImpl = new DataImpl();
		dImpl.reset();
		Integer id;
		try {
			id = dImpl.createBeverage("Coffee", 15, 30);
			dImpl.getLaTazzaAccount().setAmount(5000);
			dImpl.buyBoxes(id, 1);
			assertEquals(dImpl.getBeverageCapsulesPerBox(id), dImpl.getBeverageCapsules(id));
		} catch (BeverageException e) {
			fail();
		} catch (NotEnoughBalance n) {
			fail();
		}
		dImpl.reset();
		try {
			dImpl.getBeverageCapsules(5);
			fail();
		} catch (BeverageException e) {
		}

	}

	@Test
	public void TestBeverageCapsulesPerBox() {
		DataImpl dImpl = new DataImpl();
		dImpl.reset();
		try {
			dImpl.getBeverageCapsulesPerBox(5);
			fail();
		} catch (BeverageException e) {

		}
	}

	@Test
	public void TestGetBeverageName() {
		DataImpl dImpl = new DataImpl();
		dImpl.reset();
		try {
			dImpl.getBeverageName(5);
			fail();
		} catch (BeverageException e) {

		}
	}

	@Test
	public void TestGetBeverages() {
		DataImpl dImpl = new DataImpl();
		dImpl.reset();
		Integer id1, id2;
		Map<Integer, String> map;
		try {
			id1 = dImpl.createBeverage("Coffee", 50, 100);
			id2 = dImpl.createBeverage("Tea", 25, 50);
			map = dImpl.getBeverages();
			assertEquals("Coffee", map.get(id1));
			assertEquals("Tea", map.get(id2));
		} catch (BeverageException e) {
			fail();
		}
		dImpl.reset();
		map = dImpl.getBeverages();
		assertTrue(map.isEmpty());
		;
	}

	@Test
	public void TestGetBeveragesId() {
		DataImpl dImpl = new DataImpl();
		dImpl.reset();
		List<Integer> ids = new ArrayList<Integer>();
		Integer id1, id2;
		try {
			id1 = dImpl.createBeverage("Coffee", 50, 100);
			id2 = dImpl.createBeverage("Tea", 25, 50);
			ids = dImpl.getBeveragesId();
			assertEquals(2, ids.size());
			assertTrue(ids.contains(id1));
			assertTrue(ids.contains(id2));
		} catch (BeverageException e) {
			fail();
		}
		dImpl.reset();
		ids = dImpl.getBeveragesId();
		assertTrue(ids.isEmpty());
	}

	@Test
	public void TestGetEmployeeBalance() {
		DataImpl di = new DataImpl();
		di.reset();
		try {
			Integer employeeId1 = di.createEmployee("Nino", "Vitale");
			Integer employeeId2 = di.createEmployee("Rosetta", "Pagliuca");
			di.rechargeAccount(employeeId1, 5000);
			di.rechargeAccount(employeeId2, 4000);
			Integer balance = di.getEmployeeBalance(employeeId1);
			assertEquals(5000, balance.intValue());
		} catch (EmployeeException e) {
			fail();
		}
		try {
			Integer balance = di.getEmployeeBalance(40);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		try {
			di.getEmployeeBalance(-5);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
	}

	@Test
	public void TestGetEmployeeName() {
		DataImpl di = new DataImpl();
		di.reset();
		try {
			Integer employeeId1 = di.createEmployee("Nino", "Vitale");
			Integer employeeId2 = di.createEmployee("Rosetta", "Pagliuca");
			String name = di.getEmployeeName(employeeId1);
			assertEquals("Nino", name);
		} catch (EmployeeException e) {
			fail();
		}
		try {
			di.getEmployeeName(49);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		try {
			di.getEmployeeName(-30);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
	}

	@Test
	public void TestGetEmployeeReport() {
		DataImpl dataImpl = new DataImpl();
		dataImpl.reset();
		Integer beverageId = null;
		Integer employeeId = null;
		Date startDate = new Date(System.currentTimeMillis());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			employeeId = dataImpl.createEmployee("Mohamed", "Tourab");
			beverageId = dataImpl.createBeverage("raniero", 50, 90);
			dataImpl.rechargeAccount(employeeId, 1000);
			dataImpl.buyBoxes(beverageId, 2);
			dataImpl.sellCapsules(employeeId, beverageId, 2, true);
			dataImpl.sellCapsules(employeeId, beverageId, 1, true);
			dataImpl.sellCapsules(employeeId, beverageId, 3, true);
			dataImpl.sellCapsules(employeeId, beverageId, 4, false);
		} catch (EmployeeException ee) {

		} catch (BeverageException e) {
			// TODO Auto-generated catch block

		} catch (NotEnoughBalance e) {
			// TODO Auto-generated catch block

		} catch (NotEnoughCapsules e) {
			// TODO Auto-generated catch block

		}		

		// employee exists, positive, negative - startdate after enddate - dates null

		try {
			dataImpl.getEmployeeReport(employeeId, new Date(System.currentTimeMillis()), startDate);
			fail();
		} catch (EmployeeException | DateException e1) {
			// TODO Auto-generated catch block
		}
		try {
			dataImpl.getEmployeeReport(employeeId, null, startDate);
			fail();
		} catch (EmployeeException | DateException e1) {
			// TODO Auto-generated catch block
		}
		try {
			dataImpl.getEmployeeReport(employeeId, startDate, null);
			fail();
		} catch (EmployeeException | DateException e1) {

		}
		try {
			dataImpl.getEmployeeReport(employeeId, null, null);
			fail();
		} catch (EmployeeException | DateException e1) {

		}

		try {
			dataImpl.getEmployeeReport(1000, new Date(System.currentTimeMillis()), startDate);
			fail();
		} catch (EmployeeException | DateException e1) {

		}
		try {
			dataImpl.getEmployeeReport(1000, null, startDate);
			fail();
		} catch (EmployeeException | DateException e1) {
			// TODO Auto-generated catch block
		}
		try {
			dataImpl.getEmployeeReport(1000, startDate, null);
			fail();
		} catch (EmployeeException | DateException e1) {
			// TODO Auto-generated catch block
		}
		try {
			dataImpl.getEmployeeReport(1000, null, null);
			fail();
		} catch (EmployeeException | DateException e1) {
			// TODO Auto-generated catch block
		}

		try {
			dataImpl.getEmployeeReport(-1, new Date(System.currentTimeMillis()), startDate);
			fail();
		} catch (EmployeeException | DateException e1) {

		}
		try {
			dataImpl.getEmployeeReport(-1, startDate, new Date(System.currentTimeMillis()));
			fail();
		} catch (EmployeeException | DateException e1) {

		}
		try {
			dataImpl.getEmployeeReport(-1, null, startDate);
			fail();
		} catch (EmployeeException | DateException e1) {
			// TODO Auto-generated catch block
		}
		try {
			dataImpl.getEmployeeReport(-1, startDate, null);
			fail();
		} catch (EmployeeException | DateException e1) {
			// TODO Auto-generated catch block
		}
		try {
			dataImpl.getEmployeeReport(-1, null, null);
			fail();
		} catch (EmployeeException | DateException e1) {
			// TODO Auto-generated catch block
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Date endDate = new Date(System.currentTimeMillis());


		try {
			dataImpl.getEmployeeReport(1000, startDate, endDate);
			fail();
		} catch (EmployeeException | DateException e1) {
			// TODO Auto-generated catch block
		}
		try {
			dataImpl.getEmployeeReport(-1, startDate, endDate);
			fail();
		} catch (EmployeeException | DateException e1) {
			// TODO Auto-generated catch block
		}

		List<String> report = new ArrayList<String>();

		try {
			report = dataImpl.getEmployeeReport(employeeId, startDate, endDate);
			assertEquals(5, report.size());
		} catch (EmployeeException e) {
		} catch (DateException e) {
		}
		
		try {
			dataImpl.rechargeAccount(employeeId, 5000);
			startDate = new Date(System.currentTimeMillis());
			dataImpl.sellCapsules(employeeId, beverageId, 2, true);
			endDate = new Date(System.currentTimeMillis());
			dataImpl.sellCapsules(employeeId, beverageId, 2, false);
			dataImpl.getEmployeeReport(employeeId, startDate, endDate);
		} catch (EmployeeException | BeverageException | NotEnoughCapsules | DateException e) {
			// TODO Auto-generated catch block
			fail();
		}

	}

	@Test
	public void TestGetEmployees() {
		DataImpl di = new DataImpl();
		di.reset();
		Integer id1 = 1, id2 = 2, id3 = 3;
		int i1 = 1, i2 = 1, i3 = 1, tot = 3;
		try {
			id1 = di.createEmployee("Nino", "Vitale");
			id2 = di.createEmployee("Rosetta", "Pagliuca");
			id3 = di.createEmployee("George", "Nespresso");
		} catch (EmployeeException e) {
			fail();
		}
		Map<Integer, String> result = di.getEmployees();
		for(Entry<Integer, String> val : result.entrySet()) {
			if(id1.intValue() == val.getKey().intValue()) {
				assertTrue("Nino Vitale".equals(val.getValue()));
				tot -= i1--;
			}
			else if(id2.intValue() == val.getKey().intValue()) {
				assertTrue("Rosetta Pagliuca".equals(val.getValue()));
				tot -= i2--;
			}
			else if(id3.intValue() == val.getKey().intValue()) {
				assertTrue("George Nespresso".equals(val.getValue()));
				tot -= i3--;
			}
			else
				fail();
		}
		if(tot != 0) {
			fail();
		}
		return;
	}

	
	@Test
	public void TestGetEmployeeSurname() {
		DataImpl di = new DataImpl();
		di.reset();
		try {
			Integer employeeId1 = di.createEmployee("Nino", "Vitale");
			Integer employeeId2 = di.createEmployee("Rosetta", "Pagliuca");
			String name = di.getEmployeeSurname(employeeId1);
			assertEquals("Vitale", name);
		} catch (EmployeeException e) {
			fail();
		}
		try {
			di.getEmployeeSurname(49);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		try {
			di.getEmployeeSurname(-30);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
	}

	@Test
	public void TestGetReport() {

		DataImpl dataImpl = new DataImpl();
		Integer beverageId;
		Integer employeeId = null;
		Date startDate = null;
		Integer employeeId2 = null;

		try {
			employeeId=dataImpl.createEmployee("Mohamed", "Tourab");
			employeeId2=dataImpl.createEmployee("Rosetta", "Pagliuca");
			beverageId=dataImpl.createBeverage("mints", 50, 90);
			dataImpl.rechargeAccount(employeeId, 5000);
			startDate = new Date(System.currentTimeMillis());
			Thread.sleep(2000);
			dataImpl.buyBoxes(beverageId, 2);
			dataImpl.rechargeAccount(employeeId, 10000);
			dataImpl.rechargeAccount(employeeId2, 10000);
			dataImpl.sellCapsules(employeeId, beverageId, 2, true);
			dataImpl.sellCapsules(employeeId, beverageId, 1, true);
			dataImpl.sellCapsulesToVisitor(beverageId, 2);
			dataImpl.sellCapsules(employeeId2, beverageId, 5, false);
		}
		catch(EmployeeException e) {
			// TODO Auto-generated catch block

		} catch (BeverageException e) {
			// TODO Auto-generated catch block

		} catch (NotEnoughBalance e) {
			// TODO Auto-generated catch block

		} catch (NotEnoughCapsules e) {
			// TODO Auto-generated catch block
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e2) {
			fail();
		}
		Date endDate = new Date(System.currentTimeMillis());
		try {
			dataImpl.rechargeAccount(employeeId, 5000); //this transaction happens after the endDate
		} catch (EmployeeException e1) {
			fail();
		}
		
		List<String> report = new ArrayList<String>();

		try {
			report = dataImpl.getReport(startDate, endDate);
			assertEquals(7, report.size());// 7 is the number of transactions happened

		} catch (DateException e) {
		}

		Date wrongStartDate = new Date(System.currentTimeMillis() + 10000);

		try {
			report = dataImpl.getReport(wrongStartDate, endDate);
			fail();	
		} catch (DateException e) {
		}
		try {
			report = dataImpl.getReport(null, endDate);
			fail();	
		} catch (DateException e) {
		}
		try {
			report = dataImpl.getReport(wrongStartDate, null);
			fail();		
		} catch (DateException e) {
		}
		try {
			report = dataImpl.getReport(null, null);
			fail();	
		} catch (DateException e) {
		}
		try {
			report = dataImpl.getReport(endDate, endDate);
			assertEquals(0, report.size());
		} catch (DateException e) {
			fail();
		}

	}

	@Test
	public void TestRechargeAccount() {
		DataImpl di = new DataImpl();
		di.reset();
		Integer startBalance, endBalance;
		try {	
			startBalance = di.getLaTazzaAccount().getAmount();
			Integer employeeId1 = di.createEmployee("Nino", "Vitale");
			Integer employeeId2 = di.createEmployee("Rosetta", "Pagliuca");
			Integer totAmount = di.rechargeAccount(employeeId1, 5000);
			endBalance = startBalance+totAmount;
			assertEquals(5000, totAmount.intValue());
			assertEquals(di.getLaTazzaAccount().getAmount(), endBalance);
		} catch (EmployeeException e) {
			fail();
		}
		try {
			di.rechargeAccount(di.getEmployeesId().get(0), -1);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		try {
			di.rechargeAccount(di.getEmployeesId().get(1) + 1, 5000);
			fail();

		} catch (EmployeeException e) {
			// OK
		}

		try {
			di.rechargeAccount(di.getEmployeesId().get(1) + 1, -1);
			fail();
		} catch (EmployeeException e) {
			// OK
		}

		try {
			di.rechargeAccount(-1, 5000);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		try {
			di.rechargeAccount(-1, -1);
			fail();
		} catch (EmployeeException e) {
			// OK
		}

	}

	@Test
	public void TestReset() {

	}

	@Test
	public void TestSellCapsules() {
		DataImpl dataImpl = new DataImpl();
		dataImpl.reset();
		Integer bid, eid, previousQuantity, newQuantity;
		try {
			bid = dataImpl.createBeverage("Caffè", 50, 30);
		} catch (BeverageException be) {
			bid = 0;
		}
		try {
			eid = dataImpl.createEmployee("Rosetta", "Pagliuca");
		} catch (EmployeeException e) {
			eid = 0;
		}

		try {
			dataImpl.getLaTazzaAccount().setAmount(1000);
			dataImpl.buyBoxes(bid, 1);
		} catch (BeverageException | NotEnoughBalance e) {
			fail();
		}

		try {
			previousQuantity = dataImpl.getEmployeeBalance(eid);
			newQuantity = dataImpl.sellCapsules(eid, bid, 5, true);
			assertEquals(newQuantity.intValue(),
					previousQuantity - 5 * dataImpl.getBeverageBoxPrice(bid) / dataImpl.getBeverageCapsulesPerBox(bid));
			previousQuantity = dataImpl.getEmployeeBalance(eid);
			newQuantity = dataImpl.sellCapsules(eid, bid, 5, true);
			assertEquals(newQuantity.intValue(),
					previousQuantity - 5 * dataImpl.getBeverageBoxPrice(bid) / dataImpl.getBeverageCapsulesPerBox(bid));
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {
			fail();
		}
		try {
			previousQuantity = dataImpl.getLaTazzaAccount().getAmount();
			dataImpl.sellCapsules(eid, bid, 5, false);
			newQuantity = previousQuantity + 5 *dataImpl.getBeverageBoxPrice(bid)/ dataImpl.getBeverageCapsulesPerBox(bid);
			assertEquals(dataImpl.getLaTazzaAccount().getAmount(), newQuantity );		
		}
		catch (EmployeeException| BeverageException|NotEnoughCapsules e) {
			fail();
		}
		try {
			dataImpl.sellCapsules(eid, bid, -5, true);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e1) {
			// OK
		}
		try {
			previousQuantity = dataImpl.getEmployeeBalance(eid);
			newQuantity = dataImpl.sellCapsules(eid, bid, 100, true);
			fail();
		} catch (NotEnoughCapsules | EmployeeException | BeverageException e) {
			// OK
		}
		try {
			previousQuantity = dataImpl.getEmployeeBalance(eid);
			newQuantity = dataImpl.sellCapsules(eid, bid, 100, false);
			fail();
		} catch (NotEnoughCapsules | EmployeeException | BeverageException e) {
			// OK
		}

		try {
			dataImpl.sellCapsules(-1, bid, 5, true);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {
			// TODO Auto-generated catch block
		}
		try {
			dataImpl.sellCapsules(-1, -1, 5, true);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {

		}
		try {
			dataImpl.sellCapsules(eid, -1, 5, true);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {

		}

		try {
			dataImpl.sellCapsules(-1, bid, 5, false);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {
			// TODO Auto-generated catch block
		}
		try {
			dataImpl.sellCapsules(-1, -1, 5, false);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {

		}
		try {
			dataImpl.sellCapsules(eid, -1, 5, false);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {

		}

		try {
			dataImpl.sellCapsules(-1, bid, 100, true);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {
			// TODO Auto-generated catch block
		}
		try {
			dataImpl.sellCapsules(-1, -1, 100, true);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {

		}
		try {
			dataImpl.sellCapsules(eid, -1, 100, true);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {

		}

		try {
			dataImpl.sellCapsules(-1, bid, 100, false);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {
			// TODO Auto-generated catch block
		}
		try {
			dataImpl.sellCapsules(-1, -1, 100, false);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {

		}
		try {
			dataImpl.sellCapsules(eid, -1, 100, false);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {

		}
		//Added 28/95
		try {
			dataImpl.sellCapsules(eid, bid, -100, false);
			fail();
		} catch (EmployeeException | BeverageException | NotEnoughCapsules e) {		
		}		
		//Added 28/95
		try {
			dataImpl.sellCapsules(null, bid, 5, true);
			fail();
		}
		catch(EmployeeException e) {
		}
		catch (BeverageException e) {
			fail();
		}
		catch (NotEnoughCapsules e) {
			fail();
		}
		//Added 28/95
		try {
			dataImpl.sellCapsules(eid, null, 5, true);
			fail();
		}
		catch(EmployeeException e) {
			fail();
		}
		catch (BeverageException e) {
		}
		catch (NotEnoughCapsules e) {
			fail();
		}
		//Added 28/95
		try {
			dataImpl.sellCapsules(eid, bid, null, true);
			fail();
		}
		catch(EmployeeException e) {
			fail();
		}
		catch (BeverageException e) {
			fail();
		}
		catch (NotEnoughCapsules e) {
			
		}
		
	}

	@Test
	public void TestSellCapsulesToVisitor() {
		DataImpl di = new DataImpl();
		di.reset();

		Integer bid = 0;
		try {
			bid = di.createBeverage("Coffee", 50, 25);
		} catch (BeverageException e) {
			// TODO Auto-generated catch block
			fail();
		}
		di.getLaTazzaAccount().setAmount(50);
		try {
			di.buyBoxes(bid, 1);
		} catch (BeverageException e) {
			// TODO Auto-generated catch block
			fail();
		} catch (NotEnoughBalance e) {
			// TODO Auto-generated catch block
			fail();
		}
		try {
			di.sellCapsulesToVisitor(bid, 2);
			assertEquals(26, di.getLaTazzaAccount().getAmount().intValue());
			assertEquals(48, di.getBeverageCapsules(bid).intValue());
		} catch (BeverageException | NotEnoughCapsules e) {
			fail();
		}
		try {
			di.sellCapsulesToVisitor(bid, 3000);
			fail();
		} catch (BeverageException | NotEnoughCapsules e) {
		
		}
		try {
			di.sellCapsulesToVisitor(bid + 1, 1);
			fail();
		} catch (BeverageException | NotEnoughCapsules e) {
			
		}
		try {
			di.sellCapsulesToVisitor(bid + 1, 3000);
			fail();
		} catch (BeverageException | NotEnoughCapsules e) {
			
		}
		try {
			di.sellCapsulesToVisitor(bid, -1);
			fail();
		} catch (BeverageException | NotEnoughCapsules e) {
			
		}
		try {
			di.sellCapsulesToVisitor(bid + 1, -1);
			fail();
		} catch (BeverageException | NotEnoughCapsules e) {
			// TODO Auto-generated catch block
		}
		try {
			di.sellCapsulesToVisitor(-1, 1);
			fail();
		} catch (BeverageException | NotEnoughCapsules e) {
			// TODO Auto-generated catch block
		}
		try {
			di.sellCapsulesToVisitor(-1, 3000);
			fail();
		} catch (BeverageException | NotEnoughCapsules e) {
			// TODO Auto-generated catch block
		}
		try {
			di.sellCapsulesToVisitor(-1, -1);
			fail();
		} catch (BeverageException | NotEnoughCapsules e) {
			// TODO Auto-generated catch block
		}
		//Added 28/05
		try {
			di.sellCapsulesToVisitor(null, 5);
			fail();
		}
		catch(BeverageException e) {
			
		}
		catch (NotEnoughCapsules e) {
			fail();
		}
		//Added 28/05
		try {
			di.sellCapsulesToVisitor(bid, null);
			fail();
		}
		catch(BeverageException e) {
			fail();					
		}
		catch (NotEnoughCapsules e) {		
		}
	}

	@Test
	public void TestUpdateBeverage() {
		DataImpl dImpl = new DataImpl();
		dImpl.reset();
		Integer bcpb = new Integer(35);
		Integer bp = new Integer(70);
		Integer id1;
		try {
			id1 = dImpl.createBeverage("Coffee", 50, 100);
			dImpl.updateBeverage(id1, "Tea", bcpb, bp);
			assertEquals("Tea", dImpl.getBeverageName(id1));
			assertEquals(bcpb, dImpl.getBeverageCapsulesPerBox(id1));
			assertEquals(bp, dImpl.getBeverageBoxPrice(id1));
		} catch (BeverageException e) {
			fail();
		}
		dImpl.reset();
		try {
			dImpl.updateBeverage(5, "Tea", 35, 70);
			fail();
			} 
		catch (BeverageException e) {
			}
		try {
			id1 = dImpl.createBeverage("Coffee", 50, 100);
			dImpl.updateBeverage(id1, null, 35, 70);
			fail();
			} 
		catch (BeverageException e) {
			}
		dImpl.reset(); 
		try {
			id1 = dImpl.createBeverage("Coffee", 50, 100);
			dImpl.updateBeverage(id1, "Tea", -1, 70);
			fail();
			} 
		catch (BeverageException e) {
			}
		dImpl.reset();
		try { 
			id1 = dImpl.createBeverage("Coffee", 50, 100);
			dImpl.updateBeverage(id1, "Tea", 35, -1);
			fail();
			} 
		catch (BeverageException e) {
			}
		//Added 29/05
		dImpl.reset();
		try {
			id1 = dImpl.createBeverage("Coffee", 50, 100);
			dImpl.updateBeverage(null, "Tea", 35, -1);
			fail();
			} 
		catch (BeverageException e) {
			}
		dImpl.reset();
		try {
			id1 = dImpl.createBeverage("Coffee", 50, 100);
			dImpl.updateBeverage(id1, "", 35, -1);
			fail();
			} 
		catch (BeverageException e) {
			}
		dImpl.reset();
		try {
			id1 = dImpl.createBeverage("Coffee", 50, 100);
			dImpl.updateBeverage(id1, "Tea", null, -1);
			fail();
			} 
		catch (BeverageException e) {
			}
		dImpl.reset();
		try {
			id1 = dImpl.createBeverage("Coffee", 50, 100);
			dImpl.updateBeverage(id1, "Tea", 35, null);
			fail();
			} 
		catch (BeverageException e) {
			}
		//White box.
		dImpl.reset();
		try{
			dImpl.createBeverage("Coffee", 20, 40);
			dImpl.createBeverage("Tea", 20, 40);
			dImpl.createBeverage("Arabic Coffee", 20, 40);
			Integer id = dImpl.createBeverage("Milk", 20, 40);
			dImpl.updateBeverage(id, "Chocolate", 20, 40);
		}
		catch (BeverageException e) {
			fail();
		}
		

	}

	@Test
	public void TestUpdateEmployee() {
		DataImpl di = new DataImpl();
		di.reset();
		Integer eid = null, eid2=null, eid3=null;
		try {
			
			eid = di.createEmployee("Nino", "Vitale");

		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			fail();
			e.printStackTrace();
		}
		try {
			di.updateEmployee(eid, "Antonino", "Vitale"); //test Loop 1 -> just one employee in the database
			assertTrue("AntoninoVitale".equals(di.getEmployeeName(eid) + di.getEmployeeSurname(eid)));
		} catch (EmployeeException e) {
			fail();
		}
		
		try {
			di.updateEmployee(eid, "Nino", null);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		try {
			di.updateEmployee(eid, null, "Vitale");
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		try {
			di.updateEmployee(eid, null, null);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		
		try {
			di.updateEmployee(eid + 1, "Nino", null);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		try {
			di.updateEmployee(eid + 1, null, "Vitale");
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		try {
			di.updateEmployee(eid + 1, null, null);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		
		try {
			di.updateEmployee(-1, "Nino", null);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		try {
			di.updateEmployee(-1, null, "Vitale");
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		try {
			di.updateEmployee(-1, null, null);
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		try {
			di.updateEmployee(-1, "Nino", "Vitale");
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		//Added 29/05
		try {
			di.updateEmployee(eid, "", "Vitale");
			fail();
		} catch (EmployeeException e) {
			// OK
		}
		//Added 29/05
		try {
			di.updateEmployee(eid, "Nino", "");
			fail();
		} catch (EmployeeException e) {
			// OK
		}
	
		try {
			eid2=di.createEmployee("George", "Nespresso");
			di.updateEmployee(eid2,"George", "Ns");
			eid3=di.createEmployee("Pinco", "Pallo");
			di.updateEmployee(eid3,"Pinci", "Palla"); //test loop 2+
		} catch (EmployeeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		FileWriter writer;
		try {
			writer = new FileWriter("./db/Colleague.json");
			writer.write("[]"); //test Loop 0
			writer.close();
		} catch (IOException e) {		
		}
		try {
			di.updateEmployee(eid, "Nino", "V");
		} catch (EmployeeException e) {
			
		}
	}

}
