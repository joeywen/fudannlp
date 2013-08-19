package edu.fudan.nlp.pipe.seq;

import java.util.TreeMap;
import java.util.TreeSet;

import edu.fudan.ml.types.Dictionary;
import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.LabelAlphabet;
import edu.fudan.nlp.pipe.Pipe;
import edu.fudan.util.MultiValueMap;
import edu.fudan.util.exception.LoadModelException;

/**
 * 词性字典预处理
 * 
 * @author xpqiu
 * 
 */
public class DictPOSLabel  extends Pipe{	
	private static final long serialVersionUID = 5457382370544508743L;

	protected Dictionary dict;
	protected LabelAlphabet labels;
	
	public DictPOSLabel(Dictionary dict, LabelAlphabet labels)  {
		this.dict = dict;
		this.labels = labels;
		checkLabels();
	}

	private void checkLabels(){
		MultiValueMap<String, String> pos = dict.getPOSDict();
		for(TreeSet<String> pp: pos.valueSets()){
			if(pp==null)
				continue;
			for(String p : pp){
				if(labels.lookupIndex(p)==-1){
					System.err.println("Warning: 自定义词性: " +p+ 
							"\n标签最好在下面列表中：\n" +labels.toString());
					labels.setStopIncrement(false);
					labels.lookupIndex(p);
					labels.setStopIncrement(true);					
				}
			}
		}
	}

	public void addThruPipe(Instance instance) throws Exception {
		String[] data = (String[]) instance.getData();

		int length = data.length;
		int[][] dicData = new int[length][labels.size()];				

		for(int i = 0; i < data.length; i++) {
			//			System.out.println(data[i]);
			TreeSet<String> posset = dict.getPOS(data[i]);
			if(posset != null &&posset.size()>0){
				for(String pos:posset)
					dicData[i][labels.lookupIndex(pos)] = -1;
			}
		}

		for (int i = 0; i < length; i++) 
			if (hasWay(dicData[i]))
				for(int j = 0; j < dicData[i].length; j++)
					dicData[i][j]++;

		//		for(int i = 0; i < dicData.length; i++) {
		//			for(int j = 0; j < dicData[i].length; j++)
		//				System.out.print(dicData[i][j]);
		//			System.out.println();
		//		}

		instance.setDicData(dicData);
	}

	private boolean hasWay(int[] ia) {
		for(int i = 0; i < ia.length; i++) {
			if(ia[i] == -1)
				return true;
		}
		return false;
	}
}
