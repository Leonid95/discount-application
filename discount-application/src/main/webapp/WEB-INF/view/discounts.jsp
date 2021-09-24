<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!doctype html>

<html>
<head>

<link href="${pageContext.request.contextPath}/resources/css/styles.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script
	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

</head>


<body>

	<c:if test="${isUserAdmin}">
		<%@ include file="header-admin.jsp"%>
	</c:if>

	<c:if test="${not isUserAdmin}">
		<%@ include file="header-client.jsp"%>
	</c:if>

	<div class="container main-content">
		<div class="content-section">

			<div class="custom-content-navigation">
				<div class="filter-title">Filter discounts by category</div>
				<select id="selectCategory">
					<c:forEach var="category" items="${categories}">
						<option value="${category.id}" title="${category.description}">
							${category.title}</option>
					</c:forEach>
				</select>

				<c:if test="${isUserAdmin}">
					<a id="filterLink" class="filterButton"
						href="${pageContext.request.contextPath}/admin/discount-list">Apply</a>
				</c:if>
				<c:if test="${not isUserAdmin}">
					<a id="filterLink" class="filterButton"
						href="${pageContext.request.contextPath}/client/discount-list">Apply</a>
				</c:if>
			</div>

			<a class="add-button"
				href="${pageContext.request.contextPath}/add-discount">Add new
				discount</a>


			<c:forEach var="discount" items="${discounts}">

				<div class="object-container discount-container">
					<div class="object-field discount-field">Title: <strong>${discount.title}</strong></div>
					<div class="object-field discount-field">Description: <strong>${discount.description}</strong></div>
					<div class="object-field discount-field">Discount price: <strong>${discount.discountPrice}</strong> PLN</div>
					<div class="object-field discount-field">Regular price: <strong>${discount.regularPrice}</strong> PLN</div>
					<div class="object-field discount-field">Discount is valid since <fmt:formatDate
							value="${discount.startDate}" pattern="dd-MM-yyyy" /> till <fmt:formatDate
							value="${discount.endDate}" pattern="dd-MM-yyyy" /></div>
					<div class="object-field discount-field">Discount category: ${discount.category.title}</div>
					<div class="object-field discount-field">Discount is visible: ${discount.status}</div>

					<c:url var="updateLink" value="/update-discount">
						<c:param name="discountId" value="${discount.id}" />
					</c:url>

					<c:url var="deleteLink" value="/delete-discount">
						<c:param name="discountId" value="${discount.id}" />
					</c:url>

					<div>
						<a href="${updateLink}" class="custom-button update-button">Update discount</a>
					</div>

					<div>
						<a href="${deleteLink}"
							onclick="if(!confirm('Are you sure you want to delete this discount?')) return false;" class="custom-button delete-button">Delete
							discount</a>
					</div>
				</div>

				<br>
				<br>

			</c:forEach>

			<c:if test="${discounts.size() > 2}">
				<a class="add-button"
					href="${pageContext.request.contextPath}/add-discount">Add new
					discount</a>
			</c:if>

		</div>

	</div>

	<%@ include file="footer.jsp"%>

	<script type="text/javascript">
		$(document).ready(
				function() {
					changeLink();

					$("#selectCategory").on("change", function() {
						changeLink();
					});

					function changeLink() {
						let link = $("#filterLink").attr("href");

						link = link.split('?')[0];

						link = link + '?categoryId='
								+ $("#selectCategory :selected").val();

						$("#filterLink").attr("href", link);
					}
				});
	</script>



</body>



</html>