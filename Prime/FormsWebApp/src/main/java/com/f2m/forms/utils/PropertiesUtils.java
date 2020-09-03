package com.f2m.forms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtils {

	final static Logger logger = Logger.getLogger(PropertiesUtils.class);
	private final String FOLDER_PATH = "/opt/ecm/conf";

	private Properties prop = null;
	private String name = new String();

	public PropertiesUtils() {
	}

	public PropertiesUtils(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue(String key) throws AquariusException {
		if (prop == null) {
			openProperty();
		}
		if (prop != null) {
			return prop.getProperty(key);
		}
		return null;
	}

	@SuppressWarnings("resource")
	private boolean openProperty() throws AquariusException {
		InputStream input = null;
		boolean response = false;

		String currentDir = System.getProperty("user.dir");
		logger.debug("Buscando propiedad " + name + " en: " + currentDir);

		try {
			// String currentDir = System.getProperty("user.dir");
			logger.debug("Buscando en: " + FOLDER_PATH);
			input = new FileInputStream(new File(FOLDER_PATH + "/" + name + ".properties"));
		} catch (Exception e) {
			logger.debug("No se encontró el archivo properties. Buscando en Resource...");
			input = getClass().getClassLoader().getResourceAsStream(name + ".properties");
		}
		if (input == null) {
			throw new AquariusException(1600);
		}
		try {
			prop = new Properties();
			prop.load(input);
			response = true;
		} catch (Exception e) {
			logger.error(e);
			prop = null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return response;
	}

}
