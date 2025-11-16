package bankdata.codechallenge.accountapi.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 */
public interface AccountAPITransfer {
	
	String getFromAccountNo();
	String getToAccountNo();
	Date getValueDate();
	Double getAmount();
	BigDecimal getBigDecimalAmount();
	String getUserSecret();
}