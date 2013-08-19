package edu.fudan.nlp.cn.tag.format;

import java.util.List;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.InstanceSet;
/**
 * 
 * @author xpqiu
 *
 */
public class BasicFormatter {
	public static String format(InstanceSet testSet, String[][] labelsSet) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < testSet.size(); i++) {
			Instance inst = testSet.getInstance(i);
			String[] labels = labelsSet[i];
			sb.append(format(inst, labels));
			sb.append("\n");
		}
		return sb.toString();
	}

	public static String format(Instance inst, String[] labels) {

		StringBuilder sb = new StringBuilder();
		List data = (List) inst.getSource();

		for (int j = 0; j < data.size(); j++) {
			sb.append(data.get(j));
			sb.append('\t');
			sb.append(labels[j]);
			sb.append("\n");
		}
		return sb.toString();
	}
}
