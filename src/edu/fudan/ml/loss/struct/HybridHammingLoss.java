package edu.fudan.ml.loss.struct;

import edu.fudan.ml.loss.struct.HammingLoss;

/**
 * 计算双链的Hamming距离
 * @author Feng Ji
 *
 */
public class HybridHammingLoss extends HammingLoss {

	/**
	 * 计算o1和o2之间的Hamming距离，o1和o2必须是同类型的对象
	 * @param o1 对象1（支持二维整型数组）
	 * @param o2 对象2（支持二维整型数组）
	 * @return Hamming距离
	 */
	@Override
	public float calc(Object o1, Object o2) {
		if (!o1.getClass().equals(o2.getClass()))
			throw new IllegalArgumentException("Exception in HybridHammingLoss: o1 and o2 have different types");
		
		int[][] l1 = (int[][]) o1;
		int[][] l2 = (int[][]) o2;
		int ne = 0;
		for (int i = 0; i < l1[0].length; i++) {
			for (int j = 0; j < l1.length; j++) {
				if (l1[j][i] != l2[j][i]) {
					ne++;
					break;
				}
			}
		}
		return ne;
	}
}
