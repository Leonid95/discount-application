

<header class="custom-header">
	<div class="container">
		<ul class="custom-menu">
			<li class="custom-menu-item"><a
				href="${pageContext.request.contextPath}">Home</a></li>
			<li class="custom-menu-item"><a
				href="${pageContext.request.contextPath}/admin/discount-list">All
					discounts</a></li>
			<li class="custom-menu-item"><a
				href="${pageContext.request.contextPath}/admin/user-list">Users</a></li>
			<li class="custom-menu-item"><a
				href="${pageContext.request.contextPath}/admin/category-list">Categories</a></li>
			<li class="custom-menu-item">
			
			<form:form action="${pageContext.request.contextPath}/logout" method="POST">

					<input type="submit" class="logout-button" onclick="if(!confirm('Are you sure you want to log out?')) return false;" value="Logout" />
		   </form:form></li>
		</ul>
	</div>

</header>