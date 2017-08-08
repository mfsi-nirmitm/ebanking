
package bankapp.exception;

public class TransferAccountNotFoundException extends Exception {

    public TransferAccountNotFoundException () { }

    public TransferAccountNotFoundException (String msg) {
        super(msg);
    } 
}

