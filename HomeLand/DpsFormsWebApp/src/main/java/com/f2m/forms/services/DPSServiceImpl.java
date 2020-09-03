package com.f2m.forms.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.f2m.forms.beans.Contrato;
import com.f2m.forms.beans.Enmienda;
import com.f2m.forms.utils.AquariusException;
import com.f2m.forms.utils.DateUtils;
import com.f2m.forms.utils.GeneralUtils;
import com.fasterxml.jackson.databind.JsonNode;

@Service("dpsService")
public class DPSServiceImpl implements DPSService {

	final static Logger logger = Logger.getLogger(DPSServiceImpl.class);
	
	private GeneralUtils gutils = new GeneralUtils();
	private DateUtils dateUtils = new DateUtils();
	
	@Autowired
	WsCommService wsCommService;
	
	@Override
	public List<Contrato> getContratos(String token) throws AquariusException {
		if (token == null || token.length() == 0) {
			throw new AquariusException(1612);
		}
		List<Contrato> contratos = new ArrayList<Contrato>();
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
		JsonNode response = wsCommService.executeService("/dps/getContrato", "POST", args);
		if (response.findValue("status").asInt() == 0) {
			JsonNode results = response.findValue("exito");
			for (JsonNode contrato:results) {
				Contrato newContrato = new Contrato();
				newContrato.setFechaCreacion(dateUtils.formatDate(contrato.findValue("createdOn").findValue("time").asLong()));
				newContrato.setMonto(gutils.validateJsonNullasString(contrato.findValue("contrato").findValue("monto")));
				newContrato.setPath(gutils.validateJsonNullasString(contrato.findValue("contrato").findValue("path")));
				newContrato.setPeticionario(gutils.validateJsonNullasString(contrato.findValue("contrato").findValue("peticionario")));
				newContrato.setSubvencion(gutils.validateJsonNullasString(contrato.findValue("contrato").findValue("subvencion")));
				newContrato.setVigencia(gutils.validateJsonNullasString(contrato.findValue("contrato").findValue("vigencia")));
				newContrato.setId(gutils.validateJsonNullasString(contrato.findValue("id")));
				contratos.add(newContrato);
			}
		} else {
			logger.error("Error al recibir los contratos: [" + response.findValue("status").asInt() + "]" );
		}
		return contratos;
	}
	
	@Override
	public boolean setContratos(String data, String id, String token) throws AquariusException {
		if (data == null || data.length() == 0 ||
				token == null || token.length() == 0) {
			throw new AquariusException(1612);
		}
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("data", data));
		if (id != null && id.length() > 0) {
			args.add(wsCommService.createParam("id", id));
		}
		args.add(wsCommService.createParam("token", token));
		JsonNode response = wsCommService.executeService("/dps/setContrato", "POST", args);
		if (response.findValue("status").asInt() == 0) {
			return true;
		} else {
			logger.error("Error al recibir los contratos: [" + response.findValue("status").asInt() + "]" );
		}
		return false;
	}

	@Override
	public Contrato getContrato(String id, String token) throws AquariusException {
		if (token == null || token.length() == 0
				|| id == null || id.length() == 0) {
			throw new AquariusException(1612);
		}
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
		args.add(wsCommService.createParam("id", id));
		JsonNode response = wsCommService.executeService("/dps/getContrato", "POST", args);
		if (response.findValue("status").asInt() == 0) {
			JsonNode results = response.findValue("exito");
			for (JsonNode contrato:results) {
				Contrato newContrato = new Contrato();
				newContrato.setFechaCreacion(dateUtils.formatDate(contrato.findValue("createdOn").findValue("time").asLong()));
				newContrato.setMonto(gutils.validateJsonNullasString(contrato.findValue("contrato").findValue("monto")));
				newContrato.setPath(gutils.validateJsonNullasString(contrato.findValue("contrato").findValue("path")));
				newContrato.setPeticionario(gutils.validateJsonNullasString(contrato.findValue("contrato").findValue("peticionario")));
				newContrato.setSubvencion(gutils.validateJsonNullasString(contrato.findValue("contrato").findValue("subvencion")));
				newContrato.setVigencia(gutils.validateJsonNullasString(contrato.findValue("contrato").findValue("vigencia")));
				newContrato.setId(gutils.validateJsonNullasString(contrato.findValue("id")));
				if (contrato.findValue("enmiendas") != null) {
					for (JsonNode enmienda:contrato.findValue("enmiendas")) {
						Enmienda newEnmienda = new Enmienda();
						newEnmienda.setComentario(gutils.validateJsonNullasString(enmienda.findValue("comentario")));
						newEnmienda.setFecha(gutils.validateJsonNullasString(enmienda.findValue("fecha")));
						newEnmienda.setFechaCreacion(dateUtils.formatDate(enmienda.findValue("createdOn").asLong()));
						newEnmienda.setMonto(gutils.validateJsonNullasString(enmienda.findValue("monto")));
						newEnmienda.setPath(gutils.validateJsonNullasString(enmienda.findValue("path")));
						newEnmienda.setStatus(gutils.validateJsonNullasString(enmienda.findValue("status")));
						newEnmienda.setVigencia(gutils.validateJsonNullasString(enmienda.findValue("vigencia")));
						newContrato.getEnmiendas().add(newEnmienda);
					}
				}
				return newContrato;
			}
		} else {
			logger.error("Error al recibir los contratos: [" + response.findValue("status").asInt() + "]" );
		}
		return null;
	}

	@Override
	public JsonNode getContratoJson(String id, String token) throws AquariusException {
		if (token == null || token.length() == 0
				|| id == null || id.length() == 0) {
			throw new AquariusException(1612);
		}
		List<JsonNode> args = new ArrayList<JsonNode>();
		args.add(wsCommService.createParam("token", token));
		args.add(wsCommService.createParam("id", id));
		JsonNode response = wsCommService.executeService("/dps/getContrato", "POST", args);
		if (response.findValue("status").asInt() == 0) {
			JsonNode results = response.findValue("exito");
			return results.get(0);
		} else {
			logger.error("Error al recibir los contratos: [" + response.findValue("status").asInt() + "]" );
		}
		return null;
	}
}
