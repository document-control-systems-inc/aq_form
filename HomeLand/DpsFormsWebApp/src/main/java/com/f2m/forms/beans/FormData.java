package com.f2m.forms.beans;

public class FormData {

	private String id;
	private String template;
	private String createdOn;
	
	private String numPeticion;
	private String account;
	private String contrato;
	private String cuantia;
	private String peticionario;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getNumPeticion() {
		return numPeticion;
	}
	public void setNumPeticion(String numPeticion) {
		this.numPeticion = numPeticion;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getContrato() {
		return contrato;
	}
	public void setContrato(String contrato) {
		this.contrato = contrato;
	}
	public String getCuantia() {
		return cuantia;
	}
	public void setCuantia(String cuantia) {
		this.cuantia = cuantia;
	}
	public String getPeticionario() {
		return peticionario;
	}
	public void setPeticionario(String peticionario) {
		this.peticionario = peticionario;
	}
}
