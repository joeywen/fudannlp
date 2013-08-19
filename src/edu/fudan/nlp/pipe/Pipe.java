package edu.fudan.nlp.pipe;

import java.io.Serializable;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.InstanceSet;

/**
 * 数据类型转换管道，通过一系列的组合将数据从原始方式转为需要的数据类型
 * Pipe只能每次连续流水处理一个样本，不能按阶段多遍执行
 * 要分阶段多遍执行参见 {@link edu.fudan.ml.types.InstanceSet#loadThruStagePipes(edu.fudan.data.reader.Reader)}
 * @author xpqiu
 *
 */
public abstract class Pipe implements Serializable{
	
	/**
	 * 用来判断是否使用类别，以便在无类别使用时删掉
	 */
	boolean useTarget = false;
	
	/**
	 * 基本的数据类型转换处理操作，继承类需重新定义实现
	 * @param inst 样本
	 * @throws Exception
	 */
	public abstract void addThruPipe(Instance inst) throws Exception;
	
	/**
	 * 通过pipe直接处理实例
	 * @param instSet
	 * @throws Exception
	 */
	public void process(InstanceSet instSet) throws Exception {
		for(int i=0; i < instSet.size(); i++){
			Instance inst = instSet.getInstance(i);
			addThruPipe(inst);
		}
	}
}

