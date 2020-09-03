package com.f2m.forms.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.f2m.forms.parameters.WebServiceParameters;
import com.f2m.forms.utils.AquariusException;
import com.f2m.forms.utils.PropertiesUtils;
import com.f2m.forms.utils.SSLUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service("wsCommService")
public class WsCommServiceImpl implements WsCommService {

	final static Logger logger = Logger.getLogger(WsCommServiceImpl.class);
	private WebServiceParameters webParams = null;
	private ObjectMapper mapper = new ObjectMapper();
	private String FOLDER_PATH = null;
	private String IMG_PATH = null;
	
	private void getWebParams() throws AquariusException {
		if (webParams == null) {
			try {
				logger.debug("Obteniendo parámetros de conexión a ws");
				PropertiesUtils webServiceProperties = new PropertiesUtils("webservice");
				webParams = new WebServiceParameters();
				webParams.setProtocol(webServiceProperties.getValue("protocol"));
				webParams.setHostname(webServiceProperties.getValue("hostname"));
				webParams.setPort(Integer.parseInt(webServiceProperties.getValue("port")));
				webParams.setContext(webServiceProperties.getValue("context"));
				FOLDER_PATH = webServiceProperties.getValue("folderPath");
				IMG_PATH = webServiceProperties.getValue("imgPath");
			} catch (Exception e) {
				webParams = null;
				throw new AquariusException (1601);
			}
			
		}
	}
	
	@Override
	public String getImgPath() throws AquariusException {
		getWebParams();
		return IMG_PATH;
	}
	
	@Override
	public String getFolderPath() throws AquariusException {
		getWebParams();
		return FOLDER_PATH;
	}
	
	@Override
	public String getUrl() throws AquariusException {
		getWebParams();
		if (webParams != null) {
			String url = webParams.getProtocol() + "://" + webParams.getHostname() +
					":" + webParams.getPort();
			if (webParams.getContext() != null && webParams.getContext().length() > 0) {
				url += "/" + webParams.getContext();
			}
			return url;
		} else {
			throw new AquariusException (1601);
		}
	}
	
	@Override
	public JsonNode createParam(String key, String value) {
		ObjectNode newObject = mapper.createObjectNode();
		newObject.put("key", key);
		newObject.put("value", value);
		return newObject;
	}
	
	private JsonNode toJsonNode(String json) throws AquariusException {
		try {
			//System.out.println("[WebServiceUtils] Respuesta del servicio: " + json);
			return mapper.readTree(json);
		} catch (Exception e) {
			System.out.println("[WebServiceUtils] Error al intentar convertir la respuesta del servicio a un Json: " + e.getMessage());
			throw new AquariusException(1602);
		}
	}
	
	@Override
	public JsonNode executeService(String ws, String method, List<JsonNode> args) throws AquariusException {
		try {
			//System.out.println("[WebServiceUtils] Ejecutando el servicio: " + ws);
			SSLUtils.getCertificates();
			URL url = new URL(getUrl() + ws);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			conn.setRequestProperty("Accept", "application/json");
			if (args != null) {
				for (JsonNode arg:args) {
					conn.setRequestProperty(arg.findValue("key").asText(), arg.findValue("value").asText());
				}
			}
			if (conn.getResponseCode() != 200) {
				logger.error("El servicio " + ws + " tuvo un error http con código " + conn.getResponseCode());
				throw new AquariusException(1603);
			}
			//System.out.println("[WebServiceUtils] Se obtuvo respuesta del servicio");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()), "UTF-8"));
			String output;
			String response = "";
			while ((output = br.readLine()) != null) {
				response += output;
			}
			conn.disconnect();
			return toJsonNode(response);
		} catch (MalformedURLException e) {
			logger.error("La url del servicio está mal formada: " + e.getMessage());
			throw new AquariusException(1604);
		} catch (IOException e) {
			logger.error("Error de lectura de respuesta del servicio: " + e.getMessage());
			throw new AquariusException(1605);
		}
	}
	
	@Override
	public File executeServiceFile(String ws, String method, List<JsonNode> args) throws AquariusException {
		try {
			File response = null;
			int BUFFER_SIZE = 4096;
			SSLUtils.getCertificates();
			String fileURL = getUrl() + ws;
			URL url = new URL(fileURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			conn.setRequestProperty("Accept", "application/json");
			if (args != null) {
				for (JsonNode arg:args) {
					conn.setRequestProperty(arg.findValue("key").asText(), arg.findValue("value").asText());
				}
			}
			if (conn.getResponseCode() != 200) {
				logger.error("El servicio " + ws + " tuvo un error http con código " + conn.getResponseCode());
				throw new AquariusException(1603);
			}

			String disposition = conn.getHeaderField("Content-Disposition");
			
            String fileName = "";
            
            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }
            InputStream inputStream = conn.getInputStream();
            String saveFilePath = FOLDER_PATH + "/download_" + fileName;
             
            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
            response = new File(saveFilePath);
            if (response.exists()) {
            	return response;
            } else {
            	throw new AquariusException(1606);
            }
		} catch (MalformedURLException e) {
			logger.error("La url del servicio está mal formada: " + e.getMessage());
			throw new AquariusException(1604);
		} catch (IOException e) {
			logger.error("Error de lectura de respuesta del servicio: " + e.getMessage());
			throw new AquariusException(1605);
		}
	}
	
	@Override
	public JsonNode executeServiceBody(String ws, String method, List<JsonNode> args, String body) throws AquariusException {
		try {
			//System.out.println("[WebServiceUtils] Ejecutando el servicio: " + ws);
			SSLUtils.getCertificates();
			URL url = new URL(getUrl() + ws);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			conn.setRequestProperty("Content-Type", "application/json; utf-8");
			conn.setRequestProperty("Accept", "application/json");
			if (args != null) {
				for (JsonNode arg:args) {
					conn.setRequestProperty(arg.findValue("key").asText(), arg.findValue("value").asText());
				}
			}
			conn.setDoOutput(true);
			try(OutputStream os = conn.getOutputStream()) {
			    byte[] input = body.getBytes("utf-8");
			    os.write(input, 0, input.length);           
			}
			
			if (conn.getResponseCode() != 200) {
				logger.error("El servicio " + ws + " tuvo un error http con código " + conn.getResponseCode());
				throw new AquariusException(1603);
			}
			//System.out.println("[WebServiceUtils] Se obtuvo respuesta del servicio");
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()), "UTF-8"));
			String output;
			String response = "";
			while ((output = br.readLine()) != null) {
				response += output;
			}
			conn.disconnect();
			return toJsonNode(response);
		} catch (MalformedURLException e) {
			logger.error("La url del servicio está mal formada: " + e.getMessage());
			throw new AquariusException(1604);
		} catch (IOException e) {
			logger.error("Error de lectura de respuesta del servicio: " + e.getMessage());
			throw new AquariusException(1605);
		}
	}

}
