package bankdata.codechallenge.accountapi.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 */
public class PiggyBankTransaction implements AccountTransaction {
	
	private BigDecimal amount;
	private BigDecimal balance;
	private Date valueDate;
	private Date postingDate;
	private String peerAccountNo;
	
	
	public PiggyBankTransaction(BigDecimal amount, BigDecimal balance, Date valueDate, Date postingDate, String peerAccountNo) {
		super();
		this.amount = amount;
		this.balance = balance;
		this.valueDate = valueDate;
		this.postingDate = postingDate;
		this.peerAccountNo = peerAccountNo;
	}

	@Override
	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public BigDecimal getBalance() {
		return balance;
	}

	@Override
	public Date getValueDate() {
		return valueDate;
	}

	@Override
	public Date getPostingDate() {
		return postingDate;
	}

	@Override
	public String getPeerAccountNo() {
		return peerAccountNo;
	}
}
