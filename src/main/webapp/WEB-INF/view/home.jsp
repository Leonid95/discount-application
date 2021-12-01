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

<meta name="viewport" content="width=device-width, initial-scale=1">

</head>

<body>

	<%@ include file="header-general.jsp"%>

	<div class="container main-content">
		<div class="content-section">
			<div class="custom-content-navigation">
				<div class="filter-title">Filter discounts by category</div>



				<select id="selectCategory">
					<c:forEach var="category" items="${categories}">
						<c:set var="isSelected"
							value="${(not empty param.categoryId and param.categoryId == category.id) ? 'selected' : ''}" />


						<option value="${category.id}" title="${category.description}"
							${isSelected}>${category.title}</option>

					</c:forEach>
				</select> <a id="filterLink" class="filterButton"
					href="${pageContext.request.contextPath}">Apply</a>

				<c:if test="${not empty param.categoryId}">
					<a id="removeFilter" class="filterButton"
						href="${pageContext.request.contextPath}">Remove filters</a>
				</c:if>
			</div>


			<div class="discount-list">


				<c:forEach var="discount" items="${discounts}">

					<div class="font-cursive object-container discount-container">

						<div class="object-field discount-field">
							<strong>${discount.title}</strong>
						</div>
						<div class="object-field discount-field">
							<strong>${discount.description}</strong>
						</div>
						<div class="object-field discount-field">
							Discount price: <strong>${discount.discountPrice} PLN</strong>
						</div>
						<div class="object-field discount-field">
							Regular price: <strong>${discount.regularPrice} PLN</strong>
						</div>
						<div class="object-field discount-field">
							Discount is valid since <strong><fmt:formatDate
									value="${discount.startDate}" pattern="dd-MM-yyyy" /></strong> until <strong><fmt:formatDate
									value="${discount.endDate}" pattern="dd-MM-yyyy" /></strong>
						</div>
						<div class="object-field discount-field">
							Discount category: <strong>${discount.category.title}</strong>
						</div>
					</div>

					<br>
					<br>

				</c:forEach>
			</div>
		</div>
	</div>

	<%@ include file="footer.jsp"%>


</body>

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



</html>