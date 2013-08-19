package edu.fudan.nlp.pipe;

import java.io.Serializable;
import edu.fudan.ml.types.Instance;
import edu.fudan.util.MyArrays;

/**
 * 处理数值数据
 * 
 * @author xpqiu
 * 
 */
public class NumericPipe extends Pipe implements Serializable {

	boolean normalizelength = false;
	public NumericPipe() {

	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1342039394164863109L;

	public void addThruPipe(Instance instance) {
		String[][] data = (String[][]) instance.getData();
		if(normalizelength){
			for (int i = 0; i < data.length; i++) {
				String[] arr = data[i];
				int[] d = MyArrays.string2int(arr);

				int len = 10;

				int[] nd = normlise(d,len);

				data[i] = MyArrays.int2string(nd); 
			}
		}
		instance.setData(data);
	}


	private int[] normlise(int[] d, int len) {
		if(d.length<=len)
			return d;
		float r = d.length*1.0f/len;
		int[] nd = new int[len];
		for(int i=0;i<len;i++){
			int idx = (int) Math.round(i*r);
			nd[i]=d[idx];

		}
		return nd;
	}
}
