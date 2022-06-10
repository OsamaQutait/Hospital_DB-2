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
import javafx.scene.layout.HBox;

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

public class PaymentReportsController implements Initializable {
    @FXML
    private Button lifeStatus;

    @FXML
    private Button profits;
    @FXML
    private JFXDatePicker tDate;

    @FXML
    private JFXDatePicker sDate;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private PieChart pieChart;
    @FXML
    private HBox dBox;

    @FXML
    private Label fLabel;

    @FXML
    private Label tLabel;

    @FXML
    private Button go;

    String fromDate;
    String toDate;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*manualSearch.setOnAction((ActionEvent e) -> {
            manualSearch.setSelected(true);
            tableSearch.setSelected(false);
        });*/
        dBox.setVisible(false);
        fLabel.setVisible(false);
        tLabel.setVisible(false);
        tDate.setVisible(false);
        sDate.setVisible(false);

        lifeStatus.setStyle("-fx-background-color: #3b5998;" +
                "-fx-text-fill: #dfe3ee;");
        profits.setStyle("-fx-background-color: #3b5998;" +
                "-fx-text-fill: #dfe3ee;");

        pieChart.setVisible(false);
        barChart.setVisible(false);

        lifeStatus.setOnAction((ActionEvent e) -> {
            dBox.setVisible(false);
            fLabel.setVisible(false);
            tLabel.setVisible(false);
            tDate.setVisible(false);
            sDate.setVisible(false);
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
            dBox.setVisible(true);
            fLabel.setVisible(true);
            tLabel.setVisible(true);
            tDate.setVisible(true);
            sDate.setVisible(true);
            lifeStatus.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            profits.setStyle("-fx-background-color: #dfe3ee;" +
                    "-fx-text-fill: #3b5998;");
            /*try {
                getProfits();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }*/
        });

        go.setOnAction((ActionEvent e) -> {
            dBox.setVisible(true);
            fLabel.setVisible(true);
            tLabel.setVisible(true);
            tDate.setVisible(true);
            sDate.setVisible(true);
            lifeStatus.setStyle("-fx-background-color: #3b5998;" +
                    "-fx-text-fill: #dfe3ee;");
            profits.setStyle("-fx-background-color: #dfe3ee;" +
                    "-fx-text-fill: #3b5998;");
            try {
                Date d1 = sDate.getValue() == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(sDate.getValue()));
                Date d2 = tDate.getValue() == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(tDate.getValue()));
                fromDate = d1 == null ? null : ("'" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(d1) + "'");
                toDate = d2 == null ? null : ("'" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(d2) + "'");
                getProfits();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        });
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

    private void getProfits() throws SQLException, ClassNotFoundException, ParseException {
        ArrayList<String> monthsList = new ArrayList<>();
        monthsList.add("");
        monthsList.add("Jan.");
        monthsList.add("Feb.");
        monthsList.add("Mar.");
        monthsList.add("Apr.");
        monthsList.add("May");
        monthsList.add("June");
        monthsList.add("Jul.");
        monthsList.add("Aug.");
        monthsList.add("Sept.");
        monthsList.add("Oct.");
        monthsList.add("Nov.");
        monthsList.add("Dec.");


        ArrayList<Integer> profitPerMonth;


        ArrayList<Integer> years = new ArrayList<>();
        ArrayList<ArrayList<Float>> profits = new ArrayList<>();


        barChart.getData().removeAll(Collections.singleton(barChart.getData().setAll()));
        pieChart.setVisible(false);
        barChart.setVisible(true);

        String SQL;
        DBConnector.connectDB();

        SQL = "select year(p.stay_leave_date_time), month(p.stay_leave_date_time), sum(pay.total_bill)\n" +
                "from identity id, patient p, payment pay\n" +
                "where id.identity_number = p.identity_number and id.identity_number = pay.identity_number and p.stay_leave_date_time is not null" +
                (fromDate == null ? "\n" : ("\n" + "and p.stay_leave_date_time >= " + fromDate) + "\n") +
        (toDate == null ? "\n" : ("\n" + "and p.stay_leave_date_time <= " + toDate) + "\n")
                + "group by year(p.stay_leave_date_time), month(p.stay_leave_date_time)\n" +
        "order by year(p.stay_leave_date_time), month(p.stay_leave_date_time);";
        Statement stmt = DBConnector.getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        int prevYear = 0;
        ArrayList<Float> currProfits = new ArrayList<>();
        currProfits.add(0f);
        currProfits.add(0f);
        currProfits.add(0f);
        currProfits.add(0f);
        currProfits.add(0f);
        currProfits.add(0f);
        currProfits.add(0f);
        currProfits.add(0f);
        currProfits.add(0f);
        currProfits.add(0f);
        currProfits.add(0f);
        currProfits.add(0f);
        currProfits.add(0f);
        int i = 0;
        while (rs.next()) {
            int currYear = Integer.parseInt(rs.getString(1));
            int currMonth = Integer.parseInt(rs.getString(2));
            float currProfit = Float.parseFloat(rs.getString(3));
            if (i == 0){
                prevYear = currYear;
                i++;
            }
            if (prevYear != currYear){
                profits.add(currProfits);
                years.add(prevYear);

                currProfits = new ArrayList<>();
                currProfits.add(0f);
                currProfits.add(0f);
                currProfits.add(0f);
                currProfits.add(0f);
                currProfits.add(0f);
                currProfits.add(0f);
                currProfits.add(0f);
                currProfits.add(0f);
                currProfits.add(0f);
                currProfits.add(0f);
                currProfits.add(0f);
                currProfits.add(0f);
                currProfits.add(0f);
            }
            currProfits.set(currMonth, currProfit);
            prevYear = currYear;
        }
        profits.add(currProfits);
        years.add(prevYear);
        ArrayList<XYChart.Series> listOfSeries = new ArrayList<>();
        for (int j = 0; j < years.size(); j++){
            XYChart.Series series = new XYChart.Series();
            series.setName(String.valueOf(years.get(j)));

            ObservableList<XYChart.Data> barChartData;
            ArrayList<XYChart.Data> data = new ArrayList<>();

            profitPerMonth = new ArrayList<>();
            profitPerMonth.add(0);
            profitPerMonth.add(0);
            profitPerMonth.add(0);
            profitPerMonth.add(0);
            profitPerMonth.add(0);
            profitPerMonth.add(0);
            profitPerMonth.add(0);
            profitPerMonth.add(0);
            profitPerMonth.add(0);
            profitPerMonth.add(0);
            profitPerMonth.add(0);
            profitPerMonth.add(0);
            profitPerMonth.add(0);
            for (int a = 0; a < monthsList.size(); a++) {
                data.add(new XYChart.Data(monthsList.get(a), profitPerMonth.get(a)));
                data.add(new XYChart.Data(
                        monthsList.get(monthsList.indexOf((monthsList.get(a)))), (profits.get(j)).get(a)
                        )
                );
            }
            barChartData = FXCollections.observableArrayList(data);
            series.getData().addAll(barChartData);
            listOfSeries.add(series);
        }



        rs.close();
        stmt.close();

        DBConnector.getCon().close();

        for (XYChart.Series listOfSery : listOfSeries) {
            barChart.getData().add(listOfSery);
        }
    }
}
