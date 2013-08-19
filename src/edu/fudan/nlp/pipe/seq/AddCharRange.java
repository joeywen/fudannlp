package edu.fudan.nlp.pipe.seq;

import java.util.ArrayList;

import edu.fudan.ml.types.Instance;
import edu.fudan.nlp.cn.Chars;
import edu.fudan.nlp.pipe.Pipe;

/**
 * 在原有序列基础上，增加一列字符类型信息 
 * @see edu.fudan.nlp.cn.Chars#getType(String)
 * @author xpqiu
 * 
 */
public class AddCharRange extends Pipe {

	private static final long serialVersionUID = 3572735523891704313L;

	@Override
	public void addThruPipe(Instance inst) throws Exception {
		Object sdata = inst.getData();
		String[][] data;
		int colum;
		if(sdata instanceof ArrayList){
			ArrayList ssdata = (ArrayList) sdata;
			colum = ssdata.size();
			data = new String[colum+1][];
			for(int i=0;i<colum;i++){
				ArrayList<String> idata =  (ArrayList<String>) ssdata.get(i);
				data[i] = idata.toArray(new String[idata.size()]);
			}			
		}else{
			return;
		}
		
		int len = data[0].length;
		data[colum] = new String[len];
		for(int i=0;i<len;i++){
			data[colum][i] = Chars.getStringType(data[0][i]).toString();
		}		

		inst.setData(data);
	}

}
