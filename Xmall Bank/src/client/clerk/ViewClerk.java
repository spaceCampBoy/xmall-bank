package client.clerk;

import java.io.IOException;

import client.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ViewClerk {
	public ViewClerk(Scene scene, MainController mainController)
	{
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Clerk.fxml"));
			Parent root = loader.load();
			scene.setRoot(root);
			ClerkController controller = (ClerkController)loader.getController();
			controller.setMainController(mainController);
			controller.begin();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
