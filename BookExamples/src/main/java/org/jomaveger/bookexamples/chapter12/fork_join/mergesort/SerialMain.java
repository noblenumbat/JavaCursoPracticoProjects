package org.jomaveger.bookexamples.chapter12.fork_join.mergesort;

import java.util.Arrays;
import java.util.Date;

public class SerialMain {

	public static void main(String[] args) {
		
		AmazonMetaDataLoader loader = new AmazonMetaDataLoader();

		for (int j = 0; j < 10; j++) {
			AmazonMetaData[] data = loader.load("amazon-meta.csv");
			AmazonMetaData[] data2 = data.clone();

			Date start, end;

			start = new Date();
			Arrays.sort(data);
			end = new Date();
			System.out.println("Execution Time Java Arrays.sort(): "
					+ (end.getTime() - start.getTime()));

			System.out.println(data[0].getTitle());
			System.out.println(data2[0].getTitle());
			SerialMergeSort mySorter = new SerialMergeSort();
			start = new Date();
			mySorter.mergeSort(data2, 0, data2.length);
			end = new Date();

			System.out.println("Execution Time Java SerialMergeSort: " 
					+ (end.getTime() - start.getTime()));

			for (int i = 0; i < data.length; i++) {
				if (data[i].compareTo(data2[i]) != 0) {
					System.err.println("There's a difference is position " + i);
					System.exit(-1);
				}
			}
			System.out.println("Both arrays are equal");
			System.out.println(data2[0].getTitle() + ": " + data2[0].getSalesrank());
		}
	}
}
