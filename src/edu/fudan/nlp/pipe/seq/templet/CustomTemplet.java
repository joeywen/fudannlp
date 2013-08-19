package edu.fudan.nlp.pipe.seq.templet;

import java.io.Serializable;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.IFeatureAlphabet;

/**
 * 当前位置固定窗口下，字符串的组合类型
 * 
 * @see edu.fudan.nlp.cn.Chars#getType(String)
 * @author xpqiu
 * 
 */
public class CustomTemplet implements Templet {

	private static final long serialVersionUID = -1632435387620824968L;
	private int id;

	public CustomTemplet(int id) {
		this.id = id;
	}



	/**
	 *  {@inheritDoc}
	 */
	@Override
	public int generateAt(Instance instance, IFeatureAlphabet features, int pos,
			int... numLabels) {
		String[][] data = ( String[][]) instance.getData();
		int len = data[0].length;
		if (pos + 1 >= len) {				
			return -1;
		} 
		StringBuilder sb = new StringBuilder();

		sb.append(id);
		sb.append(':');
		String str1 = data[0][pos]; //这里数据行列和模板中行列相反
		String str2 = data[0][pos+1];
		if(str1.length()==1&&str1.equals(str2)){
			sb.append("T");
		}else{
			sb.append("F");
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
