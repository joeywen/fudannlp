package edu.fudan.nlp.cn.anaphora;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Pattern;

import edu.fudan.nlp.cn.tag.POSTagger;

/**
 * 检测文中的实体和代词
 * @author jszhao
 * @version 1.0
 * @since FudanNLP 1.5
 */
public class EntitiesGetter {

	private LinkedList<Entity> EntityList;
	static POSTagger tag;
	Pattern pattern  = Pattern.compile("形谓词|形容词|限定词|名词|人名|地名|机构名|专有名|序数词|代词|数字|量词");

	public EntitiesGetter() throws Exception{

	}
	public EntitiesGetter(String segmodel, String posmodel) throws Exception {
		initTagger(segmodel,posmodel);
	}
	public static void initTagger(POSTagger tagg) {
		tag = tagg;		
	}
	public static void initTagger(String segmodel, String posmodel) throws Exception {
		if(tag==null)
			tag = new POSTagger(segmodel,posmodel);

	}
	private boolean isPart(String str, String str2){
		return (pattern.matcher(str).find()||(str.equals("结构助词")&&str2.equals("的")));
	}

	private final static String PU = "标点";
	private static final String PN = "代词";

	private void doIt(String[][][] taggedstr,String str) {		
		Entity ey = null;	
		int distance = 0;int index =  0;int subDistance = 0;
		String strdata= null; String strtag = null;
		int flag = 0;String graTag = null;

		for(int i=0;i<taggedstr.length;i++){
			String[] words = taggedstr[i][0];
			String[] pos = taggedstr[i][1];
			for(int j=0;j<words.length;j++){	
				distance = i;
				subDistance = 0;
				if(pos[j].equals(PU)&&((words[j].equals("，"))
						||(words[j].equals("：")))){
					subDistance++;
				} 
				String isSing = "UNKONW";
				index = str.indexOf(words[j],index);
				if(isPart(pos[j],words[j])){
					int id = j;
					strdata = words[j];
					strtag = pos[j];
					flag = 0;
					ey = new Entity();
					if(j>0)
						ey.setStart(str.indexOf(words[j],index));
					else
						ey.setStart(str.indexOf(words[j]));
					while(j<words.length-1){
						boolean isTogether = (str.indexOf(words[j], index)
								+words[j].length())==str.indexOf(words[j+1], 
										(str.indexOf(words[j], index)));
						boolean isModify = !(isNN(pos[j])&&words[j+1].equals("的"));
						if(isModify&&isTogether&&isPart(pos[j+1],words[j+1])){						
							if(pos[j].equals("数词")&&(words[j].equals("一")||
									words[j].equals("半")||words[j].equals("1")))
								isSing = "YES";
							else if (pos[j].equals("数词")&&!(words[j].equals("一")
									||words[j].equals("半")||words[j].equals("1"))){
								isSing = "NO";
							}
							strdata+= words[j+1];
							strtag = pos[j+1];
							j++;		
							flag++;
						}
						else
							break;
					}
					if(strtag.contains(PN)||strdata.contains("这")||
							strdata.contains("那")||strdata.contains("该")){
						ey.setIsResolution(true);
					}
					else
						ey.setIsResolution(false); 
					int jj = j;
					while((!isNN(strtag))&&jj>=0){
						int ij = strdata.indexOf(words[jj]);
						if(ij>=0)
							strdata = strdata.substring(0,ij);
						else
							break;
						jj--;
						flag--;
						if(jj>=0)
							strtag = pos[jj];
					}
					if(strdata.length() == 0)
						continue;
					if(strdata.indexOf("的")==0){
						strdata = strdata.substring(1);
						ey.setStart(ey.getStart()+1);
					}

					ey.setPosTag(strtag);
					ey.setData(strdata);
					if(isSingular(ey.getData())){
						isSing = "YES";
					}
					else if(isNotSingular(ey.getData())){
						isSing = "NO";
					}
					ey.setSex("UNKONW");
					if(this.isFemale(ey.getData())){
						ey.setSex("Female");
					}
					else if(this.isMale(ey.getData())){
						ey.setSex("Male");
					}
					graTag = "SUB";
					while((j-flag-1)>=0&&!pos[j-flag-1].equals(PU)){
						if(isObj(pos[j-flag-1])){
							graTag = "OBJ";
							break;
						}
						graTag = "SUB";
						flag++;
					}
					int iii = 1;

					if(j<words.length-1&&pos[j+1].equals("DEG")&&
							words[j+1].equals("的")){
						graTag = "ADJ";
					}
					ey.setId(id);
					ey.setGraTag(graTag);
					ey.setSingleOrNot(isSing);
					ey.setDistance(distance);		
					ey.setSubDistance(subDistance);
					ey.setEnd(ey.getStart()+ey.getData().length());
					this.EntityList.add(ey);
				}

			}
		}
	}
	private final static String nn = "名词|代词|人名|地名|机构名|专有名";
	private final static Pattern isNN= Pattern.compile(nn);
	public boolean isNN(String strtag) {
		return isNN.matcher(strtag).find();
	}
	
