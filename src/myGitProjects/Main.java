package myGitProjects;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
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

            } else if (answer.equalsIgnoreCase("y")) {
                System.out.print("Login: ");

                System.out.print("Password: ");

            } else System.out.println("Invalid character!");


    }
}
