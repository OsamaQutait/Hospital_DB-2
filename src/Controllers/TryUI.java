package Controllers;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TryUI extends Application{

	public void start(Stage primaryStage) throws Exception {
		//FXMLLoader loader = new FXMLLoader(getClass().getResource("../screens/error.fxml"));
        //primaryStage.setTitle("error");
        //Parent root = loader.load();
        //ErrorMessage alertController = loader.getController();
        //alertController.setErrorLabel("The inserted name was more than 32 characters!"); // registration medicalStaff patients
		Parent root = FXMLLoader.load(getClass().getResource("../screens/medicalStaff.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
	
    public static void main(String[] args) {
        launch(args);
    }

}
