package edu.fudan.nlp.pipe;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.AlphabetFactory;
import edu.fudan.ml.types.alphabet.IFeatureAlphabet;
import edu.fudan.ml.types.alphabet.LabelAlphabet;
/**
 * 将字符数组类型的数据转换成特征索引
 * 数据类型：List\<String\> -\> int[]
 * @author xpqiu
 */
public class StringArray2IndexArray extends StringArray2SV{

	private static final long serialVersionUID = 358834035189351765L;


	public StringArray2IndexArray(AlphabetFactory af) {
		init(af);
	}
	public StringArray2IndexArray(AlphabetFactory af,boolean b){
		init(af);
		isSorted = b;
	}
	
	
	@Override
	public void addThruPipe(Instance inst) throws Exception {
		List<String> data = (List<String>) inst.getData();
		int size = data.size();
		int[] newdata = new int[data.size()+1];
		Iterator<String> it = data.iterator();
		
		for(int i=0;i<size;i++){
			String token = it.next();
			if(isSorted){
				token+="@"+i;
			}
			int id = features.lookupIndex(token,label.size());
			if(id==-1)
				continue;
			newdata[i] = id;
		}
		newdata[size]=constIndex;
		inst.setData(newdata);
	}

}
