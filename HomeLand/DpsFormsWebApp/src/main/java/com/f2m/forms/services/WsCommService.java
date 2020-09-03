package com.f2m.forms.services;

import java.io.File;
import java.util.List;

import com.f2m.forms.utils.AquariusException;
import com.fasterxml.jackson.databind.JsonNode;

public interface WsCommService {

	public JsonNode createParam(String key, String value);
	public JsonNode executeService(String ws, String method, List<JsonNode> args) throws AquariusException;
	public File executeServiceFile(String ws, String method, List<JsonNode> args) throws AquariusException;
	public String getUrl() throws AquariusException;
	public String getFolderPath() throws AquariusException;
}
