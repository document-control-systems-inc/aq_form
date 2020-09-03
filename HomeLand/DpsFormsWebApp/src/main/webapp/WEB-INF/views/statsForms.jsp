<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>

	<div class="row">
		<div class="col-md-12">
			<div class="card">
				<div class="card-header card-header-icon"
					data-background-color="orange">
					<i class="material-icons">assignment</i>
				</div>

				<div class="card-content">
					<h4 class="card-title">Información sobre Contratos</h4>
					<div class="toolbar">
						<!--        Here you can write extra buttons/actions for the toolbar              -->
					</div>
					<div class="material-datatables">
						<table id="datatables"
							class="table table-striped table-no-bordered table-hover"
							>
							<thead>
								<tr>
									<th>Num Petición</th>
									<th>Programa Grupo</th>
									<th>Creador</th>
									<th>Subvención</th>
									<th>Fecha</th>
									<th>Estatus</th>
									<th class="text-right">Descarga</th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th>Num Petición</th>
									<th>Programa Grupo</th>
									<th>Creador</th>
									<th>Subvención</th>
									<th>Fecha</th>
									<th>Estatus</th>
								</tr>
							</tfoot>
							<tbody>
								<c:forEach var="data" items="${datosForma}" varStatus="status">
									<tr>
										<td>${data.numPeticion}</td>
										<td>${data.account}</td>
										<td>${data.contrato}</td>
										<td>${data.cuantia}</td>
										<td>${data.peticionario}</td>
										<td>${data.createdOn}</td>
										<td class="td-actions text-right">
											<button rel="tooltip" class="btn btn-info btn-round">
					                              <i class="material-icons">chat_bubble_outline</i>
					                            </button>
											<button rel="tooltip" class="btn btn-info btn-round">
					                              <i class="material-icons">create</i>
					                            </button>
											<form target="_blank" method="post" action="${pageContext.request.contextPath}/downloadpdf">
												<input type="hidden" id="pdf_id" name="pdf_id" value="${data.id}">
					                            <button rel="tooltip" class="btn btn-info btn-round">
					                              <i class="material-icons">picture_as_pdf</i>
					                            </button>
					                        </form>
					                        <button rel="tooltip" class="btn btn-info btn-round">
					                              <i class="material-icons">attach_email</i>
					                            </button>    
				                          </td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<!-- end content-->
			</div>
			<!--  end card  -->
		</div>
		<!-- end col-md-12 -->
	</div>
	<!-- end row -->




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
												dom : "B<'row'<'col-sm-6'l><'col-sm-6'f>>"
														+ "<'row'<'col-sm-12'tr>>"
														+ "<'row'<'col-sm-5'i><'col-sm-7'p>>",
												buttons : [
														{
															extend : 'pdfHtml5',
															text : '<i class="fa fa-fw fa-file-pdf-o"></i> PDF'
														},
														{
															extend : 'excel',
															text : '<i class="fa fa-fw fa-file-excel-o"></i> Excel'
														},
														{
															extend : 'csv',
															text : '<i class="fa fa-fw fa-gears"></i> CSV',
															bom : 'true'
														},
														{
															extend : 'copy',
															text : '<i class="fa fa-fw fa-copy"></i> Copiar'
														}, ]
											});
						});
	</script>
</body>