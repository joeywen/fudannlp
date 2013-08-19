package edu.fudan.nlp.cn.anaphora;

/**
 * 实体对
 * @author jszhao
 * @version 1.0
 * @since FudanNLP 1.5
 */
public class EntityGroup {
	private Entity ahead;  // 先行词
	private Entity behind;      //照应语
	private int weight;
	
	public EntityGroup( Entity ahead, Entity behind ){
		this.ahead=ahead;
		this.behind = behind;
	}
	public Entity getAhead(){
		return ahead;
	}
	public Entity getBehind(){
		return behind;
	}
	public int getWeight(){
		return this.weight;
	}
	public void setAhead(Entity ahead){
		this.ahead= ahead;
	}
	public void setBehind(Entity behind){
		this.behind = behind;
	}
	public void setWeight(int weight){
		this.weight = weight;
	}

}
