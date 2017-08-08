<%
    String ERROR_MSG = (String) request.getAttribute("ERROR_MSG");
    String username = request.getParameter("username");
%>

<form id="reg" method="post" action="/Bank/MainController.html?Module=ACCOUNT" >
    <%=ERROR_MSG == null || ERROR_MSG.length() == 0 ? "" : "<div class=\"error\">" + ERROR_MSG + "</div>"%>

    <ul>
        <li>
            <label for="username">Account</label>
            <input id="username" type="text" name="username"  />
        </li>
        <li>
            <label for="password">Password</label>
            <input id="password" type="password" name="password"  />
        </li> 
        <li>
            <label for="type">Type</label> 
            <select name="type" id="type">
                <option value="1">Customer</option>
                <option value="2">Employee</option>
            </select>
        </li>
        <li>
            <input type="hidden" name="action" value="LOGIN" />
            <input class="nolabel" type="submit" name="submit" value="Login" />
        </li>
    </ul>

</form>

