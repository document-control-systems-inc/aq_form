package com.f2m.forms.services;

import java.util.List;

import com.f2m.forms.beans.Form;
import com.f2m.forms.utils.AquariusException;
import com.fasterxml.jackson.databind.JsonNode;

public interface FormsService {

	public List<Form> getForms(String token) throws AquariusException;
	public List<Form> getFormsCount(String token, String user) throws AquariusException;
	public JsonNode getFormById(String idForm, String token) throws AquariusException;
	public JsonNode getFormData(String token, String user) throws AquariusException;
	public JsonNode getFormDataById(String idForm, String user, String token) throws AquariusException;
	
	public boolean sendEmail(String idPdf, String email, String token) throws AquariusException;
	
	public String getUrl() throws AquariusException;
	public String setFormPDF(String token, JsonNode data) throws AquariusException;
	public String getFolderPath() throws AquariusException;
	public String getImgPath() throws AquariusException;
	
	public boolean sendAutomaticEmail(String idForm, String template, String token) throws AquariusException;
	
	public String getNumeroControl(String token) throws AquariusException;
}
