package edu.fudan.nlp.pipe.seq;

import java.util.Arrays;

import edu.fudan.ml.types.Instance;
import edu.fudan.nlp.pipe.Pipe;

/**
 * 将数据和标签分开
 * 
 * @author xpqiu
 * @deprecated 不再使用
 */
public class SplitDataAndTarget extends Pipe{

	private static final long serialVersionUID = 331639154658696010L;

	private int before = -1;
	private int target = -1;

	public SplitDataAndTarget() {
		this(-1, -1);
	}

	public SplitDataAndTarget(int before, int target) {
		assert(before < target);
		this.before = before;
		this.target = target;
	}

	public void addThruPipe(Instance instance) {
		String[][] seq = (String[][]) instance.getData();
		String[][] data = new String[seq.length][];
		String[] tags = new String[seq.length];
		for (int i = 0; i < seq.length; i++) {
			String[] arr = seq[i];
			if (arr.length < 2) {
				System.err
						.println("The number of column must be 2 at least. skip");
				System.err.println(arr[0]);
				continue;
			}
			if (before == -1) {
				before = target = arr.length - 1;
			} else {
				if (before >= arr.length)	{
					System.err.println();
				}
				if (target >= arr.length)	{
					System.err.println();
				}
			}

			data[i] = Arrays.copyOfRange(arr, 0, before);
			tags[i] = arr[target];
		}
		instance.setData(data);
		instance.setTarget(tags);
	}
}
