package com.f2m.forms.services;

import java.util.ArrayList;
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
	public JsonNode getFormData(String token) throws AquariusException {
		if (token == null || token.length() == 0) {
			throw new AquariusException(1615);
		}
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
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
	public JsonNode getFormDataById(String idForm, String token) throws AquariusException {
		if (token == null || token.length() == 0
				|| idForm == null || idForm.length() == 0) {
			throw new AquariusException(1616);
		}
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
		args.add(wsCommService.createParam("idForm", idForm));
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
	public String getUrl() throws AquariusException {
		return wsCommService.getUrl();
	}

	@Override
	public String getFolderPath() throws AquariusException {
		return wsCommService.getFolderPath();
	}
}
