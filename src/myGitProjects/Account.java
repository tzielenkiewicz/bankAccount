package myGitProjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Account {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private double balance;

    public Account(String firstName, String lastName, String login, String password, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.balance = balance;
    }


    public String getFirstName() { return firstName; }

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

        Account newAccount = new Account(firstName, lastName,
                login, password, balance);


        System.out.println("Hello " + newAccount.getFirstName() + " " + newAccount.getLastName() + ", " +
                "you have established " + "your login to '" + newAccount.getLogin() +
                "' and your password to '" + newAccount.getPassword() + "'.");
        System.out.println("And you have got a present from us - " + balance +
                " PLN for the start!");
        saveToFile(newAccount);
    }

    private static void saveToFile(Account account) throws FileNotFoundException {
        PrintWriter safeToFile = new PrintWriter("accountOf_" +
                account.getLogin().substring(account.getLogin().length()-3, account.getLogin().length())
                + account.getPassword().substring(account.getPassword().length()-3, account.getPassword().length()) + ".txt");
        safeToFile.println(account.getFirstName());
        safeToFile.println(account.getLastName());
        safeToFile.println(account.getLogin());
        safeToFile.println(account.getPassword());
        safeToFile.println(account.getBalance());
        safeToFile.close();
    }

    public static void checkIfAccountExist(File file) throws FileNotFoundException {

        if (file.isFile()) {
            System.out.println("Welcome back " + collectDataFromFile(file).getFirstName() + " " +
                    collectDataFromFile(file).getLastName() + "! Your balance is now: " +
                    collectDataFromFile(file).getBalance() + ".");
        }
        else {
            System.out.println("Account does not exist!");
            String answer;
            do {
                System.out.print("Would you like us to set an account for you? (y/n): ");
                Scanner initialQuestions = new Scanner(System.in);
                answer = initialQuestions.nextLine();
            }
            while (!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));

            if (answer.equalsIgnoreCase("y")) Account.setYourNewAccount();
            else System.exit(0);

        }
    }

    public static Account collectDataFromFile(File file) throws FileNotFoundException {
        String [] accountDataSet = new String[5];
        Scanner readFile = new Scanner(file);
        for (int i = 0; i < 5; i++) {
            accountDataSet[i] = readFile.nextLine();
        }

        return new Account(accountDataSet[0], accountDataSet[1],
                accountDataSet[2], accountDataSet[3], Double.parseDouble(accountDataSet[4]));
    }

    public static void displayDashboard(File file) throws FileNotFoundException {
        System.out.println();
        System.out.println("---------------------------------------------------------------");
        System.out.println("Name: " + Account.collectDataFromFile(file).getFirstName() + " " +
                Account.collectDataFromFile(file).getLastName());
        System.out.println("Balance: " + Account.collectDataFromFile(file).getBalance());
        System.out.println();

        System.out.println("Choose your action: ");
        System.out.println();
        System.out.println("1. Deposit");
        System.out.println("2. Withdrawal");
        System.out.println("3. Show account history");
        System.out.println("4. Change your password");
        System.out.println("5. Create an account in another currency");
        System.out.println("6. Other accounts");
        System.out.println("7. Logout");
    }

    public static void deposit(File file) throws FileNotFoundException {
        Account userAccount = collectDataFromFile(file);

        Scanner question = new Scanner (System.in);
        System.out.println("How much do would you like to deposit?: ");
        double deposit = question.nextDouble();
        userAccount.balance += deposit;
        System.out.println("Wonderful, your new balance is: "
                + userAccount.getBalance());

        saveToFile(userAccount);


    }
}



