package it.polito.latazza.data;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import it.polito.latazza.data.Transaction.TransactionType;

public class Colleague {
	private Integer employeeId;
	private String name;
	private String surname;
	private transient Account account;
	private static final String accountPath = "./db/Account.json";
	
	public static Integer num_colleague=new Integer(0);

	
	@SuppressWarnings("unchecked")
	public Colleague(String name, String surname){
		this.employeeId=num_colleague;
		num_colleague++;
		this.name = name;
		this.surname = surname;
		account=new Account(this.employeeId, 0);
		JSONParser parser = new JSONParser();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("employeeId", employeeId);
		jsonObject.put("amount", 0);
		try {
			JSONArray array = (JSONArray) parser.parse(new FileReader(accountPath));
			array.add(jsonObject);
			FileWriter writer=new FileWriter(accountPath);
			writer.write(array.toJSONString());
			writer.close();
		} catch (IOException | ParseException e) { //do not test
			// TODO Auto-generated catch block
		}
	}
	
	public Colleague(Integer employeeId, String name, String surname) {
		this.employeeId = employeeId;
		this.name = name;
		this.surname = surname;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		if(account == null) {
			return;		
		}
		this.account = account;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}
	
	public String getName() {
		return name;
	}
	public Integer getNumColleague() {
		return num_colleague;
	}
	public void setName(String name) {
		if(name == null) {
			return;
		}
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		if(surname == null) {
			return;
		}
		this.surname = surname;
	}

	public Transaction buyCapsules(Integer price, Integer num, Integer capsuleId, boolean isCash) {
		if(price <= 0 || num <=0 || capsuleId <0){
			return null;
		}
		Integer totAmount=price*num/100;
		Transaction transaction=account.setNewTransaction(totAmount, new Date(System.currentTimeMillis()), TransactionType.CONSUMPTION, capsuleId, num, isCash);
		updateJsonAccount();
		return transaction;
	}
	
	public Transaction buyCapsules(Integer oldPrice, Integer oldNum, Integer newPrice, Integer newNum, Integer capsuleId, boolean isCash) {
		if(oldPrice <= 0 || oldNum <=0 || capsuleId <0){
			return null;
		}
		Integer totAmount=(oldPrice*oldNum + newPrice*newNum)/100;
		Transaction transaction=account.setNewTransaction(totAmount, new Date(System.currentTimeMillis()), TransactionType.CONSUMPTION, capsuleId, oldNum + newNum, isCash);
		updateJsonAccount();
		return transaction;
	}
	
	public Transaction rechargeAccount(Integer price) {
		if(price<=0) {
			return null;
		}
		Transaction newTransaction = account.setNewTransaction(price, new Date(System.currentTimeMillis()), TransactionType.RECHARGE, null, null, false);
		updateJsonAccount();
		return newTransaction;
	}

	public List<Transaction> getTransactions(){
		return this.account.getTransactions();
	}
	
	public void setNumColleague(Integer num) {
		if(num <0) {
			return;
		}
		num_colleague = num;
	}
	
	@SuppressWarnings("unchecked")
	public void updateJsonAccount() {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject=new JSONObject();
		JSONArray array;
		try {
			array = (JSONArray) parser.parse(new FileReader(accountPath));
			for(int i = 0 ; i < array.size(); i++) {
				jsonObject = (JSONObject) array.get(i);
				if (Integer.parseInt(jsonObject.get("employeeId").toString()) == employeeId) {
					jsonObject.put("amount", account.getAmount());
					break;
				}
					
			}
			FileWriter writer=new FileWriter(accountPath);
			writer.write(array.toJSONString());
			writer.close();
		} catch (IOException | ParseException e) {

		}
	}
}