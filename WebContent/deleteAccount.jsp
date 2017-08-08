<%@include file="header.jsp" %>

<%if(logged == null) {%>
	<jsp:forward page="/index.jsp"/>
<%}%>

<%

String GOOD_MSG = (String)request.getAttribute("GOOD_MSG");
String ERROR_MSG = (String)request.getAttribute("ERROR_MSG");

Vector allAccounts = (Vector) session.getAttribute("allAccounts");

%>

<h1>Remove Account</h1>

<form id="reg" method="post" action="/Bank/Employee.html" >

	<%=GOOD_MSG == null && ERROR_MSG == null ? "<h2>Remove Account From System</h2>" : "" %>
	<%=GOOD_MSG == null ? "" : "<div class=\"good\">"+GOOD_MSG+"</div>"%>
	<%=ERROR_MSG == null ? "" : "<div class=\"error\">"+ERROR_MSG+"</div>"%>
	<ul>
			<li><label for="accountId">Account ID</label> 
			<select name="accountId" id="accountId" width="140">
				
				<% for (int x = 0; x < allAccounts.size(); x ++) { %> 
				
					<option value="<%=((Integer)allAccounts.elementAt(x)).intValue()%>"><%=((Integer)allAccounts.elementAt(x)).intValue()%></option>
				
				<% } %>
				
			</select>
		</li> 
	</ul>
		<input type="hidden" name="action" value="deleteAccount">
		<input class="nolabel" type="submit" name="submit" value="Delete Account" />
</form>
<%@include file="footer.jsp" %>