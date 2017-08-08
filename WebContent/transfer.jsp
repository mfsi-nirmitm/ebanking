<%@page import="bankapp.bean.account.AccountSummaryDetails"%>
<%@page import="java.text.*"%>

<%@include file="header.jsp"%>

<%if (logged == null) {%>
<jsp:forward page="/index.jsp" />
<%}%>
<%
	DecimalFormat accountFormatter = new DecimalFormat("000000000000");
	String GOOD_MSG = (String) request.getAttribute("GOOD_MSG");
	String ERROR_MSG = (String) request.getAttribute("ERROR_MSG");
	String amount = (String) request.getAttribute("amount");
	String to = (String) request.getAttribute("to");
	String from = (String) request.getAttribute("from");
	Collection account_list = (Collection) request.getAttribute("account_list");

%>

<h1>Transfer</h1>

<h2>Transfer an Account</h2>

<form id="reg" method="post" action="/Bank/MainController.html?Module=TRANSFER">
<%=GOOD_MSG == null && ERROR_MSG == null ? "" : ""%>
<%=GOOD_MSG == null  || GOOD_MSG.length()==0 ? "" : "<div class=\"good\">" + GOOD_MSG + "</div>"%>
<%=ERROR_MSG == null || ERROR_MSG.length()==0 ? "" : "<div class=\"error\">" + ERROR_MSG + "</div>"%>

<%
if( account_list == null || account_list.size() == 0) {
	%> <p>You have no accounts </p><%
} else {

%>
<ul>
	<li>
		<label for="from">Account </label> 
		<select name="from" id="from">
		<%for (Iterator i = account_list.iterator(); i.hasNext();) {
			AccountSummaryDetails account_key = (AccountSummaryDetails) i.next();%>
			<option value="<%=account_key.getAccountID()%>"><%=account_key.getAccountType() == 1 ? "Saving" : "Current"%> - <%=accountFormatter.format(account_key.getAccountID())%>	 <%=account_key.getAccountBalance() %></option>
		<%}	%>
		</select>
	</li>
	<li>
		<label for="to">Transfer To </label>
		<input id="to" type="text" name="to" value="<%=to == null ? "" : to %>" /><br />
	</li>
	<li>
		<label for="amount">Amount </label>
		<input id="amount" type="text" name="amount" value="<%=amount == null ? "" : amount %>" /><br />
	</li>
	
	<li>
		<input name="action" value="TRANSFER_MONEY" type="hidden" />
		<input	class="nolabel" type="submit" name="submit" value="Submit" />
	</li>
</ul>
</form>

<div id="note">
<p>Notes </p>	 
<ul>
	<li>Transfers exist instantly!</li>
	<li>Must have money in the account inorder to transfer!</li>
</ul>
</div>

<% } %>

<%@include file="footer.jsp"%>
