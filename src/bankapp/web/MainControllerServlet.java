package bankapp.web;

import java.util.*;
import java.io.*;
import java.math.BigDecimal;

import javax.servlet.*;
import javax.servlet.http.*;

import bankapp.bean.account.AccountActivityDetails;
import bankapp.bean.account.AccountDetails;
import bankapp.bean.account.BillAccountDetails;
import bankapp.bean.account.CustomerDetails;
import bankapp.bean.account.TransferAccountDetails;
import bankapp.bean.authentication.*;
import bankapp.bean.errorhandling.ErrorHandling;
import bankapp.database.BankDB;
import bankapp.exception.AccountNotFoundException;
import bankapp.exception.AccountsNotFoundException;
import bankapp.exception.BillPaymentException;
import bankapp.exception.DateException;
import bankapp.exception.PayeeNotFoundException;
import bankapp.exception.TransferAccountNotFoundException;
import bankapp.exception.UserNotFoundException;
import bankapp.utils.Now;

public class MainControllerServlet extends HttpServlet {

    private BankDB bankDB;
    long starttime;

    public void init() throws ServletException {

//		bankDB = (BankDB)getServletContext().getAttribute("bankDB");
//		
//		if (bankDB == null)
//			throw new UnavailableException("Couldn't get database.");
    }

//	public void destroy() {     
//		getServletContext().removeAttribute("bankDB");
//		bankDB = null;
//	}
    /**
     * ************************************************************************
     *
     * DO GET
     *
     ************************************************************************
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        starttime = System.currentTimeMillis();
        try {
            bankDB = new BankDB();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // Create fresh new session
        HttpSession session = request.getSession();

        // Authentication Object
        AuthenticationDetails logged = (AuthenticationDetails) session.getAttribute("logged");

        // Current Action
        String module = request.getParameter("Module");

        // Redirect Dispatcher
        String redirect = "/login.jsp";

        // Check if the submit has been pressed
        if (module != null && logged != null) {
            //
            // MODULE: Login
            //__________________________________________________________________
            //
            if (module.equals("LOGIN")) {
                dispatcher(request, response, "/login.jsp");
            } //
            // MODULE: Logout
            //__________________________________________________________________
            //
            else if (module.equals("LOGOUT")) {

                // Invalidate Session
                if (session != null) {
                    session.invalidate();
                    session = null;
                }

                // Go Bank to Login Page
                dispatcher(request, response, "/index.jsp");
            } //
            // MODULE: Account
            //__________________________________________________________________
            //
            else if (module.equals("ACCOUNT")) {
                // Retrieve the parameter from GET
                String method = request.getParameter("Id");

                // METHOD: View Account Details
                //==========================
                if (method != null) {
                    Collection account_list = null;
                    Collection detail_list = null;
                    AccountDetails current_account = null;

                    try {
                        account_list = bankDB.getAccounts(Integer.parseInt(logged.getUserID()));
                        detail_list = bankDB.getAccount(Integer.parseInt(method));
                        current_account = bankDB.getCurrentAccount(Integer.parseInt(method), Integer.parseInt(logged.getUserID()));
                        bankDB.remove();
                        // Set the attributes
                        request.setAttribute("current_account", current_account);
                        request.setAttribute("account_list", account_list);
                        request.setAttribute("detail_list", detail_list);
                    } catch (AccountsNotFoundException e) {
                        redirect = "/login.jsp";
                    } catch (NumberFormatException e) {
                        redirect = "/login.jsp";
                    } catch (AccountNotFoundException e) {
                        redirect = "/login.jsp";
                    }
                    redirect = "/account_activity.jsp";

                    // METHOD: View Account Summary
                    //==========================
                } else {
                    Collection account_list = null;
                    try {
                        account_list = bankDB.getAccounts(Integer.parseInt(logged.getUserID()));
                        bankDB.remove();
                        request.setAttribute("account_list", account_list);
                    } catch (AccountsNotFoundException e) {
                        redirect = "/login.jsp";
                    }
                    redirect = "/account.jsp";
                }
                dispatcher(request, response, redirect);
            } //
            // MODULE: Payment
            //__________________________________________________________________
            //
            else if (module.equals("PAYMENT")) {
                // Retrieve the parameter from GET
                String method = request.getParameter("Method");

                if (method != null) {
                    // METHOD: Add Payee
                    //==========================
                    if (method.equals("ADD")) {
                        Collection payee_list = null;
                        try {
                            payee_list = bankDB.getAllPayee(Integer.parseInt(logged.getUserID()));
                            bankDB.remove();
                        } catch (PayeeNotFoundException e) {
                            redirect = "/payment.jsp";;
                        }
                        request.setAttribute("payee_list", payee_list);
                        redirect = "/payment_add.jsp";

                        // METHOD: View Payee
                        //==========================
                    } else if (method.equals("VIEW")) {
                        String ERROR_MSG = null;
                        String GOOD_MSG = null;
                        Collection payee_list = null;
                        try {
                            payee_list = bankDB.viewCustomerPayee(Integer.parseInt(logged.getUserID()));
                            bankDB.remove();
                        } catch (PayeeNotFoundException e) {
                            ERROR_MSG = "Payee Not Found!";
                        }
                        request.setAttribute("ERROR_MSG", ERROR_MSG);
                        request.setAttribute("GOOD_MSG", GOOD_MSG);
                        request.setAttribute("payee_list", payee_list);
                        redirect = "/payment_view.jsp";

                        // METHOD: Delete
                        //==========================
                    } else if (method.equals("DELETE")) {
                        String payee = request.getParameter("Payee");
                        Collection payee_list = null;
                        String ERROR_MSG = null;
                        String GOOD_MSG = null;

                        try {
                            if (payee != null) {
                                if (bankDB.delCustomerPayee(Integer.parseInt(payee), Integer.parseInt(logged.getUserID()))) {
                                    GOOD_MSG = "Payee Deleted!";
                                } else {
                                    ERROR_MSG = "Execution Failed!";
                                }
                            }
                            payee_list = bankDB.viewCustomerPayee(Integer.parseInt(logged.getUserID()));
                        } catch (NumberFormatException e) {
                            ERROR_MSG = "Number Input incorrect!";
                        } catch (PayeeNotFoundException e) {
                            ERROR_MSG = "Payee Not Found!";
                        }
                        bankDB.remove();
                        request.setAttribute("ERROR_MSG", ERROR_MSG);
                        request.setAttribute("GOOD_MSG", GOOD_MSG);
                        request.setAttribute("payee_list", payee_list);
                        redirect = "/payment_view.jsp";

                        // METHOD: Pay Payee
                        //==========================
                    } else if (method.equals("PAY")) {
                        Collection account_list = null;
                        Collection payee_list = null;
                        try {
                            payee_list = bankDB.viewCustomerPayee(Integer.parseInt(logged.getUserID()));
                            account_list = bankDB.getAccounts(Integer.parseInt(logged.getUserID()));
                            bankDB.remove();
                        } catch (NumberFormatException e) {
                            redirect = "/login.jsp";
                        } catch (AccountsNotFoundException e) {
                            redirect = "/login.jsp";
                        } catch (PayeeNotFoundException e) {
                            redirect = "/login.jsp";
                        }
                        request.setAttribute("payee_list", payee_list);
                        request.setAttribute("account_list", account_list);
                        request.setAttribute("amount", "");
                        // Current date
                        Now currentdate = new Now();
                        String year = currentdate.getCurrentDate("yyyy");
                        String month = currentdate.getCurrentDate("MM");
                        String day = currentdate.getCurrentDate("dd");

                        request.setAttribute("year", year);
                        request.setAttribute("month", month);
                        request.setAttribute("day", day);

                        redirect = "/payment_pay.jsp";

                        // METHOD: None
                        //==========================
                    } else {
                        redirect = "/payment.jsp";
                    }

                    // Run the dispatcher
                    dispatcher(request, response, redirect);
                } else {
                    dispatcher(request, response, "/payment.jsp");
                }
            } //
            // MODULE: Transfer
            //__________________________________________________________________
            //
            else if (module.equals("TRANSFER")) {
                Collection account_list = null;
                int id = Integer.parseInt(logged.getUserID());

                try {
                    account_list = bankDB.getAccounts(id);
                } catch (AccountsNotFoundException e) {

                }

                String GOOD_MSG = null;
                String ERROR_MSG = null;

                request.setAttribute("GOOD_MSG", GOOD_MSG);
                request.setAttribute("ERROR_MSG", ERROR_MSG);
                request.setAttribute("account_list", account_list);
                dispatcher(request, response, "/transfer.jsp");

            } //
            // MODULE: Profile
            //__________________________________________________________________
            //
            else if (module.equals("PROFILE")) {

                try {
                    CustomerDetails cd = bankDB.retrieveCustomerInformation(logged.getUserID());
                    bankDB.remove();
                    request.setAttribute("customer_details", cd);
                    request.setAttribute("ERROR_MSG", null);
                    redirect = "/profile.jsp";
                } catch (UserNotFoundException e) {
                    String ERROR_MSG = "An I/O error occured while sending to the backend";
                    request.setAttribute("ERROR_MSG", ERROR_MSG);
                    redirect = "/login.jsp";
                }

                // Forward to this page
                dispatcher(request, response, redirect);
            }
        } else {
            dispatcher(request, response, "/login.jsp");
        }
    }

    /**
     * ************************************************************************
     *
     * DO POST
     *
     ************************************************************************
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        starttime = System.currentTimeMillis();
        try {
            bankDB = new BankDB();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // Create fresh new session
        HttpSession session = request.getSession();

        // Authentication Object
        AuthenticationDetails logged = (AuthenticationDetails) session.getAttribute("logged");

        // Current Action
        String action = request.getParameter("action");

        // Redirect Dispatcher
        String redirect = "/login.jsp";

        // Check if the submit has been pressed
        if (action != null) {

            //
            // ACTION: Login
            //__________________________________________________________________
            //
            if (action.equals("LOGIN")) {
                // Initialize parameters
                String userID = request.getParameter("username");
                String password = request.getParameter("password");
                int type = Integer.parseInt(request.getParameter("type").toString());
                String ERROR_MSG = "";
                redirect = "/login.jsp";

                try {
                    // Check if user exists
                    Authentication authenticate = new Authentication(bankDB);
                    AuthenticationDetails ad = null;
                    ad = authenticate.checkIfUserExist(userID, password, type);
                    bankDB.remove();
                    if (ad == null) { // Invalid Information
                        ERROR_MSG = "Access Denied!";
                        request.setAttribute("ERROR_MSG", ERROR_MSG);
                    } else { // User Logged
                        session.setAttribute("logged", ad);
                        session.setMaxInactiveInterval(3600);
                        redirect = "/myaccount.jsp";
                    }
                } catch (UserNotFoundException e) {
                    ERROR_MSG = "Improper Account Format!";
                    request.setAttribute("ERROR_MSG", ERROR_MSG);
                } catch (Exception e) {
                    ERROR_MSG = e.getMessage();
                    request.setAttribute("ERROR_MSG", ERROR_MSG);
                }

                // Redirect to page
                dispatcher(request, response, redirect);
                //
                // ACTION: Add a Payee
                //__________________________________________________________________
                //
            } else if (action.equals("ADD_PAYEE")) {

                int payeeID = Integer.parseInt(request.getParameter("payee_list"));
                String ERROR_MSG = null;
                redirect = "/payment.jsp";

                try {
                    if (bankDB.addCustomerPayee(payeeID, Integer.parseInt(logged.getUserID()))) {
                        Collection payee_list = null;
                        payee_list = bankDB.viewCustomerPayee(Integer.parseInt(logged.getUserID()));
                        bankDB.remove();
                        request.setAttribute("payee_list", payee_list);
                        redirect = "/payment_view.jsp";
                    } else {
                        ERROR_MSG = "Error Adding Payee, Please contact System Administrator!";
                    }
                } catch (NumberFormatException e) {
                    ERROR_MSG = "Number Format Error!";
                } catch (UserNotFoundException e) {
                    ERROR_MSG = "User Not Found!";
                } catch (PayeeNotFoundException e) {
                    ERROR_MSG = "Payee Not Found!";
                } catch (Exception e) {
                    ERROR_MSG = e.getMessage();
                    request.setAttribute("ERROR_MSG", ERROR_MSG);
                }

                request.setAttribute("ERROR_MSG", ERROR_MSG);
                dispatcher(request, response, redirect);

                //
                // ACTION: Transfer Money
                //__________________________________________________________________
                //			
            } else if (action.equals("TRANSFER_MONEY")) {

                // Form Objects
                String[][] formvars = new String[2][2];
                formvars[0][0] = "to";
                formvars[0][1] = request.getParameter("to");
                formvars[1][0] = "amount";
                formvars[1][1] = request.getParameter("amount");

                // Initializions of Variables
                int id = Integer.parseInt(logged.getUserID());
                int to;
                int from;
                BigDecimal amount = new BigDecimal(0);
                String ERROR_MSG = "";
                String GOOD_MSG = "";

                // Newly created Objects
                ErrorHandling errorHandler = new ErrorHandling();	// ERROR

                // Munge the Form variables
                formvars = errorHandler.mungeForm(formvars);

                // Set up the Objects For the form Select Lists
                Collection account_list = null;

                // Redirection
                redirect = "/transfer.jsp";

                // Check if the form is valid
                if (errorHandler.isValidForm(formvars)) {

                    try {

                        // Get the form parameters
                        to = Integer.parseInt(formvars[0][1]);
                        from = Integer.parseInt(request.getParameter("from"));

                        // Use a Big decimal to accurately define the amount 
                        amount = new BigDecimal(formvars[1][1]);

                        if (to != from) {

                            // Check if value is 0
                            if (amount.intValue() == 0) {
                                ERROR_MSG = "Please Enter a valid amount greater than 0!";
                            } else {
                                int cid = Integer.parseInt(logged.getUserID());
                                try {
                                    TransferAccountDetails tad = new TransferAccountDetails(to, from, amount, cid);
                                    bankDB.transferAmount(tad);
                                    GOOD_MSG = "Transfer was Successfull!";
                                } catch (TransferAccountNotFoundException e) {
                                    ERROR_MSG = "Transfer Error!";
                                } catch (Exception ex) {
                                    ERROR_MSG = ex.getMessage();
                                }

                            }
                        } else {
                            ERROR_MSG = "Can not transfer to same account!";
                        }
                    } catch (NumberFormatException e) {
                        ERROR_MSG = "Number Format incorrect!";

                    }
                    // Set up the lists
                    try {
                        account_list = bankDB.getAccounts(id);
                    } catch (AccountsNotFoundException e) {
                        ERROR_MSG = "Account not Found!";
                    }
                } else { // Form is not valid
                    id = Integer.parseInt(logged.getUserID());
                    int tempamount = 0;

                    amount = new BigDecimal(tempamount);
                    account_list = null;

                    try {
                        account_list = bankDB.getAccounts(id);
                        ERROR_MSG = errorHandler.getErrorMsg();
                    } catch (NumberFormatException e) {
                        ERROR_MSG = "Number Format Error";
                    } catch (AccountsNotFoundException e) {
                        ERROR_MSG = "Account not found";
                    } catch (Exception e) {
                        ERROR_MSG = e.getMessage();
                        request.setAttribute("ERROR_MSG", ERROR_MSG);
                    }
                }

                // Set the form attributes
                request.setAttribute("GOOD_MSG", GOOD_MSG);
                request.setAttribute("ERROR_MSG", ERROR_MSG);
                request.setAttribute("amount", amount.toString());
                request.setAttribute("account_list", account_list);

                bankDB.remove();
                // Redirect to page
                dispatcher(request, response, redirect);

                //
                // ACTION: Pay Bill
                //__________________________________________________________________
                //
            } else if (action.equals("PAY_BILL")) {

                // Form Objects
                String[][] formvars = new String[1][2];
                formvars[0][0] = "amount";
                formvars[0][1] = request.getParameter("amount");

                // Initializions of Variables
                int id = Integer.parseInt(logged.getUserID());
                int to;
                int from;
                String month = "";
                String year = "";
                String day = "";
                BigDecimal amount = new BigDecimal(0);
                String ERROR_MSG = "";
                String GOOD_MSG = "";

                // Newly created Objects
                ErrorHandling errorHandler = new ErrorHandling();	// ERROR
                Now datecustom = new Now();	// DATE

                // Munge the Form variables
                formvars = errorHandler.mungeForm(formvars);

                // Set up the Objects For the form Select Lists
                Collection account_list = null;
                Collection payee_list = null;

                // Redirection
                redirect = "/payment_pay.jsp";

                // Check if the form is valid
                if (errorHandler.isValidForm(formvars)) {

                    // Get the form parameters
                    to = Integer.parseInt(request.getParameter("to"));
                    from = Integer.parseInt(request.getParameter("from"));

                    // Form Date
                    month = request.getParameter("tmonth");
                    year = request.getParameter("tyear");
                    day = request.getParameter("tday");

                    // Current Date Upon Entering Form
                    String cmonth = request.getParameter("cmonth");
                    String cyear = request.getParameter("cyear");
                    String cday = request.getParameter("cday");

                    // Form Date
                    String tdate = year + "-" + month + "-" + day;

                    // Current Date;
                    String cdate = cyear + "-" + cmonth + "-" + cday;

                    try {
                        // Use a Big decimal to accurately define the amount 
                        amount = new BigDecimal(formvars[0][1]);

                        // Check if the date is a valid date!
                        if (datecustom.checkDate(tdate, cdate)) {
                            if (amount.intValue() == 0) {
                                ERROR_MSG = "Please Enter a valid amount greater than 0!";
                            } else {
                                int cid = Integer.parseInt(logged.getUserID());
                                try {
                                    BillAccountDetails bd = new BillAccountDetails(to, from, year, month, day, amount, cid);
                                    bankDB.billPayment(bd);
                                    GOOD_MSG = "Bill Payment was Successfull!";
                                } catch (BillPaymentException e) {
                                    ERROR_MSG = "Insuffient Funds!";

                                } catch (DateException e) {
                                    ERROR_MSG = "Date improper format";
                                } catch (Exception e) {
                                    ERROR_MSG = e.getMessage();
                                    request.setAttribute("ERROR_MSG", ERROR_MSG);
                                }

                            }
                        } else {
                            ERROR_MSG = "The date entered already has passed!";
                        }
                    } catch (NumberFormatException e) {
                        ERROR_MSG = "Number Format incorrect!";

                    }

                    // Set up the lists
                    try {
                        payee_list = bankDB.viewCustomerPayee(id);
                        account_list = bankDB.getAccounts(id);
                    } catch (PayeeNotFoundException e) {
                        ERROR_MSG = "Payee not Found!";
                    } catch (AccountsNotFoundException e) {
                        ERROR_MSG = "Account not Found!";
                    } catch (Exception e) {
                        ERROR_MSG = e.getMessage();
                        request.setAttribute("ERROR_MSG", ERROR_MSG);
                    }
                } else { // Form is not valid
                    id = Integer.parseInt(logged.getUserID());
                    int tempamount = 0;

                    amount = new BigDecimal(tempamount);
                    account_list = null;
                    payee_list = null;

                    try {
                        payee_list = bankDB.viewCustomerPayee(id);
                        account_list = bankDB.getAccounts(id);
                        ERROR_MSG = errorHandler.getErrorMsg();
                    } catch (NumberFormatException e) {
                        ERROR_MSG = "Number Format Error";
                    } catch (AccountsNotFoundException e) {
                        ERROR_MSG = "Account not found";
                    } catch (PayeeNotFoundException e) {
                        ERROR_MSG = "Payee Not found";
                    } catch (Exception e) {
                        ERROR_MSG = e.getMessage();
                        request.setAttribute("ERROR_MSG", ERROR_MSG);
                    }

                    // Set form Attributes
                    year = datecustom.getCurrentDate("yyyy");
                    month = datecustom.getCurrentDate("MM");
                    day = datecustom.getCurrentDate("dd");

                }

                // Set the form attributes
                request.setAttribute("GOOD_MSG", GOOD_MSG);
                request.setAttribute("ERROR_MSG", ERROR_MSG);
                request.setAttribute("amount", amount.toString());
                request.setAttribute("payee_list", payee_list);
                request.setAttribute("account_list", account_list);
                request.setAttribute("year", year);
                request.setAttribute("month", month);
                request.setAttribute("day", day);

                bankDB.remove();
                // Redirect to page
                dispatcher(request, response, redirect);

                //
                // ACTION: Alter Profile
                //__________________________________________________________________
                //
            } else if (action.equals("ALTER_PROFILE")) {

                // Form Objects
                String[][] formvars = new String[7][2];
                formvars[0][0] = "address";
                formvars[0][1] = request.getParameter("address");
                formvars[1][0] = "email";
                formvars[1][1] = request.getParameter("email");
                formvars[2][0] = "city";
                formvars[2][1] = request.getParameter("city");
                formvars[3][0] = "phone";
                formvars[3][1] = request.getParameter("phone");
                formvars[4][0] = "best_time";
                formvars[4][1] = request.getParameter("best_time");
                formvars[5][0] = "pass1";
                formvars[5][1] = request.getParameter("pass1");
                formvars[6][0] = "branch";
                formvars[6][1] = request.getParameter("branch");

                // Initialization
                int id = Integer.parseInt(logged.getUserID());
                String fname = logged.getFirstName();
                String lname = logged.getLastName();
                String address = "";
                String city = "";
                String phone = "";
                String email = "";
                String branch = "";
                String best_time = "";
                String pass = "";
                String pass2 = "";
                String ERROR_MSG = "";
                String GOOD_MSG = "";

                // Call the error handling class to check the forms
                ErrorHandling errorHandler = new ErrorHandling();
                CustomerDetails cd;

                // Munge the Form variables
                formvars = errorHandler.mungeForm(formvars);

                // Check if the form is valid
                if (errorHandler.isValidForm(formvars)) {

                    // Initialize Form parameters
                    address = formvars[0][1];
                    city = formvars[2][1];
                    phone = formvars[3][1];
                    email = formvars[1][1];
                    best_time = formvars[4][1];
                    pass = formvars[5][1];
                    pass2 = request.getParameter("pass2");
                    branch = formvars[6][1];

                    String oringalPass = logged.getPassword();

                    // Set the customer object
                    cd = new CustomerDetails(fname, lname, address, city, phone, email, branch, best_time, pass);
                    cd.setCustomerId(id);

                    // Check if password is the same
                    if (!pass.equals(pass2) && pass2.length() != 0) {
                        ERROR_MSG = "Passwords do not Match!";
                    } else {

                        // Check if he altered his password!
                        if (pass2.length() == 0 && !pass.equals(oringalPass)) {
                            ERROR_MSG = "You have altered your Oringinal Password";
                        } else {
                            try {// Update the info
                                if (bankDB.updateCustomerPreference(cd)) {
                                    GOOD_MSG = "User Information Updated!";
                                } else {
                                    ERROR_MSG = "Error with Updating, Contact Webmaster";
                                }
                            } catch (UserNotFoundException e) {
                                ERROR_MSG = "Error Executing DB";
                            } catch (Exception e) {
                                ERROR_MSG = e.getMessage();
                                request.setAttribute("ERROR_MSG", ERROR_MSG);
                            }
                        }
                    }
                } else {
                    ERROR_MSG = errorHandler.getErrorMsg();
                    id = Integer.parseInt(logged.getUserID());
                    fname = logged.getFirstName();
                    lname = logged.getLastName();
                    address = request.getParameter("address");
                    city = request.getParameter("city");
                    phone = request.getParameter("phone");
                    email = request.getParameter("email");
                    best_time = request.getParameter("best_time");
                    pass = request.getParameter("pass");
                    branch = request.getParameter("branch");
                    cd = new CustomerDetails(fname, lname, address, city, phone, email, branch, best_time, pass);
                    cd.setCustomerId(id);
                }
                // Set form attrib
                request.setAttribute("ERROR_MSG", ERROR_MSG);
                request.setAttribute("GOOD_MSG", GOOD_MSG);
                request.setAttribute("customer_details", cd);
                redirect = "/profile.jsp";

                bankDB.remove();

                // Redirect to page
                dispatcher(request, response, redirect);

            }
        } else {
            //Invalid Action
        }
    }

    /**
     * Dispatcher
     *
     * @param uri
     * @throws IOException
     * @throws ServletException
     */
    private void dispatcher(HttpServletRequest request, HttpServletResponse response, String uri) throws ServletException, IOException {
        request.setAttribute("QUERIES", bankDB.getNumQueries());
        request.setAttribute("START", starttime);

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(uri);
        rd.forward(request, response);
    }
}
