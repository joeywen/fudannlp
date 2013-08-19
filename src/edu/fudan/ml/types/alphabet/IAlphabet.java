package edu.fudan.ml.types.alphabet;


import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.Serializable;

/**
 * 抽象词典类
 * @author Feng Ji
 *
 */
public interface IAlphabet extends Serializable {

	
	/** the default capacity for new collections */
    public static final int DEFAULT_CAPACITY = 10;

    /** the load above which rehashing occurs. */
    public static final float DEFAULT_LOAD_FACTOR = 0.5f;
    
    int noEntryValue = -1;

	/**
	 * 判断词典是否冻结
	 * @return true - 词典冻结；false - 词典未冻结
	 */
	public boolean isStopIncrement();

	/**
	 * 不再增加新的词
	 * @param b
	 */
	public void setStopIncrement(boolean b);
	
	/**
	 * 查找字符串索引编号
	 * @param str 字符串
	 * @return 索引编号
	 */
	public int lookupIndex(String str);
	
	/**
	 * 词典大小
	 * @return 词典大小
	 */
	public int size();

	/**
	 * 恢复成新字典
	 */
	public void clear();

}
