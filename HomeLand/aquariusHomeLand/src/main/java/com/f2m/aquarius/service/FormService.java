package com.f2m.aquarius.service;

import java.util.List;
import java.util.UUID;

import com.f2m.aquarius.utils.AquariusException;
import com.f2m.aquarius.utils.GeneralUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FormService {

	private GeneralUtils gutils = new GeneralUtils();
	private DBService bd = new DBService();
	private ObjectMapper mapper = new ObjectMapper();

	// Definición de tarea:
	public List<JsonNode> getDefForm(String idDefForm, String name, String userId) throws AquariusException {
		if (userId == null || userId.length() == 0) {
			throw gutils.throwException(1501, "");
		}

		String where = "";
		if (idDefForm != null && idDefForm.length() > 0) {
			where += "data->>'id' = '" + idDefForm + "' ";
		}
		if (name != null && name.length() > 0) {
			if (where.length() > 0) {
				where += " and ";
			}
			where += "data->>'name' = '" + name + "' ";
		}
		String orderBy = "data->>'name' asc";
		List<JsonNode> defTasks = bd.selectJson("forms", where, orderBy);
		if (defTasks != null) {
			return defTasks;
		} else {
			throw gutils.throwException(1506, "");
		}
	}

	public boolean deleteDefForm(String idDefForm, String formDefName, String userId) throws AquariusException {
		if (userId == null || userId.length() == 0) {
			throw gutils.throwException(1504, "");
		}

		String where = "";
		boolean isOk = false;
		if (idDefForm != null && idDefForm.length() > 0) {
			where += "data->>'id' = '" + idDefForm + "' ";
			isOk = true;
		} else {
			if (formDefName != null && formDefName.length() > 0) {
				if (where.length() > 0) {
					where += " and ";
				}
				where += "data->>'name' = '" + formDefName + "' ";
				isOk = true;
			}
		}
		if (isOk) {
			return bd.deleteJson("forms", where);
		} else {
			throw gutils.throwException(1505, userId);
		}
	}

	public String setDefForm(String defForm, String userId) throws AquariusException {
		if (userId == null || userId.length() == 0 || defForm == null || defForm.length() == 0) {
			throw gutils.throwException(1502, "");
		}
		JsonNode defFormJson = null;
		try {
			defFormJson = mapper.readTree(defForm);
		} catch (Exception e) {
			throw gutils.throwException(1502, "");
		}
		ObjectNode newObject = mapper.createObjectNode();

		String formDefName = null;
		if (defFormJson.findValue("name") != null) {
			formDefName = defFormJson.findValue("name").asText();
			List<JsonNode> formDefExists = getDefForm(null, formDefName, userId);
			if (formDefExists != null && formDefExists.size() > 0) {
				String id = formDefExists.get(0).findValue("id").asText();
				if (deleteDefForm(id, formDefName, userId)) {
					newObject.put("id", id);
				} else {
					newObject.put("id", UUID.randomUUID().toString());
				}
			} else {
				newObject.put("id", UUID.randomUUID().toString());
			}

			newObject.put("createdBy", userId);
			newObject.putPOJO("createdOn", gutils.getTime());
			newObject.putPOJO("taskDef", defFormJson);
			if (bd.insertJson("taskdef", newObject)) {
				return newObject.findValue("id").asText();
			} else {
				throw gutils.throwException(1503, "");
			}
		} else {
			throw gutils.throwException(1502, defForm);
		}
	}
	
	// Definición de tarea:
	public List<JsonNode> getFormStats(String idDefForm, String userId) throws AquariusException {
		if (userId == null || userId.length() == 0) {
			throw gutils.throwException(1501, "");
		}
		String select = "f.data->>'label' as label, count(*) as total";
		String from = "forms f, formsdata d";
		String where = "f.data->>'name' = d.data->'formData'->>'template'";
		if (idDefForm != null && idDefForm.length() > 0) {
			if (where.length() > 0) {
				where += " and ";
			}
			where += "f.data->>'id' = '" + idDefForm + "' ";
		}
		if (userId != null && userId.length() > 0) {
			if (where.length() > 0) {
				where += " and ";
			}
			where += "upper(d.data->>'createdBy') = upper('" + userId + "') ";
		}
		String orderBy = "";
		String groupBy = "f.data->>'label'";
		
		List<JsonNode> formsStats = bd.selectColumnsJson(select, from, where, orderBy, groupBy);
		if (formsStats != null) {
			return formsStats;
		} else {
			throw gutils.throwException(1512, "");
		}
	}

	public List<JsonNode> getFormData(String idForm, String idDefForm, String userId) throws AquariusException {
		if (userId == null || userId.length() == 0) {
			throw gutils.throwException(1507, "");
		}

		String where = "";
		if (idDefForm != null && idDefForm.length() > 0) {
			where += "data->>'idDefForm' = '" + idDefForm + "' ";
		}
		if (idForm != null && idForm.length() > 0) {
			if (where.length() > 0) {
				where += " and ";
			}
			where += "data->>'id' = '" + idForm + "' ";
		}
		if (userId != null && userId.length() > 0) {
			if (where.length() > 0) {
				where += " and ";
			}
			where += "upper(data->>'createdBy') = upper('" + userId + "') ";
		}
		String orderBy = "data->>'createdOn' desc";
		List<JsonNode> formData = bd.selectJson("formsdata", where, orderBy);
		if (formData != null) {
			return formData;
		} else {
			throw gutils.throwException(1508, "");
		}
	}

	public String setFormData(String formData, String userId, String id) throws AquariusException {
		if (userId == null || userId.length() == 0 || formData == null || formData.length() == 0) {
			throw gutils.throwException(1509, "");
		}
		JsonNode formDataJson = null;
		try {
			formDataJson = mapper.readTree(formData);
		} catch (Exception e) {
			throw gutils.throwException(1509, "");
		}
		ObjectNode newObject = mapper.createObjectNode();
		if (id == null || id.length() == 0) {
			newObject.put("id", UUID.randomUUID().toString());
		} else {
			newObject.put("id", id);
		}
		newObject.put("createdBy", userId);
		newObject.putPOJO("createdOn", gutils.getTime());
		newObject.putPOJO("formData", formDataJson);
		if (bd.insertJson("formsdata", newObject)) {
			return newObject.findValue("id").asText();
		} else {
			throw gutils.throwException(1510, "");
		}
	}
}
