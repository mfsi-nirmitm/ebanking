<%@include file="header.jsp" %>

<%if (logged == null) {%>
<jsp:forward page="/index.jsp"/>
<%}%>

<%

    String GOOD_MSG = (String) request.getAttribute("GOOD_MSG");
    String ERROR_MSG = (String) request.getAttribute("ERROR_MSG");

    Vector allCustomers = (Vector) session.getAttribute("allCustomers");
    Vector allAccounts = (Vector) session.getAttribute("allAccounts");

%>

<h1>Transaction</h1>

<form id="reg" method="post" action="/Bank/Employee.html" >

    <%=GOOD_MSG == null && ERROR_MSG == null ? "<h2>Use to deposit and withdrawn</h2>" : ""%>
    <%=GOOD_MSG == null ? "" : "<div class=\"good\">" + GOOD_MSG + "</div>"%>
    <%=ERROR_MSG == null ? "" : "<div class=\"error\">" + ERROR_MSG + "</div>"%>
    <ul>
        <li><label for="cid">Customer ID</label> 
            <select name="cid" id="cid" width="140">

                <% for (int x = 0; x < allCustomers.size(); x++) {%> 

                <option value="<%=((Integer) allCustomers.elementAt(x)).intValue()%>"><%=((Integer) allCustomers.elementAt(x)).intValue()%></option>

                <% } %>

            </select>
        </li> 
        <li><label for="accountId">Account ID</label> 
            <select name="accountId" id="accountId" width="140">

                <% for (int x = 0; x < allAccounts.size(); x++) {%> 

                <option value="<%=((Integer) allAccounts.elementAt(x)).intValue()%>"><%=((Integer) allAccounts.elementAt(x)).intValue()%></option>

                <% }%>

            </select>
        </li>
        <li><label for="remark">Remarks </label>
            <input name="remark" width="140" size="25"/>
        </li>
        <li><label for="type">Transaction Type </label>
            <select name="type" id="type" width="140">
                <option>Withdrawn</option>
                <option>Deposit</option>
            </select>
        </li>
        <li><label for="amount">Amount </label>
            <input name="amount" width="140" size="25"/>
        </li>
    </ul>
    <input type="hidden" name="action" value="transaction">
    <input class="nolabel" type="submit" name="submit" value="Submit" />
</form>


<div id="note">
    <p>Notes</p>
    <ul>
        <li>All currency is in Indian Rupees!</li>
        <li>Navigate to your other accounts within the drop down box above.</li>	
        <li>Please select Transaction Type</li>
        <li>Overdraft money is not shown!</li>
    </ul>
</div>

<%@include file="footer.jsp" %>