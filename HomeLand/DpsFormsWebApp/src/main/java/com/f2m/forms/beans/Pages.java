package com.f2m.forms.beans;

public class Pages {

	public Pages() {}
	
	/**
	 * Bean de tipo Página
	 * @param name Nombre de la página
	 * @param url URL de la página
	 * @param icon Ícono usado en la página
	 */
	public Pages(String name, String url, String icon) {
		this.name = name;
		this.url = url;
		this.icon = icon;
	}
	
	private String name;
	private String url;
	private String icon;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
