<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>

<meta charset="ISO-8859-1">


<title>Treinamento JSP</title>


<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/stylesheet/styles.css">


</head>

<body>

	<div class="responsive-form">
		<h1>Entrar no Sistema</h1>
		<h3>Faça seu login</h3>
		<form class="form-container" action="ServletLogin" method="post">
			<input type="hidden" value="<%=request.getParameter("url")%>"
				name="url"> <label for="firstName"
				class="form-container-label"> Login: </label> <input type="text"
				id="firstName" name="login" placeholder="Digite seu nome"
				class="form-container-input" required> <label for="lastName"
				class="form-container-label"> Senha: </label> <input type="password"
				id="lastName" name="senha" placeholder="Digite sua senha"
				class="form-container-input" required>

			<button type="submit" class="form-container-button">Logar no
				Sistema</button>
		</form>



		<h4>${msg}</h4>

	</div>
</body>
</html>
