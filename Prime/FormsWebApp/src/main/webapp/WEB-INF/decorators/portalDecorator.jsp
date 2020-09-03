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

	<title>AQForms</title>

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
	
	<link href="${pageContext.request.contextPath}/assets/css/w3.css" rel="stylesheet">

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

<!-- Signature -->
<script src="${pageContext.request.contextPath}/assets/js/signature.js"></script>

<body >
	<div class="wrapper">

	    <div class="sidebar" data-active-color="orange" data-background-color="black" data-image="${pageContext.request.contextPath}/assets/img/sidebar-1.jpg">

	<!--  
    <div class="logo">
        <a href="${pageContext.request.contextPath}${home}" class="simple-text logo-mini">
			<img src="${pageContext.request.contextPath}/assets/img/logo-small-janitorial.png" style="width:30px">
		<a/>

        <a href="${pageContext.request.contextPath}${home}" class="simple-text logo-normal">
             Forms 
        </a>
    </div>
	-->
	<div class="logo">
		<a href="${pageContext.request.contextPath}${home}" class="simple-text logo-mini">
		<a/>
		<a href="${pageContext.request.contextPath}${home}" class="simple-text logo-normal">
			<img src="${pageContext.request.contextPath}/assets/img/logo-small-janitorial-completo.jpg" style="height:40px"> 
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
			    	<c:if test="${empty pages.isExternal}">
			    		<a href="${pageContext.request.contextPath}${pages.url}">
		                    <i class="material-icons">${pages.icon}</i>
		                    <p> ${pages.name} </p>
		                </a>	
			    	</c:if>
	                <c:if test="${not empty pages.isExternal}">
			    		<a href="${pages.url}" target="_blank">
		                    <i class="material-icons">${pages.icon}</i>
		                    <p> ${pages.name} </p>
		                </a>	
			    	</c:if>
	            </li>
			</c:forEach>
        </ul>
        <br>
        <center>
        <img style="width:200px" src="${pageContext.request.contextPath}/assets/img/logoiso9001.png" />
        </center>
    </div>
</div>


	    <!-- <div class="main-panel" style="background-image: url('${pageContext.request.contextPath}/assets/img/desktop-background.jpg'); background-position: top center;"> -->
		<div class="main-panel" >
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
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <!-- 
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="material-icons">notifications</i>
                        <span class="notification">5</span>
                        <p class="hidden-lg hidden-md">
                            Notifications
                            <b class="caret"></b>
                        </p>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Mike John responded to your email</a></li>
                        <li><a href="#">You have 5 new tasks</a></li>
                        <li><a href="#">You're now friend with Andrew</a></li>
                        <li><a href="#">Another Notification</a></li>
                        <li><a href="#">Another One</a></li>
                    </ul>
                </li>
				-->
                <li class="separator hidden-lg hidden-md"></li>
            </ul>
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
	
	<div id="signature-modal" class="w3-modal" style="background-color: #aaa;">
		<span onclick="cerrarSignature();" class="w3-button w3-display-topright">&times;</span>
		<div id="signature_frame" class="text-center">
			<canvas id="signature" style="width: 384px; height: 160px; margin-top: 2px; background-color: #fff;"></canvas>
		</div>
		<div id="signature_controls" class="text-center">
			<input id="btnBorrar" type="button" value="Borrar" style="left:50px;width:50px;height:19px;font-size:7pt" onClick="borrarSignature();">
			<input id="btnAceptar" type="button" value="Aceptar" style="left:280px;width:50px;height:19px;font-size:7pt" onClick="aceptarSignature();">
		</div>
	</div>
	
	
	<div id="calificacion" class="w3-modal">
    <div class="w3-modal-content">
      <div class="w3-container" style="background-color:aqua;padding: 10px 20px;margin: 20% auto;position: relative;">
        <span onclick="document.getElementById('calificacion').style.display='none'" class="w3-button w3-display-topright">&times;</span>
		<center>
        <label><button  style="font-size:45pt; padding: 6px 20px; border-radius:10px; " type="submit" onclick="updateTxt('')"  value="" required>&nbsp;</button> </label>
        <label><button  style="font-size:45pt; padding: 6px 20px; border-radius:10px; " type="submit" onclick="updateTxt(6)"  value="6" required>6 </button> </label>
		<label><button  style="font-size:45pt; padding: 6px 20px; border-radius:10px;" type="submit" onclick="updateTxt(7)" value="7">7 </button></label>
		<label><button style="font-size:45pt; padding: 6px 20px; border-radius:10px;" type="submit" onclick="updateTxt(8)" value="8"> 8</button></label>
		<label><button style="font-size:45pt; padding: 6px 20px; border-radius:10px;" type="submit" onclick="updateTxt(9)" value="9"> 9</button></label>
		<label><button style="font-size:45pt; padding: 6px 20px; border-radius:10px;" type="su bmit" onclick="updateTxt(10)" value="10">10 </button></label>
		</center>
      </div>
    </div>
  </div>
	
</body>

</html>