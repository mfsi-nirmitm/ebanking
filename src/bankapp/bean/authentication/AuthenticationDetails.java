package bankapp.bean.authentication;

public class AuthenticationDetails {
    private String userId = null;
    private String firstName = null;
    private String lastName = null;
    private String passWord = null;
    private int type = 1;
     

	/**
	 * Authentication Object upon logging into the system
	 * 
	 * @param userId
	 * @param firstName
	 * @param lastName
	 * @param type
	 * @param password
	 */
    public AuthenticationDetails(String userId, String firstName, String lastName, int type,  String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passWord = password;
        this.type = type;
    }

    /**
     * Retrieve the User ID
     * 
     * @return String
     */
    public String getUserID() {
        return userId;
    }
    
    /**
     * Retrieve the First Name
     * 
     * @return String
     */
    public String getFirstName() {
       return firstName;
    }
    
    /**
     * Retrieve the Password
     * 
     * @return String
     */
    public String getPassword() {
       return passWord;
    }

    /**
     * Set the Password
     * 
     */
    public void setPassword(String password) {
    	this.passWord = password;
    }
    
    /**
     * Retrieve the Last Name
     * 
     * @return String
     */
    public String getLastName() {
       return lastName;
    }
    /**
     * Retrieve the Type
     * 
     * @return String
     */
    public int getType() {
        return type;
    }
    
    /**
     * Set the Type
     * 
     */
    public void setType(int new_type) {
        this.type = new_type;
    }
}

