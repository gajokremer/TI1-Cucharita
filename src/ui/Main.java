package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private ControllerAdminGUI controller;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("MainPane.fxml"));
		controller = new ControllerAdminGUI();
		fxmlloader.setController(controller);
		Parent root = fxmlloader.load();
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("La Cucharita");
		primaryStage.show();
		
		controller.start();
	}
}
