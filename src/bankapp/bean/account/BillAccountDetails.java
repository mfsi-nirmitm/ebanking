package bankapp.bean.account;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class BillAccountDetails {
    private int to = 0;
    private int from = 0;
    private String tmonth = null;
    private String tday = null; 
    private String tyear = null;
    private BigDecimal amount;
    private int cid = 0;
    
    /**
     * Constructor to set up parameters for Bill
     * 
     * @param int to1 
     * @param int from1
     * @param String year
     * @param String month
     * @param String day
     * @param float amount1
     * @param int cid1
     */
    public BillAccountDetails(int to1, int from1, String year, String month, String day, BigDecimal amount1, int cid1) {
        this.to = to1;
        this.from = from1;
        this.tyear = year;
        this.tmonth = month;
        this.tday = day;
        this.amount = amount1;
        this.cid = cid1;
    }
    
    /**
     * Retrieve the To 
     * The account to bill
     * 
     * @return int
     */
    public int getTo() {
       return to;
    }

    /**
    * The account ID
    * 
    * @return int
    */
   public int getID() {
      return cid;
   }

    
    /**
     * Retrieve the from
     * The accoun to pay from
     * @return int
     */
    public int getFrom() {
       return from;
    }
    
    /**
     * Retrieve the Year
     * 
     * @return String
     */
    public String getYear() {
       return tyear;
    }

    /**
     * Retrieve the Month
     * 
     * @return String
     */
    public String getMonth() {
       return tmonth;
    }
    
    /**
     * Retrieve the Day
     * 
     * @return String
     */
    public String getDay() {
       return tday;
    }
  
    /**
     * Date 
     * @return Date
     * @throws ParseException
     */
    public Date getDate() throws ParseException {
    	Date newDate;
        
        String inputDate = getYear()+"-"+getMonth()+"-"+getDay();
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
        newDate = (Date) formatter.parse(inputDate);
        
		return newDate;
    }
    
    /**
     * Retrieve the Amount
     * 
     * @return BigDecimal
     */
    public BigDecimal getAmount() {
       return amount;
    }
}

