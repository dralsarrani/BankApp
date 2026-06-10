import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BankingApp {
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    loadFromFile(); // Load existing accounts on startup

    private static int nextAccountNumber = 1001; // Starts at 1001, then 1002, etc.
    private static final String FILE_NAME = "accounts.txt";

    public static void main(String[] args) {
        int choice;

        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    createSavingsAccount();
                    break;
                case 2:
                    createCheckingAccount();
                    break;
                case 3:
                    depositMoney();
                    break;
                case 4:
                    withdrawMoney();
                    break;
                case 5:
                    addInterestToSavings();
                    break;
                case 6:
                    showAccountDetails();
                    break;
                case 7:
                    showAllAccounts();
                    break;
                case 8:
                    saveToFile(); // Save before exiting
                    System.out.println("Thank you for using Banking App!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== BANKING APP ===");
        System.out.println("1. Create Savings Account");
        System.out.println("2. Create Checking Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Add Interest (Savings only)");
        System.out.println("6. Show Account Details");
        System.out.println("7. Show Transaction History");
        System.out.println("8. Show All Accounts");
        System.out.println("9. Exit");
        System.out.print("Choose (1-8): ");
    }

    private static void createSavingsAccount() {
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();

        System.out.print("Enter initial deposit: $");
        double initialDeposit = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter interest rate (as decimal, e.g., 0.05 for 5%): ");
        double interestRate = scanner.nextDouble();
        scanner.nextLine();

        String accountNumber = "SAV" + nextAccountNumber;
        nextAccountNumber++;

        SavingsAccount account = new SavingsAccount(accountNumber, name, initialDeposit, interestRate);
        accounts.add(account);

        System.out.println("✓ Savings Account created!");
        System.out.println("   Account Number: " + accountNumber);
    }

    private static void createCheckingAccount() {
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();

        System.out.print("Enter initial deposit: $");
        double initialDeposit = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter overdraft limit: $");
        double overdraftLimit = scanner.nextDouble();
        scanner.nextLine();

        String accountNumber = "CHK" + nextAccountNumber;
        nextAccountNumber++;

        CheckingAccount account = new CheckingAccount(accountNumber, name, initialDeposit, overdraftLimit);
        accounts.add(account);

        System.out.println("✓ Checking Account created!");
        System.out.println("   Account Number: " + accountNumber);
    }

    private static void depositMoney() {
        BankAccount account = findAccount();
        if (account == null)
            return;

        System.out.print("Enter amount to deposit: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        account.deposit(amount);
    }

    private static void withdrawMoney() {
        BankAccount account = findAccount();
        if (account == null)
            return;

        System.out.print("Enter amount to withdraw: $");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        account.withdraw(amount);
    }

    private static void addInterestToSavings() {
        BankAccount account = findAccount();
        if (account == null)
            return;

        // Check if it's actually a SavingsAccount
        if (account instanceof SavingsAccount) {
            SavingsAccount savings = (SavingsAccount) account; // Cast to SavingsAccount
            savings.addInterest();
        } else {
            System.out.println("❌ Interest can only be added to Savings Accounts!");
        }
    }

    private static void showAccountDetails() {
        BankAccount account = findAccount();
        if (account == null)
            return;

        System.out.println("\n--- ACCOUNT DETAILS ---");
        account.displayInfo();
    }

    private static void showAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts created yet.");
            return;
        }

        System.out.println("\n--- ALL ACCOUNTS ---");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.print((i + 1) + ". ");
            accounts.get(i).displayInfo();
        }
    }

    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (BankAccount account : accounts) {
                if (account instanceof SavingsAccount) {
                    SavingsAccount sa = (SavingsAccount) account;
                    writer.println("SAV|" + sa.getAccountNumber() + "|" + sa.getAccountHolder() + "|" + sa.getBalance()
                            + "|" + sa.getInterestRate());
                } else if (account instanceof CheckingAccount) {
                    CheckingAccount ca = (CheckingAccount) account;
                    writer.println("CHK|" + ca.getAccountNumber() + "|" + ca.getAccountHolder() + "|" + ca.getBalance()
                            + "|" + ca.getOverdraftLimit());
                }
            }
            System.out.println("✓ Accounts saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");

                if (parts[0].equals("SAV")) {
                    String accNum = parts[1];
                    String holder = parts[2];
                    double balance = Double.parseDouble(parts[3]);
                    double rate = Double.parseDouble(parts[4]);
                    SavingsAccount sa = new SavingsAccount(accNum, holder, balance, rate);
                    accounts.add(sa);
                } else if (parts[0].equals("CHK")) {
                    String accNum = parts[1];
                    String holder = parts[2];
                    double balance = Double.parseDouble(parts[3]);
                    double overdraft = Double.parseDouble(parts[4]);
                    CheckingAccount ca = new CheckingAccount(accNum, holder, balance, overdraft);
                    accounts.add(ca);
                }
            }
            System.out.println("✓ Loaded " + accounts.size() + " accounts from file.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }

    // Helper method to find an account by account number
    private static BankAccount findAccount() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts exist. Create an account first.");
            return null;
        }

        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine();

        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accNum)) {
                return account;
            }
        }

        System.out.println("❌ Account not found: " + accNum);
        return null;
    }
}