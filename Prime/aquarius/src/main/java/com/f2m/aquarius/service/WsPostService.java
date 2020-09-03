package com.f2m.aquarius.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.f2m.aquarius.utils.AquariusException;
import com.f2m.aquarius.utils.GeneralUtils;
import com.f2m.aquarius.utils.SSLUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class WsPostService {
	private GeneralUtils gutils = new GeneralUtils();
	private ObjectMapper mapper = new ObjectMapper();
	
	private JsonNode toJsonNode(String json) throws AquariusException {
		try {
			//System.out.println("[WebServiceUtils] Respuesta del servicio: " + json);
			return mapper.readTree(json);
		} catch (Exception e) {
			System.out.println("[WebServiceUtils] Error al intentar convertir la respuesta del servicio a un Json: " + e.getMessage());
			throw gutils.throwException(1602, "");
		}
	}
	
	public JsonNode createParam(String key, String value) {
		ObjectNode newObject = mapper.createObjectNode();
		newObject.put("key", key);
		newObject.put("value", value);
		return newObject;
	}
	
	public JsonNode sendPost(String strUrl, List<JsonNode> args) throws AquariusException {
		try {
			SSLUtils.getCertificates();
			URL url = new URL( strUrl );
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			conn.setRequestProperty("charset", "utf-8");
			String urlParameters = "";
			if (args != null) {
				for (JsonNode arg:args) {
					if (urlParameters.length() > 0) {
						urlParameters += "&";
					}
					urlParameters += arg.findValue("key").asText() + "=" + arg.findValue("value").asText(); 
				}
			}
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
			conn.setUseCaches(false);
			try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
			   wr.write( postData );
			}
			if (conn.getResponseCode() != 200) {
				System.out.println("El servicio " + strUrl + " tuvo un error http con código " + conn.getResponseCode());
				throw gutils.throwException(1603, "");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()), "UTF-8"));
			String output;
			String response = "";
			while ((output = br.readLine()) != null) {
				response += output;
			}
			conn.disconnect();
			return toJsonNode(response);
		} catch (MalformedURLException e) {
			System.out.println("La url del servicio está mal formada: " + e.getMessage());
			throw gutils.throwException(1604, "");
		} catch (IOException e) {
			System.out.println("Error de lectura de respuesta del servicio: " + e.getMessage());
			throw gutils.throwException(1605, "");
		}
	}
	
	
	public JsonNode sendGet(String strUrl, String token, String contentType) throws AquariusException {
		try {
			SSLUtils.getCertificates();
			URL url = new URL( strUrl );
			HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
			conn.setDoOutput(true);
			//conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", contentType); 
			conn.setRequestProperty("charset", "utf-8");
			if (token != null && token.length() > 0) {
				conn.setRequestProperty("Authorization","Bearer "+token);
			}
			
			
			if (conn.getResponseCode() != 200) {
				System.out.println("El servicio " + strUrl + " tuvo un error http con código " + conn.getResponseCode());
				throw gutils.throwException(1603, "");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()), "UTF-8"));
			String output;
			String response = "";
			while ((output = br.readLine()) != null) {
				response += output;
			}
			conn.disconnect();
			return toJsonNode(response);
		} catch (MalformedURLException e) {
			System.out.println("La url del servicio está mal formada: " + e.getMessage());
			throw gutils.throwException(1604, "");
		} catch (IOException e) {
			System.out.println("Error de lectura de respuesta del servicio: " + e.getMessage());
			throw gutils.throwException(1605, "");
		}
	}
	
	public static void main(String[] args) {
		String url = "https://asp.aquariusimaging.com/AquariusWebAPI/api/Doctypes";
		String user = "admin@pj";
		String password = "!Aquarius1$";
		WsPostService wsPost = new WsPostService();
		List<JsonNode> jsonArgs = new ArrayList<JsonNode>();
		jsonArgs.add(wsPost.createParam("username", user));
		jsonArgs.add(wsPost.createParam("password", password));
		jsonArgs.add(wsPost.createParam("grant_type", "password"));
		try {
			String token = "tQzbm0Epys6QI5XF06GLLIBNxUjPY3OLaAukjSuAqZ_IqacKNfzt_J99cF7p0z7AVteWwys6-BZrJs2lj_CIxY-FnKjjTWlQ8wBpFXUiiW1nqlcdrLV3jJQnKB43m0GhYMxHuit_KU-DqzFM4dg-Fp58LortBmWVUaRp7-eRP7xYqEyv2Z-6V_CBSi5qFAkmyx8sBlmWZ-yiapWv-dy9bTNhZ2pOifTRmkYqlXDxUwybnylEILpqO1qXyMmcFO2p";
			JsonNode response = wsPost.sendGet(url, token, "application/json");
			System.out.println(response);
		} catch (AquariusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
