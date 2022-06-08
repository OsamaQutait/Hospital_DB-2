package Controllers;

import DatabaseConnector.DBConnector;
import Hospital.Tests;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @FXML
    void deleteTestButton(ActionEvent event) {
        assignComboBoxesValues();
        Predicate<Tests> pr = a -> (a.getTestName().equals(selectTestDelete.getValue()));
        TestsNameList.remove(selectTestDelete.getValue());
        /*for (Tests test : TestsList){
            System.out.println(test.getTestName());
        }*/
        assignComboBoxesValues();
    }

    @FXML
    void insertTestButton(ActionEvent event) {
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
        if(testLab.getText().isEmpty() || !isNumeric(testLab.getText())){
            testLab.setUnFocusColor(Color.RED);
            testLab.clear();
            flag = false;
        }else {
            testLab.setUnFocusColor(Color.BLACK);
        }
        if(flag){
            TestsList.add(new Tests(Integer.parseInt(testID.getText()),
                    testName.getText(),
                    Float.parseFloat(testName.getText()),
                    Integer.parseInt(testLab.getText())));
            TestsNameList.add(testName.getText());
            //System.out.println(SurgeryList.get(SurgeryList.size()-1).getSurgery_name()+SurgeryList.get(SurgeryList.size()-1).getSurgery_id());
            //System.out.print(testID.getText() + "..." + testName.getText() + "..." + testName.getText()+ "..." +testLab.getText());
            assignComboBoxesValues();
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
    void upTestLabID(ActionEvent event) {
        boolean flag = true;
        if(newLabID.getText().isEmpty() || !isNumeric(newLabID.getText())){
            newLabID.setUnFocusColor(Color.RED);
            newLabID.clear();
            flag = false;
        }else {
            newLabID.setUnFocusColor(Color.BLACK);
        }
        if(flag){
            for (Tests test : TestsList){
                if(test.getTestName().equals(SelectUpTestName.getValue())){
                    test.setLabID(Integer.parseInt(newLabID.getText()));
                }
            }
        }
        assignComboBoxesValues();
    }

    @FXML
    void updateTestPrice(ActionEvent event) {
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
                    //System.out.println(surgeri.getSurgery_price());
                    test.setTestPrice(Float.parseFloat(newPrice.getText()));
                    //System.out.println(surgeri.getSurgery_price());
                }
            }
        }
        assignComboBoxesValues();
    }
    ArrayList<Tests> TestsList;
    ArrayList<String> TestsNameList;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        try {
            TestsList = new ArrayList<>();
            TestsNameList = new ArrayList<>();
            getData();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assignComboBoxesValues();
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
            if(testID.getText().equals(test.getTestID())){
                flag = false;
                break;
            }
        }
        return flag;
    }
}
