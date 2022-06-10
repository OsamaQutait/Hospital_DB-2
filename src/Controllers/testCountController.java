package Controllers;

import DatabaseConnector.DBConnector;
import Hospital.Lab;
import Hospital.testCount;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class testCountController implements Initializable {

    private ArrayList<testCount> LabCountList;
    private ArrayList<Integer> LabIDList;
    private ArrayList<Lab> LabList;
    private ArrayList<String> LabNameList;
    private ArrayList<testCount> data;
    private ObservableList<testCount> dataList;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        LabCountList = new ArrayList<>();
        LabIDList = new ArrayList<>();
        LabList = new ArrayList<>();
        LabNameList = new ArrayList<>();
        data = new ArrayList<>();
        dataList = FXCollections.observableArrayList(data);

        try {
            getLabCount();
            getData();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dataList = FXCollections.observableArrayList(data);
        selectLab.setItems(FXCollections.observableArrayList(LabNameList));
        labID.setCellValueFactory(new PropertyValueFactory<testCount, Integer>("labID"));
        testCount1.setCellValueFactory(new PropertyValueFactory<testCount, Integer>("count"));
        table.setItems(dataList);
    }
    @FXML
    private JFXComboBox<String> selectLab;

    @FXML
    private JFXTextArea testCount;

    @FXML
    private JFXButton countTests;

    @FXML
    private JFXButton clear;

    @FXML
    private TableView<testCount> table;

    @FXML
    private TableColumn<testCount, Integer> labID;

    @FXML
    private TableColumn<testCount, Integer> testCount1;

    @FXML
    void clear(ActionEvent event) {
        selectLab.setValue(null);
        testCount.clear();

    }

    @FXML
    void countTests(ActionEvent event) {
        testCount.clear();
        int id = 0;
        for (Lab lab : LabList){
            if(lab.getLab_name().equals(selectLab.getValue())){
                id = lab.getLab_id();
                break;
            }
        }

        for(testCount count : LabCountList){
            if (count.getLabID() == id ){
                testCount.appendText(String.valueOf(count.getCount()));
                break;
            }
        }
    }

    @FXML
    void select_Lab(ActionEvent event) {

    }

    private void getLabCount()  throws SQLException, ClassNotFoundException, ParseException {
        String SQL;

        DBConnector.connectDB();
        System.out.println("Connection established");

        SQL = "select t.lab_id, count(*)\n" +
                "from lab l, tests t\n" +
                "where l.lab_id = t.test_id\n" +
                "group by t.lab_id; ";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            dataList.add(new testCount(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2))));
            LabCountList.add(new testCount(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2))));
        }
        rs.close();
        stmt.close();
        DBConnector.getCon().close();
    }
    private void getData()  throws SQLException, ClassNotFoundException, ParseException  {
        String SQL;

        DBConnector.connectDB();
        System.out.println("Connection established");

        SQL = "select *\n" + "from lab;";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            Lab s = new Lab(Integer.parseInt(rs.getString(1)),
                    rs.getString(2),
                    rs.getString(3));
            LabList.add(s);
            //dataList.add(s);
            LabNameList.add(rs.getString(2));
        }
        rs.close();
        stmt.close();
        DBConnector.getCon().close();
    }
}
