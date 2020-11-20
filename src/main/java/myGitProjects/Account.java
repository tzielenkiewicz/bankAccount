package myGitProjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Account {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private double balance;
    private String currency;


    public Account(String firstName, String lastName, String login, String password, double balance, String currency) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.balance = balance;
        this.currency = currency;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public String getCurrency() { return currency; }

    public static Account loginProcedure() {

        Scanner checkLoginPassword = new Scanner(System.in);

        System.out.print("Login: ");
        String login = checkLoginPassword.nextLine();
        System.out.print("Password: ");
        String password = checkLoginPassword.nextLine();


        return DBConnection.collectAccountData(login, password);
    }
    public static void setYourNewAccount() {
        Scanner initialQuestions = new Scanner(System.in);

        System.out.println("If you want to join us, enter some details about you and your future account, " +
                "please: ");
        System.out.print("What is your name?: ");
        String firstName = initialQuestions.nextLine();
        System.out.print("What is your last name?: ");
        String lastName = initialQuestions.nextLine();

        String login = firstName.toLowerCase() + lastName.substring(0, 4).toLowerCase() +
                lastName.substring(lastName.length()-3, lastName.length());
        System.out.println("Remember your login, please: " + login);
        System.out.print("Choose your password: ");
        String password = initialQuestions.nextLine();

        Account newAccount = new Account(firstName, lastName,
                login, password, 100, "PLN");

        String actionInfo = "Gift for the beginning";


        try {

            Connection conn = DBConnection.connectionProcedure();
            Statement stmt = conn.createStatement();
            DBConnection.createNewCustomer(newAccount, stmt);
            DBConnection.createNewAccount(newAccount, stmt);
            DBConnection.saveOperationsHistory(newAccount, stmt, actionInfo);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("--------------------------");
            System.out.println("Statement creation failed!");
            System.out.println("--------------------------");
        }

        System.out.println("Hello " + newAccount.getFirstName() + " " + newAccount.getLastName() + ", " +
                "your login is '" + newAccount.getLogin() +
                "' and your password '" + newAccount.getPassword() + "'.");
        System.out.println("And you have got a present from us - " + newAccount.getBalance() +
                newAccount.getCurrency() + "for the start!");
        System.out.println("Now start the app once again and input your new login and password, please...");

        System.exit(0);
    }


    public static Account collectDataFromFile(File file) throws FileNotFoundException {
        String [] accountDataSet = new String[6];
        Scanner readFile = new Scanner(file);
        for (int i = 0; i < 6; i++) {
            accountDataSet[i] = readFile.nextLine();
        }

        return new Account(accountDataSet[0], accountDataSet[1],
                accountDataSet[2], accountDataSet[3], Double.parseDouble(accountDataSet[4]),
        accountDataSet[5]);
    }

    public static void displayDashboard(Account existingAccount) {
        System.out.println();
        System.out.println("---------------------------------------------------------------");
        System.out.println("Name: " + existingAccount.getFirstName() + " " +
                existingAccount.getLastName());
        System.out.println("Balance: " + existingAccount.getBalance() + existingAccount.getCurrency());
        System.out.println();

        System.out.println("Choose your action: ");
        System.out.println();
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        System.out.println("3. Show account history");
        System.out.println("4. Change your password");
        System.out.println("5. Create an account in another currency");
        System.out.println("6. Other accounts");
        System.out.println("7. Logout");
    }

    public static void deposit(Account userAccount) throws IOException {

        Scanner question = new Scanner (System.in);
        System.out.print("How much would you like to deposit?: ");
        double deposit = question.nextDouble();
        userAccount.balance += deposit;
        String depositInformation = "You have deposited " + deposit + " " + userAccount.getCurrency() +
                ", your new balance is: "
                + userAccount.getBalance() + " " + userAccount.getCurrency();
        System.out.println(depositInformation);


        try {
            Connection conn = DBConnection.connectionProcedure();
            Statement stmt = conn.createStatement();
            DBConnection.saveOperationsHistory(userAccount, stmt, depositInformation);
            DBConnection.saveAccountBalance(userAccount, stmt);
            stmt.close();
        } catch (SQLException throwables) {
            System.out.println("--------------------------");
            System.out.println("Statement creation failed!");
            System.out.println("--------------------------");
        }

    }

    public static void withdrawal(Account userAccount) throws IOException {

        Scanner question = new Scanner (System.in);
        double withdrawal;
        do {
            System.out.print("How much would you like to withdraw?: ");
            withdrawal = question.nextDouble();
            if (withdrawal > userAccount.balance) System.out.println("Not enough funds!");
        } while (withdrawal > userAccount.balance);

        userAccount.balance -= withdrawal;
        String withdrawalInformation = "You have withdrawn " + withdrawal + " "
                + userAccount.getCurrency() + ", your new balance is: "
                + userAccount.getBalance() + " " + userAccount.getCurrency();
        System.out.println(withdrawalInformation);

        try {
            Connection conn = DBConnection.connectionProcedure();
            Statement stmt = conn.createStatement();
            DBConnection.saveOperationsHistory(userAccount, stmt, withdrawalInformation);
            DBConnection.saveAccountBalance(userAccount, stmt);
            stmt.close();
        } catch (SQLException throwables) {
            System.out.println("--------------------------");
            System.out.println("Statement creation failed!");
            System.out.println("--------------------------");
        }
    }


    public static void changePassword(Account existingAccount) throws IOException {
        String newPassword1, newPassword2;
        Scanner changePassword = new Scanner(System.in);
        do {
            System.out.println("Input new password: ");
            newPassword1 = changePassword.nextLine();
            System.out.println("Confirm new password: ");
            newPassword2 = changePassword.nextLine();
            if (!newPassword1.equals(newPassword2)) System.out.println(
                    "Confirmation did not succeed! Try once again.");
        }
        while (!newPassword1.equals(newPassword2));

        DBConnection.saveNewPassword(newPassword1, existingAccount.getLogin());

    }

    public static void createCurrencyAccount(String currency, Account account) {


        Account currencyAccount = new Account (account.getFirstName(), account.getLastName(),
                account.getLogin(), account.getPassword(), 0, currency);
        Connection conn = DBConnection.connectionProcedure();
        try {
            Statement stmt = conn.createStatement();
            DBConnection.createNewAccount(currencyAccount, stmt);
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Congratulations, we have just created an account in " + currency + " for you.");
    }
/*
    public static void buyCurrency(File currencyFile, File accountFile) throws IOException {
        Account currencyAccount = collectDataFromFile(currencyFile);
        Account PLNAccount = collectDataFromFile(accountFile);
        System.out.println("Exchange rates today:");
        System.out.println("We sell USD: " + currencyRates()[0] + ", EUR: " + currencyRates()[2] + ", GBP: "
                + currencyRates()[4]);
        System.out.println("Wy buy USD: " + currencyRates()[1] + ", EUR: " + currencyRates()[3] + ", GBP: "
                + currencyRates()[5]);
        Scanner currencyOperation = new Scanner (System.in);
        System.out.println("How much " + currencyAccount.currency + " would you like to buy?");
        double deposit = currencyOperation.nextDouble();
        double PLNWithdrawal = switch (currencyAccount.getCurrency()) {
            case "USD" -> deposit * currencyRates()[0];
            case "EUR" -> deposit * currencyRates()[2];
            case "GBP" -> deposit * currencyRates()[4];
            default -> 0;
        };

        double roundedDeposit = Math.round(deposit*100);
        currencyAccount.balance += roundedDeposit / 100;
        String currencyInfo = "You have bought " + deposit + " " + currencyAccount.currency +
                ", your new balance is " + currencyAccount.balance + " " + currencyAccount.currency;
        saveToFile(currencyAccount);
        saveHistoryToFile(currencyInfo, currencyAccount.login, currencyAccount.password, currencyAccount.currency);

        double roundedPLNWithdrawal = Math.round(PLNWithdrawal*100);
        PLNAccount.balance -= roundedPLNWithdrawal / 100;
        String PLNInfo = "There has been " + PLNWithdrawal + " PLN withdrawn for " + currencyAccount.currency +
                " purchase, your new balance is " + PLNAccount.balance + " " + PLNAccount.currency;
        saveToFile(PLNAccount);
        saveHistoryToFile(PLNInfo, PLNAccount.login, PLNAccount.password, PLNAccount.currency);

        System.out.println(currencyInfo);
        System.out.println(PLNInfo);

    }

    public static void sellCurrency(File currencyFile, File accountFile) throws IOException {
        Account currencyAccount = collectDataFromFile(currencyFile);
        Account PLNAccount = collectDataFromFile(accountFile);
        System.out.println("Exchange rates today:");
        System.out.println("We sell USD: " + currencyRates()[0] + ", EUR: " + currencyRates()[2] + ", GBP: "
                + currencyRates()[4]);
        System.out.println("Wy buy USD: " + currencyRates()[1] + ", EUR: " + currencyRates()[3] + ", GBP: "
                + currencyRates()[5]);
        Scanner currencyOperation = new Scanner (System.in);
        System.out.println("How much " + currencyAccount.currency + " would you like to sell?");
        double withdrawal = currencyOperation.nextDouble();
        double PLNDeposit = switch (currencyAccount.getCurrency()) {
            case "USD" -> withdrawal * currencyRates()[1];
            case "EUR" -> withdrawal * currencyRates()[3];
            case "GBP" -> withdrawal * currencyRates()[5];
            default -> 0;
        };

        double roundedWithdrawal = Math.round(withdrawal*100);
        currencyAccount.balance -= roundedWithdrawal/100;
        String currencyInfo = "You have sold " + withdrawal + " " + currencyAccount.currency +
                ", your new balance is " + currencyAccount.balance + " " + currencyAccount.currency;
        saveToFile(currencyAccount);
        saveHistoryToFile(currencyInfo, currencyAccount.login, currencyAccount.password, currencyAccount.currency);

        double roundedPLNDeposit = Math.round(PLNDeposit*100);
        PLNAccount.balance += roundedPLNDeposit / 100;
        String PLNInfo = "There has been " + PLNDeposit + " PLN deposited from " + currencyAccount.currency +
                " sale, your new balance is " + PLNAccount.balance + " " + PLNAccount.currency;
        saveToFile(PLNAccount);
        saveHistoryToFile(PLNInfo, PLNAccount.login, PLNAccount.password, PLNAccount.currency);

        System.out.println(currencyInfo);
        System.out.println(PLNInfo);

    }

    private static double[] currencyRates() {
        Random generator = new Random();
        double diff = Math.round(generator.nextDouble()*20);

        double USDSellRate = 400 - diff;
        double USDBuyRate = 380 - diff;
        double EURSellRate = 440 - diff;
        double EURBuyRate = 420 - diff;
        double GBPSellRate = 480 - diff;
        double GBPBuyRate = 460 - diff;

        return new double[]{USDSellRate/100, USDBuyRate/100, EURSellRate/100, EURBuyRate/100, GBPSellRate/100, GBPBuyRate/100};
    }
*/
}




