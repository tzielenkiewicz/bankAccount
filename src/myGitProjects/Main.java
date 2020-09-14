package myGitProjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner initialQuestions = new Scanner(System.in);
        System.out.println("Welcome to Tee Bank!");

        String answer;
        Scanner checkLoginPassword = new Scanner(System.in);
        File checkFile = null;

        do {
            System.out.print("Have you already got an account? (y/n): ");
            answer = initialQuestions.nextLine();
        } while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));


            if (answer.equalsIgnoreCase("n")) Account.setYourNewAccount();
            else if (answer.equalsIgnoreCase("y")) {
                System.out.print("Login: ");
                String login = checkLoginPassword.nextLine();

                System.out.print("Password: ");
                String password = checkLoginPassword.nextLine();

                checkFile = new File(("accountOf_" + login.substring(login.length() - 3, login.length())
                        + password.substring(password.length() - 3, password.length()) + ".txt"));
                Account.checkIfAccountExist(checkFile);
            }

            else System.out.println("Invalid character!");

        Account.displayDashboard(checkFile);
    }

}

