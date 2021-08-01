package testing.example.bank;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for BankAccount")
class BankAccountTest {
	

	private static final String CANNOT_WITHDRAW_MESSAGE = "Cannot withdraw 7.0 from 0.0";
	private static final String NEGATIVE_AMOUNT_MESSAGE = "Negative amount: -1.0";
	private static final double NEGATIVE_AMOUNT = -1.0;
	private static final double INITIAL_BALANCE = 10.0;
	private static final double AMOUNT = 7.0;

	@Test
	@DisplayName("A positive number is automatically assigned as id")
	void testIdIsAutomaticallyAssignedAsPositiveNumber() {
		// setup
		BankAccount bankAccount = new BankAccount();
		// verify
		assertTrue(bankAccount.getId() > 0, "Id should be positive");
	}
	
	@Test
	void testIdsAreIncremental() {
		BankAccount firstAccount = new BankAccount();
		BankAccount secondAccount = new BankAccount();
		assertTrue(firstAccount.getId() < secondAccount.getId(), 
			()-> "Ids should be incremental, but" + firstAccount.getId()
			+ " is not less than " + secondAccount.getId());
	}

	@Test
	void testDepositWhenAmountIsCorrectShouldIncreaseBalance() {
		// setup
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(AMOUNT);
		// exercise
		bankAccount.deposit(INITIAL_BALANCE);
		// verify
		assertEquals(INITIAL_BALANCE + AMOUNT, bankAccount.getBalance(), 0);
	}
	
			
	@Test
	void testDepositWhenAmountIsNegativeShouldThrow() {
		BankAccount bankAccount = new BankAccount();
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, 
			()->bankAccount.deposit(NEGATIVE_AMOUNT));
		// perform assertions on the thrown exception
		assertAll(
			()->assertEquals(NEGATIVE_AMOUNT_MESSAGE, e.getMessage()),
			()->assertEquals(0, bankAccount.getBalance(), 0)
		);
	}
	
	@Test
	void testWithdrawWhenAmountIsNegativeShouldThrow() {
		BankAccount bankAccount = new BankAccount();
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()->bankAccount.withdraw(NEGATIVE_AMOUNT));
		assertEquals(NEGATIVE_AMOUNT_MESSAGE, e.getMessage());
		assertEquals(0, bankAccount.getBalance(), 0);
	}
	
	@Test
	void testWithdrawWhenBalanceIsUnsufficientShouldThrow() {
		BankAccount bankAccount = new BankAccount();
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, ()->bankAccount.withdraw(AMOUNT));
		assertEquals(CANNOT_WITHDRAW_MESSAGE, e.getMessage());
		assertEquals(0, bankAccount.getBalance(), 0);
	}
	
	@Test
	void testWithdrawWhenBalanceIsSufficientShouldDecreaseBalance() {
		// setup
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBalance(INITIAL_BALANCE);
		// exercise
		bankAccount.withdraw(AMOUNT);
		// verify
		assertEquals(INITIAL_BALANCE - AMOUNT, bankAccount.getBalance(), 0);
	}
}
