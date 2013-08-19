package edu.fudan.nlp.pipe;

import java.io.Serializable;
import java.util.Iterator;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.InstanceSet;
import edu.fudan.ml.types.sv.HashSparseVector;
import gnu.trove.iterator.TIntFloatIterator;

/**
 * 由TF计算IDF
 * 
 * @author xpqiu
 * 
 */
public class TF2IDF extends Pipe implements Serializable {

	private static final long serialVersionUID = 5563900451276233502L;
	public int[] idf;

	public TF2IDF(InstanceSet train, InstanceSet test) {

		int numFeatures = 0;
		// 得到最大的特征维数
		for (int i = 0; i < train.size(); i++) {
			int len = ((HashSparseVector) train.getInstance(i).getData()).size();
			if (len > numFeatures)
				numFeatures = len;
		}
		for (int i = 0; i < test.size(); i++) {
			int len = ((HashSparseVector) test.getInstance(i).getData()).size();
			if (len > numFeatures)
				numFeatures = len;
		}
		idf = new int[numFeatures + 1];

	}

	@Override
	public void addThruPipe(Instance inst) {
		HashSparseVector data = (HashSparseVector) inst.getData();
		TIntFloatIterator it = data.data.iterator();
		while (it.hasNext()) {
			it.advance();
			idf[it.key()]++;
		}
	}

}
