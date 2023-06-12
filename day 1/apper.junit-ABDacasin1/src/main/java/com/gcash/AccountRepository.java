package com.gcash;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountRepository {

    // Declares an ArrayList named 'accounts' that stores the instances of the 'Account' class.
    // The 'accounts' will hold all the bank accounts managed by the AccountRepository.java
    private final List<Account> accounts = new ArrayList<>();

    // Method that creates a new account wit ha generated ID using 'UUID.randomUUID()'.
    public String createAccount(String name, Double initialBalance) {
        String id = UUID.randomUUID().toString();
        Account account = new Account(id, name, initialBalance); // create the account provided the info on the fields.
        accounts.add(account); // add to the 'accounts' list
        return id; // returns a generated account ID
    }

    // Method retrieves an 'Account' from the 'accounts' list. If there is a match, it returns the corresponding account object. Otherwise, it returns 'null'.
    public Account getAccount(String id) {
        for (Account account : accounts) { // iterate over each account in the list
            if (account.id().equals(id)) { // match id
                return account;
            }
        }
        return null;
    }

    // Method updates the balance of an account provided with the ID.
    public void updateBalance(String id, Double newBalance) {
        Account account = getAccount(id); // retrieve here first
        if (account != null) { // if the account exists (not null)
            Account updatedAccount = new Account(account.id(), account.name(), newBalance); // create a new 'Account' object with the same ID and name, but the updated balance.
            int index = accounts.indexOf(account); // find the index of the existing account in the 'accounts' list
            accounts.set(index, updatedAccount); // replace it with the updated using set()
        }
    }

    // Method that deletes account from the 'accounts' list based on the provided ID.
    public void deleteAccount(String id) {
        for (Account account : accounts) { // iterate over each account
            if (account.id().equals(id)) { // match id
                accounts.remove(account); // remove the account
                return;
            }
        }
        throw new IllegalArgumentException("Account not found"); // if no match is found
    }

    // Method that returns the total number of accounts stored in the 'accounts' list.
    public Integer getNumberOfAccounts() {
        return accounts.size();
    }

    // Method that checks if an account with the provided ID exists in the 'accounts' list.
    public boolean hasAccount(String id) {
        for (Account account : accounts) { // iterate over each account
            if (account.id().equals(id)) {
                return true;
            }
        }
        return false;
    }
}