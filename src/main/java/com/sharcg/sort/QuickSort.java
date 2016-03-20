package com.sharcg.sort;

import java.util.HashMap;
import java.util.Hashtable;

public class QuickSort<T extends Number> {
	
	public void quickSort(T[] t, int beg, int end) {
		int first = beg;
		int last = end;
		T tmp = t[beg];
		while(beg < end) {
			while(beg < end && compare(tmp, t[end])<= 0) end --;
			t[beg] = t[end];
			while(beg < end && compare(tmp, t[beg])>= 0) beg ++;
			t[end] = t[beg];
			print(t);
		}
		t[beg] = tmp;
		if(first < beg -1) quickSort(t, first, beg-1);
		if(last > beg +1) quickSort(t, beg+1, last);
	}
	
	protected int compare(T t1, T t2) {
		return t1.intValue() - t2.intValue();
	}
	
	public static void main(String[] args) {
		System.out.println(Class.class.getClass().getResource("/"));
		System.out.println(QuickSort.class.getClass().getResource("/"));
		Integer[] a = new Integer[]{30, 3, 2,45, 36, 65, 21, 66, 182, 13, 20};
		print(a);
		new QuickSort<Integer>(){
			protected int compare(Integer t1, Integer t2) {
				return t2.intValue() - t1.intValue() ;
			}
		}.quickSort(a, 0, a.length-1);
		
		print(a);
	}
	
	public static<V> void print(V[] a) {
		for(V e: a)
			System.out.print(e + " ");
		System.out.println();
	}

}
