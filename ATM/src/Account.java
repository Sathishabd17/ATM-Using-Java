import java.util.ArrayList;

public class Account {
	
	// Name of the account
	private String Name;
	
	// Id number of the account
	private String Id;
	
	// user who holds this account
	private User user;
	
	// list of transaction of this account
	private ArrayList<Transaction> Transactions;
	
	/**
	 * Create new account
	 * @param Name	the name of the account
	 * @param user	the user who holds this account
	 * @param bank	which bank providing this account
	 */
	Account(String Name, User user, Bank bank) {
		
		// set user name and id
		this.Name = Name;
		this.user = user;
		
		// get a new account Id for the user
		this.Id = bank.getNewAccountId();
		
		// create empty of account transaction
		this.Transactions = new ArrayList<Transaction>();
	}
	
	/**
	 * returns account Id
	 * @return	account Id 
	 */
	public String getAccountId() {
		return this.Id;
	}
	
	/**
	 * Get summary line for the account
	 * @return	the string summary
	 */
	public String getSummaryLine() {
		
		// get the accounts balance
		double balance = this.getBalance();
		
		// format the summary line, depending on whether the balance is negative
		if(balance >= 0) {
			return String.format("%s : RS.%.2f : %s", this.Id, balance, this.Name);
		} else {
			return String.format("%s : RS.%.2f : %s", this.Id, balance, this.Name);
		}
	}
	
	/**
	 * Get the balance of this account
	 * @return	the balance amount
	 */
	public double getBalance() {
		
		double balance = 0;
		for(Transaction t : this.Transactions) {
			balance += t.getAmount();
		}
		return balance;
	}
	
	/**
	 * print the transaction history of the account
	 */
	public void printTransHistory() {
		
		System.out.printf("\nTransaction history for account %s\n", this.Id);
		if(this.Transactions.size() == 0)
		{
			System.out.println("No records found.");
			return;
		}
		for(int t = 0; t < this.Transactions.size(); t++) {
			System.out.println(this.Transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}
	
	/**
	 * Add new transaction in this account
	 * @param amount		the amount transacted
	 * @param description	the transaction description
	 */
	public void addTransaction(double amount, String description) {
		
		// create new transaction object and add it to our list
		Transaction transaction = new Transaction(amount, this, description);
		this.Transactions.add(transaction);
	}
}
