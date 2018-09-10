package client.admin;


import client.MainController;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import shared.model.Account;
import shared.model.Person;

public class AdminController{
	private MainController mainController;
	@FXML
	private Label welcome;

	@FXML
	private TextField initialBalance;

	@FXML
	private TextField name;

	@FXML
	private TextField e_mail;

	@FXML
	private ChoiceBox<String> types;

	@FXML
	void createAccount(MouseEvent event)
	{
		String type = types.getSelectionModel().getSelectedItem();
		Account account = null;
		if(!type.equals(Person.ADMIN))
		{
			double balance = 0.0;
			if(initialBalance.getText().equals(""))
			{
				balance = 0.0;
			}
			else
			{
				try {
					balance = Double.parseDouble(initialBalance.getText());				
				}
				catch(NumberFormatException e)
				{
					mainController.error("Please enter only numbers for initial balance");
					return;
				}				
			}
			account = new Account(balance);
		}
		Person p = new Person();
		String n = name.getText();
		String email = e_mail.getText();
		
		p.setAccount(account);
		p.setE_mail(email);
		p.setName(n);
		p.setType(type);
		if(mainController.createAccount(p))
		{
			mainController.success("Account created successfully");
		}
		else
		{
			mainController.error("Something went wrong. Account could not be created");
		}
	}

	@FXML
	void logOff(MouseEvent event)
	{
		mainController.logOff(event);
	}

	public void setMainController(MainController mainController)
	{
		this.mainController = mainController;

	}

	public void populateChoiceBox()
	{

		types.getItems().add(Person.CUSTOMER);
		types.getItems().add(Person.CLERK);
		types.getItems().add(Person.ADMIN);
		types.setValue(Person.CUSTOMER);
		final ObservableList<String> options = types.getItems();
		types.getSelectionModel().selectedIndexProperty()
				.addListener((ChangeListener<Number>) (ov, oldSelected, newSelected) -> {
					if(options.get(newSelected.intValue()).equals(Person.ADMIN))
					{
						initialBalance.disableProperty().set(true);
					}
					else
					{
						initialBalance.disableProperty().set(false);			
					}
				});
	}

	public void begin()
	{
		populateChoiceBox();
		welcome.setText("Welcome " + mainController.getPerson().getName());
	}
}