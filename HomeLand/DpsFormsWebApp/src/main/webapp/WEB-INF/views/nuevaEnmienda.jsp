<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>

	<div class="card">
		<div class="form-horizontal">
			<div class="card-header card-header-text"
				data-background-color="orange">
				<h4 class="card-title">Nueva Enmienda</h4>
			</div>
			<div class="card-content">
				<form id="frmFormulario" name="frmFormulario"
					action="${pageContext.request.contextPath}/nuevaEnmiendaResponse" method="post"
					enctype="multipart/form-data">
					<input type="hidden" id="idContrato" name="idContrato" value="${idContrato}">
					<div id="page1" style="display: block">
						
						<div class="row">
							<div class="col-sm-6 ">
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Estatus:</label>
									</div>
									<div class="col-sm-6">
									  <input type="text" class="form-control text-left" name="status" id="status" value=""  title="Estatus es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Monto:</label>
									</div>
									<div class="col-sm-6">
									  <input type="number" class="form-control text-center" name="monto" id="monto" number="true" value=""  title="Monto es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Vigencia:</label>
									</div>
									<div class="col-sm-6">
									  <input type="text" class="form-control datepicker" name="vigencia" id="vigencia" value=""  title="Vigencia es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Fecha:</label>
									</div>
									<div class="col-sm-6">
									  <input type="text" class="form-control datepicker" name="fecha" id="fecha" value=""  title="Fecha es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Comentarios:</label>
									</div>
									<div class="col-sm-6">
									  <input type="text" class="form-control text-left" name="comentario" id="comentario" value=""  title="Comentario es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Enmienda:</label>
									</div>
									<div class="col-sm-6">
									  <input type="file" name="fileContrato" id="fileContrato" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-10">
								<br>
								<br>
							</div>
							<div class="col-sm-10">
								<input type="button" class="btn btn-fill btn-success" value="Crear Enmienda" onclick="validateForm()">
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	
	<script>
		function showPage(actualPage, nextPage) {
			document.getElementById(actualPage).style.display='none';
			document.getElementById(nextPage).style.display='block';
		}
		</script>


	<script>
		function setFormValidation(id) {
			  
		      $(id).validate({
		        highlight: function(element) {
		          $(element).closest('.form-group').removeClass('has-success').addClass('has-danger');
		          $(element).closest('.form-check').removeClass('has-success').addClass('has-danger');
		        },
		        success: function(element) {
		          $(element).closest('.form-group').removeClass('has-danger').addClass('has-success');
		          $(element).closest('.form-check').removeClass('has-danger').addClass('has-success');
		        },
		        errorPlacement: function(error, element) {
		          $(element).closest('.form-group').append(error);
		        },
		      });
		    }

	
		function validateFormPeticion() {
			return true;
		}

		function validateForm() {
			if (validateFormPeticion()) {
				var frm = document.getElementsByName('frmFormulario')[0];
				frm.submit(); // Submit the form
				frm.reset();  // Reset all form data
			}

			else {
				alert('Debe completar los campos obligatorios');
				return false;
			}
		}

		
		$(document).ready(function() {
			$('.datepicker').datetimepicker({
				format: 'DD/MM/YYYY',
				icons: {
					time: "fa fa-clock-o",
					date: "fa fa-calendar",
					up: "fa fa-chevron-up",
					down: "fa fa-chevron-down",
					previous: 'fa fa-chevron-left',
					next: 'fa fa-chevron-right',
					today: 'fa fa-screenshot',
					clear: 'fa fa-trash',
					close: 'fa fa-remove'
				}
			 });

			
		      setFormValidation('#frmFormulario');
		    });
	</script>

</body>