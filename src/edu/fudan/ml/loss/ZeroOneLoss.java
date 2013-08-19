package edu.fudan.ml.loss;

public class ZeroOneLoss implements Loss {

	private float calc(Integer i1, Integer i2) {
		return i1==i2?0:1;
	}

	private float calc(String l1, String l2) {
		return l1.equals(l2)?0:1;
	}

	public float calc(Object l1, Object l2) {
		if (!l1.getClass().equals(l2.getClass()))	{
			throw new IllegalArgumentException("Exception in ZeroOneLoss: l1 and l2 have different types");
		}
		
		float ret = 0;
		if (l1 instanceof Integer)	{
			ret = calc((Integer)l1, (Integer)l2);
		}else if (l1 instanceof String)	{
			ret = calc((String)l1, (String)l2);
		}
		
		return ret;
	}

}
