package com.f2m.aquarius.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class SpringRestController {

	@RequestMapping(value = "/echo", method = RequestMethod.GET,headers="Accept=application/json")
	public String echo() {
		String result="Aquarius Versión 2.00.18"; 
		result += "2020-09-01";
		return result;
	}
}
