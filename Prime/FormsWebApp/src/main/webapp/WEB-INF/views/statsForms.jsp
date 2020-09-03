<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>
	<c:if test="${backButton == true}">
		<div class="row">
			<form method="post" action="${pageContext.request.contextPath}/dashboardForms">
			    <input class="btn btn-fill btn-info" value="Regresar" type="submit" />
			</form>
			<form method="post" action="${pageContext.request.contextPath}/dashboardForms">
				<input type="hidden" id="idEmployee" name="idEmployee" value="${idEmployee}">
			    <button rel="tooltip" class="btn btn-info btn-round">
			    	Estadísticas de ${fullName} <i class="material-icons">poll</i>
			  	</button>
			</form>
		</div>
	</c:if>
	<div class="row">
		<div class="col-md-12">
			<div class="card">
				<div class="card-header card-header-icon"
					data-background-color="orange">
					<i class="material-icons">assignment</i>
				</div>

				<div class="card-content">
					<h4 class="card-title">Información sobre Contratos de ${fullName}</h4>
					<div class="toolbar">
						<!--        Here you can write extra buttons/actions for the toolbar              -->
					</div>
					<div class="material-datatables">
						<table id="datatables"
							class="table table-striped table-no-bordered table-hover"
							>
							<thead>
								<tr>
									<th>Forma</th>
									<th>Creador</th>
									<th>Fecha</th>
									<th class="text-right">Descarga</th>
								</tr>
							</thead>
							<tfoot>
								<tr>
									<th>Forma</th>
									<th>Creador</th>
									<th>Fecha</th>
									<th>Acciones</th>
								</tr>
							</tfoot>
							<tbody>
								<c:forEach var="data" items="${datosForma}" varStatus="status">
									<tr>
										<td>${data.template}</td>
										<td>${data.createdBy}</td>
										<td>${data.createdOn}</td>
										<td class="td-actions text-right">
										<c:if test="${data.template == 'Requisición de Materiales'}">
											<form target="_blank" method="post" action="${pageContext.request.contextPath}/downloadReport">
												<input type="hidden" id="pdf_id" name="pdf_id" value="${data.id}">
												<input type="hidden" id="createdBy" name="createdBy" value="${data.createdBy}">
					                            <button rel="tooltip" class="btn btn-primary btn-round">
					                              <i class="material-icons">description</i>
					                            </button>
					                        </form>
					                    </c:if> 
											<form method="post" action="${pageContext.request.contextPath}/editForm">
												<input type="hidden" id="pdf_id" name="pdf_id" value="${data.id}">
												<input type="hidden" id="createdBy" name="createdBy" value="${data.createdBy}">
					                            <button rel="tooltip" class="btn btn-success btn-round">
					                              <i class="material-icons">create</i>
					                            </button>
					                        </form>
											<form target="_blank" method="post" action="${pageContext.request.contextPath}/downloadpdf">
												<input type="hidden" id="pdf_id" name="pdf_id" value="${data.id}">
												<input type="hidden" id="createdBy" name="createdBy" value="${data.createdBy}">
					                            <button rel="tooltip" class="btn btn-info btn-round">
					                              <i class="material-icons">picture_as_pdf</i>
					                            </button>
					                        </form>
					                        <form target="_blank" method="post" action="${pageContext.request.contextPath}/sendEmail">
												<input type="hidden" id="pdf_id" name="pdf_id" value="${data.id}">
												<input type="hidden" id="createdBy" name="createdBy" value="${data.createdBy}">
					                            <button type="button" rel="tooltip" class="btn btn-default" title="Enviar" onclick="showSwal('${data.id}')">
		                                            <i class="material-icons">email</i>
		                                        </button>
					                        </form>
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

    function showSwal(idPdf, createdBy){
    	swal({
            title: 'Envío por Correo Electrónico',
            html: '<div class="form-group">' +
			  '<input id="input-field" type="text" class="form-control" />' +
			  '</div>',
            showCancelButton: true,
            cancelButtonText: 'Cancelar',
            confirmButtonText: 'Enviar',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger',
            showCloseButton: true,
            allowOutsideClick: false,
            buttonsStyling: false,      
            preConfirm: () => {    
                //validate the front here if you wish (valid is the variable to say on the front is ok)
                var valid = true;
                var correo = $('#input-field').val();
                //ENVIO DE DATOS PARA O SEERVIDOR
                if(valid)
                {
            		swal.close();
            		$.getJSON('${pageContext.request.contextPath}/sendEmail?email=' + correo + '&idPdf=' + idPdf + '&createdBy=' + createdBy, '', function (data, textStatus, jqXHR) {});
                } else {
                }
                // This way the sweety does not date
                return false;
            }
        });  
    	}
	</script>



	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#datatables')
									.DataTable(
											{
												"pagingType" : "full_numbers",
												"order": [[ 2, "desc" ]],
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