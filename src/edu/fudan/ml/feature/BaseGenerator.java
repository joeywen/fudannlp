package edu.fudan.ml.feature;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.sv.HashSparseVector;
import edu.fudan.ml.types.sv.ISparseVector;

/**
 * 简单将data返回 特征不包含类别信息
 * 
 * @author xpqiu
 * 
 */
public class BaseGenerator extends Generator {

	private static final long serialVersionUID = 5209575930740335391L;
	

	public ISparseVector getVector(Instance inst) {

		return (ISparseVector) inst.getData();
	}

	public ISparseVector getVector(Instance inst, Object object) {
		return getVector(inst);
	}
}
