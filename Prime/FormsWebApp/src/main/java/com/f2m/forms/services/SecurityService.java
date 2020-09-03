package com.f2m.forms.services;

import com.f2m.forms.beans.SystemInfo;
import com.f2m.forms.utils.AquariusException;
import com.fasterxml.jackson.databind.JsonNode;

public interface SecurityService {

	public JsonNode login(String user, String password, SystemInfo systemInfo) throws AquariusException;
	public JsonNode logout(String user, String token) throws AquariusException;
	public JsonNode getUserProfile(String user, String token) throws AquariusException;
	public JsonNode getProfile(String profile, String token) throws AquariusException;
}
