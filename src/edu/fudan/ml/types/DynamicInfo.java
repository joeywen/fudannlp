package edu.fudan.ml.types;

public class DynamicInfo {
	private String pos;
	private String word;
	private int len;
	
	public DynamicInfo(String pos, String word, int len) {
		this.pos = pos;
		this.word = word;
		this.len = len;
	}
	
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	
	public String toString() {
		return word + "/" + pos + "/" + len;
	}
	
	
}
