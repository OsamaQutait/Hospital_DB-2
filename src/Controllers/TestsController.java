package Controllers;

import DatabaseConnector.DBConnector;
import Hospital.Tests;
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

public class TestsController implements Initializable {

    @FXML
    private JFXTextField testName;

    @FXML
    private JFXTextField testID;

    @FXML
    private JFXTextField testPrice;

    @FXML
    private JFXTextField testLab;

    @FXML
    private JFXButton insertTest;

    @FXML
    private JFXButton clearInsert;

    @FXML
    private JFXComboBox<String> selectTestDelete;

    @FXML
    private JFXButton deleteTest;

    @FXML
    private JFXButton clearDelete;

    @FXML
    private JFXComboBox<String> SelectUpTestName;

    @FXML
    private JFXTextField newPrice;

    @FXML
    private JFXButton updateTestPrice;

    @FXML
    private JFXButton clearUpdatePrice;

    @FXML
    private JFXComboBox<String> selectUpTsetLab;

    @FXML
    private JFXButton upTestLabID;

    @FXML
    private JFXButton clearLab;

    @FXML
    private JFXTextField newLabID;

    @FXML
    private TableView<Tests> table;

    @FXML
    private TableColumn<Tests, String> NameColumn;

    @FXML
    private TableColumn<Tests, Integer> IDColumn;


    @FXML
    void SelectUpTestName(ActionEvent event) {
        SelectUpTestName.setItems(FXCollections.observableArrayList(TestsNameList));
    }

    @FXML
    void clearDeleteButton(ActionEvent event) {
        selectTestDelete.setValue(null);
    }

    @FXML
    void clearInsert(ActionEvent event) {
        testName.clear();
        testID.clear();
        testPrice.clear();
        testLab.clear();
    }

    @FXML
    void clearLab(ActionEvent event) {
        selectUpTsetLab.setValue(null);
    }

    @FXML
    void clearUpdatePrice(ActionEvent event) {
        SelectUpTestName.setValue(null);
    }
    ArrayList<Tests> TestsList;
    ArrayList<String> TestsNameList;
    private ArrayList<Tests> data;
    private ObservableList<Tests> dataList;
    ArrayList<Integer> labIDList;

    @FXML
    void deleteTestButton(ActionEvent event) throws SQLException, ClassNotFoundException {
        int id = 0;
        for (Tests test : TestsList){
            if(test.getTestName().equals(selectTestDelete.getValue())){
                id = test.getTestID();
                break;
            }
        }
        String sql;
        sql = "delete from tests where test_id = "+id;
        DBConnector.connectDB();
        DBConnector.ExecuteStatement(sql);
        DBConnector.getCon().close();
        Predicate<Tests> pr = a -> (a.getTestName().equals(selectTestDelete.getValue()));
        TestsList.removeIf(pr);
        TestsNameList.remove(selectTestDelete.getValue());
        dataList = FXCollections.observableArrayList(TestsList);
        assignTableValues();
        assignComboBoxesValues();

    }

    @FXML
    void insertTestButton(ActionEvent event) throws SQLException, ClassNotFoundException {
        assignComboBoxesValues();
        boolean flag = true;
        if (testName.getText().isEmpty() || testName.getText().length() > 32) {
            testName.setUnFocusColor(Color.RED);
            testName.clear();
            flag = false;
        }else{
            testName.setUnFocusColor(Color.BLACK);
        }
        if(testID.getText().isEmpty() || !isNumeric(testID.getText()) || !equaledID()){
            testID.setUnFocusColor(Color.RED);
            testID.clear();
            flag = false;
        }else {
            testID.setUnFocusColor(Color.BLACK);
        }
        if(testPrice.getText().isEmpty() || !isFloat(testPrice.getText())){
            testPrice.setUnFocusColor(Color.RED);
            testPrice.clear();
            flag = false;
        }else {
            testPrice.setUnFocusColor(Color.BLACK);
        }
        if(testLab.getText().isEmpty() || !isNumeric(testLab.getText()) || !labIDList.contains(Integer.parseInt(testLab.getText()))){
            testLab.setUnFocusColor(Color.RED);
            testLab.clear();
            flag = false;
        }else {
            testLab.setUnFocusColor(Color.BLACK);
        }
        if(flag){
            TestsList.add(new Tests(Integer.parseInt(testID.getText()),
                    testName.getText(),
                    Float.parseFloat(testPrice.getText()),
                    Integer.parseInt(testLab.getText())));
            TestsNameList.add(testName.getText());
            dataList = FXCollections.observableArrayList(TestsList);
            assignTableValues();
            assignComboBoxesValues();
            String sql;
            sql = "Insert into tests values (" + Integer.parseInt(testID.getText()) + ",'" + testName.getText() + "',"
                    + Float.parseFloat(testPrice.getText()) + "," + Integer.parseInt(testLab.getText()) +")";
            DBConnector.connectDB();
            DBConnector.ExecuteStatement(sql);
            DBConnector.getCon().close();
        }

    }

