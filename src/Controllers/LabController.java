package Controllers;

import DatabaseConnector.DBConnector;
import Hospital.Lab;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class LabController implements Initializable {

    ArrayList<Lab> LabList;
    ArrayList<String> LabNameList;
    private ArrayList<Lab> data;
    private ObservableList<Lab> dataList;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        clearAll();
        LabList = new ArrayList<>();
        LabNameList = new ArrayList<>();
        data = new ArrayList<>();
        dataList = FXCollections.observableArrayList(data);
        try {
            getData();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assignComboBoxesValues();
        assignTableValues();
    }

    @FXML
    private TableView<Lab> table;

    @FXML
    private TableColumn<Lab, String> labNameTable;

    @FXML
    private TableColumn<Lab, Integer> labIDTable;

    @FXML
    private JFXTextField labName;

    @FXML
    private JFXTextField labID;

    @FXML
    private JFXTextField labDescription;

    @FXML
    private JFXButton insertLab;

    @FXML
    private JFXButton clearInsert;

    @FXML
    private JFXComboBox<String> selectLabDelete;

    @FXML
    private JFXButton deleteLab;

    @FXML
    private JFXButton clearDelete;

    @FXML
    private JFXComboBox<String> SelectUpLabName;

    @FXML
    private JFXTextField newDescription;

    @FXML
    private JFXButton updateLab;

    @FXML
    private JFXButton clearUpdate;

    @FXML
    void SelectToUpLabName(ActionEvent event) {

    }

    @FXML
    void clearDelete(ActionEvent event) {
        selectLabDelete.setValue(null);
    }

    @FXML
    void clearInsert(ActionEvent event) {
        labName.clear();
        labID.clear();
        labDescription.clear();
    }

    @FXML
    void clearUpdate(ActionEvent event) {
        SelectUpLabName.setValue(null);
        newDescription.clear();
    }

    @FXML
    void deleteLab(ActionEvent event) throws SQLException, ClassNotFoundException {
        int id = 0;
        for (Lab lab : LabList){
            if(lab.getLab_name().equals(selectLabDelete.getValue())){
                id = lab.getLab_id();
                break;
            }
        }
        String sql;
        sql = "delete from lab where lab_id = "+id;
        DBConnector.connectDB();
        DBConnector.ExecuteStatement(sql);
        DBConnector.getCon().close();
        assignComboBoxesValues();
        Predicate<Lab> pr = a -> (a.getLab_name().equals(selectLabDelete.getValue()));
        LabList.removeIf(pr);
        LabNameList.remove(selectLabDelete.getValue());
        dataList = FXCollections.observableArrayList(LabList);
        assignTableValues();
        assignComboBoxesValues();

    }

    @FXML
    void insertSurgery(ActionEvent event) throws SQLException, ClassNotFoundException {
        boolean flag = true;
        if (labName.getText().isEmpty() || labName.getText().length() > 32) {
            labName.setUnFocusColor(Color.RED);
            labName.clear();
            flag = false;
        } else {
            labName.setUnFocusColor(Color.BLACK);
        }
        if (labID.getText().isEmpty() || !isNumeric(labID.getText()) || !equaledID()) {
            labID.setUnFocusColor(Color.RED);
            labID.clear();
            flag = false;
        } else {
            labID.setUnFocusColor(Color.BLACK);
        }
        if (labDescription.getText().isEmpty() || labDescription.getText().length() > 100) {
            labDescription.setUnFocusColor(Color.RED);
            labDescription.clear();
            flag = false;
        } else {
            labDescription.setUnFocusColor(Color.BLACK);
        }
        if (flag) {
            LabList.add(new Lab(Integer.parseInt(labID.getText()),
                    labName.getText(),
                    labDescription.getText()));
            LabNameList.add(labName.getText());
            dataList = FXCollections.observableArrayList(LabList);
            assignComboBoxesValues();
            assignTableValues();
            String sql;
            sql = "Insert into lab values (" + Integer.parseInt(labID.getText()) + ",'" + labName.getText() + "',"
                    + "'" + labDescription.getText() + "'" + ")";
            DBConnector.connectDB();
            DBConnector.ExecuteStatement(sql);
            DBConnector.getCon().close();
        }
    }

    @FXML
    void selectLabToDelete(ActionEvent event) {

    }

    @FXML
    void updateLab(ActionEvent event) throws SQLException, ClassNotFoundException {
        boolean flag = true;
        if(newDescription.getText().isEmpty() || newDescription.getText().length() > 100){
            labDescription.setUnFocusColor(Color.RED);
            labDescription.clear();
            flag = false;
        }else {
            labDescription.setUnFocusColor(Color.BLACK);
        }
        if(flag){
            for (Lab lab : LabList){
                if(lab.getLab_name().equals(SelectUpLabName.getValue())){
                    lab.setLab_description(newDescription.getText());
                    dataList = FXCollections.observableArrayList(LabList);
                    assignTableValues();
                    String sql;
                    sql = "update lab set lab_description = " + " '"+ newDescription.getText() + "' " +
                            " where lab_id = " +lab.getLab_id();
                    DBConnector.connectDB();
                    DBConnector.ExecuteStatement(sql);
                    DBConnector.getCon().close();
                }
            }
        }
        assignComboBoxesValues();

    }
    public void clearAll(){
        labName.clear();
        labID.clear();
        labDescription.clear();
        selectLabDelete.setValue(null);
        SelectUpLabName.setValue(null);
        newDescription.clear();
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
            dataList.add(s);
            LabNameList.add(rs.getString(2));
        }
        rs.close();
        stmt.close();
        DBConnector.getCon().close();
    }

    public void assignComboBoxesValues() {
        selectLabDelete.setItems(FXCollections.observableArrayList(LabNameList));
        SelectUpLabName.setItems(FXCollections.observableArrayList(LabNameList));
    }

    public void assignTableValues(){
        labNameTable.setCellValueFactory(new PropertyValueFactory<Lab, String>("lab_name"));
        labIDTable.setCellValueFactory(new PropertyValueFactory<Lab, Integer>("lab_id"));
        table.setItems(dataList);
    }

    public static boolean isNumeric(String string) {
        int intValue;
        if(string == null || string.equals("")) {
            return false;
        }
        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    public static boolean isFloat(String string) {
        float floatValue;
        if(string == null || string.equals("")) {
            return false;
        }
        try {
            floatValue = Float.parseFloat(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }
    public boolean equaledID(){
        boolean flag = true;
        for (Lab lab : LabList){
            if(labID.getText().equals(String.valueOf(lab.getLab_id()))){
                flag = false;
                break;
            }
        }
        return flag;
    }

}
