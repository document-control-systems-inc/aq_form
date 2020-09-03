package com.f2m.forms.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f2m.forms.beans.ComboAccount;
import com.f2m.forms.beans.Contrato;
import com.f2m.forms.beans.Pages;
import com.f2m.forms.services.DPSService;
import com.f2m.forms.services.FormsService;
import com.f2m.forms.utils.AquariusException;
import com.f2m.forms.utils.ModelUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
public class DPSController {

	final static Logger logger = Logger.getLogger(DPSController.class);
	private ModelUtils modelUtils = new ModelUtils();
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	FormsService formsService;
	
	@Autowired
	DPSService dpsService;
	
	@RequestMapping("/nuevaEnmienda")
	public String nuevaEnmienda(Model model, HttpSession session, HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Contratos y Subvenciones", "/contratos", "description");
			model = modelUtils.setModel(model, session, actualPage);
			//String token = modelUtils.getToken(session);
			if (request.getParameter("idContrato") != null) {
				String id = request.getParameter("idContrato");
				//Contrato myContrato = dpsService.getContrato(id, token);
				model.addAttribute("idContrato", id);
			}
			//List<Form> formas = formsService.getForms(token);
			//model.addAttribute("formas", formas);
			return "nuevaEnmienda";
		} else {
			throw new AquariusException(4);
		}
	}
	
	@RequestMapping("/nuevaEnmiendaResponse")
	public String nuevaEnmiendaResponse(Model model, HttpSession session, HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Contratos y Subvenciones", "/contratos", "description");
			model = modelUtils.setModel(model, session, actualPage);
			String token = modelUtils.getToken(session);
			
			String idContrato = "";
			String status = "";
			String comentario = "";
			String monto = "";
			String vigencia = "";
			String fecha = "";
			String contratoPath = "";
			
			
			if (ServletFileUpload.isMultipartContent(request)) {
				try {
					List <FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
					if (multiparts != null) {
						for(FileItem item : multiparts){
							if(!item.isFormField()){
		                    	logger.debug("Archivo del campo: " + item.getFieldName());
		                    	if (item.getSize() > 0) {
		                    		String path = formsService.getFolderPath() + "/" + UUID.randomUUID().toString();
		                    		logger.debug("Guarando archivo en: " + path);
			                        item.write(new File(path));
			                        contratoPath = path;
		                    	}
		                    } else {
		                    	String value = IOUtils.toString(item.getInputStream(), StandardCharsets.UTF_8.name());
		                    	logger.debug("Parámetro: " + item.getFieldName() + "\tValor: " + value);
		                    	switch(item.getFieldName()) {
		                    		case "idContrato":
		                    			idContrato = value;
		                    			break;
		                    		case "status":
		                    			status = value;
		                    			break;
		                    		case "monto":
		                    			monto = value;
		                    			break;
		                    		case "vigencia":
		                    			vigencia = value;
		                    			break;
		                    		case "fecha":
		                    			fecha = value;
		                    			break;
		                    	}
		                    }
						}
					}
				} catch (Exception ex) {
					logger.error("Error al obtener los parámetros: " + ex.getMessage());
				}
			}
			if (contratoPath.length() > 0 && status.length() > 0) {
				ObjectNode data = mapper.createObjectNode();
				data.put("path", contratoPath);
				data.put("status", status);
				data.put("comentario", comentario);
				data.put("monto", monto);
				data.put("vigencia", vigencia);
				data.put("fecha", fecha);
				data.put("createdOn", System.currentTimeMillis());
				JsonNode contrato = dpsService.getContratoJson(idContrato, token);
				ArrayNode array = mapper.createArrayNode();
				ObjectNode contratoActual = contrato.deepCopy();
				if (contratoActual.findValue("enmiendas") == null) {
					array.add(data);
				} else {
					for (JsonNode element:contratoActual.findValue("enmiendas")) {
						array.add(element);
					}
					contratoActual.remove("enmiendas");
					array.add(data);
				}
				contratoActual.putPOJO("enmiendas", array);
				logger.debug("Se crea una enmienda con los siguientes datos: " + contratoActual.toString());
				dpsService.setContratos(contratoActual.toString(), idContrato, token);
				return "redirect:/detalleContrato";
			} else {
				//TODO: mensaje de error:
				if (contratoPath.length() > 0) {
					try {
						new File(contratoPath).delete();
					} catch (Exception e) {
						logger.error("No se pudo eliminar el archivo: " + contratoPath);
					}
				}
				return "redirect:/detalleContrato";
			}
		} else {
			throw new AquariusException(4);
		}
	}
	
	@RequestMapping("/contratos")
	public String contratos(Model model, HttpSession session, HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Contratos y Subvenciones", "/contratos", "description");
			model = modelUtils.setModel(model, session, actualPage);
			String token = modelUtils.getToken(session);
			List<Contrato> contratos = dpsService.getContratos(token);
			model.addAttribute("contratos", contratos);
			return "contratos";
		} else {
			throw new AquariusException(4);
		}
	}
	
	@RequestMapping("/detalleContrato")
	public String detalleContrato(Model model, HttpSession session, HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Contratos y Subvenciones", "/contratos", "description");
			model = modelUtils.setModel(model, session, actualPage);
			String token = modelUtils.getToken(session);
			model.addAttribute("infoAccounts", getAccount());
			
			if (request.getParameter("pdf_id") != null) {
				String id = request.getParameter("pdf_id");
				Contrato myContrato = dpsService.getContrato(id, token);
				model.addAttribute("contrato", myContrato);
			}
			return "detalleContrato";
		} else {
			throw new AquariusException(4);
		}
	}
	
	@RequestMapping("/edicionContrato")
	public String edicionContrato(Model model, HttpSession session, HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Contratos y Subvenciones", "/contratos", "description");
			model = modelUtils.setModel(model, session, actualPage);
			String token = modelUtils.getToken(session);
			model.addAttribute("infoAccounts", getAccount());
			
			if (request.getParameter("pdf_id") != null) {
				String id = request.getParameter("pdf_id");
				Contrato myContrato = dpsService.getContrato(id, token);
				model.addAttribute("contrato", myContrato);
			}
			
			
			//List<Form> formas = formsService.getForms(token);
			//model.addAttribute("formas", formas);
			return "detalleContrato";
		} else {
			throw new AquariusException(4);
		}
	}
	
	@RequestMapping("/nuevoContrato")
	public String nuevoContrato(Model model, HttpSession session, HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Contratos y Subvenciones", "/contratos", "description");
			model = modelUtils.setModel(model, session, actualPage);
			//String token = modelUtils.getToken(session);
			//List<Form> formas = formsService.getForms(token);
			//model.addAttribute("formas", formas);
			return "nuevoContrato";
		} else {
			throw new AquariusException(4);
		}
	}
	
	@RequestMapping("/nuevoContratoResponse")
	public String nuevoContratoResponse(Model model, HttpSession session, HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Contratos y Subvenciones", "/contratos", "description");
			model = modelUtils.setModel(model, session, actualPage);
			String token = modelUtils.getToken(session);
			
			String contratoPath = "";
			String peticionario = "";
			String subvencion = "";
			String monto = "";
			String vigencia = "";
			
			
			if (ServletFileUpload.isMultipartContent(request)) {
				try {
					List <FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
					if (multiparts != null) {
						for(FileItem item : multiparts){
							if(!item.isFormField()){
		                    	logger.debug("Archivo del campo: " + item.getFieldName());
		                    	if (item.getSize() > 0) {
		                    		String path = formsService.getFolderPath() + "/" + UUID.randomUUID().toString();
		                    		logger.debug("Guarando archivo en: " + path);
			                        item.write(new File(path));
			                        contratoPath = path;
		                    	}
		                    } else {
		                    	String value = IOUtils.toString(item.getInputStream(), StandardCharsets.UTF_8.name());
		                    	logger.debug("Parámetro: " + item.getFieldName() + "\tValor: " + value);
		                    	switch(item.getFieldName()) {
		                    		case "account":
		                    			peticionario = value;
		                    			break;
		                    		case "subvencion":
		                    			subvencion = value;
		                    			break;
		                    		case "monto":
		                    			monto = value;
		                    			break;
		                    		case "vigencia":
		                    			vigencia = value;
		                    			break;
		                    	}
		                    }
						}
					}
				} catch (Exception ex) {
					logger.error("Error al obtener los parámetros: " + ex.getMessage());
				}
			}
			if (contratoPath.length() > 0 && peticionario.length() > 0 && subvencion.length() > 0 && monto.length() > 0 && vigencia.length() > 0) {
				ObjectNode data = mapper.createObjectNode();
				data.put("path", contratoPath);
				data.put("peticionario", peticionario);
				data.put("subvencion", subvencion);
				data.put("monto", monto);
				data.put("vigencia", vigencia);
				logger.debug("Se crea contrato con los siguientes datos: " + data.toString());
				dpsService.setContratos(data.toString(), null, token);
				return "redirect:/contratos";
			} else {
				//TODO: mensaje de error:
				if (contratoPath.length() > 0) {
					try {
						new File(contratoPath).delete();
					} catch (Exception e) {
						logger.error("No se pudo eliminar el archivo: " + contratoPath);
					}
				}
				return "redirect:/nuevoContrato";
			}
		} else {
			throw new AquariusException(4);
		}
	}
	
	@RequestMapping("/updateContratoResponse")
	public String updateContratoResponse(Model model, HttpSession session, HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Contratos y Subvenciones", "/contratos", "description");
			model = modelUtils.setModel(model, session, actualPage);
			String token = modelUtils.getToken(session);
			
			String contratoPath = "";
			String peticionario = "";
			String subvencion = "";
			String monto = "";
			String vigencia = "";
			String idContrato = "";
			
			
			if (ServletFileUpload.isMultipartContent(request)) {
				try {
					List <FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
					if (multiparts != null) {
						for(FileItem item : multiparts){
							if(!item.isFormField()){
		                    	logger.debug("Archivo del campo: " + item.getFieldName());
		                    	if (item.getSize() > 0) {
		                    		String path = formsService.getFolderPath() + "/" + UUID.randomUUID().toString();
		                    		logger.debug("Guarando archivo en: " + path);
			                        item.write(new File(path));
			                        contratoPath = path;
		                    	}
		                    } else {
		                    	String value = IOUtils.toString(item.getInputStream(), StandardCharsets.UTF_8.name());
		                    	logger.debug("Parámetro: " + item.getFieldName() + "\tValor: " + value);
		                    	switch(item.getFieldName()) {
		                    		case "account":
		                    			peticionario = value;
		                    			break;
		                    		case "subvencion":
		                    			subvencion = value;
		                    			break;
		                    		case "monto":
		                    			monto = value;
		                    			break;
		                    		case "vigencia":
		                    			vigencia = value;
		                    			break;
		                    		case "idContrato":
		                    			idContrato = value;
		                    			break;
		                    	}
		                    }
						}
					}
				} catch (Exception ex) {
					logger.error("Error al obtener los parámetros: " + ex.getMessage());
				}
			}
			if (idContrato.length() > 0 && peticionario.length() > 0 && subvencion.length() > 0 && monto.length() > 0 && vigencia.length() > 0) {
				JsonNode actualContrato = dpsService.getContratoJson(idContrato, token);
				ObjectNode data = actualContrato.deepCopy();
				ObjectNode dataContrato = actualContrato.findValue("contrato").deepCopy();
				dataContrato.remove("peticionario");
				dataContrato.remove("subvencion");
				dataContrato.remove("monto");
				dataContrato.remove("vigencia");
				dataContrato.put("peticionario", peticionario);
				dataContrato.put("subvencion", subvencion);
				dataContrato.put("monto", monto);
				dataContrato.put("vigencia", vigencia);
				data.remove("contrato");
				data.putPOJO("contrato", dataContrato);
				logger.debug("Se crea contrato con los siguientes datos: " + data.toString());
				dpsService.setContratos(data.toString(), idContrato, token);
				return "redirect:/contratos";
			} else {
				//TODO: mensaje de error:
				if (contratoPath.length() > 0) {
					try {
						new File(contratoPath).delete();
					} catch (Exception e) {
						logger.error("No se pudo eliminar el archivo: " + contratoPath);
					}
				}
				return "redirect:/nuevoContrato";
			}
		} else {
			throw new AquariusException(4);
		}
	}
	
	@RequestMapping(value = "/downloadContrato")
	public void downloadContrato(HttpSession session, 
			HttpServletRequest request, HttpServletResponse httpResponse) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			try {
				//String token = modelUtils.getToken(session);
				request.setCharacterEncoding("UTF-8");
				String path = "";
				if (request.getParameter("pdf_id") != null) {
					path = request.getParameter("pdf_id");
					//Contrato contrato = dpsService.getContrato(idContrato, token);
					//if (contrato != null) {
					if (path != null) {
						File file = new File(path);
						if (file.exists()) {
							InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
							httpResponse.setContentLength((int) file.length());
							String mimeType = "application/pdf";
							httpResponse.setContentType(mimeType);
							httpResponse.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + ".pdf"  + "\"");
							FileCopyUtils.copy(inputStream, httpResponse.getOutputStream());
							inputStream.close();
							httpResponse.flushBuffer();
						} else {
							logger.error("Error al obtener el documento: ");
						}
					} else {
						logger.error("Error al obtener el documento: ");
					}
				} else {
					logger.error("Error al obtener el documento: ");
				}
			} catch (Exception e) {
				logger.error("Error al obtener el documento: " + e.getMessage());
			}
		} else {
			logger.error("Error al obtener el documento: ");
		}
	}
	
	private List<ComboAccount> getAccount() {
		List<ComboAccount> accounts = new ArrayList<ComboAccount>();
		ComboAccount myAccount = new ComboAccount();
		myAccount.setIsDisable(true);
		myAccount.setDescription("SHSP");
		accounts.add(myAccount);
		
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("1. San Juan - hssanjuan");
		myAccount.setDescription("1. San Juan - hssanjuan");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("2. Carolina - hscarolina");
		myAccount.setDescription("2. Carolina - hscarolina");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("3. Humacao - hshumacao");
		myAccount.setDescription("3. Humacao - hshumacao");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("4. Caguas - hscaguas");
		myAccount.setDescription("4. Caguas - hscaguas");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("5. Ponce - hsponce");
		myAccount.setDescription("5. Ponce - hsponce");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("6. Mayaguez - hsmayaguez");
		myAccount.setDescription("6. Mayaguez - hsmayaguez");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("7. Aguada - hsaguada");
		myAccount.setDescription("7. Aguada - hsaguada");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("8. Vega Baja - hsvegabaja");
		myAccount.setDescription("8. Vega Baja - hsvegabaja");
		accounts.add(myAccount);
		
		myAccount = new ComboAccount();
		myAccount.setIsDisable(true);
		myAccount.setDescription("OPSG");
		accounts.add(myAccount);
		
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("1. Aguada - opaguada");
		myAccount.setDescription("1. Aguada - opaguada");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("2. Aguadilla - opaguadilla");
		myAccount.setDescription("2. Aguadilla - opaguadilla");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("3. DRNA - opdrna");
		myAccount.setDescription("3. DRNA - opdrna");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("4. FURA - opfura");
		myAccount.setDescription("4. FURA - opfura");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("5. Barceloneta - opbarceloneta");
		myAccount.setDescription("5. Barceloneta - opbarceloneta");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("6. Cabo Rojo - opcaborojo");
		myAccount.setDescription("6. Cabo Rojo - opcaborojo");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("7. Hatillo - ophatillo");
		myAccount.setDescription("7. Hatillo - ophatillo");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("8. Carolina - opcarolin");
		myAccount.setDescription("8. Carolina - opcarolin");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("9. Isabela - opisabela");
		myAccount.setDescription("9. Isabela - opisabela");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("10. Manati - opmanati");
		myAccount.setDescription("10. Manati - opmanati");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("11. Ponce - opponce");
		myAccount.setDescription("11. Ponce - opponce");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("12. Patillas - oppatillas*");
		myAccount.setDescription("12. Patillas - oppatillas*");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("13. Vega Baja - opvegabaja*");
		myAccount.setDescription("13. Vega Baja - opvegabaja*");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("14. Guánica - opguanica*");
		myAccount.setDescription("14. Guánica - opguanica*");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("15. Salinas - opsalinas*");
		myAccount.setDescription("15. Salinas - opsalinas*");
		accounts.add(myAccount);
		
		myAccount = new ComboAccount();
		myAccount.setIsDisable(true);
		myAccount.setDescription("EMPG");
		accounts.add(myAccount);
		
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("1. NMEAD - empgnmead1");
		myAccount.setDescription("1. NMEAD - empgnmead1");
		accounts.add(myAccount);
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("2. NMEAD - empgnmead2");
		myAccount.setDescription("2. NMEAD - empgnmead2");
		accounts.add(myAccount);
		
		myAccount = new ComboAccount();
		myAccount.setIsDisable(true);
		myAccount.setDescription("BombSquad");
		accounts.add(myAccount);
		
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("1. policia - bqpolicia");
		myAccount.setDescription("1. policia - bqpolicia");
		accounts.add(myAccount);
		
		myAccount = new ComboAccount();
		myAccount.setIsDisable(true);
		myAccount.setDescription("Fusion Center Group");
		accounts.add(myAccount);
		
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("1. Fusion Center - fcdsp");
		myAccount.setDescription("1. Fusion Center - fcdsp");
		accounts.add(myAccount);
		
		myAccount = new ComboAccount();
		myAccount.setIsDisable(true);
		myAccount.setDescription("EDSA Group");
		accounts.add(myAccount);
		
		myAccount = new ComboAccount();
		myAccount.setIsDisable(false);
		myAccount.setValue("1. NMEAD - edsanmead");
		myAccount.setDescription("1. NMEAD - edsanmead");
		accounts.add(myAccount);
		
		return accounts;
	}
}
