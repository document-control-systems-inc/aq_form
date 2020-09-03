<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isErrorPage="true"%>
<%
	session.setAttribute("requestUri", pageContext.getErrorData().getRequestURI());
	session.setAttribute("statusCode", pageContext.getErrorData().getStatusCode());
	session.setAttribute("exception", pageContext.getException());
	response.sendRedirect(request.getContextPath() + "/error");
%>