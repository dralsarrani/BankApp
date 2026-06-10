//Parent class

import java.util.ArrayList;

public class BankAccount {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;
    protected ArrayList<String> transactionHistory = new ArrayList<>();
    
    public BankAccount(String accountNumber, String accountHolder, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;
        transactionHistory.add("Account created with $" + initialDeposit);
    }
    
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: $" + amount + " | New balance: $" + balance);
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid amount.");
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount + " | New balance: $" + balance);
            System.out.println("Withdrew: $" + amount);
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }
    
    public void displayInfo() {
        System.out.println("Account: " + accountNumber + " | Holder: " + accountHolder + " | Balance: $" + balance);
    }
    
    // Getters
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getAccountHolder() { return accountHolder; }
}