package testing.example.bank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@DisplayName("Tests for Bank")
class BankTest {

	private static final double AMOUNT = 7.0;

	private static final double INITIAL_BALANCE = 10.0;

	private static final String INEXISTENT_ID_MESSAGE = "No account found with id: 1";

	private static final int INEXISTENT_ID = 1;

	private Bank bank;

	// the collaborator of Bank that we manually instrument and inspect
	private List<BankAccount> bankAccounts;

	@BeforeEach
	void setup() {
		bankAccounts = new ArrayList<BankAccount>();
		bank = new Bank(bankAccounts);
	}
	
	@Nested
	@DisplayName("Happy cases")
	class HappyCases {
		@Test
		@DisplayName("Open a new bank account")
		void testOpenNewAccountShouldReturnAPositiveIdAndStoreTheAccount() {
			int newAccountId = bank.openNewBankAccount(0);
			assertTrue(newAccountId > 0, "Unexpected non positive id: " + newAccountId);
			assertEquals(newAccountId, bankAccounts.get(0).getId());
		}
		
		@Test
		@DisplayName("Increment balance with 'deposit'")
		void testDepositWhenAccountIsFoundShouldIncrementBalance() {
			// setup
			BankAccount testAccount = createTestAccount(INITIAL_BALANCE);
			bankAccounts.add(testAccount);
			// exercise
			bank.deposit(testAccount.getId(), AMOUNT);
			// verify
			assertEquals(INITIAL_BALANCE + AMOUNT, testAccount.getBalance(), 0);
		}
		
		@Test
		@DisplayName("Decrement balance with 'withdraw'")
		void testWithdrawWhenAccountIsFoundShouldDecrementBalance() {
			// setup
			BankAccount testAccount = createTestAccount(INITIAL_BALANCE);
			bankAccounts.add(testAccount);
			// exercise
			bank.withdraw(testAccount.getId(), AMOUNT);
			// verify
			assertEquals(INITIAL_BALANCE - AMOUNT, testAccount.getBalance(), 0);
		}
	}
	
	@Nested
	@DisplayName("Error cases")
	class ExceptionalCases {

		@Test
		@DisplayName("Account for 'deposit' not found")
		void testDepositWhenAccountIsNotFoundShouldThrow() {
			NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> bank.deposit(INEXISTENT_ID, INITIAL_BALANCE));
			assertEquals(INEXISTENT_ID_MESSAGE, e.getMessage());
		}
		
		@Test
		@DisplayName("Account fo 'withdraw' not found")
		void testWithdrawWhenAccountIsNotFoundShouldThrow() {
			NoSuchElementException e = assertThrows(NoSuchElementException.class, () -> bank.withdraw(INEXISTENT_ID, INITIAL_BALANCE));
			assertEquals(INEXISTENT_ID_MESSAGE, e.getMessage());
		}
	}

	private BankAccount createTestAccount(double initialBalance) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(initialBalance);
		return bankAccount;
	}

}
