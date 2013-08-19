package edu.fudan.data.reader;

import java.util.Iterator;

import edu.fudan.ml.types.Instance;

/**
 * @author xpqiu
 * @version 1.0	
 * Reader为数据读入接口，用一个迭代器依次读入数据，每次返回一个Instance对象
 * 使得数据处理和读入无关
 * package edu.fudan.data.reader
 */
public abstract class Reader implements Iterator<Instance> {

	public void remove () {
		throw new IllegalStateException ("This Iterator<Instance> does not support remove().");
	}
}
