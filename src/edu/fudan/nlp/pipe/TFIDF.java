package edu.fudan.nlp.pipe;

import java.io.Serializable;
import java.util.Iterator;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.sv.HashSparseVector;
import edu.fudan.ml.types.sv.SparseVector;
import gnu.trove.iterator.TIntFloatIterator;

/**
 * 计算IFIDF
 * 
 * @author xpqiu
 * 
 */
public class TFIDF extends Pipe implements Serializable {

	private static final long serialVersionUID = 2937341538282834618L;
	int[] idf;
	private int docNum;

	public TFIDF(int[] idf, int docNum) {
		this.idf = idf;
		this.docNum = docNum;
	}

	@Override
	public void addThruPipe(Instance inst) {
		HashSparseVector data = (HashSparseVector) inst.getData();
		TIntFloatIterator it = data.data.iterator();
		while (it.hasNext()) {
			it.advance();
			int id = it.key();
			if (idf[id] > 0) {
				float value = (float) (it.value()*Math.log(docNum / idf[id]));
				data.put(id, value);
			}
		}

	}

}
