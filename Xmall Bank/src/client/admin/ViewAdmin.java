package client.admin;

import java.io.IOException;

import client.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ViewAdmin {
	
	public ViewAdmin(Scene scene, MainController mainController)
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
			Parent root = loader.load();
			scene.setRoot(root);
			AdminController ac = (AdminController)loader.getController();
			ac.setMainController(mainController);
			ac.begin();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
