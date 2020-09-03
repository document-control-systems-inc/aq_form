<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>

	<div class="container">
		<div class="row">
			<div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
					<div class="card card-login card-hidden">
						<br>
						<p class="category text-center">
							<img src="${pageContext.request.contextPath}/assets/img/logo-small-janitorial-completo.jpg" style="width: 150px">
						</p>
						<form:form action="${pageContext.request.contextPath}/validate" method="post" modelAttribute="LoginForm">
							<div class="card-content">
							
								<div class="input-group">
									<span class="input-group-addon"> <i class="material-icons">face</i>
									</span>
		
									<div class="form-group label-floating">
										<label class="control-label">Usuario</label> 
										<form:input name="username" id="user" path="username" type="text" class="form-control" />
									</div>
								</div>
		
								<div class="input-group">
									<span class="input-group-addon"> <i class="material-icons">lock_outline</i>
									</span>
									<div class="form-group label-floating">
										<label class="control-label">Contraseña</label>
										<form:input name="password" id="password" path="password" type="password" class="form-control" />
									</div>
								</div>
								<div class="text-center">
									<br>
									<span class="label label-danger">${fail}</span>
								</div>
							</div>
							<div class="footer text-center">
								<button type="submit" id="submitLink"
									class="btn btn-warning">Entrar</button>
							</div>
						</form:form>
					</div>
					<center>
						<img src="${pageContext.request.contextPath}/assets/img/DCS_Logo_S.png" style="width:150px">
					</center>
					  
				
				
			</div>
		</div>
	</div>
	
	<script>
		$('#submitLink').on('click', function(evt){
			evt.preventDefault();
			console.log("To send form");
			$(this).closest('form').get(0).submit();
		});
	</script>
</body>