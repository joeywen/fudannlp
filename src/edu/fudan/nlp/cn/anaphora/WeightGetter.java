package edu.fudan.nlp.cn.anaphora;

/**
 * 用规则的方法来获取指代对的权重
 * @author jszhao
 * @version 1.0
 * @since FudanNLP 1.5
 */
public class WeightGetter {

	private Entity entity;
	private Entity pronoun;
	public WeightGetter(EntityGroup EntityGroup){
		this.entity = EntityGroup.getAhead();
		this.pronoun = EntityGroup.getBehind();
	}
	private Boolean isSub(String str1,String str2){
		Boolean bl = true;
		for(int i=1;i<str2.length();i++){
			bl = bl&&str1.contains(str2.substring(i, i+1));
			if(i==1&&!bl){
				bl = true;
			}
		}	
		return bl;
	}
	private int roleWeight(){
		if(pronoun.getData().equals("他")||pronoun.getData().equals("她")
				||pronoun.getData().equals("它")){
			
			
			if(entity.getGraTag().equals("SUB")
					&&pronoun.getGraTag().equals(entity.getGraTag())){
				return 4;
				
			}
			else if(entity.getDistance()==pronoun.getDistance()&&
					entity.getGraTag().equals("OBJ")&&
					pronoun.getGraTag().equals("OBJ")){
				return 3;
				
			}
			else if(entity.getDistance()==pronoun.getDistance()
					&&((entity.getGraTag().equals("SUB")
							&&pronoun.getGraTag().equals("OBJ"))
							||(entity.getGraTag().equals("OBJ")
									&&pronoun.getGraTag().equals("SUB")))){
				return 1;
				
			}
			
			else if(entity.getDistance()!=pronoun.getDistance()
					&&entity.getGraTag().equals("OBJ")
					&&pronoun.getGraTag().equals("OBJ")){
				return 2;
			}
			else if(entity.getDistance()!=pronoun.getDistance()
					&&((entity.getGraTag().equals("SUB")
							&&pronoun.getGraTag().equals("OBJ"))
							||(entity.getGraTag().equals("OBJ")
									&&pronoun.getGraTag().equals("SUB")))){
				return 1;
			}
			if(entity.getDistance()==pronoun.getDistance()&&
					entity.getSubDistance()==pronoun.getSubDistance()&&
					!entity.getGraTag().equals("ADJ")&&
					!pronoun.getGraTag().equals("ADJ")	){
				return -100;
			}
			return 0;
		}
		else{ 
			if(this.isSub(entity.getData(), pronoun.getData()))
			return 6;
			else
			return -200;
		}
	}
	private int distanceWeight(){
		if(entity.getDistance()==pronoun.getDistance()){
			return (pronoun.getSubDistance()-entity.getSubDistance());
		}
		else 
			return (pronoun.getDistance()-entity.getDistance())+2;
	}
	private int sexWeight(){
		if(entity.getSex().equals(pronoun.getSex())
				&&entity.getSex()!="UNKNOW"){
			return 2;
		}
		else if(entity.getSex()=="UNKNOW"
				||pronoun.getSex()=="UNKNOW")
			return 0;
		else 
			return -100;
	}
	private int numWeight(){
		if(pronoun.getIsSing().equals("YES")&&
				pronoun.getIsSing().equals(entity.getIsSing())){
			return 3;
		}
		else if(pronoun.getIsSing().equals("No")&&
				pronoun.getIsSing().equals(entity.getIsSing())){
			return 5;
		}
		else if(entity.getIsSing().equals("UNKONW")||
				pronoun.getIsSing().equals("UNKONW"))
			return 0;
		else 
			return -100;
	}
	public int getWeight(){
		return (this.numWeight()+this.roleWeight()+this.sexWeight()-this.distanceWeight());
	}
}
