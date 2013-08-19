package edu.fudan.data.reader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.fudan.ml.classifier.linear.Linear;
import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.InstanceSet;
import edu.fudan.nlp.cn.anaphora.Entity;
import edu.fudan.nlp.cn.anaphora.EntityGroup;
import edu.fudan.nlp.cn.anaphora.EntitiesGetter;
import edu.fudan.nlp.cn.anaphora.FeatureGeter;
/**
 * 用于指代消解的读入
 * @author jszhao
 * @version 1.0
 * @since FudanNLP 1.5
 */
public class AR_Reader extends Reader{
	private String data;
	private LinkedList<Instance> list;
	private Iterator it;
	private LinkedList<Entity> ll;
	private EntitiesGetter elp;
	public AR_Reader (String data) throws Exception
	{
		this.data = data;
		elp= new EntitiesGetter();
		ll = elp.getEntiyList(data);
		this.dothis();
		it = list.iterator();
	}
	public AR_Reader (String[][][] stringTag,String data) throws Exception
	{
		this.data = data;
		elp= new EntitiesGetter();
		ll = elp.getEntiyList(stringTag,data);
		this.dothis();
		it = list.iterator();
	}
	private void dothis() throws Exception{		
		list = new LinkedList<Instance>();
		Entity ss = null;Entity s2 =null;
		EntityGroup eg = null;
		FeatureGeter fp = null;
		Instance in = null;
		Iterator it =null;
		List<String> newdata = null;
		while(ll.size()>0){	
			ss=(Entity)ll.poll();					
			it= ll.iterator();				
			while(it.hasNext()){
				s2 = (Entity)it.next();				
				eg = new EntityGroup(ss,s2);
				fp = new FeatureGeter(eg);
				String[] tokens = this.intArrayToString(fp.getFeatrue()).split("\\t+|\\s+");
				newdata= Arrays.asList(tokens);
				in = new Instance(newdata,null);
				in.setSource(eg);
				list.add(in);								
			}	
		}
	}
	private String intArrayToString(int[] ia){
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i<ia.length;i++){
			sb.append(ia[i]);
			sb.append(" ");
		}
		return sb.toString();
	}
	public Instance next ()
	{
		return (Instance) it.next();
	}

	public boolean hasNext ()	{	
		return it.hasNext();	
	}
	public static void main(String args[]) throws Exception{
		Linear cl=null;;
		cl = Linear.loadFrom("./models/ar_model.gz");
		InstanceSet test = new InstanceSet(cl.getPipe());
		test.loadThruPipes(new AR_Reader("随着中国经济融入世界经济进程的加快，和以高科技为主体的经济发展，众多跨国公司在中国不 在是单纯的建立生产基地，而是越来越多的将研发中心转移到了中国。目前已经有包括：微软、 摩托罗拉和贝尔实验室在内的几十家规模较大的跨国公司，将其研发中心在中国落户。 "));
		for(int i=0;i<test.size();i++){
			String ss = cl.getStringLabel(test.getInstance(i));
			if(ss.equals("1"))
				System.out.print(ss+"\n");
		}
		System.gc();
	}
}
