package edu.fudan.ml.feature;

import edu.fudan.ml.feature.Generator;
import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.sv.BinarySparseVector;
import edu.fudan.ml.types.sv.HashSparseVector;
import edu.fudan.ml.types.sv.ISparseVector;
import edu.fudan.ml.types.sv.SparseVector;

/**
 * 结构化特征生成类
 * 
 * @version Feb 16, 2009
 */
public class SFGenerator extends Generator	{

	private static final long serialVersionUID = 6404015214630864081L;

	/**
	 * 构造函数
	 */
	public SFGenerator() {
	}

	@Override
	public ISparseVector getVector(Instance inst, Object label) {
		int[] data = (int[]) inst.getData();
		ISparseVector fv = new BinarySparseVector(data.length);
		for(int i = 0; i < data.length; i++)	{
			int idx = data[i]+(Integer)label;
			fv.put(idx);
		}
		return fv;
	}
}
