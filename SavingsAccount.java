//child class

public class SavingsAccount extends BankAccount {
    private double interestRate;  // Example: 0.05 = 5% interest
    
    public SavingsAccount(String accountNumber, String accountHolder, double initialDeposit, double interestRate) {
        super(accountNumber, accountHolder, initialDeposit);  // Call parent constructor
        this.interestRate = interestRate;
    }
    
    // Special method only savings accounts have
    public void addInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Interest added: $" + interest + " at rate " + (interestRate * 100) + "%");
    }

    public double getInterestRate() { return interestRate; }
    
    @Override
    public void displayInfo() {
        System.out.println("SAVINGS - " + accountNumber + " | " + accountHolder + " | $" + balance + " | Rate: " + (interestRate * 100) + "%");
    }
}