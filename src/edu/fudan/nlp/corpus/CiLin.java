/*
 * 文件名：CiLin.java
 * 版权：Copyright 2008-20012 复旦大学 All Rights Reserved.
 * 描述：程序总入口
 * 修改人：xpqiu
 * 修改时间：2008-12-25
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
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;


/**
 * 本类用来分析《哈工大同义词林》
 * @author Administrator
 * @version 1.0
 * @since 1.0
 */
public class CiLin {

	/**
	 * 找出同义的词对,建造hashset;
	 * @return  同义词集合
	 */
	public static HashSet buildSynonymSet(String fileName){

		try {		
			InputStreamReader  read = new InputStreamReader (new FileInputStream(fileName),"utf-8");
			BufferedReader bin = new BufferedReader(read);
			HashSet<String> synSet = new HashSet<String>();
			int c=0;
			String str = bin.readLine();
			while(str!=null&&str.length()==0){
				String[] strs = str.trim().split(" ");
				if(strs[0].endsWith("=")){
					//System.out.println(strs[0]);
					int wordNum = Integer.parseInt(strs[1]);

					for(int i=2;i<2+wordNum-1;i++){
						for(int j=i+1;j<2+wordNum;j++){

							String combine1 = strs[i]+"|"+strs[j];
							System.out.println(combine1 + c);
							synSet.add(combine1);
							String combine2 = strs[j]+"|"+strs[i];
							synSet.add(combine2);
							c++;
						}
					}
				}else{

				}
				str = bin.readLine();
			}
			return synSet;
		}catch(Exception e){
			return null;
		}


	}

	public static void main(String[] argv){
		HashSet<String> synSet = buildSynonymSet("\\\\10.11.7.3\\f$\\对于共享版《同义词词林》的改进\\improvedThesaurus.data");
	}

}
