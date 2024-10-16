<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>TELA DE ERRO</title>
</head>
<body>
	<h2>OPSS, OCORREU UM ERRO. ENTRE EM CONTATO COM A EQUIPE DE
		SUPORTE.</h2>
	<%
	out.print(request.getAttribute("msg"));
	%>
</body>
</html>