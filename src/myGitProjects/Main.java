package myGitProjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner initialQuestions = new Scanner(System.in);
        System.out.println("Welcome to Tee Bank!");
        String answer, fName, lName, log, pass;

        do {
            System.out.print("Have you already got an account? (y/n): ");
            answer = initialQuestions.nextLine();
        } while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));

            if (answer.equalsIgnoreCase("n")) {
                System.out.println("If you want to join us, enter some details about you and your future account, " +
                        "please: ");
                System.out.print("What is your name?: ");
                fName = initialQuestions.nextLine();
                System.out.print("What is your last name?: ");
                lName = initialQuestions.nextLine();
                System.out.print("Choose your login: ");
                log = initialQuestions.nextLine();
                System.out.print("Choose your password: ");
                pass = initialQuestions.nextLine();

                account newAccount = new account(fName, lName,
                        log, pass, 100);


                System.out.println("Hello " + newAccount.firstName + " " + newAccount.lastName + ", " +
                        "you have established " + "your login to '" + newAccount.login +
                        "' and your password to '" + newAccount.password + "'.");
                System.out.println("And you have got a present from us - " + newAccount.balance +
                        " PLN for the start!");
                File newAccountFile = new File("accountOf" + fName + ".txt");
                PrintWriter safeToFile = new PrintWriter("accountOf" + fName + ".txt");
                safeToFile.println(newAccount.firstName);
                safeToFile.println(newAccount.lastName);
                safeToFile.println(newAccount.login);
                safeToFile.println(newAccount.password);
                safeToFile.println(newAccount.balance);
                safeToFile.close();

            } else if (answer.equalsIgnoreCase("y")) {
                System.out.print("Login: ");

                System.out.print("Password: ");

            } else System.out.println("Invalid character!");


    }
}
