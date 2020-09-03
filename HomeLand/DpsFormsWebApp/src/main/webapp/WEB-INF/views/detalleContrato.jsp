<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<body>

	<div class="card">
		<div class="form-horizontal">
			<div class="card-header card-header-text"
				data-background-color="orange">
				<h4 class="card-title">Detalle Contrato</h4>
			</div>
			<div class="card-content">
				<div id="page1" style="display: block">
					<form id="frmFormulario" name="frmFormulario"
						action="${pageContext.request.contextPath}/updateContratoResponse"
						method="post" enctype="multipart/form-data">
						<input type="hidden" id="idContrato" name="idContrato"
							value="${contrato.id}">
						<div class="row">
							<div class="col-sm-6">
								<div class="row">
									<div class="col-sm-6">
										<label class="label-on-left">Peticionario:</label>
									</div>
									<div class="col-sm-6">
										<select class="form-control" name="account" id="account"
											title="Account es obligatorio">
											<option disabled selected>Seleccione una opción</option>
											<c:forEach var="account" items="${infoAccounts}"
												varStatus="status">
												<c:if test="${account.isDisable}">
													<option disabled>${account.description}</option>
												</c:if>
												<c:if test="${!account.isDisable}">
													<c:if test="${account.value == contrato.peticionario}">
														<option value="${account.value}" selected>${account.description}</option>
													</c:if>
													<c:if test="${account.value != contrato.peticionario}">
														<option value="${account.value}">${account.description}</option>
													</c:if>
												</c:if>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<label class="label-on-left">Subvención:</label>
									</div>
									<div class="col-sm-6">
										<input type="text" class="form-control" name="subvencion"
											id="subvencion" value="${contrato.subvencion}"
											title="Subvención es obligatorio">
									</div>
								</div>
							</div>
							<div class="col-sm-6 ">
								<div class="row">
									<div class="col-sm-6">
										<label class="label-on-left">Monto:</label>
									</div>
									<div class="col-sm-6">
										<input type="number" class="form-control text-center"
											name="monto" id="monto" number="true"
											value="${contrato.monto}" title="Monto es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<label class="label-on-left">Vigencia:</label>
									</div>
									<div class="col-sm-6">
										<input type="text" class="form-control datepicker"
											name="vigencia" id="vigencia" value="${contrato.vigencia}"
											title="Vigencia es obligatorio">
									</div>
								</div>
							</div>
						</div>
					</form>

					<div class="row">
						<div class="col-sm-10">
							<h3>Enmiendas</h3>
							<div class="toolbar">
						<form method="post" action="${pageContext.request.contextPath}/nuevaEnmienda">
							<input type="hidden" id="idContrato" name="idContrato"
							value="${contrato.id}">
							<button rel="tooltip" class="btn btn-warning btn-round">
								<i class="material-icons">add</i> Agregar Enmienda
							</button>
						</form>
						
						<!--        Here you can write extra buttons/actions for the toolbar              -->
					</div>
						</div>
						<div class="col-sm-12">
							<div class="material-datatables">
								<table id="datatables"
									class="table table-striped table-no-bordered table-hover">
									<thead>
										<tr>
											<th>Fecha Creación</th>
											<th>Estatus</th>
											<th>Monto</th>
											<th>Vigencia</th>
											<th>Fecha</th>
											<th>Comentario</th>
											<th class="text-right">Descarga</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>Fecha Creación</th>
											<th>Estatus</th>
											<th>Monto</th>
											<th>Vigencia</th>
											<th>Fecha</th>
											<th>Comentario</th>
											<th class="text-right">Detalle</th>
										</tr>
									</tfoot>
									<tbody>
										<c:forEach var="enmienda" items="${contrato.enmiendas}" varStatus="status">
											<tr>
												<td>${enmienda.fechaCreacion}</td>
												<td>${enmienda.status}</td>
												<td>${enmienda.monto}</td>
												<td>${enmienda.vigencia}</td>
												<td>${enmienda.fecha}</td>
												<td>${enmienda.comentario}</td>
												<td class="td-actions text-right">
													<form target="_blank" method="post"
														action="${pageContext.request.contextPath}/downloadContrato">
														<input type="hidden" id="pdf_id" name="pdf_id"
															value="${enmienda.path}">
														<button rel="tooltip" class="btn btn-success btn-round">
															<i class="material-icons">picture_as_pdf</i>
														</button>
													</form>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>

					</div>

					<div class="row">
						<div class="col-sm-10">
							<input type="button" class="btn btn-fill btn-success"
								value="Actualizar Contrato" onclick="validateForm()">
							<form method="post"
								action="${pageContext.request.contextPath}/contratos">
								<input class="btn btn-fill btn-info" value="Regresar"
									type="submit" />
							</form>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>


	<script>
		function showPage(actualPage, nextPage) {
			document.getElementById(actualPage).style.display = 'none';
			document.getElementById(nextPage).style.display = 'block';
		}
	</script>


	<script>
		function setFormValidation(id) {

			$(id).validate(
					{
						highlight : function(element) {
							$(element).closest('.form-group').removeClass(
									'has-success').addClass('has-danger');
							$(element).closest('.form-check').removeClass(
									'has-success').addClass('has-danger');
						},
						success : function(element) {
							$(element).closest('.form-group').removeClass(
									'has-danger').addClass('has-success');
							$(element).closest('.form-check').removeClass(
									'has-danger').addClass('has-success');
						},
						errorPlacement : function(error, element) {
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
				frm.reset(); // Reset all form data
			}

			else {
				alert('Debe completar los campos obligatorios');
				return false;
			}
		}

		$(document).ready(function() {
			$('.datepicker').datetimepicker({
				format : 'DD/MM/YYYY',
				icons : {
					time : "fa fa-clock-o",
					date : "fa fa-calendar",
					up : "fa fa-chevron-up",
					down : "fa fa-chevron-down",
					previous : 'fa fa-chevron-left',
					next : 'fa fa-chevron-right',
					today : 'fa fa-screenshot',
					clear : 'fa fa-trash',
					close : 'fa fa-remove'
				}
			});

			setFormValidation('#frmFormulario');
		});
	</script>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#datatables')
									.DataTable(
											{
												"pagingType" : "full_numbers",
												"lengthMenu" : [
														[ 10, 25, 50, -1 ],
														[ 10, 25, 50, "Todos" ] ],
												"language" : {
													"sProcessing" : "Procesando...",
													"sLengthMenu" : "Mostrar _MENU_ registros",
													"sZeroRecords" : "No se encontraron resultados",
													"sEmptyTable" : "Ningún dato disponible en esta tabla",
													"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
													"sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
													"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
													"sInfoPostFix" : "",
													"sSearch" : "Buscar:",
													"sUrl" : "",
													"sInfoThousands" : ",",
													"sLoadingRecords" : "Cargando...",
													"oPaginate" : {
														"sFirst" : "Primero",
														"sLast" : "Último",
														"sNext" : "Siguiente",
														"sPrevious" : "Anterior"
													},
													"oAria" : {
														"sSortAscending" : ": Activar para ordenar la columna de manera ascendente",
														"sSortDescending" : ": Activar para ordenar la columna de manera descendente"
													}
												},
												colReorder : true,
												dom : "<'row'<'col-sm-6'l><'col-sm-6'f>>"
														+ "<'row'<'col-sm-12'tr>>"
														+ "<'row'<'col-sm-5'i><'col-sm-7'p>>",
											});
						});
	</script>

</body>