package com.f2m.forms.beans;

import java.util.ArrayList;
import java.util.List;

public class Contrato {

	private String id;
	private String peticionario;
	private String subvencion;
	private String monto;
	private String vigencia;
	private String path;
	private String fechaCreacion;
	private List<Enmienda> enmiendas;
	
	public Contrato () {
		enmiendas = new ArrayList<Enmienda>();
	}
	
	public String getPeticionario() {
		return peticionario;
	}
	public void setPeticionario(String peticionario) {
		this.peticionario = peticionario;
	}
	public String getSubvencion() {
		return subvencion;
	}
	public void setSubvencion(String subvencion) {
		this.subvencion = subvencion;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	public String getVigencia() {
		return vigencia;
	}
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Enmienda> getEnmiendas() {
		return enmiendas;
	}
	public void setEnmiendas(List<Enmienda> enmiendas) {
		this.enmiendas = enmiendas;
	}
	
}
