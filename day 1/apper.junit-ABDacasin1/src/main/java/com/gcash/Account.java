package com.gcash;

// Define the Account class as record which generates useful methods.
// The Account class has 3 fields: id, name, and balance.
public record Account(String id, String name, Double balance) {

    // Creates a new Account object with the updated balance.
    public Account deposit(Double amount) {
        return new Account(id, name, balance + amount);
    }

    // Method to withdraw. If amount (to withdraw) <= balance (what account owner has), allow. If not, throw "Insufficient balance".
    public Account withdraw(Double amount) {
        if (amount <= balance) {
            return new Account(id, name, balance - amount);
        } else {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }

    // Method to update name and account holder. It creates a new Account object with the updated name and the same balance, and returns it.
    public Account updateName(String newName) {
        return new Account(id, newName, balance);
    }

    // Method to check if account has sufficient balance. Returns "true" if account balance is >= to the specified amount, and "false" if otherwise.
    public boolean hasSufficientBalance(Double amount) {
        return balance >= amount;
    }
}