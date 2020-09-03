<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>

	<div class="row">
		<c:forEach var="formas" items="${formas}" varStatus="status">
			<div class="col-sm-4">
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
	<div class="row">
    <div class="">
		<div class="card">
			<div class="card-header card-header-text" data-background-color="orange">
				<h4 class="card-title">Estadísticas Empleados</h4>
				<p class="category">Formas Generadas</p>
			</div>
			<div class="card-content table-responsive">
				<table class="table table-hover">
					<thead class="text-warning">
						<th>User ID</th>
						<th>Nombre</th>
						<th>Acciones</th>
					</thead>
					<tbody>
						<tr>
<td>centrodelcancer@pj</td>
<td>Annette Gaston</td>
<td>
<button type="button" rel="tooltip" class="btn btn-info btn-round">
    <i class="material-icons">grid_on</i>
  </button>
    <button type="button" rel="tooltip" class="btn btn-warning btn-round">
    <i class="material-icons">poll</i>
  </button>
</td>
</tr>

<tr>
<td>cocacola@pj</td>
<td>Noel Gonzalez</td>
<td>
<button type="button" rel="tooltip" class="btn btn-info btn-round">
    <i class="material-icons">grid_on</i>
  </button>
    <button type="button" rel="tooltip" class="btn btn-warning btn-round">
    <i class="material-icons">poll</i>
  </button>
</td>
</tr>

<tr>
<td>coordinadorecono@pj</td>
<td>Juan Arias</td>
<td>
<button type="button" rel="tooltip" class="btn btn-info btn-round">
    <i class="material-icons">grid_on</i>
  </button>
    <button type="button" rel="tooltip" class="btn btn-warning btn-round">
    <i class="material-icons">poll</i>
  </button>
</td>
</tr>

<tr>
<td>eatonarecibo@pj</td>
<td>Loyda Gonzalez</td>
<td>
<button type="button" rel="tooltip" class="btn btn-info btn-round">
    <i class="material-icons">grid_on</i>
  </button>
    <button type="button" rel="tooltip" class="btn btn-warning btn-round">
    <i class="material-icons">poll</i>
  </button>
</td>
</tr>

<tr>
<td>grancaribemall@pj</td>
<td>Keishla Ayala</td>
<td>
<button type="button" rel="tooltip" class="btn btn-info btn-round">
    <i class="material-icons">grid_on</i>
  </button>
    <button type="button" rel="tooltip" class="btn btn-warning btn-round">
    <i class="material-icons">poll</i>
  </button>
</td>
</tr>

<tr>
<td>hospitalsusoni@pj</td>
<td>Deborah Carrero</td>
<td>
<button type="button" rel="tooltip" class="btn btn-info btn-round">
    <i class="material-icons">grid_on</i>
  </button>
    <button type="button" rel="tooltip" class="btn btn-warning btn-round">
    <i class="material-icons">poll</i>
  </button>
</td>
</tr>

<tr>
<td>hpaviaarecibo@pj</td>
<td>Sarah Segarra</td>
<td>
<button type="button" rel="tooltip" class="btn btn-info btn-round">
    <i class="material-icons">grid_on</i>
  </button>
    <button type="button" rel="tooltip" class="btn btn-warning btn-round">
    <i class="material-icons">poll</i>
  </button>
</td>
</tr>

<tr>
<td>rutametro@pj</td>
<td>Victor Ortiz</td>
<td>
<button type="button" rel="tooltip" class="btn btn-info btn-round">
    <i class="material-icons">grid_on</i>
  </button>
    <button type="button" rel="tooltip" class="btn btn-warning btn-round">
    <i class="material-icons">poll</i>
  </button>
</td>
</tr>

<tr>
<td>rutanorte@pj</td>
<td>Miguel Santiago</td>
<td>
<button type="button" rel="tooltip" class="btn btn-info btn-round">
    <i class="material-icons">grid_on</i>
  </button>
    <button type="button" rel="tooltip" class="btn btn-warning btn-round">
    <i class="material-icons">poll</i>
  </button>
</td>
</tr>

<tr>
<td>torresclaropr@pj</td>
<td>Thomas Colon</td>
<td>
<button type="button" rel="tooltip" class="btn btn-info btn-round">
    <i class="material-icons">grid_on</i>
  </button>
    <button type="button" rel="tooltip" class="btn btn-warning btn-round">
    <i class="material-icons">poll</i>
  </button>
</td>
</tr>

<tr>
<td>wyndhamgr@pj</td>
<td>Waleska Diaz Caraballo</td>
<td>
<button type="button" rel="tooltip" class="btn btn-info btn-round">
    <i class="material-icons">grid_on</i>
  </button>
  <button type="button" rel="tooltip" class="btn btn-warning btn-round">
    <i class="material-icons">poll</i>
  </button>
</td>
</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>