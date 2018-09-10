package client.customer;

import java.io.IOException;

import client.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ViewCustomer {

	public ViewCustomer(Scene scene, MainController mainController)
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
			Parent root = loader.load();
			scene.setRoot(root);
			CustomerController controller = (CustomerController)loader.getController();
			controller.setMainController(mainController);
			controller.begin();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
