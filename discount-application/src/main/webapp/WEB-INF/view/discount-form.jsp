<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!doctype html>

<html>

<head>

<title>Discount form</title>
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

	<%@ include file="header-client.jsp"%>

	<div class="container">


		<div class="container main-content">

			<form:form action="${pageContext.request.contextPath}/save-discount"
				modelAttribute="discount" class="custom-form">


				<c:if test="${not empty titleError}">
					<div class="error">Current discount title was already used,
						please try another one.</div>

				</c:if>
				<div class="custom-feild">
					<form:label path="title">Title</form:label>
					<form:input type="text" path="title" />
				</div>

				<div class="custom-feild">
					<form:label path="description">Description</form:label>
					<form:textarea rows="4" cols="40" type="text" path="description" />
				</div>

				<div class="custom-feild">
					<form:label path="discountPrice">Discount Price</form:label>
					<form:input type="number" min="0" max="10000" step="0.01"
						path="discountPrice" />
				</div>
				<div class="custom-feild">
					<form:label path="regularPrice">Regular Price: </form:label>
					<form:input type="number" min="0" max="10000" step="0.01"
						path="regularPrice" />
				</div>
				<div class="custom-feild">
					<form:label path="startDateText">Start Date: </form:label>
					<form:input type="date" pattern="yyyy-MM-dd" path="startDateText" />
				</div>


				<c:if test="${not empty dateError}">
					<div class="error">${dateError}</div>

				</c:if>
				<div class="custom-feild">
					<form:label path="endDateText">End Date: </form:label>
					<form:input type="date" pattern="yyyy-MM-dd" path="endDateText" />
				</div>

				<div class="custom-feild">
					<form:label path="status">Discount is visible: </form:label>
					<form:checkbox path="status" checked="checked" />
				</div>


				<!-- Associate data with the given discount's id -->
				<form:hidden path="id" />


				<form:hidden path="userId" value="${user.id}" />

				<div class="custom-feild">
				    <form:label path="category">Discount category: </form:label>
				    
					<form:select path="category">
						<c:forEach var="category" items="${categories}">
							<form:option value="${category.id}"
								title="${category.description}">${category.title}</form:option>
						</c:forEach>
					</form:select>
				</div>

				<form:button type="submit" class="btn btn-success">Save the discount</form:button>

			</form:form>

		</div>

	</div>

	<%@ include file="footer.jsp"%>

</body>
</html>