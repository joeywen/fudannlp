package edu.fudan.nlp.pipe.seq;

import java.util.ArrayList;
import java.util.Arrays;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.IFeatureAlphabet;
import edu.fudan.ml.types.alphabet.LabelAlphabet;
import edu.fudan.nlp.pipe.Pipe;
import edu.fudan.nlp.pipe.seq.templet.TempletGroup;
import edu.fudan.util.exception.UnsupportedDataTypeException;

/**
 * 将字符序列转换成特征序列
 * 因为都是01特征，这里保存的是索引号
 * 
 * @author xpqiu
 * 
 */
public class Sequence2FeatureSequence extends Pipe{

	private static final long serialVersionUID = -6481304918657094682L;
	TempletGroup templets;
	public IFeatureAlphabet features;
	LabelAlphabet labels;

	public Sequence2FeatureSequence(TempletGroup templets,
			IFeatureAlphabet features, LabelAlphabet labels) {
		this.templets = templets;
		this.features = features;
		this.labels = labels;
	}

	public void addThruPipe(Instance instance) throws Exception {	
		Object sdata =  instance.getData();
		String[][] data;
		if(sdata instanceof String[]){
			data = new String[1][];
			data[0] = (String[]) sdata;
		}else if(sdata instanceof String[][]){
			data = (String[][]) sdata;
		}else if(sdata instanceof ArrayList){
			ArrayList ssdata = (ArrayList) sdata;
			data = new String[ssdata.size()][];
			for(int i=0;i<ssdata.size();i++){
				ArrayList<String> idata =  (ArrayList<String>) ssdata.get(i);
				data[i] = idata.toArray(new String[idata.size()]);
			}			
		}else{
			throw new UnsupportedDataTypeException(sdata.getClass().toString());
		}
		instance.setData(data);
		
		int len = data[0].length;
		int[][] newData = new int[len][templets.size()];
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < templets.size(); j++) {				
				newData[i][j] = templets.get(j).generateAt(instance,
						this.features, i, labels.size());
			}
		}
		instance.setData(newData);
		instance.setSource(data);
	}
}
