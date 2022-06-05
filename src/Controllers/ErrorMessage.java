package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

// this class is used as pop up window to notification the user with an error
public class ErrorMessage implements Initializable{

    @FXML
    private Label errorMsg;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	public void setErrorLabel(String str) {
    	errorMsg.setText(str);
    }

}
