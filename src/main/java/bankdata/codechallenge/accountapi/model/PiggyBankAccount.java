package bankdata.codechallenge.accountapi.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 */
public class PiggyBankAccount implements Account {
	
	private String userSecret;
	private Integer regNo;
	private Long accountNo;
	private String swiftCode;
	private BigDecimal balance;
	private List<AccountTransaction> transactions = new ArrayList<AccountTransaction>();

	/**
	 * 
	 * @param regNo
	 * @param accountNo
	 * @param swiftCode
	 * @param balance
	 */
	public PiggyBankAccount(String userSecret, Integer regNo, Long accountNo, String swiftCode, BigDecimal balance) {
		super();
		this.userSecret = userSecret;
		this.regNo = regNo;
		this.accountNo = accountNo;
		this.swiftCode = swiftCode;
		this.balance = balance;
	}
	
	@Override
	public String getUserSecret() {
		return userSecret;
	}

	@Override
	public Integer getRegNo() {
		return regNo;
	}

	@Override
	public Long getAccountNo() {
		return accountNo;
	}

	@Override
	public String getIBAN() {
		return swiftCode;
	}

	@Override
	public BigDecimal getBalance() {
		return balance;
	}

	@Override
	public List<AccountTransaction> getTransactions() {
		return transactions;
	}

	@Override
	public String getFullAccountNo() {
		 return String.format("%04d%010d", regNo, accountNo);
	}

	@Override
	public void transferTo(BigDecimal amount, Date valueDate, Account account) {
		Date postingDate = new Date();
		balance = balance.subtract(amount);
		transactions.add(new PiggyBankTransaction(amount, balance, valueDate, postingDate, account.getFullAccountNo()));
		account.recieve(amount, postingDate, valueDate, account.getFullAccountNo());
	}

	@Override
	public void recieve(BigDecimal amount, Date postingDate, Date valueDate, String srcAccountNo) {
		balance = balance.add(amount);
		transactions.add(new PiggyBankTransaction(amount, balance, valueDate, postingDate, srcAccountNo));
	}

	@Override
	public void initialize() {
		transactions.add(new PiggyBankTransaction(balance, balance, new Date(), new Date(), "CashEvent"));		
	}	
}
