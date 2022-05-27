package Hospital;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class PatientIdentityTable extends Application {



    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../screens/login.fxml"));
        primaryStage.setTitle("Hospital Database");

        primaryStage.setScene(new Scene(root, 750, 650));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }


}
