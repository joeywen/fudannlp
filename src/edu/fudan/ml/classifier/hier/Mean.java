package edu.fudan.ml.classifier.hier;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.InstanceSet;
import edu.fudan.ml.types.alphabet.LabelAlphabet;
import edu.fudan.ml.types.sv.HashSparseVector;
import edu.fudan.ml.types.sv.ISparseVector;

/**
 * 计算类中心
 * @author xpqiu
 *
 */
public class Mean {



	public static HashSparseVector[] mean (InstanceSet trainingList,Tree tree)
	{

		LabelAlphabet alphabet = trainingList.getAlphabetFactory().DefaultLabelAlphabet();
		int numLabels = alphabet.size();
		HashSparseVector[] means = new  HashSparseVector[numLabels];
		int[] classNum = new int[numLabels];

		for(int i=0;i<numLabels;i++){
			means[i]=new HashSparseVector();
		}

		for (int ii = 0; ii < trainingList.size(); ii++){
			Instance inst = trainingList.getInstance(ii);
			ISparseVector fv = (ISparseVector) inst.getData ();
			int target = (Integer) inst.getTarget();
			if(tree!=null){
				int[] anc = tree.getPath(target);
				for(int j=0;j<anc.length;j++){
					means[anc[j]].plus(fv);
					classNum[anc[j]]+=1;
				}
			}else{
				means[target].plus(fv);
				classNum[target]+=1;
			}
		}
		for(int i=0;i<numLabels;i++){
			if(classNum[i]>0)
				means[i].scaleDivide(classNum[i]);
		}
		return means;
	}

}

