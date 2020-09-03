<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>
<!--   ${urlForms} -->
	<form action="${pageContext.request.contextPath}/pdf" method="post" name="myForm" id="myForm">
		<input type="hidden" name="template" value="${forma.id}">
		<c:set var="contador" scope="page" value="0"/>
		<c:set var="display" scope="page" value="block"/>
		<c:if test="${forma.landscape > 0}">
			<input type="hidden" name="landscape" value="0">
		</c:if>
		<c:forEach var="pageHtml" items="${forma.html}" varStatus="status">
			<div id="page${contador}" style="display: ${display}">
				<img id="imgFondo" src="${pageContext.request.contextPath}/getImage?img=${pageHtml.image.path}" width="${pageHtml.image.width}" height="${pageHtml.image.height}" alt="Form01" border="0" usemap="#form01">
				<c:forEach var="formHtml" items="${pageHtml.form}" varStatus="status">
					<c:if test="${formHtml.component == 'input'}">
						<c:if test="${formHtml.type == 'checkbox'}">
							<c:if test="${formHtml.value == 'X'}">
								<input style="${formHtml.style}" type="${formHtml.type}" name="${formHtml.name}" id="${formHtml.id}" onchange="${formHtml.onChange}" onclick="${formHtml.onClick}" checked>
							</c:if>
							<c:if test="${formHtml.value != 'X'}">
								<input style="${formHtml.style}" type="${formHtml.type}" name="${formHtml.name}" id="${formHtml.id}" onchange="${formHtml.onChange}" onclick="${formHtml.onClick}" >
							</c:if>
						</c:if>
						<c:if test="${formHtml.type != 'checkbox'}">
							<input style="${formHtml.style}" type="${formHtml.type}" name="${formHtml.name}" id="${formHtml.id}" onchange="${formHtml.onChange}" onclick="${formHtml.onClick}" value="${formHtml.value}">
						</c:if>
					</c:if>
					<c:if test="${formHtml.component == 'textarea'}">
						<textarea style="${formHtml.style}" name="${formHtml.name}" placeholder="${formHtml.placeholder}" onchange="${formHtml.onChange}">${formHtml.value}</textarea>
					</c:if>	
				</c:forEach>
				<br>
				<c:if test="${contador > 0}">
					<input class="btn btn-fill btn-warning" type="button" value="Anterior" onclick="showPage('page${contador}', 'page${contador -1}')">
				</c:if>
				<c:if test="${contador < totalForms - 1}">
					<input class="btn btn-fill btn-warning" type="button" value="Siguiente" onclick="showPage('page${contador}', 'page${contador +1}')">
				</c:if>
				<c:if test="${contador == totalForms - 1}">
					<input class="btn btn-fill btn-success" type="button" value="Enviar" onclick="submitForm()">
				</c:if>
			</div>
			<c:set var="display" scope="page" value="none"/>
			<c:set var="contador" scope="page" value="${countador + 1}"/>
		</c:forEach>
		
	</form>

<script>
	${forma.script}
</script>

<script>
	function showPage(actualPage, nextPage) {
		document.getElementById(actualPage).style.display='none';
		document.getElementById(nextPage).style.display='block';
	}

	function submitForm() {
		if (validateForm()) {
			var frm = document.getElementsByName('myForm')[0];
			frm.submit(); // Submit the form
			//frm.reset();  // Reset all form data
		} else {
			alert('No se ha completado correctamente la forma');
		}
		
	}
</script>
</body>