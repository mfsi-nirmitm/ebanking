/**
* Checks the Login Form Validity
*
* @param form
* @return Boolean
*/
function checkLoginForm (thisform)
{

	with (thisform) {
	
		var ERROR = "";
		var ERR_CNT = 0;
		var uname = username.toLocalString();
		var upass = password.toLocalString();
		
		if (checkIfEmpty(trim(uname))) {
			ERR_CNT++;
			ERROR += "Please enter your username.\n";
			username.focus();
		}
		
		if (checkIfEmpty(trim(upass))) {
			ERR_CNT++;
			ERROR += "Please enter your password.\n";
			password.focus();
		}
		
		if (ERR_CNT != 0) {
			alert(ERROR);
			return false;
		} 
		
		return true ;
	} 
}

/**
* Trim function
*
* @param str
* @return String
*/
function trim (str)
{	
	
	return str.replace(/^\s+|\s+$/g, '')
};

/**
* Check if empty, returns true if its empty
*
* @param str
* @return boolean 
*/
function checkIfEmpty  (str) {
	if(str.value == "")
		return true;
	else 
		return false;
}


/**
* Validate telephone
*
* @param cur_field
* @param error_field
* @param reqd
*
* @return boolean
*/
function validateTelephone  (cur_field,error_field, reqd) {
  	// String clean up
	var trimmed_fld = trim(cur_field.value);
	var tel_regex = /^\+?[0-9 ()-]+[0-9]$/
	
	// Test the regular Expression
	if (!tel_regex.test(trimmed_fld))
		return false;

	// Initializations
	var numdigits = 0;
	var maxdigit = 14;
	var mindigit = 10;
	
	// Check each letter
	for (var j=0; j<trimmed_fld.length; j++)
    	if (trimmed_fld.charAt(j)>='0' && trimmed_fld.charAt(j)<='9') numdigits++;

	if (numdigits<6)
		return false;
		
	// Setting a range to check
	if (numdigits>maxdigit)
		return false
	
	return true;
};


/**
* Validate Email
*
* @param cur_field
* @param error_field
* @param reqd
*
* @return boolean
*/
function validateEmail  (cur_field,error_field,reqd)
{
  
  	// Clean up String
	var trimmed_field = trim(cur_field.value);  // value of field with whitespace trimmed off
	var email = /^[^@]+@[^@.]+\.[^@]*\w\w$/
	
	// Test the regular expression
	if (!email.test(trimmed_field)) {
		msg (error_field, "error", "ERROR: not a valid e-mail address");
		setfocus(cur_field);
		return false;
	}

	// Test the other check 
	var email2 = /^[A-Za-z][\w.-]+@\w[\w.-]+\.[\w.-]*[A-Za-z][A-Za-z]$/
	if (!email2.test(trimmed_field)) 
		msg (error_field, "warn", "Unusual e-mail address - check if correct");
	else
		msg (error_field, "warn", "");
	return true;
};
