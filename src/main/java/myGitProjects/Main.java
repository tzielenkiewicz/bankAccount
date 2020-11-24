package myGitProjects;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner initialQuestions = new Scanner(System.in);
        Account existingAccount = null;

        System.out.println("Welcome to Tee Bank!");
        String answer;

        do {
            System.out.print("Have you already got an account? (y/n): ");
            answer = initialQuestions.nextLine();
        } while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));


            if (answer.equalsIgnoreCase("n")) {
                Account.setYourNewAccount();
                }

            else if (answer.equalsIgnoreCase("y")) {
                for (int i = 0; i < 3; i++) {
                    existingAccount = Account.loginProcedure();
                    if (existingAccount == null) System.out.println
                            ("Login or password is is incorrect! Try once again");
                    else break;
                }

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
                        case 4 -> {Account.changePassword(existingAccount);
                            shouldContinue = false;
                        }

                        case 5 -> {
                            System.out.println("You can create an account in USD (1), EUR (2) or GPB (3)");
                            System.out.print("What is your choice?: ");
                            Scanner currencyChoice = new Scanner(System.in);
                            byte choice = currencyChoice.nextByte();

                            String currency;
                            if (choice == 1) Account.createCurrencyAccount(currency = "USD", existingAccount);
                            else if (choice == 2) Account.createCurrencyAccount(currency = "EUR", existingAccount);
                            else if (choice == 3) Account.createCurrencyAccount(currency = "GBP", existingAccount);
                        }
                        case 6 -> currencyAccountsOperation(existingAccount);
                        //Wyskakuje błąd!!!

                        case 7 -> {
                            System.out.println("Have a nice day, wish to see you soon!");
                            System.out.println("Logging out...");
                            shouldContinue = false;
                        }
                    }

            }

        }

    private static void currencyAccountsOperation(Account account) {
        String[] currency = {"USD", "EUR", "GBP"};
        Scanner yourChoice = new Scanner (System.in);

        for (int i=1; i<=3; i++) {
            if (DBConnection.checkCurrencyAccount(account.getLogin(), currency[i - 1])) {
                System.out.println(currency[i - 1] + " account (choose " + i + ")");
            }
        }

        byte choice = yourChoice.nextByte();
        Account currencyAccount = null;

        for (int i=1; i<=3; i++) {
            if (choice == i) {
                currencyAccount = DBConnection.collectAccountData(account.getLogin(), account.getPassword(), currency[i - 1]);

                System.out.println("You have " + currencyAccount.getBalance() + currency[i - 1] +
                        " on your " + currency[i - 1] + " account. Choose your action:");
            }
        }
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
                case 1 -> Account.deposit(currencyAccount);
                case 2 -> Account.withdrawal(currencyAccount);
                case 3 -> DBConnection.collectOperationsHistory(currencyAccount.getLogin(), currencyAccount.getCurrency());
                case 4 -> System.out.println("Account.buyCurrency(currencyFile, accountFile)");
                case 5 -> System.out.println("Account.sellCurrency(currencyFile, accountFile)");
            }
        } while (choice != 6);
    }
}


