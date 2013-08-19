package edu.fudan.ml.classifier.linear.inf;

import edu.fudan.ml.classifier.Predict;
import edu.fudan.ml.feature.Generator;
import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.sv.ISparseVector;

/**
 * @author xpqiu
 * @version 1.0
 */
public class LinearMax extends Inferencer {

	private static final long serialVersionUID = -7602321210007971450L;
	
	private Generator generator;
	private int ysize;

	public LinearMax(Generator generator, int ysize) {
		this.generator = generator;
		this.ysize = ysize;
	}
	
	public Predict getBest(Instance inst)	{
		return getBest(inst, 1);
	}

	public Predict getBest(Instance inst, int n) {

		Integer target = null;
		if (isUseTarget && inst.getTarget() != null)
			target = (Integer) inst.getTarget();

		Predict<Integer> pred = new Predict<Integer>(n);
		Predict<Integer> oracle = null;
		if (target != null) {
			oracle = new Predict<Integer>(n);
		}

		for (int i = 0; i < ysize; i++) {
			ISparseVector fv = generator.getVector(inst, i);
			float score = fv.dotProduct(weights);
			if (target != null && target == i)
				oracle.add(i,score);
			else
				pred.add(i,score);
		}
		return pred;
	}
	
}
