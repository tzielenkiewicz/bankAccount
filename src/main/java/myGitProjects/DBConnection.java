package myGitProjects;

import java.sql.*;
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

    public static Account collectAccountData(String login, String password) {
        Connection conn = DBConnection.connectionProcedure();

        Statement stmt = null;
        Account existingAccount = null;
        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT customers.name, customers.lastName, accounts.currentBalance FROM customers, accounts WHERE customers.login = '"
                    + login + "' AND customers.ID = accounts.customerID;";
            ResultSet rs = stmt.executeQuery(sql);
            String name, surname;
            double balance;
            while (rs.next()) {
                name = rs.getString("name");
                System.out.println(name);
                surname = rs.getString("lastName");
                System.out.println(surname);
                balance = rs.getDouble("currentBalance");
                System.out.println(balance);
                existingAccount = new Account(name, surname, login, password, balance, "PLN");

            }

            rs.close();
            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return existingAccount;
    }

    public static void collectOperationsHistory(String login, String currency) {
        Connection conn = DBConnection.connectionProcedure();

        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT operations.date, operations.info, operations.balance FROM operations, customers, accounts WHERE customers.login = '"
                    + login
                    + "' AND customers.ID = accounts.customerID AND operations.accountID = accounts.ID;";
            ResultSet rs = stmt.executeQuery(sql);
            Date date;
            String info;
            double balance;
            while (rs.next()) {
                date = rs.getDate("date");
                System.out.print("Date: " + date + ". ");
                info = rs.getString("info");
                System.out.print(info + ". ");
                balance = rs.getDouble("balance");
                System.out.println("Balance: " + balance + currency + ".");
            }

            rs.close();
            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void saveNewPassword(String password, String login) {
        Connection conn = DBConnection.connectionProcedure();

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE customers SET password = '"
                    + password + "' WHERE login = '"
                    + login + "';");

            stmt.close();
        } catch (SQLException throwables) {
            System.out.println("------------------");
            System.out.println("Connection failed!");
            System.out.println("------------------");
        }
        System.out.println("You have to logout now...");
    }
}
