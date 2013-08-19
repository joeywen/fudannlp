package edu.fudan.nlp.parser;

import edu.fudan.ml.types.Instance;

/**
 * Instance 对应着一句句子实例
 * 
 * 句子实例包括词序列，相应的Postag序列，每个词对应的中心词（用该词在句子中的下标表示） 完成从输入流中读入句子实例，保存句子实例。
 * 
 * @author cshen
 * @version Feb 16, 2009
 */
public class Sentence extends Instance {
	public String[] words = null;
	public String[] tags = null;
	int length = 0;

	public Sentence(String[] words) {
		this(words, null);
	}

	public Sentence(String[] words, String[] tags) {
		this.words = words;
		this.tags = tags;
		length = words.length;
	}

	public Sentence(String[] words, String[] tags, int[] heads) {
		this(words, tags);
		this.target = heads;
	}
//add
	public Sentence(String[] words, String[] tags, Target target) {
		this(words, tags);
		this.target = target;
	}
	public String getDepClass(int n){
		if (n > length || n < 0)
			throw new IllegalArgumentException(
					"index should be less than length or great than 0!");
		Target target = (Target)this.target;
		return target.getDepClass(n);
	}
//>>>
	
//	public Sentence(String[][] strings) {
//		length = strings.length;
//		words = new String[length];
//		tags = new String[length];
//		for(int i=0;i<length;i++){
//			words[i] = strings[i][0];
//			tags[i] = strings[i][1];
//		}
//	}

	public int length() {
		return length;
	}

	public String getWordAt(int n) {
		if (n > length || n < 0)
			throw new IllegalArgumentException(
					"index should be less than length or great than 0!");
		return words[n];
	}

	public String[] getWords() {
		return words;
	}

	
	public String getTagAt(int n) {
		if (n > length || n < 0)
			throw new IllegalArgumentException(
					"index should be less than length or great than 0!");
		return tags[n];
	}

	public String[] getTags() {
		return tags;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buf.append(words[i]);
			if (tags != null) {
				buf.append("/");
				buf.append(tags[i]);
			}
			buf.append(" ");
		}
		return buf.toString().trim();
	}
}
