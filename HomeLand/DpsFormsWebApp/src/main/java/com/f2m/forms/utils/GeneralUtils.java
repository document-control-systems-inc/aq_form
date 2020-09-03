package com.f2m.forms.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;

public class GeneralUtils {

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
	
	public String getStringUTF8(String string) throws AquariusException {
		if (string != null) {
			byte ptext[] = string.getBytes();
			try {
				return new String(ptext, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new AquariusException(517);
			}
		} else {
			return "";
		}
	}
	
	public String getBufferUTF8(HttpServletRequest request) {
		String str = "";
		String linea;
		try {
			while ((linea = request.getReader().readLine()) != null) {
			    str += linea;
			}
		} catch (Exception e) {}
		return str;
	}
}
