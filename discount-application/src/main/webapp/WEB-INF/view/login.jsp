<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!doctype html>

<html>

<head>

<title>Login Page</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link href="${pageContext.request.contextPath}/resources/css/styles.css"
	rel="stylesheet">

</head>

<body>

	<%@ include file="header-login.jsp"%>

	<div class="container main-content">

		<c:if test="${param.logout != null}">
			<div class="logout-message">You've been logged out.</div>
		</c:if>

		<form:form method="POST"
			action="${pageContext.request.contextPath}/authenticateTheUser"
			class="custom-form">

			<label for="username">Username</label>
			<input type="text" name="username" placeholder="Username"
				class="form-control">

			<label for="password">Password</label>
			<input type="password" name="password" placeholder="Password"
				class="form-control">


			<button type="submit" class="btn">Login</button>

		</form:form>
	</div>

	<%@ include file="footer.jsp"%>

</body>
</html>