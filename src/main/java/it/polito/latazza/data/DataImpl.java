package it.polito.latazza.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;

import it.polito.latazza.exceptions.BeverageException;
import it.polito.latazza.exceptions.DateException;
import it.polito.latazza.exceptions.EmployeeException;
import it.polito.latazza.exceptions.NotEnoughBalance;
import it.polito.latazza.exceptions.NotEnoughCapsules;


public class DataImpl implements DataInterface {
	
	private LaTazzaAccount account=new LaTazzaAccount();;
	private Map<Integer, CapsuleType> capsules=new HashMap<>();
	private Map<Integer, Colleague> colleagues=new HashMap<>();
	private static final String accountPath = "./db/Account.json";
	private static final String colleaguePath = "./db/Colleague.json";
	private static final String boxPurchasePath = "./db/BoxPurchase.json";
	private static final String capsuleTypePath = "./db/CapsuleType.json";
	private static final String consumptionPath = "./db/Consumption.json";
	private static final String laTazzaAccountPath = "./db/LaTazzaAccount.json";
	private static final String rechargePath = "./db/Recharge.json";
	
	@SuppressWarnings("unchecked")
	public DataImpl() {
		List<Account> accounts = new ArrayList<Account>();
		List<Colleague> colleagues = new ArrayList<Colleague>();
		List<BoxPurchase> boxes = new ArrayList<BoxPurchase>();
		List<CapsuleType> cTypes = new ArrayList<CapsuleType>();
		List<Consumption> consumptions = new ArrayList<Consumption>();
		LaTazzaAccount accountLT = null;
		List<Recharge> recharges = new ArrayList<Recharge>();

		
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;
		JSONArray array;
		FileWriter writer;
		

			
	try {	
			if(!new File("db").exists()) {
			 new File("db").mkdir();
			}
		
			if (!new File(laTazzaAccountPath).exists()) {
			writer=new FileWriter(laTazzaAccountPath);
			writer.write("[]");
			writer.close();
		}
		if (!new File(accountPath).exists()) {
			writer=new FileWriter(accountPath);
			writer.write("[]");
			writer.close();
		}
		if (!new File(colleaguePath).exists()) {
			writer=new FileWriter(colleaguePath);
			writer.write("[]");
			writer.close();
		}
		if (!new File(boxPurchasePath).exists()) {
			writer=new FileWriter(boxPurchasePath);
			writer.write("[]");
			writer.close();
		}
		if (!new File(capsuleTypePath).exists()) {
			writer=new FileWriter(capsuleTypePath);
			writer.write("[]");
			writer.close();
		}
		if (!new File(consumptionPath).exists()) {
			writer=new FileWriter(consumptionPath);
			writer.write("[]");
			writer.close();
		}
		if (!new File(rechargePath).exists()) {
			writer=new FileWriter(rechargePath);
			writer.write("[]");
			writer.close();
		}
	} catch (Exception e ) {
		
	}

		try {
			array = (JSONArray) parser.parse(new FileReader(laTazzaAccountPath));
			if(array.size() > 0) {
				jsonObject = (JSONObject) array.get(0);
				accountLT = new LaTazzaAccount();
				accountLT.setAmount(Integer.parseInt(jsonObject.get("amount").toString()));
			}
			else {
				accountLT = new LaTazzaAccount();
				jsonObject=new JSONObject();
				jsonObject.put("amount", accountLT.getAmount());
				array = (JSONArray) parser.parse(new FileReader(laTazzaAccountPath));
				array.add(jsonObject);
				writer=new FileWriter(laTazzaAccountPath);
				writer.write(array.toJSONString());
				writer.close();	
			}
			array = (JSONArray) parser.parse(new FileReader(accountPath));
			
			for(int i = 0 ; i < array.size(); i++) {
				jsonObject = (JSONObject) array.get(i);
				Account a = new Account(Integer.parseInt(jsonObject.get("employeeId").toString()), Integer.parseInt(jsonObject.get("amount").toString()));
				accounts.add(a);
			}
			array = (JSONArray) parser.parse(new FileReader(colleaguePath));
			
			for(int i = 0 ; i < array.size(); i++) {
				jsonObject = (JSONObject) array.get(i);
				Colleague a = new Colleague(Integer.parseInt(jsonObject.get("employeeId").toString()),jsonObject.get("name").toString(), jsonObject.get("surname").toString());
				a.setAccount(accounts.stream().filter(b->b.getEmployeeId() == a.getEmployeeId()).findFirst().get());
				colleagues.add(a);
			}
			array = (JSONArray) parser.parse(new FileReader(capsuleTypePath));
			
			for(int i = 0 ; i < array.size(); i++) {
				jsonObject = (JSONObject) array.get(i);
				CapsuleType a = new CapsuleType(Integer.parseInt(jsonObject.get("beverageId").toString()),
												jsonObject.get("name").toString(),
												Integer.parseInt(jsonObject.get("capsulesPerBox").toString()),
												Integer.parseInt(jsonObject.get("boxPrice").toString()),
												Integer.parseInt(jsonObject.get("quantity").toString()),
												Integer.parseInt(jsonObject.get("newCapsulesPerBox").toString()),
												Integer.parseInt(jsonObject.get("newQuantity").toString()),
												Integer.parseInt(jsonObject.get("newBoxPrice").toString()));
				cTypes.add(a);
			}
			
			array = (JSONArray) parser.parse(new FileReader(boxPurchasePath));
			
			for(int i = 0 ; i < array.size(); i++) {
				jsonObject = (JSONObject) array.get(i);
				BoxPurchase a = new BoxPurchase(new Date(Long.parseLong(jsonObject.get("date").toString())),
						Integer.parseInt(jsonObject.get("amount").toString()),
						Integer.parseInt(jsonObject.get("quantity").toString()),
						Integer.parseInt(jsonObject.get("capsuleType").toString()));
				 CapsuleType c = cTypes.stream().filter(b -> b.getBeverageId().compareTo(a.getCapsuleType()) == 0).findFirst().get();
			     c.addBoxPurchase(a);
			     accountLT.addTransaction(a);
					boxes.add(a);
				}
			
			array = (JSONArray) parser.parse(new FileReader(consumptionPath));
			
			for(int i = 0 ; i < array.size(); i++) {
				jsonObject = (JSONObject) array.get(i);
				Consumption a = new Consumption(new Date(Long.parseLong(jsonObject.get("date").toString())),
												Integer.parseInt(jsonObject.get("amount").toString()),
												1 == Integer.parseInt(jsonObject.get("isCash").toString()),
												Integer.parseInt(jsonObject.get("capsuleId").toString()),
												Integer.parseInt(jsonObject.get("capsulesNumber").toString()),
												jsonObject.get("employeeId") != null ? Integer.parseInt(jsonObject.get("employeeId").toString()) : -1,
												1 == Integer.parseInt(jsonObject.get("isVisitor").toString()));
				if(!a.getIsVisitor())
					accounts.stream().filter(b -> b.getEmployeeId().compareTo(a.getEmployeeId()) == 0).findFirst().get().setTransaction(a);
				accountLT.addTransaction(a);
				consumptions.add(a);
			}
			array = (JSONArray) parser.parse(new FileReader(rechargePath));
			
			for(int i = 0 ; i < array.size(); i++) {
				jsonObject = (JSONObject) array.get(i);
				Recharge a = new Recharge(new Date(Long.parseLong(jsonObject.get("date").toString())),
											Integer.parseInt(jsonObject.get("amount").toString()),
											Integer.parseInt(jsonObject.get("employeeId").toString()));
				accounts.stream().filter(b -> b.getEmployeeId().compareTo(a.getEmployeeId()) == 0).findFirst().get().setTransaction(a);				
				accountLT.addTransaction(a);
				recharges.add(a);
			}
			
				
		}
		catch(Exception e) {
		}
		if(cTypes.size() > 0) {
			cTypes.get(0).setNumBeverage(cTypes.size());
			cTypes.forEach(a -> this.capsules.put(a.getBeverageId(), a));
		}
		if(colleagues.size() > 0) {
			colleagues.get(0).setNumColleague(colleagues.size());
			colleagues.forEach(a -> this.colleagues.put(a.getEmployeeId(), a));
			}
		this.account = accountLT;
		
	}
 
