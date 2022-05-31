package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;

public class DepartmentController implements Initializable{

    @FXML
    private JFXTextField MaxNoRooms;

    @FXML
    private JFXTextField DepartmentFloor;

    @FXML
    private JFXTextField DepartmentID;

    @FXML
    private JFXTextField DepartmentName;

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
    private JFXComboBox<?> NumOfBeds;

    @FXML
    private JFXButton RoomInsert;

    @FXML
    private JFXButton RoomClear;

    @FXML
    private JFXComboBox<?> DelDepartID;

    @FXML
    private JFXButton DelDepartDelete;

    @FXML
    private JFXButton DelDepartClear;

    @FXML
    private JFXComboBox<?> DelRoomID;

    @FXML
    private JFXButton DelRoomDelete;

    @FXML
    private JFXButton DelRoomClear;

    @FXML
    private JFXComboBox<?> UpDepartID;

    @FXML
    private JFXTextField UpDepartName;

    @FXML
    private JFXTextField UpDepartNoRooms;

    @FXML
    private JFXButton UpDepartUpdate;

    @FXML
    private JFXButton UpDepartClear;

    @FXML
    private JFXComboBox<?> UpRoomID;

    @FXML
    private JFXTextField UpRoomDescription;

    @FXML
    private JFXTextField UpRoomAcco;

    @FXML
    private JFXComboBox<?> UpRoomNoBeds;

    @FXML
    private JFXButton UpRoomUpdate;

    @FXML
    private JFXButton UpRoomClear;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clearAll();
	}
	// this method call all clear sub methods 
	public void clearAll(){
		DepartmentClear();
		RoomClear();
		DeleteDepartmentClear();
		DeleteRoomClear();
		UpdateDepartmentClear();
		UpdateRoomClear();
	}
	// this method will clear the Department insertion section
	public void DepartmentClear(){
		DepartmentID.clear();
		DepartmentName.clear();
		MaxNoRooms.clear();
		DepartmentFloor.clear();
		DepartmentID.setFocusColor(Color.BLACK);
		DepartmentName.setFocusColor(Color.BLACK);
		MaxNoRooms.setFocusColor(Color.BLACK);
		DepartmentFloor.setFocusColor(Color.BLACK);
	}
	// this method will clear the room insertion section
	public void RoomClear(){
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
	public void DeleteDepartmentClear(){
		DelDepartID.setValue(null);
		DelDepartID.setFocusColor(Color.BLACK);
	}
	// this method will clear the Delete room section
	public void DeleteRoomClear(){
		DelRoomID.setValue(null);
		DelRoomID.setFocusColor(Color.BLACK);
	}
	// this method will clear the update Department section
	public void UpdateDepartmentClear(){
		UpDepartID.setValue(null);
		UpDepartName.clear();
		UpDepartNoRooms.clear();
		UpDepartID.setFocusColor(Color.BLACK);
		UpDepartName.setFocusColor(Color.BLACK);
		UpDepartNoRooms.setFocusColor(Color.BLACK);
		
	}
	// this method will clear the update room section
	public void UpdateRoomClear(){
		UpRoomID.setValue(null);
		UpRoomNoBeds.setValue(null);
		UpRoomDescription.clear();
		UpRoomAcco.clear();
		UpRoomID.setFocusColor(Color.BLACK);
		UpRoomNoBeds.setFocusColor(Color.BLACK);
		UpRoomDescription.setFocusColor(Color.BLACK);
		UpRoomAcco.setFocusColor(Color.BLACK);
	}
}
