package edu.fudan.ml.classifier;

/**
 * 结果接口
 * @author xpqiu
 * @version 2.0
 * @since 1.5
 */
public interface TPredict<T> {
	/**
	 * 获得预测结果
	 * @param i	  位置
	 * @return 第i个预测结果；如果不存在，为NULL
	 */
	public T getLabel(int i);
	/**
	 * 获得预测结果的得分
	 * @param i	     位置
	 * @return 第i个预测结果的得分；不存在为Double.NEGATIVE_INFINITY
	 */
	public float getScore(int i);
	/**
	 * 归一化得分
	 */
	public void normalize();
	/**
	 * 预测结果数量 
	 * @return 预测结果的数量
	 */
	public int size();
	/**
	 * 得到所有标签
	 * @return
	 */
	public T[] getLabels();
	/**
	 * 删除位置i的信息
	 * @param i
	 */
	public void remove(int i);
	
	

}
