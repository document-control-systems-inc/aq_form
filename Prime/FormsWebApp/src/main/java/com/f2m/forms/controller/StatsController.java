package com.f2m.forms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f2m.forms.beans.Form;
import com.f2m.forms.beans.FormData;
import com.f2m.forms.beans.Pages;
import com.f2m.forms.beans.StatsEmployees;
import com.f2m.forms.services.FormsService;
import com.f2m.forms.services.SecurityService;
import com.f2m.forms.utils.AquariusException;
import com.f2m.forms.utils.DateUtils;
import com.f2m.forms.utils.GeneralUtils;
import com.f2m.forms.utils.ModelUtils;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class StatsController {

	final static Logger logger = Logger.getLogger(StatsController.class);
	private ModelUtils modelUtils = new ModelUtils();
	private DateUtils dateUtils = new DateUtils();
	private GeneralUtils gutils = new GeneralUtils();
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	FormsService formsService;
	
	@RequestMapping("/dashboardForms")
	public String getDashboardForms(Model model, HttpSession session,
			HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Monitoreo Formas", "/dashboardForms", "dashboard");
			model = modelUtils.setModel(model, session, actualPage);
			String token = modelUtils.getToken(session);
			String userStats = "";
			JsonNode accountInfo = null;
			if (request.getParameter("idEmployee") != null) {
				userStats = request.getParameter("idEmployee");
				logger.debug("Estadísticas usuario: " + userStats);
				accountInfo = securityService.getUserProfile(userStats.toLowerCase(), token);
				model.addAttribute("backButton", true);
				model.addAttribute("idEmployee", userStats);
			} else {
				accountInfo = modelUtils.getAccountInfo(session);
				model.addAttribute("backButton", false);
			}
			
			boolean isManager = false;
			if (accountInfo.findValue("groups") != null) {				
				List<StatsEmployees> employees = new ArrayList<StatsEmployees>();
				for (JsonNode employee:accountInfo.findValue("groups")) {
					try {
						JsonNode employeeInfo = securityService.getUserProfile(employee.asText().toLowerCase(), token);
						if (employeeInfo != null) {
							StatsEmployees newEmployee = new StatsEmployees();
							newEmployee.setUsername(employee.asText());
							newEmployee.setName(gutils.validateJsonNullasString(employeeInfo.findValue("givenName")) + " " + gutils.validateJsonNullasString(employeeInfo.findValue("lastName")));
							employees.add(newEmployee);
						}
					} catch (Exception e) {
						logger.error(e);
					}
				}
				if (employees.size() > 0) {
					isManager = true;
				}
				model.addAttribute("employees", employees);
			}
			String fullName = gutils.validateJsonNullasString(accountInfo.findValue("givenName")) + " " + gutils.validateJsonNullasString(accountInfo.findValue("lastName"));
			List<Form> formas = formsService.getFormsCount(token, userStats);
			model.addAttribute("fullName", fullName);
			model.addAttribute("isManager", isManager);
			model.addAttribute("formas", formas);
			return "dashboardForms";
		} else {
			throw new AquariusException(4);
		}
	}
	
	@RequestMapping("/statsForms")
	public String getStatsForms(Model model, HttpSession session,
			HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Datos Formas", "/statsForms", "grid_on");
			model = modelUtils.setModel(model, session, actualPage);
			String token = modelUtils.getToken(session);
			List<Form> formas = formsService.getForms(token);
			List<String> myForms = new ArrayList<String>();
			JsonNode accountInfo1 = modelUtils.getAccountInfo(session);
			for (JsonNode formCreate:accountInfo1.findValue("formsView")) {
				myForms.add(formCreate.asText());
			}
			
			
			HashMap<String, String> formNames = new HashMap<String, String>();
			for (Form forma:formas) {
				formNames.put(forma.getName(), forma.getLabel());
			}
			String userStats = "";
			JsonNode accountInfo = null;
			if (request.getParameter("idEmployee") != null) {
				userStats = request.getParameter("idEmployee");
				accountInfo = securityService.getUserProfile(userStats.toLowerCase(), token);
				model.addAttribute("backButton", true);
				model.addAttribute("idEmployee", userStats);
			} else {
				accountInfo = modelUtils.getAccountInfo(session);
				model.addAttribute("backButton", false);
			}
			String fullName = gutils.validateJsonNullasString(accountInfo.findValue("givenName")) + " " + gutils.validateJsonNullasString(accountInfo.findValue("lastName"));
			model.addAttribute("fullName", fullName);
			
			JsonNode results = formsService.getFormData(token, userStats);
			List<FormData> formData = new ArrayList<FormData>();
			if (results != null) {
				for (JsonNode row:results) {
					FormData data = new FormData();
					if (row.get("id") != null) {
						data.setId(row.get("id").asText());
					} else {
						data.setId("");
					}
					if (row.get("formData").get("template") != null) {
						data.setTemplate(formNames.get(row.get("formData").get("template").asText()));
					} else {
						data.setTemplate("");
					}
					if (row.get("createdOn").get("time") != null) {
						data.setCreatedOn(dateUtils.formatDate(row.get("createdOn").get("time").asLong()));
					} else {
						data.setCreatedOn("");
					}
					if (row.get("createdBy") != null) {
						data.setCreatedBy(row.get("createdBy").asText());
					} else {
						data.setCreatedBy("");
					} 
					
					/*
					if (row.get("formData").get("values") != null) {
						for (JsonNode dataForm:row.get("formData").get("values")) {
							if (dataForm.findValue("param") != null) {
								switch (dataForm.findValue("param").asText()) {
									case "numPeticion":
										if (dataForm.findValue("value") != null) {
											data.setNumPeticion(dataForm.findValue("value").asText());
										}
										break;
									case "cuantiaSolicitada":
										if (dataForm.findValue("value") != null) {
											data.setCuantia(dataForm.findValue("value").asText());
										}
										break;
									case "peticionario":
										if (dataForm.findValue("value") != null) {
											data.setPeticionario(dataForm.findValue("value").asText());
										}
										break;
									case "account":
										if (dataForm.findValue("value") != null) {
											data.setAccount(dataForm.findValue("value").asText());
										}
										break;
									case "numContrato":
										if (dataForm.findValue("value") != null) {
											data.setContrato(dataForm.findValue("value").asText());
										}
										break;
								}
							}
						}
					}
					*/
					if (myForms.contains(row.get("formData").get("template").asText())) {
						formData.add(data);
					}
				}
				
			}
			model.addAttribute("datosForma", formData);
			return "statsForms";
		} else {
			throw new AquariusException(4);
		}
	}
}
