package it.polito.latazza.data;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.gui.MainSwing;

public class TestGUI {

	public static void main(String[] args) {
		DataInterface data = new DataImpl();
		data.reset();
		Integer eid = 0, bid = 0, eid2;
		try {
			eid = data.createEmployee("Test", "Test");
			eid2=data.createEmployee("Test2", "Test2");
			data.rechargeAccount(eid, 1000);
			data.rechargeAccount(eid2, 4000);
			bid = data.createBeverage("Test", 50, 2500);
			data.buyBoxes(bid, 1);
		} catch (EmployeeException | BeverageException | NotEnoughBalance e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new MainSwing(data);
	}

}
