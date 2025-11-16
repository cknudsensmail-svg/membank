package bankdata.codechallenge.accountapi.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import bankdata.codechallenge.accountapi.exceptions.MemBankException;
import bankdata.codechallenge.accountapi.model.Account;
import bankdata.codechallenge.accountapi.model.PiggyBankAccountAPITransfer;

/**
 * This AccountService implementation
 * Make sure to annotate @ Transactional if adding persistency
 */
@Service
public class DemoAccountService implements AccountService {
	private static Map<String, Account> memBank = new HashMap<>(); 
	
	@Override
	public void create(Account account) {
		if(memBank.containsKey(account.getFullAccountNo())) {
			throw new MemBankException(account.getFullAccountNo() + " already exists, cannot create new Account");
		}
		
		account.initialize();
		
		memBank.put(account.getFullAccountNo(), account);
	}

	@Override
	public Collection<Account> getAccounts() {
		return memBank.values();
	}
	
	@Override
	public Optional<Account> getAccount(String accountNo) {
		if(memBank.containsKey(accountNo)) {
			return Optional.of(memBank.get(accountNo));	
		}
		
		return Optional.empty();
	}

	@Override
	public void transfer(PiggyBankAccountAPITransfer aat) {
		
		if(!memBank.containsKey(aat.getFromAccountNo())) {
			throw new MemBankException(aat.getFromAccountNo() + " used as fromAccountNo not found in memBank, cannot transfer funds");
		}
		
		if(!memBank.get(aat.getFromAccountNo()).getUserSecret().equals(aat.getUserSecret())) {
			throw new MemBankException(aat.getFromAccountNo() + " userSecret mismatch, cannot transfer funds");
		} 
		
		if(!memBank.containsKey(aat.getToAccountNo())) {
			throw new MemBankException(aat.getToAccountNo() + " used as toAccountNo not found in memBank, cannot transfer funds");
		}
		
		if(aat.getValueDate() == null) {
			throw new MemBankException("No valueDate set for transfer, cannot transfer funds from " + aat.getFromAccountNo() + " to " + aat.getToAccountNo());
		}
		
		if(aat.getBigDecimalAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new MemBankException("amount to transfer must be positive, please inspect usage of transfer from " + aat.getFromAccountNo());
		}
		
		if(memBank.get(aat.getFromAccountNo()).getBalance().compareTo(aat.getBigDecimalAmount()) < 0) {
			throw new MemBankException("Transfer amount will overdraft account: " + aat.getFromAccountNo() + " memBank does not allow overdrafts");
		}
		
		memBank.get(aat.getFromAccountNo()).transferTo(aat.getBigDecimalAmount(), aat.getValueDate(), memBank.get(aat.getToAccountNo()));
	}
	
	/**
	 * Restart memBank
	 */
	static void purgeAll() {
		memBank = new HashMap<>(); 
	}
}
