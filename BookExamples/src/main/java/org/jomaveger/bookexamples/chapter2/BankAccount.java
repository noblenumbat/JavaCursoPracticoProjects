package org.jomaveger.bookexamples.chapter2;

public interface BankAccount {

    Double getBalance();

    void deposit(final Double amount);

    void withdraw(final Double amount);
}
