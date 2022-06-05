package Controllers;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PatientsController implements Initializable {
    @FXML
    private TableView<?> pTable;

    @FXML
    private TableColumn<?, ?> pId;

    @FXML
    private TableColumn<?, ?> pName;

    @FXML
    private JFXTextField idNum;

    @FXML
    private JFXTextField fullName;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXTextField phoneNumber;

    @FXML
    private JFXComboBox<?> bloodType;

    @FXML
    private JFXComboBox<?> gender;

    @FXML
    private JFXDatePicker dateOfBirth;

    @FXML
    private JFXButton idClear;

    @FXML
    private JFXDatePicker joinDate;

    @FXML
    private JFXDatePicker leaveDate;

    @FXML
    private JFXTimePicker joinTime;

    @FXML
    private JFXTimePicker leaveTime;

    @FXML
    private JFXTextField lengthOfStay;

    @FXML
    private JFXComboBox<?> emergencyStatus;

    @FXML
    private JFXComboBox<?> visitReason;

    @FXML
    private JFXButton pClear;

    @FXML
    private JFXTextField insuranceID;

    @FXML
    private JFXDatePicker expiryDate;

    @FXML
    private JFXToggleButton insuranceCheck;

    @FXML
    private Spinner<Integer> payCoverage;

    @FXML
    private JFXButton inClear;

    @FXML
    private JFXToggleButton sMode;

    @FXML
    private JFXToggleButton dMode;

    @FXML
    private JFXToggleButton upMode;

    @FXML
    private JFXToggleButton inMode;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton update;

    @FXML
    private JFXButton insert;

    @FXML
    private JFXButton statistics;

    @FXML
    private Button registrationButton;

    private ArrayList<String> genderList;
    private ArrayList<String> bloodList;
    private ArrayList<String> emergencyStatList;
    private ArrayList<String> visitReasonList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializingArrays();
        disablingItems();
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        spinnerValueFactory.setValue(0);
        payCoverage.setValueFactory(spinnerValueFactory);

        EventHandler<KeyEvent> enterKeyEventHandler;

        enterKeyEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {

                    try {
                        Integer.parseInt(payCoverage.getEditor().textProperty().get());
                    } catch (NumberFormatException e) {
                        payCoverage.getEditor().textProperty().set(String.valueOf(0));
                    }
                }
            }
        };
        payCoverage.getEditor().addEventHandler(KeyEvent.KEY_PRESSED, enterKeyEventHandler);

        sMode.setOnAction((ActionEvent e) -> {
            upMode.setSelected(false);
            dMode.setSelected(false);
            inMode.setSelected(false);
            sMode.setSelected(true);
            search.setDisable(false);
            delete.setDisable(true);
            update.setDisable(true);
            insert.setDisable(true);
        });

        upMode.setOnAction((ActionEvent e) -> {
            upMode.setSelected(true);
            dMode.setSelected(false);
            inMode.setSelected(false);
            sMode.setSelected(false);
            update.setDisable(false);
            search.setDisable(true);
            delete.setDisable(true);
            insert.setDisable(true);
        });

        inMode.setOnAction((ActionEvent e) -> {
            upMode.setSelected(false);
            dMode.setSelected(false);
            inMode.setSelected(true);
            sMode.setSelected(false);
            insert.setDisable(false);
            search.setDisable(true);
            delete.setDisable(true);
            update.setDisable(true);
        });

        dMode.setOnAction((ActionEvent e) -> {
            upMode.setSelected(false);
            dMode.setSelected(true);
            inMode.setSelected(false);
            sMode.setSelected(false);
            delete.setDisable(false);
            search.setDisable(true);
            update.setDisable(true);
            insert.setDisable(true);
        });

        idClear.setOnAction((ActionEvent e) -> {
            identityClear();
        });

        pClear.setOnAction((ActionEvent e) -> {
            patientClear();
        });

        inClear.setOnAction((ActionEvent e) -> {
            insuranceClear();
        });

        insuranceCheck.setOnAction((ActionEvent e) -> {
            insuranceID.setDisable(!insuranceCheck.isSelected());
            payCoverage.setDisable(!insuranceCheck.isSelected());
            expiryDate.setDisable(!insuranceCheck.isSelected());
        });

        registrationButton.setOnAction((ActionEvent e) -> {
            try {
                insuranceCheck.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("../screens/registration.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Hospital Database");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void identityClear() {
        idNum.clear();
        fullName.clear();
        dateOfBirth.setValue(null);
        address.clear();
        phoneNumber.clear();
        gender.setValue(null);
        bloodType.setValue(null);
        dateOfBirth.setPromptText("");
        dateOfBirth.setDefaultColor(Paint.valueOf("black"));
        bloodType.setUnFocusColor(Paint.valueOf("black"));
        gender.setUnFocusColor(Paint.valueOf("black"));
        address.setUnFocusColor(Paint.valueOf("black"));
        fullName.setUnFocusColor(Paint.valueOf("black"));
        phoneNumber.setUnFocusColor(Paint.valueOf("black"));
        idNum.setUnFocusColor(Paint.valueOf("black"));
    }

    private void insuranceClear() {
        insuranceID.clear();
        expiryDate.setValue(null);
        expiryDate.setPromptText("");
        payCoverage.getValueFactory().setValue(0);
        expiryDate.setDefaultColor(Paint.valueOf("black"));
        insuranceID.setUnFocusColor(Paint.valueOf("black"));
        if (insuranceCheck.isSelected()) {
            insuranceCheck.fire();
        }
    }

    private void patientClear() {
        emergencyStatus.setValue(null);
        visitReason.setValue(null);
        joinTime.setValue(null);
        joinTime.setPromptText("");
        joinDate.setValue(null);
        joinDate.setPromptText("");
        leaveDate.setValue(null);
        leaveDate.setPromptText("");
        leaveTime.setValue(null);
        leaveTime.setPromptText("");
        lengthOfStay.clear();
        lengthOfStay.setUnFocusColor(Paint.valueOf("black"));
        leaveDate.setDefaultColor(Paint.valueOf("black"));
        leaveTime.setDefaultColor(Paint.valueOf("black"));
        joinTime.setDefaultColor(Paint.valueOf("black"));
        joinDate.setDefaultColor(Paint.valueOf("black"));
        visitReason.setUnFocusColor(Paint.valueOf("black"));
        emergencyStatus.setUnFocusColor(Paint.valueOf("black"));
    }

    private void initializingArrays() {
        genderList = new ArrayList<>();
        genderList.add("Male");
        genderList.add("Female");

        bloodList = new ArrayList<>();
        bloodList.add("O-");
        bloodList.add("O+");
        bloodList.add("A-");
        bloodList.add("A+");
        bloodList.add("B-");
        bloodList.add("B+");
        bloodList.add("AB-");
        bloodList.add("AB+");

        emergencyStatList = new ArrayList<>();
        emergencyStatList.add("Alive");
        emergencyStatList.add("Dead");

        visitReasonList = new ArrayList<>();
        visitReasonList.add("For surgery");
        visitReasonList.add("For test");
        visitReasonList.add("For surgery and test");
    }

    private int insuranceValidation() {
        if (insuranceCheck.isSelected()) {
            if (insuranceCheck.isSelected() && (insuranceID.getText().length() != 9 || !Pattern.matches("[0-9]{9}", idNum.getText()))) {
                insuranceID.setUnFocusColor(Paint.valueOf("RED"));
                return 0;
            } else {
                insuranceID.setUnFocusColor(Paint.valueOf("black"));
            }
            if (expiryDate.getValue() == null) {
                expiryDate.setDefaultColor(Paint.valueOf("red"));
                expiryDate.setPromptText("ERROR");
                return 0;
            } else {
                expiryDate.setDefaultColor(Paint.valueOf("black"));
            }
            return 1;
        }
        return -1;
    }

    private boolean patientValidation() {
        if (emergencyStatus.getSelectionModel().isEmpty()) {
            emergencyStatus.setUnFocusColor(Paint.valueOf("red"));
            return false;
        } else {
            emergencyStatus.setUnFocusColor(Paint.valueOf("black"));
        }
        if (visitReason.getSelectionModel().isEmpty()) {
            visitReason.setUnFocusColor(Paint.valueOf("red"));
            return false;
        } else {
            visitReason.setUnFocusColor(Paint.valueOf("black"));
        }
        if (joinDate.getValue() == null) {
            joinDate.setDefaultColor(Paint.valueOf("red"));
            joinDate.setPromptText("ERROR");
            return false;
        } else {
            joinDate.setDefaultColor(Paint.valueOf("black"));
        }
        if (joinTime.getValue() == null) {
            joinTime.setDefaultColor(Paint.valueOf("red"));
            joinTime.setPromptText("ERROR");
            return false;
        } else {
            joinTime.setDefaultColor(Paint.valueOf("black"));
        }
        if (leaveTime.getValue() == null) {
            leaveTime.setDefaultColor(Paint.valueOf("red"));
            leaveTime.setPromptText("ERROR");
            return false;
        } else {
            leaveTime.setDefaultColor(Paint.valueOf("black"));
        }
        if (leaveDate.getValue() == null) {
            leaveDate.setDefaultColor(Paint.valueOf("red"));
            leaveDate.setPromptText("ERROR");
            return false;
        } else {
            leaveDate.setDefaultColor(Paint.valueOf("black"));
        }
        if (!lengthOfStay.getText().isEmpty() && Integer.parseInt(lengthOfStay.getText()) < 0) {
            lengthOfStay.setUnFocusColor(Paint.valueOf("RED"));
            return false;
        } else {
            lengthOfStay.setUnFocusColor(Paint.valueOf("black"));
        }
        return true;
    }

    private boolean identityValidation() {
        if (idNum.getText().length() != 9 || !Pattern.matches("[0-9]{9}", idNum.getText())) {
            idNum.setUnFocusColor(Paint.valueOf("RED"));
            return false;
        } else {
            idNum.setUnFocusColor(Paint.valueOf("black"));
        }
        if (phoneNumber.getText().length() != 10 || !Pattern.matches("05[2-9][0-9]{7}", phoneNumber.getText())) {
            phoneNumber.setUnFocusColor(Paint.valueOf("RED"));
            return false;
        } else {
            phoneNumber.setUnFocusColor(Paint.valueOf("black"));
        }
        if (!Pattern.matches("[A-Za-z-']+", fullName.getText())) {
            fullName.setUnFocusColor(Paint.valueOf("RED"));
            return false;
        } else {
            fullName.setUnFocusColor(Paint.valueOf("black"));
        }
        if (!Pattern.matches("[A-Za-z-']+", address.getText())) {
            address.setUnFocusColor(Paint.valueOf("RED"));
            return false;
        } else {
            address.setUnFocusColor(Paint.valueOf("black"));
        }
        if (gender.getSelectionModel().isEmpty()) {
            gender.setUnFocusColor(Paint.valueOf("red"));
            return false;
        } else {
            gender.setUnFocusColor(Paint.valueOf("black"));
        }
        if (bloodType.getSelectionModel().isEmpty()) {
            bloodType.setUnFocusColor(Paint.valueOf("red"));
            return false;
        } else {
            bloodType.setUnFocusColor(Paint.valueOf("black"));
        }
        if (dateOfBirth.getValue() == null) {
            dateOfBirth.setDefaultColor(Paint.valueOf("red"));
            dateOfBirth.setPromptText("ERROR");
            return false;
        } else {
            dateOfBirth.setDefaultColor(Paint.valueOf("black"));
        }
        return true;
    }

    private void disablingItems() {
        insuranceID.setDisable(true);
        payCoverage.setDisable(true);
        expiryDate.setDisable(true);
        search.setDisable(true);
        delete.setDisable(true);
        update.setDisable(true);
        insert.setDisable(true);
    }
}
