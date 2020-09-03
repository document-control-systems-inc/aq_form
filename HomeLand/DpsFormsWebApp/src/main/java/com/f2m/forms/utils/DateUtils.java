package com.f2m.forms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private String pattern = "yyyy/MM/dd HH:mm:ss";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	
	public String formatDate(long time) {
		return simpleDateFormat.format(new Date(time));
	}
}
