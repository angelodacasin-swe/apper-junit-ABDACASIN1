package com.gcash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BalanceServiceTest {
    private AccountRepository accountRepository;
    private BalanceService balanceService;
    private String accountId1;
    private String accountId2;

    // Creates a new instance of AccountRepository and BalanceService.
    // Then uses createAccount() method of the accountRepository to create two accounts that will be used in for the @Test as required.
    @BeforeEach
    public void setUp() {
        accountRepository = new AccountRepository();
        balanceService = new BalanceService(accountRepository);
        accountId1 = accountRepository.createAccount("Angelo", 150.0);
        accountId2 = accountRepository.createAccount("Clarice", 250.0);
    }

    @Test
    public void testDeposit() {
        // Setup
        Account account = accountRepository.getAccount(accountId1);

        // Kick
        Account updatedAccount = account.deposit(50.0); // deposit 50 to accountId1

        // Verify
        assertEquals(200.0, updatedAccount.balance()); // accountId1 from 150 (@BeforeTest value), it should now be 200.
    }

    @Test
    public void testWithdrawSufficientBalance() {
        // Setup
        Account account = accountRepository.getAccount(accountId1);

        // Kick
        Account updatedAccount = account.withdraw(50.0); // withdraw 50 from accountId1

        // Verify
        assertEquals(100.0, updatedAccount.balance()); // accountId1 from 150 (@BeforeTest value), it should now be 100
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        // Setup
        Account account = accountRepository.getAccount(accountId1);

        // Kick and Verify
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(500.0));
    }

    @Test
    public void testUpdateName() {
        // Setup
        Account account = accountRepository.getAccount(accountId1);

        // Kick
        Account updatedAccount = account.updateName("Angelo Dacasin"); // update account name to Angelo Dacasin

        // Verify
        assertEquals("Angelo Dacasin", updatedAccount.name()); // check if it will give the same value. assertEquals(expected, actual)
    }

    @Test
    public void testHasSufficientBalance() {
        Account account = accountRepository.getAccount(accountId1);
        assertTrue(account.hasSufficientBalance(50.0)); // accountId1 @BeforeTest balance is 150, thus true.
        assertFalse(account.hasSufficientBalance(200.0)); // accountId1 @BeforeTest balance is 150, thus false.
    }

    @Test
    public void testGetBalance() {
        Double balance = balanceService.getBalance(accountId1);
        assertEquals(150.0, balance); // accountId1 @BeforeTest balance is 150, thus true.
    }

    @Test
    public void testDebit() {
        balanceService.debit(accountId1, 50.0);
        Double balance = balanceService.getBalance(accountId1);
        assertEquals(100.0, balance); // accountId1 @BeforeTest balance is 150. If 50 is debited, 150 - 50 = 100.
    }

    @Test
    public void testCredit() {
        // Setup
        balanceService.credit(accountId1, 50.0);

        // Kick
        Double balance = balanceService.getBalance(accountId1);

        // Verify
        assertEquals(200.0, balance); // accountId1 @BeforeTest balance is 150. If 50 is credited, 150 + 50 = 200.
    }

    @Test
    public void testTransfer() {
        // Setup
        balanceService.transfer(accountId1, accountId2, 50.0);

        // Kick
        Double fromBalance = balanceService.getBalance(accountId1);
        Double toBalance = balanceService.getBalance(accountId2);

        //Verify
        // Transfer 50 from accountId1 which has 150 to accountId2 which has 250. Thus, we now have 100 for accountId1 and 300 for accountId2.
        assertEquals(100.0, fromBalance); // NOTE: accountId1 @BeforeTest balance is 150.
        assertEquals(300.0, toBalance); // NOTE: accountId2 @BeforeTest balance is 250.

    }

    @Test
    public void testGetBalanceAccountNotFound() {
        assertThrows(IllegalArgumentException.class, () -> balanceService.getBalance("non-existing-id"));
    }

    @Test
    public void testDebitAccountNotFound() {
        assertThrows(IllegalArgumentException.class, () -> balanceService.debit("non-existing-id", 100.0));
    }

    @Test
    public void testCreditAccountNotFound() {
        assertThrows(IllegalArgumentException.class, () -> balanceService.credit("non-existing-id", 100.0));
    }

    @Test
    public void testTransferFromAccountNotFound() {
        assertThrows(IllegalArgumentException.class, () -> balanceService.transfer("non-existing-id", accountId2, 100.0));
    }

    @Test
    public void testTransferToAccountNotFound() {
        assertThrows(IllegalArgumentException.class, () -> balanceService.transfer(accountId1, "non-existing-id", 100.0));
    }

    @Test
    public void testTransferFromAndToAccountsNotFound() {
        assertThrows(IllegalArgumentException.class, () -> balanceService.transfer("non-existing-id", "another-non-existing-id", 100.0));
    }

    @Test
    public void testDeleteAccount() {
        accountRepository.deleteAccount(accountId1);
        assertNull(accountRepository.getAccount(accountId1));
    }

    @Test
    public void testDeleteAccountNotFound() {
        assertThrows(IllegalArgumentException.class, () -> accountRepository.deleteAccount("non-existing-id"));
    }

    @Test
    public void testGetNumberOfAccounts() {
        Integer numberOfAccounts = accountRepository.getNumberOfAccounts();
        assertEquals(2, numberOfAccounts);
    }

    @Test
    public void testHasAccount() {
        assertTrue(accountRepository.hasAccount(accountId1));
    }

    @Test
    public void testHasAccountNotFound() {
        assertFalse(accountRepository.hasAccount("non-existing-id"));
    }
}