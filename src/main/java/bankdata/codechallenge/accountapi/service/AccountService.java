package bankdata.codechallenge.accountapi.service;

import java.util.Collection;
import java.util.Optional;

import bankdata.codechallenge.accountapi.model.Account;
import bankdata.codechallenge.accountapi.model.PiggyBankAccountAPITransfer;

/**
 * The service handling account in this demo api
 */
public interface AccountService {
	
	/**
	 * Create an account
	 * @param account
	 */
	void create(Account account);
	
	/**
	 * Get all accounts
	 * @return
	 */
	Collection<Account> getAccounts();
	
	/**
	 * Get the Optional account by accountNo 
	 * @param accountNo
	 * @return
	 */
	Optional<Account> getAccount(String accountNo);
	
	/**
	 * Transfer btw. accounts according to AccountAPITransfer
	 * @param aat
	 */
	void transfer(PiggyBankAccountAPITransfer aat);
}
