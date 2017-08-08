package bankapp.bean.account;

public class AccountSummaryDetails {
    private int accountType = 0;
    private int accountID = 0;
    private float amount = 0;
     

    /**
     * Account summary
     * 
     * @param String type
     * @param int account
     * @param float balance
     */
    public AccountSummaryDetails(int type, int account, float balance ) {
        this.accountType = type;
        this.accountID = account;
        this.amount = balance;
    }

    /**
     * Retrieve the Account Name
     * 
     * @return int
     */
    public int getAccountType() {
        return accountType;
    }
    
    /**
     * Retrieve the Account ID
     * 
     * @return int
     */
    public int getAccountID() {
       return accountID;
    }
  
    /**
     * Retrieve the Account Balance
     * 
     * @return float
     */
    public float getAccountBalance() {
       return amount;
    }
}

