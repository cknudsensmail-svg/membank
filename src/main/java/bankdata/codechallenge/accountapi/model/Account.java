package bankdata.codechallenge.accountapi.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 */
public interface Account {
	
	String getUserSecret();
	Integer getRegNo();
	Long getAccountNo();
	String getFullAccountNo();
	String getIBAN();
	BigDecimal getBalance();
	List<AccountTransaction> getTransactions();
	
	/**
	 * Transfer funds to another account in membank
	 * @param amount The amount to transfer, please note membank does not allow overdrafts
	 * @param valueDate The valueDate to use for the transfer
	 * @param target Transfer the funds to this account
	 */
	void transferTo(BigDecimal amount, Date valueDate, Account target);
	
	/**
	 * Recieve funds
	 * @param amount
	 * @param postingDate
	 * @param valueDate
	 * @param src
	 */
	void recieve(BigDecimal amount, Date postingDate, Date valueDate, String src);
	
	/**
	 * Initialize the account
	 */
	void initialize();
}
