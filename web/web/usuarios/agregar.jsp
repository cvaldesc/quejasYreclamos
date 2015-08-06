<%
    String  mensaje = (String)request.getAttribute("mensaje");

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
		
		<p>Datos para agregar un nuevo usuario</p>
		<form action="/quejasYreclamos/ServletUsuarios" method="POST" name="FormAgregar">
			<table>
				<tr>
					<td><label for="">ID</label></td>
					<td><input type="number" name="id"/></td>
				</tr>
				<tr>
					<td><label for="">Cedula</label></td>
					<td><input type="text" name="cedula"/></td>
				</tr>
				<tr>
					<td><label for="">Password</label></td>
					<td><input type="password" name="password"/></td>
				</tr>
				<tr>
					<td><label for="">Nombre</label></td>
					<td><input type="text" name="nombre"/></td>
				</tr>
				<tr>
					<td><label for="">Apellido</label></td>
					<td><input type="text" name="apellido"/></td>
				</tr>
				<tr>
					<td><label for="">Email</label></td>
					<td><input type="text" name="email"/></td>
				</tr>
				<tr>
					<td><label for="">Telefono</label></td>
					<td><input type="text" name="telefono"/></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" name="guardar" value="guardar"><button type="reset">Restaurar</button></td>
				</tr>
			</table>
		</form>
		
	</div>
        <div>
            <p> <span><%= mensaje != null ? mensaje : "nada"%> </span> </p>
        </div>
</body>
</html>