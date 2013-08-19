package edu.fudan.nlp.pipe.seq;

import edu.fudan.ml.types.Instance;
import edu.fudan.nlp.cn.Chars;
import edu.fudan.nlp.pipe.Pipe;

/**
 * 处理混合语言字符串
 * @author Feng Ji
 *
 */
public class MixedString2Sequence extends Pipe {

	@Override
	public void addThruPipe(Instance inst) throws Exception {
		String str = (String) inst.getData();
		char[] toks = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < toks.length; i++)	{
			if (Chars.isChar(toks[i]))	{
				sb.append(toks[i]);
				sb.append(" ");
			}
		}
	}

}
