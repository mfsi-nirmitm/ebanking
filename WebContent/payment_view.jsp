<%@page import="bankapp.bean.account.PayeeDetails"%>

<%@include file="header.jsp" %>

<%if(logged == null) {%>
	<jsp:forward page="/index.jsp"/>
<%}%>
<%
	Collection payee_collection = (Collection)request.getAttribute("payee_list");
	String GOOD_MSG = (String) request.getAttribute("GOOD_MSG");
	String ERROR_MSG = (String) request.getAttribute("ERROR_MSG");
%>

<h1>Bill Payment</h1>

<h2> Payee View</h2>

<%@include file="payment_side.jsp" %>

<%=GOOD_MSG == null  || GOOD_MSG.length()==0 ? "" : "<div class=\"good\">" + GOOD_MSG + "</div>"%>
<%=ERROR_MSG == null || ERROR_MSG.length()==0 ? "" : "<div class=\"error\">" + ERROR_MSG + "</div>"%>


<ul id="payview">
	<%if(payee_collection.size() == 0) {%>
		<li>You have no payees, please add a Payee!</li><%
	} else {
		int j = 1;
		for(Iterator i = payee_collection.iterator(); i.hasNext();) {
			PayeeDetails payee_key = (PayeeDetails) i.next();%>
			<li>#<%=j %>| <a class="deletepayee" href="/Bank/MainController.html?Module=PAYMENT&Method=DELETE&Payee=<%=payee_key.getPayeeID()%>">DELETE</a> | <%=payee_key.getPayeeName()%></li>
		<%  j++;
		} 
	} %>
</ul>

<div id="note">
<p>Notes </p>	 
<ul>
	<li>Those payees are registerd under your account.</li>
	<li>When you delete a payee it is still recorded in your Activities.</li>
</ul>
</div>
 
<%@include file="footer.jsp" %>