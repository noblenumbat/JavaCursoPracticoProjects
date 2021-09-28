package org.jomaveger.io.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jomaveger.lang.exceptions.ExceptionUtils;

public class CSVReader<T> {
	
	private String separator;

	private String file;

	private Map<String, Field> privateFields = new LinkedHashMap<String, Field>();

	private Class<T> genericType;

	private List<T> data;

	private List<String> order;

	private List<String> headers;

	private boolean initCompleted;

	private boolean hasHeader;

	private void initialize() {
		if (!this.initCompleted) {
			Field[] allFields = genericType.getDeclaredFields();
			for (Field field : allFields) {
				if (Modifier.isPrivate(field.getModifiers())) {
					privateFields.put(field.getName(), field);
				}
			}

			try {
				readData();
			} catch (InstantiationException | IllegalAccessException 
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				this.initCompleted = false;
			}
			this.initCompleted = true;
		}
	}

	public CSVReader(final Class<T> type, String file, boolean hasHeader) {
		this.file = file;
		this.hasHeader = hasHeader;
		this.genericType = type;
		this.separator = ",";
	}

	public CSVReader(final Class<T> type, String file, boolean hasHeader, String separator) {
		this.file = file;
		this.hasHeader = hasHeader;
		this.genericType = type;
		this.separator = separator;
	}

	public List<T> getData() {
		if (null == data) {
			data = new ArrayList<T>();
		}
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<String> getOrder() {
		return order;
	}

	public CSVReader<T> setOrder(List<String> order) {
		this.order = order;
		return this;
	}

	public CSVReader<T> read(List<String> order) {
		this.setOrder(order);
		initialize();
		return this;
	}

	public CSVReader<T> read() {
		initialize();
		return this;
	}

	private void readData() throws InstantiationException, IllegalAccessException, 
									IllegalArgumentException, InvocationTargetException, 
									NoSuchMethodException, SecurityException {

		BufferedReader reader = null;
		String line = null;
		try {
			reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
			while ((line = reader.readLine()) != null) {

				List<String> row = Arrays.asList(line.split(separator));

				if (this.hasHeader) {
					setHeaders(row);
					this.hasHeader = false;
					continue;
				}

				T refObject = genericType.getConstructor().newInstance();
				int index = 0;

				List<String> listOfFieldNames = (null != getOrder()) ? getOrder()
						: new ArrayList<String>(privateFields.keySet());

				for (String fieldName : listOfFieldNames) {
					if (index >= row.size()) {
						break;
					}
					assign(refObject, privateFields.get(fieldName), row.get(index++));
				}
				getData().add(refObject);
			}

			reader.close();
		} catch (FileNotFoundException e) {
			ExceptionUtils.softenException(e);
			System.out.println(ExceptionUtils.getExpandedMessage(e));
		} catch (IOException e) {
			ExceptionUtils.softenException(e);
			System.out.println(ExceptionUtils.getExpandedMessage(e));
		} finally {

			try {
				reader.close();
			} catch (Exception e) {
				ExceptionUtils.softenException(e);
				System.out.println(ExceptionUtils.getExpandedMessage(e));
			}
		}
	}

	private Field assign(T refObject, Field field, String value)
			throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		field.set(refObject, value);
		field.setAccessible(false);
		return field;
	}

	public CSVReader<T> process(CSVProcessor<T> processsor) {
		if (!this.initCompleted) {
			initialize();
		}

		if (null != processsor) {
			List<T> list = getData();
			for (T obj : list) {
				obj = processsor.process(obj);
			}
		}
		return this;
	}
}
