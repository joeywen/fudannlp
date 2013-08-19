/*
 * 文件名：SimpleFileReader.java
 * 版权：Copyright 2008-20012 复旦大学 All Rights Reserved.
 * 描述：
 * 修改人：xpqiu
 * 修改时间：2009 Sep 2, 2009 6:19:22 PM
 * 修改内容：新增
 *
 * 修改人：〈修改人〉
 * 修改时间：YYYY-MM-DD
 * 跟踪单号：〈跟踪单号〉
 * 修改单号：〈修改单号〉
 * 修改内容：〈修改内容〉
 */
package edu.fudan.data.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.sv.HashSparseVector;
import edu.fudan.ml.types.sv.SparseVector;

/**
 * @author xpqiu
 * @version 1.0 
 * 简单文件格式如下： 类别 ＋ “空格” ＋ 数据 package
 * 
 */
public class svmFileReader extends Reader {

	String content = null;
	BufferedReader reader;
	int type = 1;

	public svmFileReader(String file) {
		try {
			File f = new File(file);
			FileInputStream in = new FileInputStream(f);
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param file
	 * @param type （+1,-1,0）分别表示类标签在每行的（左，右，无）
	 */
	public svmFileReader(String file,int type) {
		this(file);
		this.type = 1;
		
	}

	public boolean hasNext() {
		try {
			content = reader.readLine();
			if (content == null) {
				reader.close();
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;

		}
		return true;
	}

	public Instance next() {
		String[] tokens = content.split("\\t+|\\s+");
		HashSparseVector sv = new HashSparseVector();
		
		for (int i = 1; i < tokens.length; i++) {
			String[] taken = tokens[i].split(":");
			if (taken.length > 1) {
				float value = Float.parseFloat(taken[1]);
				int idx = Integer.parseInt(taken[0]);
				sv.put(idx, value);
			}
		}
		return new Instance(sv, tokens[0]);
	}

}
