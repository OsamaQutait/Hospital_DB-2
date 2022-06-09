/*
 * Project: Database Hospital System
 * 
 * Authors: 
 * Kareem Afaneh - 1190359
 * Ali Mohammed - 1190502
 * Osama Qutait - 1191072
 * 
 * date : 9-6-2022
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import DatabaseConnector.DBConnector;
import Hospital.Department;
import Hospital.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DepartmentRoomsTablesController implements Initializable {

	@FXML
	private JFXButton BackButton;

	@FXML
	private JFXComboBox<String> DepChoice;

	@FXML
	private TableView<Department> TDepartment;

	@FXML
	private TableColumn<Department, Integer> TDid;

	@FXML
	private TableColumn<Department, String> TDname;

	@FXML
	private TableColumn<Department, String> TDfloor;

	@FXML
	private TableColumn<Department, Integer> TDMaxNoRooms;

	@FXML
	private JFXComboBox<String> RoomChoice;

	@FXML
	private TableView<Room> TRooms;

	@FXML
	private TableColumn<Room, String> TRid;

	@FXML
	private TableColumn<Room, Float> TRcost;

	@FXML
	private TableColumn<Room, Integer> TRtotalBeds;

	@FXML
	private TableColumn<Room, Integer> TRavailableBeds;

	@FXML
	private TableColumn<Room, String> TRdescription;

	ArrayList<String> departmentlistChoices;
	ArrayList<String> roomlistChoices;

	ArrayList<Department> departments;
	ArrayList<Room> rooms;
	ArrayList<Department> emptyDepartments;
	ArrayList<Room> emptyRooms;

	private ObservableList<Department> dataListDepartment;
	private ObservableList<Department> dataListDepartmentEmpty;
	private ObservableList<Room> dataListRoom;
	private ObservableList<Room> dataListRoomEmpty;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializingComboBoxes();
		assignComboBoxesValues();
		departments = new ArrayList<Department>();
		rooms = new ArrayList<Room>();
		emptyDepartments = new ArrayList<Department>();
		emptyRooms = new ArrayList<Room>();
		try {
			getData();
			getEmptyData();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		dataListDepartment = FXCollections.observableArrayList(departments);
		dataListDepartmentEmpty = FXCollections.observableArrayList(emptyDepartments);
		dataListRoom = FXCollections.observableArrayList(rooms);
		dataListRoomEmpty = FXCollections.observableArrayList(emptyRooms);
		BackButton.setOnAction((ActionEvent e) -> {
			backTodepartments();
		});
		DepChoice.setOnAction((ActionEvent e) -> {
			if(DepChoice.getValue().equals("All Departments"))
				ShowDepartments();
			else if(DepChoice.getValue().equals("Empty Departments only"))
				ShowEmptyDepartments();
		});
		RoomChoice.setOnAction((ActionEvent e) -> {
			if(RoomChoice.getValue().equals("All Rooms"))
				ShowRooms();
			else if(RoomChoice.getValue().equals("Empty Rooms only"))
				ShowEmptyRooms();
		});
	}

	public void initializingComboBoxes() {
		departmentlistChoices = new ArrayList<String>();
		roomlistChoices = new ArrayList<String>();
		departmentlistChoices.add("All Departments");
		departmentlistChoices.add("Empty Departments only");
		roomlistChoices.add("All Rooms");
		roomlistChoices.add("Empty Rooms only");
	}

	public void assignComboBoxesValues() {
		DepChoice.setItems(FXCollections.observableArrayList(departmentlistChoices));
		RoomChoice.setItems(FXCollections.observableArrayList(roomlistChoices));
	}

	public void getData() throws ClassNotFoundException, SQLException {
		String SQL;

		DBConnector.connectDB();

		System.out.println("Connection established");

		// add the departments
		SQL = "select * from Department;";
		Statement stmt = DBConnector.getCon().createStatement();
		ResultSet rs = stmt.executeQuery(SQL);

		while (rs.next())
			departments.add(new Department(Integer.parseInt(rs.getString(1)), rs.getString(2),
					Integer.parseInt(rs.getString(3)), rs.getString(4)));

		rs.close();
		stmt.close();

		// add the rooms
		SQL = "select * from room;";
		stmt = DBConnector.getCon().createStatement();
		rs = stmt.executeQuery(SQL);

		while (rs.next())
			rooms.add(new Room(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)),
					Integer.parseInt(rs.getString(4)), Float.parseFloat(rs.getString(5)),
					Integer.parseInt(rs.getString(6))));

		rs.close();
		stmt.close();
		DBConnector.getCon().close();
	}

	public void getEmptyData() throws ClassNotFoundException, SQLException {
		String SQL;

		DBConnector.connectDB();
		System.out.println("Connection established");

		SQL = "select D.Department_id,D.Department_name, number_Of_Rooms, Department_floor from Department D "
				+ "where D.Department_id not in (" + "select D.Department_id " + "from Department D, Room R "
				+ "where D.Department_id = R.Department_number );";
		Statement stmt = DBConnector.getCon().createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		while (rs.next()) {
			emptyDepartments.add(new Department(Integer.parseInt(rs.getString(1)), rs.getString(2),
					Integer.parseInt(rs.getString(3)), rs.getString(4)));
		}
		SQL = "select * " + " from Room R " + " where R.available_beds=R.total_number_of_beds;";
		stmt = DBConnector.getCon().createStatement();
		rs = stmt.executeQuery(SQL);

		while (rs.next()) {
			emptyRooms.add(new Room(rs.getString(1), rs.getString(2), Integer.parseInt(rs.getString(3)),
					Integer.parseInt(rs.getString(4)), Float.parseFloat(rs.getString(5)),
					Integer.parseInt(rs.getString(6))));
		}
		rs.close();
		stmt.close();
		DBConnector.getCon().close();

	}

	public void backTodepartments() {
		try {
			BackButton.getScene().getWindow().hide();
			Parent root = FXMLLoader.load(getClass().getResource("../screens/department.fxml"));
			Stage stage = new Stage();
			Scene scene = new Scene(root);
			stage.setTitle("Hospital Database");
			stage.setScene(scene);
			stage.show();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	public void ShowDepartments(){
		TDid.setCellValueFactory(new PropertyValueFactory<Department, Integer>("departmentID"));
		
		TDname.setCellValueFactory(new PropertyValueFactory<Department, String>("departmentName"));
		
		TDMaxNoRooms.setCellValueFactory(new PropertyValueFactory<Department, Integer>("numberOfRooms"));
		
		TDfloor.setCellValueFactory(new PropertyValueFactory<Department, String>("departmentFloor"));
		
		TDepartment.setItems(dataListDepartment);
	}
	
	public void ShowEmptyDepartments(){
		TDid.setCellValueFactory(new PropertyValueFactory<Department, Integer>("departmentID"));
		
		TDname.setCellValueFactory(new PropertyValueFactory<Department, String>("departmentName"));
		
		TDMaxNoRooms.setCellValueFactory(new PropertyValueFactory<Department, Integer>("numberOfRooms"));
		
		TDfloor.setCellValueFactory(new PropertyValueFactory<Department, String>("departmentFloor"));
		
		TDepartment.setItems(dataListDepartmentEmpty);
	}
	
	public void ShowRooms(){
		TRid.setCellValueFactory(new PropertyValueFactory<Room, String>("roomID"));
		
		TRcost.setCellValueFactory(new PropertyValueFactory<Room, Float>("AccommodationCost"));
		
		TRtotalBeds.setCellValueFactory(new PropertyValueFactory<Room, Integer>("totalNumberOfBeds"));
		
		TRavailableBeds.setCellValueFactory(new PropertyValueFactory<Room, Integer>("AvailableBeds"));
		
		TRdescription.setCellValueFactory(new PropertyValueFactory<Room, String>("roomDescription"));
		
		TRooms.setItems(dataListRoom);
	}
	
	public void ShowEmptyRooms(){
		TRid.setCellValueFactory(new PropertyValueFactory<Room, String>("roomID"));
		
		TRcost.setCellValueFactory(new PropertyValueFactory<Room, Float>("AccommodationCost"));
		
		TRtotalBeds.setCellValueFactory(new PropertyValueFactory<Room, Integer>("totalNumberOfBeds"));
		
		TRavailableBeds.setCellValueFactory(new PropertyValueFactory<Room, Integer>("AvailableBeds"));
		
		TRdescription.setCellValueFactory(new PropertyValueFactory<Room, String>("roomDescription"));
		
		TRooms.setItems(dataListRoomEmpty);
	}
}
