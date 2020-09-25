package myGitProjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

                byte userChoice = initialQuestions.nextByte();
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
                    case 6 -> System.out.println("Wybrano 6");
                    case 7 -> {
                        System.out.println("Have a nice day, wish to see you soon!");
                        System.out.println("Logging out...");
                        shouldContinue = false;
                    }
                }
            }
    }
}

