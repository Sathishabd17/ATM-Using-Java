import java.util.Date;

public class Transaction {

	// amount of this transaction
	private double Amount;
	
	// date and time of this transaction
	private Date Time;
	
	// discription of this transaction
	private String Description;
	
	// account in which transaction was performed
	private Account account;
	
	/**
	 * Create a new transaction
	 * @param amount	the amount transacted
	 * @param account	the account the transaction belongs to
	 */
	Transaction(double amount, Account account) {
		this.Amount = amount;
		this.Time = new Date();
		this.account = account;
		this.Description = "";
	}
	
	/**
	 * Create a new transaction
	 * @param amount		the amount transacted
	 * @param account		the account the transaction belongs to
	 * @param discreption	the description for the transaction
	 */
	Transaction(double amount, Account account, String description) {
		// call the two-arg constructor first
		this(amount, account);
		
		// set the description
		this.Description = description;
	}
	
	/**
	 * Get the amount of the transaction
	 * @return	the amount
	 */
	public double getAmount() {
		return this.Amount;
	}
	
	/**
	 * Get a string summarizing the transaction
	 * @return	the summary string
	 */
	public String getSummaryLine() {
		
		if(this.Amount >= 0) {
			return String.format("%s : RS.%.2f : %s", this.Time.toString(),
					this.Amount, this.Description);
		} else {
			return String.format("%s : RS.%.2f : %s", this.Time.toString(),
					this.Amount, this.Description);
		}
	}
}
