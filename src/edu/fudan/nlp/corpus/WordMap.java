/*
 * 文件名：WordMap.java
 * 版权：Copyright 2008-20012 复旦大学 All Rights Reserved.
 * 描述：程序总入口
 * 修改人：xpqiu
 * 修改时间：2008-12-26
 * 修改内容：新增
 *
 * 修改人：〈修改人〉
 * 修改时间：YYYY-MM-DD
 * 跟踪单号：〈跟踪单号〉
 * 修改单号：〈修改单号〉
 * 修改内容：〈修改内容〉
 */
package edu.fudan.nlp.corpus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.fudan.nlp.similarity.EditDistance;
import edu.fudan.nlp.similarity.EditDistanceWithSemantic;
import edu.fudan.nlp.similarity.ISimilarity;

/**
 * 进行单词、短语的映射，将相同意思的词都标准化成同一的词
 * @author Administrator
 * @version 1.0
 * @since 1.0
 */
public class WordMap {

	private Map<String, String> nameMap;
	private String fileName;
	private String serFileName;
	ISimilarity is;

	/**
	 * 初始化实例
	 * @param filename 保存单词匹配的文件名
	 */
	public WordMap(String filename){
		fileName = filename;
		buildNameMap();

	}

	/**
	 * @param filename 
	 * @return
	 */
	private void buildNameMap() {

		nameMap = Collections.synchronizedMap(new HashMap<String, String>()); 
		try {		
			InputStreamReader  read = new InputStreamReader (new FileInputStream(fileName),"utf-8");
			BufferedReader bin = new BufferedReader(read);
			String info = bin.readLine();
			while(info!=null&&info.length()>0){
				String[] toks = info.split("\\s+");
				for(int i=0;i<toks.length;i++){
					nameMap.put(toks[i], toks[0]);
				}
				info = bin.readLine();
			}
		}catch(Exception e){

		}

	}

	/**
	 * 查找输入词有没有对应的标准词
	 * @param word 
	 * @return 词
	 */
	public String getMap(String word){
		if(nameMap==null||!nameMap.containsKey(word))
			return word;
		else
			return nameMap.get(word);
	}


	/**
	 * 查找输入词有没有对应的标准词,进行宽松的匹配方法
	 * @param str
	 * @return 词
	 * @throws Exception
	 */
	public String getLooseMap (String str) throws Exception {
		if(is==null)
			is = new EditDistanceWithSemantic();

		String resName = str;

		if(str==null||str.trim().length()==0)
			return resName;
		for(Iterator it = nameMap.keySet().iterator();it.hasNext();){
			str = (String) it.next();
			if(is.calc(str,resName)==0){
				resName = nameMap.get(str);
				System.out.println("匹配："+str+"<"+resName);
				break;
			}
		}
		return resName;
	}

}
