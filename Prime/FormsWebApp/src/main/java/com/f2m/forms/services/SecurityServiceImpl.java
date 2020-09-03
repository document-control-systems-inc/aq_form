package com.f2m.forms.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2m.forms.beans.SystemInfo;
import com.f2m.forms.utils.AquariusException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

	private ObjectMapper mapper = new ObjectMapper();
	private HashMap<String,JsonNode> profiles = null;
	
	@Autowired
	WsCommService wsCommService;
	
	@Override
	public JsonNode login(String user, String password, SystemInfo systemInfo) throws AquariusException {
		if (user == null || user.length() == 0
				|| password == null || password.length() == 0) {
			throw new AquariusException(1607);
		}
		
		ObjectNode systemObject = mapper.createObjectNode();
		if (systemInfo.getId() == null || systemInfo.getId().length() == 0) {
			systemInfo.setId(UUID.randomUUID().toString());
		}
		systemObject.put("ID", systemInfo.getId());
		systemObject.put("SO", systemInfo.getOs());
		systemObject.put("Version", systemInfo.getVersion());
		systemObject.put("Browser", systemInfo.getBrowser());
		
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("user", user));
		args.add(wsCommService.createParam("password", password));
		return wsCommService.executeService("/loginAquarius", "GET", args);
	}

	@Override
	public JsonNode logout(String user, String token) throws AquariusException {
		boolean byUser = true;
		if (user == null || user.length() == 0) {
			byUser = false;
			if (token == null || token.length() == 0) {
				throw new AquariusException(1608);
			}
		}
		List<JsonNode> args = new ArrayList<JsonNode>();
		if (byUser) {
			args.add(wsCommService.createParam("accountName", user));
		} else {
			args.add(wsCommService.createParam("token", token));
		}
		return wsCommService.executeService("/logout", "GET", args);
	}

	@Override
	public JsonNode getUserProfile(String user, String token) throws AquariusException {
		if (user == null || user.length() == 0
				|| token == null || token.length() == 0) {
			throw new AquariusException(1609);
		}
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
		args.add(wsCommService.createParam("uid", user));
		JsonNode response = wsCommService.executeService("/user/info", "GET", args);
		if (response.findValue("status").asInt() == 0) {
			return response.findValue("exito");
		}
		throw new AquariusException(1610);
	}
	
	@Override
	public JsonNode getProfile(String profile, String token) throws AquariusException {
		if (profile == null || profile.length() == 0
				|| token == null || token.length() == 0) {
			throw new AquariusException(1611);
		}
		getProfiles(token);
		return profiles.get(profile);
	}
	
	private void getProfiles(String token) throws AquariusException {
		if (profiles == null) {
			List<JsonNode> args = new ArrayList<JsonNode>();
			args.add(wsCommService.createParam("token", token));
			JsonNode response = wsCommService.executeService("/profile", "GET", args);
			if (response.findValue("status").asInt() == 0) {
				JsonNode results = response.findValue("exito");
				System.out.println("Resultados de los perfiles: " + results);
				profiles = new HashMap<String,JsonNode>();
				for (JsonNode profile:results) {
					System.out.println("Perfil: " + profile.toString());
					profiles.put(profile.findValue("name").asText(), profile);
				}
			}
		}
	}
}
