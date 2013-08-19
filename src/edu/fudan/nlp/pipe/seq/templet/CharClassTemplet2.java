package edu.fudan.nlp.pipe.seq.templet;

import java.util.HashSet;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.IFeatureAlphabet;
import edu.fudan.ontology.CharClassDictionary;
import gnu.trove.set.hash.TCharHashSet;

/**
 * 当前位置字符的语义类型
 *  
 * @author xpqiu
 * 
 */
public class CharClassTemplet2 implements Templet {

	private static final long serialVersionUID = 3572735523891704313L;
	private int id;
	private TCharHashSet set;
	private int idx;

	public CharClassTemplet2(int id,int idx, TCharHashSet set) {
		this.id = id;
		this.idx = idx;
		this.set = set;
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public int generateAt(Instance instance, IFeatureAlphabet features, int pos,
			int... numLabels) {
		String[][] data = ( String[][]) instance.getData();
		int len = data[0].length;

		StringBuilder sb = new StringBuilder();

		sb.append(id);
		sb.append(':');
		pos = pos+idx;
		if(pos<0||pos>=len)
			return -1;
		char c = data[0][pos].charAt(0); //这里数据行列和模板中行列相反
		// 得到字符串类型		
		if(!set.contains(c)){
			return -1;
		}
	int index = features.lookupIndex(sb.toString(),numLabels[0]);
	return index;
}

@Override
public int getOrder() {
	return 0;
}

public int[] getVars() {
	return new int[] { 0 };
}

public int offset(int... curs) {
	return 0;
}

}
