package edu.fudan.ml.loss;

public interface Loss {

	/**
	 * 计算l1和l2之间的损失
	 * @param l1 对象1
	 * @param l2 对象2
	 * @return 损失
	 */
	public float calc(Object l1, Object l2);
}
