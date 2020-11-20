package myGitProjects;

import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner initialQuestions = new Scanner(System.in);
        Account existingAccount = null;
        /*File accountFile = null;
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost/test";
        String USER = "root";
        String PASSWORD = "Tomeczek1"; */

        System.out.println("Welcome to Tee Bank!");
        //DBConnection myConnection = new DBConnection(JDBC_DRIVER, DB_URL, USER, PASSWORD);
        String answer;

        do {
            System.out.print("Have you already got an account? (y/n): ");
            answer = initialQuestions.nextLine();
        } while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));


            if (answer.equalsIgnoreCase("n")) {
                Account.setYourNewAccount();
                //Account.checkIfAccountExist(accountFile = Account.setYourNewAccount());
            }

            else if (answer.equalsIgnoreCase("y")) {
                existingAccount = Account.loginProcedure();
                if (existingAccount == null) {
                    System.out.println("Seems that you are first time here!");
                    Account.setYourNewAccount();
                }
            }

            else System.out.println("Invalid character!");

            boolean shouldContinue = true;
            while (shouldContinue) {
                Account.displayDashboard(existingAccount);

                    byte userChoice = initialQuestions.nextByte();

                    switch (userChoice) {
                        case 1 -> Account.deposit(existingAccount);
                        case 2 -> Account.withdrawal(existingAccount);
                        case 3 -> DBConnection.collectOperationsHistory(existingAccount.getLogin(), existingAccount.getCurrency());
                        case 4 -> {
                            System.out.println("Change password");
                            shouldContinue = false;
                        }
/*
                        case 5 -> {
                            System.out.println("You can create an account in USD (1), EUR (2) or GPB (3)");
                            System.out.print("What is your choice?: ");
                            Scanner currencyChoice = new Scanner(System.in);
                            byte choice = currencyChoice.nextByte();

                            String currency;
                            if (choice == 1) Account.createCurrencyAccount(currency = "USD", accountFile);
                            else if (choice == 2) Account.createCurrencyAccount(currency = "EUR", accountFile);
                            else if (choice == 3) Account.createCurrencyAccount(currency = "GBP", accountFile);
                        }
                        case 6 -> currencyAccountsOperation(existingAccount);
*/
                        case 7 -> {
                            System.out.println("Have a nice day, wish to see you soon!");
                            System.out.println("Logging out...");
                            shouldContinue = false;
                        }
                    }

            }

        }
/*
    private static void currencyAccountsOperation(Account account) throws IOException {
        String[] currency = {"USD", "EUR", "GBP"};
        for (int i=1; i<=3; i++) {
            File checkCurrencyFile = new File(currency[i-1] +"accountOf_" +
                    Account.collectDataFromFile(accountFile).getLogin().substring
                            (Account.collectDataFromFile(accountFile).getLogin().length() - 3,
                                    Account.collectDataFromFile(accountFile).getLogin().length())
                    + Account.collectDataFromFile(accountFile).getPassword().substring
                    (Account.collectDataFromFile(accountFile).getPassword().length() - 3,
                            Account.collectDataFromFile(accountFile).getPassword().length()) + ".txt");
            if (checkCurrencyFile.isFile()) {
                System.out.println(currency[i-1] + " account (choose " + i +")");
            }
        }
        Scanner yourChoice = new Scanner (System.in);
        byte choice = yourChoice.nextByte();
        for (int i = 1; i <=3; i++) {
            File currencyFile = new File(currency[i-1] +"accountOf_" +
                    Account.collectDataFromFile(accountFile).getLogin().substring
                            (Account.collectDataFromFile(accountFile).getLogin().length() - 3,
                                    Account.collectDataFromFile(accountFile).getLogin().length())
                    + Account.collectDataFromFile(accountFile).getPassword().substring
                    (Account.collectDataFromFile(accountFile).getPassword().length() - 3,
                            Account.collectDataFromFile(accountFile).getPassword().length()) + ".txt");
            if (choice == i) {

                System.out.println("You have " + Account.collectDataFromFile(currencyFile).getBalance() +
                        " on your " + currency[i-1] + " account. Choose your action:");
                do {
                    System.out.println("1. Deposit");
                    System.out.println("2. Withdrawal");
                    System.out.println("3. Show account history");
                    System.out.println("4. Transfer from PLN account");
                    System.out.println("5. Transfer to PLN account");
                    System.out.println("6. Come back to the main menu");
                    do {
                        choice = yourChoice.nextByte();
                        if (choice < 1 || choice > 6) System.out.println("Choose from 1 to 6");
                    } while (choice < 1 || choice > 6);
                    switch (choice) {
                        case 1 -> Account.deposit(existingAccount);
                        case 2 -> Account.withdrawal(currencyFile);
                        case 3 -> Account.collectHistoryFromFile(currencyFile);
                        case 4 -> Account.buyCurrency(currencyFile, accountFile);
                        case 5 -> Account.sellCurrency(currencyFile, accountFile);
                    }
                } while (choice !=6);
            }
        }
    } */
}

