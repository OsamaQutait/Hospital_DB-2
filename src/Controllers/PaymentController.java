package Controllers;
//yearly: according to year
//monthly: according to month

import DatabaseConnector.DBConnector;
import Hospital.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    @FXML
    private Button registrationButton;

    @FXML
    private TableView<Identity> pTable;

    @FXML
    private TableColumn<Identity, Integer> pId;

    @FXML
    private TableColumn<Identity, String> pName;

    @FXML
    private TableView<Surgeries> surgeryTable;

    @FXML
    private TableColumn<Surgeries, String> sName;

    @FXML
    private TableColumn<Surgeries, Float> sPrice;

    @FXML
    private TableView<Tests> testsTable;

    @FXML
    private TableColumn<Tests, String> tName;

    @FXML
    private TableColumn<Tests, Float> tPrice;

    @FXML
    private JFXTextField surgeryBill;

    @FXML
    private JFXTextField testBill;

    @FXML
    private JFXTextField hospitalFees;

    @FXML
    private JFXTextField coverage;

    @FXML
    private JFXTextField totalBIll;

    @FXML
    private JFXTextField invoice;

    @FXML
    private JFXTextField roomCost;

    @FXML
    private JFXTextField hosStay;

    @FXML
    private JFXButton reports;

    @FXML
    private JFXButton createBill;

    @FXML
    private Label roomLabel;

    private ArrayList<Identity> patientsSQL;
    private ObservableList<Identity> patientsOBS;

    private ArrayList<Surgeries> surgeriesSQL;
    private ObservableList<Surgeries> surgeriesOBS;

    private ArrayList<Tests> testsSQL;
    private ObservableList<Tests> testsOBS;

    private float testsPrice;
    private float surgeriesPrice;
    private final float hospitalFeesValue = 1000;
    private final float deadFeesValue = 550;

    private Payment payment;
    private Insurance insurance = null;
    private Identity identity = null;
    private Patient patient = null;
    private Room room = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            patientsSQL = new ArrayList<>();
            surgeriesSQL = new ArrayList<>();
            testsSQL = new ArrayList<>();
            getData();
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

        createBill.setOnAction((ActionEvent e) -> {
            surgeriesSQL = new ArrayList<>();
            testsSQL = new ArrayList<>();
            payment = new Payment();

            identity = pTable.getSelectionModel().getSelectedItem();
            if (identity != null){
                try {
                    getSurgeries();
                    getTests();
                    getInsurance();
                    getPatient();
                    getRoom();
                    if (patient.getEmergencyStatus().equals("Dead")){
                        roomLabel.setText("Death cost");
                    }else{
                        roomLabel.setText("Room cost");
                    }
                    tName.setCellValueFactory(new PropertyValueFactory<Tests, String>("testName"));
                    tPrice.setCellValueFactory(new PropertyValueFactory<Tests, Float>("testPrice"));
                    testsOBS = FXCollections.observableArrayList(testsSQL);
                    testsTable.setItems(testsOBS);

                    sName.setCellValueFactory(new PropertyValueFactory<Surgeries, String>("surgery_name"));
                    sPrice.setCellValueFactory(new PropertyValueFactory<Surgeries, Float>("surgery_price"));
                    surgeriesOBS = FXCollections.observableArrayList(surgeriesSQL);
                    surgeryTable.setItems(surgeriesOBS);

                    if (patient != null && room != null) {
                        surgeriesOBS = FXCollections.observableArrayList(surgeriesSQL);
                        surgeryTable.setItems(surgeriesOBS);

                        testsOBS = FXCollections.observableArrayList(testsSQL);
                        testsTable.setItems(testsOBS);

                        int coverageValue = insurance == null ? 0 : insurance.getPaymentCoverage();
                        surgeryBill.setText(String.valueOf(surgeriesPrice));
                        testBill.setText(String.valueOf(testsPrice));
                        hospitalFees.setText(String.valueOf(hospitalFeesValue));

                        coverage.setText(String.valueOf(coverageValue) + "%");

                        float valueOfBill = hospitalFeesValue + surgeriesPrice + testsPrice + patient.getLengthOfStay() * room.getAccommodationCost();
                        valueOfBill = Math.round(valueOfBill * 100) / 100.0f;
                        float valueOfInvoice = (float) ((1.0 - coverageValue / 100.0) * valueOfBill);
                        valueOfInvoice = Math.round(valueOfInvoice * 100) / 100.0f;

                        if (patient.getEmergencyStatus().equals("Dead")) {
                            valueOfBill = hospitalFeesValue + deadFeesValue * patient.getLengthOfStay();
                            valueOfBill = Math.round(valueOfBill * 100) / 100.0f;
                            totalBIll.setText(String.valueOf(valueOfBill));
                            invoice.setText(String.valueOf(valueOfBill));
                            roomCost.setText(String.valueOf(deadFeesValue));
                        }else{
                            totalBIll.setText(String.valueOf(valueOfBill));
                            invoice.setText(String.valueOf(valueOfInvoice));
                            roomCost.setText(String.valueOf(room.getAccommodationCost()));
                        }

                        hosStay.setText(String.valueOf(Math.round(patient.getLengthOfStay() * 10) / 10.0f) + " days");
                        payment.setCoverage(coverageValue);
                        payment.setInvoice(valueOfInvoice);
                        payment.setTotal_bill(valueOfBill);
                        payment.setIdentity_number(patient.getIdentityNumber());
                        payment.setIssued_date(new Date());
                        if (!paymentExistence(payment.getIdentity_number())) {
                            setPayment();
                        }
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

        reports.setOnAction((ActionEvent e) -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../screens/paymentReports.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                //stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }


    private void getData() throws SQLException, ClassNotFoundException, ParseException {
        String SQL;
        DBConnector.connectDB();

        SQL = "select distinct id.identity_number, id.full_name, id.gender, id.date_of_birth, id.blood_type, id.living_address\n" +
                "from identity id, patient p\n" +
                "where id.identity_number = p.identity_number and p.stay_leave_date_time is not null;";
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


    private void setPayment() throws SQLException, ClassNotFoundException, ParseException {
        DBConnector.connectDB();
        try {
            DBConnector.ExecuteStatement("Insert into Payment (issued_date, invoice, total_bill, coverage, identity_number) values("
                    + payment.getIssued_dateToString() + ", "
                    + payment.getInvoice() + ", "
                    + payment.getTotal_bill() + ", "
                    + payment.getCoverage() + ", "
                    + payment.getIdentity_number() + ");");
            DBConnector.getCon().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void getSurgeries() throws SQLException, ClassNotFoundException, ParseException {
        String SQL;
        DBConnector.connectDB();
        SQL = "select s.surgery_id, s.surgery_name, s.surgery_price\n" +
                "from surgeries s, medicalstaff2surgeries2patient m\n" +
                "where s.surgery_id = m.surgery_id and m.identity_number = " + identity.getIdentityNumber() + ";";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        boolean flag = false;
        while (rs.next()) {
            surgeriesSQL.add(new Surgeries(
                    Integer.parseInt(rs.getString(1)),
                    rs.getString(2),
                    Float.parseFloat(rs.getString(3))
            ));
            flag = true;
        }


        if (flag) {
            SQL = "select sum(s.surgery_price)\n" +
                    "from surgeries s, medicalstaff2surgeries2patient m\n" +
                    "where s.surgery_id = m.surgery_id and m.identity_number = " + identity.getIdentityNumber() + ";";
            stmt = DBConnector.getCon().createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                surgeriesPrice = Float.parseFloat(rs.getString(1));
            }
        }else{
            surgeriesPrice = 0;
        }
        rs.close();
        stmt.close();
        DBConnector.getCon().close();
    }

    private void getTests() throws SQLException, ClassNotFoundException, ParseException {
        String SQL;
        DBConnector.connectDB();
        SQL = "select t.test_id, t.test_name, t.test_price, l.lab_id\n" +
                "from tests t, medicalstaff2tests2patient m2t2p, lab l\n" +
                "where l.lab_id = t.lab_id and t.test_id = m2t2p.test_id and m2t2p.identity_number = " + identity.getIdentityNumber() + ";";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        boolean flag = false;
        while (rs.next()) {
            testsSQL.add(new Tests(
                    Integer.parseInt(rs.getString(1)),
                    rs.getString(2),
                    Float.parseFloat(rs.getString(3))
            ));
            flag = true;
        }


        if (flag) {
            SQL = "select distinct sum(t.test_price) \n" +
                    "from tests t, medicalstaff2tests2patient m2t2p\n" +
                    "where t.test_id = m2t2p.test_id and m2t2p.identity_number = " + identity.getIdentityNumber() + ";";
            stmt = DBConnector.getCon().createStatement();
            rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                testsPrice = Float.parseFloat(rs.getString(1));
            }
        }else{
            testsPrice = 0;
        }
        rs.close();
        stmt.close();
        DBConnector.getCon().close();
    }

    private void getInsurance() throws SQLException, ClassNotFoundException, ParseException {
        String SQL;
        DBConnector.connectDB();
        SQL = "select distinct insur.insurance_id, insur.payment_coverage, insur.expire_date, insur.identity_number\n" +
                "from patient p, insurance insur\n" +
                "where p.identity_number = " + identity.getIdentityNumber() + " and insur.identity_number = " + identity.getIdentityNumber() + ";";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()) {
            insurance = new Insurance(
                    Integer.parseInt(rs.getString(1)),
                    Integer.parseInt(rs.getString(2)),
                    rs.getString(3) == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(3)),
                    Integer.parseInt(rs.getString(4))
            );
        }
        rs.close();
        stmt.close();
        DBConnector.getCon().close();
    }

    private void getPatient() throws SQLException, ClassNotFoundException, ParseException {
        String SQL;
        DBConnector.connectDB();
        SQL = "select distinct p.visit_reason, p.emergency_status, p.stay_length_of_stay, p.stay_join_date_time, p.stay_leave_date_time, p.identity_number, p.Room_id\n" +
                "from patient p, identity id\n" +
                "where id.identity_number = " + identity.getIdentityNumber() + " and p.identity_number = " + identity.getIdentityNumber() + ";";
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
        rs.close();
        stmt.close();
        DBConnector.getCon().close();
    }

    private void getRoom() throws SQLException, ClassNotFoundException, ParseException {
        String SQL;
        DBConnector.connectDB();
        SQL = "select distinct R.Room_id, r.Room_Description, R.available_beds, R.total_number_of_beds, R.accommodation_cost, R.Department_number\n" +
                "from patient p, room R\n" +
                "where R.Room_id = '" + patient.getRoomID()  + "';";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            room = new Room(
                    rs.getString(1), rs.getString(2),
                    Integer.parseInt(rs.getString(3)),
                    Integer.parseInt(rs.getString(4)),
                    Float.parseFloat(rs.getString(5)),
                    Integer.parseInt(rs.getString(6))
            );
        }
        rs.close();
        stmt.close();
        DBConnector.getCon().close();
    }

    private boolean paymentExistence(int id) throws SQLException, ClassNotFoundException, ParseException {
        String SQL;
        DBConnector.connectDB();
        SQL = "select distinct * \n" +
                "from payment p \n" +
                "where p.identity_number =  " + id + ";";
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
