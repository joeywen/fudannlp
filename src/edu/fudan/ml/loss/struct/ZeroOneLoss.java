/*
 * 文件名：ZeroOneLoss.java
 * 版权：Copyright 2008-20012 复旦大学 All Rights Reserved.
 * 修改人：xpqiu
 * 修改时间：2009 Sep 7, 2009 6:36:21 PM
 * 修改内容：新增
 *
 * 修改人：〈修改人〉
 * 修改时间：YYYY-MM-DD
 * 修改内容：〈修改内容〉
 */
package edu.fudan.ml.loss.struct;

import java.util.List;

import edu.fudan.ml.loss.Loss;

/**
 * 0-1错误
 */
public class ZeroOneLoss implements Loss {

	private float calc(List l1, List l2) {
		boolean eq = true;
		for(int i=0; i<l1.size(); i++) {
			if (!l1.get(i).equals(l2.get(i))){
				eq = false;
				break;
			}
		}
		return eq?0:1;
	}
	
	private float calc(int[] l1, int[] l2) {
		boolean eq = true;
		for(int i=0; i<l1.length; i++) {
			if (l1[i] != l2[i]){
				eq = false;
				break;
			}
		}
		return eq?0:1;
	}

	public float calc(Object l1, Object l2) {
		if (!l1.getClass().equals(l2.getClass()))
			throw new IllegalArgumentException("Exception in HammingLoss: l1 and l2 have different types");
		
		float ret = 0;
		if (l1 instanceof int[])	{
			ret = calc((int[])l1, (int[])l2);
		}else if (l1 instanceof List)	{
			ret = calc((List)l1, (List)l2);
		}else	{
			throw new UnsupportedOperationException("");
		}
		
		return ret;
	}
}