package com.f2m.forms.utils;

import org.apache.log4j.Logger;

public class ExceptionUtils {

	final static Logger logger = Logger.getLogger(ExceptionUtils.class);
	
	public String parseException(Object exception) {
		if (exception != null) {
			int indexOf = exception.toString().indexOf("AquariusException:");
			if (indexOf > -1) {
				return exception.toString().substring(indexOf + 18);
			}
		}
		return null;
	}
}
