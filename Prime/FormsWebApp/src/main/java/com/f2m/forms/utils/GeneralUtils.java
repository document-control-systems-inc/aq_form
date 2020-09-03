package com.f2m.forms.utils;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;

public class GeneralUtils {
	final static Logger logger = Logger.getLogger(GeneralUtils.class);

	public String validateStringNull(String str) {
		if (str == null) {
			return "";
		} else {
			return str;
		}
	}
	
	public String validateJsonNullasString(JsonNode node) {
		if (node == null) {
			return "";
		} else {
			return node.asText();
		}
	}
}
