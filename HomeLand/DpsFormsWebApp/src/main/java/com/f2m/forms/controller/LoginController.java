package com.f2m.forms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f2m.forms.beans.Login;
import com.f2m.forms.services.SecurityService;
import com.f2m.forms.utils.AquariusException;
import com.f2m.forms.utils.SecurityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
public class LoginController {
	
	final static Logger logger = Logger.getLogger(LoginController.class);
	private SecurityUtils securityUtils = new SecurityUtils();
	
	@Autowired
	SecurityService securityService;

	@RequestMapping(value={"/index","/"})
	public String login(Model model, HttpServletRequest request) throws AquariusException {
		Login loginData = new Login();
		model.addAttribute("LoginForm", loginData);
		HttpSession session = request.getSession();
		if (session.getAttribute("userInfo") != null) {
			Object object = session.getAttribute("userInfo");
			if (object != null) {
				try {
					JsonNode userAccountInfo = (JsonNode) object;
					securityService.logout(null, userAccountInfo.findValue("token").asText());
				} catch (Exception e) {}
				session.removeAttribute("userInfo");
			}
		}
		if (session.getAttribute("param.fail") != null) {
			Object object = session.getAttribute("param.fail");
			if (object != null) {
				try {
					model.addAttribute("fail", object.toString());
				} catch (Exception e) {}
				session.removeAttribute("param.fail");
				
			}
		}
		model.addAttribute("version", "3.00.02");
		return "login";
	}
	
	@RequestMapping("/validate")
	public String validateLogin(Model model, HttpSession session, @ModelAttribute("LoginForm") Login loginData,
			HttpServletRequest request) throws AquariusException {
			if (loginData != null) {
				if (loginData.getUsername() == null || loginData.getUsername().length() == 0
						|| loginData.getPassword() == null || loginData.getPassword().length() == 0) {
					session.setAttribute("param.fail","Sin información del usuario");
					return "redirect:/index";
				}
				JsonNode response = securityService.login(loginData.getUsername(), loginData.getPassword(), 
						securityUtils.getSystemInfo(request));
				logger.debug("Respuesta de servicio de login: " + response.findValue("status").asInt());
				if (response.findValue("status").asInt() != 0) {
					if (response.findValue("status").asInt() == 303 ||
							response.findValue("status").asInt() == 311 ||
							response.findValue("status").asInt() == 312 ||
							response.findValue("status").asInt() == 313) {
						session.setAttribute("param.fail","Usuario o contraseña inválido");
					} else if (response.findValue("status").asInt() == 310) {
						session.setAttribute("param.fail","El usuario no tiene permisos para ingresar al portal");
					} else if (response.findValue("status").asInt() == 301 || response.findValue("status").asInt() == 302 || response.findValue("status").asInt() == 307) {
						JsonNode responseWs = securityService.logout(loginData.getUsername(), null);
						if (responseWs != null) {
							if (responseWs.findValue("status").asInt() == 0) {
								return validateLogin(model, session, loginData, request);
							}
						}
						session.setAttribute("param.fail","El usuario ya cuenta con una sesión abierta. Cierre esa sesión antes de entrar al portal");
					} else if (response.findValue("status").asInt() == 304 || response.findValue("status").asInt() == 305) {
						session.setAttribute("param.fail","No se encontró la sesión del usuario");
					} else {
						session.setAttribute("param.fail","Ocurrió un error. Código: [" + response.findValue("status").asInt() + "]");
					}
					return "redirect:/index";
				} else {
					String token = response.findValue("exito").asText();
					JsonNode userAccount = securityService.getUserProfile(loginData.getUsername(), token);
					ObjectNode userAccountInfo = userAccount.get(0).deepCopy();
					userAccountInfo.put("token", token);
					session.setAttribute("userInfo", userAccountInfo);
					logger.debug("Info del Usuario:" + userAccountInfo.toString());
					JsonNode perfiles = userAccountInfo.get("securityProfiles");
					String perfil = "";
					for (JsonNode value:perfiles) {
						perfil = value.asText();
					}
					JsonNode profile = securityService.getProfile(perfil, token);
					session.setAttribute("pages", profile.findValue("pages"));
					logger.debug("Info del perfil:" + profile);
					String myHome = profile.findValue("home").asText();
					session.setAttribute("home", myHome);
					return "redirect:" + myHome;
				}
			} else {
				session.setAttribute("param.fail","Sin información del usuario");
				return "redirect:/index";
			}
			
			
		
    }
	
	@RequestMapping("/logout")
	public String logout(Model model, HttpSession session,
			HttpServletRequest request, HttpServletResponse responsehttp) {
		try {
			Object object = session.getAttribute("userInfo");
			if (object != null) {
				JsonNode userAccountInfo = (JsonNode) object;
				JsonNode response = securityService.logout(null, userAccountInfo.findValue("token").asText());
				if (response != null) {
					if (response.findValue("status").asInt() == 0) {
						session.removeAttribute("userInfo");
						session.invalidate();
						return "redirect:/index";
					} else {
						session.setAttribute("param.fail","No se pudo cerrar la sesión del usuario");
						return "redirect:/index";
					}
				} else {
					session.setAttribute("param.fail","Error de comunicación con los servicios");
					return "redirect:/index";
				}
			} else {
				session.setAttribute("param.fail","Error de comunicación con los servicios");
				return "redirect:/index";
			}
		} catch (Exception e) {
			System.out.println("[logout] " + e.getMessage());
			session.setAttribute("param.fail","Ocurrió un error. Intente más tarde.");
			return "redirect:/index";
		}
	}
	
}
