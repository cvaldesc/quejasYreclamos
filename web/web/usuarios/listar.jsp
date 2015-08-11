<%-- 
    Document   : listar
    Created on : 29-07-2015, 04:44:48 PM
    Author     : cvaldes
--%>
<%@page import="java.util.Iterator"%>
<%@page import="quejasYreclamos.modelo.entidades.Usuarios"%>
<%@page import="java.util.List"%>
<%
    String mensaje = (String) request.getParameter("mensaje");
    List<Usuarios> users = (List<Usuarios>) request.getAttribute("Listados_Usuarios");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <center>
        <h1>Listado de Usuarios </h1>
        <hr>
        <table>
            <h3>Usuarios Listados</h3>
            <thead>
                <th>
                    <p><b>#</b></p>
                </th>
                <th>
                    <p><b>ID</b></p>
                </th>
                <th>
                    <p><b>Nombre Completo</b></p>
                </th>
                <th>
                    <p><b>Ver Mas</b></p>
                </th>
                <th>
                    <p><b>Editar</b></p>
                </th>
                <th>
                    <p><b>Eliminar</b></p>
                </th>
            </thead>
            <tbody>

                <%
                int cont = 0;
                Iterator<Usuarios> iterator = users.iterator();
                while( iterator.hasNext() == true){
                    Usuarios user = iterator.next();
                    cont += 1;
                %>
                <tr>
                    <td>
                        <p><%= cont %></p>
                    </td>
                     <td>
                        <p><%= user.getUsuariosId() %></p>
                    </td>
                    <td>
                        <p><%= user.getNombre()+ " "+ user.getApellido() %></p>
                    </td>
                    <td>
                        <p>
                            <a href="<%= request.getContextPath() %>/ServletUsuarios?action=buscar&id=<%= user.getUsuariosId()%>">ver mas</a>
                        </p>
                    </td>
                    <td>
                        <p>
                             <a href="<%= request.getContextPath() %>/ServletUsuarios?action=buscar&id=<%= user.getUsuariosId()%>">Editar</a>
                        </p>
                    </td>
                    <td>
                        <p>
                             <a href="<%= request.getContextPath() %>/ServletUsuarios?action=eliminar_listado&id=<%= user.getUsuariosId()%>">Eliminar</a>
                        </p>
                    </td>
                </tr>
                <%
                }
                %>
            </tbody>
            <tfoot>
               
            </tfoot>
        </table>
    </center>
    </body>
</html>
