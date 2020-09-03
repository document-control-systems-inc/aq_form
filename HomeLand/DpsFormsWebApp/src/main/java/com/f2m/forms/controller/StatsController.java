package com.f2m.forms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f2m.forms.beans.FormData;
import com.f2m.forms.beans.Pages;
import com.f2m.forms.services.FormsService;
import com.f2m.forms.utils.AquariusException;
import com.f2m.forms.utils.DateUtils;
import com.f2m.forms.utils.ModelUtils;
import com.fasterxml.jackson.databind.JsonNode;

@Controller
public class StatsController {

	final static Logger logger = Logger.getLogger(StatsController.class);
	private ModelUtils modelUtils = new ModelUtils();
	private DateUtils dateUtils = new DateUtils();
	
	@Autowired
	FormsService formsService;
	
	@RequestMapping("/dashboardForms")
	public String getDashboardForms(Model model, HttpSession session,
			HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Monitoreo Estatus Peticiones", "/dashboardForms", "dashboard");
			model = modelUtils.setModel(model, session, actualPage);
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
			Pages actualPage = new Pages("Registro de Peticiones", "/statsForms", "grid_on");
			model = modelUtils.setModel(model, session, actualPage);
			String token = modelUtils.getToken(session);
			JsonNode results = formsService.getFormData(token);
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
						data.setTemplate(row.get("formData").get("template").asText());
					} else {
						data.setTemplate("");
					}
					if (row.get("createdOn").get("time") != null) {
						data.setCreatedOn(dateUtils.formatDate(row.get("createdOn").get("time").asLong()));
					} else {
						data.setCreatedOn("");
					}
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
					
					formData.add(data);
				}
				
			}
			model.addAttribute("datosForma", formData);
			return "statsForms";
		} else {
			throw new AquariusException(4);
		}
	}
}
