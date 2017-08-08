package bankapp.database;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import bankapp.bean.account.*;
import bankapp.bean.authentication.AuthenticationDetails;
import bankapp.exception.*;
import com.mysql.jdbc.Util;
import java.math.BigDecimal;

import java.util.*;

public class BankDB extends DBConnection {

    public BankDB() throws Exception {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Check if customer exists
     *
     * @param cid
     * @param password
     * @return AuthenticationDetails
     * @throws AccountNotFoundException
     */
    public AuthenticationDetails customerExist(String cid, String password) throws UserNotFoundException {

        try {
            // SELECT 
            String selectStatement = "SELECT cid,fname,lname,pass FROM customer WHERE cid = ? AND pass = ?";
            getConnection();

            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, cid);
            prepStmt.setString(2, password);

            ResultSet rs = prepStmt.executeQuery();

            if (rs.next()) {
                AuthenticationDetails ad = new AuthenticationDetails(rs.getString(1), rs.getString(2), rs.getString(3), 1, rs.getString(4));
                prepStmt.close();
                return ad;
            } else {
                prepStmt.close();
                return null;
            }
        } catch (SQLException ex) {
            throw new UserNotFoundException("Couldn't find User: " + cid + " " + ex.getMessage());
        }
    }

    /**
     * Check if customer exists
     *
     * @param cid
     * @param password
     * @return AuthenticationDetails
     * @throws AccountNotFoundException
     */
    public AuthenticationDetails employeeExist(String eid, String password) throws UserNotFoundException {

        try {
            // SELECT 
            String selectStatement = "SELECT eid,fname,lname,pass FROM employee WHERE eid = ? AND pass = ?";
            getConnection();

            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, eid);
            prepStmt.setString(2, password);

            ResultSet rs = prepStmt.executeQuery();

            if (rs.next()) {
                AuthenticationDetails ad = new AuthenticationDetails(rs.getString(1), rs.getString(2), rs.getString(3), 1, rs.getString(4));
                prepStmt.close();
                return ad;
            } else {
                prepStmt.close();
                return null;
            }
        } catch (SQLException ex) {
            throw new UserNotFoundException("Couldn't find User: " + eid + " " + ex.getMessage());
        }
    }

    /**
     * Fetch from the DB the customers information
     *
     * @param cid
     * @return CustomerDetails
     * @throws UserNotFoundException
     */
    public CustomerDetails retrieveCustomerInformation(String cid) throws UserNotFoundException {

        try {
            String selectStatement = "SELECT cid,fname,lname,address,city,phone,email,branch,best_time,pass FROM customer WHERE cid = ?";
            getConnection();
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, cid);

            ResultSet rs = prepStmt.executeQuery();

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
            if (rs.next()) {
                CustomerDetails cd = new CustomerDetails(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));

                prepStmt.close();
                return cd;
            } else {
                prepStmt.close();
                return null;
            }
        } catch (SQLException ex) {
            throw new UserNotFoundException("Couldn't find User: " + cid + " " + ex.getMessage());
        }

    }

    /**
     * Update the user preferences
     *
     * @param customer
     * @return boolean
     * @throws UserNotFoundException
     */
    public boolean updateCustomerPreference(CustomerDetails customer) throws UserNotFoundException {
        try {

            // Query for updating customer
            String selectStatement = "UPDATE customer"
                    + " SET email = ?, phone = ?, pass = ?, address = ? "
                    + "WHERE cid = ?";

            // Get the database connection from the Pool of connections
            getConnection();

            // Start the prepared statement
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, customer.getEmail());
            prepStmt.setString(2, customer.getPhone());
            prepStmt.setString(3, customer.getPassword());
            prepStmt.setString(4, customer.getAddress());
            prepStmt.setInt(5, customer.getCustomerId());

            // Execute the statement
            int rows = prepStmt.executeUpdate();

            // Return the connection back to the pool
            prepStmt.close();

            // Check if update was successfull
            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            throw new UserNotFoundException("Couldn't find User: " + customer.getCustomerId() + " " + ex.getMessage());
        }
    }

    /**
     * Get all the accounts in a collection
     *
     * @return Collection
     * @throws AccountsNotFoundException
     */
    public Collection getAccounts(int customerID) throws AccountsNotFoundException {
        ArrayList account = new ArrayList();
        try {
            String selectStatement = "SELECT H.acttype, A.account, A.amount "
                    + "FROM customer C "
                    + "NATURAL JOIN holds H "
                    + "NATURAL JOIN account A "
                    + "WHERE C.cid = ? ORDER BY H.acttype,A.account,A.amount";

            getConnection();
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, customerID);

            ResultSet rs = prepStmt.executeQuery();

            while (rs.next()) {
                AccountSummaryDetails ad = new AccountSummaryDetails(rs.getInt(1), rs.getInt(2), rs.getFloat(3));
                account.add(ad);
            }
            prepStmt.close();
        } catch (SQLException ex) {
            throw new AccountsNotFoundException(ex.getMessage());
        }
        return account;
    }

    /**
     * Retreive the
     *
     * @param TransferAccountDetails
     * @throws TransferAccountNotFoundException
     */
    public void transferAmount(TransferAccountDetails trans) throws TransferAccountNotFoundException, Exception {
        try {
			//-- PARAM (AMOUNT,ACCOUNTFROM,ACCOUNTTO,CUSTOMER)

            //String selectStatement = "SELECT transferMoney(?,?,?,?)";
            //PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            //prepStmt.setBigDecimal(1, trans.getAmount());
            //prepStmt.setInt(2, trans.getFrom());
            //prepStmt.setInt(3, trans.getTo());
            //prepStmt.setInt(4, trans.getID());
            //prepStmt.executeQuery();
            con.setAutoCommit(false);

            String selectStatement = "SELECT (amount-abs(?)) v_remaining,LPAD(?,12,'0') V_TO FROM account WHERE account=?";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setBigDecimal(1, trans.getAmount());
            prepStmt.setInt(2, trans.getTo());
            prepStmt.setInt(3, trans.getFrom());
            ResultSet rs = prepStmt.executeQuery();
            BigDecimal v_remaining = new BigDecimal(0);
            BigDecimal v_amount = new BigDecimal(0);
            v_amount = trans.getAmount();

            if (v_amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new Exception("Amount cannot have negative values");
            }

            String v_desc = "";
            if (rs.next()) {
                v_remaining = rs.getBigDecimal("v_remaining");
                v_desc = "Transfer to - " + rs.getString("V_TO");
            }

            if (v_remaining.compareTo(BigDecimal.ZERO) < 0) {
                throw new Exception("Insuffient funds, does not exist in your account");
            }
            
            float v_rate = getRate(trans.getFrom());
            
            if (v_remaining.compareTo(new BigDecimal(v_rate)) < 0) {
                throw new Exception("Insuffient funds, does not exist in your account");
            }

            selectStatement = "INSERT INTO activity(descr,account,cid,date,rem_bal,w_amount) VALUES('" + v_desc + "',?,?,sysdate(),?,?);";
            prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, trans.getFrom());
            prepStmt.setInt(2, trans.getID());
            prepStmt.setBigDecimal(3, v_remaining);
            prepStmt.setBigDecimal(4, trans.getAmount());

            int count = prepStmt.executeUpdate();

            selectStatement = "UPDATE account SET amount=amount-? WHERE account=?;";
            prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setBigDecimal(1, trans.getAmount());
            prepStmt.setInt(2, trans.getFrom());
            count = prepStmt.executeUpdate();

            selectStatement = "SELECT (amount+abs(?)) v_remaining,LPAD(?,12,'0') V_TO FROM account WHERE account=?";
            prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setBigDecimal(1, trans.getAmount());
            prepStmt.setInt(2, trans.getFrom());
            prepStmt.setInt(3, trans.getTo());
            rs = prepStmt.executeQuery();
            v_remaining = new BigDecimal(0);
            v_desc = "";
            if (rs.next()) {
                v_remaining = rs.getBigDecimal("v_remaining");
                v_desc = "Transfer From - " + rs.getString("V_TO");
            }

            if (v_remaining.compareTo(BigDecimal.ZERO) < 0) {
                throw new Exception("Insuffient funds, does not exist in your account");
            }

            selectStatement = "INSERT INTO activity(descr,account,cid,date,rem_bal,d_amount) VALUES('" + v_desc + "',?,?,sysdate(),?,?);";
            prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, trans.getTo());
            prepStmt.setInt(2, trans.getID());
            prepStmt.setBigDecimal(3, v_remaining);
            prepStmt.setBigDecimal(4, trans.getAmount());

            count = prepStmt.executeUpdate();

            selectStatement = "UPDATE account SET amount=amount+? WHERE account=?;";
            prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setBigDecimal(1, trans.getAmount());
            prepStmt.setInt(2, trans.getTo());
            count = prepStmt.executeUpdate();

            // Return the connection back to the pool
            prepStmt.close();

            con.commit();

        } catch (SQLException ex) {
            con.rollback();
            throw new TransferAccountNotFoundException("Insufficient Funds: " + trans.getAmount() + " is not enough.");
        }
    }

    /**
     * Retreive the
     *
     * @param bill
     * @throws BillPaymentException
     */
    public void billPayment(BillAccountDetails bill) throws BillPaymentException, DateException, Exception {
        try {
            //-- PARAM (AMOUNT,ACCOUNT,PAYEE,CUSTOMER,DATE)

            //con.setAutoCommit(false);
            //String selectStatement = "SELECT payBill(?,?,?,?,?)";
            //PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            // Convert to Date
            java.util.Date this_date = bill.getDate();
            java.sql.Date sqldate = new java.sql.Date(this_date.getTime());

            //prepStmt.setBigDecimal(1, bill.getAmount());
            //prepStmt.setInt(2, bill.getFrom());
            //prepStmt.setInt(3, bill.getTo());
            //prepStmt.setInt(4, bill.getID());
            //prepStmt.setDate(5, sqldate);
            //prepStmt.executeQuery();
            con.setAutoCommit(false);

            String selectStatement = "SELECT (amount-abs(?)) v_remaining,LPAD(?,12,'0') V_TO FROM account WHERE account=?";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setBigDecimal(1, bill.getAmount());
            prepStmt.setInt(2, bill.getTo());
            prepStmt.setInt(3, bill.getFrom());
            ResultSet rs = prepStmt.executeQuery();
            BigDecimal v_remaining = new BigDecimal(0);
            BigDecimal v_amount = new BigDecimal(0);
            v_amount = bill.getAmount();

            if (v_amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new Exception("Amount cannot have negative values");
            }

            String v_desc = "";
            if (rs.next()) {
                v_remaining = rs.getBigDecimal("v_remaining");
            }

            selectStatement = "SELECT payee FROM payee WHERE pid=?;";
            prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, bill.getTo());
            rs = prepStmt.executeQuery();
            if (rs.next()) {
                v_desc = "Bill payment - " + rs.getString(1);
            }

            float v_rate = getRate(bill.getFrom());
            
            if (v_remaining.compareTo(new BigDecimal(v_rate)) < 0) {
                throw new Exception("Insuffient funds, does not exist in your account");
            }

            if (v_remaining.compareTo(BigDecimal.ZERO) < 0) {
                throw new Exception("Insuffient funds, does not exist in your account");
            }            

            selectStatement = "INSERT INTO bill(pid,date,account,cid,amount) VALUES(?,current_date,?,?,?);";
            prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, bill.getTo());
            prepStmt.setInt(2, bill.getFrom());
            prepStmt.setInt(3, bill.getID());
            prepStmt.setBigDecimal(4, bill.getAmount());

            int count = prepStmt.executeUpdate();

            selectStatement = "INSERT INTO activity(descr,account,cid,date,rem_bal,w_amount) VALUES('" + v_desc + "',?,?,sysdate(),?,?);";
            prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, bill.getFrom());
            prepStmt.setInt(2, bill.getID());
            prepStmt.setBigDecimal(3, v_remaining);
            prepStmt.setBigDecimal(4, bill.getAmount());

            count = prepStmt.executeUpdate();

            selectStatement = "UPDATE account SET amount=amount-? WHERE account=?;";
            prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setBigDecimal(1, bill.getAmount());
            prepStmt.setInt(2, bill.getFrom());
            count = prepStmt.executeUpdate();

            // Return the connection back to the pool
            prepStmt.close();

            con.commit();

        } catch (SQLException ex) {
            con.rollback();
            throw new BillPaymentException(ex.getMessage());
        } catch (ParseException e) {
            con.rollback();
            throw new DateException(e.toString());
        } catch (Exception ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        }
    }

    public float getRate(int accountId) {
        float rate = 0;
        try {
            String selectStatement = "SELECT * FROM SAVINGS WHERE ACCOUNT=" + accountId + " UNION SELECT * FROM CHECKING WHERE ACCOUNT=" + accountId;
            getConnection();
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);

            ResultSet rs = prepStmt.executeQuery();

            // Go through the whole result set
            while (rs.next()) {
                rate = rs.getFloat("rate");
            }
            prepStmt.close();

        } catch (Exception ex) {

        }
        return rate;
    }

    /**
     * Gets all the payees from the list
     *
     * @return Collection
     * @throws PayeeNotFoundException
     */
    public Collection getAllPayee(int accountId) throws PayeeNotFoundException {
        ArrayList payee = null;
        payee = new ArrayList();
        try {

            // Prepare the Prepared Statement
            String selectStatement = "SELECT P.pid,P.payee "
                    + "FROM payee P "
                    + "NATURAL LEFT JOIN customer_payee C "
                    + "WHERE C.cid IS NULL OR C.cid <> ? "
                    + "ORDER BY P.payee";
            getConnection();
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, accountId);

            ResultSet rs = prepStmt.executeQuery();

            // Go through the whole result set
            while (rs.next()) {
                PayeeDetails pd = new PayeeDetails(rs.getInt(1), rs.getString(2));
                payee.add(pd);
            }
            prepStmt.close();
        } catch (SQLException ex) {
            throw new PayeeNotFoundException(ex.getMessage());
        }
        // Collections.sort(payee);
        return payee;
    }

    /**
     * Add Customers Payee
     *
     * @param payeeID
     * @param customerID
     * @return boolean
     * @throws UserNotFoundException
     */
    public boolean addCustomerPayee(int payeeID, int customerID) throws UserNotFoundException {
        try {

            // Query for inserting payee
            String selectStatement = "INSERT INTO customer_payee VALUES(?,?,current_date)";

            // Get the database connection from the Pool of connections
            getConnection();

            // Start the prepared statement
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);

            prepStmt.setInt(1, customerID);
            prepStmt.setInt(2, payeeID);

            // Execute the statement
            int rows = prepStmt.executeUpdate();

            // Return the connection back to the pool
            prepStmt.close();

            // Check if update was successfull
            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            throw new UserNotFoundException("Couldn't find User: " + payeeID + " " + ex.getMessage());
        }
    }

    /**
     * Del Customers Payee
     *
     * @param payeeID
     * @param customerID
     * @return boolean
     * @throws PayeeNotFoundException
     */
    public boolean delCustomerPayee(int payeeID, int customerID) throws PayeeNotFoundException {
        try {

            // Query for inserting payee
            String selectStatement = "DELETE FROM customer_payee WHERE cid=? AND pid=?";

            // Get the database connection from the Pool of connections
            getConnection();

            // Start the prepared statement
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);

            prepStmt.setInt(1, customerID);
            prepStmt.setInt(2, payeeID);

            // Execute the statement
            int rows = prepStmt.executeUpdate();

            // Return the connection back to the pool
            prepStmt.close();

            // Check if update was successfull
            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            throw new PayeeNotFoundException("Couldn't find User: " + payeeID + " " + ex.getMessage());
        }
    }

    /**
     * Returns a list of Customer Payess that they picked
     *
     * @param cid
     * @return Collection
     * @throws PayeeNotFoundException
     */
    public Collection viewCustomerPayee(int cid) throws PayeeNotFoundException {
        ArrayList payee = null;
        payee = new ArrayList();
        try {
            String selectStatement = "SELECT C.pid,P.payee "
                    + "FROM customer_payee C "
                    + "NATURAL JOIN payee P "
                    + "WHERE C.cid = ? ORDER BY P.payee";

            getConnection();
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, cid);

            ResultSet rs = prepStmt.executeQuery();

            while (rs.next()) {
                PayeeDetails pd = new PayeeDetails(rs.getInt(1), rs.getString(2));

                payee.add(pd);
            }
            prepStmt.close();
        } catch (SQLException ex) {
            throw new PayeeNotFoundException(ex.getMessage());
        }
        // Collections.sort(payee);
        return payee;
    }

    /**
     * Information about that specific account
     *
     * @param int accountID
     * @return Collection
     * @throws AccountsNotFoundException
     */
    public Collection getAccount(int accountID) throws AccountNotFoundException {
        ArrayList account = new ArrayList();
        try {
            String selectStatement = "SELECT AC.account,A.cid,A.rem_bal,A.w_amount,A.d_amount,A.descr,A.date,A.activity_id "
                    + "FROM activity AS A "
                    + "JOIN account AS AC ON A.account=AC.account "
                    + "WHERE AC.account = ? ORDER BY A.activity_id ASC";

            getConnection();
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, accountID);

            ResultSet rs = prepStmt.executeQuery();

            while (rs.next()) {
                AccountActivityDetails aa = new AccountActivityDetails(rs.getInt("account"), rs.getInt("cid"), rs.getFloat("rem_bal"), rs.getFloat("w_amount"), rs.getFloat("d_amount"), rs.getString("descr"), rs.getString("date"), rs.getInt("activity_id"));
                account.add(aa);
            }
            prepStmt.close();
        } catch (SQLException ex) {
            throw new AccountNotFoundException(ex.getMessage());
        }
        return account;
    }

    /**
     * Get the current Account Details!
     *
     * @param accountID
     * @return AccountActivityDetails
     * @throws AccountNotFoundException
     */
    public AccountDetails getCurrentAccount(int accountID, int customerID) throws AccountNotFoundException {
        try {
            String selectStatement = "SELECT A.account,H.acttype,A.amount "
                    + "FROM account AS A "
                    + "JOIN holds AS H "
                    + "	ON H.account = A.account "
                    + "WHERE H.account=? AND H.cid=?";

            getConnection();
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, accountID);
            prepStmt.setInt(2, customerID);

            ResultSet rs = prepStmt.executeQuery();

            if (rs.next()) {
                AccountDetails ad = new AccountDetails(rs.getInt(1), rs.getFloat(3), rs.getInt(2));
                prepStmt.close();
                return ad;
            } else {
                prepStmt.close();
                return null;
            }
        } catch (SQLException ex) {
            throw new AccountNotFoundException("Couldn't find Account: " + accountID + " " + ex.getMessage());
        }
    }

    /**
     * Gets the overdraft
     *
     * @param account
     * @return
     * @throws AccountNotFoundException
     */
    public float getOverDraft(int account) throws AccountNotFoundException {

        float overdraft = 0;

        try {
            String selectStatement = "SELECT overdraft FROM checkings WHERE account = ?";

            getConnection();
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, account);

            ResultSet rs = prepStmt.executeQuery();

            if (rs.next()) {
                overdraft = rs.getInt(1);
                prepStmt.close();
            } else {
                prepStmt.close();
            }

            return overdraft;
        } catch (SQLException ex) {
            throw new AccountNotFoundException("Couldn't find Account: " + account + " " + ex.getMessage());
        }
    }
}
