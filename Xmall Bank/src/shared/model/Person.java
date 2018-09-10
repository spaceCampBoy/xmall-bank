package shared.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Person implements Serializable{
	private Account account;
	private String name;
	private String e_mail;
	public static String ADMIN = "Admin"; 
	public static String CLERK = "Clerk"; 
	public static String CUSTOMER = "Customer"; 
	private String type;
	
	public Person() {}

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getE_mail()
	{
		return e_mail;
	}

	public void setE_mail(String e_mail)
	{
		this.e_mail = e_mail;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
	
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Person))
		{
			return false;
		}
		else
		{
			Person other = (Person)obj;
			return account.equals(other.account) && name.equals(other.name) && e_mail.equals(other.e_mail) && type.equals(other.type);
		}
	}
	
}
