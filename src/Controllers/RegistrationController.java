package Controllers;
//payment coverage error in float and text
//must handle all dates and validate them
//if dead he can't do anything

import DatabaseConnector.DBConnector;
import Hospital.Identity;
import Hospital.Insurance;
import Hospital.Patient;
import com.jfoenix.controls.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RegistrationController implements Initializable {

    @FXML
    private JFXTextField idNum;

    @FXML
    private JFXTextField fullName;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXComboBox<String> bloodType;

    @FXML
    private JFXComboBox<String> gender;

    @FXML
    private JFXDatePicker dateOfBirth;

    @FXML
    private JFXTextField insuranceID;

    @FXML
    private Spinner<Integer> payCoverage;

    @FXML
    private JFXDatePicker expiryDate;

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
    private JFXComboBox<String> departmentName;

    @FXML
    private JFXComboBox<String> roomName;

    @FXML
    private JFXComboBox<String> surgeryName;

    @FXML
    private JFXComboBox<String> doctor;

    @FXML
    private JFXComboBox<String> testName;

    @FXML
    private JFXComboBox<String> nurse;

    @FXML
    private JFXButton register;

    @FXML
    private JFXButton idClear;

    @FXML
    private JFXToggleButton insuranceCheck;

    @FXML
    private JFXButton inClear;

    @FXML
    private JFXButton pClear;

    @FXML
    private JFXButton dClear;

    @FXML
    private JFXButton addSurgery;

    @FXML
    private JFXButton sClear;

    @FXML
    private JFXButton addTest;

    @FXML
    private JFXButton tClear;

    @FXML
    private JFXDatePicker testDate;

    @FXML
    private JFXDatePicker surgeryDate;

    @FXML
    private VBox departBOX;

    @FXML
    private JFXButton addPhoneNumbers;

    @FXML
    private Button patientButton;

    private ArrayList<String> genderList;
    private ArrayList<String> bloodList;
    private ArrayList<String> emergencyStatList;
    private ArrayList<String> visitReasonList;
    private ArrayList<String> departmentList;
    private ArrayList<String> roomList;
    private ArrayList<String> surgeryList;
    private ArrayList<String> doctorList;
    private ArrayList<String> testList;
    private ArrayList<String> nurseList;
    private static ArrayList<String> phoneNumbers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disablingItems();
        initializingArrays();
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

        try {
            getData();
            ObservableList<String> visit = FXCollections.observableArrayList(visitReasonList);
            visitReason.setItems(visit);
            gender.setItems(FXCollections.observableArrayList(genderList));
            bloodType.setItems(FXCollections.observableArrayList(bloodList));
            emergencyStatus.setItems(FXCollections.observableArrayList(emergencyStatList));
            departmentName.setItems(FXCollections.observableArrayList(departmentList));
            testName.setItems(FXCollections.observableArrayList(testList));
            surgeryName.setItems(FXCollections.observableArrayList(surgeryList));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        insuranceCheck.setOnAction((ActionEvent e) -> {
            insuranceID.setDisable(!insuranceCheck.isSelected());
            payCoverage.setDisable(!insuranceCheck.isSelected());
            expiryDate.setDisable(!insuranceCheck.isSelected());
        });

        visitReason.setOnAction((ActionEvent e) -> {
            testClear();
            surgeryClear();
            if (visitReason.getSelectionModel().isSelected(0)) {
                disableSurgery(false);
                disableTest(true);
            } else if (visitReason.getSelectionModel().isSelected(1)) {
                disableSurgery(true);
                disableTest(false);
            } else {
                disableSurgery(false);
                disableTest(false);
            }
        });
        register.setOnAction((ActionEvent e) -> {
            identityValidation();
            departmentValidation();
            surgeryValidation();
            testValidation();
            patientValidation();
            insuranceValidation();
            if (identityValidation() && departmentValidation() && patientValidation()) {
                datesValidation();
                try {
                    Patient p = new Patient();
                    Identity id = new Identity();
                    Insurance insurance = new Insurance();

                    id.setBloodType(bloodType.getValue());
                    id.setFullName(fullName.getText());
                    id.setGender(gender.getValue());
                    id.setLivingAddress(address.getText());
                    id.setIdentityNumber(Integer.parseInt(idNum.getText()));

                    insurance.setInsuranceID(Integer.parseInt(insuranceID.getText()));
                    insurance.setPaymentCoverage(payCoverage.getValue());
                    insurance.setExpiryDate(
                            new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(String.valueOf(expiryDate.getValue()))
                    );
                    insurance.setIdentityNumber(Integer.parseInt(idNum.getText()));

                    p = new Patient(visitReason.getValue(), emergencyStatus.getValue(), Integer.parseInt(lengthOfStay.getText()), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(joinDate.getValue() + " " + joinTime.getValue() + ":00"),
                            Integer.parseInt(idNum.getText()));
                    if (leaveDate.getValue() != null) {
                        p.setLeaveDateAndTime(
                                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(leaveDate.getValue() + " " + leaveTime.getValue() + ":00")
                        );
                    }

                    id.setDateOfBirth(
                            new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(dateOfBirth.getValue()))
                    );

                    String identitySQL = "Insert into Patient values("
                            + "'" + p.getVisitReason() + "','"
                            + p.getEmergencyStatus() + "',"
                            + p.getLengthOfStay() + ",'"
                            + p.getJoinDateAndTimeToString() + "','"
                            + p.getLeaveDateAndTimeToString() + "');";

                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
        });
        idClear.setOnAction((ActionEvent e) -> {
            identityClear();
        });
        inClear.setOnAction((ActionEvent e) -> {
            insuranceClear();
        });
        pClear.setOnAction((ActionEvent e) -> {
            patientClear();
            surgeryClear();
            testClear();
        });
        dClear.setOnAction((ActionEvent e) -> {
            departmentClear();
        });
        sClear.setOnAction((ActionEvent e) -> {
            surgeryClear();
        });
        tClear.setOnAction((ActionEvent e) -> {
            testClear();
        });

        addPhoneNumbers.setOnAction((ActionEvent e) -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../screens/phoneNumbers.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        patientButton.setOnAction((ActionEvent e) -> {
            try {
                addPhoneNumbers.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("../screens/patients.fxml"));
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
        try {
            idNum.clear();
            fullName.clear();
            dateOfBirth.setValue(null);
            address.clear();
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
        disableTest(true);
        disableSurgery(true);
        lengthOfStay.setUnFocusColor(Paint.valueOf("black"));
        leaveDate.setDefaultColor(Paint.valueOf("black"));
        leaveTime.setDefaultColor(Paint.valueOf("black"));
        joinTime.setDefaultColor(Paint.valueOf("black"));
        joinDate.setDefaultColor(Paint.valueOf("black"));
        visitReason.setUnFocusColor(Paint.valueOf("black"));
        emergencyStatus.setUnFocusColor(Paint.valueOf("black"));
    }

    private void departmentClear() {
        departmentName.setValue(null);
        roomName.setValue(null);
        departmentName.setUnFocusColor(Paint.valueOf("black"));
        roomName.setUnFocusColor(Paint.valueOf("black"));
    }

    private void surgeryClear() {
        surgeryName.setUnFocusColor(Paint.valueOf("black"));
        doctor.setUnFocusColor(Paint.valueOf("black"));
        surgeryDate.setDefaultColor(Paint.valueOf("black"));
        surgeryName.setValue(null);
        doctor.setValue(null);
        surgeryDate.setPromptText("");
    }

    private void testClear() {
        testDate.setDefaultColor(Paint.valueOf("black"));
        nurse.setUnFocusColor(Paint.valueOf("black"));
        testName.setUnFocusColor(Paint.valueOf("black"));
        testName.setValue(null);
        nurse.setValue(null);
        testDate.setPromptText("");
    }

    //must check if insurance exists
    private int insuranceValidation() {
        if (insuranceCheck.isSelected()) {
            boolean flag = false;
            if (insuranceCheck.isSelected() && (insuranceID.getText().length() != 9 || !Pattern.matches("[0-9]{9}", idNum.getText()))) {
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

    private boolean patientValidation() {
        boolean flag = false;
        if (emergencyStatus.getSelectionModel().isEmpty()) {
            emergencyStatus.setUnFocusColor(Paint.valueOf("red"));
            flag = true;
        } else {
            emergencyStatus.setUnFocusColor(Paint.valueOf("black"));
        }
        if (visitReason.getSelectionModel().isEmpty()) {
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
        if (leaveTime.getValue() == null) {
            leaveTime.setDefaultColor(Paint.valueOf("red"));
            leaveTime.setPromptText("ERROR");
            flag = true;
        } else {
            leaveTime.setDefaultColor(Paint.valueOf("black"));
        }
        if (leaveDate.getValue() == null) {
            leaveDate.setDefaultColor(Paint.valueOf("red"));
            leaveDate.setPromptText("ERROR");
            flag = true;
        } else {
            leaveDate.setDefaultColor(Paint.valueOf("black"));
        }
        if (!lengthOfStay.getText().isEmpty() && Integer.parseInt(lengthOfStay.getText()) < 0) {
            lengthOfStay.setUnFocusColor(Paint.valueOf("RED"));
            flag = true;
        } else {
            lengthOfStay.setUnFocusColor(Paint.valueOf("black"));
        }
        return !flag;
    }

    private int testValidation() {
        if (!testName.isDisabled()) {
            boolean flag = false;
            if (testName.getSelectionModel().isEmpty()) {
                testName.setUnFocusColor(Paint.valueOf("red"));
                flag = true;
            } else {
                testName.setUnFocusColor(Paint.valueOf("black"));
            }
            if (nurse.getSelectionModel().isEmpty()) {
                nurse.setUnFocusColor(Paint.valueOf("red"));
                flag = true;
            } else {
                nurse.setUnFocusColor(Paint.valueOf("black"));
            }
            if (testDate.getValue() == null) {
                testDate.setDefaultColor(Paint.valueOf("red"));
                testDate.setPromptText("ERROR");
                flag = true;
            } else {
                testDate.setDefaultColor(Paint.valueOf("black"));
            }
            return !flag ? 1 : 0;
        }
        return -1;
    }

    ///must be handled isDisabled() to be removed
    private int surgeryValidation() {
        if (!surgeryName.isDisabled()) {
            boolean flag = false;
            if (surgeryName.getSelectionModel().isEmpty()) {
                surgeryName.setUnFocusColor(Paint.valueOf("red"));
                flag = true;
            } else {
                surgeryName.setUnFocusColor(Paint.valueOf("black"));
            }
            if (doctor.getSelectionModel().isEmpty()) {
                doctor.setUnFocusColor(Paint.valueOf("red"));
                flag = true;
            } else {
                doctor.setUnFocusColor(Paint.valueOf("black"));
            }
            if (surgeryDate.getValue() == null) {
                surgeryDate.setDefaultColor(Paint.valueOf("red"));
                surgeryDate.setPromptText("ERROR");
                flag = true;
            } else {
                surgeryDate.setDefaultColor(Paint.valueOf("black"));
            }
            return !flag ? 1 : 0;
        }
        return -1;
    }

    //must add date
    private boolean departmentValidation() {
        boolean flag = true;
        if (departmentName.getSelectionModel().isEmpty()) {
            departmentName.setUnFocusColor(Paint.valueOf("red"));
            flag = false;
        } else {
            departmentName.setUnFocusColor(Paint.valueOf("black"));
        }
        if (roomName.getSelectionModel().isEmpty()) {
            roomName.setUnFocusColor(Paint.valueOf("red"));
            flag = false;
        } else {
            roomName.setUnFocusColor(Paint.valueOf("black"));
        }
        return flag;
    }

    //must check if id exists
    private boolean identityValidation() {
        boolean flag = true;
        if (idNum.getText().length() != 9 || !Pattern.matches("[0-9]{9}", idNum.getText())) {
            idNum.setUnFocusColor(Paint.valueOf("RED"));
            flag = false;
        } else {
            idNum.setUnFocusColor(Paint.valueOf("black"));
        }
        if (phoneNumbers.isEmpty()){
            addPhoneNumbers.setStyle("-fx-background-color: #ff0000");
            flag = false;
        }else{
            addPhoneNumbers.setStyle("-fx-background-color: #3b5998");
        }

        if (!Pattern.matches("[A-Za-z-']+", fullName.getText())) {
            fullName.setUnFocusColor(Paint.valueOf("RED"));
            flag = false;
        } else {
            fullName.setUnFocusColor(Paint.valueOf("black"));
        }
        if (!Pattern.matches("[A-Za-z-']+", address.getText())) {
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

    private void datesValidation() {
        try {
            Date joinDT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(joinDate.getValue().toString() + " " + joinTime.getValue().toString() + ":00");
            Date leaveDT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(leaveDate.getValue().toString() + " " + leaveTime.getValue().toString() + ":00");
            Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth.getValue().toString());
            if (insuranceCheck.isSelected()) {
                Date inExpiry = new SimpleDateFormat("yyyy-MM-dd").parse(expiryDate.getValue().toString());
                if (inExpiry.compareTo(joinDT) < 0) {
                    expiryDate.setDefaultColor(Paint.valueOf("red"));
                } else {
                    expiryDate.setDefaultColor(Paint.valueOf("black"));
                }
            }
            if (!surgeryName.isDisabled()) {
                Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(surgeryDate.getValue().toString());
                if (sDate.compareTo(joinDT) < 0 || sDate.compareTo(leaveDT) >= 0) {
                    surgeryDate.setDefaultColor(Paint.valueOf("red"));
                } else {
                    surgeryDate.setDefaultColor(Paint.valueOf("black"));
                }
            }
            if (!testName.isDisabled()) {
                Date tDate = new SimpleDateFormat("yyyy-MM-dd").parse(testDate.getValue().toString());
                if (tDate.compareTo(joinDT) < 0 || tDate.compareTo(leaveDT) >= 0) {
                    testDate.setDefaultColor(Paint.valueOf("red"));
                } else {
                    testDate.setDefaultColor(Paint.valueOf("black"));
                }
            }

            if (dob.compareTo(joinDT) <= 0) {
                joinDate.setDefaultColor(Paint.valueOf("red"));
                joinTime.setDefaultColor(Paint.valueOf("red"));
            } else {
                joinDate.setDefaultColor(Paint.valueOf("black"));
                joinTime.setDefaultColor(Paint.valueOf("black"));
            }
            if (leaveDT.compareTo(joinDT) < 0) {
                leaveDate.setDefaultColor(Paint.valueOf("red"));
                leaveTime.setDefaultColor(Paint.valueOf("red"));
            } else {
                leaveDate.setDefaultColor(Paint.valueOf("black"));
                leaveTime.setDefaultColor(Paint.valueOf("black"));
            }
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
    }

    private void disablingItems() {
        insuranceID.setDisable(true);
        payCoverage.setDisable(true);
        expiryDate.setDisable(true);
        disableSurgery(true);
        disableTest(true);
    }

    private void disableSurgery(Boolean b) {
        surgeryDate.setDisable(b);
        addSurgery.setDisable(b);
        sClear.setDisable(b);
        surgeryName.setDisable(b);
        doctor.setDisable(b);
    }

    private void disableTest(Boolean b) {
        tClear.setDisable(b);
        addTest.setDisable(b);
        testDate.setDisable(b);
        nurse.setDisable(b);
        testName.setDisable(b);
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

        departmentList = new ArrayList<>();
        testList = new ArrayList<>();
        surgeryList = new ArrayList<>();
        phoneNumbers = new ArrayList<>();
    }

    private void getData() throws SQLException, ClassNotFoundException, ParseException {
        String SQL;

        DBConnector.connectDB();
        System.out.println("Connection established");

        SQL = "select D.Department_name\n" +
                "from Department D;";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            departmentList.add(rs.getString(1));
        }

        SQL = "select T.test_name\n" +
                "from Tests T;";
        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            testList.add(rs.getString(1));
        }

        SQL = "select S.surgery_name\n" +
                "from Surgeries S;";
        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            surgeryList.add(rs.getString(1));
        }

        /*if (departmentName.getSelectionModel() != null){
            SQL = "select T.test_name\n" +
                    "from Tests T;";
            stmt = DBConnector.getCon().createStatement();
            rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                testList.add(rs.getString(0));
            }
        }*/

        rs.close();
        stmt.close();

        DBConnector.getCon().close();
    }

    public ArrayList<String> getPhoneNumbers(){
        return phoneNumbers;
    }

    public static void setPhoneNumbers(ArrayList<String> pNums) {
        RegistrationController.phoneNumbers = pNums;
    }
}
