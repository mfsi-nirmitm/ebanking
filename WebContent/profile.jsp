<%@page import="bankapp.database.EmployeeDB"%>
<%@page import="bankapp.bean.account.CustomerDetails"%>

<%@include file="header.jsp" %>

<%if (logged == null) {%>
<jsp:forward page="/index.jsp"/>
<%}%>
<%
    String GOOD_MSG = (String) request.getAttribute("GOOD_MSG");
    String ERROR_MSG = (String) request.getAttribute("ERROR_MSG");
    String submit = request.getParameter("submit");

    CustomerDetails cd = (CustomerDetails) request.getAttribute("customer_details");

%>

<h1>Profile</h1>

<h2>Update Porfile information</h2>

<form id="reg" method="post" action="/Bank/MainController.html?Module=PROFILE" >

    <%=GOOD_MSG == null || GOOD_MSG.length() == 0 ? "" : "<div class=\"good\">" + GOOD_MSG + "</div>"%>
    <%=ERROR_MSG == null || ERROR_MSG.length() == 0 ? "" : "<div class=\"error\">" + ERROR_MSG + "</div>"%>

    <ul>
        <li>
            <label for="email">*E-Mail</label>
            <input id="email" type="text" name="email"  value="<%=cd.getEmail() == null ? "" : cd.getEmail()%>" />
        </li> 

        <li>
            <label for="branch">*Branch</label>
            <select name="branch" id=branch>
                <option><%=cd.getBranch()%></option>
            </select>
        </li>

        <li>
            <label for="pass1">*Password</label><input id="pass1" type="password" name="pass1"  value="<%=cd.getPassword() == null ? "" : cd.getPassword()%>" />
        </li> 
        <li>
            <label for="pass2">Confirm Password</label>
            <input id="pass2" type="password" name="pass2"  />
        </li> 
        <li>
            <label for="address">*Address</label>
            <input id="address" type="text" name="address"  value="<%=cd.getAddress() == null ? "" : cd.getAddress()%>" />
        </li> 
        <li>
            <label for="city">*City</label>
            <input id="city" type="text" name="city"  value="<%=cd.getCity() == null ? "" : cd.getCity()%>" />
        </li> 
        <li>
            <label for="phone">*Phone</label>
            <input id="phone" type="text" name="phone"  value="<%=cd.getPhone() == null ? "" : cd.getPhone()%>" />
        </li> 
        <li>
            <label for="best_time">*Best Time</label> 
            <select name="best_time" id=best_time>
                <option><%=cd.getBestTime()%></option>
            </select>
        </li>
        <li>
            <input type="hidden" name="action" value="ALTER_PROFILE" />
            <input class="nolabel" type="submit" name="submit" value="Update" />
        </li>
    </ul>
</form>

<div id="note">
    <p>Notes </p>	 
    <ul>
        <li>Those payees are registerd under your account.</li>
        <li>When you delete a payee it is still recorded in your Activities.</li>
    </ul>
</div>
<%@include file="footer.jsp" %>