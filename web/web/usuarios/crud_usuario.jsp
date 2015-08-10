<%@page import="quejasYreclamos.modelo.entidades.Usuarios"%>
<%
    String  mensaje = (String) request.getAttribute("mensaje");
    String action = (String) request.getAttribute("action");
    Usuarios user = (Usuarios) request.getAttribute("user");
    Integer nextID = (Integer) request.getAttribute("next_Id");
    nextID = (nextID != null) ? nextID.intValue() : 0 ;
%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<style>
#formAgregarUsuario{
	margin:0 auto; border:1px solid #888; width:80%; text-align: center;
}
#formAgregarUsuario  form table {
	width: 100%;
}
#formAgregarUsuario  form table tr td label{
	width: 30%;
	text-align: left;
	display: inline-block;
}
#formAgregarUsuario  form table tr td input{
	width: 70%;
}
</style>
</head>
<body id="agregarUsuario">
		<center><h1><b>Sitio de Peticiones, reclamos  y Quejas</b></h1></center>
	<hr style="width:50%;">
	<div id="formAgregarUsuario">
		
		<p>formulario para administrar usuarios</p>
		<form action="/quejasYreclamos/ServletUsuarios" method="POST" name="FormAgregar">
			<table>
				<tr>
					<td><label for="">ID</label></td>
                                        <td>
                                            <input type="number" name="id" 
                                            value="<%= ( user != null) ? user.getUsuariosId() : nextID %>" 
                                            <%= (action.equals("mos_agregar") )?"readonly":"" %>
                                            />
                                        </td>
				</tr>
				<tr>
					<td><label for="">Cedula</label></td>
					<td>
                                            <input type="text" name="cedula" 
                                                   value="<%= (user != null) ? user.getCedula() : "" %>"
                                            <%= ( action.equals("mos_agregar") || action.equals("mos_buscar"))? "":"readonly" %>
                                            />
                                        </td>
				</tr>
				<tr>
					<td><label for="">Password</label></td>
					<td>
                                            <input type="password" name="password"
                                            value="<%= (user != null) ? user.getPassword(): "" %>"       
                                            <%= (action.equals("mos_agregar") || action.equals("mos_buscar"))?"":"readonly" %>
                                            />
                                        </td>
				</tr>
				<tr>
					<td><label for="">Nombre</label></td>
					<td>
                                            <input type="text" name="nombre" 
                                                   value="<%= (user != null) ? user.getNombre() : "" %>"
                                            <%= (action.equals("mos_agregar") || action.equals("mos_buscar"))?"":"readonly" %>
                                            />
                                        </td>
				</tr>
				<tr>
					<td><label for="">Apellido</label></td>
					<td>
                                            <input type="text" name="apellido"
                                                   value="<%= (user != null) ? user.getApellido() : "" %>"
                                            <%= (action.equals("mos_agregar") || action.equals("mos_buscar"))?"":"readonly" %>
                                            />
                                        </td>
				</tr>
				<tr>
					<td><label for="">Email</label></td>
					<td>
                                            <input type="text" name="email"
                                                   value="<%= (user != null) ? user.getEmail() : "" %>"
                                            <%= (action.equals("mos_agregar") || action.equals("mos_buscar"))?"":"readonly" %>
                                            />
                                        </td>
				</tr>
				<tr>
					<td><label for="">Telefono</label></td>
					<td>
                                            <input type="text" name="telefono"
                                                   value="<%= (user != null) ? user.getTelefono() : "" %>"
                                            <%= (action.equals("mos_agregar") || action.equals("mos_buscar"))?"":"readonly" %>
                                            />
                                        </td>
				</tr>
				<tr>
					<td colspan="2">
                                            <input type="submit" name="action" value="guardar" 
                                            <%= (action.equals("mos_agregar"))? "":"disabled" %>
                                            />
                                            <input type="submit" name="action" value="buscar"
                                             <%= (action.equals("mos_editar") || action.equals("mos_buscar")  )? "":"disabled" %>         
                                            />
                                            <input type="submit" name="action" value="editar"
                                            <%= (action.equals("mos_editar") || action.equals("mos_buscar")  )? "":"disabled" %>       
                                            />
                                            <input type="submit" name="action" value="eliminar"
                                            <%= (action.equals("mos_eliminar")|| action.equals("mos_buscar")  )? "":"disabled" %>      
                                            />
                                            <button type="reset">Restaurar</button>
                                        </td>
				</tr>
			</table>
		</form>
		
	</div>
        <div>
            <p> <span><%= mensaje != null ? mensaje : "nada"%> </span> </p>
        </div>
</body>
</html>