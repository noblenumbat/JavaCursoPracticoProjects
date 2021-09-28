package org.jomaveger.io.csv;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVWriter {

	private List<String[]> dataLines;

	public CSVWriter(String[] headers, List<String[]> data) {
		this.dataLines = new ArrayList<>();
		this.dataLines.add(headers);
		this.dataLines.addAll(data);
	}
	
	public boolean writeToCSV(String fileAbsolutePath) {
		File csvOutputFile = new File(fileAbsolutePath);
	    try (PrintWriter pw = new PrintWriter(csvOutputFile, StandardCharsets.UTF_8)) {
	        dataLines.stream()
	          .map(this::convertToCSV)
	          .forEach(pw::println);
	    } catch (IOException e) {
			return false;
		}
	    return true;
	}
	
	private String convertToCSV(String[] data) {
	    return Stream.of(data)
	      .map(this::escapeSpecialCharacters)
	      .collect(Collectors.joining(","));
	}
	
	private String escapeSpecialCharacters(String data) {
	    String escapedData = data.replaceAll("\\R", " ");
	    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
	        data = data.replace("\"", "\"\"");
	        escapedData = "\"" + data + "\"";
	    }
	    return escapedData;
	}
}
