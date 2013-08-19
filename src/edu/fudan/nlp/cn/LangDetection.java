package edu.fudan.nlp.cn;

import java.lang.Character.UnicodeBlock;

public class LangDetection {

	public static String detect(String str){
		char[] ch = str.toCharArray();
		if(isChinese(ch))
			return "cn";
		else
			return "en";
	}
	public static boolean isChinese(char[] ch){
		for(int i=0;i<ch.length;i++){
			if(isChinese(ch[i]))
				return true;
		}
		return false;
	}
	
	private static boolean isChinese(char c) {
		UnicodeBlock ub = UnicodeBlock.of(c);
		if(ub==UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
			ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS||
			ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A||
			ub == UnicodeBlock.GENERAL_PUNCTUATION||
			ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION||
			ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
			return true;
		return false;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str;
		str = ".";
		System.out.println(LangDetection.detect(str)+":\t"+str);
		
		str = "you and me";
		System.out.println(LangDetection.detect(str)+":\t"+str);
		
		str = "()";
		System.out.println(LangDetection.detect(str)+":\t"+str);
		
		str = "。";
		System.out.println(LangDetection.detect(str)+":\t"+str);
		str = "我们";
		System.out.println(LangDetection.detect(str)+":\t"+str);
		str = "我们and";
		System.out.println(LangDetection.detect(str)+":\t"+str);
		str = "《and";
		System.out.println(LangDetection.detect(str)+":\t"+str);

	}

}
