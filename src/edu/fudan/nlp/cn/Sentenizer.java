package edu.fudan.nlp.cn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.fudan.util.MyStrings;

/**
 * 简单中文文本的断句
 * @author xpqiu
 * @version 1.0
 * @since FudanNLP 1.5
 */
public class Sentenizer {

	private static char[] puncs = new char[] { '。', '？', '！','；' };
	
	/**
	 * 根据标点符号进行断句
	 * @param sent
	 * @return
	 */

    public static void setPuncs(char[] puncs) {
        Sentenizer.puncs = puncs;
    }

	public static String[] split(String sent) {
		List<Integer> plist = new ArrayList<Integer>();
		int p = 0;
		for (int i = 0; i < puncs.length; i++) {
			p = sent.indexOf(puncs[i]);
			while (p != -1) {
				plist.add(p);
				p = sent.indexOf(puncs[i], p + 1);
			}
		}
		Collections.sort(plist);
		if (!plist.isEmpty()) {
			p = plist.get(plist.size() - 1);
			if (p < sent.length() - 1)
				plist.add(sent.length() - 1);
		}else	{
			plist.add(sent.length() - 1);
		}

		String[] ret = new String[plist.size()];
		p = 0;
		for (int i = 0; i < plist.size(); i++) {
			ret[i] = sent.substring(p, plist.get(i) + 1);
			p = plist.get(i) + 1;
		}
		plist.clear();

		return ret;
	}
}
