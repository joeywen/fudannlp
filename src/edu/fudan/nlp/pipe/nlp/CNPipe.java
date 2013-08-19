package edu.fudan.nlp.pipe.nlp;

import edu.fudan.ml.types.Instance;
import edu.fudan.nlp.cn.tag.CWSTagger;
import edu.fudan.nlp.pipe.Pipe;

/**
 * 进行分词等操作
 * @author xpqiu
 *
 */
public class CNPipe extends Pipe{

	private static final long serialVersionUID = -2329969202592736092L;
	private transient CWSTagger seg;

	public CNPipe() {
	}

	public CNPipe(CWSTagger seg) {
		this.seg = seg;
	}

	@Override
	public void addThruPipe(Instance inst) {
		String data = (String) inst.getData();
		String[] newdata = seg.tag2Array(data);
		inst.setData(newdata);
	}
}
