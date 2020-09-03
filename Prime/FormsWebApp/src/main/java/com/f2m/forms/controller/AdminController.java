package com.f2m.forms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.f2m.forms.beans.Pages;
import com.f2m.forms.utils.AquariusException;
import com.f2m.forms.utils.ModelUtils;


@Controller
public class AdminController {

	final static Logger logger = Logger.getLogger(AdminController.class);
	private ModelUtils modelUtils = new ModelUtils();
	
	@RequestMapping("/home")
	public String getHome(Model model, HttpSession session,
			HttpServletRequest request) throws AquariusException {
		if (session.getAttribute("home") == null) {
			throw new AquariusException(4);
		}
		if (session.getAttribute("userInfo") != null) {
			Pages actualPage = new Pages("Home", "/home", "home");
			model = modelUtils.setModel(model, session, actualPage);
			return "home";
		} else {
			throw new AquariusException(4);
		}
	}
}
