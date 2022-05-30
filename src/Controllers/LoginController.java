package Controllers;

import DatabaseConnector.DBConnector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
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
                    System.out.println("The user exists in the system");
                }else{
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
}
