package com.f2m.aquarius.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.f2m.aquarius.beans.ServiceResponse;
import com.f2m.aquarius.beans.UserSession;
import com.f2m.aquarius.service.ConfigurationService;
import com.f2m.aquarius.service.FormService;
import com.f2m.aquarius.service.LdapService;
import com.f2m.aquarius.service.MailService;
import com.f2m.aquarius.utils.AquariusException;
import com.f2m.aquarius.utils.GeneralUtils;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@CrossOrigin(methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class EmailController {

	private LdapService ldapService = new LdapService();
	private ConfigurationService conf = new ConfigurationService();
	private GeneralUtils gutils = new GeneralUtils();
	private FormService formService = new FormService();
	private MailService mailService = new MailService();
	
	@RequestMapping(value = "/email", method = RequestMethod.POST, headers = "Accept=application/json")
	public ServiceResponse getPolicies(@RequestHeader(required = true, value = "correo") String correo,
			@RequestHeader(required = true, value = "idPdf") String idPdf,
			@RequestHeader(required = true, value = "token") String token) {
		ServiceResponse response = new ServiceResponse();
		try {
			UserSession session = new UserSession();
			session.setSessionId(token);
			session.setHostnameConnected("localhost");
			if (ldapService.validateSession(session)) {
				String userUid = ldapService.getUidFromSession(session);
				List<JsonNode> results = formService.getFormData(idPdf, "", userUid);
				String filePath = "";
				if (results != null) {
					for (JsonNode row:results) {
						filePath = row.get("formData").get("file").asText();
					}
					File file = new File(filePath);
					if (file.exists()) {
						System.out.println("Puedo descargar el documento");
						try {
							mailService.sendMailAttachment(correo, "AqForms", "", filePath);
						} catch (Exception e) {
							System.out.println("Error al enviar el correo electrónico");
						}
					}
				}
				
				
			} else {
				response.setStatus(6);
			}
		} catch (AquariusException e) {
			response.setStatus(gutils.getErrorCode(e.getMessage()));
		}
		return response;
			
		/*
			
			JsonNode session = loginService.validateSession(token);
			String username = session.findValue("username").asText();
			JsonNode jsonPolicy = dixService.getDocumentById(idPoliza);
			String nombreCliente = "";
			String nombreAgente = "";
			String pathFile = "";
			if (jsonPolicy.findValue("files") != null) {
				for (JsonNode file:jsonPolicy.findValue("files")) {
					if (file.findValue("type").asText().equals("html")) {
						pathFile = file.findValue("path").asText();
					}
				}
			}
			if (pathFile.length() == 0) {
				if (jsonPolicy.findValue("files") != null) {
					for (JsonNode file:jsonPolicy.findValue("files")) {
						if (file.findValue("type").asText().equals("pdf")) {
							pathFile = file.findValue("path").asText();
						}
					}
				}
			}
			
			if (jsonPolicy.findValue("info").findValue("asegurado") != null) {
				nombreCliente = gUtils.validateJsonNullasString(jsonPolicy.findValue("info").findValue("asegurado").findValue("nom_asegurado"));
			}
			if (jsonPolicy.findValue("info").findValue("agente") != null) {
				nombreAgente = gUtils.validateJsonNullasString(jsonPolicy.findValue("info").findValue("agente").findValue("nombre"));
			}
			String numeroPoliza = gUtils.validateJsonNullasString(jsonPolicy.findValue("info").findValue("poliza").findValue("s")) + "-" + gUtils.validateJsonNullasString(jsonPolicy.findValue("info").findValue("poliza").findValue("ramo")) + "-" + gUtils.validateJsonNullasString(jsonPolicy.findValue("info").findValue("poliza").findValue("numero_poliza")) + "-" + gUtils.validateJsonNullasString(jsonPolicy.findValue("info").findValue("poliza").findValue("sufijo")) + "-" + gUtils.validateJsonNullasString(jsonPolicy.findValue("info").findValue("poliza").findValue("endoso"));
			String ramo = gUtils.validateJsonNullasString(jsonPolicy.findValue("info").findValue("poliza").findValue("nombre_ramo"));
			
			File file = new File(pathFile);
			if (file.exists()) {
				String message = emailService.getEmailBody(nombreCliente, nombreAgente, numeroPoliza, ramo);
				if (emailService.sendEmail(correo, file, message, null)) {
					// se actualiza la base de datos para marcar que ya fue enviado
					response.setResponse("Correo Enviado");
					response.setStatus(0);
					//prospeccionService.updateSendEmail(new Boolean(true), id);
				} else {
					response.setResponse("Error al enviar el correo");
					response.setStatus(706);
				}
			} else {
				response.setResponse("No se encontraron los datos para poder enviar el correo electrónico");
				response.setStatus(711);
			}
		} catch (DixException e) {
			logger.error(e);
			response = exceptionUtils.parseDixException(e.getMessage());
		}
		return response;
		*/
	}
	
	@RequestMapping(value = "/emailAutomaticFile", method = RequestMethod.POST, headers = "Accept=application/json")
	public ServiceResponse emailAutomaticFile(@RequestHeader(required = true, value = "idPdf") String idPdf,
			@RequestHeader(required = true, value = "template") String template,
			@RequestHeader(required = true, value = "token") String token) {
		ServiceResponse response = new ServiceResponse();
		try {
			UserSession session = new UserSession();
			session.setSessionId(token);
			session.setHostnameConnected("localhost");
			if (ldapService.validateSession(session)) {
				String userUid = ldapService.getUidFromSession(session);
				JsonNode email = null;
				JsonNode confEmailAutomatic = conf.getemailAutomaticFile();
				if (confEmailAutomatic != null) {
					for (JsonNode rule: confEmailAutomatic.findValue("rules")) {
						if (rule.findValue("template").asText().equals(template)) {
							if (rule.findValue("type").asText().equals("pdf")) {
								email = rule.findValue("email");
							}
						}
					}
				}
				if (email != null && email.size() > 0) {
					List<JsonNode> results = formService.getFormData(idPdf, "", userUid);
					String filePath = "";
					if (results != null) {
						for (JsonNode row:results) {
							filePath = row.get("formData").get("file").asText();
						}
						File file = new File(filePath);
						if (file.exists()) {
							for (JsonNode myEmail:email) {
								try {
									mailService.sendMailAttachment(myEmail.asText(), "AqForms", "", filePath);
								} catch (Exception e) {
									System.out.println("Error al enviar el correo electrónico");
								}
							}
						}
					}
				}
			} else {
				response.setStatus(6);
			}
		} catch (AquariusException e) {
			response.setStatus(gutils.getErrorCode(e.getMessage()));
		}
		return response;
	}
}
