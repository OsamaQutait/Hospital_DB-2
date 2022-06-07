package DatabaseConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class DBConnector {
    private static String dbURL;
    private static String dbUsername = "root";
    private static String dbPassword = "1234";
    private static String URL = "127.0.0.1";
    private static String port = "3306";
    private static String dbName = "hospital";
    private static Connection con;

    public static void connectDB() throws ClassNotFoundException, SQLException {
        dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
        Properties p = new Properties();
        p.setProperty("user", dbUsername);
        p.setProperty("password", dbPassword);
        p.setProperty("useSSL", "false");
        p.setProperty("autoReconnect", "true");
        Class.forName("com.mysql.jdbc.Driver");

        con = DriverManager.getConnection(dbURL, p);
    }

    public static void ExecuteStatement(String SQL) throws SQLException {
        try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(SQL);
            stmt.close();
        } catch (SQLException s) {
            s.printStackTrace();
            System.out.println("SQL statement is not executed!");
        }
    }

    public static Connection getCon() {
        return con;
    }
}
