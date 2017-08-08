package bankapp.bean.account;


public class PayeeDetails {
    private int payeeID = 0;
    private String payeeName = null;
     


    /**
     * Payee Details
     * 
     * @param id
     * @param name
     */
    public PayeeDetails(int id, String name ) {
        this.payeeID = id;
        this.payeeName = name;
    }

    /**
     * Retrieve the Payee ID
     * 
     * @return int
     */
    public int getPayeeID() {
        return payeeID;
    }
    
    /**
     * Retrieve the Payee Name
     * 
     * @return String
     */
    public String getPayeeName() {
       return payeeName;
    }
    
}

