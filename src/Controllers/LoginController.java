package Controllers;

import DatabaseConnector.DBConnector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private JFXTextField username;

    @FXML
    private PasswordField password;

    @FXML
    private JFXButton login;
    
    @FXML
    private Label labelpass;
    
    @FXML
    private Label labeluser;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    	// when click on login button, check if the user name and password are valid from  the database
        login.setOnAction((ActionEvent e) -> {
            try {
                DBConnector.connectDB();
                System.out.println("Connection established");
                String SQL = "select * from Users " +
                        "where username = '" + username.getText() +
                        "' and password = '" + password.getText() + "';";
                Statement stmt = DBConnector.getCon().createStatement();
                ResultSet rs = stmt.executeQuery(SQL);
                if (rs.next()){
                	labelpass.setTextFill(Paint.valueOf("BLACK"));
                	labeluser.setTextFill(Paint.valueOf("BLACK"));
                    System.out.println("The user exists in the system");
                    openDashBoard();
                }else{
                	labelpass.setTextFill(Paint.valueOf("RED"));
                	labeluser.setTextFill(Paint.valueOf("RED"));
                	username.clear();
                	password.clear();
                    System.out.println("The user does not exist");
                }
                rs.close();
                stmt.close();
                DBConnector.getCon().close();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
    public void openDashBoard(){
    	try {
    		labelpass.getScene().getWindow().hide();
			Parent root = FXMLLoader.load(getClass().getResource("../screens/patients.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setTitle("Hospital Database");
			stage.setScene(scene);
			stage.show();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
    }
}
