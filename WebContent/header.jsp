<%@page import="java.util.*"%>
<%@page import="javax.naming.*"%>
<%@page import="javax.rmi.*"%>
<%@page import="java.*"%>
<%@page import="java.math.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="bankapp.bean.authentication.AuthenticationDetails"%>
<%@page contentType="text/html; charset=ISO-8859-1"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@page session="true"%>
<%
	AuthenticationDetails logged = (AuthenticationDetails) session
			.getAttribute("logged");
	String module = request.getParameter("Module");
	String active = "class=\"active\"";
	if (module == null) {
		module = "";
	}

	if (logged == null && module != "") {
		module = "";
	}
	if (logged != null && module == "") {
		module = "HOME";
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Main Savings Inc.</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="robots" content="none" />
<meta name="author" content="SANTOSHKUMAR DHANIKLAL SINGH" />
<meta name="keywords" content="University of Ottawa Blog System" />
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="/Bank/css/base.css"
	title="Default" />

<script type="text/javascript" src="/Bank/javascript/functions.js"></script>
<script type="text/javascript" src="/Bank/javascript/form.js"></script>
<script type="text/javascript"
	src="/Bank/javascript/calendarDateInput.js"></script>
<script type="text/javascript" src="/Bank/javascript/style.js"></script>
</head>

<body>
	<div id="container" class="<%=module%>isBackImg">

		<div id="header">
			<div id="lefthead">
				<a href="/Bank/" title="Go Back to Home Page!"> </a>
			</div>
			<div id="subbar">
				<p>
					Welcome <b><%=logged == null ? "Guest" : logged.getFirstName() + " "
					+ logged.getLastName()%></b>
				</p>
				<%
					if (logged != null) {
				%>
				<%
					if (logged.getType() == 1) {
				%>Customer
				<%
					} else if (logged.getType() == 2) {
				%>Employer
				<%
					}
					}
				%>
			</div>
		</div>
		<%
			if (logged != null) {
		%>
		<div id="nav">

			<%
				if (logged.getType() == 1) {
			%>

			<ul>
				<li <%=module.equals("") || module.equals("HOME") ? active
							: ""%>><a
					href="/Bank/myaccount.jsp">Home</a></li>
				<li <%=module.equals("ACCOUNT") ? active : ""%>><a
					href="/Bank/MainController.html?Module=ACCOUNT">Accounts</a></li>
				<li <%=module.equals("PAYMENT") ? active : ""%>><a
					href="/Bank/MainController.html?Module=PAYMENT">Payments</a></li>
				<li <%=module.equals("TRANSFER") ? active : ""%>><a
					href="/Bank/MainController.html?Module=TRANSFER">Transfers</a></li>
				<li <%=module.equals("PROFILE") ? active : ""%>><a
					href="/Bank/MainController.html?Module=PROFILE">Profile</a></li>
				<li <%=module.equals("LOGOUT") ? active : ""%>><a
					href="/Bank/MainController.html?Module=LOGOUT">Logout</a></li>
			</ul>


			<%
				} else if (logged.getType() == 2) {
			%>

			<li <%=module.equals("ADD_CUSTOMER") ? active : ""%>><a
				href="/Bank/Employee.html?Module=ADD_CUSTOMER">Add Customer</a></li>
			<li <%=module.equals("REMOVE_CUSTOMER") ? active : ""%>><a
				href="/Bank/Employee.html?Module=REMOVE_CUSTOMER">Remove
					Customer</a></li>
			<li <%=module.equals("CREATE_ACCOUNT") ? active : ""%>><a
				href="/Bank/Employee.html?Module=CREATE_ACCOUNT">Create Account</a></li>
			<li <%=module.equals("ADD_ACC_MEM") ? active : ""%>><a
				href="/Bank/Employee.html?Module=ADD_ACC_MEM">Add Account
					Members</a></li>
			<li <%=module.equals("TRANSACTION") ? active : ""%>><a
				href="/Bank/Employee.html?Module=TRANSACTION">Transaction</a></li>
			<li <%=module.equals("DELETE_ACCOUNT") ? active : ""%>><a
				href="/Bank/Employee.html?Module=DELETE_ACCOUNT">Delete Account</a></li>
			<li <%=module.equals("LOGOUT") ? active : ""%>><a
				href="/Bank/MainController.html?Module=LOGOUT">Logout</a></li>

			<%
				}
			%>
		</div>
		<%
			}
		%>


		<div id="main">