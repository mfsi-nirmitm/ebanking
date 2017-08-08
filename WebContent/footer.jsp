<%

    Integer QUERIES = new Integer(0);

    if (request.getAttribute("QUERIES") != null) {
        QUERIES = (Integer) request.getAttribute("QUERIES");
    }

    Long startime = new Long(System.currentTimeMillis());
    if (request.getAttribute("START") != null) {
        startime = (Long) request.getAttribute("START");
    }

    long end = System.currentTimeMillis();
    long start = startime.longValue();
    long diff = end - start;
    float elapse_time = (float) diff / 1000;

%>
</div>
<div id="footer" style="padding-left: 10px;">
    <br/>
    <span>Developed by Nirmit Maheshwari, Surya Prakash, Ayush Mishra, Israil Ahmad</span>
    <br/>
    <span>Copyright &copy; 2017  All rights reserved. Page loaded in <%=elapse_time%> seconds, using <%=QUERIES%> query(s)!</span>    
    <br/>
</div>
</div>
</body>
</html>