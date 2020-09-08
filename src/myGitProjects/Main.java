package myGitProjects;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner initialQuestions = new Scanner(System.in);
        System.out.println("Welcome to Tee Bank!");
        String answer, login, password;

        do {
        System.out.print("Have you already got an account? (y/n): ");
        answer = initialQuestions.nextLine();

            if (answer.equalsIgnoreCase("n")) {
                String firstName, lastName;

                System.out.println("If you want to join us, enter some details about you and your future account, please: ");
                System.out.print("What is your name?: ");
                firstName = initialQuestions.nextLine();
                System.out.print("What is your last name?: ");
                lastName = initialQuestions.nextLine();
                System.out.print("Choose your login: ");
                login = initialQuestions.nextLine();
                System.out.print("Choose your password: ");
                password = initialQuestions.nextLine();

                System.out.println("Hello " + firstName + " " + lastName + ", you have established your login to " + login +
                        " and your password to " + password + ".");
            } else if (answer.equalsIgnoreCase("y")) {
                System.out.print("Login: ");
                login = initialQuestions.nextLine();
                System.out.print("Password: ");
                password = initialQuestions.nextLine();
            } else System.out.println("Invalid character!");
        }
        while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));
    }
}
