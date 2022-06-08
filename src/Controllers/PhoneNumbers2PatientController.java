package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PhoneNumbers2PatientController implements Initializable {
    @FXML
    private JFXTextField pNum1;

    @FXML
    private JFXTextField pNum2;

    @FXML
    private JFXTextField pNum3;

    @FXML
    private JFXButton upPhones;

    @FXML
    private JFXButton close;

    @FXML
    private JFXButton addPhones;

    private static ArrayList<String> phoneNumbers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (PatientsController.pSelection == 1){
            phoneNumbers = new ArrayList<>();
            addPhones.setVisible(true);
            close.setVisible(false);
            upPhones.setVisible(false);
        }
        if (PatientsController.pSelection == 2){
            phoneNumbers = PatientsController.getPhoneNumbers();
            pNum1.setText("0" + phoneNumbers.get(0));
            pNum2.setText(phoneNumbers.size() == 2 ? "0" + phoneNumbers.get(1) : "");
            pNum3.setText(phoneNumbers.size() == 3 ? "0" + phoneNumbers.get(2) : "");

            pNum1.setEditable(false);
            pNum2.setEditable(false);
            pNum3.setEditable(false);
            addPhones.setVisible(false);
            close.setVisible(true);
            upPhones.setVisible(false);
        }
        if (PatientsController.pSelection == 3){
            phoneNumbers = PatientsController.getPhoneNumbers();
            pNum1.setText("0" + phoneNumbers.get(0));
            pNum2.setText(phoneNumbers.size() == 2 ? "0" + phoneNumbers.get(1) : "");
            pNum3.setText(phoneNumbers.size() == 3 ? "0" + phoneNumbers.get(2) : "");

            addPhones.setVisible(false);
            close.setVisible(false);
            upPhones.setVisible(true);
        }
        addPhones.setOnAction((ActionEvent e) -> {
            if (phoneNumbersValidation()){
                phoneNumbers = new ArrayList<>();
                if (!pNum1.getText().isEmpty()){
                    phoneNumbers.add(pNum1.getText());
                }
                if (!pNum2.getText().isEmpty() && !phoneNumbers.contains(pNum2.getText())){
                    phoneNumbers.add(pNum2.getText());
                }
                if (!pNum3.getText().isEmpty() && !phoneNumbers.contains(pNum3.getText())){
                    phoneNumbers.add(pNum3.getText());
                }
                PatientsController.setPhoneNumbers(phoneNumbers);
                pNum1.getScene().getWindow().hide();
            }
        });
        close.setOnAction((ActionEvent e) -> {
            pNum1.setEditable(false);
            pNum2.setEditable(false);
            pNum3.setEditable(false);
            pNum1.getScene().getWindow().hide();
        });

        upPhones.setOnAction((ActionEvent e) -> {
            if (phoneNumbersValidation()){
                phoneNumbers = new ArrayList<>();
                if (!pNum1.getText().isEmpty()){
                    phoneNumbers.add(pNum1.getText());
                }
                if (!pNum2.getText().isEmpty() && !phoneNumbers.contains(pNum2.getText())){
                    phoneNumbers.add(pNum2.getText());
                }
                if (!pNum3.getText().isEmpty() && !phoneNumbers.contains(pNum3.getText())){
                    phoneNumbers.add(pNum3.getText());
                }
                PatientsController.setPhoneNumbers(phoneNumbers);
                pNum1.getScene().getWindow().hide();
            }
        });
    }
    private boolean phoneNumbersValidation(){
        int i = 0;
        boolean f1 = pNum1.getText().isEmpty();
        boolean f2 = pNum2.getText().isEmpty();
        boolean f3 = pNum3.getText().isEmpty();

        if (f1 && f2 && f3){
            pNum1.setUnFocusColor(Paint.valueOf("RED"));
            pNum2.setDisable(true);
            pNum3.setDisable(true);
            i++;
        }
        if (!f1) {
            if (pNum1.getText().length() == 10 && Pattern.matches("05[2-9][0-9]{7}", pNum1.getText())) {
                pNum1.setUnFocusColor(Paint.valueOf("black"));
            }else{
                pNum1.setUnFocusColor(Paint.valueOf("red"));
                i++;
            }
        }
        if (!f2) {
            if (pNum2.getText().length() == 10 && Pattern.matches("05[2-9][0-9]{7}", pNum2.getText())) {
                pNum2.setUnFocusColor(Paint.valueOf("black"));
            }else{
                pNum2.setUnFocusColor(Paint.valueOf("red"));
                i++;
            }
        }
        if (!f3) {
            if (pNum3.getText().length() == 10 && Pattern.matches("05[2-9][0-9]{7}", pNum3.getText())) {
                pNum3.setUnFocusColor(Paint.valueOf("black"));
            }else{
                pNum3.setUnFocusColor(Paint.valueOf("red"));
                i++;
            }
        }
        return i == 0;
    }

    public static ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }
}
