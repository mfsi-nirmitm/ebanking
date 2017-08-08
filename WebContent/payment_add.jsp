<%@page import="bankapp.bean.account.PayeeDetails"%>

<%@include file="header.jsp" %>

<%if(logged == null) {%>
	<jsp:forward page="/index.jsp"/>
<%}%>
<%
Collection payee_collection = (Collection)request.getAttribute("payee_list");
String ERROR_MSG = (String) request.getAttribute("ERROR_MSG");
%>

<h1>Bill Payment</h1>

<h2>Add a Payee</h2>

<%@include file="payment_side.jsp" %>

<form id="reg" method="post" action="/Bank/MainController.html?Module=PAYMENT" >

<%=ERROR_MSG == null || ERROR_MSG.length()==0 ? "" : "<div class=\"error\">" + ERROR_MSG + "</div>"%>

<ul>
	<li>
		<label for="best_time">Payee List:</label> 
		<select name="payee_list" id="payee_list">
		<%for(Iterator i = payee_collection.iterator(); i.hasNext();) {
			PayeeDetails payee_key = (PayeeDetails) i.next();%>
			<option value="<%=payee_key.getPayeeID()%>"> <%=payee_key.getPayeeName()%></option>
		<% } %>
		</select>
	</li>
	<li>
		<input type="hidden" name="action" value="ADD_PAYEE">
		<input class="nolabel" type="submit" name="submit" value="Add Payee" />
	</li>		
</ul>

</form>

<div id="note">
<p>Notes </p>	 
<ul>
	<li>If you wish to add a payee to the list, please contact the branch for approval. (security purposes)</li>
 </ul>
 </div>
 
<%@include file="footer.jsp" %>