package edu.fudan.ml.classifier.linear.update;

import edu.fudan.ml.loss.Loss;
import edu.fudan.ml.types.Instance;

/**
 * 线性分类的参数更新类，采用PA算法
 * @author Feng Ji
 *
 */
public class LinearMaxPAUpdate extends AbstractPAUpdate {

	public LinearMaxPAUpdate(Loss loss) {
		super(loss);
	}

	@Override
	protected int diff(Instance inst, float[] weights, Object target,
			Object predict) {

		int[] data = (int[]) inst.getData();
		int gold;
		if (target == null)
			gold = (Integer) inst.getTarget();
		else
			gold = (Integer) target;
		int pred = (Integer) predict;

		for (int i = 0; i < data.length; i++) {
			if (data[i] != -1) {
				int ts = data[i] + gold;
				int ps = data[i] + pred;
				diffv.put(ts, 1.0f);
				diffv.put(ps, -1.0f);
				diffw += weights[ts]-weights[ps];  // w^T(f(x,y)-f(x,ybar))
			}
		}

		return 1;
	}


}
