package bankapp.bean.account;


public class AccountDetails {
    private int accountID = 0;
    private float amount = 0;
    private int type = 0;
     
    /**
     * Account Details
     * 
     * @param accountID
     * @param amount
     * @param type
     */
    public AccountDetails(int accountID, float amount, int type ) {
        this.accountID = accountID;
        this.amount = amount;
        this.type = type;
    }

    /**
     * Retrieve the account ID
     * 
     * @return int
     */
    public int getAccountID() {
        return accountID;
    }
    
    /**
     * Retrieve the account type
     * 
     * @return int
     */
    public int getAccountType() {
        return type;
    }
    
    /**
     * Retrieve the Float
     * 
     * @return float
     */
    public float getAmount() {
       return amount;
    }
    
}

