<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>
	<c:if test="${backButton == true}">
		<div class="row">
			<form method="post" action="${pageContext.request.contextPath}/dashboardForms">
			    <input class="btn btn-fill btn-info" value="Regresar" type="submit" />
			</form>
			<form method="post" action="${pageContext.request.contextPath}/statsForms">
				<input type="hidden" id="idEmployee" name="idEmployee" value="${idEmployee}">
				<button rel="tooltip" class="btn btn-info btn-round">
			    	Datos de Formas de ${fullName} <i class="material-icons">grid_on</i>
			  	</button>
			</form>
		</div>
		
	</c:if>
	<div class="row">
		<div class="card">
				<div class="card-header card-header-text" data-background-color="orange">
					<h4 class="card-title">Estadísticas de ${fullName}</h4>
				</div>
				<div class="card-content table-responsive">
					<c:forEach var="formas" items="${formas}" varStatus="status">
						<div class="col-sm-6">
							<div class="card card-stats">
								<div class="card-header" data-background-color="orange">
									<i class="material-icons">equalizer</i>
								</div>
								<div class="card-content">
									<p class="category">${formas.label}</p>
									<h3 class="card-title">${formas.data}</h3>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
		</div>
	</div>
	<c:if test="${isManager == true}">
		<div class="row">
			<div class="card">
				<div class="card-header card-header-text" data-background-color="orange">
					<h4 class="card-title">Estadísticas Grupo</h4>
				</div>
				<div class="card-content table-responsive">
					<table class="table table-hover">
						<thead class="text-warning">
							<tr>
								<th>User ID</th>
								<th>Nombre</th>
								<th>Acciones</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="employees" items="${employees}" varStatus="status">
								<tr>
									<td>${employees.username}</td>
									<td>${employees.name}</td>
									<td>
										<form method="post" action="${pageContext.request.contextPath}/statsForms">
											<input type="hidden" id="idEmployee" name="idEmployee" value="${employees.username}">
											<button rel="tooltip" class="btn btn-info btn-round">
										    	<i class="material-icons">grid_on</i>
										  	</button>
										</form>
									  	<form method="post" action="${pageContext.request.contextPath}/dashboardForms">
											<input type="hidden" id="idEmployee" name="idEmployee" value="${employees.username}">
										    <button rel="tooltip" class="btn btn-warning btn-round">
										    	<i class="material-icons">poll</i>
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
	</c:if>
</body>