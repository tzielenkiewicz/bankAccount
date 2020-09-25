package myGitProjects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Account {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private double balance;
    private String currency;


    public Account(String firstName, String lastName, String login, String password, double balance, String currency) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.balance = balance;
        this.currency = currency;
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

    public String getCurrency() { return currency; }

    public static File loginProcedure() throws FileNotFoundException {
        Scanner checkLoginPassword = new Scanner(System.in);
        File checkFile = null;

        System.out.print("Login: ");
        String login = checkLoginPassword.nextLine();

        System.out.print("Password: ");
        String password = checkLoginPassword.nextLine();

        checkFile = new File("PLNaccountOf_" + login.substring(login.length() - 3, login.length())
                + password.substring(password.length() - 3, password.length()) + ".txt");

        return checkFile;
    }
    public static File setYourNewAccount() throws FileNotFoundException {
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
        String currency = "PLN";

        Account newAccount = new Account(firstName, lastName,
                login, password, balance, currency);
        saveToFile(newAccount);

        System.out.println("Hello " + newAccount.getFirstName() + " " + newAccount.getLastName() + ", " +
                "you have established " + "your login to '" + newAccount.getLogin() +
                "' and your password to '" + newAccount.getPassword() + "'.");
        System.out.println("And you have got a present from us - " + balance +
                newAccount.getCurrency() + "for the start!");
        return loginProcedure();

    }

    static void saveToFile(Account account) throws FileNotFoundException {
        PrintWriter safeToFile = new PrintWriter(account.getCurrency() + "accountOf_" +
                account.getLogin().substring(account.getLogin().length()-3, account.getLogin().length())
                + account.getPassword().substring(account.getPassword().length()-3, account.getPassword().length()) + ".txt");
        safeToFile.println(account.getFirstName());
        safeToFile.println(account.getLastName());
        safeToFile.println(account.getLogin());
        safeToFile.println(account.getPassword());
        safeToFile.println(account.getBalance());
        safeToFile.println(account.getCurrency());
        safeToFile.close();
    }

    public static File checkIfAccountExist(File file) throws FileNotFoundException {

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

            if (answer.equalsIgnoreCase("y")) file = setYourNewAccount();

            else {
                System.out.println("Try to log in once again...");
                System.exit(0);
            }
        }
        return file;
    }

    public static Account collectDataFromFile(File file) throws FileNotFoundException {
        String [] accountDataSet = new String[6];
        Scanner readFile = new Scanner(file);
        for (int i = 0; i < 6; i++) {
            accountDataSet[i] = readFile.nextLine();
        }

        return new Account(accountDataSet[0], accountDataSet[1],
                accountDataSet[2], accountDataSet[3], Double.parseDouble(accountDataSet[4]),
        accountDataSet[5]);
    }

    public static void displayDashboard(File file) throws FileNotFoundException {
        System.out.println();
        System.out.println("---------------------------------------------------------------");
        System.out.println("Name: " + Account.collectDataFromFile(file).getFirstName() + " " +
                Account.collectDataFromFile(file).getLastName());
        System.out.println("Balance: " + Account.collectDataFromFile(file).getBalance() + Account.collectDataFromFile(file).getCurrency());
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
        System.out.print("How much would you like to deposit?: ");
        double deposit = question.nextDouble();
        userAccount.balance += deposit;
        String depositInformation = "You have deposited " + deposit + " " + userAccount.getCurrency() +
                ", your new balance is: "
                + userAccount.getBalance() + userAccount.getCurrency();
        System.out.println(depositInformation);

        saveToFile(userAccount);
        saveHistoryToFile(depositInformation, userAccount.getLogin(), userAccount.getPassword(), userAccount.getCurrency());
    }

    public static void withdrawal(File file) throws IOException {
        Account userAccount = collectDataFromFile(file);

        Scanner question = new Scanner (System.in);
        System.out.print("How much would you like to withdraw?: ");
        double withdrawal = question.nextDouble();
        userAccount.balance -= withdrawal;
        String depositInformation = "You have withdrawn " + withdrawal + " " + userAccount.getCurrency() + ", your new balance is: "
                + userAccount.getBalance() + " PLN";
        System.out.println(depositInformation);

        saveToFile(userAccount);
        saveHistoryToFile(depositInformation, userAccount.getLogin(), userAccount.getPassword(), userAccount.getCurrency());

    }

    private static void saveHistoryToFile(String info, String login, String password, String currency) throws IOException {
        FileWriter safeHistoryToFile = new FileWriter("history" + currency + "accountOf_" +
                login.substring(login.length()-3, login.length())
                + password.substring(password.length()-3, password.length()) + ".txt", true);
        BufferedWriter infoOut = new BufferedWriter(safeHistoryToFile);

        String actionInfo;
        LocalDate today = LocalDate.now();

        DateTimeFormatter plDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        actionInfo = today.format(plDateFormat) + ": " + info + "\n";
        infoOut.write(actionInfo);
        infoOut.close();

    }

    public static void collectHistoryFromFile(File file) throws IOException {
        Account userAccount = collectDataFromFile(file);
        String historyPath = "history" + userAccount.getCurrency() + "accountOf_" +
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

            // Alternatively, following instruction may be used

            // System.out.println(Files.readAllLines(Paths.get
            // (historyPath), Charset.forName("UTF-8")));
        }
    }

    public static void changePassword(File file) throws IOException {
        Account existingAccount = Account.collectDataFromFile(file);
        String newPassword1, newPassword2;
        Scanner changePassword = new Scanner(System.in);
        do {
            System.out.println("Input new password: ");
            newPassword1 = changePassword.nextLine();
            System.out.println("Confirm new password: ");
            newPassword2 = changePassword.nextLine();
            if (!newPassword1.equals(newPassword2)) System.out.println(
                    "Confirmation did not succeed! Try once again.");
            else {
                System.out.println("You have successfully changed your password!");
                existingAccount = new Account(existingAccount.getFirstName(),
                        existingAccount.getLastName(), existingAccount.getLogin(),
                        newPassword1, existingAccount.getBalance(),
                        existingAccount.getCurrency());

                String filePath = file.toString();
                String historyFilePath = "history" + existingAccount.getCurrency() + filePath.substring(3, filePath.length());
                String newHistoryFilePath = "history" + existingAccount.getCurrency() + filePath.substring(3, 16) +
                        existingAccount.getPassword().substring(existingAccount.getPassword().length()-3,
                                existingAccount.getPassword().length()) + ".txt";

                Files.move(Paths.get(historyFilePath), Paths.get(newHistoryFilePath),
                        StandardCopyOption.REPLACE_EXISTING);

                file.delete();
                Account.saveToFile(existingAccount);
                System.out.println("You have to log out now.");
                System.out.println("Logging out...");
            }
        }
        while (!newPassword1.equals(newPassword2));
    }

    public static void createCurrencyAccount(String currency, File file) throws FileNotFoundException {
        System.out.println("Congratulations, we have just created an account in " + currency + " for you.");

        Account currencyAccount = new Account (Account.collectDataFromFile(file).getFirstName(),
                Account.collectDataFromFile(file).getLastName(),
                Account.collectDataFromFile(file).getLogin(),
                Account.collectDataFromFile(file).getPassword(), 0, currency);
        saveToFile(currencyAccount);
    }

}




