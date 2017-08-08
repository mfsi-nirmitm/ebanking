<%@page import="bankapp.bean.account.AccountSummaryDetails"%>
<%@page import="java.text.*"%>

<%@include file="header.jsp" %>

<%if (logged == null) {%>
<jsp:forward page="/index.jsp"/>
<%}%>
<%
    Collection account_list = (Collection) request.getAttribute("account_list");
    DecimalFormat accountFormatter = new DecimalFormat("000000000000");
    DecimalFormat balanceFormatter = new DecimalFormat("0.00");

    double totalbalance = 0.0;

    int j = 1;
    String color;
    String color1 = "row1";
    String color2 = "row2";

%>

<h1>Account</h1>

<h2>My Account Summary</h2>

<table id="accsumm">
    <thead>
        <tr>
            <td>Account Type</td>
            <td>Account Number</td>
            <td>Balance</td>
        </tr>
    </thead>
    <tbody>
        <%for (Iterator i = account_list.iterator(); i.hasNext();) {
                AccountSummaryDetails account_key = (AccountSummaryDetails) i.next();

                if (j % 2 == 1) {
                    color = color1;
                } else {
                    color = color2;
                }
                j++;

                totalbalance = totalbalance + account_key.getAccountBalance();

        %>
        <tr class="<%=color%>">
            <td><%=account_key.getAccountType() == 1 ? "Saving" : "Current"%></td>
            <td><a href="/Bank/MainController.html?Module=ACCOUNT&Id=<%=account_key.getAccountID()%>" class="actlnk"><%=accountFormatter.format(account_key.getAccountID())%></a></td>
            <td><%=balanceFormatter.format(account_key.getAccountBalance())%></td>
        </tr>		
        <%	}%>
    </tbody>
    <tfoot>
        <tr>
            <td colspan="2">Total Day-to-Day Banking:</td>
            <td><%=balanceFormatter.format(totalbalance)%></td>
        </tr>
    </tfoot>
</table>

<div id="note">
    <p>Notes</p>
    <ul>
        <li>Important Information about how your accounts are displayed</li>
        <li>Click account number to view account information.</li>
        <li>Click on the balance to view the account details (where applicable).</li>
    </ul>
</div>
<%@include file="footer.jsp" %>