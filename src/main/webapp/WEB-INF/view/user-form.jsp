<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!doctype html>

<html>

<head>

<title>User form</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link href="${pageContext.request.contextPath}/resources/css/styles.css"
	rel="stylesheet">

</head>

<body>

	<%@ include file="header-admin.jsp"%>

	<div class="pt-4 container main-content">


		<form:form action="${pageContext.request.contextPath}/admin/save-user"
			modelAttribute="user" class="custom-form">


			<c:if test="${not empty usernameUsedError}">
				<div class="error">${usernameUsedError}</div>

			</c:if>
			<div class="custom-feild">
				<form:label path="username">Username</form:label>
				<form:input type="text" path="username" />
			</div>

			<c:if test="${not empty emptyPassword}">
				<div class="error">${emptyPassword}</div>
			</c:if>

			<c:if test="${empty update}">
				<div class="custom-feild">
					<form:label path="password">Password</form:label>
					<form:input type="text" path="password" />
				</div>
			</c:if>

			<c:if test="${not empty update}">

				<div class="custom-feild">
					<form:label path="newPassword">New Password</form:label>
					<form:input type="text" path="newPassword" />
				</div>
			</c:if>


			<c:if test="${not empty emailUsedError}">
				<div class="error">${emailUsedError}</div>
			</c:if>

			<c:if test="${not empty emailIncorrectError}">
				<div class="error">${emailIncorrectError}</div>
			</c:if>
			<div class="custom-feild">
				<form:label path="email">Email</form:label>
				<form:input type="text" path="email" />
			</div>

			<div class="custom-feild">
				<form:label path="firstName">First Name</form:label>
				<form:input type="text" path="firstName" />
			</div>

			<div class="custom-feild">
				<form:label path="lastName">Last Name</form:label>
				<form:input type="text" path="lastName" />
			</div>

			<div class="custom-feild">
				<form:label path="role">Role</form:label>

				<form:select path="role">
					<c:forEach var="role" items="${roles}">
						<c:set var="isSelected"
							value="${role.id == user.role.id ? 'selected' : ''}" />
						<form:option value="${role.id}" selected="${isSelected}">${role.name}</form:option>
					</c:forEach>
				</form:select>
			</div>

			<!-- Associate data with the given user's id -->
			<form:hidden path="id" />


			<form:button type="submit" class="btn btn-success">Save the user</form:button>

		</form:form>

	</div>

	<%@ include file="footer.jsp"%>

</body>
</html>