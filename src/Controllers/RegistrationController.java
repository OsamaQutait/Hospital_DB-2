package Controllers;

import DatabaseConnector.DBConnector;
import Hospital.Identity;
import Hospital.Patient;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        disablingItems();
        initializingArrays();

        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
        spinnerValueFactory.setValue(0);
        payCoverage.setValueFactory(spinnerValueFactory);


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

        idClear.setOnAction((ActionEvent e) -> {
            idNum.clear();
            fullName.clear();
            dateOfBirth.setValue(null);
            address.clear();
            phoneNumber.clear();
            gender.setValue(null);
            bloodType.setValue(null);
        });

        inClear.setOnAction((ActionEvent e) -> {
            insuranceID.clear();
            expiryDate.setValue(null);
            payCoverage.getValueFactory().setValue(0);
            if (insuranceCheck.isSelected()) {
                insuranceCheck.fire();
            }
        });

        pClear.setOnAction((ActionEvent e) -> {
            emergencyStatus.setValue(null);
            visitReason.setValue(null);
            joinTime.setValue(null);
            joinDate.setValue(null);
            leaveDate.setValue(null);
            leaveTime.setValue(null);
            lengthOfStay.clear();
        });

        dClear.setOnAction((ActionEvent e) -> {
            departmentName.setValue(null);
            roomName.setValue(null);
        });

        sClear.setOnAction((ActionEvent e) -> {
            surgeryName.setValue(null);
            doctor.setValue(null);
        });

        tClear.setOnAction((ActionEvent e) -> {
            testName.setValue(null);
            nurse.setValue(null);
        });

        register.setOnAction((ActionEvent e) -> {

        });
    }
    @FXML
    private void eventID(ActionEvent event) {

        idNum.setText("test");
    }
    private Boolean isPatientInTheSystem(){
        return true;
    }
    private void disablingItems(){
        insuranceID.setDisable(true);
        payCoverage.setDisable(true);
        expiryDate.setDisable(true);
        surgeryName.setDisable(true);
        doctor.setDisable(true);
        testName.setDisable(true);
        nurse.setDisable(true);
        sClear.setDisable(true);
        addSurgery.setDisable(true);
        tClear.setDisable(true);
        addTest.setDisable(true);
    }
    private void initializingArrays(){
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
