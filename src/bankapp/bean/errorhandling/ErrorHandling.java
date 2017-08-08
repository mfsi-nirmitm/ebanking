package bankapp.bean.errorhandling;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ErrorHandling
{

	private String ERROR_MSG = "";
	
	public ErrorHandling() {
		
	}
	
	/**
	 * Checks if the form is valid
	 * 
	 * @param formvars
	 * @return boolean
	 */
	public boolean isValidForm(String[][] formvars) {
		if(checkEmptyForm(formvars)) {
			return true;
		} 
		return false;
	}
	
	/**
	 * Munges the white space form the form data
	 * 
	 * @param formvars
	 * @return HashMap
	 */
	public String[][] mungeForm(String[][] formvars) {
	
		// Traverse the Hashmap
		for(int i=0;i<formvars.length;i++) {
			formvars[i][1] = formvars[i][1].trim();
		}
		return formvars;	
	}
	
	/**
	 * Checks if form is empty
	 * 
	 * @param formvars
	 * @return boolean
	 */
	private boolean checkEmptyForm(String[][] formvars){
		int ERR_CNT = 0;
	
		// Check if nothing is in the form
		if(formvars.length == 0) {
			return false;
		}
		
		// Check for Null or empty form
		for(int i=0;i<formvars.length;i++) {
			if(formvars[i][1] == null || formvars[i][1].length() == 0) {
				ERR_CNT++;
				ERROR_MSG += "<p>Empty: " + formvars[i][0] + "</p>";
			}
		}
		
		// Check if an Error exists
		if (ERR_CNT == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Gets the current Form Errors
	 * 
	 * @return String
	 */
	public String getErrorMsg() {
		return this.ERROR_MSG;
	}
	
}
