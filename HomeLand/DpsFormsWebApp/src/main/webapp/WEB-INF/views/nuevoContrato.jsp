<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>

	<div class="card">
		<div class="form-horizontal">
			<div class="card-header card-header-text"
				data-background-color="orange">
				<h4 class="card-title">Nuevo Contrato</h4>
			</div>
			<div class="card-content">
				<form id="frmFormulario" name="frmFormulario"
					action="${pageContext.request.contextPath}/nuevoContratoResponse" method="post"
					enctype="multipart/form-data">
					<input type="hidden" name="template" value="homeland">
					<div id="page1" style="display: block">
						
						<div class="row">
							<div class="col-sm-6">
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Peticionario:</label>
									</div>
									<div class="col-sm-6">
									  <select class="form-control" name="account" id="account" title="Account es obligatorio"  >
										<option disabled selected>Seleccione una opción</option>
										<option disabled>SHSP</option>
										<option value="1. San Juan - hssanjuan">1. San Juan - hssanjuan</option>
										<option value="2. Carolina - hscarolina">2. Carolina - hscarolina</option>
										<option value="3. Humacao - hshumacao">3. Humacao - hshumacao</option>
										<option value="4. Caguas - hscaguas">4. Caguas - hscaguas</option>
										<option value="5. Ponce - hsponce">5. Ponce - hsponce</option>
										<option value="6. Mayaguez - hsmayaguez">6. Mayaguez - hsmayaguez</option>
										<option value="7. Aguada - hsaguada">7. Aguada - hsaguada</option>
										<option value="8. Vega Baja - hsvegabaja">8. Vega Baja - hsvegabaja</option>
										<option disabled>OPSG</option>
										<option value="1. Aguada - opaguada">1. Aguada - opaguada</option>
										<option value="2. Aguadilla - opaguadilla">2. Aguadilla - opaguadilla</option>
										<option value="3. DRNA - opdrna">3. DRNA - opdrna</option>
										<option value="4. FURA - opfura">4. FURA - opfura</option>
										<option value="5. Barceloneta - opbarceloneta">5. Barceloneta - opbarceloneta</option>
										<option value="6. Cabo Rojo - opcaborojo">6. Cabo Rojo - opcaborojo</option>
										<option value="7. Hatillo - ophatillo">7. Hatillo - ophatillo</option>
										<option value="8. Carolina - opcarolin">8. Carolina - opcarolina</option>
										<option value="9. Isabela - opisabela">9. Isabela - opisabela</option>
										<option value="10. Manati - opmanati">10. Manati - opmanati</option>
										<option value="11. Ponce - opponce">11. Ponce - opponce</option>
										<option value="12. Patillas - oppatillas*">12. Patillas - oppatillas*</option>
										<option value="13. Vega Baja - opvegabaja*">13. Vega Baja - opvegabaja*</option>
										<option value="14. Guánica - opguanica*">14. Guánica - opguanica*</option>
										<option value="15. Salinas - opsalinas*">15. Salinas - opsalinas*</option>
										<option disabled>EMPG</option>
										<option value="1. NMEAD - empgnmead1">1. NMEAD - empgnmead1</option>
										<option value="2. NMEAD - empgnmead2">2. NMEAD - empgnmead2</option>
										<option disabled>BombSquad/option>
										<option value="1. policia - bqpolicia">1. policia - bqpolicia</option>
										<option disabled>Fusion Center Group</option>
										<option value="1. Fusion Center - fcdsp">1. Fusion Center - fcdsp</option>
										<option disabled>EDSA Group</option>
										<option value="1. NMEAD - edsanmead">1. NMEAD - edsanmead</option>
									  </select>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Subvención:</label>
									</div>
									<div class="col-sm-6">
									<input type="text" class="form-control" name="subvencion" id="subvencion" value=""  title="Subvención es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Contrato:</label>
									</div>
									<div class="col-sm-6">
									  <input type="file" name="fileContrato" id="fileContrato" />
									</div>
								</div>
							</div>
							<div class="col-sm-6 ">
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
							</div>
						</div>
						<div class="row">
							<div class="col-sm-10">
								<br>
								<br>
							</div>
							<div class="col-sm-10">
								<input type="button" class="btn btn-fill btn-success" value="Crear Contrato" onclick="validateForm()">
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