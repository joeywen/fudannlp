package edu.fudan.util;

import java.util.Map;
/**
 * Map按值比较
 * @author xpqiu
 * @version 1.0
 * @since FudanNLP 1.5
 */
public class ValueComparator implements java.util.Comparator {
	private Map m; // the original map

	public ValueComparator(Map m) {
		this.m = m;
	}

	public int compare(Object o1, Object o2) {
		// handle some exceptions here
		Object v1 = m.get(o1);
		Object v2 = m.get(o2);
		// make sure the values implement Comparable
		return -((Comparable) v1).compareTo(v2);
	}
}
