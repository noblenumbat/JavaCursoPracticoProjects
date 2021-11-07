package org.jomaveger.bookexamples.chapter11.properties.imperative;

import org.apache.log4j.Logger;

public class PropertyReaderTest {
	
	private static final Logger log = Logger.getLogger(PropertyReaderTest.class);

	public static void main(String[] args) throws Exception {
		
		PropertyReader preader = new PropertyReader("config.properties");
		
		log.info(preader.getProperty("host"));
		log.info(preader.getProperty("port"));
		log.info(preader.getProperty("name"));
		log.info(preader.getProperty("temp"));
		log.info(preader.getProperty("price"));
		log.info(preader.getProperty("id"));
		log.info(preader.getProperty("firstName"));
		log.info(preader.getProperty("lastName"));
		log.info(preader.getProperty("type"));
		
	}
}
