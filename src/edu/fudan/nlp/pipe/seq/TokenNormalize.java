package edu.fudan.nlp.pipe.seq;

import java.io.Serializable;
import java.util.Arrays;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.LabelAlphabet;
import edu.fudan.nlp.cn.Chars;
import edu.fudan.nlp.pipe.Pipe;

public class TokenNormalize extends Pipe implements Serializable {

	private static final long serialVersionUID = 8129957080708134793L;

	private LabelAlphabet labels;

	public TokenNormalize(LabelAlphabet labels) {
		this.labels = labels;
	}

	/**
	 * 将英文、数字标点硬标为S，目前废弃
	 */
	public void addThruPipe(Instance instance) throws Exception {
		String[][] data = (String[][]) instance.getData();

		int[][] tempData = new int[data[0].length][labels.size()];
		
		
		for (int i = 0; i < data[0].length; i++) {
			char s = data[0][i].charAt(0);
			if (Chars.isLetterOrDigitOrPunc(s)) {
				Arrays.fill(tempData[i], 1);
				tempData[i][labels.lookupIndex("S")] = 0;
			}
		}

//		for(int i = 0; i < tempData.length; i++) {
//			for(int j = 0; j < tempData[i].length; j++)
//				System.out.print(tempData[i][j]);
//			System.out.println();
//		}
		
		instance.setTempData(tempData);
	}
}
