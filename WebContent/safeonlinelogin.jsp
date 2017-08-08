<%@include file="header.jsp" %>

<%if (logged != null) {%>
<jsp:forward page="/login.jsp"/>
<%}%>

<h1>Main</h1>


<style type="text/css">
    div.LOGOUTisBackImg, div.isBackImg {
        background: url("/Bank/images/SecureLogo.jpg") no-repeat scroll 32em 24em rgba(0, 0, 0, 0);
    }

    #accounts_summary1 {
        background-color: #F4F8F9;
        border: 1px solid #E3E7E8;
        font-family: Arial,Helvetica,sans-serif;
        font-size: 11px;
        font-weight: bold;
        line-height: 13px;
        margin: 10px 5px;
        width: 99%;
    }
    #write1 {
        border-right: 1px solid #E3E7E8;
        float: left;
        height: 80px;
        padding-left: 10px;
        width: 23%;
    }

    .login_tips_1 {
        background: url("/Bank/images/smiley.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
        height: 45px;
        margin-top: 15px;
        padding: 2px 0 0 50px;
    }

    #wrong1 {
        border-right: 1px solid #E3E7E8;
        float: left;
        height: 80px;
        padding-left: 10px;
        width: 23%;
    }


    .login_tips_2 {
        background: url("/Bank/images/smiley-sad.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
        height: 45px;
        margin-top: 15px;
        padding: 2px 0 0 50px;
    }
</style>

<div id="accounts_summary1" >
    <!-- Jaya -->
    <div id="write1">
        <div class="login_tips_1"> <span class="blue">ALWAYS </span>change your passwords periodically. </div>
    </div>

    <div id="wrong1">
        <div class="login_tips_2"> <span class="orange">NEVER</span>&nbsp;reveal your passwords or card details to anyone. </div>
    </div>

    <div id="write1">
        <div class="login_tips_1"> <span class="blue">ALWAYS </span>keep your computer free&nbsp;of malware. </div>
    </div>

    <div style="border-right: #fff 0px solid;" id="wrong1">
        <div class="login_tips_2"> <span class="orange">NEVER </span>respond to any communication seeking your passwords. </div>
    </div>			    
    <div style="clear: both"></div>
</div>

<div id="homecont" style="float:left;">
    <ul>
        <li>
            <p>Do not enter login or other sensitive information in any pop up window</p>

        </li>
        <li>
            <p>Phishing is a fraudulent attempt, usually made through email, phone calls, SMS etc seeking your personal and confidential information.</p>

        </li>
        <li>
            <p>Bank or any of its representative never sends you email/SMS or calls you over phone to get your personal information, password or one time SMS (high security) password</p>

        </li>
        <li>
            <p> Any such e-mail/SMS or phone call is an attempt to fraudulently withdraw money from your account through Internet Banking. Never respond to such email/SMS or phone call. Please report immediately.If you receive any such email/SMS or Phone call. </p>
        </li>
    </ul>
    <a href="login.jsp">
        <img border="0" align="center" class="phisingcontinueButton" alt="Continue to Login" title="Continue to Login" src="/Bank/images/login.jpg">
    </a>
</div>

<%@include file="footer.jsp" %>