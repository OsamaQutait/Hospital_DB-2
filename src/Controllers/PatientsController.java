package Controllers;
//search can be done from table or searching manually.
//delete: have to check if patient has left the hospital or he doesnt have surgeries/tests or if he paid his bill
//update/insert: have to check all data
/*Reports
* 6) report about insurance coverage
* 7) check people whom insurance has expired or will expire soon
* 8) find person who stayed for much time in the hospital
* 9)
 */
//add margins for deleting: if he has left no problem, if not pay bill first
//when update: can add insurance
//join date and leave date can't be before any surgery/test

//blood type, gender,

import DatabaseConnector.DBConnector;
import Hospital.*;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class PatientsController implements Initializable {
    @FXML
    private TableView<Identity> pTable;

    @FXML
    private TableColumn<Identity, Integer> pId;

    @FXML
    private TableColumn<Identity, String> pName;

    @FXML
    private JFXTextField idNum;

    @FXML
    private JFXTextField fullName;

    @FXML
    private JFXComboBox<String> address;

    @FXML
    private JFXTextField phoneNumber;

    @FXML
    private JFXComboBox<String> bloodType;

    @FXML
    private JFXComboBox<String> gender;

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
    private JFXComboBox<String> emergencyStatus;

    @FXML
    private JFXComboBox<String> visitReason;

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
    private JFXButton reports;

    @FXML
    private Button registrationButton;

    @FXML
    private Button paymentButton;

    @FXML
    private JFXButton addPhoneNumbers;

    @FXML
    private JFXToggleButton tableSearch;

    @FXML
    private JFXToggleButton manualSearch;

    @FXML
    private JFXButton seePhoneNumbers;

    @FXML
    private JFXButton upPhoneNumbers;

    private ArrayList<String> genderList;
    private ArrayList<String> bloodList;
    private ArrayList<String> emergencyStatList;
    private ArrayList<String> visitReasonList;
    private static ArrayList<String> phoneNumbers;
    private static ArrayList<String> phoneNumbersUP;
    private ArrayList<String> cityList;

    private ArrayList<Identity> patientsSQL;
    private ObservableList<Identity> patientsOBS;

    public static int pSelection;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //search.setVisible(false);
        initializingArrays();
        disablingItems();
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        spinnerValueFactory.setValue(0);
        payCoverage.setValueFactory(spinnerValueFactory);

        try {
            getData();
            visitReason.setItems(FXCollections.observableArrayList(visitReasonList));
            gender.setItems(FXCollections.observableArrayList(genderList));
            bloodType.setItems(FXCollections.observableArrayList(bloodList));
            emergencyStatus.setItems(FXCollections.observableArrayList(emergencyStatList));
            address.setItems(FXCollections.observableArrayList(cityList));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pId.setCellValueFactory(new PropertyValueFactory<Identity, Integer>("identityNumber"));
        pName.setCellValueFactory(new PropertyValueFactory<Identity, String>("fullName"));
        patientsOBS = FXCollections.observableArrayList(patientsSQL);
        pTable.setItems(patientsOBS);

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
            idNum.setEditable(false);
            insuranceID.setEditable(false);
            addPhoneNumbers.setVisible(false);
            upPhoneNumbers.setVisible(false);
            seePhoneNumbers.setVisible(true);
            pSelection = 2;
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
            idNum.setEditable(false);
            insuranceID.setEditable(false);
            addPhoneNumbers.setVisible(false);
            seePhoneNumbers.setVisible(false);
            upPhoneNumbers.setVisible(true);
            pSelection = 3;
            if (insuranceID.isDisabled()){
                insuranceCheck.setDisable(false);
                insuranceID.setEditable(true);
            }else{
                insuranceID.setEditable(false);
            }


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
            idNum.setEditable(true);
            insuranceID.setEditable(true);
            pSelection = 1;
            insuranceClear();
            identityClear();
            patientClear();
            seePhoneNumbers.setVisible(false);
            upPhoneNumbers.setVisible(false);
            addPhoneNumbers.setVisible(true);
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
            idNum.setEditable(false);
            insuranceID.setEditable(false);
            addPhoneNumbers.setVisible(false);
            seePhoneNumbers.setVisible(false);
            upPhoneNumbers.setVisible(false);
        });

        tableSearch.setOnAction((ActionEvent e) -> {
            tableSearch.setSelected(true);
            manualSearch.setSelected(false);
        });

        manualSearch.setOnAction((ActionEvent e) -> {
            manualSearch.setSelected(true);
            tableSearch.setSelected(false);
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
                stage.setTitle("Hospital Database | Registration");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        paymentButton.setOnAction((ActionEvent e) -> {
            try {
                addPhoneNumbers.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("../screens/payment.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Hospital Database | Payment");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        addPhoneNumbers.setOnAction((ActionEvent e) -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../screens/phoneNumbers2Patient.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("Hospital Database | Add phone numbers");
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        reports.setOnAction((ActionEvent e) -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../screens/patientReports.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Hospital Database | Patients - Reports");
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        search.setOnAction((ActionEvent e) -> {
            pSelection = 2;
            insuranceClear();
            identityClear();
            insuranceClear();
            seePhoneNumbers.setVisible(true);
            addPhoneNumbers.setVisible(false);
            upPhoneNumbers.setVisible(false);
            if (tableSearch.isSelected() && pTable.getSelectionModel().getSelectedItem() != null){
                try {
                    displaySelected();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
        });

        update.setOnAction((ActionEvent e) -> {
            upPhoneNumbers.setVisible(true);
            addPhoneNumbers.setVisible(false);
            seePhoneNumbers.setVisible(false);
            pSelection = 3;
            System.out.println(identityValidation() + " " + patientValidation()  + " " +  (insuranceValidation() == 1 || insuranceValidation() == -1));
            if (identityValidation() && patientValidation() && (insuranceValidation() == 1 || insuranceValidation() == -1)){
                try {
                    if (leaveTime.getValue() == null || leaveDate.getValue() == null){
                        leaveDate.setValue(null);
                        leaveTime.setValue(null);
                    }
                    if (datesValidation()) {
                        updatePatient();
                        System.out.println("hi");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
        });

        insert.setOnAction((ActionEvent e) -> {
            pSelection = 1;
            addPhoneNumbers.setVisible(true);
            seePhoneNumbers.setVisible(false);
            upPhoneNumbers.setVisible(false);
            if (identityValidation() && patientValidation() && (insuranceValidation() == 1 || insuranceValidation() == -1)){
                try {
                    if (leaveTime.getValue() == null || leaveDate.getValue() == null){
                        leaveDate.setValue(null);
                        leaveTime.setValue(null);
                    }
                    if (datesValidation()) {
                        insertPatient();
                        pTable.refresh();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
        });

        delete.setOnAction((ActionEvent e) -> {
            addPhoneNumbers.setVisible(false);
            seePhoneNumbers.setVisible(false);
            upPhoneNumbers.setVisible(false);
            try {
                deletePatient();
                pTable.refresh();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });

        seePhoneNumbers.setOnAction((ActionEvent e) -> {
            if (!idNum.getText().isEmpty()) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../screens/phoneNumbers2Patient.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        upPhoneNumbers.setOnAction((ActionEvent e) -> {
            phoneNumbersUP = phoneNumbers;
            if (!idNum.getText().isEmpty()) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../screens/phoneNumbers2Patient.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        emergencyStatus.setOnAction((ActionEvent e) -> {
            if (emergencyStatus.getValue() != null && emergencyStatus.getValue().equals("Dead")) {
                visitReason.setDisable(true);
            }else{
                visitReason.setDisable(false);
                lengthOfStay.setDisable(false);
            }
        });

    }

    private void identityClear() {
        try {
            idNum.clear();
            fullName.clear();
            dateOfBirth.setValue(null);
            address.setValue(null);
            gender.setValue(null);
            bloodType.setValue(null);
            dateOfBirth.setPromptText("");
            dateOfBirth.setDefaultColor(Paint.valueOf("black"));
            bloodType.setUnFocusColor(Paint.valueOf("black"));
            gender.setUnFocusColor(Paint.valueOf("black"));
            address.setUnFocusColor(Paint.valueOf("black"));
            fullName.setUnFocusColor(Paint.valueOf("black"));
            idNum.setUnFocusColor(Paint.valueOf("black"));
            addPhoneNumbers.setStyle("-fx-background-color: #3b5998");
            idNum.clear();
            addPhoneNumbers.setVisible(false);
            seePhoneNumbers.setVisible(false);
            upPhoneNumbers.setVisible(false);
        } catch (Exception e) {
            System.out.println("");
        }
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
        visitReason.setDisable(false);
        lengthOfStay.setDisable(false);
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

        cityList = new ArrayList<>();
        cityList.add("Salfit");
        cityList.add("Nablus");
        cityList.add("Ramallah");
        cityList.add("Jenin");
        cityList.add("Tulkarm");
        cityList.add("Jerusalem");
        cityList.add("Jericho");
        cityList.add("Qalqilya");
        cityList.add("Bethlehem");
        cityList.add("Hebron");

        phoneNumbers = new ArrayList<>();
        patientsSQL = new ArrayList<>();
    }

    private int insuranceValidation() {
        if (insuranceCheck.isSelected()) {
            boolean flag = false;
            if (insuranceCheck.isSelected() && !Pattern.matches("[0-9]{1,}", idNum.getText())) {
                insuranceID.setUnFocusColor(Paint.valueOf("RED"));
                flag = true;
            } else {
                insuranceID.setUnFocusColor(Paint.valueOf("black"));
            }
            if (expiryDate.getValue() == null) {
                expiryDate.setDefaultColor(Paint.valueOf("red"));
                expiryDate.setPromptText("ERROR");
                flag = true;
            } else {
                expiryDate.setDefaultColor(Paint.valueOf("black"));
            }
            return !flag ? 1 : 0;
        }
        return -1;
    }

    private boolean datesValidation() {
        try {
            boolean flag = true;
            Date joinDT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(joinDate.getValue().toString() + " " + joinTime.getValue().toString() + ":00");
            Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth.getValue().toString());
            Date currentDate = new Date();
            Date leaveDT = null;
            if (leaveTime.getValue() != null && leaveDate.getValue() != null){
                leaveDT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(leaveDate.getValue().toString() + " " + leaveTime.getValue().toString() + ":00");
            }

            if (currentDate.compareTo(dob) <= 0) {
                dateOfBirth.setDefaultColor(Paint.valueOf("red"));
                dateOfBirth.setValue(null);
                dateOfBirth.setPromptText("ERROR");
                flag = false;
            } else {
                dateOfBirth.setDefaultColor(Paint.valueOf("black"));
            }

            if (leaveDT != null && dob.compareTo(leaveDT) > 0){
                dateOfBirth.setDefaultColor(Paint.valueOf("red"));
                dateOfBirth.setValue(null);
                dateOfBirth.setPromptText("ERROR");
                flag = false;
            }else{
                dateOfBirth.setDefaultColor(Paint.valueOf("black"));
            }

            if (leaveTime.getValue() != null && leaveDate.getValue() != null && dob.compareTo(leaveDT) > 0) {
                leaveDate.setDefaultColor(Paint.valueOf("red"));
                leaveTime.setDefaultColor(Paint.valueOf("red"));
                leaveTime.setValue(null);
                leaveDate.setValue(null);
                leaveTime.setPromptText("ERROR");
                leaveDate.setPromptText("ERROR");
                flag = false;
            } else {
                leaveDate.setDefaultColor(Paint.valueOf("black"));
                leaveTime.setDefaultColor(Paint.valueOf("black"));
            }

            if (insuranceCheck.isSelected()) {
                Date inExpiry = new SimpleDateFormat("yyyy-MM-dd").parse(expiryDate.getValue().toString());
                if (inExpiry.compareTo(joinDT) < 0 || currentDate.compareTo(inExpiry) > 0) {
                    expiryDate.setDefaultColor(Paint.valueOf("red"));
                    expiryDate.setValue(null);
                    expiryDate.setPromptText("ERROR");
                    flag = false;
                } else {
                    expiryDate.setDefaultColor(Paint.valueOf("black"));
                }
            }

            if (dob.compareTo(joinDT) > 0) {
                joinDate.setDefaultColor(Paint.valueOf("red"));
                joinTime.setDefaultColor(Paint.valueOf("red"));
                joinTime.setValue(null);
                joinDate.setValue(null);
                joinTime.setPromptText("ERROR");
                joinDate.setPromptText("ERROR");
                flag = false;
            } else {
                joinDate.setDefaultColor(Paint.valueOf("black"));
                joinTime.setDefaultColor(Paint.valueOf("black"));
            }
            if (leaveDate.getValue() != null && leaveTime.getValue() != null && leaveDT.compareTo(joinDT) < 0) {
                leaveDate.setDefaultColor(Paint.valueOf("red"));
                leaveTime.setDefaultColor(Paint.valueOf("red"));
                leaveDate.setValue(null);
                leaveTime.setValue(null);
                leaveDate.setPromptText("ERROR");
                leaveTime.setPromptText("ERROR");
                flag = false;
            } else {
                leaveDate.setDefaultColor(Paint.valueOf("black"));
                leaveTime.setDefaultColor(Paint.valueOf("black"));
            }
            if (leaveDate.getValue() != null && leaveTime.getValue() != null && flag){
                Date d1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(joinDate.getValue() + " " + joinTime.getValue() + ":00");
                Date d2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(leaveDate.getValue() + " " + leaveTime.getValue() + ":00");
                float length = Math.abs((float) ((d1.getTime() - d2.getTime()) / (1000.0 * 60 * 60 * 24)));
                length = Math.round(length*10000)/10000.0f;
                lengthOfStay.setText(String.valueOf(length));
            }

            return flag;
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        return false;
    }

    private boolean patientValidation() {
        boolean flag = false;
        if (emergencyStatus.getSelectionModel().isEmpty()) {
            emergencyStatus.setUnFocusColor(Paint.valueOf("red"));
            flag = true;
        } else {
            emergencyStatus.setUnFocusColor(Paint.valueOf("black"));
        }
        if (!visitReason.isDisabled() && visitReason.getSelectionModel().isEmpty()) {
            visitReason.setUnFocusColor(Paint.valueOf("red"));
            flag = true;
        } else {
            visitReason.setUnFocusColor(Paint.valueOf("black"));
        }
        if (joinDate.getValue() == null) {
            joinDate.setDefaultColor(Paint.valueOf("red"));
            joinDate.setPromptText("ERROR");
            flag = true;
        } else {
            joinDate.setDefaultColor(Paint.valueOf("black"));
        }
        if (joinTime.getValue() == null) {
            joinTime.setDefaultColor(Paint.valueOf("red"));
            joinTime.setPromptText("ERROR");
            flag = true;
        } else {
            joinTime.setDefaultColor(Paint.valueOf("black"));
        }
        if (emergencyStatus.getValue().equals("Dead") && (leaveTime.getValue() == null || leaveDate.getValue() == null)) {
            leaveDate.setDefaultColor(Paint.valueOf("red"));
            leaveDate.setPromptText("ERROR");
            leaveTime.setDefaultColor(Paint.valueOf("red"));
            leaveTime.setPromptText("ERROR");
            flag = true;
        } else {
            leaveDate.setDefaultColor(Paint.valueOf("black"));
            leaveTime.setDefaultColor(Paint.valueOf("black"));
        }
        return !flag;
    }

    private boolean identityValidation() {
        boolean flag = true;
        if (idNum.getText().length() != 9 || !Pattern.matches("[0-9]{9}", idNum.getText())) {
            idNum.setUnFocusColor(Paint.valueOf("RED"));
            flag = false;
        } else {
            idNum.setUnFocusColor(Paint.valueOf("black"));
        }
        if (phoneNumbers.isEmpty()) {
            addPhoneNumbers.setStyle("-fx-background-color: #ff0000");
            flag = false;
        } else {
            addPhoneNumbers.setStyle("-fx-background-color: #3b5998");
        }

        if (!Pattern.matches("[A-Za-z-' ]+", fullName.getText())) {
            fullName.setUnFocusColor(Paint.valueOf("RED"));
            flag = false;
        } else {
            fullName.setUnFocusColor(Paint.valueOf("black"));
        }
        if (address.getSelectionModel().isEmpty()) {
            address.setUnFocusColor(Paint.valueOf("RED"));
            flag = false;
        } else {
            address.setUnFocusColor(Paint.valueOf("black"));
        }
        if (gender.getSelectionModel().isEmpty()) {
            gender.setUnFocusColor(Paint.valueOf("red"));
            flag = false;
        } else {
            gender.setUnFocusColor(Paint.valueOf("black"));
        }
        if (bloodType.getSelectionModel().isEmpty()) {
            bloodType.setUnFocusColor(Paint.valueOf("red"));
            flag = false;
        } else {
            bloodType.setUnFocusColor(Paint.valueOf("black"));
        }
        if (dateOfBirth.getValue() == null) {
            dateOfBirth.setDefaultColor(Paint.valueOf("red"));
            dateOfBirth.setPromptText("ERROR");
            flag = false;
        } else {
            dateOfBirth.setDefaultColor(Paint.valueOf("black"));
        }
        return flag;
    }

    private void disablingItems() {
        insuranceID.setDisable(true);
        payCoverage.setDisable(true);
        expiryDate.setDisable(true);
        search.setDisable(true);
        delete.setDisable(true);
        update.setDisable(true);
        insert.setDisable(true);
        idNum.setEditable(false);
        insuranceID.setEditable(false);
        insuranceCheck.setDisable(true);
        addPhoneNumbers.setVisible(false);
        seePhoneNumbers.setVisible(false);
        upPhoneNumbers.setVisible(false);
        lengthOfStay.setEditable(false);
    }

    private void getData() throws SQLException, ClassNotFoundException, ParseException {
        String SQL;
        DBConnector.connectDB();

        SQL = "select distinct id.identity_number, id.full_name, id.gender, id.date_of_birth, id.blood_type, id.living_address\n" +
                "from identity id, patient p\n" +
                "where id.identity_number = p.identity_number;";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            patientsSQL.add(
                    new Identity(
                            Integer.parseInt(rs.getString(1)),
                            rs.getString(2),
                            rs.getString(3),
                            new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(4)),
                            rs.getString(5),
                            rs.getString(6)
                    )
            );
        }

        rs.close();
        stmt.close();

        DBConnector.getCon().close();
    }

    private void getPatientInfo(){}

    private void insertPatient() throws SQLException, ClassNotFoundException, ParseException {
        Identity id = new Identity(
                Integer.parseInt(idNum.getText()),
                fullName.getText(),
                gender.getValue(),
                new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(dateOfBirth.getValue())),
                bloodType.getValue(),
                address.getValue()
        );
        patientsOBS.add(id);
        Patient patient = new Patient(
                visitReason.getValue(),
                emergencyStatus.getValue(),
                lengthOfStay.getText().isEmpty() ? 0 : Float.parseFloat(lengthOfStay.getText()),
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(joinDate.getValue() + " " + joinTime.getValue() + ":00"),
                leaveDate.getValue() != null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(leaveDate.getValue() + " " + leaveTime.getValue() + ":00") : null,
                Integer.parseInt(idNum.getText()),
                null
        );

        Insurance insurance = null;
        if (insuranceCheck.isSelected()){
            insurance = new Insurance(
                    Integer.parseInt(insuranceID.getText()),
                    payCoverage.getValue(),
                    new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(expiryDate.getValue())),
                    Integer.parseInt(idNum.getText())
            );
        }

        String phones2Str = "";
        if (phoneNumbersUP.size() == 1) {
            phones2Str = "(" + id.getIdentityNumber() + ", " + phoneNumbersUP.get(0) + ");";
        } else {
            int i = 0;
            for (i = 0; i < phoneNumbersUP.size() - 1; i++) {
                phones2Str += "(" + id.getIdentityNumber() + ", " + phoneNumbersUP.get(i) + "),\n";
            }
            phones2Str += "(" + id.getIdentityNumber() + ", " + phoneNumbersUP.get(i) + ");";
        }

        DBConnector.connectDB();
        try {
            DBConnector.ExecuteStatement("Insert into Identity (identity_number, full_name, gender, date_of_birth, blood_type, living_address) values(" +
                    +id.getIdentityNumber()+",'"
                    +id.getFullName()+"', '"
                    +id.getGender()+"', '"
                    +id.getDateOfBirthToString()+"', '"
                    +id.getBloodType()+"', '"
                    +id.getLivingAddress()+"');");

            DBConnector.ExecuteStatement("Insert into Patient (visit_reason, emergency_status, stay_length_of_stay, stay_join_date_time, stay_leave_date_time, identity_number, Room_id) values('"
                    +patient.getVisitReason()+"', '"
                    +patient.getEmergencyStatus()+"', "
                    +patient.getLengthOfStay()+", '"
                    +patient.getJoinDateAndTimeToString()+"', "
                    +patient.getLeaveDateAndTimeToString()+", "
                    +patient.getIdentityNumber()+", "
                    +(patient.getRoomID() == null ? "null" : "'" + patient.getRoomID() + "'") + ");");

            DBConnector.ExecuteStatement("Insert into Phone2ID (identity_number, phone_number) values" + phones2Str);

            if (insuranceCheck.isSelected()){
                DBConnector.ExecuteStatement("Insert into Insurance (insurance_id, payment_coverage, expire_date, identity_number) values("
                        +insurance.getInsuranceID()+", "
                        +insurance.getPaymentCoverage()+", "
                        +insurance.getExpiryDateToString()+", "
                        +insurance.getIdentityNumber()+ ");");
            }
            DBConnector.getCon().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePatient() throws SQLException, ClassNotFoundException, ParseException {
        Identity id = new Identity(
                Integer.parseInt(idNum.getText()),
                fullName.getText(),
                gender.getValue(),
                new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(dateOfBirth.getValue())),
                bloodType.getValue(),
                address.getValue()
        );

        Identity temp = patientsOBS.get(IntStream.range(0, patientsOBS.size())
                .filter(i -> patientsOBS.get(i).getIdentityNumber() == id.getIdentityNumber())
                .findFirst()
                .orElse(-1));
        temp.setDateOfBirth(id.getDateOfBirth());
        temp.setLivingAddress(id.getLivingAddress());
        temp.setGender(id.getGender());
        temp.setBloodType(id.getBloodType());
        temp.setFullName(id.getFullName());


        Patient patient = new Patient(
                visitReason.getValue(),
                emergencyStatus.getValue(),
                lengthOfStay.getText().isEmpty() ? 0 : Float.parseFloat(lengthOfStay.getText()),
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(joinDate.getValue() + " " + joinTime.getValue() + ":00"),
                leaveDate.getValue() != null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(leaveDate.getValue() + " " + leaveTime.getValue() + ":00") : null,
                Integer.parseInt(idNum.getText()),
                null
        );

        boolean insuranceEx = false;
        Insurance insurance = null;
        if (insuranceCheck.isSelected()){
            insurance = new Insurance(
                    Integer.parseInt(insuranceID.getText()),
                    payCoverage.getValue(),
                    new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(expiryDate.getValue())),
                    Integer.parseInt(idNum.getText())
            );
            insuranceEx = insuranceExistence(id.getIdentityNumber());
        }

        DBConnector.connectDB();
        try {
            DBConnector.ExecuteStatement("Update Identity ID\n"
                    + "SET " + "ID.identity_number = " + id.getIdentityNumber()+", "
                    + "ID.full_name = '" + id.getFullName()+"', "
                    + "ID.gender = '" + id.getGender()+"', "
                    + "ID.date_of_birth = '" + id.getDateOfBirthToString()+"', "
                    + "ID.blood_type = '" + id.getBloodType()+"', "
                    + "ID. living_address = '" + id.getLivingAddress() + "'\n"
                    + "WHERE " + "ID.identity_number = " + id.getIdentityNumber() + ";");

            DBConnector.ExecuteStatement("Update Patient p\n"
                    + "SET p.visit_reason = '" + patient.getVisitReason()+"', "
                    + "p.emergency_status = '" + patient.getEmergencyStatus()+"', "
                    + "p.stay_length_of_stay = " + patient.getLengthOfStay()+", "
                    + "p.stay_join_date_time = '" + patient.getJoinDateAndTimeToString()+"', "
                    + "p.stay_leave_date_time = " + patient.getLeaveDateAndTimeToString()+" \n"
                    + " where p.identity_number = " + patient.getIdentityNumber()+
                    (patient.getRoomID() == null ? ";" : " and " + " p.Room_id = '" + patient.getRoomID() + "';"));

            for (String s : phoneNumbers) {
                DBConnector.ExecuteStatement("delete from Phone2ID p2id\n"
                        + "where p2id.phone_number = " + Integer.parseInt(s) + " and p2id.identity_number = " + id.getIdentityNumber() + ";"
                );
            }
            //phoneNumbers = phoneNumbersUP;
            String phones2Str = "";
            if (phoneNumbersUP.size() == 1) {
                phones2Str = "(" + id.getIdentityNumber() + ", " + phoneNumbersUP.get(0) + ");";
            } else {
                int i = 0;
                for (i = 0; i < phoneNumbersUP.size() - 1; i++) {
                    phones2Str += "(" + id.getIdentityNumber() + ", " + phoneNumbersUP.get(i) + "),\n";
                }
                phones2Str += "(" + id.getIdentityNumber() + ", " + phoneNumbersUP.get(i) + ");";
            }

            DBConnector.ExecuteStatement("Insert into Phone2ID (identity_number, phone_number) values" + phones2Str);

            if (insurance != null && insuranceEx){
                DBConnector.ExecuteStatement("Update Insurance insur\n"
                        + " SET insur.payment_coverage = " + insurance.getPaymentCoverage()+", "
                        + " insur.expire_date = " + insurance.getExpiryDateToString()+"\n"
                        + " where insur.insurance_id = " + insurance.getInsuranceID()+" and "
                        + " insur.identity_number = " + insurance.getIdentityNumber()+ ";");
            }else{
                if (insurance != null) {
                    DBConnector.ExecuteStatement("Insert into Insurance (insurance_id, payment_coverage, expire_date, identity_number) values("
                            + insurance.getInsuranceID() + ", "
                            + insurance.getPaymentCoverage() + ", "
                            + insurance.getExpiryDateToString() + ", "
                            + insurance.getIdentityNumber() + ");");
                }
            }
            DBConnector.getCon().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deletePatient() throws SQLException, ClassNotFoundException, ParseException {
        Identity id = new Identity(
                Integer.parseInt(idNum.getText()),
                fullName.getText(),
                gender.getValue(),
                new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(dateOfBirth.getValue())),
                bloodType.getValue(),
                address.getValue()
        );

        patientsOBS.remove(IntStream.range(0, patientsOBS.size())
                .filter(i -> patientsOBS.get(i).getIdentityNumber() == id.getIdentityNumber())
                .findFirst()
                .orElse(-1));

        Patient patient = new Patient(
                visitReason.getValue(),
                emergencyStatus.getValue(),
                lengthOfStay.getText().isEmpty() ? 0 : Float.parseFloat(lengthOfStay.getText()),
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(joinDate.getValue() + " " + joinTime.getValue() + ":00"),
                leaveDate.getValue() != null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(leaveDate.getValue() + " " + leaveTime.getValue() + ":00") : null,
                Integer.parseInt(idNum.getText()),
                null
        );

        DBConnector.connectDB();
        try {
            DBConnector.ExecuteStatement("delete from Patient P\n"
                    + "WHERE " + "P.identity_number = " + patient.getIdentityNumber() + ";");
            DBConnector.getCon().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void displaySelected() throws SQLException, ClassNotFoundException, ParseException {
        Identity id = pTable.getSelectionModel().getSelectedItem();
        Patient patient = new Patient();
        Insurance insurance = null;



        String SQL;
        DBConnector.connectDB();

        SQL = "select distinct p.visit_reason, p.emergency_status, p.stay_length_of_stay, p.stay_join_date_time, p.stay_leave_date_time, p.identity_number, p.Room_id\n" +
                "from patient p, identity id\n" +
                "where id.identity_number = " + id.getIdentityNumber() + " and p.identity_number = " + id.getIdentityNumber() + ";";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            patient = new Patient(
                    rs.getString(1), rs.getString(2),
                    Float.parseFloat(rs.getString(3)),
                    rs.getString(4) == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(4)),
                    rs.getString(5) == null ? null : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString(5)),
                    Integer.parseInt(rs.getString(6)), rs.getString(7)
            );
        }
        SQL = "select distinct insur.insurance_id, insur.payment_coverage, insur.expire_date, insur.identity_number\n" +
                "from patient p, insurance insur\n" +
                "where p.identity_number = " + id.getIdentityNumber() + " and insur.identity_number = " + id.getIdentityNumber() + ";";
        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            insurance = new Insurance(
                    Integer.parseInt(rs.getString(1)),
                    Integer.parseInt(rs.getString(2)),
                    rs.getString(3) == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(3)),
                    Integer.parseInt(rs.getString(4))
            );
        }

        SQL = "select p2id.phone_number\n" +
                "from phone2id p2id\n" +
                "where p2id.identity_number = " + id.getIdentityNumber() + ";";

        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);
        phoneNumbers = new ArrayList<>();
        phoneNumbersUP = new ArrayList<>();
        while (rs.next()) {
            phoneNumbers.add(rs.getString(1));
            phoneNumbersUP.add(rs.getString(1));

        }
        if (insurance != null){
            insuranceCheck.setDisable(false);
            insuranceCheck.setSelected(true);
            insuranceID.setDisable(false);
            expiryDate.setDisable(false);
            payCoverage.setDisable(false);

            insuranceID.setText(String.valueOf(insurance.getInsuranceID()));
            expiryDate.setValue(insurance.expiryDateLocal());
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
            spinnerValueFactory.setValue(insurance.getPaymentCoverage());
            payCoverage.setValueFactory(spinnerValueFactory);
        }

        idNum.setText(String.valueOf(id.getIdentityNumber()));
        fullName.setText(id.getFullName());
        dateOfBirth.setValue(id.getDOBLocalDate());
        gender.setValue(id.getGender());
        address.setValue(id.getLivingAddress());
        bloodType.setValue(id.getBloodType());

        emergencyStatus.setValue(patient.getEmergencyStatus());
        visitReason.setValue(patient.getVisitReason());
        joinDate.setValue(patient.getJoinDateLocalDate());
        joinTime.setValue(patient.getJoinTime());
        leaveDate.setValue(patient.getLeaveDateLocalDate());
        leaveTime.setValue(patient.getLeaveTime());
        lengthOfStay.setText(String.valueOf(patient.getLengthOfStay()));


        rs.close();
        stmt.close();

        DBConnector.getCon().close();
    }

    public static void setPhoneNumbers(ArrayList<String> pNums) {
        PatientsController.phoneNumbersUP = pNums;
    }

    public static ArrayList<String> getPhoneNumbers() {
        return PatientsController.phoneNumbers;
    }

    private boolean insuranceExistence(int id) throws SQLException, ClassNotFoundException, ParseException {
        String SQL;
        DBConnector.connectDB();
        SQL = "select distinct * \n" +
                "from insurance ins \n" +
                "where ins.identity_number = " + id + ";";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);
        boolean res = rs.next();
        rs.close();
        stmt.close();
        DBConnector.getCon().close();
        return res;
    }

}
