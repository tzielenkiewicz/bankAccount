package myGitProjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class account {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private double balance;

    public account(String firstName, String lastName, String login, String password, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.balance = balance;
    }


    public String getFirstName(){return firstName; }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public static void setYourNewAccount() throws FileNotFoundException {
        Scanner initialQuestions = new Scanner(System.in);
        System.out.println("If you want to join us, enter some details about you and your future account, " +
                "please: ");
        System.out.print("What is your name?: ");
        String firstName = initialQuestions.nextLine();
        System.out.print("What is your last name?: ");
        String lastName = initialQuestions.nextLine();
        System.out.print("Choose your login: ");
        String login = initialQuestions.nextLine();
        System.out.print("Choose your password: ");
        String password = initialQuestions.nextLine();

        double balance = 100;

        account newAccount = new account(firstName, lastName,
                login, password, balance);


        System.out.println("Hello " + newAccount.getFirstName() + " " + newAccount.getLastName() + ", " +
                "you have established " + "your login to '" + newAccount.getLogin() +
                "' and your password to '" + newAccount.getPassword() + "'.");
        System.out.println("And you have got a present from us - " + balance +
                " PLN for the start!");
        PrintWriter safeToFile = new PrintWriter("accountOf_" +
                newAccount.getLogin().substring(newAccount.getLogin().length()-3, newAccount.getLogin().length())
                + newAccount.getPassword().substring(newAccount.getPassword().length()-3, newAccount.getPassword().length()) + ".txt");
        safeToFile.println(newAccount.getFirstName());
        safeToFile.println(newAccount.getLastName());
        safeToFile.println(newAccount.getLogin());
        safeToFile.println(newAccount.getPassword());
        safeToFile.println(newAccount.getBalance());
        safeToFile.close();
    }

    public static void checkIfAccountExistAndCollectData() throws FileNotFoundException {
        Scanner checkLoginPassword = new Scanner(System.in);
        System.out.print("Login: ");
        String login = checkLoginPassword.nextLine();

        System.out.print("Password: ");
        String password = checkLoginPassword.nextLine();

        File checkFile = new File("accountOf_" + login.substring(login.length()-3, login.length())
                + password.substring(password.length()-3, password.length()) + ".txt");
        if (checkFile.isFile()) {
            Scanner readFile = new Scanner(checkFile);
            String firstName = readFile.nextLine();
            String lastName = readFile.nextLine();
            login = readFile.nextLine();
            password = readFile.nextLine();
            String stringBalance = readFile.nextLine();

            double balance = Double.parseDouble(stringBalance);

            account existingAccount = new account(firstName, lastName, login, password, balance);
            System.out.println("Welcome back " + existingAccount.getFirstName() + " " +
                    existingAccount.getLastName() + "! Your balance is now: " +
                    existingAccount.getBalance() + ".");
            }
        else {
            System.out.println("Account does not exist!");
            /*do {
                System.out.print("Would you like us to set an account for you? (y/n): ");
                answer = initialQuestions.nextLine();
            }
            while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));

            if (answer.equalsIgnoreCase("y")) account.setYourNewAccount();
            else System.exit(0);*/
        }
    }
}



