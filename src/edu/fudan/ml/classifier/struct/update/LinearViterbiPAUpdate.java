package edu.fudan.ml.classifier.struct.update;

import edu.fudan.ml.classifier.linear.update.AbstractPAUpdate;
import edu.fudan.ml.classifier.struct.inf.LinearViterbi;
import edu.fudan.ml.loss.struct.HammingLoss;
import edu.fudan.ml.types.Instance;
import edu.fudan.nlp.pipe.seq.templet.TempletGroup;

/**
 * 一阶线性序列的参数更新类，采用PA算法
 * @author Feng Ji
 *
 */
public class LinearViterbiPAUpdate extends AbstractPAUpdate {

	private int ysize;
	private int[] golds;
	private int[] preds;
	private int[][] data;
	private int[] orders;

	public LinearViterbiPAUpdate(LinearViterbi inf, HammingLoss loss) {
		super(loss);
		this.ysize = inf.ysize();
		this.orders = inf.orders();
	}
	
	public LinearViterbiPAUpdate(LinearViterbi inf, HammingLoss loss, TempletGroup dynamic) {
		super(loss);
		this.ysize = inf.ysize();
		this.orders = concat(inf.orders(), dynamic.getOrders());
	}
	
	private int[] concat(int[] A, int[] B) {
		int[] C= new int[A.length+B.length];
		System.arraycopy(A, 0, C, 0, A.length);
		System.arraycopy(B, 0, C, A.length, B.length);
		return C;
	}

	/**
	 * @return 预测序列和对照序列之间不同的Clique数量
	 */
	@Override
	protected int diff(Instance inst, float[] weights, Object targets,
			Object predicts) {

		data = (int[][]) inst.getData();

		if (targets == null)
			golds = (int[]) inst.getTarget();
		else
			golds = (int[]) targets;
		preds = (int[]) predicts;

		int diff = 0;

		if (golds[0] != preds[0]) {
			diff++;
			diffClique(weights, 0);
		}
		for (int p = 1; p < data.length; p++) {
			if (golds[p - 1] != preds[p - 1] || golds[p] != preds[p]) {
				diff++;
				diffClique(weights, p);
			}
		}

		return diff;
	}

	/**
	 * 调整权重
	 * @param weights 权重 
	 * @param p 位置
	 */
	private void diffClique(float[] weights, int p) {
		for (int t = 0; t < orders.length; t++) {
			if (data[p][t] == -1)
				continue;

			if (orders[t] == 0) {
				if (golds[p] != preds[p]) {
					int ts = data[p][t] + golds[p];
					int ps = data[p][t] + preds[p];
					adjust(weights, ts, ps);
				}
			}
			if (p > 0 && orders[t] == 1) {
				int ts = data[p][t] + (golds[p - 1] * ysize + golds[p]);
				int ps = data[p][t] + (preds[p - 1] * ysize + preds[p]);
				adjust(weights, ts, ps);
			}
		}
	}

}
