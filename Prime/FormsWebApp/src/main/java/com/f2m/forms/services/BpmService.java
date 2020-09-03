package com.f2m.forms.services;

import com.f2m.forms.utils.AquariusException;
import com.fasterxml.jackson.databind.JsonNode;

public interface BpmService {

	public JsonNode getTasks(String token) throws AquariusException;
}
