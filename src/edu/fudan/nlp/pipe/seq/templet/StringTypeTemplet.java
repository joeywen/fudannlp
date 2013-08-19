package edu.fudan.nlp.pipe.seq.templet;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.IFeatureAlphabet;
import edu.fudan.nlp.cn.Chars;
import edu.fudan.nlp.cn.Chars.StringType;

/**
 * 字符串中字符组合模板
 * 
 * @author xpqiu
 * 
 */
public class StringTypeTemplet implements Templet {

	private static final long serialVersionUID = -4911289807273417691L;
	private int id;

	public StringTypeTemplet(int id) {
		this.id = id;
	}



	/**
	 *  {@inheritDoc}
	 */
	@Override
	public int generateAt(Instance instance, IFeatureAlphabet features, int pos,
			int... numLabels) {
		String[][] data = ( String[][]) instance.getData();
		
		int len = data[0][pos].length();

		StringBuilder sb = new StringBuilder();

		sb.append(id);
		sb.append(':');
		sb.append(pos);
		sb.append(':');
		
		String str = data[0][pos]; //这里数据行列和模板中行列相反
		StringType type = Chars.getStringType(str);
		String stype = type.name(); 
		
		if(type == StringType.M){
			
			if(str.length()>4 && str.startsWith("http:"))
				stype = "U$";
			else if(str.length()>4&&str.contains("@"))
				stype  = "E$";
			else if(str.contains(":"))
				stype = "T$";
		}
		
		sb.append(stype);	
		
		int index = features.lookupIndex(sb.toString(), numLabels[0]);
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
