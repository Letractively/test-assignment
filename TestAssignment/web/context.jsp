<%-- 
    Document   : context
    Created on : 09.08.2013, 9:32:23
    Author     : roman
--%>

<%@page import="Control.WebEngine"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Здравствуй <%=request.getSession().getAttribute(WebEngine.SESSION_USER_NAME) %>!</h1>
        <a href="Login?operationtype=deavtorizare">выход из сеанса</a>
    </body>
</html>
