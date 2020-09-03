<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<link rel="apple-touch-icon" sizes="76x76" href="${pageContext.request.contextPath}/assets/img/favicon.png" />
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/img/favicon.png" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>Aquarius Forms</title>

	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
	<meta name="viewport" content="width=device-width" />

	<!-- Bootstrap core CSS     -->
	<link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet" />
	


	<!--  Material Dashboard CSS    -->
	<link href="${pageContext.request.contextPath}/assets/css/material-dashboard.css?v=1.3.0" rel="stylesheet"/>

	<!--  CSS for Demo Purpose, don't include it in your project  -->
	
	<!--     Fonts and icons     -->
	<link href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/assets/css/googleapisRoboto.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/assets/css/googleapisMaterialIcons.css" rel="stylesheet">
	

</head>

	<!--   Core JS Files   -->
<script src="${pageContext.request.contextPath}/assets/js/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/material.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/assets/js/perfect-scrollbar.jquery.min.js" type="text/javascript"></script>

<!-- Include a polyfill for ES6 Promises (optional) for IE11, UC Browser and Android browser support SweetAlert -->
<script src="${pageContext.request.contextPath}/assets/js/core_2.4.1.js"></script>

<!-- Library for adding dinamically elements -->
<script src="${pageContext.request.contextPath}/assets/js/arrive.min.js" type="text/javascript"></script>

<!-- Forms Validations Plugin -->
<script src="${pageContext.request.contextPath}/assets/js/jquery.validate.min.js"></script>

<!--  Plugin for Date Time Picker and Full Calendar Plugin-->
<script src="${pageContext.request.contextPath}/assets/js/moment.min.js"></script>

<!--  Charts Plugin, full documentation here: https://gionkunz.github.io/chartist-js/ -->
<script src="${pageContext.request.contextPath}/assets/js/chartist.min.js"></script>

<!--  Plugin for the Wizard, full documentation here: https://github.com/VinceG/twitter-bootstrap-wizard -->
<script src="${pageContext.request.contextPath}/assets/js/jquery.bootstrap-wizard.js"></script>

<!--  Notifications Plugin, full documentation here: http://bootstrap-notify.remabledesigns.com/    -->
<script src="${pageContext.request.contextPath}/assets/js/bootstrap-notify.js"></script>

<!--  Plugin for the DateTimePicker, full documentation here: https://eonasdan.github.io/bootstrap-datetimepicker/ -->
<script src="${pageContext.request.contextPath}/assets/js/bootstrap-datetimepicker.js"></script>

<!-- Vector Map plugin, full documentation here: http://jvectormap.com/documentation/ -->
<script src="${pageContext.request.contextPath}/assets/js/jquery-jvectormap.js"></script>

<!-- Sliders Plugin, full documentation here: https://refreshless.com/nouislider/ -->
<script src="${pageContext.request.contextPath}/assets/js/nouislider.min.js"></script>

<!--  Plugin for Select, full documentation here: http://silviomoreto.github.io/bootstrap-select -->
<script src="${pageContext.request.contextPath}/assets/js/jquery.select-bootstrap.js"></script>

<!--  DataTables.net Plugin, full documentation here: https://datatables.net/ -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/pdfmake.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/vfs_fonts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/datatables.min.js"></script>


<!-- Sweet Alert 2 plugin, full documentation here: https://limonte.github.io/sweetalert2/ -->
<script src="${pageContext.request.contextPath}/assets/js/sweetalert2.js"></script>

<!-- Plugin for Fileupload, full documentation here: http://www.jasny.net/bootstrap/javascript/#fileinput -->
<script src="${pageContext.request.contextPath}/assets/js/jasny-bootstrap.min.js"></script>

<!--  Full Calendar Plugin, full documentation here: https://github.com/fullcalendar/fullcalendar    -->
<script src="${pageContext.request.contextPath}/assets/js/fullcalendar.min.js"></script>

<!-- Plugin for Tags, full documentation here: https://github.com/bootstrap-tagsinput/bootstrap-tagsinputs  -->
<script src="${pageContext.request.contextPath}/assets/js/jquery.tagsinput.js"></script>

<!-- Material Dashboard javascript methods -->
<script src="${pageContext.request.contextPath}/assets/js/material-dashboard.js?v=1.3.0"></script>

<body >
	<div class="wrapper">
		<!--  
		4410 - background-color: #191919; -> #1e4072
	    <div class="sidebar" data-active-color="orange" data-background-color="black" data-image="${pageContext.request.contextPath}/assets/img/sidebar-1.jpg">
		-->
		
		<div class="sidebar" data-active-color="orange" data-background-color="black" >
    <div class="logo">
        <a href="${pageContext.request.contextPath}${home}" class="simple-text logo-mini">
			<img src="${pageContext.request.contextPath}/assets/img/logo-dps.png" style="width:30px">
		<a/>

        <a href="${pageContext.request.contextPath}${home}" class="simple-text logo-normal">
             Forms 
        </a>
    </div>

    <div class="sidebar-wrapper">
        <div class="user">
            <div class="photo">
                <img src="${pageContext.request.contextPath}/assets/img/default-avatar.png" />
            </div>
            <div class="info">
                <a data-toggle="collapse" href="#collapseExample" class="collapsed">
                    <span>${nombreUsuario}</span>
                </a>
                <div class="clearfix"></div>
                <div class="collapse" id="collapseExample">
                    <ul class="nav">
                        <!--  
                        <li>
                            <a href="#">
                                <span class="sidebar-mini"> MP </span>
                                <span class="sidebar-normal"> Mi Perfil </span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span class="sidebar-mini"> EP </span>
                                <span class="sidebar-normal"> Editar Perfil </span>
                            </a>
                        </li>
                        -->
                        <li>
                            <a href="${pageContext.request.contextPath}/logout">
                                <span class="sidebar-mini"> CS </span>
                                <span class="sidebar-normal"> Cerrar Sesión </span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <ul class="nav">
        	<c:forEach var="pages" 
			       items="${pages}"
			       varStatus="status">
			       
			    <c:if test = "${actualPage.url == pages.url}">
			    	<li class="active">
			    </c:if>
				<c:if test = "${actualPage.url != pages.url}">
			    	<li>
			    </c:if>	
	                <a href="${pageContext.request.contextPath}${pages.url}">
	                    <i class="material-icons">${pages.icon}</i>
	                    <p> ${pages.name} </p>
	                </a>
	            </li>
			</c:forEach>
        </ul>
    </div>
</div>


	    <div class="main-panel">

			<nav class="navbar navbar-transparent navbar-absolute">
    <div class="container-fluid">
        <div class="navbar-minimize">
            <button id="minimizeSidebar" class="btn btn-round btn-white btn-fill btn-just-icon">
                <i class="material-icons visible-on-sidebar-regular">more_vert</i>
                <i class="material-icons visible-on-sidebar-mini">view_list</i>
            </button>
        </div>
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <label class="navbar-brand"> ${actualPage.name} </label>
        </div>
        
    </div>
</nav>


			

				<div class="content">
					<div class="container-fluid">
						<sitemesh:write property='body' />
					</div>
				</div>

			 

			
			<footer class="footer">
    <div class="container-fluid">
        <p class="copyright pull-right">
            &copy; <script>document.write(new Date().getFullYear())</script> <a href="http://www.f2m.com.mx"> F2M S.A. de C.V. </a>
        </p>
    </div>
</footer>

			
		</div>
	</div>
</body>

</html>