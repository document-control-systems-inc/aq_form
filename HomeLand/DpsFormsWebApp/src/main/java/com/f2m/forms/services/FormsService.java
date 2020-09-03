package com.f2m.forms.services;

import java.util.List;

import com.f2m.forms.beans.Form;
import com.f2m.forms.utils.AquariusException;
import com.fasterxml.jackson.databind.JsonNode;

public interface FormsService {

	public List<Form> getForms(String token) throws AquariusException;
	public JsonNode getFormById(String idForm, String token) throws AquariusException;
	public JsonNode getFormData(String token) throws AquariusException;
	public JsonNode getFormDataById(String idForm, String token) throws AquariusException;
	public String getUrl() throws AquariusException;
	public String getFolderPath() throws AquariusException;
}
