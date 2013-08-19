package edu.fudan.ml.loss.struct;

import java.util.List;

import edu.fudan.ml.loss.Loss;

/**
 * 序列hamming distance
 * @author xpqiu
 *
 */
public class HammingLoss implements Loss {

	private float calc(List l1, List l2) {

		int ne = 0;
		for(int i=0; i<l1.size(); i++) {
			if (!l1.get(i).equals(l2.get(i)))
				ne++;
		}
		return ne;

	}
	
	private float calc(int[] l1,int[] l2) {
		int ne = 0;
		for(int i=0; i<l1.length; i++) {
			if (l1[i] != l2[i])
				ne++;
		}
		return ne;
	}
	
	private float calc(String[] l1,String[] l2) {
		int ne = 0;
		for(int i=0; i<l1.length; i++) {
			if (l2[i] == null || l1[i].compareTo(l2[i])!=0)
				ne++;
		}
		return ne;
	}
	
	/**
	 * 计算Hamming距离，l1和l2必须是同类型的对象
	 * @param l1 对象1（支持整型数组、字符串数组、链表）
	 * @param l2 对象2（支持整型数组、字符串数组、链表）
	 * @return Hamming距离
	 */
	@Override
	public float calc(Object l1, Object l2) {
		if (!l1.getClass().equals(l2.getClass()))
			throw new IllegalArgumentException("Exception in HammingLoss: l1 and l2 have different types");
		
		float ret = 0;
		if (l1 instanceof int[])	{
			ret = calc((int[])l1, (int[])l2);
		}else if (l1 instanceof String[])	{
			ret = calc((String[])l1, (String[])l2);
		}else if (l1 instanceof List)	{
			ret = calc((List)l1, (List)l2);
		}else	{
			throw new UnsupportedOperationException("");
		}
		
		return ret;
	}

}

