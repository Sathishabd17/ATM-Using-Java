import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {
		
		// init Scanner
		Scanner sc = new Scanner(System.in);
		
		// init Bank
		Bank bank = new Bank("XYZ Bank");
		
		// add a user, which also creates a savings account
		User user = bank.addUser("Ajith", "Kumar", "12345");
		
		// add a checking account for our user
		Account account = new Account("Current", user, bank);
		user.addAccount(account);
		bank.addAccount(account);
		
		User curUser;
		while(true) {
			
			// stay in login menu until successful login
			curUser = ATM.mainMenu(bank, sc);
			
			// stay in main menu until user quits
			ATM.userMenu(curUser, sc);
			
		}
	}
	
	/**
	 * Print the ATM login menu
	 * @param bank	the Bank object whose account to use
	 * @param sc	the Scanner object to use for user input
	 * @return		the authenticated User object
	 */
	public static User mainMenu(Bank bank, Scanner sc) {
		
		// init
		String userId;
		String pin;
		User authUser;
		
		// prompt the user for userId and pin until a correct one
		do {
			
			System.out.printf("\n\nWelcome to %s\n\n", bank.getName());
			System.out.printf("Enter user ID: ");
			userId = sc.nextLine();
			System.out.printf("Enter pin: ");
			pin = sc.nextLine();
			
			// try to get user object corresponding to the Id and pin
			authUser = bank.userLogin(userId, pin);
			if(authUser == null) {
				System.out.println("\nIncorrect user ID or pin." + 
							"Please try again.");
			}
			
		} while(authUser == null); // continue looping until successful login
		
			
		return authUser;
	}
	
	public static void userMenu(User curUser, Scanner sc) {
		
		// print summary of the user's accounts
		curUser.accountSummary();
		
		// init
		int choice;
		
		// user menu
		do {
			System.out.printf("Welcome %s, what would you like to do?\n", 
					curUser.getFirstName());
			System.out.println(" 1. Show account transaction history");
			System.out.println(" 2. Withdraw");
			System.out.println(" 3. Deposit");
			System.out.println(" 4. Transfer");
			System.out.println(" 5. Quit");
			System.out.println();
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			
			if(choice < 1 || choice > 5) {
				System.out.println("\nInvalid choice. Please choose (1-5)\n");
			}
		} while(choice < 1 || choice > 5);
		
		// process the choice
		switch(choice) {
			
			case 1:
				ATM.transactionHistory(curUser, sc);
				break;
			case 2:
				ATM.withdrawFunds(curUser, sc);
				break;
			case 3:
				ATM.depositFunds(curUser, sc);
				break;
			case 4:
				ATM.tranferFunds(curUser, sc);
				break;
			case 5:
				sc.nextLine();
				break;
		}
		
		// redisplay this menu until user wants to quit
		if(choice != 5) {
			ATM.userMenu(curUser, sc);
		}
	}
	
	/**
	 * Show the transaction history for an account
	 * @param user	the logged in User object
	 * @param sc	the Scanner object used for user input
	 */
	public static void transactionHistory(User user, Scanner sc) {
		
		int account;
		
		// get account whose transaction history to look at
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + 
					"whose transactios you want to see: ",
						user.numAccounts());
			
			account = sc.nextInt() - 1;
			if(account < 0 || account >= user.numAccounts()) {
				System.out.println("\nInvalid account. Please try again.\n");
			}
		} while(account < 0 || account >= user.numAccounts());
		
		// print transaction history
		user.AcctTransHistory(account);
	}
	
	/**
	 * Process transferring funds one account to another
	 * @param user	the logged in User object
	 * @param sc	the Scanner object used for user input
	 */
	public static void tranferFunds(User user, Scanner sc) {
		
		// init
		int fromAcct;
		int toAcct;
		double amount;
		double accBal;
		
		// get the account to transfer fund from
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + 
					"to transfer from: ", user.numAccounts());
			fromAcct = sc.nextInt() - 1;
			
			if(fromAcct < 0 || fromAcct >= user.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while(fromAcct < 0 || fromAcct >= user.numAccounts());
		accBal = user.getAcctBalance(fromAcct);
		
		// get the account to transfer fund to
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + 
					"to transfer to: ", user.numAccounts());
			toAcct = sc.nextInt() - 1;
			
			if(toAcct < 0 || toAcct >= user.numAccounts()) {
				System.out.println("\nInvalid account. Please try again.\n");
			}
		} while(toAcct < 0 || toAcct >= user.numAccounts());
		
		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to transfer (max RS.%.2f) : RS.",
					accBal);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("\nAmount must be greater than zero.");
			} else if(amount > accBal) {
				System.out.printf("\nAmount must not be greater than\n" + 
						"balance of RS.%.2f\n", accBal);
			}
		} while(amount < 0 || amount > accBal);
		
		// do the transfer
		user.addAcctTransaction(fromAcct, -1*amount, String.format(
				"Transfer to account %s", user.getAcctId(toAcct)));
		user.addAcctTransaction(toAcct, amount, String.format(
				"Transfer from account %s", user.getAcctId(fromAcct)));
	}

	/**
	 * Process a fund withdraw from an account
	 * @param user	the logged in User object
	 * @param sc	the Scanner object used for user input
	 */
	public static void withdrawFunds(User user, Scanner sc) {
		
		// init
		int fromAcct;
		double amount;
		double accBal;
		String description;
		
		// get the account to transfer fund from
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + 
					"to withdraw from: ", user.numAccounts());
			fromAcct = sc.nextInt() - 1;
			
			if(fromAcct < 0 || fromAcct >= user.numAccounts()) {
				System.out.println("\nInvalid account. Please try again.\n");
			}
		} while(fromAcct < 0 || fromAcct >= user.numAccounts());
		accBal = user.getAcctBalance(fromAcct);
		
		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to withdraw (max RS.%.2f) : RS.",
					accBal);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if(amount > accBal) {
				System.out.printf("Amount must not be greater than\n" + 
						"balance of RS.%.2f\n", accBal);
			}
		} while(amount < 0 || amount > accBal);
		sc.nextLine();
		
		// get a description
		System.out.print("Enter a description: ");
		description = sc.nextLine();
		
		// do the withdraw
		user.addAcctTransaction(fromAcct, -1*amount, description);
	}
	
	/**
	 * Process a fund deposit to an account
	 * @param user	the logged in User object
	 * @param sc	the Scanner object used for user input
	 */
	public static void depositFunds(User user, Scanner sc) {
		
		// init
		int toAcct;
		double amount;
		double accBal;
		String description;
		
		// get the account to transfer fund from
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + 
					"to deposit: ", user.numAccounts());
			toAcct = sc.nextInt() - 1;
			
			if(toAcct < 0 || toAcct >= user.numAccounts()) {
				System.out.println("\nInvalid account. Please try again.\n");
			}
		} while(toAcct < 0 || toAcct >= user.numAccounts());
		accBal = user.getAcctBalance(toAcct);
		
		// get the amount to transfer
		do {
			System.out.printf("Enter the amount to deposit: RS.",
					accBal);
			amount = sc.nextDouble();
			if(amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} 
		} while(amount < 0);
		sc.nextLine();
		
		// get a description
		System.out.print("Enter a description: ");
		description = sc.nextLine();
		
		// do the withdraw
		user.addAcctTransaction(toAcct, amount, description);
	}
	
}
