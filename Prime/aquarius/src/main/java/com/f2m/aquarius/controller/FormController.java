package com.f2m.aquarius.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.f2m.aquarius.beans.ServiceResponse;
import com.f2m.aquarius.beans.UserSession;
import com.f2m.aquarius.service.FormService;
import com.f2m.aquarius.service.LdapService;
import com.f2m.aquarius.service.PDFService;
import com.f2m.aquarius.utils.AquariusException;
import com.f2m.aquarius.utils.GeneralUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class FormController {

	private GeneralUtils gutils = new GeneralUtils();
	private ObjectMapper mapper = new ObjectMapper();
	private PDFService pdfService = new PDFService();
	private LdapService ldapService = new LdapService();
	private FormService formService = new FormService();
	private final String userConnected = "ecm";

	@RequestMapping(value = "/form", method = RequestMethod.GET, headers = "Accept=application/json", produces = "text/html")
	public String getFormHtml(HttpServletResponse httpResponse) {
		// response.setContentType("text/html");
		httpResponse.setCharacterEncoding("UTF-8");

		return "<html><head></head><body><img src=\"http://aquarius.f2m.com.mx/form1.jpg\" alt=\"Form01\" width=\"595\" height=\"842\" border=\"0\" usemap=\"#form01\"><form action=\"http://localhost:9090/getFormPdf\" method=\"post\"><input type=\"hidden\" name=\"template\" value=\"template01\"><input type=\"hidden\" name=\"docName\" value=\"ejemplo001\"><input style=\"position:absolute;top:91px;left:468px;width:13px;height:14px;background-color:#efe4b0;font-size:7pt\" type=\"text\" name=\"codigo_cea_1\"><input style=\"position:absolute;top:91px;left:482px;width:13px;height:14px;background-color:#efe4b0;font-size:7pt\" type=\"text\" name=\"codigo_cea_2\"><input style=\"position:absolute;top:91px;left:496px;width:13px;height:14px;background-color:#efe4b0;font-size:7pt\" type=\"text\" name=\"codigo_cea_3\"><input style=\"position:absolute;top:91px;left:510px;width:13px;height:14px;background-color:#efe4b0;font-size:7pt\" type=\"text\" name=\"codigo_cea_4\"><input style=\"position:absolute;top:91px;left:524px;width:13px;height:14px;background-color:#efe4b0;font-size:7pt\" type=\"text\" name=\"codigo_cea_5\"><input style=\"position:absolute;top:91px;left:538px;width:13px;height:14px;background-color:#efe4b0;font-size:7pt\" type=\"text\" name=\"codigo_cea_6\"><input style=\"position:absolute;top:91px;left:552px;width:13px;height:14px;background-color:#efe4b0;font-size:7pt\" type=\"text\" name=\"codigo_cea_7\"><input style=\"position:absolute;top:91px;left:566px;width:13px;height:14px;background-color:#efe4b0;font-size:7pt\" type=\"text\" name=\"codigo_cea_8\"><input style=\"position:absolute;top:117px;left:162px;width:185px;height:14px;background-color:#efe4b0;font-size:7pt;border:1px solid #ff0000\" type=\"text\" name=\"nombre_CEA\"><input style=\"position:absolute;top:137px;left:162px;width:185px;height:14px;background-color:#efe4b0;font-size:7pt;border:1px solid #ff0000\" type=\"text\" name=\"nombre_subcentro\"><input style=\"position:absolute;top:156px;left:162px;width:185px;height:14px;background-color:#efe4b0;font-size:7pt\" type=\"text\" name=\"direccion_cea\"><input style=\"position:absolute;top:175px;left:162px;width:185px;height:14px;background-color:#efe4b0;font-size:7pt\" type=\"text\" name=\"distrito_educativo\"><input style=\"position:absolute;top:130px;left:391px;width:14px;height:14px;background-color:#efe4b0;cursor:pointer;\" type=\"radio\" name=\"dependencia\" value=\"fiscal\"><input style=\"position:absolute;top:130px;left:482px;width:14px;height:14px;background-color:#efe4b0;cursor:pointer;\" type=\"radio\" name=\"dependencia\" value=\"convenio\"><input style=\"position:absolute;top:130px;left:550px;width:14px;height:14px;background-color:#efe4b0;cursor:pointer;\" type=\"radio\" name=\"dependencia\" value=\"privada\"><input style=\"position:absolute;top:171px;left:391px;width:14px;height:14px;background-color:#efe4b0;cursor:pointer;\" type=\"radio\" name=\"areafuncionamiento\" value=\"rural\"><input style=\"position:absolute;top:171px;left:482px;width:14px;height:14px;background-color:#efe4b0;cursor:pointer;\" type=\"radio\" name=\"areafuncionamiento\" value=\"urbano\"><input style=\"position:absolute;top:535px;left:580px;width:18px;height:18px;background-color:#efe4b0;cursor:pointer;\" type=\"checkbox\" name=\"requisito_matrimonio\"><input style=\"position:absolute;top:555px;left:580px;width:18px;height:18px;background-color:#efe4b0;cursor:pointer;\" type=\"checkbox\" name=\"requisito_nacimiento\"><input style=\"position:absolute;top:575px;left:580px;width:18px;height:18px;background-color:#efe4b0;cursor:pointer;\" type=\"checkbox\" name=\"requisito_militar\"><input style=\"position:absolute;top:596px;left:580px;width:18px;height:18px;background-color:#efe4b0;cursor:pointer;\" type=\"checkbox\" name=\"requisito_testigos\"><select style=\"position:absolute;top:316px;left:370px;width:27px;height:16px;background-color:#efe4b0;font-size:7pt;border:1px solid #ff0000;-webkit-appearance: none; -moz-appearance: none; text-indent: 1px;text-overflow: '';\" name=\"nacimiento_dia\"><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select><select style=\"border:1px solid #ff0000;position:absolute;top:316px;left:402px;width:27px;height:16px;background-color:#efe4b0;font-size:7pt;-webkit-appearance: none; -moz-appearance: none; text-indent: 1px;text-overflow: '';\" name=\"nacimiento_mes\"><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select><select style=\"border:1px solid #ff0000;position:absolute;top:316px;left:433px;width:54px;height:16px;background-color:#efe4b0;font-size:7pt;-webkit-appearance: none; -moz-appearance: none; text-indent: 1px;text-overflow: '';\" name=\"nacimiento_año\"><option>2000</option><option>2001</option><option>2002</option><option>2003</option><option>2004</option></select><input type=\"submit\" value=\"Enviar\"></form></body></html>";
	}

	@RequestMapping(value = "/form/formStats", method = RequestMethod.GET, headers = "Accept=application/json")
	public ServiceResponse getFormStats(@RequestHeader(required = true, value = "token") String token,
			@RequestHeader(required = false, value = "id") String id,
			@RequestHeader(required = false, value = "user") String user) {
		ServiceResponse response = new ServiceResponse();
		try {
			UserSession session = new UserSession();
			session.setSessionId(token);
			session.setHostnameConnected("localhost");
			if (ldapService.validateSession(session)) {
				String userUid = ldapService.getUidFromSession(session);
				if (user != null && user.length() > 0) {
					response.setExito(formService.getFormStats(id, user));
					response.setStatus(0);
				} else {
					if (userUid != null) {
						response.setExito(formService.getFormStats(id, userUid));
						response.setStatus(0);
					} else {
						response.setStatus(4);
					}
				}
			} else {
				response.setStatus(6);
			}
		} catch (AquariusException e) {
			response.setStatus(gutils.getErrorCode(e.getMessage()));
		}
		return response;
	}
	
	
	@RequestMapping(value = "/form/formDef", method = RequestMethod.GET, headers = "Accept=application/json")
	public ServiceResponse getFormDef(@RequestHeader(required = true, value = "token") String token,
			@RequestHeader(required = false, value = "id") String id,
			@RequestHeader(required = false, value = "name") String name) {
		ServiceResponse response = new ServiceResponse();
		try {
			UserSession session = new UserSession();
			session.setSessionId(token);
			session.setHostnameConnected("localhost");
			if (ldapService.validateSession(session)) {
				String userUid = ldapService.getUidFromSession(session);
				if (userUid != null) {
					response.setExito(formService.getDefForm(id, name, userUid));
					response.setStatus(0);
				} else {
					response.setStatus(4);
				}
			} else {
				response.setStatus(6);
			}
		} catch (AquariusException e) {
			response.setStatus(gutils.getErrorCode(e.getMessage()));
		}
		return response;
	}

	@RequestMapping(value = "/form/formDef", method = RequestMethod.PUT, headers = "Accept=application/json")
	public ServiceResponse setFormDef(@RequestHeader(required = true, value = "token") String token,
			@RequestHeader(required = false, value = "formDef") String formDef) {
		ServiceResponse response = new ServiceResponse();
		try {
			UserSession session = new UserSession();
			session.setSessionId(token);
			session.setHostnameConnected("localhost");
			if (ldapService.validateSession(session)) {
				String userUid = ldapService.getUidFromSession(session);
				if (userUid != null) {
					response.setExito(formService.setDefForm(formDef, userUid));
					response.setStatus(0);
				} else {
					response.setStatus(4);
				}
			} else {
				response.setStatus(6);
			}
		} catch (AquariusException e) {
			response.setStatus(gutils.getErrorCode(e.getMessage()));
		}
		return response;
	}

	@RequestMapping(value = "/form/formData", method = RequestMethod.GET, headers = "Accept=application/json")
	public ServiceResponse getFormData(@RequestHeader(required = true, value = "token") String token,
			@RequestHeader(required = false, value = "idDefForm") String idDefForm,
			@RequestHeader(required = false, value = "idForm") String idForm,
			@RequestHeader(required = false, value = "user") String user) {
		ServiceResponse response = new ServiceResponse();
		try {
			UserSession session = new UserSession();
			session.setSessionId(token);
			session.setHostnameConnected("localhost");
			if (ldapService.validateSession(session)) {
				String userUid = ldapService.getUidFromSession(session);
				if (user != null && user.length() > 0) {
					response.setExito(formService.getFormData(idForm, idDefForm, user));
					response.setStatus(0);
				} else {
					if (userUid != null) {
						response.setExito(formService.getFormData(idForm, idDefForm, userUid));
						response.setStatus(0);
					} else {
						response.setStatus(4);
					}
				}
			} else {
				response.setStatus(6);
			}
		} catch (AquariusException e) {
			response.setStatus(gutils.getErrorCode(e.getMessage()));
		}
		return response;
	}

	@RequestMapping(value = "/getFormPdfClaro")
	public void getFormDataClaro(HttpSession session, HttpServletRequest request, HttpServletResponse httpResponse) {
		ServiceResponse response = new ServiceResponse();
		try {
			String docName = "";
			Map<String, String[]> parameters = request.getParameterMap();
			ObjectNode newData = mapper.createObjectNode();
			ArrayNode values = newData.putArray("values");
			for (String key : parameters.keySet()) {
				if (request.getParameter(key) != null) {
					if (key.equals("template")) {
						newData.put("template", request.getParameter(key).toString());
					} else if (key.equals("docName")) {
						newData.put("docName", request.getParameter(key).toString());
						docName = request.getParameter(key).toString();
					} else {
						ObjectNode newObject = mapper.createObjectNode();
						newObject.put("param", key);
						newObject.put("value", request.getParameter(key).toString());
						System.out.println(newObject);
						values.add(newObject);
					}
				}
			}
			System.out.println(newData.toString());
			String txtTemplate = "{\"name\": \"sampleTemplate\",\"form\": "
					+ "[{\"image\": \"/IBM/WebSphere/AppServer/ADN/files/formClaro.jpg\",\"params\": "

					+ "[{\"type\": \"text\",\"name\": \"contratoCredito\",\"xcoord\": 42,\"ycoord\": 167,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"claroUpdate\",\"xcoord\": 42,\"ycoord\": 183,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"claroFinanciamiento\",\"xcoord\": 42,\"ycoord\": 203,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"bringYourOwnPhone\",\"xcoord\": 42,\"ycoord\": 216,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"garantia07\",\"xcoord\": 42,\"ycoord\": 229,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"garantiaEquipo\",\"xcoord\": 42,\"ycoord\": 241,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"compraAccesorios\",\"xcoord\": 42,\"ycoord\": 254,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"orientacion\",\"xcoord\": 42,\"ycoord\": 267,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"pagoPenalidad\",\"xcoord\": 42,\"ycoord\": 279,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"servicioFWA\",\"xcoord\": 42,\"ycoord\": 295,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"equipoPreOwned\",\"xcoord\": 42,\"ycoord\": 315,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"proteccionMovil\",\"xcoord\": 42,\"ycoord\": 328,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"orientacionFactura\",\"xcoord\": 42,\"ycoord\": 341,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"email\",\"xcoord\": 335,\"ycoord\": 339,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"miClaro\",\"xcoord\": 42,\"ycoord\": 352,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"nombreCliente\",\"xcoord\": 280,\"ycoord\": 702,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"fechaDocumento\",\"xcoord\": 280,\"ycoord\": 740,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"numeroCelular\",\"xcoord\": 280,\"ycoord\": 760,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"text\",\"name\": \"numeroEmpleado\",\"xcoord\": 280,\"ycoord\": 800,\"font\": \"Courier\",\"fontSize\": 10}"
					+ ",{\"type\": \"image\",\"name\": \"firmaCliente\",\"xcoord\": 350,\"ycoord\": 726,\"scaleX\": 600,\"scaleY\": 25}"
					+ "]}]}";
			JsonNode template = mapper.readTree(txtTemplate);
			File file = new File(pdfService.createPDFForm(template, newData, false, docName));
			if (file.exists()) {
				// InputStream inputStream = new BufferedInputStream(new
				// FileInputStream(file));
				// httpResponse.setContentLength((int)file.length());
				// String mimeType= "application/pdf";
				// httpResponse.setContentType(mimeType);
				// FileCopyUtils.copy(inputStream,
				// httpResponse.getOutputStream());
				// file.delete();
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				httpResponse.setContentLength((int) file.length());
				String mimeType = "application/pdf";
				httpResponse.setContentType(mimeType);
				httpResponse.setHeader("Content-Disposition", "inline; filename=\"" + docName + ".pdf" + "\"");
				FileCopyUtils.copy(inputStream, httpResponse.getOutputStream());
				// file.delete();
				inputStream.close();
				httpResponse.flushBuffer();
			} else {
				response.setStatus(1);
				response.setExito("Error al generar el PDF");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			response.setStatus(1);
			response.setExito("Error al generar el PDF");
			// } catch (AquariusException e) {
			// response.setStatus(gutils.getErrorCode(e.getMessage()));
		}
	}

	@RequestMapping(value = "/getFormPdfPrimePDF", method = {RequestMethod.GET, RequestMethod.POST}, headers = "Accept=application/json",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse getFormDataPrimePDF(HttpServletRequest request, HttpServletResponse httpResponse,
			@RequestHeader(required = true, value = "token") String token) {
		ServiceResponse response = new ServiceResponse();
		try {
			StringBuilder builder = new StringBuilder();
			String data = null;
			String charset = request.getCharacterEncoding();
		    if (charset == null) {
		        charset = "utf-8";
		    }
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), charset));
			String line;

			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			data = builder.toString();
		    
			ObjectNode newData;
			newData = (ObjectNode) mapper.readTree(data);
			String templateKey = newData.get("template").asText();
			boolean landscape = true;
			if (newData.get("landscape") != null) {
				landscape = newData.get("landscape").asBoolean();
			}
			UserSession session = new UserSession();
			session.setSessionId(token);
			session.setHostnameConnected("localhost");
			if (ldapService.validateSession(session)) {
				String userUid = ldapService.getUidFromSession(session);
				if (userUid != null) {
					List<JsonNode> formas = formService.getDefForm(null, templateKey, userUid);
					if (!formas.isEmpty()) {
						String docId = UUID.randomUUID().toString();
						String docName = formas.get(0).get("name").asText() + "_" + docId;
						System.out.println("Creando forma: " + docName);
						File file = null;
						if (formas.get(0).get("type") != null) {
							if (formas.get(0).get("type").asText().equals("word")) {
								file = new File(pdfService.createPDFFormFromWord(formas.get(0), newData, docName));
							}
						}
						if (file == null) {
							file = new File(pdfService.createPDFForm(formas.get(0), newData, landscape, docName));
						}
						System.out.println("Validando si el archivo se creó");
						if (file.exists()) {
							newData.put("file",file.getAbsolutePath());
							String idDoc = formService.setFormData(newData.toString(), userUid, docId);
							response.setStatus(0);
							response.setExito(idDoc);
						} else {
							response.setStatus(1511);
						}
					} else {
						response.setStatus(1501);
					}
				} else {
					response.setStatus(4);
				}
			} else {
				response.setStatus(6);
			}
		} catch (AquariusException e) {
			System.out.println("Error: " + e.getMessage());
			response.setStatus(gutils.getErrorCode(e.getMessage()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			response.setStatus(1511);
		}
		return response;
	}
	
	@RequestMapping(value = "/getFormPdfPrime", method = {RequestMethod.GET, RequestMethod.POST}, headers = "Accept=application/json")
	public void getFormDataPrime(HttpServletRequest request, HttpServletResponse httpResponse) {
		ServiceResponse response = new ServiceResponse();
		try {
			
			Map<String, String[]> parameters = request.getParameterMap();
			ObjectNode newData = mapper.createObjectNode();
			ArrayNode values = newData.putArray("values");
			String templateKey = "";
			String token = "";
			boolean landscape = true;
			for (String key : parameters.keySet()) {
				if (request.getParameter(key) != null) {
					if (key.equals("template")) {
						newData.put("template", request.getParameter(key).toString());
						templateKey = request.getParameter(key).toString();
					} else if (key.equals("landscape")) {
						landscape = false;
					} else if (key.equals("userToken")) {
						token = request.getParameter(key).toString();
					} else {
						ObjectNode newObject = mapper.createObjectNode();
						newObject.put("param", key);
						newObject.put("value", request.getParameter(key).toString());
						values.add(newObject);
					}
				}
			}
			UserSession session = new UserSession();
			session.setSessionId(token);
			session.setHostnameConnected("localhost");
			if (ldapService.validateSession(session)) {
				String userUid = ldapService.getUidFromSession(session);
				if (userUid != null) {
					List<JsonNode> formas = formService.getDefForm(null, templateKey, userUid);
					if (!formas.isEmpty()) {
						String docId = UUID.randomUUID().toString();
						String docName = formas.get(0).get("name").asText() + "_" + docId;
						File file = new File(pdfService.createPDFForm(formas.get(0), newData, landscape, docName));
						if (file.exists()) {
							// InputStream inputStream = new BufferedInputStream(new
							// FileInputStream(file));
							// httpResponse.setContentLength((int)file.length());
							// String mimeType= "application/pdf";
							// httpResponse.setContentType(mimeType);
							// FileCopyUtils.copy(inputStream,
							// httpResponse.getOutputStream());
							// file.delete();
							newData.put("file",file.getAbsolutePath());
							formService.setFormData(newData.toString(), userUid, docId);
							InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
							httpResponse.setContentLength((int) file.length());
							String mimeType = "application/pdf";
							httpResponse.setContentType(mimeType);
							httpResponse.setHeader("Content-Disposition", "inline; filename=\"" + docName + ".pdf" + "\"");
							FileCopyUtils.copy(inputStream, httpResponse.getOutputStream());
							// file.delete();
							inputStream.close();
							httpResponse.flushBuffer();
						} else {
							response.setStatus(1511);
						}
					} else {
						response.setStatus(1501);
					}
				} else {
					response.setStatus(4);
				}
			} else {
				response.setStatus(6);
			}
		} catch (AquariusException e) {
			response.setStatus(gutils.getErrorCode(e.getMessage()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			response.setStatus(1511);
		}
	}
	
	@RequestMapping(value = "/getFormDps", method = RequestMethod.GET, headers = "Accept=application/json")
	public void getFormDps(HttpSession session, HttpServletRequest request, HttpServletResponse httpResponse) {
		ServiceResponse response = new ServiceResponse();
		try {
			String docName = "";
			Map<String, String[]> parameters = request.getParameterMap();
			ObjectNode newData = mapper.createObjectNode();
			ArrayNode values = newData.putArray("values");
			for (String key : parameters.keySet()) {
				if (request.getParameter(key) != null) {
					if (key.equals("template")) {
						newData.put("template", request.getParameter(key).toString());
					} else if (key.equals("docName")) {
						newData.put("docName", request.getParameter(key).toString());
						docName = request.getParameter(key).toString();
					} else {
						ObjectNode newObject = mapper.createObjectNode();
						newObject.put("param", key);
						newObject.put("value", request.getParameter(key).toString());
						System.out.println(newObject);
						values.add(newObject);
					}
				}
			}
			System.out.println(newData.toString());
			
			String txtTemplate = "{\"form\": [{\"image\": \"/IBM/WebSphere/AppServer/ADN/files/formatoDPS.jpg\",\"params\": ["
					
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"numPeticion\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 233,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"account\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 256,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"tipoOperacion\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 279,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"subvencion\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 301,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"numContrato\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 324,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"motivoPeticion\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 346,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"concepto\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 368,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"itemSolicitado\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 390,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"justificacion\",\"type\": \"text\",\"xcoord\": 170,\"ycoord\": 411,\"fontSize\": 10},"
	
				
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"cuantiaSolicitada\",\"type\": \"text\",\"xcoord\": 412,\"ycoord\": 256,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"cuantiaAprobada\",\"type\": \"text\",\"xcoord\": 412,\"ycoord\": 279,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"cuantiaAjustada\",\"type\": \"text\",\"xcoord\": 412,\"ycoord\": 301,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"peticionario\",\"type\": \"text\",\"xcoord\": 412,\"ycoord\": 324,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"puesto\",\"type\": \"text\",\"xcoord\": 412,\"ycoord\": 346,\"fontSize\": 10},"
				
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_solicitud_adelanto_fondos\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 495,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_solicitud_adelanto_fondos\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 495,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_solicitud_adelanto_fondos\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 495,\"fontSize\": 10},"
				
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_informe_horas_extras\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 509,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_informe_horas_extras\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 509,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_informe_horas_extras\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 509,\"fontSize\": 10},"
				
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_hojas_asistencia\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 523,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_hojas_asistencia\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 523,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_hojas_asistencia\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 523,\"fontSize\": 10},"
				
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_cheque_cancelado\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 537,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_cheque_cancelado\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 537,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_cheque_cancelado\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 537,\"fontSize\": 10},"
				
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_otros\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 551,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_otros\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 551,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_otros\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 551,\"fontSize\": 10},"
				
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_gerentes_budget\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 565,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_gerentes_budget\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 565,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_gerentes_budget\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 565,\"fontSize\": 10},"
				
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"chk_finanzas_prifas\",\"type\": \"text\",\"xcoord\": 48,\"ycoord\": 579,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"adj_finanzas_prifas\",\"type\": \"text\",\"xcoord\": 275,\"ycoord\": 579,\"fontSize\": 10},"
				+ "{\"font\": \"Helvetica-Bold\",\"name\":\"txt_finanzas_prifas\",\"type\": \"text\",\"xcoord\": 385,\"ycoord\": 579,\"fontSize\": 10}"
				
				+ "]}"
				
				+ ",{\"image\": \"\",\"params\": ["
				+ "{\"name\":\"justificacion_path\",\"type\": \"file\"}"
				+ "]}"
				
				+ ",{\"image\": \"\",\"params\": ["
				+ "{\"name\":\"adj_solicitud_adelanto_fondos_path\",\"type\": \"file\"}"
				+ "]}"
				
				+ ",{\"image\": \"\",\"params\": ["
				+ "{\"name\":\"adj_informe_horas_extras_path\",\"type\": \"file\"}"
				+ "]}"
				
				+ ",{\"image\": \"\",\"params\": ["
				+ "{\"name\":\"adj_hojas_asistencia_path\",\"type\": \"file\"}"
				+ "]}"
				
				+ ",{\"image\": \"\",\"params\": ["
				+ "{\"name\":\"adj_cheque_cancelado_path\",\"type\": \"file\"}"
				+ "]}"
				
				+ ",{\"image\": \"\",\"params\": ["
				+ "{\"name\":\"adj_otros_path\",\"type\": \"file\"}"
				+ "]}"
				
				+ ",{\"image\": \"\",\"params\": ["
				+ "{\"name\":\"adj_gerentes_budget_path\",\"type\": \"file\"}"
				+ "]}"
				
				+ ",{\"image\": \"\",\"params\": ["
				+ "{\"name\":\"adj_finanzas_prifas_path\",\"type\": \"file\"}"
				+ "]}"
				
				+ "],"
				+ "\"name\": \"HomeLand\",\"label\": \"HomeLand\"}";
			JsonNode template = mapper.readTree(txtTemplate);
			String docId = UUID.randomUUID().toString();
			if (docName.length() == 0) {
				docName = "homeland";
			}
			docName += "_" + docId;
			File file = new File(pdfService.createPDFForm(template, newData, false, docName));
			if (file.exists()) {
				// InputStream inputStream = new BufferedInputStream(new
				// FileInputStream(file));
				// httpResponse.setContentLength((int)file.length());
				// String mimeType= "application/pdf";
				// httpResponse.setContentType(mimeType);
				// FileCopyUtils.copy(inputStream,
				// httpResponse.getOutputStream());
				// file.delete();
				newData.put("file",file.getAbsolutePath());
				formService.setFormData(newData.toString(), userConnected, docId);
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				httpResponse.setContentLength((int) file.length());
				String mimeType = "application/pdf";
				httpResponse.setContentType(mimeType);
				httpResponse.setHeader("Content-Disposition", "inline; filename=\"" + docName + ".pdf" + "\"");
				FileCopyUtils.copy(inputStream, httpResponse.getOutputStream());
				// file.delete();
				inputStream.close();
				httpResponse.flushBuffer();
			} else {
				response.setStatus(1511);
			}
			
		} catch (AquariusException e) {
			response.setStatus(gutils.getErrorCode(e.getMessage()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			response.setStatus(1511);
		}
	}
	
	/*
	@RequestMapping(value = "/getFormDps", method = {RequestMethod.GET, RequestMethod.POST}, headers = "Accept=application/json")
	public void getFormDps(HttpServletRequest request, HttpServletResponse httpResponse) {
		ServiceResponse response = new ServiceResponse();
		try {
			Map<String, String[]> parameters = request.getParameterMap();
			ObjectNode newData = mapper.createObjectNode();
			ArrayNode values = newData.putArray("values");
			String templateKey = "";
			for (String key : parameters.keySet()) {
				if (request.getParameter(key) != null) {
					if (key.equals("template")) {
						newData.put("template", request.getParameter(key).toString());
						templateKey = request.getParameter(key).toString();
					} else {
						ObjectNode newObject = mapper.createObjectNode();
						newObject.put("param", key);
						newObject.put("value", request.getParameter(key).toString());
						values.add(newObject);
					}
				}
			}
			
			
			
			List<JsonNode> formas = formService.getDefForm(null, templateKey, userConnected);
			if (!formas.isEmpty()) {
				String docId = UUID.randomUUID().toString();
				String docName = formas.get(0).get("name").asText() + "_" + docId;
				File file = new File(pdfService.createPDFForm(formas.get(0), newData, true, docName));
				if (file.exists()) {
					// InputStream inputStream = new BufferedInputStream(new
					// FileInputStream(file));
					// httpResponse.setContentLength((int)file.length());
					// String mimeType= "application/pdf";
					// httpResponse.setContentType(mimeType);
					// FileCopyUtils.copy(inputStream,
					// httpResponse.getOutputStream());
					// file.delete();
					newData.put("file",file.getAbsolutePath());
					formService.setFormData(newData.toString(), userConnected, docId);
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					httpResponse.setContentLength((int) file.length());
					String mimeType = "application/pdf";
					httpResponse.setContentType(mimeType);
					httpResponse.setHeader("Content-Disposition", "inline; filename=\"" + docName + ".pdf" + "\"");
					FileCopyUtils.copy(inputStream, httpResponse.getOutputStream());
					// file.delete();
					inputStream.close();
					httpResponse.flushBuffer();
				} else {
					response.setStatus(1511);
				}
			} else {
				response.setStatus(1501);
			}
		} catch (AquariusException e) {
			response.setStatus(gutils.getErrorCode(e.getMessage()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			response.setStatus(1511);
		}
	}
	*/

	@RequestMapping(value = "/getFormPdf")
	public void getFormData(HttpSession session, HttpServletRequest request, HttpServletResponse httpResponse) {
		ServiceResponse response = new ServiceResponse();
		try {
			String docName = "";
			Map<String, String[]> parameters = request.getParameterMap();
			ObjectNode newData = mapper.createObjectNode();
			ArrayNode values = newData.putArray("values");
			for (String key : parameters.keySet()) {
				if (request.getParameter(key) != null) {
					if (key.equals("template")) {
						newData.put("template", request.getParameter(key).toString());
					} else if (key.equals("docName")) {
						newData.put("docName", request.getParameter(key).toString());
						docName = request.getParameter(key).toString();
					} else {
						ObjectNode newObject = mapper.createObjectNode();
						newObject.put("param", key);
						newObject.put("value", request.getParameter(key).toString());
						System.out.println(newObject);
						values.add(newObject);
					}
				}
			}
			System.out.println(newData.toString());
			String txtTemplate = "{\"name\": \"sampleTemplate\",\"form\": "
					+ "[{\"image\": \"/IBM/WebSphere/AppServer/ADN/files/formUniversal.jpg\",\"params\": "

					+ "[{\"type\": \"text\",\"name\": \"numero_contrato\",\"xcoord\": 500,\"ycoord\": 165,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"nombre_comprador\",\"xcoord\": 74,\"ycoord\": 192,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"fecha_mes\",\"xcoord\": 450,\"ycoord\": 192,\"font\": \"Courier\",\"fontSize\": 10}, "
					+ "{\"type\": \"text\",\"name\": \"fecha_dia\",\"xcoord\": 490,\"ycoord\": 192,\"font\": \"Courier\",\"fontSize\": 10}, "
					+ "{\"type\": \"text\",\"name\": \"fecha_anio\",\"xcoord\": 520,\"ycoord\": 192,\"font\": \"Courier\",\"fontSize\": 10}, "

					+ "{\"type\": \"text\",\"name\": \"direccion_numero\",\"xcoord\": 74,\"ycoord\": 212,\"font\": \"Courier\",\"fontSize\": 10}, "
					+ "{\"type\": \"text\",\"name\": \"direccion_calle\",\"xcoord\": 185,\"ycoord\": 212,\"font\": \"Courier\",\"fontSize\": 10}, "
					+ "{\"type\": \"text\",\"name\": \"direccion_urbanizacion\",\"xcoord\": 350,\"ycoord\": 212,\"font\": \"Courier\",\"fontSize\": 10}, "

					+ "{\"type\": \"text\",\"name\": \"direccion_ciudad\",\"xcoord\": 30,\"ycoord\": 230,\"font\": \"Courier\",\"fontSize\": 10}, "
					+ "{\"type\": \"text\",\"name\": \"direccion_estado\",\"xcoord\": 310,\"ycoord\": 230,\"font\": \"Courier\",\"fontSize\": 10}, "
					+ "{\"type\": \"text\",\"name\": \"direccion_zip\",\"xcoord\": 450,\"ycoord\": 230,\"font\": \"Courier\",\"fontSize\": 10}, "

					+ "{\"type\": \"text\",\"name\": \"direccion_postal\",\"xcoord\": 74,\"ycoord\": 248,\"font\": \"Courier\",\"fontSize\": 10}, "

					+ "{\"type\": \"text\",\"name\": \"telefono_residencial_lada\",\"xcoord\": 74,\"ycoord\": 263,\"font\": \"Courier\",\"fontSize\": 10}, "
					+ "{\"type\": \"text\",\"name\": \"telefono_residencial_numero\",\"xcoord\": 95,\"ycoord\": 263,\"font\": \"Courier\",\"fontSize\": 10}, "
					+ "{\"type\": \"text\",\"name\": \"telefono_trabajo_lada\",\"xcoord\": 280,\"ycoord\": 263,\"font\": \"Courier\",\"fontSize\": 10}, "
					+ "{\"type\": \"text\",\"name\": \"telefono_trabajo_numero\",\"xcoord\": 301,\"ycoord\": 263,\"font\": \"Courier\",\"fontSize\": 10},"
					+ " {\"type\": \"text\",\"name\": \"telefono_celular_lada\",\"xcoord\": 445,\"ycoord\": 263,\"font\": \"Courier\",\"fontSize\": 10}, "
					+ "{\"type\": \"text\",\"name\": \"telefono_celular_numero\",\"xcoord\": 466,\"ycoord\": 263,\"font\": \"Courier\",\"fontSize\": 10}, "

					+ "{\"type\": \"text\",\"name\": \"email\",\"xcoord\": 74,\"ycoord\": 278,\"font\": \"Courier\",\"fontSize\": 10}, "

					+ "{\"type\": \"text\",\"name\": \"calentador_cantidad\",\"xcoord\": 30,\"ycoord\": 329,\"font\": \"Courier\",\"fontSize\": 12},"
					+ " {\"type\": \"text\",\"name\": \"calentador_adicional\",\"xcoord\": 280,\"ycoord\": 329,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"calentador_importe\",\"xcoord\": 490,\"ycoord\": 329,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"calentador_centavos\",\"xcoord\": 545,\"ycoord\": 329,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"tanque_cantidad\",\"xcoord\": 30,\"ycoord\": 341,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"tanque_adicional\",\"xcoord\": 180,\"ycoord\": 341,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"tanque_importe\",\"xcoord\": 490,\"ycoord\": 341,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"tanque_centavos\",\"xcoord\": 545,\"ycoord\": 341,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"colectores_cantidad\",\"xcoord\": 30,\"ycoord\": 353,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"colectores_importe\",\"xcoord\": 490,\"ycoord\": 353,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"colectores_centavos\",\"xcoord\": 545,\"ycoord\": 353,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"calentador_piscina_cantidad\",\"xcoord\": 30,\"ycoord\": 365,\"font\": \"Courier\",\"fontSize\": 12},"
					+ " {\"type\": \"text\",\"name\": \"calentador_piscina_importe\",\"xcoord\": 490,\"ycoord\": 365,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"calentador_piscina_centavos\",\"xcoord\": 545,\"ycoord\": 365,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"caja_cantidad\",\"xcoord\": 30,\"ycoord\": 377,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"caja_importe\",\"xcoord\": 490,\"ycoord\": 377,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"caja_centavos\",\"xcoord\": 545,\"ycoord\": 377,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"bomba_cantidad\",\"xcoord\": 30,\"ycoord\": 388,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"bomba_importe\",\"xcoord\": 490,\"ycoord\": 388,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"bomba_centavos\",\"xcoord\": 545,\"ycoord\": 388,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"osmosis_cantidad\",\"xcoord\": 30,\"ycoord\": 400,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"osmosis_importe\",\"xcoord\": 490,\"ycoord\": 400,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"osmosis_centavos\",\"xcoord\": 545,\"ycoord\": 400,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"suavizador_cantidad\",\"xcoord\": 30,\"ycoord\": 412,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"suavizador_importe\",\"xcoord\": 490,\"ycoord\": 412,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"suavizador_centavos\",\"xcoord\": 545,\"ycoord\": 412,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"fotovoltica_cantidad\",\"xcoord\": 30,\"ycoord\": 423,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"fotovoltica_importe\",\"xcoord\": 490,\"ycoord\": 423,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"fotovoltica_centavos\",\"xcoord\": 545,\"ycoord\": 423,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"panel_cantidad\",\"xcoord\": 30,\"ycoord\": 434,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"panel_importe\",\"xcoord\": 490,\"ycoord\": 434,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"panel_centavos\",\"xcoord\": 545,\"ycoord\": 434,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"inversores_cantidad\",\"xcoord\": 30,\"ycoord\": 446,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"inversores_importe\",\"xcoord\": 490,\"ycoord\": 446,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"inversores_centavos\",\"xcoord\": 545,\"ycoord\": 446,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"bateria_cantidad\",\"xcoord\": 30,\"ycoord\": 458,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"bateria_importe\",\"xcoord\": 490,\"ycoord\": 458,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"bateria_centavos\",\"xcoord\": 545,\"ycoord\": 458,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"controlador_cantidad\",\"xcoord\": 30,\"ycoord\": 470,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"controlador_importe\",\"xcoord\": 490,\"ycoord\": 470,\"font\": \"Courier\",\"fontSize\": 12},"
					+ " {\"type\": \"text\",\"name\": \"controlador_centavos\",\"xcoord\": 545,\"ycoord\": 470,\"font\": \"Courier\",\"fontSize\": 12},"

					+ " {\"type\": \"text\",\"name\": \"adicional_cantidad\",\"xcoord\": 30,\"ycoord\": 480,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"adicional_texto\",\"xcoord\": 100,\"ycoord\": 480,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"adicional_importe\",\"xcoord\": 490,\"ycoord\": 480,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"adicional_centavos\",\"xcoord\": 545,\"ycoord\": 480,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"adicional2_cantidad\",\"xcoord\": 30,\"ycoord\": 492,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"adicional2_texto\",\"xcoord\": 100,\"ycoord\": 492,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"adicional2_importe\",\"xcoord\": 490,\"ycoord\": 492,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"adicional2_centavos\",\"xcoord\": 545,\"ycoord\": 492,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"precio_total_importe\",\"xcoord\": 490,\"ycoord\": 508,\"font\": \"Courier\",\"fontSize\": 13}, "
					+ "{\"type\": \"text\",\"name\": \"precio_total_centavos\",\"xcoord\": 545,\"ycoord\": 508,\"font\": \"Courier\",\"fontSize\": 13}, "

					+ "{\"type\": \"text\",\"name\": \"impuesto_importe\",\"xcoord\": 490,\"ycoord\": 530,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"impuesto_centavos\",\"xcoord\": 545,\"ycoord\": 530,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"total_venta_importe\",\"xcoord\": 490,\"ycoord\": 552,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"total_venta_centavos\",\"xcoord\": 545,\"ycoord\": 552,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"pronto_pago_importe\",\"xcoord\": 490,\"ycoord\": 574,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"pronto_pago_centavos\",\"xcoord\": 545,\"ycoord\": 574,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"balance_importe\",\"xcoord\": 490,\"ycoord\": 596,\"font\": \"Courier\",\"fontSize\": 12}, "
					+ "{\"type\": \"text\",\"name\": \"balance_centavos\",\"xcoord\": 545,\"ycoord\": 596,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"text\",\"name\": \"nombre_vendedor\",\"xcoord\": 30,\"ycoord\": 760,\"font\": \"Courier\",\"fontSize\": 12}, "

					+ "{\"type\": \"image\",\"name\": \"firma_conforme\",\"xcoord\": 470,\"ycoord\": 760,\"scaleX\": 200,\"scaleY\": 30}]}]}";
			JsonNode template = mapper.readTree(txtTemplate);
			File file = new File(pdfService.createPDFForm(template, newData, false, docName));
			if (file.exists()) {
				// InputStream inputStream = new BufferedInputStream(new
				// FileInputStream(file));
				// httpResponse.setContentLength((int)file.length());
				// String mimeType= "application/pdf";
				// httpResponse.setContentType(mimeType);
				// FileCopyUtils.copy(inputStream,
				// httpResponse.getOutputStream());
				// file.delete();
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				httpResponse.setContentLength((int) file.length());
				String mimeType = "application/pdf";
				httpResponse.setContentType(mimeType);
				httpResponse.setHeader("Content-Disposition", "inline; filename=\"" + docName + ".pdf" + "\"");
				FileCopyUtils.copy(inputStream, httpResponse.getOutputStream());
				// file.delete();
				inputStream.close();
				httpResponse.flushBuffer();
			} else {
				response.setStatus(1);
				response.setExito("Error al generar el PDF");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			response.setStatus(1);
			response.setExito("Error al generar el PDF");
			// } catch (AquariusException e) {
			// response.setStatus(gutils.getErrorCode(e.getMessage()));
		}
	}
	
	@RequestMapping(value = "/getFormPdfTest", method = {RequestMethod.GET, RequestMethod.POST}, headers = "Accept=application/json")
	public void getFormDataTest(HttpServletRequest request, HttpServletResponse httpResponse) {
		ServiceResponse response = new ServiceResponse();
		try {
			
			Map<String, String[]> parameters = request.getParameterMap();
			ObjectNode newData = mapper.createObjectNode();
			ArrayNode values = newData.putArray("values");
			String templateKey = "";
			String pdfTemplate = "";
			boolean landscape = true;
			for (String key : parameters.keySet()) {
				if (request.getParameter(key) != null) {
					if (key.equals("template")) {
						newData.put("template", request.getParameter(key).toString());
						templateKey = request.getParameter(key).toString();
					} else if (key.equals("landscape")) {
						landscape = false;
					} else if (key.equals("pdfTemplate")) {
						pdfTemplate = request.getParameter(key).toString();
					}
					else {
						ObjectNode newObject = mapper.createObjectNode();
						newObject.put("param", key);
						newObject.put("value", request.getParameter(key).toString());
						values.add(newObject);
					}
				}
			}
			List<JsonNode> formas = null;
			if (pdfTemplate.length() > 0) {
				String strJson = "{ \"form\": [" +
	"{ 	\"image\": \"/opt/ecm/formsTemplate/requisicion_materiales_1.jpg\", " + 
		"\"params\": [ " +
	
"{ \"font\": \"Courier\", \"name\": \"proyecto\", \"type\": \"text\", \"xcoord\": 90, \"ycoord\": 62, \"fontSize\": 6 }," +
			"{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 97, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 97, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80060_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 105, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80060_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 105, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80020_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 112, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80020_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 112, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80070_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 119, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80070_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 119, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80030_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 126, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80030_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 126, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80100_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 134, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80100_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 134, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80095_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 142, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80095_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 142, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80135_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 149, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80135_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 149, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80045_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 157, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80045_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 157, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80050_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 164, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80050_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 164, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80065_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 172, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80065_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 172, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80085_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 179, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80085_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 179, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 187, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 187, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 203, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 203, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 211, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 211, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 218, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 218, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 226, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 226, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 234, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 234, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 242, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 242, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 249, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 249, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 256, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 256, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 264, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 264, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 279, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 279, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 287, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 287, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 295, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 295, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 302, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 302, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 310, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 310, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 317, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 317, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 325, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 325, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 332, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 332, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 340, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 340, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 355, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 355, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 363, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 363, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 370, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 370, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 378, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 378, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 393, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 393, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 400, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 400, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 415, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 415, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 423, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 423, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 430, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 430, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 438, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 438, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 445, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 445, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 453, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 453, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 460, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 460, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 475, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 475, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 483, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"t1_subtotal_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 500, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"t1_subtotal_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 500, \"fontSize\": 6 }," +
 
 
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 97, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 97, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80060_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 105, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80060_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 105, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80020_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 112, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80020_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 112, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80070_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 119, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80070_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 119, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80030_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 126, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80030_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 126, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80100_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 134, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80100_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 134, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80095_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 142, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80095_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 142, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80045_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 157, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80045_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 157, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80050_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 164, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80050_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 164, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80065_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 172, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80065_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 172, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80085_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 179, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 187, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 187, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 203, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 203, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 211, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 211, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 218, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 218, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 234, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 234, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 242, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 242, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 249, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 249, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 256, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 256, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 264, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 264, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 272, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 272, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 279, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 279, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 287, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 287, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 302, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 302, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 310, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 310, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 317, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 325, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 325, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 332, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 340, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 340, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 355, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 355, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 363, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 363, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 370, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 370, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 378, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 378, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 385, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 385, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 393, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 393, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 407, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 407, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 415, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 415, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 423, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 423, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 430, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 430, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 438, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 438, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 445, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 445, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 453, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 453, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 460, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 460, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 468, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 468, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 475, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 475, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 483, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 483, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"t2_subtotal_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 500, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"t2_subtotal_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 500, \"fontSize\": 6 }] }," +
 
 
 "{ \"image\": \"/opt/ecm/formsTemplate/requisicion_materiales_2.jpg\", \"params\": [" + 
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 61, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 61, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 68, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 68, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 75, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 75, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 83, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 83, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 90, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 90, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 98, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 98, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80060_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 106, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80060_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 106, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80020_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 113, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80030_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 128, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80100_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 136, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80095_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 144, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80135_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 151, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80045_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 159, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80050_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 166, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80065_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 174, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 189, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 189, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 196, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 196, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 203, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 203, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 212, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 212, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 219, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 219, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 227, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 227, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 235, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 235, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 250, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 257, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 265, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 272, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 280, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 288, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 303, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 311, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 318, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 326, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 333, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 341, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 348, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 356, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 364, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 371, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 379, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 387, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 394, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 401, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 408, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 416, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 424, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 431, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 431, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 439, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 439, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 447, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 447, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 455, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 463, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 470, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 477, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 485, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 500, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 500, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 508, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 508, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 516, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 524, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"t3_subtotal_ea\", \"type\": \"text\", \"xcoord\": 330, \"ycoord\": 539, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"t3_subtotal_cs\", \"type\": \"text\", \"xcoord\": 365, \"ycoord\": 539, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 61, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 61, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 68, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 68, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 75, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 75, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 83, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 83, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 90, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 90, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 98, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80035_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 98, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80060_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 106, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80060_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 106, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80020_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 113, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80020_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 113, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80020_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 120, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80020_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 120, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80030_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 128, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80030_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 128, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80100_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 136, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80100_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 136, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80095_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 144, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80095_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 144, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80135_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 151, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80135_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 151, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80045_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 159, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80045_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 159, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80050_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 166, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80050_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 166, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80065_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 174, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80065_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 174, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 189, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 196, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 203, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 212, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 219, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 227, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 235, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 243, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 257, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 257, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 265, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 265, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 272, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 272, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 280, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 280, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 288, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 288, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 296, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 296, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 303, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 303, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 318, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 318, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 326, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 326, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 333, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 333, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 341, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 341, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 348, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 348, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 356, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 364, \"fontSize\": 6 }," +
 
 

"{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 379, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 379, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 386, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 386, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 394, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 394, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 402, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 402, \"fontSize\": 6 }," +

"{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 409, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 409, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 417, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 417, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 425, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 425, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 432, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 432, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 440, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 440, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 447, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 447, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 455, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 455, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 462, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 462, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 470, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"paper_80145_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 470, \"fontSize\": 6 }," +
 
"{ \"font\": \"Courier\", \"name\": \"id_adicional_1_ea\", \"type\": \"text\", \"xcoord\": 402, \"ycoord\": 485, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"descripcion_adicional_1_ea\", \"type\": \"text\", \"xcoord\": 468, \"ycoord\": 485, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"adicional_1_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 485, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"adicional_1_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 485, \"fontSize\": 6 }," +

"{ \"font\": \"Courier\", \"name\": \"id_adicional_2_ea\", \"type\": \"text\", \"xcoord\": 402, \"ycoord\": 492, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"descripcion_adicional_2_ea\", \"type\": \"text\", \"xcoord\": 468, \"ycoord\": 492, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"adicional_2_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 492, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"adicional_2_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 492, \"fontSize\": 6 }," +

"{ \"font\": \"Courier\", \"name\": \"id_adicional_3_ea\", \"type\": \"text\", \"xcoord\": 402, \"ycoord\": 500, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"descripcion_adicional_3_ea\", \"type\": \"text\", \"xcoord\": 468, \"ycoord\": 500, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"adicional_3_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 500, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"adicional_3_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 500, \"fontSize\": 6 }," +

"{ \"font\": \"Courier\", \"name\": \"id_adicional_4_ea\", \"type\": \"text\", \"xcoord\": 402, \"ycoord\": 507, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"descripcion_adicional_4_ea\", \"type\": \"text\", \"xcoord\": 468, \"ycoord\": 507, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"adicional_4_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 507, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"adicional_4_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 507, \"fontSize\": 6 }," +

"{ \"font\": \"Courier\", \"name\": \"id_adicional_5_ea\", \"type\": \"text\", \"xcoord\": 402, \"ycoord\": 515, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"descripcion_adicional_5_ea\", \"type\": \"text\", \"xcoord\": 468, \"ycoord\": 515, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"adicional_5_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 515, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"adicional_5_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 515, \"fontSize\": 6 }," +
 
 
 "{ \"font\": \"Courier\", \"name\": \"t4_subtotal_ea\", \"type\": \"text\", \"xcoord\": 675, \"ycoord\": 530, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"t4_subtotal_cs\", \"type\": \"text\", \"xcoord\": 710, \"ycoord\": 530, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"budget_mensual\", \"type\": \"text\", \"xcoord\": 470, \"ycoord\": 538, \"fontSize\": 6 }," +
 "{ \"font\": \"Courier\", \"name\": \"gran_total\", \"type\": \"text\", \"xcoord\": 680, \"ycoord\": 538, \"fontSize\": 6 }," + 
 
"{ \"font\": \"Courier\", \"name\": \"supervisor\", \"type\": \"text\", \"xcoord\": 478, \"ycoord\": 545, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"supervisor_fecha\", \"type\": \"text\", \"xcoord\": 685, \"ycoord\": 545, \"fontSize\": 6 }," +

"{ \"font\": \"Courier\", \"name\": \"gerente\", \"type\": \"text\", \"xcoord\": 505, \"ycoord\": 552, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"gerente_fecha\", \"type\": \"text\", \"xcoord\": 685, \"ycoord\": 552, \"fontSize\": 6 }," +

"{ \"font\": \"Courier\", \"name\": \"recibido_por\", \"type\": \"text\", \"xcoord\": 450, \"ycoord\": 560, \"fontSize\": 6 }," +
"{ \"font\": \"Courier\", \"name\": \"recibido_por_fecha\", \"type\": \"text\", \"xcoord\": 685, \"ycoord\": 560, \"fontSize\": 6 }" +
 
 "] }], \"name\": \"requisicion\" }";
				formas = new ArrayList<JsonNode>();
				JsonNode jsonForm = mapper.readTree(strJson);
				System.out.println("Template de Prueba: " + jsonForm.toString());
				formas.add(jsonForm);
			} else {
				formas = formService.getDefForm(null, templateKey, userConnected);
			}
			if (!formas.isEmpty()) {
				String docId = UUID.randomUUID().toString();
				String docName = formas.get(0).get("name").asText() + "_" + docId;
				File file = new File(pdfService.createPDFForm(formas.get(0), newData, landscape, docName));
				if (file.exists()) {
					// InputStream inputStream = new BufferedInputStream(new
					// FileInputStream(file));
					// httpResponse.setContentLength((int)file.length());
					// String mimeType= "application/pdf";
					// httpResponse.setContentType(mimeType);
					// FileCopyUtils.copy(inputStream,
					// httpResponse.getOutputStream());
					// file.delete();
					newData.put("file",file.getAbsolutePath());
					//formService.setFormData(newData.toString(), userConnected, docId);
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					httpResponse.setContentLength((int) file.length());
					String mimeType = "application/pdf";
					httpResponse.setContentType(mimeType);
					httpResponse.setHeader("Content-Disposition", "inline; filename=\"" + docName + ".pdf" + "\"");
					FileCopyUtils.copy(inputStream, httpResponse.getOutputStream());
					// file.delete();
					inputStream.close();
					httpResponse.flushBuffer();
				} else {
					response.setStatus(1511);
				}
			} else {
				response.setStatus(1501);
			}
		} catch (AquariusException e) {
			response.setStatus(gutils.getErrorCode(e.getMessage()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			response.setStatus(1511);
		}
	}
}
