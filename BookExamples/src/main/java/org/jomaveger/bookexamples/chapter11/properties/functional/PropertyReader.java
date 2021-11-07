package org.jomaveger.bookexamples.chapter11.properties.functional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jomaveger.functional.control.Result;

public final class PropertyReader {
	
	private static final Logger log = Logger.getLogger(PropertyReader.class);

	private final Result<Properties> properties;
	
	private final String source;

	private PropertyReader(Result<Properties> properties, String source) {
		this.properties = properties;
		this.source = source;
	}
	
	public static PropertyReader filePropertyReader(String fileName) {
	    return new PropertyReader(readPropertiesFromFile(fileName), String.format("File: %s", fileName));
	  }

	private static Result<Properties> readPropertiesFromFile(String configFileName) {
		try (InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream(configFileName)) {
			Properties properties = new Properties();
			properties.load(inputStream);
			return Result.of(properties);
		} catch (NullPointerException e) {
			Result.failure(String.format("File %s not found in classpath", configFileName)).forEachOrFail(System.out::println).forEach(log::error);
			return Result.failure(String.format("File %s not found in classpath", configFileName));
	    } catch (IOException e) {
	    	Result.failure(String.format("IOException reading classpath resource %s", configFileName)).forEachOrFail(System.out::println).forEach(log::error);
	    	return Result.failure(String.format("IOException reading classpath resource %s", configFileName));
	    } catch (Exception e) {
	    	Result.failure(String.format("Exception reading classpath resource %s", configFileName), e).forEachOrFail(System.out::println).forEach(log::error);
	    	return Result.failure(String.format("Exception reading classpath resource %s", configFileName), e);
	    } 
	}
	
	public Result<String> getProperty(String name) {
	    return properties.flatMap(props -> getProperty(props, name));
	}

	private Result<String> getProperty(Properties props, String name) {
		return Result.of(props.getProperty(name)).mapFailure(String.format("Property \"%s\" not found in %s", name, this.source));
	}
}
