package edu.fudan.ml.classifier.linear;

import edu.fudan.ml.classifier.AbstractClassifier;
import edu.fudan.ml.types.InstanceSet;

/**
 * 抽象参数训练类
 * @author Feng Ji
 *
 */
public abstract class AbstractTrainer {

	/**
	 * 抽象参数训练方法
	 * @param trainset 训练数据集
	 * @param devset 评估性能的数据集，可以为NULL
	 * @return 分类器 
	 */
	public abstract AbstractClassifier train(InstanceSet trainset, InstanceSet devset);
	
	/**
	 * 参数训练方法
	 * @param trainset 训练数据集
	 * @return 分类器 
	 */
	public  AbstractClassifier train(InstanceSet trainset){
		return train(trainset,null);
	}
	
	/**
	 * 评估性能方法
	 * @param devset 评估性能的数据集
	 */
	protected abstract void evaluate(InstanceSet devset);
	
}
