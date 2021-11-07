package org.jomaveger.bookexamples.chapter11.properties.imperative;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class PropertyReader {
	
	private final Logger log = Logger.getLogger(PropertyReader.class);

	private final Properties properties;
	
	public PropertyReader(String configFileName) throws Exception {
		this.properties = readProperties(configFileName);			 
	}

	private Properties readProperties(String configFileName) throws Exception {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName)) {
			Properties properties = new Properties();
			properties.load(inputStream);
			return properties;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}	
	}
	
	public String getProperty(String name) {
		return this.properties.getProperty(name);
	}
}
