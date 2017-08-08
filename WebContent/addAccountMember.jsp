<%@page import="java.util.*" %>
<%@include file="header.jsp" %>

<%if(logged == null) {%>
	<jsp:forward page="/index.jsp"/>
<%}%>

<%

String GOOD_MSG = (String)request.getAttribute("GOOD_MSG");
String ERROR_MSG = (String)request.getAttribute("ERROR_MSG");

Vector allCustomers = (Vector) session.getAttribute("allCustomers");
Vector allAccounts = (Vector) session.getAttribute("allAccounts");

%>

<h1>Add Customer To Account</h1>

<form id="reg" method="post" action="/Bank/Employee.html" >

	<%=GOOD_MSG == null && ERROR_MSG == null ? "<h2>Add Customer to Account</h2>" : "" %>
	<%=GOOD_MSG == null ? "" : "<div class=\"good\">"+GOOD_MSG+"</div>"%>
	<%=ERROR_MSG == null ? "" : "<div class=\"error\">"+ERROR_MSG+"</div>"%>
	<ul>
		<li><label for="cid">Customer ID</label> 
			<select name="cid" id="cid" width="140">
				
				<% for (int x = 0; x < allCustomers.size(); x ++) { %> 
				
					<option value="<%=((Integer)allCustomers.elementAt(x)).intValue()%>"><%=((Integer)allCustomers.elementAt(x)).intValue()%></option>
				
				<% } %>
				
			</select>
		</li> 
		
		<li><label for="accountId">Account ID</label> 
			<select name="accountId" id="accountId" width="140">
				
				<% for (int x = 0; x < allAccounts.size(); x ++) { %> 
				
					<option value="<%=((Integer)allAccounts.elementAt(x)).intValue()%>"><%=((Integer)allAccounts.elementAt(x)).intValue()%></option>
				
				<% } %>
				
			</select>
		</li> 
		
	</ul>
		<input type="hidden" name="action" value="addCustomerToAccount">
		<input class="nolabel" type="submit" name="submit" value="Add Customer to Account" />
</form>
<%@include file="footer.jsp" %>