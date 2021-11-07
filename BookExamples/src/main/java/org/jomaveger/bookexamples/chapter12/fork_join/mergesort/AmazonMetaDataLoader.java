package org.jomaveger.bookexamples.chapter12.fork_join.mergesort;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AmazonMetaDataLoader {

	public AmazonMetaData[] load(String configFileName) {
		List<AmazonMetaData> list = new ArrayList<AmazonMetaData>();

		try (InputStream in =
				getClass().getClassLoader().getResourceAsStream(configFileName);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
		{
			String line = null;
			while ((line = reader.readLine()) != null) {
				AmazonMetaData item = processItem(line);
				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		AmazonMetaData ret[] = new AmazonMetaData[list.size()];
		return list.toArray(ret);
	}

	private AmazonMetaData processItem(String line) {
		AmazonMetaData item = new AmazonMetaData();
		String tokens[] = line.split(";;");
		if (tokens.length != 8) {
			System.err.println("Error: " + line);
			System.err.println("Tokens: " + tokens.length);
			System.exit(-1);
		}

		item.setId(Integer.valueOf(tokens[0]));
		item.setASIN(tokens[1]);
		item.setTitle(tokens[2]);
		item.setGroup(tokens[3]);
		item.setSalesrank(Long.valueOf(tokens[4]));
		item.setReviews(Integer.valueOf(tokens[5]));
		item.setSimilar(Integer.valueOf(tokens[6]));
		item.setCategories(Integer.valueOf(tokens[7]));

		return item;
	}
}
