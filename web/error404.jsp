<%-- 
    Document   : error404
    Created on : 03-08-2015, 06:11:18 PM
    Author     : cvaldes
--%>

<%
    String mensaje = (String) request.getAttribute("mensaje");
    String estilo = (String) request.getAttribute("estilo");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .info, .exito, .alerta, .error{
                font-family: Arial, Helvetica, sans-serif;
                font-size: 17px;
                border: 1px solid;
                margin:10px 0px;
                padding:15px 10px 15px 50px;
               
            }
            .info{
                color:#00529B;
                background-color: #BDE5F8;
            }
            .exito{
                color: #4F8A10;
                background-color: #DFF2BF;
            }
            .alerta{
                color:#9F6000;
                background-color: #FEEFB3;
            }
            .error{
                color:#D8000C;
                background-color: #FFBABA;
            }
        </style>
    </head>
    <body>
        <center>
            <h1 class="error">
                ESTE ES UN ERROR 404
            </h1>
            <p><a href="index.jsp">sigue buscando en Home</a></p>
        </center>
    </body>
</html>
