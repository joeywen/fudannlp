package edu.fudan.nlp.cn;

import java.util.regex.Pattern;

/**
 * 中文词性操作类
 * @author xpqiu
 * @version 1.0
 * @since FudanNLP 1.5
 */
public class Tags {
	
	public static enum PartOfSpeech {
	      Noun, Verb, Adjective, Adverb, Pronoun, Preposition, Conjunction, Article, Unknown
	    }
	
	static Pattern entitiesPattern  = Pattern.compile("人名|地名|机构名|专有名");
	/**
	 * 判断词性是否为一个实体，包括：人名|地名|机构名|专有名。
	 * @param pos 词性
	 * @return true,false
	 */
	public static boolean isEntiry(String pos) {
		return (entitiesPattern.matcher(pos).find());
	}
	
	static Pattern nounsPattern  = Pattern.compile("名词|人名|地名|机构名|专有名");
	
	public static boolean isNoun(String pos) {
		return (nounsPattern.matcher(pos).find());
	}

	
	static Pattern stopwordPattern  = Pattern.compile(".*代词|标点|介词|从属连词|语气词|叹词|结构助词|拟声词|方位词");
	/**
	 * 判断词性是否为无意义词。
	 * @param pos 词性
	 * @return true,false
	 */
	public static boolean isStopword(String pos) {
		return (stopwordPattern.matcher(pos).find());
	}
}
