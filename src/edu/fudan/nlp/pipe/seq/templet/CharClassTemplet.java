package edu.fudan.nlp.pipe.seq.templet;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.IFeatureAlphabet;
import edu.fudan.ontology.CharClassDictionary;

/**
 * 当前位置字符的语义类型
 *  
 * @author xpqiu
 * 
 */
public class CharClassTemplet implements Templet {

	private static final long serialVersionUID = 3572735523891704313L;
	private int id;
	private CharClassDictionary[] dicts;

	public CharClassTemplet(int id,CharClassDictionary[] dicts) {
		this.id = id;
		this.dicts = dicts;
	}

	/**
	 *  {@inheritDoc}
	 */
	@Override
	public int generateAt(Instance instance, IFeatureAlphabet features, int pos,
			int... numLabels) {
		String[][] data = ( String[][]) instance.getData();
//		int len = data[0].length;

		StringBuilder sb = new StringBuilder();

		sb.append(id);
		sb.append(':');
		char c = data[0][pos].charAt(0); //这里数据行列和模板中行列相反
		// 得到字符串类型
		for(int i=0;i<dicts.length;i++){
			if(dicts[i].contains(c)){
				sb.append("/");
				sb.append(dicts[i].name);
			}
		}
		if(sb.length()<3)
			return -1;
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
