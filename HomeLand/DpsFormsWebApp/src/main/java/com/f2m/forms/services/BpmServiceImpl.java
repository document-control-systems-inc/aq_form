package com.f2m.forms.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.f2m.forms.beans.Form;
import com.f2m.forms.beans.TaskList;
import com.f2m.forms.utils.AquariusException;
import com.fasterxml.jackson.databind.JsonNode;

public class BpmServiceImpl implements BpmService {
	
	final static Logger logger = Logger.getLogger(BpmServiceImpl.class);
	
	@Autowired
	WsCommService wsCommService;

	@Override
	public JsonNode getTasks(String token) throws AquariusException {
		if (token == null || token.length() == 0) {
			throw new AquariusException(1612);
		}
		List<Form> infoFormas = new ArrayList<Form>();
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
		args.add(wsCommService.createParam("status", "0|1"));
		JsonNode response = wsCommService.executeService("/bpm/task/list", "GET", args);
		HashMap<String,JsonNode> formas = new HashMap<String,JsonNode>();
		if (response.findValue("status").asInt() == 0) {
			JsonNode results = response.findValue("exito");
			logger.debug("Resultados de las tareas: " + results);
			return results;
		} else {
			logger.error("Error al recibir las formas: [" + response.findValue("status").asInt() + "]" );
		}
		return null;
	}
	
}
