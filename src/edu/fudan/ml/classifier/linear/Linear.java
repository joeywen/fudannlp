package edu.fudan.ml.classifier.linear;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import edu.fudan.ml.classifier.AbstractClassifier;
import edu.fudan.ml.classifier.LabelParser;
import edu.fudan.ml.classifier.LabelParser.Type;
import edu.fudan.ml.classifier.Predict;
import edu.fudan.ml.classifier.linear.inf.Inferencer;
import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.AlphabetFactory;
import edu.fudan.nlp.pipe.Pipe;
import edu.fudan.util.exception.LoadModelException;

/**
 * 线性分类器
 * 
 * @author xpqiu
 * 
 */
public class Linear extends AbstractClassifier implements Serializable	{

	private static final long serialVersionUID = -2626247109469506636L;

	protected Inferencer inferencer;
	protected AlphabetFactory factory;
	protected Pipe pipe;

	public Linear(Inferencer inferencer, AlphabetFactory factory) {
		this.inferencer = inferencer;
		this.factory = factory;
	}

	public Linear() {		
	}

	public Predict classify(Instance instance, int n) {
		return (Predict) inferencer.getBest(instance, n);
	}
	
	@Override
	public Predict classify(Instance instance, Type t, int n) {
		Predict res = (Predict) inferencer.getBest(instance, n);
		return LabelParser.parse(res,factory.DefaultLabelAlphabet(),t);
	}
	
	/**
	 * 得到类标签
	 * @param idx 类标签对应的索引
	 * @return
	 */
	public String getLabel(int idx) {
		return factory.DefaultLabelAlphabet().lookupString(idx);
	}

	/**
	 * 将分类器保存到文件
	 * @param file
	 * @throws IOException
	 */
	public void saveTo(String file) throws IOException {
		File f = new File(file);
		File path = f.getParentFile();
		if(!path.exists()){
			path.mkdirs();
		}
		
		ObjectOutputStream out = new ObjectOutputStream(new GZIPOutputStream(
				new BufferedOutputStream(new FileOutputStream(file))));
		out.writeObject(this);
		out.close();
	}
	/**
	 *  从文件读入分类器
	 * @param file
	 * @return
	 * @throws LoadModelException
	 */
	public static Linear loadFrom(String file) throws LoadModelException{
		Linear cl = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new GZIPInputStream(
					new BufferedInputStream(new FileInputStream(file))));
			cl = (Linear) in.readObject();
			in.close();
		} catch (Exception e) {
			throw new LoadModelException(e,file);
		}
		return cl;
	}

	public Inferencer getInferencer() {
		return inferencer;
	}
	
	public void setInferencer(Inferencer inferencer)	{
		this.inferencer = inferencer;
	}

	public AlphabetFactory getAlphabetFactory() {
		return factory;
	}

	public void setWeights(float[] weights) {
		inferencer.setWeights(weights);
	}

	public float[] getWeights() {
		return inferencer.getWeights();
	}

	public void setPipe(Pipe pipe) {
		this.pipe = pipe;		
	}
	public Pipe getPipe() {
		return pipe;		
	}

}
