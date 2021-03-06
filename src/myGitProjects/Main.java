package myGitProjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner initialQuestions = new Scanner(System.in);
        File accountFile = null;
        System.out.println("Welcome to Tee Bank!");

        String answer;

        do {
            System.out.print("Have you already got an account? (y/n): ");
            answer = initialQuestions.nextLine();
        } while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));


            if (answer.equalsIgnoreCase("n")) {

                Account.checkIfAccountExist(accountFile = Account.setYourNewAccount());
            }

            else if (answer.equalsIgnoreCase("y"))
                accountFile = Account.checkIfAccountExist(Account.loginProcedure());

            else System.out.println("Invalid character!");

            boolean shouldContinue = true;
            while (shouldContinue) {
                Account.displayDashboard(accountFile);
                //byte userChoice = 0;

                //try {
                    byte userChoice = initialQuestions.nextByte();
                //}
                //catch (InputMismatchException e) {
                  //  System.out.println("Wrong format! Try once again: ");
                //}
                    switch (userChoice) {
                        case 1 -> Account.deposit(accountFile);
                        case 2 -> Account.withdrawal(accountFile);
                        case 3 -> Account.collectHistoryFromFile(accountFile);
                        case 4 -> {
                            Account.changePassword(accountFile);
                            shouldContinue = false;
                        }

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
                        case 6 -> currencyAccountsOperation(accountFile);

                        case 7 -> {
                            System.out.println("Have a nice day, wish to see you soon!");
                            System.out.println("Logging out...");
                            shouldContinue = false;
                        }
                    }

            }

        }

    private static void currencyAccountsOperation(File accountFile) throws IOException {
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
                        case 1 -> Account.deposit(currencyFile);
                        case 2 -> Account.withdrawal(currencyFile);
                        case 3 -> Account.collectHistoryFromFile(currencyFile);
                        case 4 -> Account.buyCurrency(currencyFile, accountFile);
                        case 5 -> Account.sellCurrency(currencyFile, accountFile);
                    }
                } while (choice !=6);
            }
        }
    }
}

