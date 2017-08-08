package bankapp.bean.account;


public class CustomerDetails {
    private int customerId = 0;
    private String password = null;
    private String firstName = null;
    private String lastName = null;
    private String address = null;
    private String city = null;
    private String phone = null;
    private String email = null;
    private String branch = null;
    private String best_time = null;
    

    /**
     * 
     * @param firstName
     * @param lastName
     * @param address
     * @param city
     * @param phone
     * @param email
     * @param best_time
     * @param pass
     */
    public CustomerDetails(String firstName, String lastName, String address, 
    		String city, String phone, String email, String branch,String best_time,String pass) {
    	this.password = pass;
        this.firstName = firstName;
        this.lastName =  lastName;
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.branch = branch;
        this.best_time = best_time;
    }

    
    /**
     * Retrieve the Customer ID
     * 
     * @return int
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Retrieve the Customer ID
     * 
     * @return int
     */
    public void setPassword(String pass) {
    	this.password = pass;
    }
    
    /**
     * Retrieve the Customer ID
     * 
     * @return int
     */
    public int getCustomerId() {
        return customerId;
    }
    
    /**
     * Retrieve the Customer ID
     * 
     * @return int
     */
    public void setCustomerId(int customerId) {
    	this.customerId = customerId;
    }
    
    /**
     * Retrieve Customers First Name
     * 
     * @return String
     */
    public String getFirstName() {
       return firstName;
    }

    /**
     * Setting the first name
     * 
     * @param fname
     */
    public void setFirstName(String fname) {
    	this.firstName = fname;
    }
    /**
     * Retrieve the Customers Last Name
     * 
     * @return String
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Setting the last name
     * 
     * @param lname
     */
    public void setLastName(String lname) {
    	this.lastName = lname;
    }
    
    /**
     * Retrieve the Customers Address
     * 
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setting the address
     * 
     * @param addr
     */
    public void setAddress(String addr) {
    	this.address = addr;
    }
    
    /**
     * Retrieve the Customers City
     * 
     * @return String
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Setting the current
     * 
     * @param citytemp
     */
    public void setCity(String citytemp) {
    	this.city = citytemp;
    }
    
    /**
     * Retrieve the Phone 
     * 
     * @return String
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Setting the phonenumber
     * 
     * @param tel
     */
    public void setPhone(String tel) {
    	this.phone = tel;
    }
     
    /**
     * Retrieve the Email 
     * 
     * @return String
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Setting the email
     *
     * @param email
     */
    public void setEmail(String email) {
    	this.email = email;
    }
    
    
    public String getBranch() {
        return this.branch;
    }

    /**
     * Setting the email
     *
     * @param email
     */
    public void setBranch(String branch) {
    	this.branch = branch;
    }
     
    
    public String getBestTime() {
        return this.best_time;
    } 
    
    /**
     * Setting up the best Time
     * 
     * @param best
     */
    public void setBestTime(String best) {
    	this.best_time = best;
    }
}

