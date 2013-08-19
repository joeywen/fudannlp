package edu.fudan.ml.classifier.linear.inf;

import java.io.Serializable;

import edu.fudan.ml.classifier.TPredict;
import edu.fudan.ml.types.Instance;

/**
 * 推理类
 * @author xpqiu
 *
 */
public abstract class Inferencer implements Serializable	{

	private static final long serialVersionUID = -7254946709189008567L;
	
	protected float[] weights;
	
	protected boolean isUseTarget;
		
	/**
	 * 得到前n个最可能的预测值
	 * @param inst 
	 * @param n 返回个数
	 * @return
	 * Sep 9, 2009
	 */
	public abstract TPredict getBest(Instance inst);
	
	public abstract TPredict getBest(Instance inst, int n);
	
	public float[] getWeights()	{
		return weights;
	}
	
	public void setWeights(float[] weights)	{
		this.weights = weights;
	}

	public void isUseTarget(boolean b) {
		isUseTarget = b;
	}
}
