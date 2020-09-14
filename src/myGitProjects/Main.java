package myGitProjects;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner initialQuestions = new Scanner(System.in);
        System.out.println("Welcome to Tee Bank!");

        String answer;

        do {
            System.out.print("Have you already got an account? (y/n): ");
            answer = initialQuestions.nextLine();
        } while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));

            if (answer.equalsIgnoreCase("n")) account.setYourNewAccount();

            else if (answer.equalsIgnoreCase("y")) {
                account.checkIfAccountExistAndCollectData();

            }
            else System.out.println("Invalid character!");
            }

    }

