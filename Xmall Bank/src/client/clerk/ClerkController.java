package client.clerk;

import client.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import shared.model.Person;

public class ClerkController {
	private MainController mainController;
    @FXML
    private Label welcome;

    @FXML
    private Label accountNumber;

    @FXML
    private Label balance;

    @FXML
    private TextField amountW;

    @FXML
    private TextField amountD;

    @FXML
    void depositMoney(MouseEvent event) {
    	double amountToD = 0.0;
    	try {
    		amountToD = Double.parseDouble(amountD.getText());
    	}
    	catch(NumberFormatException e)
    	{
    		mainController.error("Please enter correct amount to deposit");
    		return;
    	}
    	if(mainController.updateBalance(accountNumber.getText(), amountToD))
    	{
    		double oldBalance = Double.parseDouble(balance.getText());
    		double newBalance = oldBalance + amountToD;
    		mainController.getPerson().getAccount().setBalance(newBalance);
    		balance.setText(""+newBalance);
    		mainController.success("Deposit successfull");
    	}
    	else
    	{
    		mainController.error("Could not complete the deposit.");
    	}
    }

    @FXML
    void logOff(MouseEvent event) {
    	mainController.logOff(event);
    }

    @FXML
    void withdrawMoney(MouseEvent event) {
    	double amountToW = 0.0;
    	try {
    		amountToW = Double.parseDouble(amountW.getText());
    	}
    	catch(NumberFormatException e)
    	{
    		mainController.error("Please enter correct amount to withdraw");
    		return;
    	}
    	
    	double oldBalance = Double.parseDouble(balance.getText());
    	if(oldBalance - amountToW < 0)
    	{
    		mainController.error("Insufficient funds!");
    		return;
    	}
    	
    	
    	
    	if(mainController.updateBalance(accountNumber.getText(), -1*amountToW))
    	{
    		double newBalance = oldBalance - amountToW;
    		mainController.getPerson().getAccount().setBalance(newBalance);
    		balance.setText(""+newBalance);
    		mainController.success("Withdrawl successfull");
    	}
    	else
    	{
    		mainController.error("Could not complete the withdrawl.");
    	}
    }


	public void setMainController(MainController mainController)
	{
		this.mainController = mainController; 
	}
	
	public void begin()
	{
		Person person = mainController.getPerson();
		welcome.setText("Welcome " + person.getName());
		balance.setText("" + person.getAccount().getBalance());
		accountNumber.setText(person.getAccount().getAccountNumber());
	}

}
