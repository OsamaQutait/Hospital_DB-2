package Controllers;
//must handle all dates and validate them
//i think leave date is not compulsory
//decrease the number of available beds

import DatabaseConnector.DBConnector;
import Hospital.*;
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

    @FXML
    private JFXComboBox<String> nurse;

    @FXML
    private Label ratingLabel;

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

    private ArrayList<Department> departmentsSQL;
    private ArrayList<Tests> testsSQL;
    private ArrayList<Surgeries> surgeriesSQL;
    private ArrayList<Identity> doctorsSQL;
    private ArrayList<Identity> nursesSQL;
    private ArrayList<Room> roomsSQL;


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

            departmentList = departmentsSQL.stream().map(
                    Department::getDepartmentName
            ).collect(Collectors.toCollection(ArrayList::new));

            testList = testsSQL.stream().map(
                    Tests::getTestName
            ).collect(Collectors.toCollection(ArrayList::new));

            surgeryList = surgeriesSQL.stream().map(
                    Surgeries::getSurgery_name
            ).collect(Collectors.toCollection(ArrayList::new));

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

        emergencyStatus.setOnAction((ActionEvent e) -> {
            if (emergencyStatus.getValue() != null && emergencyStatus.getValue().equals("Dead")) {
                testClear();
                surgeryClear();
                departmentClear();
                visitReason.setDisable(true);
                lengthOfStay.setDisable(true);
                departmentName.setDisable(true);
                roomName.setDisable(true);
            }else{
                testClear();
                surgeryClear();
                departmentClear();
                visitReason.setDisable(false);
                lengthOfStay.setDisable(false);
                departmentName.setDisable(false);
                roomName.setDisable(false);
            }
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
                if (leaveTime.getValue() == null || leaveDate.getValue() == null){
                    leaveDate.setValue(null);
                    leaveTime.setValue(null);
                }
                datesValidation();
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

        surgeryName.setOnAction((ActionEvent e) -> {
            if (surgeryName.getValue() != null) {
                doctor.setDisable(false);
                doctor.setValue(null);
                doctor.getItems().clear();
                try {
                    getDoctors();
                    doctorList = new ArrayList<>();
                    doctorList = doctorsSQL.stream().map(
                            Identity::getFullName
                    ).collect(Collectors.toCollection(ArrayList::new));
                    doctor.setItems(FXCollections.observableArrayList(doctorList));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }

        });

        testName.setOnAction((ActionEvent e) -> {
            if (testName.getValue() != null) {
                nurse.setDisable(false);
                nurse.setValue(null);
                nurse.getItems().clear();
                try {
                    getNurses();
                    nurseList = new ArrayList<>();
                    nurseList = nursesSQL.stream().map(
                            Identity::getFullName
                    ).collect(Collectors.toCollection(ArrayList::new));
                    nurse.setItems(FXCollections.observableArrayList(nurseList));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
        });

        departmentName.setOnAction((ActionEvent e) -> {
            if (departmentName.getValue() != null) {
                roomName.setDisable(false);
                roomName.setValue(null);
                roomName.getItems().clear();
                try {
                    getRooms();
                    roomList = new ArrayList<>();
                    roomList = roomsSQL.stream().map(
                            Room::getRoomID
                    ).collect(Collectors.toCollection(ArrayList::new));
                    roomName.setItems(FXCollections.observableArrayList(roomList));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
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
        visitReason.setDisable(false);
        lengthOfStay.setDisable(false);
        departmentName.setDisable(false);
        roomName.setDisable(false);
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
        roomName.setDisable(true);
    }

    private void surgeryClear() {
        surgeryName.setUnFocusColor(Paint.valueOf("black"));
        doctor.setUnFocusColor(Paint.valueOf("black"));
        surgeryDate.setDefaultColor(Paint.valueOf("black"));
        surgeryName.setValue(null);
        doctor.setValue(null);
        surgeryDate.setPromptText("");
        doctor.setDisable(true);
    }

    private void testClear() {
        testDate.setDefaultColor(Paint.valueOf("black"));
        nurse.setUnFocusColor(Paint.valueOf("black"));
        testName.setUnFocusColor(Paint.valueOf("black"));
        testName.setValue(null);
        nurse.setValue(null);
        testDate.setPromptText("");
        nurse.setDisable(true);
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
        if (phoneNumbers.isEmpty()) {
            addPhoneNumbers.setStyle("-fx-background-color: #ff0000");
            flag = false;
        } else {
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

    //must check if all dates are before current date (not test and surgery)
    private void datesValidation() {
        try {
            Date joinDT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(joinDate.getValue().toString() + " " + joinTime.getValue().toString() + ":00");
            Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth.getValue().toString());
            Date leaveDT = null;
            if (leaveTime.getValue() != null && leaveDate.getValue() != null){
                leaveDT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(leaveDate.getValue().toString() + " " + leaveTime.getValue().toString() + ":00");
            }else
            if (insuranceCheck.isSelected()) {
                Date inExpiry = new SimpleDateFormat("yyyy-MM-dd").parse(expiryDate.getValue().toString());
                if (inExpiry.compareTo(joinDT) < 0) {
                    expiryDate.setDefaultColor(Paint.valueOf("red"));
                } else {
                    expiryDate.setDefaultColor(Paint.valueOf("black"));
                }
            }
            if (!surgeryName.isDisabled() && leaveTime.getValue() != null && leaveDate.getValue() != null) {
                Date sDate = new SimpleDateFormat("yyyy-MM-dd").parse(surgeryDate.getValue().toString());
                if (sDate.compareTo(joinDT) < 0 || sDate.compareTo(leaveDT) >= 0) {
                    surgeryDate.setDefaultColor(Paint.valueOf("red"));
                } else {
                    surgeryDate.setDefaultColor(Paint.valueOf("black"));
                }
            }
            if (!testName.isDisabled() && leaveTime.getValue() != null && leaveDate.getValue() != null) {
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
            if (leaveDate.getValue() != null && leaveTime.getValue() != null &&leaveDT.compareTo(joinDT) < 0) {
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
        roomName.setDisable(true);
        disableSurgery(true);
        disableTest(true);
    }

    private void disableSurgery(Boolean b) {
        surgeryDate.setDisable(b);
        addSurgery.setDisable(b);
        sClear.setDisable(b);
        surgeryName.setDisable(b);
        doctor.setDisable(true);
    }

    private void disableTest(Boolean b) {
        tClear.setDisable(b);
        addTest.setDisable(b);
        testDate.setDisable(b);
        nurse.setDisable(true);
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
        doctorList = new ArrayList<>();
        nurseList = new ArrayList<>();
        departmentsSQL = new ArrayList<>();
        surgeriesSQL = new ArrayList<>();
        testsSQL = new ArrayList<>();
        roomsSQL = new ArrayList<>();
    }

    private void getData() throws SQLException, ClassNotFoundException, ParseException {
        String SQL;
        DBConnector.connectDB();

        SQL = "select distinct d.Department_id, d.Department_name, d.number_Of_Rooms, d.Department_floor\n" +
                "from department d, room r\n" +
                "where d.Department_id = r.Department_number and r.available_beds > 0;";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            departmentsSQL.add(
                    new Department(
                            Integer.parseInt(rs.getString(1)),
                            rs.getString(2),
                            Integer.parseInt(rs.getString(3)),
                            Integer.parseInt(rs.getString(4))
                    )
            );
        }

        SQL = "select distinct t.test_id, t.test_name, t.test_price, l.lab_id\n" +
                "from tests t, medicalstaff2tests2patient m2t2p, medicalstaff ms, lab l\n" +
                "where t.test_id = m2t2p.test_id and l.lab_id = t.lab_id and ms.staff_id = m2t2p.staff_id;";
        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            testsSQL.add(
                    new Tests(
                            Integer.parseInt(rs.getString(1)),
                            rs.getString(2),
                            Integer.parseInt(rs.getString(3)),
                            Integer.parseInt(rs.getString(4))
                    )
            );
        }

        SQL = "select distinct s.surgery_id, s.surgery_name, s.surgery_price\n" +
                "from surgeries s, medicalstaff2surgeries2patient m2s2p, medicalstaff ms\n" +
                 "where s.surgery_id = m2s2p.surgery_id and ms.staff_id = m2s2p.staff_id" + ";";
        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            surgeriesSQL.add(
                    new Surgeries(
                            Integer.parseInt(rs.getString(1)),
                            rs.getString(2),
                            Float.parseFloat(rs.getString(3))
                    )
            );
        }

        /*SQL = "select *\n" +
                "from Identity;";

        while (rs.next()) {
            testingID.add(new Identity(
                    Integer.parseInt(rs.getString(1)),
                    rs.getString(2),
                    new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(4)),
                    rs.getString(3),
                    rs.getString(5), rs.getString(6),
                    new int[Integer.parseInt(rs.getString(7))]
            ));
        }*/

        rs.close();
        stmt.close();

        DBConnector.getCon().close();
    }

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    private void getNurses() throws SQLException, ClassNotFoundException, ParseException {
        nursesSQL = new ArrayList<>();
        int testID = testsSQL.get(testName.getSelectionModel().getSelectedIndex()).getTestID();
        String SQL;
        DBConnector.connectDB();
        SQL = "select ID.identity_number, ID.full_name, ID.gender, ID.date_of_birth, ID.blood_type, ID.living_address\n" +
                "from identity ID, medicalstaff MS, tests T, medicalstaff2tests2patient M2T2P\n" +
                "where MS.staff_id = M2T2P.staff_id and ID.identity_number = MS.identity_number and T.test_id = " + testID + " and M2T2P.test_id = " + testID + ";";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);
        while (rs.next()) {
            nursesSQL.add(
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

    private void getRooms() throws SQLException, ClassNotFoundException, ParseException {
        roomsSQL = new ArrayList<>();
        int depNum = departmentsSQL.get(departmentName.getSelectionModel().getSelectedIndex()).getDepartmentID();
        String SQL;
        DBConnector.connectDB();
        SQL = "select distinct R.room_id, R.room_description, R.available_beds, R.total_number_of_beds, R.accommodation_cost, R.department_number\n" +
                "from Department D, Room R\n" +
                "where D.Department_id = R.Department_number and R.available_beds > 0 and D.Department_id = " + depNum + " and R.department_number = " + depNum + ";";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            roomsSQL.add(
                    new Room(
                            rs.getString(1),
                            rs.getString(2),
                            Integer.parseInt(rs.getString(3)),
                            Integer.parseInt(rs.getString(4)),
                            Float.parseFloat(rs.getString(5)),
                            Integer.parseInt(rs.getString(6))
                    )
            );
        }
        rs.close();
        stmt.close();

        DBConnector.getCon().close();
    }

    private void getDoctors() throws SQLException, ClassNotFoundException, ParseException {
        doctorsSQL = new ArrayList<>();
        int surgeryId = surgeriesSQL.get(surgeryName.getSelectionModel().getSelectedIndex()).getSurgery_id();
        String SQL;
        DBConnector.connectDB();
        SQL = "select ID.identity_number, ID.full_name, ID.gender, ID.date_of_birth, ID.blood_type, ID.living_address\n" +
                "from identity ID, medicalstaff MS, surgeries S, medicalstaff2surgeries2patient M2S2P\n" +
                "where MS.staff_id = M2S2P.staff_id and ID.identity_number = MS.identity_number and S.surgery_id = " + surgeryId + " and M2S2P.surgery_id = " + surgeryId + ";";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);
        while (rs.next()) {
            doctorsSQL.add(
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

    public static void setPhoneNumbers(ArrayList<String> pNums) {
        RegistrationController.phoneNumbers = pNums;
    }

    private void setData() throws SQLException, ClassNotFoundException, ParseException {
        Identity id = new Identity(
            Integer.parseInt(idNum.getText()),
                fullName.getText(),
                gender.getValue(),
                new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(dateOfBirth.getValue())),
                bloodType.getValue(),
                address.getText()
        );
        Patient patient = new Patient(
              visitReason.getValue(),
                emergencyStatus.getValue(),
                lengthOfStay.getText().isEmpty() ? null : Integer.parseInt(lengthOfStay.getText()),
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(joinDate.getValue() + " " + joinTime.getValue() + ":00"),
                leaveDate.getValue() != null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(leaveDate.getValue() + " " + leaveTime.getValue() + ":00") : null,
                Integer.parseInt(idNum.getText()),
                roomName.getValue()
        );
        Department department = new Department(

        );
        Surgeries surgery = new Surgeries();
        Tests test = new Tests();
        //if selected
        Insurance insurance = new Insurance();


        String SQL;
        DBConnector.connectDB();

        SQL = "select distinct d.Department_id, d.Department_name, d.number_Of_Rooms, d.Department_floor\n" +
                "from department d, room r\n" +
                "where d.Department_id = r.Department_number and r.available_beds > 0;";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            departmentsSQL.add(
                    new Department(
                            Integer.parseInt(rs.getString(1)),
                            rs.getString(2),
                            Integer.parseInt(rs.getString(3)),
                            Integer.parseInt(rs.getString(4))
                    )
            );
        }

        SQL = "select distinct t.test_id, t.test_name, t.test_price, l.lab_id\n" +
                "from tests t, medicalstaff2tests2patient m2t2p, medicalstaff ms, lab l\n" +
                "where t.test_id = m2t2p.test_id and l.lab_id = t.lab_id and ms.staff_id = m2t2p.staff_id;";
        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            testsSQL.add(
                    new Tests(
                            Integer.parseInt(rs.getString(1)),
                            rs.getString(2),
                            Integer.parseInt(rs.getString(3)),
                            Integer.parseInt(rs.getString(4))
                    )
            );
        }

        SQL = "select distinct s.surgery_id, s.surgery_name, s.surgery_price\n" +
                "from surgeries s, medicalstaff2surgeries2patient m2s2p, medicalstaff ms\n" +
                "where s.surgery_id = m2s2p.surgery_id and ms.staff_id = m2s2p.staff_id" + ";";
        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            surgeriesSQL.add(
                    new Surgeries(
                            Integer.parseInt(rs.getString(1)),
                            rs.getString(2),
                            Float.parseFloat(rs.getString(3))
                    )
            );
        }

        /*SQL = "select *\n" +
                "from Identity;";

        while (rs.next()) {
            testingID.add(new Identity(
                    Integer.parseInt(rs.getString(1)),
                    rs.getString(2),
                    new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(4)),
                    rs.getString(3),
                    rs.getString(5), rs.getString(6),
                    new int[Integer.parseInt(rs.getString(7))]
            ));
        }*/

        rs.close();
        stmt.close();

        DBConnector.getCon().close();

        /*try {
            DBConnector.connectDB();
            DBConnector.ExecuteStatement("Insert into Patient (record_number, length_of_stay, join_date_time, leave_date_time) values(" +
                    +p.getRecordNumber()+","
                    +p.getLengthOfStay()+",'"
                    + p.getJoinDateAndTimeToString() +"','"
                    + p.getLeaveDateAndTimeToString()+"');");
            System.out.println("Insert into Patient (record_number, length_of_stay, join_date_time, leave_date_time) values(" +
                    +p.getRecordNumber()+","
                    +p.getLengthOfStay()+",'"
                    + p.getJoinDateAndTimeToString() +"','"
                    + p.getLeaveDateAndTimeToString()+"');");
            DBConnector.ExecuteStatement("Insert into Identity (identity_number, idby_record_number, full_name, gender, date_of_birth, blood_type,living_address, phone_number) values(" +
                    +identity.getIdentityNumber()+","
                    +identity.getIdbyRecordNumber()+",'"
                    +identity.getFullName()+"','"
                    +identity.getGender()+"','"
                    +identity.getDateOfBirthToString()+"','"
                    +identity.getBloodType()+"','"
                    +identity.getLivingAddress()+"',"
                    +identity.getPhoneNumber()+");");
            System.out.println("Insert into Identity (identity_number, idby_record_number, full_name, gender, date_of_birth, blood_type,living_address, phone_number) values(" +
                    +identity.getIdentityNumber()+","
                    +identity.getIdbyRecordNumber()+",'"
                    +identity.getFullName()+"','"
                    +identity.getGender()+"','"
                    +identity.getDateOfBirthToString()+"','"
                    +identity.getBloodType()+"','"
                    +identity.getLivingAddress()+"',"
                    +identity.getPhoneNumber()+");");
            DBConnector.getCon().close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    private Department getDepartment() throws SQLException, ClassNotFoundException, ParseException{
        Department department = new Department();
        int depNum = departmentsSQL.get(departmentName.getSelectionModel().getSelectedIndex()).getDepartmentID();
        String roomNum = roomsSQL.get(roomName.getSelectionModel().getSelectedIndex()).getRoomID();
        String SQL;
        DBConnector.connectDB();
        SQL = "select distinct d.Department_id, d.Department_name, d.number_Of_Rooms, d.Department_floor\n" +
                "from department d, room r\n" +
                "where d.Department_id = " + depNum + " and r.Department_number = " + depNum +
                " and r.room_id = " + roomNum + ";";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);
        while (rs.next()) {
            department = new Department(
                    Integer.parseInt(rs.getString(1)),
                    rs.getString(2),
                    Integer.parseInt(rs.getString(3)),
                    Integer.parseInt(rs.getString(4))
            );
        }
        rs.close();
        stmt.close();
        DBConnector.getCon().close();
        return department;
    }
}
