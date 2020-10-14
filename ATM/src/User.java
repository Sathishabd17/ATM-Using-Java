import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
	
	// First name of the user
	private String FristName;
	
	// Last name of the user
	private String LastName;
	
	// ID number of the user
	private String Id;
	
	// MD5 hash of the user
	private byte Pin[];
	
	// List of accounts of the user
	private ArrayList<Account> Accounts;
	
	
	/**
	 * Create a new user
	 * @param FristName frist name of the user
	 * @param LastName  last name of the user
	 * @param pin		pin number of the user
	 * @param bank 		the user is the customer of the Bank
	 */
	User(String FristName, String LastName, String pin, Bank bank) {
		
		// set user's name
		this.FristName = FristName;
		this.LastName = LastName;
		
		// store the pin's MD5 hash, Instead of storing original value for
		// the security reasons;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.Pin = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error found, NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		
		// get a new user id for the user
		this.Id = bank.getNewUserId();
		
		// create empty list of user account
		this.Accounts = new ArrayList<Account>();
		
		// print log message
		System.out.printf("New user %s, %s with ID %s created.\n", FristName, LastName, this.Id);
	}
	
	/**
	 * returns user Id
	 * @return user Id
	 */
	public String getUserId() {
		return this.Id;
	}
	
	/**
	 * Add an account
	 * @param account	the account to add
	 */
	public void addAccount(Account account) {
		this.Accounts.add(account);
	}
	
	/**
	 * Check whether a given pin matches the true user pin
	 * @param Pin	the pin to check
	 * @return		whether the pin is valid or not
	 */
	public boolean validatePin(String aPin) {
		
		//System.out.println(aPin);
		//System.out.println(this.Pin);
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), 
					this.Pin);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Error found, NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		
		return false;
	}
	
	/**
	 * returns user's first name
	 * @return	the user's first name
	 */
	public String getFirstName() {
		
		return this.FristName;
	}
	/**
	 * prints summaries of the accounts for this user
	 */
	public void accountSummary() {
		
		System.out.printf("\n\n%s's accounts summary\n", this.FristName);
		for(int a = 0; a < this.Accounts.size(); a++) {
			System.out.printf(" %d. %s\n", a+1, 
					this.Accounts.get(a).getSummaryLine());
		}
		System.out.println();
	}
	
	/**
	 * Get number of accounts the user have
	 * @return	the number of accounts
	 */
	public int numAccounts() {
		return this.Accounts.size();
	}
	
	/**
	 * Print transaction history for a particular account
	 * @param acctIndx	the index of the account to use
	 */
	public void AcctTransHistory(int acctIndx) {
		this.Accounts.get(acctIndx).printTransHistory(); 
	}
	
	/**
	 * Get the balance of a particular account
	 * @param acctIndx	the index of the account to use
	 * @return			the balance of the account
	 */
	public double getAcctBalance(int acctIndx) {
		return this.Accounts.get(acctIndx).getBalance();
	}
	
	/**
	 * Get the Id of a particular account
	 * @param acctIndx	the index of the account to use
	 * @return			the Id of the account
	 */
	public String getAcctId(int acctIndx) {
		return this.Accounts.get(acctIndx).getAccountId();
	}
	
	/**
	 * Add a transaction to a particular account
	 * @param acctIdx		the index of the account
	 * @param amount		the amount of the transaction
	 * @param description	the description of the transaction
	 */
	public void addAcctTransaction(int acctIdx, double amount, String description) {
		this.Accounts.get(acctIdx).addTransaction(amount, description);
	}
}
