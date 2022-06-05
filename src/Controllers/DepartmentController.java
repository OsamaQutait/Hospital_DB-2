package Controllers;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

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
	private JFXComboBox<String> UpDepartNoRooms;

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

	@FXML
	private JFXComboBox<String> depatmentRoom;

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
			e.printStackTrace();
		}
		assignComboBoxesValues();

		// the clear button for department section (clear all elements)
		DepClear.setOnAction((ActionEvent e) -> {
			DepartmentClear();
		});
		// the insert button for the department section
		DepInsert.setOnAction((ActionEvent e) -> {
			newDepartmentValidation();
		});
		// the clear button for room section (clear all elements)
		RoomClear.setOnAction((ActionEvent e) -> {
			RoomClear();
		});
		// the insert button for the room section
		RoomInsert.setOnAction((ActionEvent e) -> {
			newRoomValidation();
		});
		// the clear button for delete department section (clear all elements)
		DelDepartClear.setOnAction((ActionEvent e) -> {
			DeleteDepartmentClear();
		});
		// the delete button for the department delete section
		DelDepartDelete.setOnAction((ActionEvent e) -> {
			DeleteDepartmentValidation();
		});
		// the clear button for delete room section (clear all elements)
		DelRoomClear.setOnAction((ActionEvent e) -> {
			DeleteRoomClear();
		});
		// the delete button for the room delete section
		DelRoomDelete.setOnAction((ActionEvent e) -> {
			DeleteRoomValidation();
		});
		// the clear button for Update department section (clear all elements)
		UpDepartClear.setOnAction((ActionEvent e) -> {
			UpdateDepartmentClear();
		});
		// the Update button for the department update section
		UpDepartUpdate.setOnAction((ActionEvent e) -> {
			UpdateDepartmentValidation();
		});
		// the clear button for Update room section (clear all elements)
		UpRoomClear.setOnAction((ActionEvent e) -> {
			UpdateRoomClear();
		});
		// the Update button for the room update section
		UpRoomUpdate.setOnAction((ActionEvent e) -> {
			UpdateRoomValidation();
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
		DepartmentID.setUnFocusColor(Color.BLACK);
		DepartmentName.setUnFocusColor(Color.BLACK);
		MaxNoRooms.setUnFocusColor(Color.BLACK);
		DepartmentFloor.setUnFocusColor(Color.BLACK);
	}

	// this method will clear the room insertion section
	public void RoomClear() {
		RoomID.clear();
		AccoCost.clear();
		RoomDescription.clear();
		NumOfBeds.setValue(null);
		depatmentRoom.setValue(null);
		RoomID.setUnFocusColor(Color.BLACK);
		AccoCost.setUnFocusColor(Color.BLACK);
		RoomDescription.setUnFocusColor(Color.BLACK);
		NumOfBeds.setUnFocusColor(Color.BLACK);
		depatmentRoom.setUnFocusColor(Color.BLACK);
	}

	// this method will clear the Delete Department section
	public void DeleteDepartmentClear() {
		DelDepartID.setValue(null);
		DelDepartID.setUnFocusColor(Color.BLACK);
	}

	// this method will clear the Delete room section
	public void DeleteRoomClear() {
		DelRoomID.setValue(null);
		DelRoomID.setUnFocusColor(Color.BLACK);
	}

	// this method will clear the update Department section
	public void UpdateDepartmentClear() {
		UpDepartID.setValue(null);
		UpDepartName.clear();
		UpDepartNoRooms.setValue(null);
		UpDepartID.setUnFocusColor(Color.BLACK);
		UpDepartName.setUnFocusColor(Color.BLACK);
		UpDepartNoRooms.setUnFocusColor(Color.BLACK);

	}

	// this method will clear the update room section
	public void UpdateRoomClear() {
		UpRoomID.setValue(null);
		UpRoomNoBeds.setValue(null);
		UpRoomDescription.clear();
		UpRoomAcco.clear();
		UpRoomID.setUnFocusColor(Color.BLACK);
		UpRoomNoBeds.setUnFocusColor(Color.BLACK);
		UpRoomDescription.setUnFocusColor(Color.BLACK);
		UpRoomAcco.setUnFocusColor(Color.BLACK);
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
		initializeDepartmentAndRoom();
	}

	// this method is used to initialize Department And Room lists
	public void initializeDepartmentAndRoom() throws ParseException {
		String SQL;
		roomlist = new ArrayList<>();
		departmentlist = new ArrayList<>();
		try {
			DBConnector.connectDB();
			System.out.println("Connection established");

			SQL = "select D.Department_id\n" + "from Department D;";
			Statement stmt = DBConnector.getCon().createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				departmentlist.add(rs.getString(1));
			}
			SQL = "select R.Room_id\n" + "from room R;";
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

	// in this method we assign ComboBoxes the values
	public void assignComboBoxesValues() {
		MaxNoRooms.setItems(FXCollections.observableArrayList(MaxRoomlist));
		DepartmentFloor.setItems(FXCollections.observableArrayList(DepartmentFloorlist));
		NumOfBeds.setItems(FXCollections.observableArrayList(MaxNumBedslist));
		DelRoomID.setItems(FXCollections.observableArrayList(roomlist));
		UpRoomID.setItems(FXCollections.observableArrayList(roomlist));
		DelDepartID.setItems(FXCollections.observableArrayList(departmentlist));
		UpDepartID.setItems(FXCollections.observableArrayList(departmentlist));
		UpRoomNoBeds.setItems(FXCollections.observableArrayList(MaxNumBedslist));
		depatmentRoom.setItems(FXCollections.observableArrayList(departmentlist));
		UpDepartNoRooms.setItems(FXCollections.observableArrayList(MaxRoomlist));
	}

	// in this method we make a validation for the department section attributes
	public void newDepartmentValidation() {
		boolean flag = true;
		if (DepartmentID.getText().isEmpty() || DepartmentID.getText().length() > 9
				|| !Pattern.matches("[0-9]", DepartmentID.getText())) {
			DepartmentID.setUnFocusColor(Color.RED);
			DepartmentID.clear();
			// "The inserted name was more than 32 characters!"
			errorPop("Not Vaild Department ID, please get sure to insert an integer number with 9 digits max!");
	        System.out.print("why");
			flag = false;
		} else {
			DepartmentID.setUnFocusColor(Color.BLACK);
		}
		if (DepartmentName.getText().length() > 32) {
			DepartmentName.setUnFocusColor(Color.RED);
			DepartmentName.clear();
			flag = false;
		} else {
			DepartmentName.setUnFocusColor(Color.BLACK);
		}
		if (MaxNoRooms.getSelectionModel().isEmpty()) {
			MaxNoRooms.setUnFocusColor(Color.RED);
			MaxNoRooms.setValue(null);
			flag = false;
		} else {
			MaxNoRooms.setUnFocusColor(Color.BLACK);
		}
		if (DepartmentFloor.getSelectionModel().isEmpty()) {
			DepartmentFloor.setUnFocusColor(Color.RED);
			DepartmentFloor.setValue(null);
			flag = false;
		} else {
			DepartmentFloor.setUnFocusColor(Color.BLACK);
		}
		if (flag) {
			DepartmentClear();
		}
	}

	// in this method we make a validation for the room section attributes
	public void newRoomValidation() {
		boolean flag = true;
		if (RoomID.getText().isEmpty() || RoomID.getText().length() > 2
				|| !Pattern.matches("[0-9]", RoomID.getText())) {
			RoomID.setUnFocusColor(Color.RED);
			RoomID.clear();
			flag = false;
		} else if (RoomID.getText().length() == 1 || Pattern.matches("[0-9]", RoomID.getText())) {
			// add 0 with the number chosen (e.g 3 ==> 03)
			RoomID.setText("0" + RoomID.getText());
			RoomID.setUnFocusColor(Color.BLACK);
		} else {
			RoomID.setUnFocusColor(Color.BLACK);
		}
		if (AccoCost.getText().isEmpty() || (!Pattern.matches("[+]?[0-9]*\\.?[0-9]+", AccoCost.getText())
				&& !Pattern.matches("[0-9]", AccoCost.getText()))) {
			AccoCost.setUnFocusColor(Color.RED);
			AccoCost.clear();
			flag = false;
		} else {
			AccoCost.setUnFocusColor(Color.BLACK);
		}
		if (RoomDescription.getText().length() > 128) {
			RoomDescription.setUnFocusColor(Color.RED);
			RoomDescription.clear();
			flag = false;
		} else {
			RoomDescription.setUnFocusColor(Color.BLACK);
		}
		if (NumOfBeds.getSelectionModel().isEmpty()) {
			NumOfBeds.setUnFocusColor(Color.RED);
			NumOfBeds.setValue(null);
			flag = false;
		} else {
			NumOfBeds.setUnFocusColor(Color.BLACK);
		}
		if (depatmentRoom.getSelectionModel().isEmpty()) {
			depatmentRoom.setUnFocusColor(Color.RED);
			depatmentRoom.setValue(null);
			flag = false;
		} else {
			depatmentRoom.setUnFocusColor(Color.BLACK);
		}
		if (flag) {
			RoomClear();
		}
	}

	// in this method we make a validation for the delete department section
	// attributes
	public void DeleteDepartmentValidation() {
		boolean flag = true;
		if (DelDepartID.getSelectionModel().isEmpty()) {
			DelDepartID.setUnFocusColor(Color.RED);
			DelDepartID.setValue(null);
			flag = false;
		} else {
			DelDepartID.setUnFocusColor(Color.BLACK);
		}
		if (flag) {
			DeleteDepartmentClear();
		}
	}

	// in this method we make a validation for the delete room section
	// attributes
	public void DeleteRoomValidation() {
		boolean flag = true;
		if (DelRoomID.getSelectionModel().isEmpty()) {
			DelRoomID.setUnFocusColor(Color.RED);
			DelRoomID.setValue(null);
			flag = false;
		} else {
			DelRoomID.setUnFocusColor(Color.BLACK);
		}
		if (flag) {
			DeleteRoomClear();
		}
	}

	// in this method we make a validation for the Update department section
	// attributes
	public void UpdateDepartmentValidation() {
		boolean flag = true;
		if (UpDepartID.getSelectionModel().isEmpty()) {
			UpDepartID.setUnFocusColor(Color.RED);
			UpDepartID.setValue(null);
			flag = false;
		} else {
			UpDepartID.setUnFocusColor(Color.BLACK);
		}
		if (UpDepartName.getText().length() > 32) {
			UpDepartName.setUnFocusColor(Color.RED);
			UpDepartName.clear();
			flag = false;
		} else {
			UpDepartName.setUnFocusColor(Color.BLACK);
		}
		if (UpDepartNoRooms.getSelectionModel().isEmpty()) {
			UpDepartNoRooms.setUnFocusColor(Color.RED);
			UpDepartNoRooms.setValue(null);
			flag = false;
		} else {
			UpDepartNoRooms.setUnFocusColor(Color.BLACK);
		}
		if (flag) {
			UpdateDepartmentClear();
		}
	}

	// in this method we make a validation for the Update room section
	// attributes
	public void UpdateRoomValidation() {
		boolean flag = true;
		if (UpRoomID.getSelectionModel().isEmpty()) {
			UpRoomID.setUnFocusColor(Color.RED);
			UpRoomID.setValue(null);
			flag = false;
		} else {
			UpRoomID.setUnFocusColor(Color.BLACK);
		}
		if (UpRoomDescription.getText().length() > 128) {
			UpRoomDescription.setUnFocusColor(Color.RED);
			UpRoomDescription.clear();
			flag = false;
		} else {
			UpRoomDescription.setUnFocusColor(Color.BLACK);
		}
		if (UpRoomAcco.getText().isEmpty() || (!Pattern.matches("[+]?[0-9]*\\.?[0-9]+", UpRoomAcco.getText())
				&& !Pattern.matches("[0-9]", UpRoomAcco.getText()))) {
			UpRoomAcco.setUnFocusColor(Color.RED);
			UpRoomAcco.clear();
			flag = false;
		} else {
			UpRoomAcco.setUnFocusColor(Color.BLACK);
		}
		if (UpRoomNoBeds.getSelectionModel().isEmpty()) {
			UpRoomNoBeds.setUnFocusColor(Color.RED);
			UpRoomNoBeds.setValue(null);
			flag = false;
		} else {
			UpRoomNoBeds.setUnFocusColor(Color.BLACK);
		}
		if (flag) {
			UpdateRoomClear();
		}
	}
	public void errorPop(String str){
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../screens/error.fxml"));
        primaryStage.setTitle("error");
        Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        ErrorMessage alertController = loader.getController();
        alertController.setErrorLabel(str);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
	}
}
