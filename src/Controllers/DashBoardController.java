package Controllers;

import DatabaseConnector.DBConnector;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable{

    @FXML
    private Button RegistrationButton;

    @FXML
    private Button PatientsButton;

    @FXML
    private Button DepartmentsButton;

    @FXML
    private Button LabsButton;

    @FXML
    private Button SurgeriesButton;

    @FXML
    private Button PaymentButton;

    @FXML
    private Button DashboardButton;

    @FXML
    private Button StaffButton;

    @FXML
    private Button TestsButton;

    @FXML
    private Label CurrentStaff;

    @FXML
    private Label CurrentPatient;

    @FXML
    private Label availableSurgeries;

    @FXML
    private Label availableTests;

    @FXML
    private JFXComboBox<String> ComboChart;

    @FXML
    private JFXComboBox<String> ComboTable;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private TableView<?> Tpatient;

    @FXML
    private TableColumn<?, ?> Tid;

    @FXML
    private TableColumn<?, ?> Tname;
    
    ArrayList<String> selectChart;
    ArrayList<String> selectTable;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clearAll();
		assignComboBoxes();
		try {
			findingCurrentStaff();
			findingCurrentPatients();
			findingCurrentSurgery();
			findingCurrentTests();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}		
		RegistrationButton.setOnAction((ActionEvent e) -> {
			openNewWindow("registration");
		});
		PatientsButton.setOnAction((ActionEvent e) -> {
			openNewWindow("patients");
		});
		DepartmentsButton.setOnAction((ActionEvent e) -> {
			openNewWindow("department");
		});
		LabsButton.setOnAction((ActionEvent e) -> {
			openNewWindow("lab");
		});
		SurgeriesButton.setOnAction((ActionEvent e) -> {
			openNewWindow("surgery");
		});
		PaymentButton.setOnAction((ActionEvent e) -> {
			openNewWindow("payment");
		});
		StaffButton.setOnAction((ActionEvent e) -> {
			openNewWindow("medicalStaff");
		});
		TestsButton.setOnAction((ActionEvent e) -> {
			openNewWindow("tests");
		});
	}
	
	public void clearAll(){
		ComboChart.setValue(null);
		ComboTable.setValue(null);
		ComboChart.setUnFocusColor(Color.BLACK);
		ComboTable.setUnFocusColor(Color.BLACK);
	}
	
	public void assignComboBoxes(){
		selectChart = new ArrayList<>();
		selectChart.add("Empty rooms in each floor");
		
		selectTable = new ArrayList<>();
		selectTable.add("All Dead People");
		
		
		ComboChart.setItems(FXCollections.observableArrayList(selectChart));
		ComboTable.setItems(FXCollections.observableArrayList(selectTable));
	}
	
	public void findingCurrentStaff() throws ClassNotFoundException, SQLException{
		String SQL;

		DBConnector.connectDB();

		System.out.println("Connection established");

		
		SQL = " select count(*) " + " from MedicalStaff ;";
		Statement stmt = DBConnector.getCon().createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		int num = 0;
		while (rs.next())
			num = Integer.parseInt(rs.getString(1));

		rs.close();
		stmt.close();
		CurrentStaff.setText(num+"");
		DBConnector.getCon().close();
	}
	
	public void findingCurrentPatients() throws ClassNotFoundException, SQLException{
		String SQL;

		DBConnector.connectDB();

		System.out.println("Connection established");


		SQL = " select count(*) " + " from Patient P\n"+
		"where p.emergency_status!='dead' ;";
		Statement stmt = DBConnector.getCon().createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		int num = 0;
		while (rs.next())
			num = Integer.parseInt(rs.getString(1));

		rs.close();
		stmt.close();
		CurrentPatient.setText(num+"");
		DBConnector.getCon().close();
	}

	public void findingCurrentSurgery() throws ClassNotFoundException, SQLException{
		availableSurgeries.setText(null);

		String SQL;

		DBConnector.connectDB();

		System.out.println("Connection established");


		SQL = " select count(*) " + " from surgeries\n";
		Statement stmt = DBConnector.getCon().createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		int num = 0;
		while (rs.next())
			num = Integer.parseInt(rs.getString(1));

		rs.close();
		stmt.close();
		availableSurgeries.setText(num+"");
		DBConnector.getCon().close();
	}

	public void findingCurrentTests() throws ClassNotFoundException, SQLException{
		availableTests.setText(null);

		String SQL;

		DBConnector.connectDB();

		System.out.println("Connection established");


		SQL = " select count(*) " + " from tests\n";
		Statement stmt = DBConnector.getCon().createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		int num = 0;
		while (rs.next())
			num = Integer.parseInt(rs.getString(1));

		rs.close();
		stmt.close();
		availableTests.setText(num+"");
		DBConnector.getCon().close();
	}
	
	public void openNewWindow(String str){
		try {
			CurrentPatient.getScene().getWindow().hide();
			Parent root = FXMLLoader.load(getClass().getResource("../screens/"+str+".fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setTitle("Hospital Database");
			stage.setScene(scene);
			stage.show();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}
