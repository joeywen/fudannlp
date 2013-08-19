package edu.fudan.data.reader;

import java.net.URI;
import java.util.Iterator;

import edu.fudan.ml.types.Instance;

/**
 * 字符串数组，每维为一个样本，无类别信息
 * @author xpqiu
 * @version 1.0
 * StringReader
 * package edu.fudan.ml.data
 */
public class StringReader extends Reader
{
	String[] data;
	int index;
	
	public StringReader (String[] data)
	{
		this.data = data;
		this.index = 0;
	}

	public Instance next ()
	{
		return new Instance (data[index++], null);
	}

	public boolean hasNext ()	{	return index < data.length;	}
	
	

}
