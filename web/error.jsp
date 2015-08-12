<%-- 
    Document   : error
    Created on : 30-07-2015, 12:51:50 PM
    Author     : cvaldes
--%>
<%
    String mensaje = (String) request.getAttribute("mensaje");
    String imprimir = (String) request.getAttribute("impirmir");
    String estilo = (String) request.getAttribute("estilo");
    String url = (String) request.getAttribute("url");
    String action = (String) request.getAttribute("nextAction");
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
            <h3 class="<%= estilo != null ? estilo : "" %>"><%= mensaje != null ? mensaje : "" %></h3>
            <p>
                <a href="<%= request.getContextPath() %>/<%=url%>?action=<%= action %>">sigue buscando en Home</a>
            </p>
     
        </center>
    </body>
</html>
