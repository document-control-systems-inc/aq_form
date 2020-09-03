package com.f2m.aquarius.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.f2m.aquarius.beans.TimePerDay;

public class StatsUtils {

	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss.SSS");
	
	private String getDateTimeString(long actualTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(actualTime);
		return dateFormat.format(calendar.getTime());
	}
	
	public String TimePerDayToString(TimePerDay time) {
		String result = "Ini: " + getDateTimeString(time.getIni()) + "\tFin: " + getDateTimeString(time.getFin());
		return result;
	}
	
	public TimePerDay getToday() {
		TimePerDay today = new TimePerDay();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		today.setIni(calendar.getTimeInMillis());
		calendar.add(Calendar.DATE, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		today.setFin(calendar.getTimeInMillis());
		return today;
	}
	
	public static void main(String[] args) {
		StatsUtils statsUtils = new StatsUtils();
		System.out.println(statsUtils.TimePerDayToString(statsUtils.getToday()));
	}
}
