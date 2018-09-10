package server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import shared.model.Person;

public class PersonDBAccess implements IPersonData{
	private Connection connection;
	private PreparedStatement pStatement;
	private String sql, driver, url, user, pw;
	private ResultSet resultSet;

	public PersonDBAccess()
	{
		/*
		 *  Setting up a connection to the Database
		 *  1. Loading JDBC driver
		 *  2. URL for database
		 *  3. Username to database
		 *  4. password to database
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
//			setSearchPath();
	}

	private void closeConnection()
	{
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Person logIn(String userName, String password)
	{
		Person person = null;
		sql=("SELECT * FROM person"
				+ " WHERE email = ? AND pass = ?");
		try {
//			System.out.println("checking in database" + userName + password + sql);
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, userName);
			pStatement.setString(2, password);
			resultSet = pStatement.executeQuery();
			while(resultSet.next())
			{
				person = new Person();
				person.setE_mail(resultSet.getString("email"));
				person.setName(resultSet.getString("name"));
				person.setType(resultSet.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		finally
		{
			closeConnection();
		}
		return person;
	}

	@Override
	public boolean registerPerson(Person person)
	{
		sql = "INSERT INTO person (email,name,type,pass)"
				+ " VALUES (?, ?, ?,?)";
		try {
			openConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1,person.getE_mail());
			pStatement.setString(2, person.getName());
			pStatement.setString(3, person.getType());
			pStatement.setString(4, "0000");
			pStatement.executeUpdate();
			System.out.println("Person registered into Database.");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			closeConnection();
		}
	}
//	
//	private void setSearchPath()
//	{
//		
//		sql = "set search_path = 'SDJ3'";
//		try {
//			
//			PreparedStatement pStatement0 = connection.prepareStatement(sql);
//			
//			pStatement0.executeUpdate();
//			pStatement0.close();
//			System.out.println("Search Path for database set to SDJ3.");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	
}
