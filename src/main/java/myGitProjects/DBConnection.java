package myGitProjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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

    public static Connection connectionProcedure () {
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/bank";
        String USER = "root";
        String PASSWORD = "Tomeczek1";
        Connection conn = null;
        try {
            System.out.println("Connecting to DB...");
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connection ready!");
            //Thread.sleep(500);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("------------------");
            System.out.println("Connection failed!");
            System.out.println("------------------");
        }
        return conn;
    }

    public static void createNewCustomer(Account newAccount, Statement stmt) {
        try {
            stmt.executeUpdate("INSERT INTO customers VALUES(null, '" + newAccount.getFirstName() + "', '"
                    + newAccount.getLastName() + "', '" + newAccount.getLogin() + "', '"
                    + newAccount.getPassword() + "');");
        } catch (SQLException throwables) {
            System.out.println("------------------");
            System.out.println("Connection failed!");
            System.out.println("------------------");
        }
    }

    public static void createNewAccount(Account newAccount, Statement stmt) {
        try {
            stmt.executeUpdate("INSERT INTO accounts VALUES(null, (SELECT ID FROM customers WHERE login = '"
                    + newAccount.getLogin() + "'), '" + newAccount.getCurrency() + "', '"
                    + newAccount.getBalance() + "');");
        } catch (SQLException throwables) {
            System.out.println("------------------");
            System.out.println("Connection failed!");
            System.out.println("------------------");
        }
    }

    public static void saveOperationsHistory(Account account, Statement stmt, String actionInfo) {
        LocalDate today = LocalDate.now();
        try {
            stmt.executeUpdate("INSERT INTO operations VALUES(null, (SELECT ID FROM accounts WHERE currency = '"
                    + account.getCurrency() +"' AND customerID = (SELECT ID FROM customers WHERE login = '"
                    + account.getLogin() + "')), '" + today + "', '" + actionInfo + "', "
                    + account.getBalance() + ");");
        } catch (SQLException throwables) {
            System.out.println("------------------");
            System.out.println("Connection failed!");
            System.out.println("------------------");
        }
    }

    public static void saveAccountBalance(Account account, Statement stmt) {
        try {
            stmt.executeUpdate("UPDATE accounts, customers SET currentBalance = "
                    + account.getBalance() + " WHERE customers.login = '"
                    + account.getLogin() + "' AND customers.ID = accounts.customerID;");
        } catch (SQLException throwables) {
            System.out.println("------------------");
            System.out.println("Connection failed!");
            System.out.println("------------------");
        }
    }
}
