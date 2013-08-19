/*
 * 文件名：Classifier.java
 * 版权：Copyright 2008-20012 复旦大学 All Rights Reserved.
 * 修改人：xpqiu
 * 修改时间：2009 Sep 6, 2009 11:09:40 AM
 * 修改内容：新增
 *
 * 修改人：〈修改人〉
 * 修改时间：YYYY-MM-DD
 * 修改内容：〈修改内容〉
 */
package edu.fudan.ml.classifier;

import java.io.Serializable;

import edu.fudan.ml.classifier.LabelParser.Type;
import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.InstanceSet;

/**
 * 分类器抽象类
 * @author xpqiu
 * @version 1.0
 * Classifier
 * package edu.fudan.ml.classifier
 */
public abstract class AbstractClassifier implements Serializable{

	private static final long serialVersionUID = -175929257288466023L;


	/**
	 * 返回分类内部结果，标签为内部表示索引，需要还原处理
	 * @param instance
	 * @return
	 */
	public TPredict classify(Instance instance){
		return classify(instance,1);
	}
	/**
	 * 返回前n个分类内部结果，标签为内部表示索引，需要还原处理
	 * @param instance
	 * @param n 返回结果个数
	 * @return
	 */
	public abstract TPredict classify(Instance instance,int n);

	/**
	 * 对多个样本进行分类，返回前n个分类内部结果
	 * 标签为内部表示索引，需要还原处理
	 * @param instances
	 * @param n 返回结果个数
	 * @return
	 */
	public TPredict[] classify(InstanceSet instances, int n) {
		TPredict[] ress = new TPredict[instances.size()];
		for(int i=0;i<instances.size();i++){
			ress[i] = classify(instances.get(i), n);
		}
		return ress;
	}
	
	
	/**
	 * 对单个样本进行分类，返回得分最高的标签
	 * @param instance
	 * @param type 返回标签类型
	 * @return
	 */
	public TPredict classify(Instance instance,LabelParser.Type t){
		return classify(instance, t,1);
	}
	/**
	 * 对单个样本进行分类，返回得分最高前n的标签
	 * @param instance
	 * @param type 返回标签类型
	 * @param n 返回结果个数
	 * @return
	 */
	public abstract TPredict classify(Instance instance,LabelParser.Type type, int n);
	
	/**
	 * 对多个样本进行分类，返回得分最高前n的标签
	 * @param instances 样本集合
	 * @param type 返回标签类型
	 * @param n 返回结果个数
	 * @return
	 */
	public TPredict[] classify(InstanceSet instances,LabelParser.Type type, int n) {
		TPredict[] res= new Predict[instances.size()];
		for(int i=0;i<instances.size();i++){
			res[i]=  classify(instances.getInstance(i),type,n);			
		}
		return res;
	}

	/**
	 * 对单个样本进行分类，返回得分最高的标签
	 * 原始标签类型必须为字符串标签
	 * @param instance 待分类样本
	 * @return
	 */
	public String getStringLabel(Instance instance) {
		TPredict pred = classify(instance,Type.STRING);
		return (String) pred.getLabel(0);		
	}
	
	/**
	 * 对单个样本进行分类，返回得分最高的前n个标签，
	 * 原始标签类型必须为字符串标签
	 * @param instance 待分类样本
	 * @param n 返回结果个数
	 * @return
	 */
	public String[] getStringLabel(Instance instance,int n) {
		TPredict pred = classify(instance,Type.STRING);
		return (String[]) pred.getLabels();		
	}
	
	
	/**
	 * 对多个样本进行分类，返回得分最高的标签，缺省为字符串标签
	 * @param set
	 * @return
	 */
	public String[] getLabel(InstanceSet set) {
		String[] pred= new String[set.size()];
		for(int i=0;i<set.size();i++){
			pred[i]=  getStringLabel(set.getInstance(i));			
		}
		return pred;
	}
	

}
