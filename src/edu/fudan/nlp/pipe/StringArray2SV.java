package edu.fudan.nlp.pipe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.AlphabetFactory;
import edu.fudan.ml.types.alphabet.IFeatureAlphabet;
import edu.fudan.ml.types.alphabet.LabelAlphabet;
import edu.fudan.ml.types.sv.HashSparseVector;
/**
 * 将字符数组类型的数据转换成稀疏向量
 * 数据类型：List\<String\> -\> SparseVector
 * @author xpqiu
 */
public class StringArray2SV extends Pipe implements Serializable {

	private static final long serialVersionUID = 358834035189351765L;
	protected IFeatureAlphabet features;
	protected LabelAlphabet label;
	protected static final String constant = "!#@$";
	/**
	 * 常数项。为防止特征字典优化时改变，设为不可序列化
	 */
	protected transient int constIndex;
	
	
	/**
	 * 特征是否为有序特征
	 */
	protected boolean isSorted = false;
	
	public StringArray2SV() {
	}

	public StringArray2SV(AlphabetFactory af) {
		init(af);
	}
	public StringArray2SV(AlphabetFactory af,boolean b){
		init(af);
		isSorted = b;
	}
	
	
	protected void init(AlphabetFactory af) {
		this.features = af.DefaultFeatureAlphabet();
		this.label = af.DefaultLabelAlphabet();
		// 增加常数项
		constIndex = features.lookupIndex(constant);
	}
	
	
	
	@Override
	public void addThruPipe(Instance inst) throws Exception {
		List<String> data = (List<String>) inst.getData();
		int size = data.size();
		HashSparseVector sv = new HashSparseVector();
		
		Iterator<String> it = data.iterator();
		
		for(int i=0;i<size;i++){
			String token = it.next();
			if(isSorted){
				token+="@"+i;
			}
			int id = features.lookupIndex(token);
			if(id==-1)
				continue;
			sv.put(id, 1.0f);
		}
		sv.put(constIndex, 1.0f);
		inst.setData(sv);
	}
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
		ois.defaultReadObject();
		constIndex = features.lookupIndex(constant);
	}

}
