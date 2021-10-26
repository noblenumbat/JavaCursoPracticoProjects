package org.jomaveger.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public interface IResourcesLoader {
	
	static String getResourceFileAsString(String fileName) throws IOException {
	    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	    try (InputStream is = classLoader.getResourceAsStream(fileName)) {
	        
	    	if (is == null) 
	        	return null;
	    	
	        try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
	             BufferedReader reader = new BufferedReader(isr)) {
	            
	        	return reader.lines().collect(Collectors.joining(System.lineSeparator()));
	        }
	    }
	}

	static List<String> getResourceFileAsListOfString(String fileName) throws IOException {
	    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	    
	    URL url = classLoader.getResource(fileName);
	    
	    if (url == null)
	    	return null;
	    
	    File file = new File(url.getFile());
	    
	    return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);	
	}
	
	static String getResourceFileAbsolutePath(String fileName) throws IOException {
	    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	    
	    URL url = classLoader.getResource(fileName);
	    
	    if (url == null)
	    	return null;
	    
	    File file = new File(url.getFile());
	    
	    return file.getAbsolutePath();	
	}
	
	static URL getResourceFileURL(String fileName) throws IOException {
	    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	    
	    URL url = classLoader.getResource(fileName);
	    
	    if (url == null)
	    	return null;
	    
	    return url;	
	}
}
