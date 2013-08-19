package edu.fudan.nlp.similarity;

/**
 * @author xpqiu
 * @version 1.0
 * @since 1.0
 * ISimilarity
 */
public interface ISimilarity <E> {
	
	public float calc(E item1,E item2);

}
