package edu.fudan.nlp.parser;

import java.util.Arrays;

import edu.fudan.nlp.parser.dep.DependencyTree;

/**
 * 句法树的另一种表示形式
 * ，包括依赖词的id和依赖关系
 * @author 
 *
 */
public class Target {
	private String[] words;
	private String[] pos;
	private String[] relations;
	private int[] heads;

	public Target(int[] heads ,String[] relations){
		this.heads = heads;
		this.relations = relations;
	}
	public Target(int len) {
		words= new String[len];
		pos = new String[len];
		relations = new String[len];
		heads = new int[len];
		Arrays.fill(heads, -1);
	}
	public Target() {
	}

	public String getDepClass(int idx){
		return this.relations[idx];
	}
	public int getHead(int idx){
		return this.heads[idx];
	}

	public void setDepClass(int i, String relations){
		this.relations[i] = relations;
	}
	public void setHeads(int i, int heads){
		this.heads[i] = heads;
	}
	public int size() {

		return heads.length;
	}
	public String[] getRelations() {
		return relations;
	}
	public int[] getHeads() {
		return heads;
	}
	public static Target ValueOf(DependencyTree dt) {
		Target t = new Target(dt.size());
		to2HeadsArray(dt,t);
		return t;
	}

	private static void to2HeadsArray(DependencyTree dt, Target t) {
		for(int i = 0; i < dt.leftChilds.size(); i++)	{
			DependencyTree ch = dt.leftChilds.get(i);
			t.setHeads(ch.id, dt.id);
			t.setDepClass(ch.id, ch.getDepClass());
			to2HeadsArray(ch,t);
		}
		t.words[dt.id] = dt.word;
		t.pos[dt.id] = dt.pos;
		for(int i = 0; i < dt.rightChilds.size(); i++)	{
			DependencyTree ch = dt.rightChilds.get(i);
			t.setHeads(ch.id,dt.id);
			t.setDepClass(ch.id,ch.getDepClass());
			to2HeadsArray(ch,t);
		}
	}


}
