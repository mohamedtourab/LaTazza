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

public class CapsuleType {
	
	private Integer beverageId;
	private String name;
	private Integer priceInCents;
	private Integer capsulesPerBox;
	private Integer boxPrice;
	private Integer quantity = 0;
	private Integer newQuantity = 0;
	private Integer newCapsulesPerBox = 0;
	private Integer newBoxPrice = 0;
	private List<BoxPurchase> boxPurchases = new ArrayList<BoxPurchase>();
	
	private static final String capsuleTypePath = "./db/CapsuleType.json";

	
	public static Integer num_beverages=new Integer(0);
	


	
	public CapsuleType(Integer id, String name, Integer capsulesPerBox, Integer boxPrice, Integer quantity, Integer ncpb, Integer nq, Integer nbp) {
		this.beverageId=id;
		this.name = name;
		this.capsulesPerBox = capsulesPerBox;
		this.boxPrice = boxPrice;
		this.priceInCents=100*boxPrice/capsulesPerBox;
		this.quantity = quantity;
		this.newBoxPrice = nbp;
		this.newCapsulesPerBox = ncpb;
		if(nq == 0 && ncpb !=0) {
			this.capsulesPerBox = ncpb;
			this.boxPrice = nbp;
			this.priceInCents=100*this.boxPrice/this.capsulesPerBox;
		}
		else {
			this.newQuantity = nq;
		}
	} 
	
	public CapsuleType(String name, Integer capsulesPerBox, Integer boxPrice) {
		this.beverageId=num_beverages;
		num_beverages++;
		this.name = name;
		this.capsulesPerBox = capsulesPerBox;
		this.boxPrice = boxPrice;
		this.priceInCents=100*boxPrice/capsulesPerBox;		
	}
	public Integer getBeverageId() {
		return this.beverageId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if(name == null) {
			return;
		}
		this.name = name;
	}
	public Integer getPrice() {
		return priceInCents;
	}
	public void setPrice(Integer price) {
		if(price<=0){
			return;
		}
		this.priceInCents = price;
	}
	public Integer getCapsulesPerBox() {
		return capsulesPerBox;
	}
	public void setCapsulesPerBox(Integer capsulesPerBox) {
		if(capsulesPerBox <= 0) {
			return;
		}
		this.capsulesPerBox = capsulesPerBox;
	}
		
	public Integer getBoxPrice() {
		return boxPrice;
	}
	
	public Integer getNumBeverage() {
		return num_beverages;		
	}
	
	public void setBoxPrice(Integer boxPrice) {
		if(boxPrice <= 0) {
			return;
		}
		this.boxPrice = boxPrice;
	}
	public List<BoxPurchase> getBoxPurchases() {
		return boxPurchases;
	}
	
	public Integer getQuantity() {
		return this.quantity + newQuantity;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void setQuantity(Integer quantity) {
		if(quantity < 0) {
			return;
		}
		this.quantity = quantity;
		JSONParser parser = new JSONParser();
		JSONObject jsonObject=new JSONObject();
		JSONArray array;
		try {
			array = (JSONArray) parser.parse(new FileReader(capsuleTypePath));
			for(int i = 0 ; i < array.size(); i++) {
				jsonObject = (JSONObject) array.get(i);
				if (Integer.parseInt(jsonObject.get("beverageId").toString()) == beverageId) {
					jsonObject.put("quantity", quantity);
					break;
				}
					
			}
			FileWriter writer=new FileWriter(capsuleTypePath);
			writer.write(array.toJSONString());
			writer.close();
		} catch (IOException | ParseException e) {//do not test
		}
	}
	public void addBoxPurchase(BoxPurchase bp) {
		if(bp == null) {
			return;
		}
		this.boxPurchases.add(bp);
	}
	
	public void setNumBeverage(Integer num) {
		if(num <=0) {
			return;
		}
		num_beverages = num;
	}
	
	public Integer getNewQuantity() {
		return newQuantity;
	}
	
	public Integer getNewCapsulesPerBox() {
		return newCapsulesPerBox;
	}
	
	public void setNewCapsulesPerBox(Integer newCapsulesPerBox) {
		this.newCapsulesPerBox=newCapsulesPerBox;
	}
	
	public void setNewBoxPrice(Integer newBoxPrice) {
		this.newBoxPrice=newBoxPrice;
	}
	
	public void setNewQuantity(Integer nq) {
		if(nq < 0 )
			return;
		newQuantity = nq;
		JSONParser parser = new JSONParser();
		JSONObject jsonObject=new JSONObject();
		JSONArray array;
		try {
			array = (JSONArray) parser.parse(new FileReader(capsuleTypePath));
			for(int i = 0 ; i < array.size(); i++) {
				jsonObject = (JSONObject) array.get(i);
				if (Integer.parseInt(jsonObject.get("beverageId").toString()) == beverageId) {
					jsonObject.put("newQuantity", nq);
					break;
				}
					
			}
			FileWriter writer=new FileWriter(capsuleTypePath);
			writer.write(array.toJSONString());
			writer.close();
		} catch (IOException | ParseException e) {//do not test

		}
	}
	public Integer getNewBoxPrice() {
		return newBoxPrice;
	}
	
	public void setNewCapsule() {
		this.boxPrice = newBoxPrice;
		this.capsulesPerBox = newCapsulesPerBox;
		this.quantity = newQuantity;
		this.priceInCents = 100*boxPrice/capsulesPerBox;
		newBoxPrice = 0;
		newCapsulesPerBox = 0;
		newQuantity = 0;
		setNewQuantity(0);
	}
	
}
