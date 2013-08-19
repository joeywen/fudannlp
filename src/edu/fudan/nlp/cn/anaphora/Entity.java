package edu.fudan.nlp.cn.anaphora;

/**
 * 实体
 * @author jszhao
 * @version 1.0
 * @since FudanNLP 1.5
 */
public class Entity implements Comparable<Entity>{
	private String data;
	private String posTag;
	private String graTag;
	private String sex;
	private String isSing;
	private int subDistance;
	private int distance;
	private int start;
	private int end;
	private int id;
	private boolean isResolution;
	
	public int getId(){
		return this.id;
	}
	public boolean getIsResolution() {
		return this.isResolution;
	}
	public String getData(){
		return this.data;
	}
	public String getPosTag(){
		return this.posTag;
	}
	public String getGraTag(){
		return this.graTag;
	}
	public String getSex(){
		return this.sex;
	}
	public String getIsSing(){
		return this.isSing;
	}
	public int getSubDistance(){
		return this.subDistance;
	}
	public int getDistance(){
		return this.distance;
	}
	public int getStart(){
		return this.start;
	}
	public int getEnd(){
		return this.end;
	}
	
	public void setId(int id){
		this.id = id;
	}
	public void setIsResolution(boolean isResolution){
		this.isResolution = isResolution;
	}
	public void setData(String data){
		this.data = data;
	}
	public void setPosTag(String posTag){
		this.posTag = posTag;
	}
	public void setGraTag(String graTag){
		this.graTag = graTag;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	public void setSingleOrNot(String singleOrNot){
		this.isSing = singleOrNot;
	}
	public void setSubDistance(int subDistance){
		this.subDistance  = subDistance;
	}
	public void setDistance(int distance){
		this.distance = distance;
	}
	public void setStart(int start){
		this.start = start;
	}
	public void setEnd(int end){
		this.end = end;
	}
	@Override
	public int compareTo(Entity et) {
		
		if(this.getStart()>et.getStart())
			return 1;
		else if(this.getStart()<et.getStart())
			return -1;
		else
			return 0;
	}
	
}
