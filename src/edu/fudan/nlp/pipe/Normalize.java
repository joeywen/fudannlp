package edu.fudan.nlp.pipe;

import java.io.Serializable;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.sv.SparseVector;

/**
 * 归一化，data类型须为SparseVector
 * @author xpqiu
 *
 */
public class Normalize extends Pipe  implements Serializable {

	private static final long serialVersionUID = -4740915822925015609L;

	@Override
	public void addThruPipe(Instance instance) {
		SparseVector data = (SparseVector) instance.getData();
		data.normalize();
	}

}
