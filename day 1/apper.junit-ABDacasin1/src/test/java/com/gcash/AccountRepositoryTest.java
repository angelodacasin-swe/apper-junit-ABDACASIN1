package com.gcash;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountRepositoryTest {


    // JUnit DAY 1 notes //

    @Test
    void successfulAccountCreation() {
        // Setup
        AccountRepository repository = new AccountRepository();

        // Kick
        String accountId = repository.createAccount("Angelo", 89.9);

        // Verify
        Assertions.assertEquals(1, repository.getNumberOfAccounts());
        Assertions.assertEquals("Angelo", repository.getAccount(accountId).name());
        Assertions.assertNotNull(accountId);
    }

    @Test
    void successfulGetAccount() {
        AccountRepository repository = new AccountRepository();

        String accountId = repository.createAccount("Angelo", 89.9);

        Assertions.assertEquals("Angelo", repository.getAccount(accountId).name());
        Assertions.assertEquals(89.9, repository.getAccount(accountId).balance());
        Assertions.assertNull(repository.getAccount("randomid"));
    }

    @Test
    void successfulDelete() {
        //Setup
        AccountRepository repository = new AccountRepository();
        String id = repository.createAccount("Angelo", 89.9);

        //Kick
        repository.deleteAccount(id);

        //Verify
        Assertions.assertEquals(0, repository.getNumberOfAccounts());
    }

    @Test
    void successfulGetNumberOfAccounts() {
        //Setup and kick
        AccountRepository repository = new AccountRepository();
        String id0 = repository.createAccount("Angelo", 89.9);
//        String id1 = repository.createAccount("Angelo", 89.9);
//        String id2 = repository.createAccount("Angelo", 89.9);
//        String id3 = repository.createAccount("Angelo", 89.9);

        //Verify
        Assertions.assertEquals(4, repository.getNumberOfAccounts());

        //Setup
        repository.deleteAccount(id0);

        //Verify
        Assertions.assertEquals(3, repository.getNumberOfAccounts());
    }
}