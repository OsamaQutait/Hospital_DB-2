package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    private JFXComboBox<?> ComboChart;

    @FXML
    private JFXComboBox<?> ComboTable;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private TableView<?> Tpatient;

    @FXML
    private TableColumn<?, ?> Tid;

    @FXML
    private TableColumn<?, ?> Tname;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}
