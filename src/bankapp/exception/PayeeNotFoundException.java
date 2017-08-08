
package bankapp.exception;

public class PayeeNotFoundException extends Exception {

    public PayeeNotFoundException () { }

    public PayeeNotFoundException (String msg) {
        super(msg);
    } 
}

