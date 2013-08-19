/*
 * 文件名：Target2Label.java
 * 版权：Copyright 2008-20012 复旦大学 All Rights Reserved.
 * 修改人：xpqiu
 * 修改时间：2009 Sep 2, 2009 6:54:46 PM
 * 修改内容：新增
 *
 * 修改人：〈修改人〉
 * 修改时间：YYYY-MM-DD
 * 修改内容：〈修改内容〉
 */
package edu.fudan.nlp.pipe;

import java.io.Serializable;
import java.util.List;
import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.LabelAlphabet;
import edu.fudan.util.exception.UnsupportedDataTypeException;

/**
 * 将目标值对应的索引号作为类别
 * 
 * @author xpqiu
 * @version 1.0 Target2Label
 */
public class Target2Label extends Pipe implements Serializable {

	private static final long serialVersionUID = -4270981148181730985L;
	private LabelAlphabet labelAlphabet;


	public Target2Label(LabelAlphabet labelAlphabet) {
		this.labelAlphabet = labelAlphabet;
		useTarget = true;
	}

	@Override
	public void addThruPipe(Instance instance) throws UnsupportedDataTypeException {
		// 处理类别
//		instance.setTempData(instance.getTarget());

		Object t = instance.getTarget();
		if (t == null)
			return;

		if (t instanceof String) {
			instance.setTarget(labelAlphabet.lookupIndex((String) t));
		} else if (t instanceof Object[]) {
			Object[] l = (Object[]) t;
			int[] newTarget = new int[l.length];
			for (int i = 0; i < l.length; ++i)
				newTarget[i] = labelAlphabet.lookupIndex((String) l[i]);
			instance.setTarget(newTarget);
		} else if (t instanceof List) {
			List l = (List) t;
			int[] newTarget = new int[l.size()];
			for (int i = 0; i < l.size(); ++i)
				newTarget[i] = labelAlphabet.lookupIndex((String) l.get(i));
			instance.setTarget(newTarget);
		}else{
			throw new UnsupportedDataTypeException(t.getClass().toString());
		}
	}
}