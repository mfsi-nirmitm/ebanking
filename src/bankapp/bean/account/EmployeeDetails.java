package bankapp.bean.account;


public class EmployeeDetails {
    private String employeeId = null;
    private String firstName = null;
    private String lastName = null;
    private String phone = null;
    private String start_date = null;
     
    /**
     * Constructor that sets the Employee Information
     * 
     * @param employeeId
     * @param firstName
     * @param lastName
     * @param phone
     * @param start_date
     */
    public EmployeeDetails(String employeeId, String firstName, String lastName, String phone, 
                       String start_date) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.start_date = start_date;
    }

    /**
     * Retrieve the Employee ID
     * 
     * @return String
     */
    public String getEmployeeID() {
        return employeeId;
    }
    
    /**
     * Retrieve the Employee's First Name
     * 
     * @return String
     */
    public String getFirstName() {
       return firstName;
    }

    /**
     * Retrieve the Employees Last Name
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retrieve the Employee  Phone Number
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Retrieve the Employees Start Date
     * @return
     */
    public String getStartDate() {
        return start_date;
    }
}

