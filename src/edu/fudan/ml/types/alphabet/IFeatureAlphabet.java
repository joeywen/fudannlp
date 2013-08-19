package edu.fudan.ml.types.alphabet;

import gnu.trove.impl.hash.TIntHash;
import gnu.trove.iterator.TIterator;
import gnu.trove.iterator.TObjectIntIterator;
import gnu.trove.map.hash.THashMap;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * 特征词典
 * @author Feng Ji
 *
 */
public interface IFeatureAlphabet extends IAlphabet {

	/**
	 * 查询字符串索引编号
	 * @param str 字符串
	 * @param indent 间隔
	 * @return 字符串索引编号，-1表示词典中不存在字符串
	 */
	public int lookupIndex(String str, int indent);


	/**
	 * 字典键的个数
	 * @return
	 */
	public int keysize();
	
	/**
	 * 实际存储的数据大小
	 * @return
	 */
	public int nonZeroSize();

	/**
	 * 索引对应的字符串是否存在在词典中
	 * @param id 索引
	 * @return 是否存在在词典中
	 */
	public boolean hasIndex(int id);

	public TIterator iterator();
	
	/**
	 * 按索引建立HashMap并返回
	 * @return 按“索引-特征字符串”建立的HashMap
	 */
	public TIntHash toInverseIndexMap();
	
}
