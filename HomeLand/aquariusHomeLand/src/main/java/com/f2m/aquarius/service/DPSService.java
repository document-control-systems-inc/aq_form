package com.f2m.aquarius.service;

import java.util.List;
import java.util.UUID;

import com.f2m.aquarius.utils.AquariusException;
import com.f2m.aquarius.utils.GeneralUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DPSService {

	private GeneralUtils gutils = new GeneralUtils();
	private DBService bd = new DBService();
	private ObjectMapper mapper = new ObjectMapper();
	
	
	public String setContrato(String data, String userId, String id) throws AquariusException {
		if (userId == null || userId.length() == 0 || data == null || data.length() == 0) {
			throw gutils.throwException(1509, "");
		}
		JsonNode formDataJson = null;
		try {
			formDataJson = mapper.readTree(data);
		} catch (Exception e) {
			throw gutils.throwException(1509, "");
		}
		
		if (id == null || id.length() == 0) {
			ObjectNode newObject = mapper.createObjectNode();
			newObject.put("id", UUID.randomUUID().toString());
			newObject.put("createdBy", userId);
			newObject.putPOJO("createdOn", gutils.getTime());
			newObject.putPOJO("contrato", formDataJson);
			if (bd.insertJson("contratos", newObject)) {
				return newObject.findValue("id").asText();
			} else {
				throw gutils.throwException(1510, "");
			}
		} else {
			// Si existe, se elimina para actualizar:
			String where = "data->>'id'='" + id + "'";
			bd.deleteJson("contratos", where);
			if (bd.insertJson("contratos", formDataJson)) {
				return formDataJson.findValue("id").asText();
			} else {
				throw gutils.throwException(1510, "");
			}
		}
	}
	
	public List<JsonNode> getContratos(String id, String userId) throws AquariusException {
		if (userId == null || userId.length() == 0) {
			throw gutils.throwException(1501, "");
		}

		String where = "";
		if (id != null && id.length() > 0) {
			where += "data->>'id' = '" + id + "' ";
		}
		String orderBy = "data->'contrato'->>'subvencion' asc";
		List<JsonNode> contratos = bd.selectJson("contratos", where, orderBy);
		if (contratos != null) {
			return contratos;
		} else {
			throw gutils.throwException(1506, "");
		}
	}
}
