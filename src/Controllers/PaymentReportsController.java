package Controllers;

import DatabaseConnector.DBConnector;
import com.jfoenix.controls.JFXDatePicker;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class PaymentReportsController implements Initializable {
    @FXML
    private Button lifeStatus;

    @FXML
    private Button profits;

    @FXML
    private Label tDate;

    @FXML
    private JFXDatePicker expiryDate;

    @FXML
    private JFXDatePicker sDate;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private PieChart pieChart;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*manualSearch.setOnAction((ActionEvent e) -> {
            manualSearch.setSelected(true);
            tableSearch.setSelected(false);
        });*/
        lifeStatus.setStyle("-fx-background-color: #3b5998;" +
                "-fx-text-fill: #dfe3ee;");
        profits.setStyle("-fx-background-color: #3b5998;" +
                "-fx-text-fill: #dfe3ee;");

        pieChart.setVisible(false);
        barChart.setVisible(false);

        lifeStatus.setOnAction((ActionEvent e) -> {
            lifeStatus.setStyle("-fx-background-color: #dfe3ee;" +
                    "-fx-text-fill: #3b5998;");
            profits.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            try {
                getLifeStat();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });

        profits.setOnAction((ActionEvent e) -> {
            lifeStatus.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            profits.setStyle("-fx-background-color: #dfe3ee;" +
                    "-fx-text-fill: #3b5998;");
            try {
                getBloodType();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });
    }

    private void getGender() throws SQLException, ClassNotFoundException, ParseException {
        pieChart.getData().removeAll(Collections.singleton(pieChart.getData().setAll()));
        pieChart.setVisible(true);
        barChart.setVisible(false);
        ObservableList<PieChart.Data> pieChartData;
        ArrayList<PieChart.Data> data = new ArrayList<>();

        String SQL;
        DBConnector.connectDB();

        SQL = "select id.gender, count(*)\n" +
                "from identity id, patient p\n" +
                "where id.identity_number = p.identity_number\n"+
                "group by id.gender;";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            data.add(new PieChart.Data(rs.getString(1), Integer.parseInt(rs.getString(2))));
        }

        rs.close();
        stmt.close();

        DBConnector.getCon().close();

        pieChartData = FXCollections.observableArrayList(data);
        pieChartData.forEach(data1 ->
                data1.nameProperty().bind(
                        Bindings.concat(
                                " No. of ", data1.getName(), ": ", data1.pieValueProperty()
                        )
                ));

        pieChart.getData().addAll(pieChartData);
    }

    private void getLifeStat() throws SQLException, ClassNotFoundException, ParseException {
        pieChart.getData().removeAll(Collections.singleton(pieChart.getData().setAll()));
        pieChart.setVisible(true);
        barChart.setVisible(false);
        ObservableList<PieChart.Data> pieChartData;
        ArrayList<PieChart.Data> data = new ArrayList<>();

        boolean aliveFlag = false;
        boolean deadFlag = false;

        String SQL;
        DBConnector.connectDB();

        SQL = "select p.emergency_status, sum(pay.total_bill)\n" +
                "from patient p, payment pay\n" +
                "where p.identity_number = pay.identity_number\n"+
                "group by p.emergency_status;";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            data.add(new PieChart.Data(rs.getString(1), Integer.parseInt(rs.getString(2))));
            if (rs.getString(1).equals("Alive")){
                aliveFlag = true;
            }
            if (rs.getString(1).equals("Dead")){
                deadFlag = true;
            }
        }
        if (!deadFlag){
            data.add(new PieChart.Data("Dead", 0));
        }
        if (!aliveFlag){
            data.add(new PieChart.Data("Alive", 0));
        }

        rs.close();
        stmt.close();

        DBConnector.getCon().close();

        pieChartData = FXCollections.observableArrayList(data);
        pieChartData.forEach(data1 ->
                data1.nameProperty().bind(
                        Bindings.concat(
                                "Profit of ", data1.getName(), ": ", data1.pieValueProperty()
                        )
                ));

        pieChart.getData().addAll(pieChartData);
    }

    private void getBloodType() throws SQLException, ClassNotFoundException, ParseException {
        ArrayList<String> bloodList = new ArrayList<>();
        bloodList.add("O-");
        bloodList.add("O+");
        bloodList.add("A-");
        bloodList.add("A+");
        bloodList.add("B-");
        bloodList.add("B+");
        bloodList.add("AB-");
        bloodList.add("AB+");


        ArrayList<Integer> bloodNumbers = new ArrayList<>();
        bloodNumbers.add(0);
        bloodNumbers.add(0);
        bloodNumbers.add(0);
        bloodNumbers.add(0);
        bloodNumbers.add(0);
        bloodNumbers.add(0);
        bloodNumbers.add(0);
        bloodNumbers.add(0);


        barChart.getData().removeAll(Collections.singleton(barChart.getData().setAll()));
        pieChart.setVisible(false);
        barChart.setVisible(true);

        XYChart.Series series = new XYChart.Series();
        series.setName("Blood type");

        ObservableList<XYChart.Data> barChartData;
        ArrayList<XYChart.Data> data = new ArrayList<>();

        String SQL;
        DBConnector.connectDB();

        SQL = "select id.blood_type, count(*)\n" +
                "from identity id, patient p\n" +
                "where id.identity_number = p.identity_number\n"+
                "group by id.blood_type;";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            bloodNumbers.set(bloodList.indexOf(rs.getString(1)), Integer.parseInt(rs.getString(2)));
        }

        for (int i = 0; i < bloodList.size(); i++)
            data.add(new XYChart.Data(bloodList.get(i), bloodNumbers.get(i)));

        rs.close();
        stmt.close();

        DBConnector.getCon().close();

        barChartData = FXCollections.observableArrayList(data);
        series.getData().addAll(barChartData);

        barChart.getData().addAll(series);
    }

    private void getCity() throws SQLException, ClassNotFoundException, ParseException {
        ArrayList<String> cityList = new ArrayList<>();
        cityList.add("Salfit");
        cityList.add("Nablus");
        cityList.add("Ramallah");
        cityList.add("Jenin");
        cityList.add("Tulkarm");
        cityList.add("Jerusalem");
        cityList.add("Jericho");
        cityList.add("Qalqilya");
        cityList.add("Bethlehem");
        cityList.add("Hebron");

        ArrayList<Integer> cityNumbers = new ArrayList<>();
        cityNumbers.add(0);
        cityNumbers.add(0);
        cityNumbers.add(0);
        cityNumbers.add(0);
        cityNumbers.add(0);
        cityNumbers.add(0);
        cityNumbers.add(0);
        cityNumbers.add(0);
        cityNumbers.add(0);
        cityNumbers.add(0);


        barChart.getData().removeAll(Collections.singleton(barChart.getData().setAll()));
        pieChart.setVisible(false);
        barChart.setVisible(true);

        XYChart.Series series = new XYChart.Series();
        series.setName("Cities");

        ObservableList<XYChart.Data> barChartData;
        ArrayList<XYChart.Data> data = new ArrayList<>();

        String SQL;
        DBConnector.connectDB();

        SQL = "select id.living_address, count(*)\n" +
                "from identity id, patient p\n" +
                "where id.identity_number = p.identity_number\n"+
                "group by id.living_address;";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            cityNumbers.set(cityList.indexOf(rs.getString(1)), Integer.parseInt(rs.getString(2)));
        }

        for (int i = 0; i < cityList.size(); i++)
            data.add(new XYChart.Data(cityList.get(i), cityNumbers.get(i)));

        rs.close();
        stmt.close();

        DBConnector.getCon().close();

        barChartData = FXCollections.observableArrayList(data);
        series.getData().addAll(barChartData);

        barChart.getData().addAll(series);
    }
}
