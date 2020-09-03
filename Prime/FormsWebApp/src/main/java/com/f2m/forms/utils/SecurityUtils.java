package com.f2m.forms.utils;

import javax.servlet.http.HttpServletRequest;

import com.f2m.forms.beans.SystemInfo;


public class SecurityUtils {

	public SystemInfo getSystemInfo(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		SystemInfo response = new SystemInfo();
		response.setId(getClientIpAddr(request));
		if (userAgent == null || userAgent.length() == 0) {
			response.setBrowser("Desconocido");
			response.setVersion("N/A");
			response.setOs("Desconocido");
		} else {
			int indexOf = userAgent.indexOf("MSIE");
			if (indexOf > -1) {
				response.setBrowser("IE Explorer");
				response.setVersion("10");
			} else {
				indexOf = userAgent.indexOf("Trident");
				if (indexOf > -1) {
					response.setBrowser("IE Explorer");
					response.setVersion("11");
				} else {
					indexOf = userAgent.indexOf("Edge");
					if (indexOf > -1) {
						response.setBrowser("Edge");
						response.setVersion(getBrowserVersion(userAgent, indexOf + 5, " "));
					} else {
						indexOf = userAgent.indexOf("Opera");
						if (indexOf > -1) {
							response.setBrowser("Opera");
							response.setVersion(getBrowserVersion(userAgent, indexOf + 6, " "));
						} else {
							indexOf = userAgent.indexOf("OPR");
							if (indexOf > -1) {
								response.setBrowser("Opera");
								response.setVersion(getBrowserVersion(userAgent, indexOf + 4, " "));
							} else {
								indexOf = userAgent.indexOf("Firefox");
								if (indexOf > -1) {
									response.setBrowser("Firefox");
									response.setVersion(getBrowserVersion(userAgent, indexOf + 8, " "));
								} else {
									indexOf = userAgent.indexOf("Chrome");
									if (indexOf > -1) {
										response.setBrowser("Chrome");
										response.setVersion(getBrowserVersion(userAgent, indexOf + 7, " "));
									} else {
										indexOf = userAgent.indexOf("Safari");
										if (indexOf > -1) {
											response.setBrowser("Safari");
											response.setVersion(getBrowserVersion(userAgent, indexOf + 7, " "));
										} else {
											response.setBrowser("Desconocido");
											response.setVersion("N/A");
										}
									}
								}
							}
						}
					}
				}
			}
			// Buscamos Sistema Operativo:
			indexOf = userAgent.indexOf("Win");
			if (indexOf > -1) {
				response.setOs("Windows");
			} else {
				indexOf = userAgent.indexOf("Mac");
				if (indexOf > -1) {
					response.setOs("Mac");
				} else {
					indexOf = userAgent.indexOf("X11");
					if (indexOf > -1) {
						response.setOs("Unix");
					} else {
						indexOf = userAgent.indexOf("Android");
						if (indexOf > -1) {
							response.setOs("Android");
							response.setVersion(getBrowserVersion(userAgent, indexOf + 8, ";"));
						} else {
							indexOf = userAgent.indexOf("Linux");
							if (indexOf > -1) {
								response.setOs("Linux");
							} else {
								response.setOs("Desconocido");
							}
						}
					}
				}
			}
		}
		return response;
	}
	
	private String getBrowserVersion(String data, int ini, String separator) {
		String tmp = data.substring(ini);
		int indexOf = tmp.indexOf(separator);
		if (indexOf > -1) {
			return tmp.substring(0, indexOf);
		} else {
			return tmp;
		}
	}
	
	private String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
}
