package com.f2m.forms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f2m.forms.utils.AquariusException;
import com.f2m.forms.utils.ExceptionUtils;


@Controller
public class ErrorController {

	final static Logger logger = Logger.getLogger(ErrorController.class);
	private ExceptionUtils exceptionUtils = new ExceptionUtils();
	
	
	@RequestMapping("/error")
	public String errorHandler(Model model, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws AquariusException {
		String errorMessage = "";
		if (session.getAttribute("statusCode") != null) {
			int statusCode = Integer.parseInt(session.getAttribute("statusCode").toString());
			String resource = "";
			if (session.getAttribute("requestUri") != null) {
				resource = session.getAttribute("requestUri").toString();
			}
			switch(statusCode) {
				case 404:
					errorMessage = "Recurso no encontrado";
					logger.debug(errorMessage + ": " +  resource);
					break;
				case 403:
					errorMessage = "Sin autorización para ingresar al recurso";
					logger.debug(errorMessage + ": "+ resource);
					break;
				case 500:
					logger.error(session.getAttribute("exception"));
					String message = exceptionUtils.parseException(session.getAttribute("exception"));
					if (message != null) {
						if (message.equals("304")) {
							errorMessage = "No se ha encontrado la sesión";
						} else {
							errorMessage = "Se ha producido un error. Código: [" + message + "]";
						}
					} else {
						errorMessage = "Se ha producido un error. Código: [1]";
					}
					break;
			}
		}
		if (session.getAttribute("requestUri") != null) {
			if (session.getAttribute("requestUri").toString().equals("/index")) {
				if (errorMessage.length() == 0) {
					errorMessage = "Se ha producido un error. Código: [2]";
				}
				model.addAttribute("fail", errorMessage);
				return "error";
			}
		}
		session.setAttribute("param.fail",errorMessage);
		try {
			resp.sendRedirect(req.getContextPath() + "/index");
		} catch (IOException e) {
			logger.error("Error al redireccionar el error: " + e.getMessage());
		}
		return null;
	}
}