	@Override
	public Integer sellCapsules(Integer employeeId, Integer beverageId, Integer numberOfCapsules, Boolean fromAccount)
			throws EmployeeException, BeverageException, NotEnoughCapsules {
		if(beverageId == null) {
			throw new BeverageException();
		}
		CapsuleType capsuleType = capsules.get(beverageId);
		Transaction newTransaction;
		if (capsuleType == null) 
			throw new BeverageException();
		if (numberOfCapsules == null || capsuleType.getQuantity() < numberOfCapsules || numberOfCapsules < 0) 
			throw new NotEnoughCapsules();
		Colleague colleague = colleagues.get(employeeId);
			if (colleague == null) 
				throw new EmployeeException();
		Integer oldQuantity=capsuleType.getQuantity()-capsuleType.getNewQuantity();
		Integer left;
		Integer amount;
		if (oldQuantity >= numberOfCapsules) {
			capsuleType.setQuantity(oldQuantity - numberOfCapsules);
			newTransaction =colleague.buyCapsules(capsuleType.getPrice(), numberOfCapsules, capsuleType.getBeverageId(), !fromAccount);
			amount=capsuleType.getPrice()*numberOfCapsules/100;
		}else {
			left=numberOfCapsules-oldQuantity;
			Integer oldPrice=capsuleType.getPrice();
			capsuleType.setNewCapsule();
			capsuleType.setQuantity(capsuleType.getQuantity() - left);
			newTransaction =colleague.buyCapsules(oldPrice, oldQuantity, capsuleType.getPrice(), left,  capsuleType.getBeverageId(), !fromAccount);
			amount=(oldPrice*oldQuantity+capsuleType.getPrice()*left)/100;
			}
			
		account.addTransaction(newTransaction);	
		if (fromAccount == false) 
			account.setAmount(account.getAmount() + amount);	
		return colleague.getAccount().getAmount();
	} 

