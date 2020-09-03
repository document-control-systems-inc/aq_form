<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="utf-8" />
	<link rel="apple-touch-icon" sizes="76x76" href="${pageContext.request.contextPath}/assets/img/favicon.png" />
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/img/favicon.png" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

	<title>Aquarius Forms</title>

	<style id="antiClickjack">body{display:none !important;}</style>
	<script type="text/javascript">
	   if (self === top) {
	       var antiClickjack = document.getElementById("antiClickjack");
	       antiClickjack.parentNode.removeChild(antiClickjack);
	   } else {
	       top.location = self.location;
	   }
	</script>

	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0' name='viewport' />
	<meta name="viewport" content="width=device-width" />

	<!-- Bootstrap core CSS     -->
	<link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet" />
	


	<!--  Material Dashboard CSS    -->
	<link href="${pageContext.request.contextPath}/assets/css/material-dashboard.css" rel="stylesheet" />

	
	<!--     Fonts and icons     -->
	<link href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/assets/css/googleapisRoboto.css" rel="stylesheet" type="text/css"  />
	<link href="${pageContext.request.contextPath}/assets/css/googleapisMaterialIcons.css" rel="stylesheet" />

</head>

<body class="off-canvas-sidebar">
    <nav class="navbar navbar-primary navbar-transparent navbar-absolute">
	</nav>


    <div class="wrapper wrapper-full-page">
    		<!--  
            <div class="full-page login-page" data-image="${pageContext.request.contextPath}/assets/img/login.jpeg">
            -->
            <div class="full-page login-page" data-image="${pageContext.request.contextPath}/assets/img/fondo01.png">
        <!--   you can change the color of the filter page using: data-color="blue | purple | green | orange | red | rose " -->
        <div class="content">
        
        
        	<sitemesh:write property='body' />
        	
        	
		</div>
		<footer class="footer">
		    <div class="container-fluid">
		        <p class="copyright pull-right">
		        	<span class="label label-warning">Versión ${version}</span>&nbsp;&nbsp;
		            &copy; <script>document.write(new Date().getFullYear())</script> <a href="http://www.f2m.com.mx"> F2M S.A. de C.V. </a>
		        </p>
		    </div>
		</footer>
		
		    </div>
		
		    </div>
    
</body>

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

<!--  DataTables.net Plugin, full documentation here: https://datatables.net/    -->
<script src="${pageContext.request.contextPath}/assets/js/jquery.datatables.js"></script>

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

<!-- Script para el decorator de login -->
<script src="${pageContext.request.contextPath}/assets/js/login.js"></script>

    <script type="text/javascript">
        $().ready(function(){
        	login.checkFullPageBackgroundImage();

            setTimeout(function(){
                // after 1000 ms we add the class animated to the login/register card
                $('.card').removeClass('card-hidden');
            }, 700)
        });
    </script>
</html>
