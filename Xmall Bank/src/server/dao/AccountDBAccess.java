package server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import shared.model.Account;

public class AccountDBAccess implements IAccountData {
	private Connection connection;
	private PreparedStatement pStatement;
	private String sql, driver, url, user, pw;
	private ResultSet resultSet;

	public AccountDBAccess()
	{
		/*
		 * Setting up a connection to the Database 1. Loading JDBC driver 2. URL for
		 * database 3. Username to database 4. password to database
		 */

		driver = "org.postgresql.Driver";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		url = "jdbc:postgresql://localhost:5432/postgres";
		user = "postgres";
		 pw = "postgres";

		sql = "";
		pStatement = null;
		resultSet = null;
		

	}

	private void openConnection() throws SQLException
	{
		connection = DriverManager.getConnection(url, user, pw);
		connection.setSchema("SDJ3");
	}

	private void closeConnection()
	{
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean createAccount(String email, Account account)
	{
		sql = "INSERT INTO Account (email,balance)" + " VALUES (?, ?)";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, email);
			pStatement.setDouble(2, account.getBalance());
			pStatement.executeUpdate();
			System.out.println("Account added to Database.");
			return true;
		} catch (SQLException e) {

			e.printStackTrace();
			if (e.getErrorCode() == 23505) {
				return false;
			} else {
				return false;
			}
		} finally {
			closeConnection();
		}
	}

	@Override
	public boolean updateBalance(String accountNumber, double balance)
	{
		sql = "UPDATE account set balance = balance + ( ? )  WHERE id = ?";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setDouble(1, balance);
			pStatement.setInt(2, Integer.parseInt(accountNumber));
			pStatement.executeUpdate();
			System.out.println("Account balance update in Database.");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeConnection();
		}
	}

	@Override
	public Account getAccount(String email)
	{
		Account account = null;
		sql = ("SELECT * FROM account" + " WHERE email = ?");
		try {
			System.out.println("checking in database");
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, email);
			resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
				account = new Account();
				account.setAccountNumber(resultSet.getString("id"));
				account.setBalance(resultSet.getDouble("balance"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return account;
	}
}
