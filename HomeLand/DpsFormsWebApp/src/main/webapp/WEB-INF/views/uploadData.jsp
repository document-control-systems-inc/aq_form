<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>

	<div class="row">


		<div class="col-sm-4 col-sm-offset-1">
			<div class="fileinput fileinput-new text-center"
				data-provides="fileinput">
				<div class="fileinput-new thumbnail">
					<img src="assets/img/image_placeholder.jpg" alt="...">
				</div>
				<div class="fileinput-preview fileinput-exists thumbnail"></div>
				<div>
					<span class="btn btn-danger btn-round btn-file"> <span
						class="fileinput-new">Seleccionar Archivo CSV</span> <span
						class="fileinput-exists">Cambiar</span> <input type="file"
						name="filename" id="filename" />
					</span> <a href="#pablo" class="btn btn-danger btn-round fileinput-exists"
						data-dismiss="fileinput"><i class="fa fa-times"></i> Quitar</a>
				</div>
			</div>
			<button type="submit" rel="tooltip" class="btn btn-success"
				title="Cargar Datos" onclick="enviar();">
				<i class="material-icons">cloud_upload</i>
			</button>
		</div>
	</div>


	<script>
		function enviar() {
			window.location.replace('cargaDatosInfo.html');
		}
	</script>
</body>