package bankdata.codechallenge.accountapi.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import bankdata.codechallenge.accountapi.exceptions.MemBankException;
import bankdata.codechallenge.accountapi.model.Account;
import bankdata.codechallenge.accountapi.model.PiggyBankAccountAPITransfer;
import bankdata.codechallenge.accountapi.model.PiggyBankAccount;

@ExtendWith(MockitoExtension.class)
public class TestAccountService {
	
	/**
	 * Make sure memBank is empty before each test 
	 */
	@BeforeEach
	void setUp() {
		DemoAccountService.purgeAll();
	}
	
	@Test
	void testCreateAccountMustFailDoublet() {
		AccountService accountService = new DemoAccountService();
		
		Integer regNo = 9308;
		Long accountNo = 1234567891L;
		String iban = String.format("DK55%d%010d", regNo, accountNo);
				
		Account mockAccount = new PiggyBankAccount("MySecret", regNo, accountNo, iban, BigDecimal.valueOf(10000.0));
		accountService.create(mockAccount);
		assertThrows(MemBankException.class, () -> accountService.create(mockAccount));
	}
	
	@Test
	void testCreateAccountsSucces() {
		AccountService accountService = new DemoAccountService();
				
		Integer regNo = 9308;
		Long accountNo = 1234567891L;
		String iban = String.format("DK55%d%010d", regNo, accountNo);
		
		Account acc1 = new PiggyBankAccount("MySecret", regNo, accountNo, iban, BigDecimal.valueOf(10000.0));
		accountService.create(acc1);
		
		regNo = 9308;
		accountNo = 1234567892L;
		iban = String.format("DK55%d%010d", regNo, accountNo);
		Account acc2 = new PiggyBankAccount("MySecret", regNo, accountNo, iban, BigDecimal.valueOf(10000.0));
		accountService.create(acc2);
		
		assert(accountService.getAccounts().size() == 2);
		assert(accountService.getAccount(acc1.getFullAccountNo()).isPresent());
		assert(accountService.getAccount(acc2.getFullAccountNo()).isPresent());
	}
	
	@Test
	void testTransfersFails() {
		AccountService accountService = new DemoAccountService();
		assert(accountService.getAccounts().size() == 0);
		
		Integer regNo = 9308;
		Long accountNo = 1234567891L;
		String iban = String.format("DK55%d%010d", regNo, accountNo);
		
		Account acc1 = new PiggyBankAccount("MySecret", regNo, accountNo, iban, BigDecimal.valueOf(10000.0));
		accountService.create(acc1);
		
		regNo = 9308;
		accountNo = 1234567892L;
		iban = String.format("DK55%d%010d", regNo, accountNo);
		Account acc2 = new PiggyBankAccount("MySecret", regNo, accountNo, iban, BigDecimal.valueOf(10000.0));
		accountService.create(acc2);
		
		assertThrows(MemBankException.class, () -> accountService.transfer(new PiggyBankAccountAPITransfer("MyWrongSecret", acc1.getFullAccountNo(), acc2.getFullAccountNo(), new Date(), 10000.01)));
		assertThrows(MemBankException.class, () -> accountService.transfer(new PiggyBankAccountAPITransfer(null, acc1.getFullAccountNo(), acc2.getFullAccountNo(), new Date(), -0.01)));
		
		assertThrows(MemBankException.class, () -> accountService.transfer(new PiggyBankAccountAPITransfer("MySecret", "not-there", acc2.getFullAccountNo(), new Date(), 1.0)));
		assertThrows(MemBankException.class, () -> accountService.transfer(new PiggyBankAccountAPITransfer("MySecret", null, acc2.getFullAccountNo(), new Date(), 1.0)));
		
		assertThrows(MemBankException.class, () -> accountService.transfer(new PiggyBankAccountAPITransfer("MySecret", acc1.getFullAccountNo(), "not-there", new Date(), 1.0)));
		assertThrows(MemBankException.class, () -> accountService.transfer(new PiggyBankAccountAPITransfer("MySecret", acc1.getFullAccountNo(), "not-there", new Date(), 1.0)));
		
		assertThrows(MemBankException.class, () -> accountService.transfer(new PiggyBankAccountAPITransfer("MySecret", acc1.getFullAccountNo(), "not-there", new Date(), 1.0)));
		assertThrows(MemBankException.class, () -> accountService.transfer(new PiggyBankAccountAPITransfer("MySecret", acc1.getFullAccountNo(), "not-there", new Date(), 1.0)));
		
		assertThrows(MemBankException.class, () -> accountService.transfer(new PiggyBankAccountAPITransfer("MySecret", acc1.getFullAccountNo(), acc2.getFullAccountNo(), null, 1.0)));
		
		assertThrows(MemBankException.class, () -> accountService.transfer(new PiggyBankAccountAPITransfer("MySecret", acc1.getFullAccountNo(), acc2.getFullAccountNo(), new Date(), 10000.01)));
		assertThrows(MemBankException.class, () -> accountService.transfer(new PiggyBankAccountAPITransfer("MySecret", acc1.getFullAccountNo(), acc2.getFullAccountNo(), new Date(), -0.01)));
	}
	
	@Test
	void testTransfersSucces() {
		AccountService accountService = new DemoAccountService();
		assert(accountService.getAccounts().size() == 0);
		
		Integer regNo = 9308;
		Long accountNo = 1234567891L;
		String iban = String.format("DK55%d%010d", regNo, accountNo);
		
		Account acc1 = new PiggyBankAccount("MySecret", regNo, accountNo, iban, BigDecimal.valueOf(10000.0));
		accountService.create(acc1);
		
		regNo = 9308;
		accountNo = 1234567892L;
		iban = String.format("DK55%d%010d", regNo, accountNo);
		Account acc2 = new PiggyBankAccount("MySecret", regNo, accountNo, iban, BigDecimal.valueOf(10000.0));
		accountService.create(acc2);
		
		accountService.transfer(new PiggyBankAccountAPITransfer("MySecret", acc1.getFullAccountNo(), acc2.getFullAccountNo(), new Date(), 0.01));
		
		assert(acc1.getBalance().compareTo(BigDecimal.valueOf(9999.99)) == 0);
		assert(acc2.getBalance().compareTo(BigDecimal.valueOf(10000.01)) == 0);
	}
}
