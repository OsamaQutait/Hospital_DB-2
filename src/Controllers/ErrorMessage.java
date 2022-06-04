package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorMessage {

    @FXML
    private Label errorMsg;
    
    public void setErrorLabel(String str) {
    	errorMsg.setText(str);
    }

}
