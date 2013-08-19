package edu.fudan.ontology;

import edu.fudan.util.MyCollection;
import gnu.trove.iterator.hash.TObjectHashIterator;
import gnu.trove.set.hash.THashSet;

import java.io.IOException;
import java.io.Serializable;

/**
 * 简易字典
 * @author xpqiu
 * @version 1.0
 * @since FudanNLP 1.5
 */
public class Dictionary implements Serializable{

	private static final long serialVersionUID = 4368388258602283597L;
	
	public String name;
	int maxLen;
	private THashSet<String> dict;

	public Dictionary() {
		dict = new THashSet<String>();
	}
	/**
	 * 从文件中读取
	 * @param path 文件路径
	 * @param tag 词典名
	 * @throws IOException 
	 */
	public void load(String path,String tag) throws IOException{
		if(path == null) return;
		dict = MyCollection.loadTSet(path);
		maxLen = 0;
		TObjectHashIterator<String> it = dict.iterator();
		while(it.hasNext()){
			String k = it.next();
			if(k.length()>maxLen){
				maxLen = k.length();
			}
		}		
		name = tag;		
	}	
	
	
	
	/**
	 * 返回词典标签
	 * @param word
	 * @return 词典列表
	 */
	public boolean contains(String word) {
		if(word.length()>maxLen)
			return false;
		return dict.contains(word);
	}
}
