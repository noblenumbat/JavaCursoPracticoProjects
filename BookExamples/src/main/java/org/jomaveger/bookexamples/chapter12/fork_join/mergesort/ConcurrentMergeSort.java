package org.jomaveger.bookexamples.chapter12.fork_join.mergesort;

import java.util.concurrent.ForkJoinPool;

public class ConcurrentMergeSort {

	public void mergeSort(Comparable data[], int start, int end) {

		MergeSortTask task = new MergeSortTask(data, start, end, null);
		ForkJoinPool.commonPool().invoke(task);
	}
}
