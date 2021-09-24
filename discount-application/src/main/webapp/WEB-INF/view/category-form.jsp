<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!doctype html>

<html>

<head>

<title>Category form</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

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
		
		
		<form:form action="${pageContext.request.contextPath}/admin/save-category" modelAttribute="category" class="custom-form">


			<c:if test="${not empty titleError}">
				<div class="error">${titleError}</div>

			</c:if>
			<div class="custom-feild">
				<form:label path="title">Title</form:label>
				<form:input type="text" path="title" />
			</div>
			
			<div class="custom-feild">
				<form:label path="description">Description</form:label>
				<form:input type="text" path="description" />
			</div>

			<!-- Associate data with the given category id -->
			<form:hidden path="id" />


			<form:button type="submit" class="btn btn-success">Save this category</form:button>

		</form:form>

	</div>
	
	<%@ include file="footer.jsp"%>

</body>
</html>