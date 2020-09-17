package myGitProjects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

    public static void deposit(File file) throws IOException {
        Account userAccount = collectDataFromFile(file);

        Scanner question = new Scanner (System.in);
        System.out.print("How much do would you like to deposit?: ");
        double deposit = question.nextDouble();
        userAccount.balance += deposit;
        String depositInformation = "You have deposited " + deposit + " PLN, your new balance is: "
                + userAccount.getBalance() + " PLN";
        System.out.println(depositInformation);

        saveToFile(userAccount);
        saveHistoryToFile(depositInformation, userAccount.getLogin(), userAccount.getPassword());
    }

    public static void withdrawal(File file) throws IOException {
        Account userAccount = collectDataFromFile(file);

        Scanner question = new Scanner (System.in);
        System.out.print("How much do would you like to withdraw?: ");
        double withdrawal = question.nextDouble();
        userAccount.balance -= withdrawal;
        String depositInformation = "You have withdrawn " + withdrawal + " PLN, your new balance is: "
                + userAccount.getBalance() + " PLN";
        System.out.println(depositInformation);

        saveToFile(userAccount);
        saveHistoryToFile(depositInformation, userAccount.getLogin(), userAccount.getPassword());

    }

    private static void saveHistoryToFile(String info, String login, String password) throws IOException {
        FileWriter safeHistoryToFile = new FileWriter("historyAccountOf_" +
                login.substring(login.length()-3, login.length())
                + password.substring(password.length()-3, password.length()) + ".txt", true);
        BufferedWriter infoOut = new BufferedWriter(safeHistoryToFile);

        String actionInfo;
        LocalDate today = LocalDate.now();

        DateTimeFormatter plDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        actionInfo = today.format(plDateFormat) + ": " + info;
        infoOut.write(actionInfo + "\n");
        infoOut.close();

    }

    public static void collectHistoryFromFile(File file) throws IOException {
        Account userAccount = collectDataFromFile(file);
        String historyPath = "historyAccountOf_" +
                userAccount.getLogin().substring(userAccount.getLogin().length()-3,
                        userAccount.getLogin().length())+
                userAccount.getPassword().substring(userAccount.getPassword().length()-3,
                        userAccount.getPassword().length()) + ".txt";

        File historyPathFile = new File(historyPath);

        int historyFileLinesCount = (int) Files.lines(Paths.get(historyPath)).count();

        String [] accountHistory = new String[historyFileLinesCount];


        Scanner readHistoryFile = new Scanner(historyPathFile);
        for (int i = 0; i < historyFileLinesCount; i++) {
            accountHistory[i] = readHistoryFile.nextLine();
            System.out.println(accountHistory[i]);
        }
    }
}




