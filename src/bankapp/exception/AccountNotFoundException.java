
package bankapp.exception;


public class AccountNotFoundException extends Exception {

    public AccountNotFoundException () { }

    public AccountNotFoundException (String msg) {
        super(msg);
    } 
}

