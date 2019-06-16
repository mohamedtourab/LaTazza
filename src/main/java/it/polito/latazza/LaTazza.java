package it.polito.latazza;

import it.polito.latazza.gui.MainSwing;
import it.polito.latazza.data.*;

public class LaTazza {
	
	public static void main(String[] args) {
		DataInterface data = new DataImpl();
		new MainSwing(data);
	}

}
