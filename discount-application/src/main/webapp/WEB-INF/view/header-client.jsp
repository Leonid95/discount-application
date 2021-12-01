<header class="custom-header">
	<div class="container">
		<ul class="custom-menu">
			<li class="custom-menu-item"><a
				href="${pageContext.request.contextPath}">Home</a></li>
			<li class="custom-menu-item"><a
				href="${pageContext.request.contextPath}/admin/discount-list">All
					discounts</a></li>
			<li class="custom-menu-item"><form:form
					action="${pageContext.request.contextPath}/logout" method="POST">

					<input type="submit" class="logout-button" value="Logout" />
				</form:form></li>
		</ul>
	</div>

</header>