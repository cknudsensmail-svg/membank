package bankdata.codechallenge.accountapi.model;

import java.math.BigDecimal;
import java.util.Date;

public class PiggyBankAccountAPITransfer implements AccountAPITransfer {
	private String userSecret;
	private String fromAccountNo;
	private String toAccountNo;
	private Date valueDate;
	private Double amount;
	
	public PiggyBankAccountAPITransfer(String userSecret, String fromAccountNo, String toAccountNo, Date valueDate, Double amount) {
		super();
		this.userSecret = userSecret;
		this.fromAccountNo = fromAccountNo;
		this.toAccountNo = toAccountNo;
		this.valueDate = valueDate;
		this.amount = amount;
	}
	
	public String getFromAccountNo() {
		return fromAccountNo;
	}

	public AccountAPITransfer setFromAccountNo(String fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
		return this;
	}

	public String getToAccountNo() {
		return toAccountNo;
	}

	public AccountAPITransfer setToAccountNo(String toAccountNo) {
		this.toAccountNo = toAccountNo;
		return this;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public AccountAPITransfer setValueDate(Date valueDate) {
		this.valueDate = valueDate;
		return this;
	}

	public Double getAmount() {
		return amount;
	}
	
	public BigDecimal getBigDecimalAmount() {
		return BigDecimal.valueOf(amount);
	}

	public AccountAPITransfer setAmount(Double amount) {
		this.amount = amount;
		return this;
	}

	public String getUserSecret() {
		return userSecret;
	}

	public AccountAPITransfer setUserSecret(String userSecret) {
		this.userSecret = userSecret;
		return this;
	}
}
