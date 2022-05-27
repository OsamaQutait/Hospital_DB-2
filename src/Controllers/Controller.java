// tesssssssssssssst
// salam Ali and Osama ... from Kareem 
// byee
package Controllers;

import Hospital.Identity;
import Hospital.Patient;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    @FXML
    private JFXTextArea identityNum;
    @FXML
    private JFXTextArea fullName1;
    @FXML
    private JFXTextArea dateOfBirth;
    @FXML
    private JFXTextArea gender;
    @FXML
    private JFXTextArea livingAddress;
    @FXML
    private JFXTextArea bloodType;
    @FXML
    private JFXTextArea phoneNumber;
    @FXML
    private JFXTextArea recordNumber;
    @FXML
    private JFXTextArea lengthOfStay;
    @FXML
    private JFXTimePicker joinTime;
    @FXML
    private JFXTimePicker leaveTime;
    @FXML
    private JFXButton insert;
    @FXML
    private JFXButton select;
    @FXML
    private JFXButton clear;
    @FXML
    private JFXToggleButton insertMode;
    @FXML
    private JFXDatePicker joinDate;
    @FXML
    private JFXDatePicker leaveDate;
    @FXML
    private TableView<Identity> patientTable;
    @FXML
    private TableColumn<Identity, String> fullName;
    @FXML
    private TableColumn<Identity, Integer> identityNumber;

    private String dbURL;
    private String dbUsername = "root";
    private String dbPassword = "1234";
    private String URL = "127.0.0.1";
    private String port = "3306";
    private String dbName = "hospital";
    private Connection con;

    private ArrayList<Patient> patients;
    private ArrayList<Identity> identities;
    private ObservableList<Identity> patientData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        patients = new ArrayList<>();
        identities = new ArrayList<>();
        try {
            getData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        patientData = FXCollections.observableArrayList(identities);
        fullName.setCellValueFactory(new PropertyValueFactory<Identity, String>("fullName"));
        identityNumber.setCellValueFactory(new PropertyValueFactory<Identity, Integer>("identityNumber"));
        patientTable.setItems(patientData);

        insertMode.setOnAction((ActionEvent e) -> {
            if (insertMode.isSelected()) {
                select.setDisable(true);
                recordNumber.setEditable(true);
                identityNum.setEditable(true);
                insert.setDisable(false);
                clearWindow();
            }else{
                select.setDisable(false);
                recordNumber.setEditable(false);
                identityNum.setEditable(false);
                insert.setDisable(true);
                clearWindow();
            }
        });
        insert.setDisable(true);
        insert.setOnAction((ActionEvent e) -> {
            List<Integer> idNum = identities.stream().map(Identity::getIdentityNumber).collect(Collectors.toList());
            List<Integer> recNum = patients.stream().map(Patient::getRecordNumber).collect(Collectors.toList());
            if (idNum.contains(Integer.parseInt(identityNum.getText())) || recNum.contains(Integer.parseInt(recordNumber.getText()))){
                identityNum.setUnFocusColor(Paint.valueOf("RED"));
                recordNumber.setUnFocusColor(Paint.valueOf("RED"));
            }else{
                Patient p;
                Identity id;
                try {
                    if (joinDate.getValue() == null)
                        System.out.println("NULL");
                    p = new Patient(Integer.parseInt(recordNumber.getText()),
                            Integer.parseInt(lengthOfStay.getText()),
                            (joinDate.getValue() == null || joinTime == null) ? null :new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(joinDate.getValue().toString() + " " + joinTime.getValue().toString() + ":00"),
                            (leaveDate.getValue() == null || leaveTime == null) ? null : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(leaveDate.getValue().toString() + " " + leaveTime.getValue().toString() + ":00")
                            );
                    id = new Identity(Integer.parseInt(identityNum.getText()),
                            Integer.parseInt(recordNumber.getText()),
                            fullName1.getText(),
                            new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth.getText()),
                            gender.getText(),
                            livingAddress.getText(),
                            bloodType.getText(),
                            Integer.parseInt(phoneNumber.getText()));
                    patients.add(p);
                    identities.add(id);
                    patientData.add(id);
                    insertData(p, id);
                    identityNum.setUnFocusColor(Color.color(0.3, 0.3, 0.3));
                    recordNumber.setUnFocusColor(Color.color(0.3, 0.3, 0.3));
                    patientTable.refresh();

                    insert.setDisable(true);
                    select.setDisable(false);
                    insertMode.fire();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
        });

        clear.setOnAction((ActionEvent e) -> {
            if (insertMode.isSelected()){
                insertMode.fire();
            }
            select.setDisable(false);
            recordNumber.setEditable(false);
            identityNum.setEditable(false);
            insert.setDisable(true);
            clearWindow();
        });

        select.setOnAction((ActionEvent e) -> {
            clearWindow();
            identityNum.setText(String.valueOf(patientTable.getSelectionModel().getSelectedItem().getIdentityNumber()));
            fullName1.setText(patientTable.getSelectionModel().getSelectedItem().getFullName());
            dateOfBirth.setText(patientTable.getSelectionModel().getSelectedItem().getDateOfBirthToString());
            gender.setText(patientTable.getSelectionModel().getSelectedItem().getGender());
            livingAddress.setText(patientTable.getSelectionModel().getSelectedItem().getLivingAddress());
            bloodType.setText(patientTable.getSelectionModel().getSelectedItem().getBloodType());
            phoneNumber.setText(String.valueOf(patientTable.getSelectionModel().getSelectedItem().getPhoneNumber()));
            Patient p = (patients.stream().filter(patient -> patient.getRecordNumber() == patientTable.getSelectionModel().getSelectedItem().getIdbyRecordNumber()).collect(Collectors.toList())).get(0);
            recordNumber.setText(String.valueOf(p.getRecordNumber()));
            lengthOfStay.setText(String.valueOf(p.getLengthOfStay()));
            joinDate.setValue(p.getJoinDateLocalDate());
            joinTime.setValue(p.getJoinTime());
            leaveDate.setValue(p.getLeaveDateLocalDate());
            leaveTime.setValue(p.getLeaveTime());
        });
    }
    private void insertData(Patient p, Identity identity){
        try {
            connectDB();
            ExecuteStatement("Insert into Patient (record_number, length_of_stay, join_date_time, leave_date_time) values(" +
                    +p.getRecordNumber()+","
                    +p.getLengthOfStay()+",'"
                    + p.getJoinDateAndTimeToString() +"','"
                    + p.getLeaveDateAndTimeToString()+"');");
            System.out.println("Insert into Patient (record_number, length_of_stay, join_date_time, leave_date_time) values(" +
                    +p.getRecordNumber()+","
                    +p.getLengthOfStay()+",'"
                    + p.getJoinDateAndTimeToString() +"','"
                    + p.getLeaveDateAndTimeToString()+"');");
            ExecuteStatement("Insert into Identity (identity_number, idby_record_number, full_name, gender, date_of_birth, blood_type,living_address, phone_number) values(" +
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
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearWindow(){
        identityNum.setUnFocusColor(Color.color(0.3, 0.3, 0.3));
        recordNumber.setUnFocusColor(Color.color(0.3, 0.3, 0.3));
        identityNum.clear();
        fullName1.clear();
        dateOfBirth.clear();
        gender.clear();
        livingAddress.clear();
        bloodType.clear();
        phoneNumber.clear();
        recordNumber.clear();
        lengthOfStay.clear();
        joinDate.setValue(null);
        joinTime.setValue(null);
        leaveDate.setValue(null);
        leaveTime.setValue(null);
    }
    private void getData() throws SQLException, ClassNotFoundException, ParseException {
        String SQL;

        connectDB();
        System.out.println("Connection established");

        SQL = "select *\n" +
                "from Patient P, Identity ID\n" +
                "where P.record_number = ID.idby_record_number";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            patients.add(new Patient(Integer.parseInt(rs.getString(1)),
                    Integer.parseInt(rs.getString(2)),
                    new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString(3)),
                    rs.getString(4) == null ? null : new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(rs.getString(4))));
            identities.add(new Identity(Integer.parseInt(rs.getString(5)),
                    Integer.parseInt(rs.getString(6)),
                    rs.getString(7),
                    new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(9)),
                    rs.getString(8),
                    rs.getString(10),
                    rs.getString(11),
                    Integer.parseInt(rs.getString(12))
            ));
        }
        rs.close();
        stmt.close();

        con.close();
    }

    private void connectDB() throws ClassNotFoundException, SQLException {
        dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
        Properties p = new Properties();
        p.setProperty("user", dbUsername);
        p.setProperty("password", dbPassword);
        p.setProperty("useSSL", "false");
        p.setProperty("autoReconnect", "true");
        Class.forName("com.mysql.jdbc.Driver");

        con = DriverManager.getConnection(dbURL, p);
    }

    public void ExecuteStatement(String SQL) throws SQLException {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
        } catch (SQLException s) {
            s.printStackTrace();
            System.out.println("SQL statement is not executed!");

        }
    }
}
