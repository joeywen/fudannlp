package edu.fudan.ontology;

import gnu.trove.set.hash.TCharHashSet;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

/**
 * 简易字典
 * @author xpqiu
 *
 */
public class CharClassDictionary implements Serializable{

	private static final long serialVersionUID = 4368388258602283597L;

	public String name;
	private TCharHashSet dict;

	public CharClassDictionary() {
		dict = new TCharHashSet();
	}
	public void load(String path,String tag) throws Exception{
		if(path == null) return;
		BufferedReader bfr;
		bfr = new BufferedReader(new InputStreamReader(new FileInputStream(path),"utf8"));
		String line = null;			
		while ((line = bfr.readLine()) != null) {
			if(line.length()==0)
				continue;
			int len = line.length();
			dict.add(line.charAt(0));
			if(len>1){
				for(int i=1;i<len-1;i++){
					dict.add(line.charAt(i));
				}
				dict.add(line.charAt(len-1));
			}
		}
		name = tag;

	}	


	/**
	 * 返回词典标签
	 * @param word
	 * @return 词典列表
	 */
	public boolean contains(char c) {
		return dict.contains(c);
	}
}
