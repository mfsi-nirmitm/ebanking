
package bankapp.exception;

public class AccountsNotFoundException extends Exception {

    public AccountsNotFoundException () { }

    public AccountsNotFoundException (String msg) {
        super(msg);
    } 
}

