package org.jomaveger.bookexamples.chapter11.properties.functional;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jomaveger.functional.control.Result;

public final class PropertyReader1 {
	
	private final Logger log = Logger.getLogger(PropertyReader1.class);

	private final Result<Properties> properties;

	public PropertyReader1(String configFileName) {
		this.properties = readProperties(configFileName);
	}

	private Result<Properties> readProperties(String configFileName) {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName)) {
			Properties properties = new Properties();
			properties.load(inputStream);
			return Result.of(properties);
		} catch (Exception e) {
			Result.failure(e).forEachOrFail(System.out::println).forEach(log::error);
			return Result.failure(e);
		}
	}
	
	public Result<String> getProperty(String name) {
		return properties.map(props -> props.getProperty(name));
	}
}
