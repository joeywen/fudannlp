package edu.fudan.data.reader;

import java.util.List;

import edu.fudan.ml.types.Instance;

public class ListReader extends Reader{

	//测试指代消解临时写的   jszhao
	List<String>[] data;
	int index;

	public ListReader (List<String>[] data)
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
