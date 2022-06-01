package Controllers;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import DatabaseConnector.DBConnector;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class DepartmentController implements Initializable {

	@FXML
	private JFXTextField DepartmentID;

	@FXML
	private JFXTextField DepartmentName;

	@FXML
	private JFXComboBox<String> DepartmentFloor;

	@FXML
	private JFXComboBox<String> MaxNoRooms;

	@FXML
	private JFXButton DepInsert;

	@FXML
	private JFXButton DepClear;

	@FXML
	private JFXTextField AccoCost;

	@FXML
	private JFXTextField RoomDescription;

	@FXML
	private JFXTextField RoomID;

	@FXML
	private JFXComboBox<String> NumOfBeds;

	@FXML
	private JFXButton RoomInsert;

	@FXML
	private JFXButton RoomClear;

	@FXML
	private JFXComboBox<String> DelDepartID;

	@FXML
	private JFXButton DelDepartDelete;

	@FXML
	private JFXButton DelDepartClear;

	@FXML
	private JFXComboBox<String> DelRoomID;

	@FXML
	private JFXButton DelRoomDelete;

	@FXML
	private JFXButton DelRoomClear;

	@FXML
	private JFXComboBox<String> UpDepartID;

	@FXML
	private JFXTextField UpDepartName;

	@FXML
	private JFXTextField UpDepartNoRooms;

	@FXML
	private JFXButton UpDepartUpdate;

	@FXML
	private JFXButton UpDepartClear;

	@FXML
	private JFXComboBox<String> UpRoomID;

	@FXML
	private JFXTextField UpRoomDescription;

	@FXML
	private JFXTextField UpRoomAcco;

	@FXML
	private JFXComboBox<String> UpRoomNoBeds;

	@FXML
	private JFXButton UpRoomUpdate;

	@FXML
	private JFXButton UpRoomClear;

	ArrayList<String> MaxRoomlist;
	ArrayList<String> DepartmentFloorlist;
	ArrayList<String> MaxNumBedslist;
	ArrayList<String> roomlist;
	ArrayList<String> departmentlist;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clearAll();
		try {
			comboBoxesInitializing();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assignComboBoxesValues();
		
		// the clear button for department section (clear all elements)
		DepClear.setOnAction((ActionEvent e) -> {
			DepartmentClear();
        });
		DepInsert.setOnAction((ActionEvent e) -> {
			newDepartmentValidation();
        });
		// the clear button for room section (clear all elements)
		RoomClear.setOnAction((ActionEvent e) -> {
			RoomClear();
        });
		// the clear button for delete department section (clear all elements)
		DelDepartClear.setOnAction((ActionEvent e) -> {
			DeleteDepartmentClear();
        });
		// the clear button for delete room section (clear all elements)
		DelRoomClear.setOnAction((ActionEvent e) -> {
			DeleteRoomClear();
        });
		// the clear button for Update department section (clear all elements)
		UpDepartClear.setOnAction((ActionEvent e) -> {
			UpdateDepartmentClear();
        });
		// the clear button for Update room section (clear all elements)
		UpRoomClear.setOnAction((ActionEvent e) -> {
			UpdateRoomClear();
        });
	}

	// this method call all clear sub methods
	public void clearAll() {
		DepartmentClear();
		RoomClear();
		DeleteDepartmentClear();
		DeleteRoomClear();
		UpdateDepartmentClear();
		UpdateRoomClear();
	}

	// this method will clear the Department insertion section
	public void DepartmentClear() {
		DepartmentID.clear();
		DepartmentName.clear();
		MaxNoRooms.setValue(null);
		DepartmentFloor.setValue(null);
		DepartmentID.setFocusColor(Color.BLACK);
		DepartmentName.setFocusColor(Color.BLACK);
		MaxNoRooms.setFocusColor(Color.BLACK);
		DepartmentFloor.setFocusColor(Color.BLACK);
	}

	// this method will clear the room insertion section
	public void RoomClear() {
		RoomID.clear();
		AccoCost.clear();
		RoomDescription.clear();
		NumOfBeds.setValue(null);
		RoomID.setFocusColor(Color.BLACK);
		AccoCost.setFocusColor(Color.BLACK);
		RoomDescription.setFocusColor(Color.BLACK);
		NumOfBeds.setFocusColor(Color.BLACK);
	}

	// this method will clear the Delete Department section
	public void DeleteDepartmentClear() {
		DelDepartID.setValue(null);
		DelDepartID.setFocusColor(Color.BLACK);
	}

	// this method will clear the Delete room section
	public void DeleteRoomClear() {
		DelRoomID.setValue(null);
		DelRoomID.setFocusColor(Color.BLACK);
	}

	// this method will clear the update Department section
	public void UpdateDepartmentClear() {
		UpDepartID.setValue(null);
		UpDepartName.clear();
		UpDepartNoRooms.clear();
		UpDepartID.setFocusColor(Color.BLACK);
		UpDepartName.setFocusColor(Color.BLACK);
		UpDepartNoRooms.setFocusColor(Color.BLACK);

	}

	// this method will clear the update room section
	public void UpdateRoomClear() {
		UpRoomID.setValue(null);
		UpRoomNoBeds.setValue(null);
		UpRoomDescription.clear();
		UpRoomAcco.clear();
		UpRoomID.setFocusColor(Color.BLACK);
		UpRoomNoBeds.setFocusColor(Color.BLACK);
		UpRoomDescription.setFocusColor(Color.BLACK);
		UpRoomAcco.setFocusColor(Color.BLACK);
	}

	public void comboBoxesInitializing() throws ParseException {
		// MaxNoRooms range = 1-99
		MaxRoomlist = new ArrayList<>();
		for (int i = 1; i < 100; i++) {
			String tmp = "" + i;
			MaxRoomlist.add(tmp);
		}
		// Department floors range is 0-9
		DepartmentFloorlist = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			String tmp = "" + i;
			DepartmentFloorlist.add(tmp);
		}
		// number of beds at a room range 1-4
		MaxNumBedslist = new ArrayList<>();
		for (int i = 1; i < 5; i++) {
			String tmp = "" + i;
			MaxNumBedslist.add(tmp);
		}
		// to read the data from the SQL and save them
		intitializeDepartmentAndRoom();
	}

	public void intitializeDepartmentAndRoom() throws ParseException {
		String SQL;
		roomlist = new ArrayList<>();
		departmentlist = new ArrayList<>();
		try {
			DBConnector.connectDB();
			System.out.println("Connection established");

			SQL = "select D.Department_id\n" + 
					"from Department D;";
			Statement stmt = DBConnector.getCon().createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				departmentlist.add(rs.getString(1));
			}
			SQL = "select R.Room_id\n" + 
					"from room R;";
			stmt = DBConnector.getCon().createStatement();
			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				roomlist.add(rs.getString(1));
			}
			rs.close();
	        stmt.close();
	        DBConnector.getCon().close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void assignComboBoxesValues(){
		MaxNoRooms.setItems(FXCollections.observableArrayList(MaxRoomlist));
		DepartmentFloor.setItems(FXCollections.observableArrayList(DepartmentFloorlist));
		NumOfBeds.setItems(FXCollections.observableArrayList(MaxNumBedslist));
		DelRoomID.setItems(FXCollections.observableArrayList(roomlist));
		UpRoomID.setItems(FXCollections.observableArrayList(roomlist));
		DelDepartID.setItems(FXCollections.observableArrayList(departmentlist));
		UpDepartID.setItems(FXCollections.observableArrayList(departmentlist));
		UpRoomNoBeds.setItems(FXCollections.observableArrayList(MaxNumBedslist));
	}
	public void newDepartmentValidation(){
		boolean flag=true;
		if (DepartmentID.getText().length() != 9 || !Pattern.matches("[0-9]{9}", DepartmentID.getText())) {
			DepartmentID.setUnFocusColor(Paint.valueOf("RED"));
			DepartmentID.clear();
			flag=false;
        }else{
        	DepartmentID.setUnFocusColor(Paint.valueOf("black"));
        }
		if (MaxNoRooms.getSelectionModel().isEmpty()) {
			MaxNoRooms.setUnFocusColor(Paint.valueOf("RED"));
			MaxNoRooms.setValue(null);
			flag=false;
        }else{
        	MaxNoRooms.setUnFocusColor(Paint.valueOf("black"));
        }
		if (DepartmentFloor.getSelectionModel().isEmpty()) {
			DepartmentFloor.setUnFocusColor(Paint.valueOf("RED"));
			DepartmentFloor.setValue(null);
			flag=false;
        }else{
        	DepartmentFloor.setUnFocusColor(Paint.valueOf("black"));
        }
		if(flag){
			DepartmentClear();
		}
	}
}
