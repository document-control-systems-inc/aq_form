package com.f2m.forms.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2m.forms.beans.Form;
import com.f2m.forms.utils.AquariusException;
import com.fasterxml.jackson.databind.JsonNode;

@Service("formsService")
public class FormsServiceImpl implements FormsService{

	final static Logger logger = Logger.getLogger(FormsServiceImpl.class);
	private HashMap<String,JsonNode> formas = null;
	private String pattern = "yy-MM";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	
	
	@Autowired
	WsCommService wsCommService;

	@Override
	public List<Form> getForms(String token) throws AquariusException {
		if (token == null || token.length() == 0) {
			throw new AquariusException(1612);
		}
		List<Form> infoFormas = new ArrayList<Form>();
		getFormsService(token);
		for (Map.Entry<String, JsonNode> entry : formas.entrySet()) {
			Form forma = new Form();
			forma.setName(entry.getKey());
			forma.setLabel(entry.getValue().get("label").asText());
			infoFormas.add(forma);
		}
		return infoFormas;
	}
	
	@Override
	public List<Form> getFormsCount(String token, String user) throws AquariusException {
		if (token == null || token.length() == 0) {
			throw new AquariusException(1612);
		}
		List<Form> infoFormas = new ArrayList<Form>();
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
		if (user != null && user.length() > 0) {
			args.add(wsCommService.createParam("user", user));
		}
		JsonNode response = wsCommService.executeService("/form/formStats", "GET", args);
		if (response.findValue("status").asInt() == 0) {
			JsonNode results = response.findValue("exito");
			logger.debug("Resultados de las formas: " + results);
			for (JsonNode form:results) {
				logger.debug("Forma: " + form.toString());
				Form forma = new Form();
				forma.setName("");
				forma.setLabel(form.get("label").asText());
				forma.setData(form.get("total").asText());
				infoFormas.add(forma);
			}
		} else {
			logger.error("Error al recibir las estadísticas de las formas: [" + response.findValue("status").asInt() + "]" );
		}
		return infoFormas;
	}
	
	@Override
	public JsonNode getFormById(String idForm, String token) throws AquariusException {
		if (token == null || token.length() == 0
				|| idForm == null || idForm.length() == 0) {
			throw new AquariusException(1614);
		}
		getFormsService(token);
		return formas.get(idForm);
	}
	
	private void getFormsService(String token) throws AquariusException {
		if (formas == null) {
			List<JsonNode> args = new ArrayList<JsonNode>();
			args.add(wsCommService.createParam("token", token));
			JsonNode response = wsCommService.executeService("/form/formDef", "GET", args);
			if (response.findValue("status").asInt() == 0) {
				JsonNode results = response.findValue("exito");
				logger.debug("Resultados de las formas: " + results);
				formas = new HashMap<String,JsonNode>();
				for (JsonNode form:results) {
					logger.debug("Forma: " + form.toString());
					formas.put(form.get("name").asText(), form);
				}
			} else {
				logger.error("Error al recibir las formas: [" + response.findValue("status").asInt() + "]" );
			}
		}
	}

	@Override
	public JsonNode getFormData(String token, String user) throws AquariusException {
		if (token == null || token.length() == 0) {
			throw new AquariusException(1615);
		}
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
		if (user != null && user.length() > 0) {
			args.add(wsCommService.createParam("user", user));
		}
		JsonNode response = wsCommService.executeService("/form/formData", "GET", args);
		if (response.findValue("status").asInt() == 0) {
			JsonNode results = response.findValue("exito");
			logger.debug("Datos de las formas: " + results);
			return results;
		} else {
			logger.error("Error al recibir los datos de las formas: [" + response.findValue("status").asInt() + "]" );
		}
		return null;
	}
	
	@Override
	public JsonNode getFormDataById(String idForm, String user, String token) throws AquariusException {
		if (token == null || token.length() == 0
				|| idForm == null || idForm.length() == 0
				|| user == null || user.length() == 0) {
			throw new AquariusException(1616);
		}
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
		args.add(wsCommService.createParam("idForm", idForm));
		args.add(wsCommService.createParam("user", user));
		JsonNode response = wsCommService.executeService("/form/formData", "GET", args);
		if (response.findValue("status").asInt() == 0) {
			JsonNode results = response.findValue("exito");
			logger.debug("Datos de las formas: " + results);
			return results;
		} else {
			logger.error("Error al recibir los datos de las formas: [" + response.findValue("status").asInt() + "]" );
		}
		return null;
	}
	
	@Override
	public String setFormPDF(String token, JsonNode data) throws AquariusException {
		if (token == null || token.length() == 0
				|| data == null) {
			throw new AquariusException(1617);
		}
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
		JsonNode response = wsCommService.executeServiceBody("/getFormPdfPrimePDF", "POST", args, data.toString());
		if (response.findValue("status").asInt() == 0) {
			JsonNode results = response.findValue("exito");
			logger.debug("Datos de las formas: " + results);
			return results.asText();
		} else {
			logger.error("Error al generar la forma : [" + response.findValue("status").asInt() + "]" );
		}
		return null;
	}

	@Override
	public String getUrl() throws AquariusException {
		return wsCommService.getUrl();
	}

	@Override
	public String getFolderPath() throws AquariusException {
		return wsCommService.getFolderPath();
	}
	
	@Override
	public String getImgPath() throws AquariusException {
		return wsCommService.getImgPath();
	}

	@Override
	public boolean sendEmail(String idPdf, String email, String token) throws AquariusException {
		if (token == null || token.length() == 0
				|| idPdf == null || idPdf.length() == 0
				|| token == null || token.length() == 0) {
			throw new AquariusException(1624);
		}
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
		args.add(wsCommService.createParam("idPdf", idPdf));
		args.add(wsCommService.createParam("correo", email));
		
		JsonNode response = wsCommService.executeService("/email", "POST", args);
		if (response.findValue("status").asInt() == 0) {
			return true;
		} else {
			logger.error("Error al recibir respuesta del envío de correo: [" + response.findValue("status").asInt() + "]" );
		}
		return false;
	}

	@Override
	public boolean sendAutomaticEmail(String idPdf, String template, String token) throws AquariusException {
		if (token == null || token.length() == 0
				|| idPdf == null || idPdf.length() == 0
				|| template == null || template.length() == 0
				|| token == null || token.length() == 0) {
			throw new AquariusException(1624);
		}
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
		args.add(wsCommService.createParam("idPdf", idPdf));
		args.add(wsCommService.createParam("template", template));
		JsonNode response = wsCommService.executeService("/emailAutomaticFile", "POST", args);
		if (response.findValue("status").asInt() == 0) {
			return true;
		} else {
			logger.error("Error al recibir respuesta del envío de correo: [" + response.findValue("status").asInt() + "]" );
		}
		return false;
	}

	@Override
	public String getNumeroControl(String token) throws AquariusException {
		Formatter fmt = new Formatter();
		String numControl = simpleDateFormat.format(new Date());
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
		JsonNode response = wsCommService.executeService("/numeroControl", "GET", args);
		int numConsecutivo = 1;
		if (response.findValue("status").asInt() == 0) {
			numConsecutivo = response.findValue("exito").asInt();
		}
		fmt.format("%03d",numConsecutivo);
		numControl += "-" + fmt;
		fmt.close();
		return numControl;
	}
}