	@Override
	public void sellCapsulesToVisitor(Integer beverageId, Integer numberOfCapsules)
			throws BeverageException, NotEnoughCapsules {
		if(beverageId == null ||beverageId < 0) //beverageId == null
			throw new BeverageException();
		CapsuleType capsuleType = capsules.get(beverageId);
		if (capsuleType == null ) 
			throw new BeverageException();
		if (numberOfCapsules == null || capsuleType.getQuantity() < numberOfCapsules || numberOfCapsules < 0) 
			throw new NotEnoughCapsules();
		Integer oldQuantity=capsuleType.getQuantity()-capsuleType.getNewQuantity();
		Integer amount, left;
		if (oldQuantity >= numberOfCapsules) {
			capsuleType.setQuantity(oldQuantity - numberOfCapsules);
			amount=capsuleType.getPrice()*numberOfCapsules/100;
		}else {
			left=numberOfCapsules-oldQuantity;
			Integer oldPrice=capsuleType.getPrice();
			capsuleType.setNewCapsule();
			capsuleType.setQuantity(capsuleType.getQuantity()-left);
			amount=(oldPrice*oldQuantity+capsuleType.getPrice()*left)/100;
			}
		account.addTransaction(new Consumption(new Date(System.currentTimeMillis()), amount, true, beverageId, numberOfCapsules, null, true));
		account.setAmount(account.getAmount() + amount);
	}

	@Override
	public Integer rechargeAccount(Integer id, Integer amountInCents) throws EmployeeException{
		Colleague coll = colleagues.get(id);
		if (coll == null || amountInCents <= 0)
			throw new EmployeeException();
		Transaction newTransaction = coll.rechargeAccount(amountInCents);
		account.setAmount(account.getAmount() + amountInCents); //CHANGE -> this is updated
		this.account.addTransaction(newTransaction);
		return coll.getAccount().getAmount();
	}

	@Override
	public void buyBoxes(Integer beverageId, Integer boxQuantity) throws BeverageException, NotEnoughBalance {
		CapsuleType cp = capsules.get(beverageId);
		if (cp == null)
			throw new BeverageException();

		Integer amount = (cp.getNewCapsulesPerBox() == 0) ? cp.getBoxPrice() * boxQuantity : cp.getNewBoxPrice() * boxQuantity;	
		if (amount > account.getAmount())
			throw new NotEnoughBalance();
		if (boxQuantity > 0) {
		if(cp.getNewCapsulesPerBox() > 0) { //it means that capsule type has been updated
			cp.setNewQuantity(cp.getNewQuantity() + cp.getNewCapsulesPerBox()*boxQuantity);
		}
		else
			cp.setQuantity(cp.getQuantity() + cp.getCapsulesPerBox()*boxQuantity);
		Date date = new Date(System.currentTimeMillis());
		Transaction box = new BoxPurchase(date, amount, boxQuantity, beverageId);
		account.setAmount(account.getAmount() - amount);
		account.addTransaction(box);
		}
	}


