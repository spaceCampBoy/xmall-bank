package shared.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Account implements Serializable{
	private String accountNumber;
	private double balance;
	public Account() {}
	
	public Account( double balance)
	{
		this.accountNumber = null;
		this.balance = balance;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public double getBalance()
	{
		return balance;
	}

	public void setBalance(double balance)
	{
		this.balance = balance;
	}
	
	public boolean equals(Object obj)
	{
		if(! (obj instanceof Account))
		{
			return false;
		}
		else
		{
			Account other = (Account)obj;
			return accountNumber.equals(other.accountNumber) && balance == other.balance;
		}
	}
}
