package shared;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import shared.model.Person;

public interface IServer extends Remote{
	Person logIn(String e_mail, String pass)throws RemoteException, MalformedURLException, NotBoundException;
	boolean createAccount(Person person)throws RemoteException, MalformedURLException, NotBoundException;
	boolean updateBalance(String accountNumber, double amount)throws RemoteException, MalformedURLException, NotBoundException;
	

}