	@Override
	public List<String> getEmployeeReport(Integer employeeId, Date startDate, Date endDate)
			throws EmployeeException, DateException {
		Colleague employee;
		if(startDate == null || endDate == null || startDate.after(endDate) || startDate.after(new Date(System.currentTimeMillis())))
			throw new DateException();
		if((employee = colleagues.get(employeeId)) == null)
				throw new EmployeeException();
		
		return employee.getTransactions().stream().filter(a -> (a.getDate().before(endDate) && a.getDate().after(startDate)) ).map(a -> {
			String newVal, datetime, type, emp, amount, bName, nCaps;
			
			datetime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(a.getDate());
			emp = employee.getName() + " " + employee.getSurname();

			if(a instanceof Recharge) {
				type = "RECHARGE";
				amount = String.format(Locale.US, "%.2f \u20ac", (float)a.getAmount() / 100.0);
				newVal = datetime + " " + type + " " + emp + " " + amount;
			}
			else {
				if(((Consumption)a).getIsCash())
					type = "CASH";
				else
					type = "BALANCE";
				bName = capsules.get(((Consumption)a).getCapsuleId()).getName();
				nCaps = ((Consumption)a).getCapsulesNumber().toString();
				newVal = datetime + " " + type + " " + emp + " " + bName + " " + nCaps; 
			}
			return newVal;
		}).collect(Collectors.toList());
	}

	@Override
	public List<String> getReport(Date startDate, Date endDate) throws DateException {
		if(startDate == null || endDate == null || startDate.after(endDate) || startDate.after(new Date(System.currentTimeMillis())))
			throw new DateException();
		
		if (startDate.equals(endDate)) {
			return new ArrayList<>();
		}
		return account.getTransactions().stream().filter(a -> a.getDate().before(endDate) && a.getDate().after((startDate)) ).map(a -> {
			String newVal = null, datetime, type, emp, amount, bName, nCaps;
			
			datetime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(a.getDate());

			if(a instanceof Recharge) {
				Colleague colleague = colleagues.get(((Recharge)a).getEmployeeId());
				type = "RECHARGE";
				amount = String.format(Locale.US, "%.2f \u20ac", (float)a.getAmount() / 100.0);
				emp = colleague.getName() + " " + colleague.getSurname();
				newVal = datetime + " " + type + " " + emp + " " + amount;
			}
			else if(a instanceof Consumption){
				Colleague colleague = colleagues.get(((Consumption)a).getEmployeeId());
				if(((Consumption)a).getIsCash() && !((Consumption)a).getIsVisitor()) {
					type = "CASH";
					emp = colleague.getName() + " " + colleague.getSurname() + " ";
				}
				else if (!((Consumption)a).getIsCash() && !((Consumption)a).getIsVisitor()) {
					type = "BALANCE";
					emp = colleague.getName() + " " + colleague.getSurname() + " ";
				}
				else {
					type = "VISITOR";
					emp = "";
				}
					 
				bName = capsules.get(((Consumption)a).getCapsuleId()).getName();
				nCaps = ((Consumption)a).getCapsulesNumber().toString();
				newVal = datetime + " " + type + " " + emp + bName + " " + nCaps; 
			}
			else if(a instanceof BoxPurchase) {
				type = "BUY";
				nCaps = ((BoxPurchase)a).getQuantity().toString();
				//if (capsules.size()>0)
				bName = capsules.get(((BoxPurchase)a).getCapsuleType()).getName();
				//else 
				//	bName="";
				newVal = datetime + " " + type + " " + bName + " " + nCaps;
			}
			return newVal;
		}).collect(Collectors.toList());
	}

