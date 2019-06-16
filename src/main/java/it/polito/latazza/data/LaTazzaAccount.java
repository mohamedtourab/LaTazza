package it.polito.latazza.data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class LaTazzaAccount {
	
	private static final String boxPurchasePath = "./db/BoxPurchase.json";
	private static final String consumptionPath = "./db/Consumption.json";
	private static final String rechargePath = "./db/Recharge.json";
	private static final String laTazzaAccountPath = "./db/LaTazzaAccount.json";

 
	private Integer amount;
	private List<Transaction> transactions = new ArrayList<>();

	public LaTazzaAccount() {
		this.amount=0; //CHANGE -> before amount = 5000
	}
	public Integer getAmount() {
		return amount;
	}

	@SuppressWarnings("unchecked")
	public void setAmount(Integer amount) {
		if(amount < 0) {
			return;
		}
		this.amount = amount;
		JSONParser parser = new JSONParser();
		JSONArray array;
		JSONObject jsonObject;
		try {
			array = (JSONArray) parser.parse(new FileReader(laTazzaAccountPath));
			if(array.size() > 0)
				jsonObject = (JSONObject) array.get(0);
			else
				jsonObject = new JSONObject();
			array.clear();
			array.add(jsonObject);
			jsonObject.put("amount", amount);
			FileWriter writer=new FileWriter(laTazzaAccountPath);
			writer.write(array.toJSONString());
			writer.close();
		} catch (IOException | ParseException e) {
		}
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	@SuppressWarnings("unchecked")
	public void addTransaction(Transaction tr) {
		if (tr== null )
			return;
		transactions.add(tr);
		

		
		JSONObject jsonObject = new JSONObject();		
		JSONParser jsonParser = new JSONParser();
			
		jsonObject.put("date", tr.getDate().getTime());
		jsonObject.put("amount", tr.getAmount());
		try {
		if(tr instanceof Consumption) {
				jsonObject.put("isCash", ((Consumption) tr).getIsCash() ? 1 : 0);
				jsonObject.put("capsuleId", ((Consumption) tr).getCapsuleId());
				jsonObject.put("capsulesNumber", ((Consumption)tr).getCapsulesNumber());
				jsonObject.put("employeeId", ((Consumption)tr).getEmployeeId());
				jsonObject.put("isVisitor", ((Consumption) tr).getIsVisitor() ? 1 : 0);
				JSONArray array = (JSONArray) jsonParser.parse(new FileReader(consumptionPath));
				array.add(jsonObject);
				FileWriter writer=new FileWriter(consumptionPath);
				writer.write(array.toJSONString());
				writer.close();
		 
		}
		else if(tr instanceof BoxPurchase) {
				jsonObject.put("capsuleType", ((BoxPurchase) tr).getCapsuleType());
				jsonObject.put("quantity", ((BoxPurchase)tr).getQuantity());
				JSONArray array = (JSONArray) jsonParser.parse(new FileReader(boxPurchasePath));
				array.add(jsonObject);
				FileWriter writer=new FileWriter(boxPurchasePath);
				writer.write(array.toJSONString());
				writer.close();
			
		}else if(tr instanceof Recharge) {
			
				jsonObject.put("employeeId", ((Recharge)tr).getEmployeeId());
				JSONArray array = (JSONArray) jsonParser.parse(new FileReader(rechargePath));
				array.add(jsonObject);
				FileWriter writer=new FileWriter(rechargePath);
				writer.write(array.toJSONString());
				writer.close();			
			}
		} catch(Exception e) {
		}
	}

}
