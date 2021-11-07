package org.jomaveger.bookexamples.chapter12.fork_join.mergesort;

import java.util.Arrays;
import java.util.Date;

public class ConcurrentMain {

	public static void main(String[] args) {

		AmazonMetaDataLoader loader = new AmazonMetaDataLoader();
		
		for (int j = 0; j < 10; j++) {
			AmazonMetaData[] data = loader.load("amazon-meta.csv");
			AmazonMetaData data2[] = Arrays.copyOf(data, data.length);

			Date start, end;

			start = new Date();
			Arrays.parallelSort(data);
			end = new Date();
			System.out.println("Execution Time Java Arrays.parallelSort(): "
			+ (end.getTime() - start.getTime()));

			System.out.println(data[0].getTitle());
			System.out.println(data2[0].getTitle());
			ConcurrentMergeSort mySorter = new ConcurrentMergeSort();
			start = new Date();
			mySorter.mergeSort(data2, 0, data2.length);
			end = new Date();

			System.out.println("Execution Time Java ConcurrentMergeSort: "
			+ (end.getTime() - start.getTime()));

			for (int i = 0; i < data.length; i++) {
				if (data[i].compareTo(data2[i]) != 0) {
					System.err.println("There's a difference is position " + i);
					System.exit(-1);
				}
			}

			System.out.println("Both arrays are equal");
		}
	}
}
