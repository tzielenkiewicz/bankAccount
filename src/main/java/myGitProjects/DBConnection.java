package myGitProjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private String JDBC_DRIVER;
    private String DB_URL;

    private String USER;
    private String PASSWORD;

    public DBConnection (String JDBC_DRIVER, String DB_URL, String USER, String PASSWORD){
        this.JDBC_DRIVER = JDBC_DRIVER;
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
    }

    public String getJDBC_DRIVER() {
        return JDBC_DRIVER;
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public String getUSER() {
        return USER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public static Connection connectionProcedure (DBConnection myConnection) {
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/bank";
        String USER = "root";
        String PASSWORD = "Tomeczek1";
        Connection conn = null;
        try {
            System.out.println("Connecting to DB...");
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Nie udało się nawiązać połączenia");
        }
        return conn;
    }

    public static void safeNewCustomer(String name, String lastName, String login, String password, DBConnection myConnection) {
        try {
            String sql = String.format("INSERT INTO customers VALUES(null, '%s', '%s', '%s', '%s')",
                    name, lastName, login, password);
            System.out.println(sql);
            Statement stmt = connectionProcedure(myConnection).createStatement();

            stmt.executeUpdate(sql);
            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
