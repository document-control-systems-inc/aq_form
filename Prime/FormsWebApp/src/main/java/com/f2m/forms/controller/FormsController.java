package com.f2m.forms.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f2m.forms.beans.Form;
import com.f2m.forms.beans.FormComponent;
import com.f2m.forms.beans.FormHtml;
import com.f2m.forms.beans.FormImage;
import com.f2m.forms.beans.FormWeb;
import com.f2m.forms.beans.Pages;
import com.f2m.forms.services.FormsService;
import com.f2m.forms.utils.AquariusException;
import com.f2m.forms.utils.GeneralUtils;
import com.f2m.forms.utils.ModelUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
public class FormsController {

	final static Logger logger = Logger.getLogger(FormsController.class);
	private ModelUtils modelUtils = new ModelUtils();
	private final String PDF_PATH = "/getFormPdfPrime";	
	private GeneralUtils gUtils = new GeneralUtils();
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	FormsService formsService;

	@RequestMapping("/createForm")
	public String getCreateForm(Model model, HttpSession session, HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Document Control Systems", "/createForm", "content_paste");
			model = modelUtils.setModel(model, session, actualPage);
			String token = modelUtils.getToken(session);
			List<Form> formas = formsService.getForms(token);
			List<Form> myForms = new ArrayList<Form>();
			JsonNode accountInfo = modelUtils.getAccountInfo(session);
			for (JsonNode formCreate:accountInfo.findValue("formsCreate")) {
				for (Form form:formas) {
					if (form.getName().equals(formCreate.asText())) {
						myForms.add(form);
						break;
					}
				}
			}
			model.addAttribute("formas", myForms);
			return "createForm";
		} else {
			throw new AquariusException(4);
		}
	}
	
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
						if (forma.get("isLandScape") != null) {
							myForm.setLandscape(1);
						}
						for (JsonNode page:forma.get("formHtml").get("html")) {
							FormHtml html = new FormHtml();
							FormImage image = new FormImage();
							image.setPath(page.get("image").get("path").asText());
							image.setWidth(page.get("image").get("width").asText());
							image.setHeight(page.get("image").get("height").asText());
							logger.debug("Imagen: " + image.getPath() + ". W: " + image.getWidth() + ", H: " + image.getHeight());
							html.setImage(image);
							for(JsonNode form:page.get("form")) {
								FormComponent newComponent = new FormComponent();
								newComponent.setName(gUtils.validateJsonNullasString(form.get("name")));
								newComponent.setOnChange(gUtils.validateJsonNullasString(form.get("onchange")));
								newComponent.setStyle(gUtils.validateJsonNullasString(form.get("style")));
								newComponent.setType(gUtils.validateJsonNullasString(form.get("type")));
								newComponent.setComponent(gUtils.validateJsonNullasString(form.get("component")));
								newComponent.setPlaceholder(gUtils.validateJsonNullasString(form.get("placeholder")));
								String isHidden = gUtils.validateJsonNullasString(form.get("hidden"));
								if (isHidden.length() > 0) {
									newComponent.setIsHidden(true);
								} else {
									newComponent.setIsHidden(false);
								}
								newComponent.setOnClick(gUtils.validateJsonNullasString(form.get("onclick")));
								newComponent.setValue(gUtils.validateJsonNullasString(form.get("value")));
								newComponent.setId(gUtils.validateJsonNullasString(form.get("id")));
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
	
	@RequestMapping("/sendEmail")
	public void sendEmail(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			String email = "";
			String idPdf = "";
			String token = modelUtils.getToken(session);
			for (Map.Entry<String,String[]> entry : request.getParameterMap().entrySet()) {
				String key = entry.getKey();
				String value = "";
				if (entry.getValue().length > 0) {
					value = entry.getValue()[0].replaceAll("(\r\n|\n)", "</w:t><w:br/><w:t>");;
				}
				if (key.equals("email")) {
					email = value;
				}
				if (key.equals("idPdf")) {
					idPdf = value;
				}
			}
			if (email != null && email.length() > 0
					&& idPdf != null && idPdf.length() > 0) {
				formsService.sendEmail(idPdf, email, token);
			}
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
		ObjectNode newData = mapper.createObjectNode();
		ArrayNode values = newData.putArray("values");
		
		
		if (session.getAttribute("userInfo") != null) {
			String token = modelUtils.getToken(session);
			parameters += "userToken=" + token + "&";
			for (Map.Entry<String,String[]> entry : request.getParameterMap().entrySet()) {
				String key = entry.getKey();
				String value = "";
				parameters += entry.getKey() + "=";
				if (entry.getValue().length > 0) {
					parameters += entry.getValue()[0];
					value = entry.getValue()[0].replaceAll("(\r\n|\n)", "</w:t><w:br/><w:t>");;
				}
				parameters += "&";
				if (key.equals("template")) {
					String template = request.getParameter(key).toString();
					newData.put("template", template);
					if (template.equals("servicio")) {
						value = formsService.getNumeroControl(token);
						System.out.println("Hoja de servicio, se crea numero_control: " + value);
						ObjectNode newObject = mapper.createObjectNode();
						newObject.put("param", "numero_control");
						newObject.put("value", value);
						values.add(newObject);
					}
				} else if (key.equals("landscape")) {
					newData.put("landscape", false);
				} else {
					ObjectNode newObject = mapper.createObjectNode();
					newObject.put("param", key);
					newObject.put("value", value);
					values.add(newObject);
				}
			}
			logger.debug("Parametros:" + parameters);
			logger.debug("Json:" + newData.toString());
			String idForm = formsService.setFormPDF(token, newData);
			if (idForm == null || idForm.length() == 0) {
				try {
					response.sendRedirect(request.getContextPath() + "/createForm");
				} catch (Exception e) {
					logger.error("Error al redireccionar el error: " + e.getMessage());
				}
				throw new AquariusException(4);
			} else {
				Pages actualPage = new Pages("Forma", "/pdf", "content_paste");
				model = modelUtils.setModel(model, session, actualPage);
				formsService.sendAutomaticEmail(idForm, newData.findValue("template").asText(), token);
				model.addAttribute("urlForms", request.getContextPath() + "/downloadpdf?pdf_id=" + idForm);
				return "pdf";
			}
		} else {
			throw new AquariusException(4);
		}
	}
	
	@RequestMapping(value = "/getImage")
	public void getImage(HttpSession session, 
			HttpServletRequest request, HttpServletResponse httpResponse) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			try {
				if (request.getParameter("img") != null) {
					String img = request.getParameter("img");
					String filePath = formsService.getImgPath() + "/" + img;
					File file = new File(filePath);
					if (file.exists()) {
						InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
						httpResponse.setContentLength((int) file.length());
						String mimeType = "image/jpeg";
						httpResponse.setContentType(mimeType);
						httpResponse.setHeader("Content-Disposition", "inline; filename=\"" + img + "\"");
						FileCopyUtils.copy(inputStream, httpResponse.getOutputStream());
						inputStream.close();
						httpResponse.flushBuffer();
					} else {
						logger.error("Error al obtener la imagen: " + img);
					}
				} else {
					logger.error("Error al obtener la imagen");
				}
			} catch (Exception e) {
				logger.error("Error al obtener la imagen: " + e.getMessage());
			}
		} else {
			logger.error("Error al obtener el imagen ");
		}
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
				String createdBy = "";
				if (request.getParameter("createdBy") != null) {
					createdBy = request.getParameter("createdBy");
				} else {
					createdBy = modelUtils.getUserId(session);
				}
				request.setCharacterEncoding("UTF-8");
				String idForm = "";
				if (request.getParameter("pdf_id") != null) {
					idForm = request.getParameter("pdf_id");
					JsonNode results = formsService.getFormDataById(idForm, createdBy, token);
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
							httpResponse.setHeader("Content-Disposition", "inline; filename=\"" + docName + "\"");
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
	
	public String escapeSpecialCharacters(String data) {
	    String escapedData = data.replaceAll("\\R", " ");
	    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
	        data = data.replace("\"", "\"\"");
	        escapedData = "\"" + data + "\"";
	    }
	    return escapedData;
	}
	
	public String convertToCSV(String[] data) {
	    return Stream.of(data)
	      .map(this::escapeSpecialCharacters)
	      .collect(Collectors.joining(","));
	}
	
	public boolean givenDataArray_whenConvertToCSV_thenOutputCreated(List<String[]> dataLines, String name) throws IOException {
	    File csvOutputFile = new File(name);
	    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
	        dataLines.stream()
	          .map(this::convertToCSV)
	          .forEach(pw::println);
	    }
	    return csvOutputFile.exists();
	}
	
	@RequestMapping(value = "/downloadReport")
	public void downloadReport(HttpSession session, 
			HttpServletRequest request, HttpServletResponse httpResponse) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			try {
				String token = modelUtils.getToken(session);
				request.setCharacterEncoding("UTF-8");
				String createdBy = "";
				if (request.getParameter("createdBy") != null) {
					createdBy = request.getParameter("createdBy");
				} else {
					createdBy = modelUtils.getUserId(session);
				}
				String idForm = "";
				if (request.getParameter("pdf_id") != null) {
					idForm = request.getParameter("pdf_id");
					JsonNode results = formsService.getFormDataById(idForm, createdBy, token);
					String docName = "";
					String pattern = "yyyyMMdd";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					List<String[]> dataLines = new ArrayList<>();
					dataLines.add(new String[] {"RECTYPE", "ORDUNIQ", "CUSTOMER", "PONUMBER", "TYPE", "ORDDATE", "HASJOB", "NONINVABLE"});
					dataLines.add(new String[] {"RECTYPE", "ORDUNIQ", "LINENUM", "ITEM", "LOCATION", "QTYORDERED", "JOBRELATED", "CONTRACT", "PROJECT", "CCATEGORY"});
					dataLines.add(new String[] {"RECTYPE", "ORDUNIQ", "LINENUM", "SERIALNUMF"});
					dataLines.add(new String[] {"RECTYPE", "ORDUNIQ", "LINENUM", "LOTNUMF"});
					dataLines.add(new String[] {"RECTYPE", "ORDUNIQ", "PAYMENT"});
					dataLines.add(new String[] {"RECTYPE", "ORDUNIQ", "UNIQUIFIER"});
					dataLines.add(new String[] {"RECTYPE", "ORDUNIQ", "OPTFIELD"});
					dataLines.add(new String[] {"RECTYPE", "ORDUNIQ", "LINENUM", "OPTFIELD"});
					
					if (results != null) {
						for (JsonNode row:results) {
							if (row.get("formData").get("template") != null) {
								docName += row.get("formData").get("template").asText();
							}
							int contador = 1;
							String fecha = simpleDateFormat.format(new Date());
							dataLines.add(new String[] 
									  { "1", "", "1520", "", "1", fecha, "-1","-1" });
							if (row.get("formData").get("values") != null) {
								for (JsonNode values:row.get("formData").get("values")) {
									if (values.findValue("param").asText().endsWith("_ea") ) {
										if (values.findValue("value").asText().length() > 0) {
											if (!values.findValue("param").asText().contains("total")) {
												String clave = values.findValue("param").asText();
												clave = clave.substring(0, clave.length() - 3);
												clave = clave.replace("_", "/");
												String cantidad = values.findValue("value").asText();
												int indexProd = clave.indexOf("/");
												String type = clave.substring(0, indexProd);
												String category = "";
												switch(type.toUpperCase()) {
													case "CHEMS":
														category = "CHEMICALS";
														break;
													case "PAPER":
														category = "PAPER";
														break;
													case "JANIS":
														category = "JANISUPP";
														break;
													default:
														category = type.toUpperCase();
												}
												dataLines.add(new String[] 
														  { "2", "", Integer.toString(contador*32), clave.toUpperCase(), "1", cantidad, "-1","120-1520-10", "1002", category });
												contador++;
											}
										}
										
									}
								}
							}
						}
						docName += "_" + idForm + ".csv";
						String pathFile = "/opt/ecm/tmp/";
						if (givenDataArray_whenConvertToCSV_thenOutputCreated(dataLines, pathFile + docName)) {
							File file = new File(pathFile + docName);
							if (file.exists()) {
								InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
								httpResponse.setContentLength((int) file.length());
								String mimeType = "text/csv";
								httpResponse.setContentType(mimeType);
								httpResponse.setHeader("Content-Disposition", "inline; filename=\"" + docName + "\"");
								FileCopyUtils.copy(inputStream, httpResponse.getOutputStream());
								inputStream.close();
								httpResponse.flushBuffer();
								file.delete();
							} else {
								logger.error("Error al obtener el documento: ");
							}
						}
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
	
	@RequestMapping(value = "/editForm")
	public String editForm(Model model, HttpSession session, 
			HttpServletRequest request, HttpServletResponse httpResponse) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			try {
				HashMap<String, String> valuesForm = new HashMap<String, String>();
				String token = modelUtils.getToken(session);
				
				request.setCharacterEncoding("UTF-8");
				String idFormData = "";
				
				String createdBy = "";
				if (request.getParameter("createdBy") != null) {
					createdBy = request.getParameter("createdBy");
				} else {
					createdBy = modelUtils.getUserId(session);
				}
				if (request.getParameter("pdf_id") != null) {
					idFormData = request.getParameter("pdf_id");
					JsonNode results = formsService.getFormDataById(idFormData, createdBy, token);
					String opcion = "";
					if (results != null) {
						if (results.size() > 0) {
							if (results.get(0).findValue("formData") != null) {
								if (results.get(0).findValue("formData").findValue("template") != null) {
									opcion = results.get(0).findValue("formData").findValue("template").asText();
								}
								if (results.get(0).findValue("formData").findValue("values") != null) {
									for (JsonNode value:results.get(0).findValue("formData").findValue("values")) {
										String param = gUtils.validateJsonNullasString(value.findValue("param"));
										if (param.length() > 0) {
											valuesForm.put(param, gUtils.validateJsonNullasString(value.findValue("value")));
										}
									}
								}
								
							}
						}
					}
					JsonNode forma = formsService.getFormById(opcion, token);
					if (forma != null) {
						logger.debug("Forma: " + forma.toString());
						String titulo = forma.get("label").asText();
						String idForm = forma.get("name").asText();
						FormWeb myForm = new FormWeb();
						myForm.setId(idForm);
						myForm.setScript(forma.get("formHtml").get("script").asText());
						if (forma.get("isLandScape") != null) {
							myForm.setLandscape(1);
						}
						for (JsonNode page:forma.get("formHtml").get("html")) {
							FormHtml html = new FormHtml();
							FormImage image = new FormImage();
							image.setPath(page.get("image").get("path").asText());
							image.setWidth(page.get("image").get("width").asText());
							image.setHeight(page.get("image").get("height").asText());
							logger.debug("Imagen: " + image.getPath() + ". W: " + image.getWidth() + ", H: " + image.getHeight());
							html.setImage(image);
							for(JsonNode form:page.get("form")) {
								FormComponent newComponent = new FormComponent();
								newComponent.setName(gUtils.validateJsonNullasString(form.get("name")));
								newComponent.setOnChange(gUtils.validateJsonNullasString(form.get("onchange")));
								newComponent.setStyle(gUtils.validateJsonNullasString(form.get("style")));
								newComponent.setType(gUtils.validateJsonNullasString(form.get("type")));
								newComponent.setComponent(gUtils.validateJsonNullasString(form.get("component")));
								newComponent.setPlaceholder(gUtils.validateJsonNullasString(form.get("placeholder")));
								String isHidden = gUtils.validateJsonNullasString(form.get("hidden"));
								if (isHidden.length() > 0) {
									newComponent.setIsHidden(true);
								} else {
									newComponent.setIsHidden(false);
								}
								newComponent.setOnClick(gUtils.validateJsonNullasString(form.get("onclick")));
								String myValue = gUtils.validateJsonNullasString(form.get("value"));
								if (myValue.length() == 0) {
									if (valuesForm.containsKey(gUtils.validateJsonNullasString(form.get("name")))) {
										myValue = valuesForm.get(gUtils.validateJsonNullasString(form.get("name")));
										if (newComponent.getType().equals("checkbox")) {
											myValue = "X";
										}
									}
								}
								newComponent.setValue(myValue);
								newComponent.setId(gUtils.validateJsonNullasString(form.get("id")));
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
				} else {
					logger.error("Error al obtener el documento: ");
				}
			} catch (Exception e) {
				logger.error("Error al obtener el documento: " + e.getMessage());
			}
		} else {
			logger.error("Error al obtener el documento: ");
		}
		try {
			httpResponse.sendRedirect(request.getContextPath() + "/createForm");
		} catch (IOException e) {
			logger.error("Error al redireccionar el error: " + e.getMessage());
		}
		return null;
	}
	
}