	@Override
	public Integer createBeverage(String name, Integer capsulesPerBox, Integer boxPrice) throws BeverageException {
		if(capsulesPerBox == null || boxPrice == null|| name == null  || capsulesPerBox <= 0 || boxPrice <=0 || name.equals("")) { 
			throw new BeverageException();
		}
		CapsuleType ct = new CapsuleType(name, capsulesPerBox, boxPrice);
		if (capsules.values().stream().anyMatch(a -> a.getName().equals(ct.getName())) || (ct == null)) 
			throw new BeverageException();
		capsules.put(ct.getBeverageId(), ct);
		JSONParser parser = new JSONParser();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("beverageId", ct.getBeverageId());
		jsonObject.put("name", name);
		jsonObject.put("quantity", 0);
		jsonObject.put("price", ct.getPrice());
		jsonObject.put("capsulesPerBox", capsulesPerBox);
		jsonObject.put("boxPrice", boxPrice);
		jsonObject.put("newCapsulesPerBox", 0);
		jsonObject.put("newBoxPrice", 0);
		jsonObject.put("newQuantity", 0);
		try { 
			JSONArray array = (JSONArray) parser.parse(new FileReader(capsuleTypePath));
			array.add(jsonObject);
			FileWriter writer=new FileWriter(capsuleTypePath);
			writer.write(array.toJSONString());
			writer.close();
		} catch (ParseException | IOException e) { //do not test
		} 
		return ct.getBeverageId(); //do not test
	}

	@Override
	public void updateBeverage(Integer id, String name, Integer capsulesPerBox, Integer boxPrice)
			throws BeverageException {
		//CHANGED
		if(id == null|| capsulesPerBox==null ||boxPrice== null ||name == null ||name == "" || capsulesPerBox <=0 || boxPrice <=0) { // id == null
			throw new BeverageException();
		}
		CapsuleType ct = capsules.get(id);
		if (ct == null)
			throw new BeverageException();
		JSONParser parser = new JSONParser();
		JSONObject jsonObject=new JSONObject();
		JSONArray array;
		try {
			array = (JSONArray) parser.parse(new FileReader(capsuleTypePath));
			for(int i = 0 ; i < array.size(); i++) {
				jsonObject = (JSONObject) array.get(i);
				if (Integer.parseInt(jsonObject.get("beverageId").toString()) == id) {
					jsonObject.put("name", name);
					jsonObject.put("newCapsulesPerBox", capsulesPerBox);
					jsonObject.put("newBoxPrice", boxPrice);
					break;
				}
					
			}
			FileWriter writer=new FileWriter(capsuleTypePath);
			writer.write(array.toJSONString());
			writer.close();
		} catch (IOException | ParseException e) { //do not test
		}
		ct.setName(name);
		ct.setNewCapsulesPerBox(capsulesPerBox);
		ct.setNewBoxPrice(boxPrice);

	}

	@Override
	public String getBeverageName(Integer id) throws BeverageException {
		CapsuleType ct = capsules.get(id);
		if (ct == null)
			throw new BeverageException();
		return ct.getName();
	}

	@Override
	public Integer getBeverageCapsulesPerBox(Integer id) throws BeverageException {
		CapsuleType ct = capsules.get(id);
		if (ct == null)
			throw new BeverageException();
		Integer capsulesPerBox=  (ct.getNewCapsulesPerBox() == 0) ? ct.getCapsulesPerBox() : ct.getNewCapsulesPerBox();
			return capsulesPerBox;
	}
 
	@Override
	public Integer getBeverageBoxPrice(Integer id) throws BeverageException {
		// TODO Auto-generated method stub
		CapsuleType ct = capsules.get(id);
		if (ct == null)
			throw new BeverageException();
		Integer boxPrice= (ct.getNewCapsulesPerBox() == 0) ? ct.getBoxPrice() : ct.getNewBoxPrice();
		return boxPrice;
	}

	@Override
	public List<Integer> getBeveragesId() {
		return capsules.values().stream().map(a -> a.getBeverageId()).collect(Collectors.toList());
	}

	@Override
	public Map<Integer, String> getBeverages() {
		// TODO Auto-generated method stub
		Map<Integer, String> map_bev=new HashMap<>();
		for (Integer i: getBeveragesId())
			map_bev.put(i, capsules.get(i).getName());
		return map_bev;
	}

