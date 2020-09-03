package com.f2m.forms.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.f2m.forms.utils.AquariusException;
import com.f2m.forms.utils.SSLUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class WsTest {
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private JsonNode toJsonNode(String json) throws AquariusException {
		try {
			//System.out.println("[WebServiceUtils] Respuesta del servicio: " + json);
			return mapper.readTree(json);
		} catch (Exception e) {
			System.out.println("[WebServiceUtils] Error al intentar convertir la respuesta del servicio a un Json: " + e.getMessage());
			throw new AquariusException(1602);
		}
	}
	
	public JsonNode createParam(String key, String value) {
		ObjectNode newObject = mapper.createObjectNode();
		newObject.put("key", key);
		newObject.put("value", value);
		return newObject;
	}
	
	
	
	public JsonNode wsPost(String ws, List<JsonNode> args) throws AquariusException {
		try {
			String request = "https://asp.aquariusimaging.com/AquariusWebAPI" + ws;
			SSLUtils.getCertificates();
			URL url = new URL( request );
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
				System.out.println("El servicio " + ws + " tuvo un error http con código " + conn.getResponseCode());
				throw new AquariusException(1603);
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
			throw new AquariusException(1604);
		} catch (IOException e) {
			System.out.println("Error de lectura de respuesta del servicio: " + e.getMessage());
			throw new AquariusException(1605);
		}
	}
	
	public static void main(String[] args) {
		WsTest test = new WsTest();
		List<JsonNode> jsonArgs = new ArrayList<JsonNode>();
		try {
			jsonArgs.add(test.createParam("username", "test@oasp"));
			jsonArgs.add(test.createParam("password", "!Aquarius1$"));
			jsonArgs.add(test.createParam("grant_type", "password"));
			//test.testWs("https://asp.aquariusimaging.com/AquariusWebAPI", "/token", "POST", jsonArgs);
			JsonNode response = test.wsPost("/token", jsonArgs);
			System.out.println(response.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
