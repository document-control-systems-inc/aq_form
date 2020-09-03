<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>

	<div class="card">
		<div class="form-horizontal">
			<div class="card-header card-header-text"
				data-background-color="orange">
				<h4 class="card-title">Formulario de Peticiones</h4>
			</div>
			<div class="card-content">
				<form id="frmFormulario" name="frmFormulario"
					action="${pageContext.request.contextPath}/pdf" method="post"
					enctype="multipart/form-data">
					<input type="hidden" name="template" value="homeland">
					<div id="page1" style="display: block">
						<div class="row">
							<div class="col-sm-6 col-lg-3">
							  <label class="label-on-left">Número de Petición:</label>
							</div>
							<div class="col-sm-6 col-lg-3">
							  <input type="text" class="form-control" id="numPeticion" name="numPeticion" value=""  title="Número de Petición es obligatorio">
							</div>
							
						</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Account:</label>
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
									  <label class="label-on-left">Grant:</label>
									</div>
									<div class="col-sm-6">
									  <input type="text" class="form-control" name="tipoOperacion" id="tipoOperacion" value="Grant"  title="Grant es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Subvención:</label>
									</div>
									<div class="col-sm-6">
									  <select class="form-control" name="subvencion" id="subvencion"  title="Subvención es obligatorio">
										<option disabled selected>Seleccione una opción</option>
										<option value="EMW-2016-SS-00031-S01">1. EMW-2016-SS-00031-S01</option>
										<option value="EMW-2017-SS-00031-S01">2. EMW-2016-SS-00031-S01</option>
									  </select>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Contrato:</label>
									</div>
									<div class="col-sm-6">
									  <input type="text" class="form-control" name="numContrato" id="numContrato" value=""  title="Contrato es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Petición por:</label>
									</div>
									<div class="col-sm-6">
									  <select class="form-control" name="motivoPeticion" id="motivoPeticion"  title="Petición por es obligatorio">
										<option disabled selected>Seleccione una opción</option>
										<option value="Adelanto">1. Adelanto</option>
										<option value="Reembolso">2. Reembolso</option>
									  </select>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Por concepto:</label>
									</div>
									<div class="col-sm-6">
									  <select class="form-control" name="concepto" id="concepto"  title="Por concepto es obligatorio">
										<option disabled selected>Seleccione una opción</option>
										<option value="Orden de Compra">1. Orden de Compra</option>
										<option value="Contrato">2. Contrato</option>
										<option value="Gastos de Viaje">3. Gastos de Viaje</option>
										<option value="Combustible">4. Combustible</option>
										<option value="Horas Extra">5. Horas Extra</option>
									  </select>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Item Solicitado:</label>
									</div>
									<div class="col-sm-6">
									  <input type="text" class="form-control" name="itemSolicitado" id="itemSolicitado" value=""  title="Item solicitado es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Justificación:</label>
									</div>
									<div class="col-sm-6">
									  <input type="file" name="justificacion" id="justificacion" />
									</div>
								</div>
							</div>
							<div class="col-sm-6 ">
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Cuantía Solicitada:</label>
									</div>
									<div class="col-sm-6">
									  <input type="text" class="form-control text-center" name="cuantiaSolicitada" id="cuantiaSolicitada" number="true" value=""  title="Cuantía Solicitada es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Cuantía Aprobada:</label>
									</div>
									<div class="col-sm-6">
									  <input type="text" class="form-control text-center" name="cuantiaAprobada" id="cuantiaAprobada" number="true" value=""  title="Cuantía aprobada es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Cuantía Ajustada:</label>
									</div>
									<div class="col-sm-6">
									  <input type="text" class="form-control text-center" name="cuantiaAjustada" id="cuantiaAjustada" number="true" value=""  title="Cuantía ajustada es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Peticionario:</label>
									</div>
									<div class="col-sm-6">
									  <input type="text" class="form-control" name="peticionario" id="peticionario" value="${nombreUsuario}"  title="Peticionario es obligatorio">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
									  <label class="label-on-left">Puesto:</label>
									</div>
									<div class="col-sm-6">
									  <input type="text" class="form-control" name="puesto" id="puesto" value="">
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
								<input class="btn btn-fill btn-warning" type="button" value="Siguiente" onclick="showPage('page1', 'page2')">
							</div>
						</div>
					</div>
					<div id="page2" style="display: none">
						<div class="row">
							<div class="card">
								<div class="card-content">
									<div class="table-responsive">
										<table class="table table-striped">
										  <thead class="text-warning">
											<tr>
											  <th class="text-center">#</th>
											  <th>Pre-Intervención Horas Extra</th>
											  <th>Adjunto</th>
											  <th></th>
											  <th>Otros</th>
											</tr>
										  </thead>
										  <tbody>
											<tr>
											  <td>
												<div class="form-check">
												  <label class="form-check-label">
													<input class="form-check-input" name="chk_solicitud_adelanto_fondos" id="chk_solicitud_adelanto_fondos" type="checkbox" value="" >
													<span class="form-check-sign">
													  <span class="check"></span>
													</span>
												  </label>
												</div>
											  </td>
											  <td>Solicitud de Adelanto de Fondos</td>
											  <td><input type="file" name="adj_solicitud_adelanto_fondos" id="adj_solicitud_adelanto_fondos" /></td>
											  <td>Required</td>
											  <td><input type="text" class="form-control" name="txt_solicitud_adelanto_fondos" id="txt_solicitud_adelanto_fondos" value=""></td>
											</tr>
											<tr>
											  <td>
												<div class="form-check">
												  <label class="form-check-label">
													<input class="form-check-input" name="chk_informe_horas_extras" id="chk_informe_horas_extras" type="checkbox" value="" >
													<span class="form-check-sign">
													  <span class="check"></span>
													</span>
												  </label>
												</div>
											  </td>
											  <td>Informe de Horas Extras</td>
											  <td><input type="file" name="adj_informe_horas_extras" id="adj_informe_horas_extras" /></td>
											  <td>Required</td>
											  <td><input type="text" class="form-control" name="txt_informe_horas_extras" id="txt_informe_horas_extras" value=""></td>
											</tr>
											<tr>
											  <td>
												<div class="form-check">
												  <label class="form-check-label">
													<input class="form-check-input" name="chk_hojas_asistencia" id="chk_hojas_asistencia" type="checkbox" value="" >
													<span class="form-check-sign">
													  <span class="check"></span>
													</span>
												  </label>
												</div>
											  </td>
											  <td>Hojas de Asistencia</td>
											  <td><input type="file" name="adj_hojas_asistencia" id="adj_hojas_asistencia" /></td>
											  <td>Required</td>
											  <td><input type="text" class="form-control" name="txt_hojas_asistencia" id="txt_hojas_asistencia" value=""></td>
											</tr>
											<tr>
											  <td>
												<div class="form-check">
												  <label class="form-check-label">
													<input class="form-check-input" name="chk_cheque_cancelado" id="chk_cheque_cancelado" type="checkbox" value="" >
													<span class="form-check-sign">
													  <span class="check"></span>
													</span>
												  </label>
												</div>
											  </td>
											  <td>Cheque Cancelado / Aviso de Crédito</td>
											  <td><input type="file" name="adj_cheque_cancelado" id="adj_cheque_cancelado" /></td>
											  <td>Required</td>
											  <td><input type="text" class="form-control" name="txt_cheque_cancelado" id="txt_cheque_cancelado" value=""></td>
											</tr>
											<tr>
											  <td>
												<div class="form-check">
												  <label class="form-check-label">
													<input class="form-check-input" name="chk_otros" id="chk_otros" type="checkbox" value="" >
													<span class="form-check-sign">
													  <span class="check"></span>
													</span>
												  </label>
												</div>
											  </td>
											  <td>Otros</td>
											  <td><input type="file" name="adj_otros" id="adj_otros" /></td>
											  <td>Not Required</td>
											  <td><input type="text" class="form-control" name="txt_otros" id="txt_otros" value=""></td>
											</tr>
											<tr>
											  <td>
												<div class="form-check">
												  <label class="form-check-label">
													<input class="form-check-input" name="chk_gerentes_budget" id="chk_gerentes_budget" type="checkbox" value="" >
													<span class="form-check-sign">
													  <span class="check"></span>
													</span>
												  </label>
												</div>
											  </td>
											  <td>Gerentes - Budget y Hoja de Cómputo</td>
											  <td><input type="file" name="adj_gerentes_budget" id="adj_gerentes_budget" /></td>
											  <td>Required</td>
											  <td><input type="text" class="form-control" name="txt_gerentes_budget" id="txt_gerentes_budget" value=""></td>
											</tr>
											<tr>
											  <td>
												<div class="form-check">
												  <label class="form-check-label">
													<input class="form-check-input" name="chk_finanzas_prifas" id="chk_finanzas_prifas" type="checkbox" value="" >
													<span class="form-check-sign">
													  <span class="check"></span>
													</span>
												  </label>
												</div>
											  </td>
											  <td>Finanzas - PRIFAS, Budget/H. Cómputo</td>
											  <td><input type="file" name="adj_finanzas_prifas" id="adj_finanzas_prifas" /></td>
											  <td>Required</td>
											  <td><input type="text" class="form-control" name="txt_finanzas_prifas" id="txt_finanzas_prifas" value=""></td>
											</tr>
										  </tbody>
										</table>
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
								<input class="btn btn-fill btn-warning" type="button" value="Anterior" onclick="showPage('page2', 'page1')">
								<input type="button" class="btn btn-fill btn-success" value="Generar Forma" onclick="validateForm()">
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
		      setFormValidation('#frmFormulario');
		    });
	</script>

</body>