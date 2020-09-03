<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>
	<div class="row">
		<h1>Estadística de Noel Gonzalez</h1>
	</div>

	<div class="row">
		
			<div class="col-sm-4">
				<div class="card card-stats">
					<div class="card-header" data-background-color="orange">
						<i class="material-icons">equalizer</i>
					</div>
					<div class="card-content">
						<p class="category">Informe Mensual de Operaciones en Proyectos</p>
						<h3 class="card-title">10</h3>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="card card-stats">
					<div class="card-header" data-background-color="orange">
						<i class="material-icons">equalizer</i>
					</div>
					<div class="card-content">
						<p class="category">Hoja de Servicio</p>
						<h3 class="card-title">7</h3>
					</div>
				</div>
			</div>
	</div>
	<div class="row">
		<input class="btn btn-fill btn-success" type="button" value="Regresar" onclick="submitForm()">
	</div>
	
</body>