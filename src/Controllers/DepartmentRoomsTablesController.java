package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class DepartmentRoomsTablesController implements Initializable{

    @FXML
    private JFXButton BackButton;

    @FXML
    private JFXComboBox<?> DepChoice;

    @FXML
    private TableView<?> TDepartment;

    @FXML
    private TableColumn<?, ?> TDid;

    @FXML
    private TableColumn<?, ?> TDname;

    @FXML
    private TableColumn<?, ?> TDfloor;

    @FXML
    private TableColumn<?, ?> TDMaxNoRooms;

    @FXML
    private TableColumn<?, ?> TDAssignedRooms;

    @FXML
    private JFXComboBox<?> RoomChoice;

    @FXML
    private TableView<?> TRooms;

    @FXML
    private TableColumn<?, ?> TRid;

    @FXML
    private TableColumn<?, ?> TRcost;

    @FXML
    private TableColumn<?, ?> TRtotalBeds;

    @FXML
    private TableColumn<?, ?> TRavailableBeds;

    @FXML
    private TableColumn<?, ?> TRdescription;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		BackButton.setOnAction((ActionEvent e) -> {
	        backTodepartments();
	    });
		
	}
	
	public void backTodepartments(){
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
}
