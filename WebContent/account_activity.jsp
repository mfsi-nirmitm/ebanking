<%@page import="bankapp.bean.account.AccountSummaryDetails"%>
<%@page import="bankapp.bean.account.AccountActivityDetails"%>
<%@page import="bankapp.bean.account.AccountDetails"%>
<%@page import="java.text.*"%>
<%@page import="java.text.*"%>

<%@include file="header.jsp" %>

<%if(logged == null) {%>
	<jsp:forward page="/index.jsp"/>
<%}%>
<%
	AccountDetails current_account = (AccountDetails) request.getAttribute("current_account");

	if(current_account == null) {%>
		<h1>ERROR 404: File Not Found</h1>
		
		<h2>Your access to this area has been denied!</h2>
	<%} else {
	
	Collection detail_list = (Collection) request.getAttribute("detail_list");	
	Collection account_list = (Collection) request.getAttribute("account_list");
	
	DecimalFormat activityFormatter = new DecimalFormat("00000");
	DecimalFormat accountFormatter = new DecimalFormat("000000000000");
	DecimalFormat balanceFormatter = new DecimalFormat("0.00");
	
	double totalbalance = 0.0;
	
	int j = 1;
	String color1 = "row1";
	String color2 = "row2";
	String color = color1;
%>

<h1>Account Details</h1>

<form method="get" action="">
	<select name="Id" id="Id" onchange="jumpToPage('parent',this,0)">
		<%for (Iterator i = account_list.iterator(); i.hasNext();) {
			AccountSummaryDetails account_key = (AccountSummaryDetails) i.next(); %>
		<option value="<%=account_key.getAccountID()%>" <%=Integer.parseInt(request.getParameter("Id")) == account_key.getAccountID() ? "selected" : ""%>><%=account_key.getAccountType() == 1 ? "Saving" : "Current"%> - <%=accountFormatter.format(account_key.getAccountID())%>	</option>
	<% } %>
	</select>
</form>

<h2>Account</h2>
<table id="accsumm">
<thead>
	<tr>
		<td>Account Type</td>
		<td>Account Number</td>
		<td>Balance</td>
	</tr>
</thead>
<tbody>
	<tr class="<%=color %>">
		<td><%=current_account.getAccountType() == 1 ? "Saving" : "Current"%></td>
		<td><%=accountFormatter.format(current_account.getAccountID()) %></td>
		<td><%=balanceFormatter.format(current_account.getAmount()) %></td>
	</tr>
</tbody>
</table>

<h2>Transaction Details</h2>
<table id="accsumm">
<thead>
	<tr>
		<td>Activity ID</td>
		<td>Date</td>
		<td>Description</td>
		<td>Withdrawls</td>
		<td>Deposits</td>
		<td>Balance</td>
	</tr>
</thead>
<tbody>
<%if(detail_list.size()==0) {%>
	<tr class="row2">
		<td colspan="6">You have no activity yet for this account!</td>
	</tr>
<%} else {
	for (Iterator i = detail_list.iterator(); i.hasNext();) {
		AccountActivityDetails activity_key = (AccountActivityDetails) i.next();

		if (j % 2 == 1)
			color = color1;
		else 
			color = color2;
		j++;
		
		float w_amount = activity_key.getWAmount();
                float d_amount = activity_key.getDAmount();
		%>
	<tr class="<%=color %>">
		<td><%=activityFormatter.format(activity_key.getActivityID())%></td>
		<td><%=activity_key.getDate()%></td>
		<td><%=activity_key.getDescription()%></td>
		<td><%if(w_amount<=0) { %> - <% } else { %> <%=balanceFormatter.format(w_amount) %> <% } %></td>
		<td><%if(d_amount<=0) { %> - <% } else { %> <%=balanceFormatter.format(d_amount) %> <% } %></td>
		<td><%=balanceFormatter.format(activity_key.getBalance())%></td>
	</tr>		
	<%	}
	} %>
</tbody>
</table>

<div id="note">
<p>Notes</p>
<ul>
	<li>All currency is in Indian Rupees!</li>
	<li>Navigate to your other accounts within the drop down box above.</li>	
	<li>The date shown will be the date when the money is being sent!</li>
	<li>Overdraft money is not shown!</li>
</ul>
</div>
<% } %>
<%@include file="footer.jsp" %>