import java.util.ArrayList;
import java.util.Random;

public class Bank {
	
	// name of the bank
	private String Name;
	
	// list of users in this bank
	private ArrayList<User> users;
	
	// list of accounts in this bank
	private ArrayList<Account> accounts;
	
	/**
	 * Create a new Bank object with empty lists of users and accounts
	 * @param Name	the name of the bank
	 */
	Bank(String Name) {
		
		this.Name = Name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	/**
	 * Generate new Id for a user
	 * @return	the Id
	 */
	public String getNewUserId() {
		
		// Initialize
		Random rand = new Random();
		String Id;
		int size = 6;
		boolean nonUnique;
		
		do {
			
			// Generate the Id
			Id = "";
			for(int i = 0; i < size; i++)
			{
				Id += ((Integer)rand.nextInt(10)).toString();
			}
			
			// Check to make sure it's unique
			nonUnique = false;
			for(User u : this.users)
			{
				if(Id.compareTo(u.getUserId()) == 0)
				{
					nonUnique = true;
					break;
				}
			}
		} while(nonUnique);
			
		return Id;
	}
	
	/**
	 * Generate new Id for an account
	 * @return	 the Id
	 */
	public String getNewAccountId() {
		
		// Initialize
		Random rand = new Random();
		String Id;
		int size = 10;
		boolean nonUnique;
		
		do {
			
			// Generate the Id
			Id = "";
			for(int i = 0; i < size; i++)
			{
				Id += ((Integer)rand.nextInt(10)).toString();
			}
			
			// Check to make sure it's unique
			nonUnique = false;
			for(Account a : this.accounts)
			{
				if(Id.compareTo(a.getAccountId()) == 0)
				{
					nonUnique = true;
					break;
				}
			}
		} while(nonUnique);
			
		return Id;
	}
	
	/**
	 * Add an account
	 * @param account	the account to add
	 */
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	/**
	 * Create a new user of the bank
	 * @param FirstName	user's first name
	 * @param LastName	user's last name
	 * @param Pin		user's pin
	 * @return			new user
	 */
	public User addUser(String FirstName, String LastName, String Pin) {
		
		// create new user and add it to our account
		User newUser = new User(FirstName, LastName, Pin, this);
		this.users.add(newUser);
		
		// create a savings account for the user
		Account newAccount = new Account("Savings", newUser, this);
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		return newUser;
	}
	
	/**
	 * Get the User object associated with the userId and Pin, if they are valid
	 * @param userId	id of the user to login
	 * @param Pin		pin of the user
	 * @return			the User object, if the login successful, or null, if it is not
	 */
	public User userLogin(String userId, String Pin) {
		
		// search through list of users
		for(User u : this.users) {
			
			// check user Id is correct
			if(u.getUserId().compareTo(userId) == 0 && u.validatePin(Pin)) {
				return u; 
			}
		}
		
		// if we haven't found the user or have an incorrect pin
		return null;
	}
	
	/**
	 * returns the bank name
	 * @return the bank name
	 */
	public String getName() {
		
		return this.Name;
	}
}
