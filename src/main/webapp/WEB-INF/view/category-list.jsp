<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!doctype html>

<html>
<head>

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


	<div class="pt-4 pb-4 container main-content">
		<div class="content-section">

			<a class="add-button"
				href="${pageContext.request.contextPath}/admin/add-category">Add
				new category</a>


			<c:forEach var="category" items="${categories}">

				<div class="object-container category-container"
					style="margin-top: 20px; margin-bottom: 20px;">

					<div class="object-field category-field">Title:
						${category.title}</div>

					<div class="object-field category-field">Description:
						${category.description}</div>

					<c:url var="updateLink" value="/admin/update-category">
						<c:param name="categoryId" value="${category.id}" />
					</c:url>

					<c:url var="deleteLink" value="/admin/delete-category">
						<c:param name="categoryId" value="${category.id}" />
					</c:url>

					<div >
						<a class="custom-button update-button" href="${updateLink}">Update this category</a>
					</div>

					<div >
						<a class="custom-button delete-button" 
						onclick="if(!confirm('Are you sure you want to delete this category?')) return false;"
						href="${deleteLink}">Delete this category</a>
					</div>

				</div>


			</c:forEach>

			<c:if test="${categories.size() > 2}">
				<a class="add-button"
					href="${pageContext.request.contextPath}/admin/add-category">Add
					new category</a>
			</c:if>

		</div>
	</div>

	<%@ include file="footer.jsp"%>

</body>



</html>