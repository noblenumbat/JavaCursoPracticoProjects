package org.jomaveger.bookexamples.chapter2;

public class SavingsBankAccount implements BankAccount {

    private double balance;
    private double annualInterestRate;
    private double lastAmountOfInterestEarned;

    public SavingsBankAccount(final Double balance, final Double interestRate) {
        this.balance = balance;
        this.annualInterestRate = interestRate;
        this.lastAmountOfInterestEarned = 0.0;
    }

    @Override
    public Double getBalance() {
        return this.balance;
    }

    @Override
    public void deposit(final Double amount) {
        this.balance = this.balance + amount;
    }

    @Override
    public void withdraw(final Double amount) {
        if (amount < this.balance) {
            this.balance = this.balance - amount;
        }
    }

    public void addInterest() {

        // Get the monthly interest rate.
        Double monthlyInterestRate = annualInterestRate / 12;

        // Calculate the last amount of interest earned.
        this.lastAmountOfInterestEarned = monthlyInterestRate * this.balance;

        // Add the interest to the balance.
        this.balance = this.balance + lastAmountOfInterestEarned;
    }

    public Double getAnnualInterestRate() {
        return this.annualInterestRate;
    }

    public Double getLastAmountOfInterestEarned() {
        return this.lastAmountOfInterestEarned;
    }
}
