<%@page import="bankapp.bean.account.AccountSummaryDetails"%>
<%@page import="bankapp.bean.account.PayeeDetails"%>
<%@page import="java.text.*"%>

<%@include file="header.jsp"%>

<%if (logged == null) {%>
<jsp:forward page="/index.jsp" />
<%}%>
<%
    DecimalFormat accountFormatter = new DecimalFormat("000000000000");
    String GOOD_MSG = (String) request.getAttribute("GOOD_MSG");
    String ERROR_MSG = (String) request.getAttribute("ERROR_MSG");
    String submit = request.getParameter("submit");
    String amount = request.getAttribute("amount").toString();
    String year = request.getAttribute("year").toString();
    String month = request.getAttribute("month").toString();
    String day = request.getAttribute("day").toString();

    Collection account_list = (Collection) request.getAttribute("account_list");
    Collection payee_list = (Collection) request.getAttribute("payee_list");
%>

<h1>Bill Payment</h1>

<h2>Pay a Bill</h2>

<%@include file="payment_side.jsp"%>

<form id="reg" method="post" action="/Bank/MainController.html?Module=PAYMENT">
    <%=GOOD_MSG == null && ERROR_MSG == null ? "" : ""%>
    <%=GOOD_MSG == null || GOOD_MSG.length() == 0 ? "" : "<div class=\"good\">" + GOOD_MSG + "</div>"%>
    <%=ERROR_MSG == null || ERROR_MSG.length() == 0 ? "" : "<div class=\"error\">" + ERROR_MSG + "</div>"%>

    <%
        if (payee_list == null || payee_list.size() == 0) {
    %> <p>You must add a payee first!</p><%
    } else if (account_list == null || account_list.size() == 0) {
    %> <p>You have no accounts </p><%
    } else {

    %>
    <ul>
        <li>
            <label for="to">To </label> 
            <select name="to" id="to">
                <%for (Iterator i = payee_list.iterator(); i.hasNext();) {
                        PayeeDetails payee_key = (PayeeDetails) i.next();%>
                <option value="<%=payee_key.getPayeeID()%>"><%=payee_key.getPayeeName()%></option>
                <%}%>
            </select>
        </li>
        <li>
            <label for="from">From </label> 
            <select name="from" id="from">
                <%for (Iterator i = account_list.iterator(); i.hasNext();) {
                        AccountSummaryDetails account_key = (AccountSummaryDetails) i.next();%>
                <option value="<%=account_key.getAccountID()%>"><%=account_key.getAccountType() == 1 ? "Saving" : "Current"%> - <%=accountFormatter.format(account_key.getAccountID())%>	 <%=account_key.getAccountBalance()%></option>
                <%}%>
            </select>
        </li>
        <li>
            <label>Date </label> 
            <select name="tmonth">
                <option value="01" <%=month.equals("01") ? "selected=\"selected\"" : ""%>>January</option>
                <option value="02" <%=month.equals("02") ? "selected=\"selected\"" : ""%>>February</option>
                <option value="03" <%=month.equals("03") ? "selected=\"selected\"" : ""%>>March</option>
                <option value="04" <%=month.equals("04") ? "selected=\"selected\"" : ""%>>April</option>
                <option value="05" <%=month.equals("05") ? "selected=\"selected\"" : ""%>>May</option>
                <option value="06" <%=month.equals("06") ? "selected=\"selected\"" : ""%>>June</option>
                <option value="07" <%=month.equals("07") ? "selected=\"selected\"" : ""%>>July</option>
                <option value="08" <%=month.equals("08") ? "selected=\"selected\"" : ""%>>August</option>
                <option value="09" <%=month.equals("09") ? "selected=\"selected\"" : ""%>>September</option>
                <option value="10" <%=month.equals("10") ? "selected=\"selected\"" : ""%>>October</option>
                <option value="11" <%=month.equals("11") ? "selected=\"selected\"" : ""%>>November</option>
                <option value="12" <%=month.equals("12") ? "selected=\"selected\"" : ""%>>December</option>
            </select> 
            <select name="tday">
                <%DecimalFormat myFormatter = new DecimalFormat("00");
                    for (int i = 1; i < 32; i++) {%>
                <option value="<%=myFormatter.format(i)%>" <%=day.equals(myFormatter.format(i)) ? "selected=\"selected\"" : ""%>><%=myFormatter.format(i)%></option>
                <%}	%>
            </select> 
            <select name="tyear">      
                <% for (int i = 2013; i <= 2015; i++) {
                %>
                <option  value = "<%=i%>" < %= year.equals("<%=i%>") ? "selected=\"selected\"" : "" %>><%=i%></option>
                <%}%>
            </select> 

            <!-- <script>DateInput('orderdate', true, 'DD-MON-YYYY')</script> -->
        </li>
        <li>
            <label for="amount">Amount </label>
            <input id="amount" type="text" name="amount" value="<%=amount == null ? "" : amount%>" /><br />
        </li>

        <li>
            <input name="cyear" value="<%=year%>" type="hidden" /> 
            <input name="cmonth" value="<%=month%>" type="hidden" /> 
            <input name="cday" value="<%=day%>" type="hidden" /> 
            <input name="action" value="PAY_BILL" type="hidden" />
            <input	class="nolabel" type="submit" name="submit" value="Submit" />
        </li>
    </ul>
</form>

<div id="note">
    <p>Notes </p>	 
    <ul>
        <li>Date must be today or later!</li>
        <li>The balance on your account doesn't have overdraft protection.</li>
        <li>Use the navigation above to add/view a payee or pay a bill.</li>
    </ul>
</div>

<% }%>
<%@include file="footer.jsp"%>
