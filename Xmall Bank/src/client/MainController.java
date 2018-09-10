package client;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import client.admin.ViewAdmin;
import client.clerk.ViewClerk;
import client.customer.ViewCustomer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import shared.IServer;
import shared.model.Person;

public class MainController implements Initializable {
	private Scene scene;
	private Parent root;

	private Person person;
	private String e_mail;
	private String pass;

	private IServer server;

	@FXML
	private TextField nameText;

	@FXML
	private PasswordField passText;

	@FXML
	private Button logInButton;

	@FXML
	private Label noteLabel;
	private Alert alert;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{

	}
	
	public Person getPerson()
	{
		return person;
	}

	public void setScene(Scene scene)
	{
		this.scene = scene;
		root = scene.getRoot();
	}

	@FXML
	public void logIn(MouseEvent e)
	{
		e_mail = nameText.getText();
		pass = passText.getText();
		try {
			person = server.logIn(e_mail, pass);
		} catch (RemoteException | MalformedURLException | NotBoundException e1) {
			e1.printStackTrace();
		}

		if (person == null) {
			noteLabel.setText("<!> Incorrect username or password");
		} else if (person.getType().equals(Person.ADMIN)) {
			new ViewAdmin(scene, this);
		} else if (person.getType().equals(Person.CLERK)) {
			new ViewClerk(scene, this);
		} else if (person.getType().equals(Person.CUSTOMER)) {
			new ViewCustomer(scene, this);
		}
	}

	@FXML
	public void logOff(MouseEvent e)
	{
		person = null;
		e_mail = null;
		pass = null;
		nameText.setText("");
		passText.setText("");
		scene.setRoot(root);
	}


	public boolean createAccount(Person person)
	{
		try {
			return server.createAccount(person);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBalance(String accountNumber, double amount)
	{
		try {
			return server.updateBalance(accountNumber, amount);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void error(String string)
	{
		if(alert == null)
		{
			alert = new Alert(Alert.AlertType.ERROR);
		}
		alert.setContentText(string);
		alert.showAndWait();
		
	}
	
	public void success(String string)
	{
		if(alert == null)
		{
			alert = new Alert(Alert.AlertType.INFORMATION);
		}
		alert.setContentText(string);
		alert.showAndWait();
	}
	
	public void connectToServer()
	{
		try {
			server = (IServer) Naming.lookup("rmi://localhost:1099/bankingServer");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

}