<%-- 
    Document   : context
    Created on : 07.08.2013, 21:37:22
    Author     : Роман
--%>

<%@page import="Control.UserMessage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>авторизация</title>
    </head>
    <body>
    <dir>
        <h1>Авторизация</h1>
        <jsp:useBean id="userException" scope="request" class="Control.UserMessage" />
        
        <% if(userException.getMessage()!=null){
         out.print("<i>"+userException.getMessage()+"</i>"); 
        } %>
        
        
        <form name="avtorization" id="avtorization" action="Login" method="post">
            <input type="text" placeholder="логин" id="login" name="login">
            <p><input type="password" placeholder="пароль" id="password" name="password">
            <p><input type="submit" value="Авторизация">
                <input type="checkbox" name="remember" id="remember"> Запомнить меня
                <input type="hidden" value="login" name="operationtype" id="operationtype">
            <p><input type="button" value="Регистрация" onclick="document.getElementById('operationtype').value='register'; document.getElementById('avtorization').submit();"> 
        </form>
        
    </dir>
     </body>
</html>
