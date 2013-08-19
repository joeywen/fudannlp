package edu.fudan.nlp.cn.anaphora;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

import edu.fudan.data.reader.AR_Reader;
import edu.fudan.ml.classifier.linear.Linear;
import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.InstanceSet;
import edu.fudan.nlp.cn.tag.POSTagger;
/**
 * 指代消解的程序接口
 * @author jszhao
 * @version 1.0
 * @since FudanNLP 1.5
 */
public class Anaphora {

	private LinkedList<EntityGroup> arGroup;
	private Linear cl;
	private TreeSet<Entity> ts;
	private FormChanger fc;
	private LinkedList<Instance> llis;
	private InstanceSet test;
	
	public Anaphora(String segmodel, String posmodel, String armodel) throws Exception{
		EntitiesGetter.initTagger(segmodel,posmodel);		
		cl = Linear.loadFrom(armodel);
	}
	
	public Anaphora(POSTagger tag,String armodel) throws Exception{
			EntitiesGetter.initTagger(tag);
			cl = Linear.loadFrom(armodel);
	}
	/**
	 * 用了标注模型，得到指代对集合
	 * @param str
	 * @return 指代对集合
	 * @throws Exception
	 */
	public LinkedList<EntityGroup> getArGroup(String str) throws Exception{
		this.init(str);
		this.doIt();
		return this.arGroup;
	}
	/**
	 * 不用标注模型，得到指代对集合
	 * @param str
	 * @return 指代对集合
	 * @throws Exception
	 */
	public LinkedList<EntityGroup> getArGroup(String[][][] stringTag,String str) throws Exception{
		this.init2(stringTag,str);
		this.doIt();
		return this.arGroup;
	}
	private void init(String str) throws Exception{
		arGroup = new LinkedList<EntityGroup>();
		ts = new TreeSet<Entity>();
		llis = new LinkedList<Instance>();
		fc = new FormChanger();
		
		
		test = new InstanceSet(cl.getPipe());
		test.loadThruPipes(new AR_Reader(str));
		for(int i=0;i<test.size();i++){
			String ss = cl.getStringLabel(test.getInstance(i));
			if(ss.equals("1")){
				llis.add(test.getInstance(i));
			}	
		}
		fc.groupToList(llis);
		fc.getLlsb();
		ts = fc.getTs();
	}
	private void init2(String[][][]stringTag,String str) throws Exception{
		arGroup = new LinkedList<EntityGroup>();
		ts = new TreeSet<Entity>();
		llis = new LinkedList<Instance>();
		fc = new FormChanger();
		test = new InstanceSet(cl.getPipe());
		test.loadThruPipes(new AR_Reader(stringTag,str));
		for(int i=0;i<test.size();i++){
			String ss = cl.getStringLabel(test.getInstance(i));
			if(ss.equals("1")){
				llis.add(test.getInstance(i));
			}	
		}
		fc.groupToList(llis);
		fc.getLlsb();
		ts = fc.getTs();
	}
	private void doIt(){
		LinkedList<Entity> ll =null;
		int flag = 0;Entity re =null;Entity re1 =null;
		int i = this.ts.size();int j =0;
		WeightGetter wp = null;
		EntityGroup reg =null;
		EntityGroup reg1 =null;
		while(flag!=i-j){
			flag =0;
			ll = new LinkedList<Entity>();
			Iterator<Entity> it = this.ts.iterator();
			while(it.hasNext()){
				flag++;
				re = it.next();
				if(!re.getIsResolution()){
					ll.add(re);
				}
				else{
					j++;
					it.remove();
					break;
				}					
			}
			if(flag==i-j&&!re.getIsResolution())
				break;
			it = ll.iterator();
			int ii = -1000;
			while(it.hasNext()){
				re1 = it.next();			
				reg = new EntityGroup(re1,re);
				wp = new WeightGetter(reg);
				if(wp.getWeight()>=ii){
					ii = wp.getWeight();
					reg1 =reg;
					reg1.setWeight(ii);
				}
			}
			if(reg1!=null&&reg1.getWeight()<-100){
				continue;
			}
			this.arGroup.add(reg1);
		}
	}

	/**
	 * 用标注工具的最后结果
	 * @param str 句子
	 * @throws Exception
	 */
	public String resultToString(String str) throws Exception{
		EntityGroup reg =null;
		StringBuffer strBuf = new StringBuffer();
		getArGroup(str) ;
		Iterator it = arGroup.iterator();
		
		while(it.hasNext()){
			reg = (EntityGroup) it.next();
			strBuf.append(reg.getAhead().getData()+"("+reg.getAhead().getStart()+")"+"<--"+reg.getBehind().getData()+"("+reg.getBehind().getStart()+")"+"\n");
		}	
		return strBuf.toString();
	}
	/**
	 * 不用标注工具的最后结果
	 * @param stringTag 词和词性数组
	 * @param str 句子
	 * @throws Exception
	 */
	public String resultToString(String[][][] stringTag,String str) throws Exception{
		EntityGroup reg =null;
		StringBuffer strBuf = new StringBuffer();
		getArGroup(stringTag,str);
		Iterator it = arGroup.iterator();
		
		while(it.hasNext()){
			reg = (EntityGroup) it.next();
			strBuf.append(reg.getAhead().getData()+"("+reg.getAhead().getStart()+")"+"<--"+reg.getBehind().getData()+"("+reg.getBehind().getStart()+")"+"\n");
		}	
		return strBuf.toString();
	}
}
