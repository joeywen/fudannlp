package edu.fudan.nlp.cn.anaphora;

import edu.fudan.ml.types.Instance;
import edu.fudan.nlp.cn.Tags;
/**
 * 用于训练模型的特征生成
 * @author jszhao
 * @version 1.0
 * @since FudanNLP 1.5
 */
public class FeatureGeter {
	private int[] featrue;
	private EntityGroup eGroup;
	private Instance inst;
	public FeatureGeter(Instance inst){
		this.inst =inst;
		this.eGroup = (EntityGroup) inst.getData();
		featrue = new int[19];
		this.doFeature();
	}
	public FeatureGeter(EntityGroup eGroup){
		featrue = new int[19];
		this.eGroup = eGroup;
		this.doFeature();
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

	private void doFeature(){   
		Entity ahead = this.eGroup.getAhead();
		Entity behind = this.eGroup.getBehind();
		String aheadData = ahead.getData();
		String behindData = behind.getData();
		Boolean bool = this.isSub(aheadData, behindData);		
		if(bool){                                   //中心词匹配
			featrue[0] = 1;
		}
		else
			featrue[0] = 0;				
 
		if(ahead.getPosTag().contains("代词"))    //I为人称代词
			featrue[1] = 1;
		else
			featrue[1] = 0;
		if(ahead.getPosTag().equals("名词"))
			featrue[2] = 1;
		else
			featrue[2] = 0;
		if(Tags.isEntiry(ahead.getPosTag()))
			featrue[3] = 1;
		else
			featrue[3] = 0;
		
		if(behind.getData().contains("他")||behind.getData().contains("她"))        //J为人称代词
			featrue[4] = 1;
		else
			featrue[4] = 0;
		if(behind.getData().contains("它"))        //J为人称代词
			featrue[5] = 1;
		else
			featrue[5] = 0;
		if(behind.getData().contains("我")||behind.getData().contains("你"))        //J为人称代词
			featrue[6] = 1;
		else
			featrue[6] = 0;
		if(behind.getData().contains("自己"))        //J为人称代词
			featrue[7] = 1;
		else
			featrue[7] = 0;
		if(behind.getPosTag().contains("代词"))
			featrue[8] = 1;
		else
			featrue[8] = 0;
		if(behindData.contains("这")||behindData.contains("那")||behindData.contains("其")||behindData.contains("该"))  //J为指示性名词
			featrue[9] = 1;
		else
			featrue[9] = 0;
		if(ahead.getPosTag().contains("代词"))
			featrue[8] = 1;
		else
			featrue[8] = 0;
		if(ahead.getPosTag().equals("名词"))
			featrue[9] = 1;
		else
			featrue[9] = 0;
		if(Tags.isEntiry(ahead.getPosTag()))
				featrue[10] = 1;
			else
				featrue[10] = 0;
		//是否性别一致
		if(!ahead.getSex().equals(behind.getSex())&&ahead.getSex()!="UNKNOW"){
			featrue[11] = 1;
		}
		else
			featrue[11] = 0;
		if(ahead.getSex()=="UNKNOW"||behind.getSex()=="UNKNOW")
			featrue[12] = 1;
		else 
			featrue[12] = 0;
		//是否单复数一致
		if(!ahead.getIsSing().equals(behind.getIsSing())&&ahead.getIsSing()!="UNKNOW"){
			featrue[13] = 1;
		}
		else
			featrue[13] = 0;
		if(ahead.getIsSing()=="UNKNOW"||behind.getIsSing()=="UNKNOW")
			featrue[14] = 1;
		else 
			featrue[14] = 0;
		if(ahead.getGraTag().equals("SUB"))
			featrue[15]= 1;
		else
			featrue[15]= 0;
		if(behind.getGraTag().equals("SUB"))
			featrue[16]= 1;
		else
			featrue[16]= 0;
		if(ahead.getGraTag().equals("OBJ"))
			featrue[17]= 1;
		else
			featrue[17]= 0;
		if(behind.getGraTag().equals("OBJ"))
			featrue[18]= 1;
		else
			featrue[18]= 0;		
	
	}
	public Instance getInst(){
		return this.inst;
	}
	public int[]getFeatrue(){
		return this.featrue;
	}
	public EntityGroup getEgroup(){
		return this.eGroup;
	}
}
