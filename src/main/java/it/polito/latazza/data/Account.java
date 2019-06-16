package it.polito.latazza.data;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import it.polito.latazza.data.Transaction.TransactionType;

public class Account {
	private Integer employeeId;
	private Integer amount;
	private List<Transaction> transactions = new ArrayList<>();

	
	
	
	public Account(Integer employeeId, Integer amount) {
		this.employeeId = employeeId;
		this.amount = amount;
	}



	public Integer getEmployeeId() {
		return employeeId;
	}

	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	List<Transaction> getTransactions(){
		return transactions;
	}
	
	public Transaction setNewTransaction(Integer amount, Date date, TransactionType type, Integer capsuleId, Integer capsulesNumber, boolean isCash) {
		Transaction newTransaction = null;
		Integer relativeAmount = null;
		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
			try {
				if (amount < 0 || date.before(formatter.parse("01/01/2015")) || date.after(formatter.parse("01/01/2025")))
					return null;
			} catch (ParseException e) {
			}

		switch(type) {
			case RECHARGE:
				relativeAmount = amount;
				newTransaction = new Recharge(date, amount, this.employeeId);
				break;
			case  CONSUMPTION:
				if (capsuleId == null || capsulesNumber == null || capsulesNumber<0 || capsuleId < 0)
					return null;
				relativeAmount = -amount;
				newTransaction = new Consumption(date, amount, isCash, capsuleId, capsulesNumber, this.employeeId, false);
				break;
			default:
				return null;
		}
		transactions.add(newTransaction);
		if(isCash == false)
			this.setAmount(this.amount+relativeAmount);
		return newTransaction;
	}
	
	public void setTransaction(Transaction tr) {
		this.transactions.add(tr);
	}
	
	
	
	
}
