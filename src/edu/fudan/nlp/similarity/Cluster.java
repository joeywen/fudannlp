package edu.fudan.nlp.similarity;

import java.io.Serializable;

public class Cluster implements Serializable {

	private static final long serialVersionUID = 5016034594028420143L;
	float prop;
	int id;
	public String rep;
	Cluster left;
	Cluster right;


	public Cluster(int key, float v, String s) {
		id = key;
		prop = v;
		rep = s;
	}

	public Cluster(int newid, Cluster c1, Cluster c2, float pc) {
		id = newid;
		prop = pc;
		left = c1;
		right = c2;
		rep = c1.rep+":"+c2.rep;		
	}

	String getN() {
		return id+":"+rep+" "+ prop;
	}

	Cluster getRight() {
		return right;
	}

	Cluster getLeft() {
		return left;
	}  
	public String toString(){
		return rep;
	}
}
