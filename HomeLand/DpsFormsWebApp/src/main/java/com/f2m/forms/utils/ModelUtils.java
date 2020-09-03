package com.f2m.forms.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;

import com.f2m.forms.beans.Pages;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ModelUtils {

	final static Logger logger = Logger.getLogger(ModelUtils.class);
	private ObjectMapper mapper = new ObjectMapper();
	
	public Model setModel(Model model, HttpSession session, Pages actualPage) {
		
		JsonNode userAccountInfo = (JsonNode) session.getAttribute("userInfo");
		model.addAttribute("home", session.getAttribute("home").toString());
		JsonNode jsonPages = (JsonNode) session.getAttribute("pages");
		try {
			List<Pages> pages = new ArrayList<Pages>();
			for (JsonNode jsonPage:jsonPages) {
				pages.add(mapper.readValue(jsonPage.toString(), Pages.class));
			}
			model.addAttribute("pages", pages);
		} catch (IOException e) {
			logger.error("No se pudo parsear la página. " + e.getMessage());
		}
		model.addAttribute("nombreUsuario", userAccountInfo.findValue("givenName").asText() + " " + userAccountInfo.findValue("lastName").asText());
		model.addAttribute("actualPage", actualPage);
		return model;
	}
	
	public String getToken(HttpSession session) {
		JsonNode userAccountInfo = (JsonNode) session.getAttribute("userInfo");
		logger.debug(userAccountInfo.toString());
		return userAccountInfo.get("token").asText();
	}
	
	public String getUserId(HttpSession session) {
		JsonNode userAccountInfo = (JsonNode) session.getAttribute("userInfo");
		logger.debug(userAccountInfo.toString());
		return userAccountInfo.get("uid").asText();
	}
}
