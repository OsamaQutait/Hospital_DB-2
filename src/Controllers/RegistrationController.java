package Controllers;
//payment coverage error in float and text

import DatabaseConnector.DBConnector;
import Hospital.Identity;
import Hospital.Patient;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
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
    private JFXTextField phoneNumber;

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
            /*try {
                Date joinDT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(joinDate.getValue().toString() + " " + joinTime.getValue().toString() + ":00");
                Date leaveDT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(leaveDate.getValue().toString() + " " + leaveTime.getValue().toString() + ":00");
                Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth.getValue().toString());
                if (dob.compareTo(joinDT) >= 0){
                    joinDate.setDefaultColor(Paint.valueOf("red"));
                    joinTime.setDefaultColor(Paint.valueOf("red"));
                }
                if (leaveDT.compareTo(joinDT) < 0){
                    leaveDate.setDefaultColor(Paint.valueOf("red"));
                    leaveTime.setDefaultColor(Paint.valueOf("red"));
                }
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }*/

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

    private int testValidation() {
        if (!testName.isDisabled()) {
            if (testName.getSelectionModel().isEmpty()) {
                testName.setUnFocusColor(Paint.valueOf("red"));
                return 0;
            } else {
                testName.setUnFocusColor(Paint.valueOf("black"));
            }
            if (nurse.getSelectionModel().isEmpty()) {
                nurse.setUnFocusColor(Paint.valueOf("red"));
                return 0;
            } else {
                nurse.setUnFocusColor(Paint.valueOf("black"));
            }
            if (testDate.getValue() == null) {
                testDate.setDefaultColor(Paint.valueOf("red"));
                testDate.setPromptText("ERROR");
                return 0;
            } else {
                testDate.setDefaultColor(Paint.valueOf("black"));
            }
            return 1;
        }
        return -1;
    }

    ///must be handled isDisabled() to be removed
    private int surgeryValidation() {
        if (!surgeryName.isDisabled()) {
            if (surgeryName.getSelectionModel().isEmpty()) {
                surgeryName.setUnFocusColor(Paint.valueOf("red"));
                return 0;
            } else {
                surgeryName.setUnFocusColor(Paint.valueOf("black"));
            }
            if (doctor.getSelectionModel().isEmpty()) {
                doctor.setUnFocusColor(Paint.valueOf("red"));
                return 0;
            } else {
                doctor.setUnFocusColor(Paint.valueOf("black"));
            }
            if (surgeryDate.getValue() == null) {
                surgeryDate.setDefaultColor(Paint.valueOf("red"));
                surgeryDate.setPromptText("ERROR");
                return 0;
            } else {
                surgeryDate.setDefaultColor(Paint.valueOf("black"));
            }
            return 1;
        }
        return -1;
    }

    //must add date
    private boolean departmentValidation() {
        if (departmentName.getSelectionModel().isEmpty()) {
            departmentName.setUnFocusColor(Paint.valueOf("red"));
            return false;
        } else {
            departmentName.setUnFocusColor(Paint.valueOf("black"));
        }
        if (roomName.getSelectionModel().isEmpty()) {
            roomName.setUnFocusColor(Paint.valueOf("red"));
            return false;
        } else {
            roomName.setUnFocusColor(Paint.valueOf("black"));
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
}
