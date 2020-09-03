<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>

	<div class="container">
		<div class="row">
			<div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
					<div class="card card-login card-hidden">
						<br>
						<p class="category text-center">
							<img src="${pageContext.request.contextPath}/assets/img/logo-janitorial.png" style="width: 120px">
						</p>
							<div class="card-content">
								<div class="text-center">
									<br>
									<span class="label label-danger">${fail}</span>
								</div>
							</div>
							<br>
							<br>
							<br>
							
							<div class="footer text-center">
								<button onclick="window.location.href='${pageContext.request.contextPath}/index';"
									class="btn btn-info">Inicio</button>
							</div>
					</div>
			</div>
		</div>
	</div>
	
	<script>
		$('#submitLink').on('click', function(evt){
			evt.preventDefault();
			console.log("To send form");
			$(this).closest('form').get(0).submit();
		});
	</script>
</body>