package com.f2m.forms.beans;

import java.util.ArrayList;
import java.util.List;

public class FormHtml {
	
	private FormImage image;
	private List<FormComponent> form = new ArrayList<FormComponent>();
	
	public void addForm(FormComponent text) {
		form.add(text);
	}
	
	public FormImage getImage() {
		return image;
	}
	public void setImage(FormImage image) {
		this.image = image;
	}
	public List<FormComponent> getForm() {
		return form;
	}
	public void setForm(List<FormComponent> form) {
		this.form = form;
	}
}
