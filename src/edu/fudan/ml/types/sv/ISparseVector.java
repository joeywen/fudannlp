package edu.fudan.ml.types.sv;

import java.io.Serializable;

public interface ISparseVector extends Serializable {

	/**
	 * 点积
	 * @param vector
	 * @return
	 */
	public float dotProduct(float[] vector);
	
	/**
	 * 
	 * @param sv
	 * @return
	 */
	public float dotProduct(HashSparseVector sv);
	
	/**
	 * 增加元素
	 * @param vector
	 * @return
	 */
	public void put(int i);
	/**
	 * 增加多个元素
	 * @param vector
	 * @return
	 */
	public void put(int[] idx);
	/**
	 * L2模
	 * @return
	 */
	public float l2Norm2();

}