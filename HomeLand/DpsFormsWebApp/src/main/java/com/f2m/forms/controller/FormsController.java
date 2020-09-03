package com.f2m.forms.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

import com.f2m.forms.beans.Pages;
import com.f2m.forms.services.FormsService;
import com.f2m.forms.utils.AquariusException;
import com.f2m.forms.utils.ModelUtils;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class FormsController {

	final static Logger logger = Logger.getLogger(FormsController.class);
	private ModelUtils modelUtils = new ModelUtils();
	private final String PDF_PATH = "/getFormDps";
	
	@Autowired
	FormsService formsService;

	@RequestMapping("/createForm")
	public String getCreateForm(Model model, HttpSession session, HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Gestión de Peticiones", "/createForm", "content_paste");
			model = modelUtils.setModel(model, session, actualPage);
			//String token = modelUtils.getToken(session);
			//List<Form> formas = formsService.getForms(token);
			//model.addAttribute("formas", formas);
			return "createForm";
		} else {
			throw new AquariusException(4);
		}
	}
	
	/*
	@RequestMapping("/form")
	public String getForm(Model model, HttpSession session, 
			HttpServletRequest req, HttpServletResponse resp) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			try {
				String token = modelUtils.getToken(session);
				req.setCharacterEncoding("UTF-8");
				
				Map<String, String[]> parameters = req.getParameterMap();
				for (String key : parameters.keySet()) {
					System.out.println("Key: " + key);
				}
				
				if (req.getParameter("rdoFormulario") != null) {
					String opcion = req.getParameter("rdoFormulario");
					JsonNode forma = formsService.getFormById(opcion, token);
					if (forma != null) {
						logger.debug("Forma: " + forma.toString());
						String titulo = forma.get("label").asText();
						String idForm = forma.get("name").asText();
						FormWeb myForm = new FormWeb();
						myForm.setId(idForm);
						myForm.setScript(forma.get("formHtml").get("script").asText());
						for (JsonNode page:forma.get("formHtml").get("html")) {
							FormHtml html = new FormHtml();
							FormImage image = new FormImage();
							image.setPath(page.get("image").get("path").asText());
							image.setWidth(page.get("image").get("width").asText());
							image.setHeight(page.get("image").get("height").asText());
							html.setImage(image);
							for(JsonNode form:page.get("form")) {
								FormComponent newComponent = new FormComponent();
								newComponent.setName(form.get("name").asText());
								newComponent.setOnChange(form.get("onChange").asText());
								newComponent.setStyle(form.get("style").asText());
								newComponent.setType(form.get("type").asText());
								newComponent.setComponent(form.get("component").asText());
								html.addForm(newComponent);
							}
							myForm.addFormHtml(html);
						}
						Pages actualPage = new Pages(titulo, "/form", "content_paste");
						model = modelUtils.setModel(model, session, actualPage);
						model.addAttribute("forma", myForm);
						model.addAttribute("totalForms", myForm.getHtml().size());
						return "form";
					}
				}
			} catch (UnsupportedEncodingException e) {
				logger.debug(e);
			}
			try {
				resp.sendRedirect(req.getContextPath() + "/createForm");
			} catch (IOException e) {
				logger.error("Error al redireccionar el error: " + e.getMessage());
			}
			throw new AquariusException(4);
		} else {
			throw new AquariusException(4);
		}
	}
	*/

	@RequestMapping("/uploadData")
	public String getUploadData(Model model, HttpSession session, HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Cargar Datos", "/uploadData", "cloud_upload");
			model = modelUtils.setModel(model, session, actualPage);
			return "uploadData";
		} else {
			throw new AquariusException(4);
		}
	}

	@RequestMapping("/view")
	public String getView(Model model, HttpSession session, HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Ver Archivos", "/view", "attach_file");
			model = modelUtils.setModel(model, session, actualPage);
			return "view";
		} else {
			throw new AquariusException(4);
		}
	}
	
	@RequestMapping("/pdf")
	public String getPdf(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		String parameters = "?";
		if (session.getAttribute("userInfo") != null) {
			if (ServletFileUpload.isMultipartContent(request)) {
				try {
					List <FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
					if (multiparts != null) {
						for(FileItem item : multiparts){
							if(!item.isFormField()){
		                    	logger.debug("Archivo del campo: " + item.getFieldName());
		                    	if (item.getSize() > 0) {
		                    		String name = new File(item.getName()).getName();
		                    		String path = formsService.getFolderPath() + "/" + UUID.randomUUID().toString();
		                    		if (name.length() > 0) {
			        					parameters += item.getFieldName() + "_file=";
			        					parameters += name;
			        					parameters += "&" + item.getFieldName() + "=";
			        					parameters += "Si";
			        					parameters += "&" + item.getFieldName() + "_path=";
			        					path += "_" + name;
			        					parameters += path;
			        				}
			        				parameters += "&";
			                        item.write(new File(path));
		                    	}
		                    } else {
		                    	String value = IOUtils.toString(item.getInputStream(), StandardCharsets.UTF_8.name());
		                    	parameters += item.getFieldName() + "=";
		                    	if (item.getFieldName().startsWith("chk")) {
		                    		value = "X";
		                    	}
		        				if (value.length() > 0) {
		        					parameters += value;
		        				}
		        				parameters += "&";
		                    	logger.debug("Parámetro: " + item.getFieldName() + "\tValor: " + value);
		                    }
						}
					}
				} catch (Exception ex) {
					logger.error("Error al obtener los parámetros: " + ex.getMessage());
				}
			}
		}
		/*
		
			for (Map.Entry<String,String[]> entry : request.getParameterMap().entrySet()) {
				parameters += entry.getKey() + "=";
				if (entry.getValue().length > 0) {
					parameters += entry.getValue()[0];
				}
				parameters += "&";
			}
			logger.debug("Parametros:" + parameters);
		
			
				
	                
	                
	                    
	                    
	                }
	               //File uploaded successfully
			}
			
		} else {
			logger.error("No se encontró la sesión del usuario");
		}
		*/
		Pages actualPage = new Pages("Forma", "/pdf", "content_paste");
		model = modelUtils.setModel(model, session, actualPage);
		model.addAttribute("urlForms", formsService.getUrl() + PDF_PATH + parameters);
		return "pdf";
	}
	
	@RequestMapping(value = "/downloadpdf")
	public void downloadPdf(HttpSession session, 
			HttpServletRequest request, HttpServletResponse httpResponse) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			try {
				String token = modelUtils.getToken(session);
				request.setCharacterEncoding("UTF-8");
				String idForm = "";
				if (request.getParameter("pdf_id") != null) {
					idForm = request.getParameter("pdf_id");
					JsonNode results = formsService.getFormDataById(idForm, token);
					String docName = "";
					String filePath = "";
					if (results != null) {
						for (JsonNode row:results) {
							if (row.get("formData").get("template") != null) {
								docName = row.get("formData").get("template").asText();
							}
							if (row.get("formData").get("file") != null) {
								filePath = row.get("formData").get("file").asText();
							}
						}
						docName += "_" + idForm + ".pdf";
						File file = new File(filePath);
						if (file.exists()) {
							InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
							httpResponse.setContentLength((int) file.length());
							String mimeType = "application/pdf";
							httpResponse.setContentType(mimeType);
							httpResponse.setHeader("Content-Disposition", "inline; filename=\"" + docName + ".pdf" + "\"");
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
	
}
