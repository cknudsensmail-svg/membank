package bankdata.codechallenge.accountapi.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 */
public interface AccountTransaction {
	
	/**
	 * 
	 * @return
	 */
	BigDecimal getAmount();
	
	/**
	 * 
	 * @return
	 */
	BigDecimal getBalance();
	
	/**
	 * 	
	 * @return
	 */
	Date getValueDate();
	
	/**
	 * 
	 * @return
	 */
	Date getPostingDate();
	
	/**
	 * 
	 * @return
	 */
	String getPeerAccountNo();
}
