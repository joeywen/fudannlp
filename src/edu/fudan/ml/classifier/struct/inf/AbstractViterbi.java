package edu.fudan.ml.classifier.struct.inf;

import edu.fudan.ml.classifier.Predict;
import edu.fudan.ml.classifier.linear.inf.Inferencer;
import edu.fudan.ml.types.Instance;
import edu.fudan.nlp.pipe.seq.templet.TempletGroup;

/**
 * 抽象最优序列解码器
 * @author Feng Ji
 *
 */

public abstract class AbstractViterbi extends Inferencer {

	private static final long serialVersionUID = 2627448350847639460L;
	
	
	protected int[] orders;
	
	/**
	 * 标记个数
	 */
	int ysize;

	/**
	 * 模板个数
	 */
	int numTemplets;
	
	/**
	 * 模板组
	 */
	protected TempletGroup templets;

	/**
	 * 状态组合个数
	 */
	protected int numStates;
	
	/**
	 * 抽象最优解码算法实现
	 * @param inst 样本实例
	 */
	public abstract Predict getBest(Instance inst);
	/**
	 * Viterbi解码算法不支持K-Best解码
	 */
	@Override
	public  Predict getBest(Instance inst, int nbest)	{
		return getBest(inst);
	}
	public TempletGroup getTemplets() {
		return templets;
	}
	public void setTemplets(TempletGroup templets) {
		this.templets = templets;
	}
	
}
