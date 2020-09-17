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
                Account.setYourNewAccount();
                accountFile = loginProcedure();
            }

            else if (answer.equalsIgnoreCase("y")) {
                accountFile = loginProcedure();
                Account.checkIfAccountExist(accountFile);
            }

            else System.out.println("Invalid character!");

            boolean shouldContinue = true;
            while (shouldContinue) {
                Account.displayDashboard(accountFile);

                byte userChoice = initialQuestions.nextByte();
                switch (userChoice) {
                    case 1 -> Account.deposit(accountFile);
                    case 2 -> Account.withdrawal(accountFile);
                    case 3 -> Account.collectHistoryFromFile(accountFile);
                    case 4 -> System.out.println("Wybrano 4");
                    case 5 -> System.out.println("Wybrano 5");
                    case 6 -> System.out.println("Wybrano 6");
                    case 7 -> {
                        System.out.println("Have a nice day, wish to see you soon!");
                        System.out.println("Logging out...");
                        shouldContinue = false;
                    }
                }
            }
    }

    public static File loginProcedure() throws FileNotFoundException {
        Scanner checkLoginPassword = new Scanner(System.in);
        File checkFile = null;

        System.out.print("Login: ");
        String login = checkLoginPassword.nextLine();

        System.out.print("Password: ");
        String password = checkLoginPassword.nextLine();

        checkFile = new File(("accountOf_" + login.substring(login.length() - 3, login.length())
                + password.substring(password.length() - 3, password.length()) + ".txt"));
        return checkFile;
    }

}

