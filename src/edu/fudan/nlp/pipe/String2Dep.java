package edu.fudan.nlp.pipe;

import edu.fudan.ml.types.Instance;
import edu.fudan.nlp.cn.CNFactory;
import edu.fudan.nlp.cn.tag.CWSTagger;
import edu.fudan.nlp.cn.tag.POSTagger;
import edu.fudan.nlp.parser.dep.DependencyTree;
import edu.fudan.nlp.parser.dep.JointParser;
import edu.fudan.util.exception.UnsupportedDataTypeException;
/**
 * 将字符串转换为依次句法结构
 * @author xpqiu
 *
 */
public class String2Dep extends Pipe{

	private static final long serialVersionUID = -3646974372853044208L;
	private static CNFactory factory;

	public String2Dep(){
		this.factory = CNFactory.getInstance();
	}
	
	public String2Dep(CNFactory factory){
		this.factory = factory;
	}

	@Override
	public void addThruPipe(Instance inst) throws Exception {
		String data = (String)inst.getData();
//		System.out.println(data);
		DependencyTree t = factory.parse2T(data);
		

		inst.setData(t);
	}




}
