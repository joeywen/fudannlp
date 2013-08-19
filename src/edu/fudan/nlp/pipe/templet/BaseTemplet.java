package edu.fudan.nlp.pipe.templet;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.IFeatureAlphabet;

/**
 * 基于模板的文本序列特征抽取
 * 处理数据格式为：String[][]
 * 例如：
 *     x1 x2 x3
 *     y1 y2 y3
 *     z1 z2 z3
 */
public class BaseTemplet implements Templet {

	private static final long serialVersionUID = -4019640352729137328L;

	String templet;

	int id;
	/**
	 * 特征标记，两维数组，第二维大小为2
	 */
	int[][] dims;

	/**
	 * 构造函数
	 * @param id
	 * @param dims
	 */
	public BaseTemplet(int id, int[][] dims) {
		this.id = id;
		this.dims = dims;
	}

	/**
	 * @see Templet#generateAt(Instance, IFeatureAlphabet, int...)
	 */
	public int[] generateAt(Instance instance, IFeatureAlphabet features,
			int numLabels) throws Exception {

		String[][] data = (String[][]) instance.getData();

		int len = data[0].length;
		int[] index = new int[len];
		for(int pos = 0;pos<len;pos++){
			StringBuffer sb = new StringBuffer();
			sb.append(id);
			sb.append(':');
			for (int i = 0; i < dims.length; i++) {
				String rp = "";
				int k = dims[i][0]; //行号
				int j = dims[i][1]; //列号
				if (pos + j < 0 || pos + j >= len) {
					if (pos + j < 0)
						rp = "B_" + String.valueOf(-(pos + j) - 1);
					if (pos + j >= len)
						rp = "E_" + String.valueOf(pos + j - len);
				} else {
					rp = data[k][pos + j];
				}
				if (-1 != rp.indexOf('$'))
					rp = rp.replaceAll("\\$", "\\\\\\$");
				sb.append(rp);
				sb.append("//");
			}
//			System.out.println(sb.toString());
			index[pos] = features.lookupIndex(sb.toString(),numLabels);
		}
		return index;
	}

	public String toString() {
		return this.templet;
	}
}
