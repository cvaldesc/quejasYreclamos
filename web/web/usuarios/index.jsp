<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<center><h1><b>Sitio de Peticiones, reclamos  y Quejas</b></h1></center>
	<hr style="width:50%;">
	<center>
		<p>Menu para operaciones de usuarios</p>
		<ul>
                    <li><a href=" <%=request.getContextPath() %>/ServletUsuarios?action=sol_agregar">Agregar Usuario</a></li>
			<li><a href="consultar.jsp">Consultar Usuario</a></li>
			<li><a href="editar.jsp">Editar Usuario</a></li>
			<li><a href="eliminar.jsp">Eliminar Usuario</a></li>
			<li><a href="listar.jsp">Listar Usuario</a></li>
		</ul>
	</center>
</body>
</html>
