package com.gcash;

public class BalanceService {

    // Use AccountRepository as dependency. The accountRepository field holds an instance of the AccountRepository class.
    private final AccountRepository accountRepository;

    public BalanceService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // getBalance() method retrieved the current balance of an account based on the ID.
    public Double getBalance(String id) {
        Account account = accountRepository.getAccount(id); // use getAccount() from the accountRepository to fetch the account object.
        if (account != null) {
            return account.balance();
        } else {
            throw new IllegalArgumentException("Account is not found. Please double check the account information.");
        }
    }

    // debit() method is used to subtract an amount from the balance of an account.
    public void debit(String id, Double amount) {
        Account account = accountRepository.getAccount(id); // use getAccount() from the accountRepository to fetch the account object.
        if (account != null) {
            accountRepository.updateBalance(id, account.balance() - amount);
        } else {
            throw new IllegalArgumentException("Account is not found. Please double check the account information.");
        }
    }

    // credit() method is used to add an amount from the balance of an account.
    public void credit(String id, Double amount) {
        Account account = accountRepository.getAccount(id); // use getAccount() from the accountRepository to fetch the account object.
        if (account != null) {
            accountRepository.updateBalance(id, account.balance() + amount);
        } else {
            throw new IllegalArgumentException("Account is not found. Please double check the account information.");
        }
    }

    // transfer() method is used to transfer an amount from one account to another.
    public void transfer(String from, String to, Double amount) {
        Account fromAccount = accountRepository.getAccount(from); // retrieves the fromAccount using the getAccount() method from the accountRepository
        Account toAccount = accountRepository.getAccount(to); // retrieves the toAccount using the getAccount() method from the accountRepository

        // If both accounts exist, it will subtract the 'amount' from the balance of the 'fromAccount' and adds it the balance of the 'toAccount' using the 'updateBalance()' method from the accountRepository.
        if (fromAccount != null && toAccount != null) {
            accountRepository.updateBalance(from, fromAccount.balance() - amount);
            accountRepository.updateBalance(to, toAccount.balance() + amount);
        } else {
            throw new IllegalArgumentException("Account is not found. Please double check the account information."); // if either account is not found.
        }
    }
}