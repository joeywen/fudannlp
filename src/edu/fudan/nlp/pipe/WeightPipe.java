package edu.fudan.nlp.pipe;

import edu.fudan.ml.types.Instance;

public class WeightPipe extends Pipe {

	private static final long serialVersionUID = 1L;
	private static float[] weight = {};
	
	public WeightPipe(boolean b){
		if(b){
			weight = new float[10];
			int i=0;
			for(;i<5;i++){
				weight[i] = 2f;
			}
			for(;i<10;i++){
				weight[i] = 1.5f;
			}
		}
	}

	@Override
	public void addThruPipe(Instance inst) throws Exception {
		
		Object sdata =  inst.getData();
		int len;
		if(sdata instanceof int[][]){//转换后的特征
			int[][] data = (int[][]) sdata;
			len = data.length;
		}else{
			System.err.println("WeightPipe: Error");
			return;
		}
		
		float w;
		if(len<weight.length)
			w = weight[len-1];
		else
			w = 1f;

		inst.setWeight(w);

	}

}