    @FXML
    void selectTestDelete(ActionEvent event) {
        selectTestDelete.setItems(FXCollections.observableArrayList(TestsNameList));
    }

    @FXML
    void selectUpTsetLab(ActionEvent event) {
        selectUpTsetLab.setItems(FXCollections.observableArrayList(TestsNameList));
    }

    @FXML
    void upTestLabID(ActionEvent event) throws SQLException, ClassNotFoundException {
        boolean flag = true;
        if(newLabID.getText().isEmpty() || !isNumeric(newLabID.getText()) || !labIDList.contains(Integer.parseInt(testLab.getText()))){
            newLabID.setUnFocusColor(Color.RED);
            newLabID.clear();
            flag = false;
        }else {
            newLabID.setUnFocusColor(Color.BLACK);
        }
        if(flag){
            for (Tests test : TestsList){
                if(test.getTestName().equals(selectUpTsetLab.getValue())){
                    test.setLabID(Integer.parseInt(newLabID.getText()));
                    dataList = FXCollections.observableArrayList(TestsList);
                    assignTableValues();
                    String sql;
                    sql = "update tests set lab_id = " + Integer.parseInt(newLabID.getText()) +
                            " where test_id = " +test.getTestID();
                    DBConnector.connectDB();
                    DBConnector.ExecuteStatement(sql);
                    DBConnector.getCon().close();
                }
            }
        }
        assignComboBoxesValues();
    }

    @FXML
    void updateTestPrice(ActionEvent event) throws SQLException, ClassNotFoundException {
        boolean flag = true;
        if(newPrice.getText().isEmpty() || !isFloat(newPrice.getText())){
            newPrice.setUnFocusColor(Color.RED);
            newPrice.clear();
            flag = false;
        }else {
            newPrice.setUnFocusColor(Color.BLACK);
        }
        if(flag){
            for (Tests test : TestsList){
                if(test.getTestName().equals(SelectUpTestName.getValue())){
                    test.setTestPrice(Float.parseFloat(newPrice.getText()));
                    dataList = FXCollections.observableArrayList(TestsList);
                    assignTableValues();
                    String sql;
                    sql = "update tests set test_price = " + Float.parseFloat(newPrice.getText()) +
                            " where test_id = " +test.getTestID();
                    DBConnector.connectDB();
                    DBConnector.ExecuteStatement(sql);
                    DBConnector.getCon().close();
                }
            }
        }
        assignComboBoxesValues();
        assignTableValues();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        clearAll();
        TestsList = new ArrayList<>();
        TestsNameList = new ArrayList<>();
        labIDList = new ArrayList<>();
        data = new ArrayList<>();
        dataList = FXCollections.observableArrayList(data);
        assignTableValues();
        try {
            getData();
            getLabID();
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
    private void getData() throws SQLException, ClassNotFoundException, ParseException {
        String SQL;

        DBConnector.connectDB();
        System.out.println("Connection established");

        SQL = "select *\n" + "from tests;";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            TestsList.add( new Tests(Integer.parseInt(rs.getString(1)),
                    rs.getString(2),
                    Float.parseFloat(rs.getString(3)),
                    Integer.parseInt(rs.getString(4))));
            TestsNameList.add(rs.getString(2));
            dataList.add(new Tests(Integer.parseInt(rs.getString(1)),
                    rs.getString(2),
                    Float.parseFloat(rs.getString(3)),
                    Integer.parseInt(rs.getString(4))));
        }
        rs.close();
        stmt.close();
        DBConnector.getCon().close();
    }

    private void getLabID() throws SQLException, ClassNotFoundException, ParseException {
        String SQL;
        DBConnector.connectDB();
        SQL = "select lab_id\n" + "from lab;";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()) {
            labIDList.add(Integer.parseInt(rs.getString(1)));
        }
        rs.close();
        stmt.close();
        DBConnector.getCon().close();
    }

    public void clearAll(){
        testName.clear();
        testID.clear();
        testPrice.clear();
        testLab.clear();
        selectUpTsetLab.setValue(null);
        selectTestDelete.setValue(null);
        newPrice.clear();
        SelectUpTestName.setValue(null);
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
    public void assignComboBoxesValues() {
        selectTestDelete.setItems(FXCollections.observableArrayList(TestsNameList));
        SelectUpTestName.setItems(FXCollections.observableArrayList(TestsNameList));
        selectUpTsetLab.setItems(FXCollections.observableArrayList(TestsNameList));
    }

    public boolean equaledID(){
        boolean flag = true;
        for (Tests test : TestsList){
            if(testID.getText().equals(String.valueOf(test.getTestID()))){
                flag = false;
                break;
            }
        }
        return flag;
    }
    public void assignTableValues(){
        NameColumn.setCellValueFactory(new PropertyValueFactory<Tests, String>("testName"));
        IDColumn.setCellValueFactory(new PropertyValueFactory<Tests, Integer>("testID"));
        table.setItems(dataList);
    }
}
