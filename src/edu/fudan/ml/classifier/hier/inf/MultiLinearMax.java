/*
 * 文件名：LinearMax.java
 * 版权：Copyright 2008-20012 复旦大学 All Rights Reserved.
 * 修改人：xpqiu
 * 修改时间：2009 Sep 7, 2009 5:28:29 PM
 * 修改内容：新增
 *
 * 修改人：〈修改人〉
 * 修改时间：YYYY-MM-DD
 * 修改内容：〈修改内容〉
 */
package edu.fudan.ml.classifier.hier.inf;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.fudan.ml.classifier.hier.Predict;
import edu.fudan.ml.classifier.hier.Tree;
import edu.fudan.ml.classifier.linear.inf.Inferencer;
import edu.fudan.ml.feature.Generator;
import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.LabelAlphabet;
import edu.fudan.ml.types.sv.HashSparseVector;
import edu.fudan.ml.types.sv.ISparseVector;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.TIntSet;

/**
 * 树层次结构求最大
 * @author xpqiu
 * @version 1.0 LinearMax package edu.fudan.ml.solver
 */
public class MultiLinearMax extends Inferencer implements Serializable	{


	private static final long serialVersionUID = 460812009958228912L;
	private LabelAlphabet alphabet;
	private Tree tree;
	int numThread;
	private transient ExecutorService pool;

	private HashSparseVector[] weights;
	private Generator featureGen;
	private int numClass;
	/**
	 * 类结构树的叶子节点集合，没有树就对应到各个标签
	 * 因为leafs = alphabet.toTSet();是不可序列化，故需要每次加载是重新生成
	 */
	transient TIntSet leafs =null;
	private boolean isUseTarget = true;


	/**
	 * 
	 * @param featureGen
	 * @param alphabet
	 * @param tree
	 * @param threads
	 */
	public MultiLinearMax(Generator featureGen, LabelAlphabet alphabet, Tree tree,int threads) {
		this.featureGen = featureGen;
		this.alphabet = alphabet;
		numThread = threads;
		this.tree = tree;
		pool = Executors.newFixedThreadPool(numThread);

		numClass = alphabet.size();
		if(tree==null){
			leafs = alphabet.toTSet();
		}else
			leafs= tree.getLeafs();
	}
	/**
	 * 预测最佳标签
	 */
	public Predict getBest(Instance inst) {
		return getBest(inst, 1);
	}
	/**
	 * 预测前n个最佳标签
	 */
	public Predict getBest(Instance inst, int n) {
		Integer target =null;
		if(isUseTarget)
			target = (Integer) inst.getTarget();

		ISparseVector fv = featureGen.getVector(inst);

		//每个类对应的内积
		float[] sw = new float[alphabet.size()];
		Callable<Float>[] c= new Multiplesolve[numClass];
		Future<Float>[] f = new Future[numClass];

		for (int i = 0; i < numClass; i++) {
			c[i] = new Multiplesolve(fv,i);
			f[i] = pool.submit(c[i]);
		}

		//执行任务并获取Future对象
		for (int i = 0; i < numClass; i++){ 			
			try {
				sw[i] = (Float) f[i].get();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		Predict pred = new Predict(n);
		Predict oracle = null;
		if(target!=null){
			oracle = new Predict(n);
		}

		TIntIterator it = leafs.iterator();

		while(it.hasNext()){
			
			float score=0;
			int i = it.next();

			if(tree!=null){//计算含层次信息的内积
				int[] anc = tree.getPath(i);
				for(int j=0;j<anc.length;j++){
					score += sw[anc[j]];
				}
			}else{
				score = sw[i];
			}

			//给定目标范围是，只计算目标范围的值
			if(target!=null&&target.equals(i)){
				oracle.add(i,score);
			}else{
				pred.add(i,score);
			}

		}
		if(target!=null){
			inst.setTempData(oracle);
		}
		return pred;
	}

	class Multiplesolve implements Callable {
		ISparseVector fv;
		int idx;
		public  Multiplesolve(ISparseVector fv2,int i) {
			this.fv = fv2;
			idx = i;
		}

		public Float call() {

			// sum up xi*wi for each class
			float score = fv.dotProduct(weights[idx]);
			return score;

		}
	}
	public void setWeight(HashSparseVector[] weights) {
		this.weights = weights;

	}

	public void isUseTarget(boolean b) {
		isUseTarget = b;

	}

	private void writeObject(ObjectOutputStream oos) throws IOException{
		oos.defaultWriteObject();
	}

	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
//		System.out.println("s readObject");
		ois.defaultReadObject();
		if(tree==null){
			leafs = alphabet.toTSet();
		}else
			leafs= tree.getLeafs();
		pool = Executors.newFixedThreadPool(numThread);
	}
}
