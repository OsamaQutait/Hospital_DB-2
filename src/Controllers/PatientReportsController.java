package Controllers;

import DatabaseConnector.DBConnector;
import Hospital.Identity;
import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;

public class PatientReportsController implements Initializable {
    @FXML
    private Button city;

    @FXML
    private Button bloodType;

    @FXML
    private Button lifeStat;

    @FXML
    private Button visitReason;

    @FXML
    private Button gender;

    @FXML
    private Button insurance;

    @FXML
    private BarChart<String, String> barChart;

    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*manualSearch.setOnAction((ActionEvent e) -> {
            manualSearch.setSelected(true);
            tableSearch.setSelected(false);
        });*/
        gender.setStyle("-fx-background-color: #3b5998;" +
                "-fx-text-fill: #dfe3ee;");
        city.setStyle("-fx-background-color: #3b5998;" +
                "-fx-text-fill: #dfe3ee;");
        bloodType.setStyle("-fx-background-color: #3b5998;" +
                "-fx-text-fill: #dfe3ee;");
        lifeStat.setStyle("-fx-background-color: #3b5998;" +
                "-fx-text-fill: #dfe3ee;");
        visitReason.setStyle("-fx-background-color: #3b5998;" +
                "-fx-text-fill: #dfe3ee;");
        insurance.setStyle("-fx-background-color: #3b5998;" +
                "-fx-text-fill: #dfe3ee;");


        pieChart.setVisible(false);
        barChart.setVisible(false);

        gender.setOnAction((ActionEvent e) -> {
            gender.setStyle("-fx-background-color: #dfe3ee;" +
                    "-fx-text-fill: #3b5998;");
            city.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            bloodType.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            lifeStat.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            visitReason.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            insurance.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");

            try {
                getGender();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });

        bloodType.setOnAction((ActionEvent e) -> {
            gender.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            city.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            bloodType.setStyle("-fx-background-color: #dfe3ee;" +
                    "-fx-text-fill: #3b5998;");
            lifeStat.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            visitReason.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            insurance.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
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

        city.setOnAction((ActionEvent e) -> {
            gender.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            city.setStyle("-fx-background-color: #dfe3ee;" +
                    "-fx-text-fill: #3b5998;");
            bloodType.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            lifeStat.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            visitReason.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            insurance.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            try {
                getCity();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });

        lifeStat.setOnAction((ActionEvent e) -> {
            gender.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            city.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            bloodType.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            lifeStat.setStyle("-fx-background-color: #dfe3ee;" +
                    "-fx-text-fill: #3b5998;");
            visitReason.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            insurance.setStyle("-fx-background-color: #3b5998;" +
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
        visitReason.setOnAction((ActionEvent e) -> {
            gender.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            city.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            bloodType.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            lifeStat.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            visitReason.setStyle("-fx-background-color: #dfe3ee;" +
                    "-fx-text-fill: #3b5998;");
            insurance.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            try {
                getVisitReason();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });
        insurance.setOnAction((ActionEvent e) -> {
            gender.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            city.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            bloodType.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            lifeStat.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            insurance.setStyle("-fx-background-color: #dfe3ee;" +
                    "-fx-text-fill: #3b5998;");
            visitReason.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            try {
                getInsurance();
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

        SQL = "select p.emergency_status, count(*)\n" +
                "from identity id, patient p\n" +
                "where id.identity_number = p.identity_number\n"+
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
                                "No. of ", data1.getName(), ": ", data1.pieValueProperty()
                        )
                ));

        pieChart.getData().addAll(pieChartData);
    }

    private void getVisitReason() throws SQLException, ClassNotFoundException, ParseException {
        pieChart.getData().removeAll(Collections.singleton(pieChart.getData().setAll()));
        pieChart.setVisible(true);
        barChart.setVisible(false);
        ObservableList<PieChart.Data> pieChartData;
        ArrayList<PieChart.Data> data = new ArrayList<>();

        boolean sFlag = false;
        boolean tFlag = false;
        boolean stFlag = false;

        String SQL;
        DBConnector.connectDB();

        SQL = "select p.visit_reason, count(*)\n" +
                "from identity id, patient p\n" +
                "where id.identity_number = p.identity_number and p.visit_reason is not null\n"+
                "group by p.visit_reason;";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            data.add(new PieChart.Data(rs.getString(1), Integer.parseInt(rs.getString(2))));
            if (rs.getString(1).equals("For test")){
                tFlag = true;
            }
            if (rs.getString(1).equals("For surgery")){
                sFlag = true;
            }
            if (rs.getString(1).equals("For surgery and test")){
                stFlag = true;
            }
        }
        if (!tFlag){
            data.add(new PieChart.Data("For test", 0));
        }
        if (!sFlag){
            data.add(new PieChart.Data("For surgery", 0));
        }
        if (!stFlag){
            data.add(new PieChart.Data("For surgery and test", 0));
        }

        rs.close();
        stmt.close();

        DBConnector.getCon().close();

        pieChartData = FXCollections.observableArrayList(data);
        pieChartData.forEach(data1 ->
                data1.nameProperty().bind(
                        Bindings.concat(
                                data1.getName(), ": ", data1.pieValueProperty()
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

    private void getInsurance() throws SQLException, ClassNotFoundException, ParseException {
        pieChart.getData().removeAll(Collections.singleton(pieChart.getData().setAll()));
        pieChart.setVisible(true);
        barChart.setVisible(false);
        ObservableList<PieChart.Data> pieChartData;
        ArrayList<PieChart.Data> data = new ArrayList<>();

        boolean sFlag = false;
        boolean tFlag = false;
        boolean stFlag = false;

        Date d1 = new Date();
        String dateWithTime = ("'" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(d1) + "'");
        String dateWithOutTime = ("'" + new SimpleDateFormat("yyyy-MM-dd").format(d1) + "'");


        String SQL;
        DBConnector.connectDB();

        SQL = "select count(*)\n" +
                "from insurance insu, patient p\n" +
                "where p.identity_number = insu.identity_number and (p.stay_leave_date_time is null or p.stay_leave_date_time >= " + dateWithTime + ") and insu.expire_date < " + dateWithOutTime + ";\n";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            data.add(new PieChart.Data("No. of Expired insurances", Integer.parseInt(rs.getString(1))));
        }

        SQL = "select count(*)\n" +
                "from insurance insu, patient p\n" +
                "where p.identity_number = insu.identity_number and (p.stay_leave_date_time is null or p.stay_leave_date_time >= " + dateWithTime + ") and insu.expire_date = " + dateWithOutTime + ";\n";
        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            data.add(new PieChart.Data("No. of Insurances expire today", Integer.parseInt(rs.getString(1))));
        }

        SQL = "select count(*)\n" +
                "from insurance insu, patient p\n" +
                "where p.identity_number = insu.identity_number and (p.stay_leave_date_time is null or p.stay_leave_date_time >= " + dateWithTime + ") and insu.expire_date > " + dateWithOutTime + ";\n";
        stmt = DBConnector.getCon().createStatement();
        rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            data.add(new PieChart.Data("No. of Unexpired insurances", Integer.parseInt(rs.getString(1))));
        }

        rs.close();
        stmt.close();

        DBConnector.getCon().close();

        pieChartData = FXCollections.observableArrayList(data);
        pieChartData.forEach(data1 ->
                data1.nameProperty().bind(
                        Bindings.concat(
                                data1.getName(), ": ", data1.pieValueProperty()
                        )
                ));

        pieChart.getData().addAll(pieChartData);
    }
}
