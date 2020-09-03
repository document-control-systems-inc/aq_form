package com.f2m.forms.services;

import java.util.List;

import com.f2m.forms.beans.Contrato;
import com.f2m.forms.utils.AquariusException;
import com.fasterxml.jackson.databind.JsonNode;

public interface DPSService {

	public List<Contrato> getContratos(String token) throws AquariusException;
	public Contrato getContrato(String id, String token) throws AquariusException;
	public JsonNode getContratoJson(String id, String token) throws AquariusException;
	public boolean setContratos(String data, String id, String token) throws AquariusException;
}
