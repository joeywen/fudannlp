package edu.fudan.nlp.cn.ner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Address {
	ArrayList<Pattern> patterns;

	public Address(){
		patterns= new ArrayList<Pattern>();
		patterns.add(Pattern.compile("地址：\\s*(.{2,6}市?.{2,6}路.{2,6}号.{2,6}号楼.{2,6}室)"));
		patterns.add(Pattern.compile("地址：\\s*(.{2,6}路.{2,6}号.+号楼.+室)"));
		patterns.add(Pattern.compile("地址：\\s*(.{2,6}路.{2,6}号)"));
		patterns.add(Pattern.compile("地址：\\s*(.{2,6}市.{2,6}区.{2,6}路.{2,6}号)\\s+"));
	}
	public List<String> tag(String str){
		Iterator<Pattern> it = patterns.iterator();
		List<String> list = new ArrayList<String>();
		while(it.hasNext()){
			Pattern p = it.next();
			Matcher m = p.matcher(str);
			if(m.find()) {//匹配
				int idx = patterns.indexOf(p);				
				for(int i=1;i<=m.groupCount();i++){
					list.add(m.group(i));
				}
			}
		}
		return list;
	}
	public static void main(String[] args) throws Exception {
		String ss = "地址：上海市杨浦区邯郸路220号 \n地址：上海莱希信息科技有限公司（以下简称“上海莱希”）在上海成立，注册地为国家863...联系人：张先生地址：上海张江高科技园区毕升路299号11号楼402室 电话：33932148 ...";
		Address ad = new Address();
		System.out.println(ad.tag(ss));
	}
}
