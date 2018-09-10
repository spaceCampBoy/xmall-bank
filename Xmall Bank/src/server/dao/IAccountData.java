package server.dao;

import shared.model.Account;

public interface IAccountData {
	boolean createAccount(String email, Account account);
	Account getAccount(String email);
	boolean updateBalance(String accountNumber, double balance);
}
