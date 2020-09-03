package com.f2m.forms.beans;

import java.util.ArrayList;
import java.util.List;

public class FormWeb {

	String url;
	String id;
	List<FormHtml> html = new ArrayList<FormHtml>();
	String script;
	
	public void addFormHtml(FormHtml newForm) {
		html.add(newForm);
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<FormHtml> getHtml() {
		return html;
	}
	public void setHtml(List<FormHtml> html) {
		this.html = html;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
}
