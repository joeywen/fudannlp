package edu.fudan.nlp.cn.anaphora;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

import edu.fudan.ml.types.Instance;
/**
 * 将指代对样本集合转换成代词和代词候选集的集合
 * @author jszhao
 * @version 1.0
 * @since FudanNLP 1.5
 */
public class FormChanger {
	private LinkedList<StringBuffer> llsb;
	private LinkedList<Instance> llst;
	private TreeSet<Entity> ts;
	public FormChanger(){
		llsb = new LinkedList<StringBuffer>();
		llst = new LinkedList<Instance>();
	}
	
	public void groupToList(LinkedList<Instance> ll){
		StringBuffer sb = null;
		EntityGroup eg1 = null;
		EntityGroup eg2 = null;

		
		Instance in1 = null;
		Instance in2 = null;
		Iterator<Instance> it = null;
		if(ll.size()==0)
			ts = new TreeSet<Entity>();
		while(ll.size()>0){
			in1 = ll.poll();
			ts=new TreeSet<Entity>();
			sb = new StringBuffer();
			sb.append("<  ");
			eg1= (EntityGroup) in1.getSource();
			ts.add(eg1.getAhead());
			ts.add(eg1.getBehind());
			do{
				if(llst.size()>0){
					in1 = llst.poll();
					eg1= (EntityGroup) in1.getSource();
				}
				it = ll.iterator();
				while(it.hasNext()){					
					in2 = (Instance) it.next();
					eg2 = (EntityGroup) in2.getSource();
					if(eg1.getAhead().getStart() == eg2.getAhead().getStart()||eg1.getBehind().getStart() == eg2.getAhead().getStart()){
						ts.add(eg2.getBehind());
						llst.add(in2);
						it.remove();
					}
					else if(eg1.getAhead().getStart() == eg2.getBehind().getStart()||eg1.getBehind().getStart() == eg2.getBehind().getStart()){
						ts.add(eg2.getAhead());
						llst.add(in2);
						it.remove();
					}		
				}
			}
			while(llst.size()>0);
			Iterator<Entity> it3 = ts.iterator();
			while(it3.hasNext()){
				Entity et1 = it3.next();
				sb.append(et1.getData()+"("+et1.getStart()+")"+"  ");
			}
			sb.append(">");
			llsb.add(sb);
			
		}
			
	}
	public TreeSet<Entity> getTs(){
		return this.ts;
	}
	public LinkedList<StringBuffer> getLlsb(){
		return this.llsb;
	}

}
