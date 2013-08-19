package edu.fudan.nlp.cn.tag.format;

import java.util.ArrayList;
import java.util.List;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.InstanceSet;

/**
 * 将序列标注转换成数组
 * 
 * @author xpqiu
 * 
 */
public class Seq2ArrayWithTag {

	public static String format(InstanceSet testSet, String[][] labelsSet) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < testSet.size(); i++) {
			Instance inst = testSet.getInstance(i);
			String[] labels = labelsSet[i];
			sb.append(format(inst, labels));
		}
		return sb.toString();
	}

	public static List[] format(Instance inst, String[] labels) {
		String[][] data = (String[][]) inst.getSource();

		List<String> w = new ArrayList<String>();
		List<String> p = new ArrayList<String>();

		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < data.length; j++) {
			String label = labels[j];
			int tagidx = label.indexOf("-");
			String tag = label.substring(tagidx + 1);
			label = label.substring(0, tagidx);
			String c = data[j][0];
			sb.append(c);
			if (label.equals("E") || label.equals("S")) {
				w.add(sb.toString());
				p.add(tag);
				sb = new StringBuilder();
				tag = "";
			}
		}
		return new List[] { w, p };
	}
}
