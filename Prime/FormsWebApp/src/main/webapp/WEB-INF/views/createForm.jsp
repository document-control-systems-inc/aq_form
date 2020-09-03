<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>

	<div class="card">
		<div class="form-horizontal">
			<div class="card-header card-header-text"
				data-background-color="orange">
				<h4 class="card-title">Formularios</h4>
			</div>
			<div class="card-content">
				<form name="frmFormulario"
					action="${pageContext.request.contextPath}/form" method="post"
					onsubmit="return validateForm()">
					<div class="row">
						<label class="col-sm-2 label-on-left">Formularios
							Disponibles:</label>

						<div class="col-sm-4 col-sm-offset-1 checkbox-radios">

							<c:forEach var="formas" items="${formas}" varStatus="status">

								<div class="radio">
									<label> <input type="radio" value="${formas.name}"
										name="rdoFormulario" id="rdoFormulario">
										${formas.label}
									</label>
								</div>

							</c:forEach>
						</div>
					</div>
					<br> <br>
					<div class="col-sm-10">
						<div class="col-md-3"></div>
						<div class="col-md-4">
							<div class="form-group form-button">
								<input type="submit" class="btn btn-fill btn-warning"
									value="Iniciar Forma">
							</div>
						</div>
						<div class="col-md-5"></div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
		function validateRadio(radios) {
			for (i = 0; i < radios.length; ++i) {
				if (radios[i].checked)
					return true;
			}
			return false;
		}

		function validateForm() {
			if (validateRadio(document.forms["frmFormulario"]["rdoFormulario"])) {
				return true;
			}

			else {
				alert('Debe seleccionar una forma');
				return false;
			}
		}
	</script>

</body>