	String obj = "副词|动词|介词|形谓词";
	Pattern isobj = Pattern.compile(obj);
	
	private boolean isObj(String string) {
		return isobj.matcher(string).find();
	}
	private Boolean isSingular(String str){
		if(str.contains("这个")||str.contains("这种")||
				str.contains("每")||str.equals("他")||
				str.equals("它")||str.equals("她")){
			return true;
		}
		else
			return false;

	}
	private Boolean isNotSingular(String str){ 
		if(str.startsWith("各")||str.contains("群")||
				str.contains("多")||str.startsWith("二者")||
				str.startsWith("全体")||str.startsWith("所有")
				||str.contains("们")){
			return true;
		}
		else
			return false;

	}
	private Boolean isFemale(String str){
		if(str.contains("娘")||str.contains("妻")||
				str.contains("媳")||str.contains("姑")||
				str.contains("夫人")||str.contains("她")||
				str.contains("小姐")||str.contains("女")||
				str.contains("母")||str.contains("妞")||
				str.contains("妈")||str.contains("妇")||
				str.contains("婆")){
			return true;
		}
		else
			return false;

	}

	private Boolean isMale(String str){
		if(str.contains("先生")||str.contains("男")||
				str.contains("丈夫")||str.contains("父")||
				str.contains("兄")||str.contains("儿子")
				||str.contains("哥")){
			return true;
		}
		else
			return false;

	}
	public LinkedList<Entity>  getEntiyList(String str) throws Exception{
		EntityList =new LinkedList<Entity>();

		String[][][] stringTag = tag.tag2DoubleArray(str);
		this.doIt(stringTag,str);		
		return this.EntityList;
	}
	public LinkedList<Entity>  getEntiyList(String[][][] stringTag,String str){
		EntityList =new LinkedList<Entity>();
		this.doIt(stringTag,str);		
		return this.EntityList;
	}

	public static void main(String args[]) throws Exception{
		EntitiesGetter ep = new EntitiesGetter("./models/seg.m","./models/pos.m");
		Entity ey = null;
		LinkedList<Entity> list = ep.getEntiyList("尽管美韩媒体提出种种猜测，" +
				"不过韩联社17日称，美国太平洋司令部司令塞缪尔·洛克利尔承认，" +
				"尚无法确认该导弹是真品还是仿制品，也难以评价。他同时强调，" +
				"若朝鲜进行第三次核试验，美军可能对朝鲜核试验基地进行精确打击。");
		Iterator it = list.iterator();
		while (it.hasNext()){
			ey = (Entity)it.next();
			System.out.print(ey.getData()+"\t"+ey.getIsResolution()+"\t"+ey.getGraTag()+"\n");
		}
	}


}
