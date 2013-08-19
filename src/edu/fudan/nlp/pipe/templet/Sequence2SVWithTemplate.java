package edu.fudan.nlp.pipe.templet;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.IFeatureAlphabet;
import edu.fudan.ml.types.alphabet.LabelAlphabet;
import edu.fudan.ml.types.sv.BinarySparseVector;
import edu.fudan.nlp.pipe.Pipe;

/**
 * 将字符序列转换成特征序列 因为都是01特征，这里保存的是索引号
 * 
 * @author xpqiu
 * 
 */
public class Sequence2SVWithTemplate extends Pipe{

	private static final long serialVersionUID = -4782249062779216625L;
	TempletGroup templets;
	public IFeatureAlphabet features;
	LabelAlphabet labels;

	public Sequence2SVWithTemplate(TempletGroup templets,
			IFeatureAlphabet features, LabelAlphabet labels) {
		this.templets = templets;
		this.features = features;
		this.labels = labels;
	}

	public void addThruPipe(Instance instance) throws Exception {	
		String[][] data = (String[][]) instance.getData();

		BinarySparseVector sv = new BinarySparseVector();
		for (int j = 0; j < templets.size(); j++) {
			int[] idx = templets.get(j).generateAt(instance,
					this.features, 1);
			sv.put(idx);
		}
		instance.setData(sv);
	}
}
