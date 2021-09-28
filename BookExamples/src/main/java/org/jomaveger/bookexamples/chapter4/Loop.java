package org.jomaveger.bookexamples.chapter4;

import org.apache.commons.lang3.ArrayUtils;
import org.jomaveger.lang.dbc.Contract;

public class Loop {

	public static boolean isSorted(int[] v) {
		Contract.require(v != null);
		
		int i = 0;
		boolean isSorted = true;
		while (i < v.length - 1 && isSorted) {
			
			Contract.check(v.length - 1 - i >= 0); 
			
			isSorted = v[i] <= v[i+1];
			i++;
			
			Contract.check(i >= 0 && (i < v.length) && (isSorted || (v[i-1] > v[i])));
		}
		
		
		Contract.ensure(isSorted == ArrayUtils.isSorted(v));
		return isSorted;
	}
	
	public static int binarySearch(int[] v, int e) {
		Contract.require(v != null && Loop.isSorted(v));
		
		int k;
		int i = -1;
		int d = v.length;
		
		while (i + 1 != d) {
			
			Contract.check(d - i >= 0);
			
			int m = (i + d) / 2;
			
			if (v[m] <= e) i = m;
			else d = m;
			
			Contract.check((i >= -1) && (i <= v.length -1) && (d >= 0) && (d <= v.length)
					&& (i + 1 <= d) && checkOrderingInv(v, e, i, d));
		}
		
		k = i;
		Contract.ensure((k >= -1) && (k <= v.length -1) && checkOrderingPost(v, e, k));
		return k;
	}

	private static boolean checkOrderingInv(int[] v, int e, int i, int d) {
		boolean ordering = true;
		int j = 0;
		while (j <= i && ordering) {
			ordering = v[j] <= e;
			j++;
		}
		j = d;
		ordering = true;
		while (j < v.length && ordering) {
			ordering = e < v[j];
			j++;
		}
		return ordering;
	}

	private static boolean checkOrderingPost(int[] v, int e, int k) {
		boolean ordering = true;
		int j = 0;
		while (j <= k && ordering) {
			ordering = v[j] <= e;
			j++;
		}
		j = k + 1;
		ordering = true;
		while (j < v.length && ordering) {
			ordering = e < v[j];
			j++;
		}
		return ordering;
	}
}
