<%@page import="bankapp.database.EmployeeDB"%>
<%@include file="header.jsp" %>

<%if (logged == null) {%>
<jsp:forward page="/index.jsp"/>
<%}%>

<%

    String GOOD_MSG = (String) request.getAttribute("GOOD_MSG");
    String ERROR_MSG = (String) request.getAttribute("ERROR_MSG");

    Vector allBranchs = (Vector) session.getAttribute("allBranchs");

    EmployeeDB bankDB = (EmployeeDB) getServletContext().getAttribute("bankDB");
    if (allBranchs == null) {
        session.setAttribute("allBranchs", bankDB.getAllBranches());
        allBranchs = (Vector) session.getAttribute("allBranchs");
    }

%>

<h1>Add a Customer</h1>

<form id="reg" method="post" action="/Bank/Employee.html" >

    <%=GOOD_MSG == null && ERROR_MSG == null ? "<h2>Insert Customer Into Database</h2>" : ""%>
    <%=GOOD_MSG == null ? "" : "<div class=\"good\">" + GOOD_MSG + "</div>"%>
    <%=ERROR_MSG == null ? "" : "<div class=\"error\">" + ERROR_MSG + "</div>"%>
    <ul>
        <li><label for="fname">First Name</label><input id="fname" type="text" name="fname"  value=""/><br/></li> 
        <li><label for="lname">Last Name</label><input id="lname" type="text" name="lname"  value=""/><br/></li> 
        <li><label for="address">Address</label><input id="address" type="text" name="address"  value=""/><br/></li> 
        <li><label for="city">City</label><input id="city" type="text" name="city"  value="" /><br/></li>
        <li><label for="phone">Phone Number</label><input id="phone" type="text" name="phone"  value=""/><br/></li> 
        <li><label for="email">Email Address</label><input id="email" type="text" name="email"  value=""/><br/></li>  

        <li><label for=""branch"">Branch Name</label> 
            <select name="branch" id=""branch" width="140">

                    <% for (int x = 0; x < allBranchs.size(); x++) {%> 

                    <option value="<%=(String) allBranchs.elementAt(x)%>"><%=((String) allBranchs.elementAt(x)).toString()%></option>

                <% }%>
            </select>
        </li>

        <li><label for="best_time">Best Time</label> 
            <select name="best_time" id="best_time" width="140">
                <option value="morning">Morning</option>
                <option value="evening">Evening</option>
                <option value="night">Night</option>
            </select>
        </li>
    </ul>
    <input type="hidden" name="action" value="addCustomer">
    <input class="nolabel" type="submit" name="submit" value="Add Customer" />
</form>
<%@include file="footer.jsp" %>