	@Override
	public Integer getBeverageCapsules(Integer id) throws BeverageException {
		// TODO Auto-generated method stub
		CapsuleType ct=capsules.get(id);
		if( ct ==null)
			throw new BeverageException();
		return ct.getQuantity();
	}

	@Override
	public Integer createEmployee(String name, String surname) throws EmployeeException {
		Colleague coll = new Colleague(name, surname);
		if (coll == null || name == null || surname == null || name.equals("") || surname.equals("")) // name|surname == ""
			throw new EmployeeException();
		colleagues.put(coll.getEmployeeId(), coll);

		JSONParser parser = new JSONParser();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("employeeId", coll.getEmployeeId());
		jsonObject.put("name", name);
		jsonObject.put("surname", surname);
		try {
			JSONArray array = (JSONArray) parser.parse(new FileReader(colleaguePath));
			array.add(jsonObject);
			FileWriter writer=new FileWriter(colleaguePath);
			writer.write(array.toJSONString());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return coll.getEmployeeId();
	}

	@Override
	public void updateEmployee(Integer id, String name, String surname) throws EmployeeException {		
		Colleague coll = colleagues.get(id);
		if (coll == null)
			throw new EmployeeException();
		if(name == null || surname == null || name.equals("") || surname.equals("")) // name || surname == ""
			throw new EmployeeException();
		coll.setName(name);
		coll.setSurname(surname);
		JSONParser parser = new JSONParser();
		JSONObject jsonObject=new JSONObject();
		JSONArray array;
		try {
			array = (JSONArray) parser.parse(new FileReader(colleaguePath));
			for(int i = 0 ; i < array.size(); i++) {
				jsonObject = (JSONObject) array.get(i);
				if (Integer.parseInt(jsonObject.get("employeeId").toString()) == id) {
					jsonObject.put("name", name);
					jsonObject.put("surname", surname);
					break;
				}				
			}
			FileWriter writer=new FileWriter(colleaguePath);
			writer.write(array.toJSONString());
			writer.close();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getEmployeeName(Integer id) throws EmployeeException {
		Colleague coll=colleagues.get(id);
		if (coll == null)
			throw new EmployeeException();
		return coll.getName();
	}

	@Override
	public String getEmployeeSurname(Integer id) throws EmployeeException {
		Colleague coll=colleagues.get(id);
		if (coll == null)
			throw new EmployeeException();
		return coll.getSurname();
	}

	@Override
	public Integer getEmployeeBalance(Integer id) throws EmployeeException {
		Colleague coll=colleagues.get(id);
		if (coll == null)
			throw new EmployeeException();
		return coll.getAccount().getAmount();
	}

	@Override
	public List<Integer> getEmployeesId() {
		List<Integer> idlist = new ArrayList<Integer>(colleagues.keySet());		
		return idlist;
	}

	@Override
	public Map<Integer, String> getEmployees() {
		Map<Integer, String> hashMap = new HashMap<Integer, String>();
		for (Colleague coll : colleagues.values()) {
			String concat = coll.getName()+" "+coll.getSurname();
			hashMap.put(coll.getEmployeeId(), concat);
			
		}
		return hashMap;
	}

	@Override
	public Integer getBalance() {
		// TODO Auto-generated method stub
		return account.getAmount();
	}
	
	public LaTazzaAccount getLaTazzaAccount() {
		return this.account;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		account=new LaTazzaAccount();
		capsules=new HashMap<>();
		colleagues=new HashMap<>();
		// LA TAZZA ACCOUNT NOT ANYMORE 50000 at beginning
		CapsuleType.num_beverages=0;
		Colleague.num_colleague=0;
		FileWriter writer;
		try {
			writer = new FileWriter(accountPath);
			writer.write("[]");
			writer.close();
			writer=new FileWriter(colleaguePath);
			writer.write("[]");
			writer.close();
			writer=new FileWriter(boxPurchasePath);
			writer.write("[]");
			writer.close();
			writer=new FileWriter(capsuleTypePath);
			writer.write("[]");
			writer.close();
			writer=new FileWriter(consumptionPath);
			writer.write("[]");
			writer.close();
			writer=new FileWriter(laTazzaAccountPath);
			writer.write("[]");
			writer.close();
			writer=new FileWriter(rechargePath);
			writer.write("[]");
			writer.close();
		} catch (IOException e) {
		}		
	}
	
	
}
