/*
 * 文件名：QuestionTemplate.java
 * 版权：Copyright 2008-20012 复旦大学 All Rights Reserved.
 * 描述：程序总入口
 * 修改人：xpqiu
 * 修改时间：Nov 30, 2008
 * 修改内容：新增
 *
 * 修改人：〈修改人〉
 * 修改时间：YYYY-MM-DD
 * 跟踪单号：〈跟踪单号〉
 * 修改单号：〈修改单号〉
 * 修改内容：〈修改内容〉
 */
package edu.fudan.nlp.pipe.templet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 记录模板
 * 模板格式：#dd#
 * @author xpqiu
 * @version 1.0 
 */
public class RETemplate implements Serializable{

	private static final long serialVersionUID = 7528628307437160316L;
	private static final double BASE = 2;
	ArrayList<String> templates;	
	ArrayList<Integer> lens;	
	ArrayList<Float> weights;	
	ArrayList<Pattern> patterns;
	int minlen = 2;
	
	/**
	 * 保存捕获分组结果
	 */
	ArrayList<ArrayList<String>> matchGroup;

	
	String comment = "";

	public RETemplate(){
		templates = new ArrayList<String>();
		patterns = new ArrayList<Pattern>();
		lens = new ArrayList<Integer>();
		weights = new ArrayList<Float>();
		matchGroup = new ArrayList<ArrayList<String>>();
	}


	/**
	 * @param qm
	 * @throws Exception 
	 */
	public void addTemplates(ArrayList<String> templatesList) throws Exception {
		Iterator<String> it = templatesList.iterator();
		while(it.hasNext()){
			String temp = it.next();
			addTemplate(temp,1);
		}

	}

	/**
	 * 将问题模板转换成Pattern
	 * @param strTemplate
	 * @param weight 
	 * @throws Exception 
	 */
	public void addTemplate(String strTemplate, int weight) throws Exception {
		strTemplate = strTemplate.replaceAll("[\\?\\*\\[\\]\\{\\}\\,\\|]", "");
//		if(strTemplate.contains("."))
//			System.out.println(strTemplate);
		strTemplate = strTemplate.replaceAll("\\.", "\\\\.");
		
		Pattern p = Pattern.compile("(#[^#]+#)+");
		Matcher m = p.matcher(strTemplate);
		if(m.matches())
			return;
		
//		strTemplate = "^"+strTemplate;
//		strTemplate += "$";
		
		String str = new String(strTemplate);
		ArrayList<String> matchs = new ArrayList<String>();
		int len = 0;
		int patternNum = 0;
		while(m.find()) {
			patternNum++;
			String target = m.group();
			len+=target.length();
			matchs.add(target);
			str = str.replace(target, "(.{"+minlen+",})");
		}
		len = str.length()-len;
		if(len<2)
			return;
		
		templates.add(strTemplate);
		lens.add(len);
		weights.add((float) (weight*Math.pow(BASE, Math.max(0,2-patternNum))));
		matchGroup.add(matchs);
		//System.out.println(str);
		try {
			patterns.add(Pattern.compile(str));
		} catch (Exception e) {
			System.out.println(str);
			e.printStackTrace();
			throw e;
		}
	}

	public static void main(String[] args) throws Exception{
		RETemplate qt = new RETemplate();
		qt.addTemplate("最近的#poi#",1);

		float a = qt.matches("最近的加油站");
		System.out.println(a);
	}



	/**
	 * @param str
	 * @return
	 */
	public float matches(String str) {

		Iterator<Pattern> it = patterns.iterator();
		float w = 0;
		while(it.hasNext()){
			Pattern p = it.next();
			Matcher m = p.matcher(str);
			if(m.find()) {//匹配
				int idx = patterns.indexOf(p);
//				System.out.print("模板："+ templates.get(idx));
//				System.out.print("\t");
				ArrayList<String> matchs = matchGroup.get(idx);
				String[] matchedName = new String[matchs.size()];
				int len=0;
				for(int i=1;i<=matchs.size();i++){
					matchedName[i-1] = m.group(i);
					len +=matchedName[i-1].length();
//					System.out.print("匹配："+matchedName[i-1]);
//					System.out.print("\t");					
				}
//				System.out.print("类别："+comment);
//				System.out.print("\t");			
				int mlen = str.length();
				float ww = Math.min(mlen, lens.get(idx));
				ww /= Math.max(mlen, lens.get(idx));
				ww *= weights.get(idx);
				w +=ww;
//				System.out.println("权重："+w);
			}
		}
		
		return w;
	}

	public String toString(){
		return comment;
	}




}
