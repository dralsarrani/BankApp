//Child class

public class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, String accountHolder, double initialDeposit, double overdraftLimit) {
        super(accountNumber, accountHolder, initialDeposit);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        double maxWithdraw = balance + overdraftLimit;
        if (amount > 0 && amount <= maxWithdraw) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount + " | New balance: $" + balance + " (Overdraft used: "
                    + (balance < 0 ? "Yes" : "No") + ")");
            System.out.println("Withdrew: $" + amount);
            if (balance < 0) {
                System.out.println("Warning: Overdraft used! Balance: $" + balance);
            }
        } else {
            System.out.println("Overdraft limit exceeded or invalid amount.");
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("CHECKING - " + accountNumber + " | " + accountHolder + " | $" + balance + " | Overdraft: $"
                + overdraftLimit);
    }
}