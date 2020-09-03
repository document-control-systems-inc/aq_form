<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>

	<div class="card">
		<div class="form-horizontal">
			<div class="card-header card-header-text"
				data-background-color="orange">
				<h4 class="card-title">Formulario Generado</h4>
			</div>
			<div class="card-content">
				<iframe src="${urlForms}" width="100%" height="500px"></iframe>
				<br> <br>
				<div class="col-sm-10">
					<div class="col-md-3"></div>
					<div class="col-md-4">
						<div class="form-group form-button">
							<form action="${pageContext.request.contextPath}/form" method="post">
								<input type="submit" class="btn btn-fill btn-warning"
									value="Nueva forma">
							</form>
						</div>
					</div>
					<div class="col-md-5"></div>
				</div>
			</div>
		</div>
	</div>
</body>