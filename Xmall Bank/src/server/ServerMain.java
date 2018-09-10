package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import server.dao.AccountDBAccess;
import server.dao.IAccountData;
import server.dao.IPersonData;
import server.dao.PersonDBAccess;
import shared.IServer;
import shared.model.Person;

public class ServerMain implements IServer {
	IPersonData personDB;
	IAccountData accountDB;
	public ServerMain() throws RemoteException
	{
		personDB = new PersonDBAccess();
		accountDB = new AccountDBAccess();
		UnicastRemoteObject.exportObject(this, 0);
	}
	
	/**
	 * Main method for running the server
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
			LocateRegistry.createRegistry(1099);
			IServer server = new ServerMain();
			Naming.rebind("bankingServer", server);
			System.out.println("Starting server....");

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public Person logIn(String e_mail, String pass) throws RemoteException, MalformedURLException, NotBoundException
	{
		Person person = personDB.logIn(e_mail, pass);
		if(person != null && !(person.getType().equals(Person.ADMIN)))
		{
			person.setAccount(accountDB.getAccount(person.getE_mail()));
		}
		return person;
	}

	@Override
	public boolean createAccount(Person person) throws RemoteException, MalformedURLException, NotBoundException
	{
		boolean result = personDB.registerPerson(person);
		if(result == true && !(person.getType().equals(Person.ADMIN)))
		{
			result = accountDB.createAccount(person.getE_mail(), person.getAccount());
		}
		return result;
	}

	@Override
	public boolean updateBalance(String accountNumber, double amount)
			throws RemoteException, MalformedURLException, NotBoundException
	{
		return accountDB.updateBalance(accountNumber, amount);
	}


}
