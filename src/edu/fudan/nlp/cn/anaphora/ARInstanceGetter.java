package edu.fudan.nlp.cn.anaphora;

import edu.fudan.ml.types.Instance;

/**
 * 获得指代消解的样本
 * @author jszhao
 * @version 1.0
 * @since FudanNLP 1.5
 */

public class ARInstanceGetter {

	private Instance instance;
	public ARInstanceGetter(FeatureGeter fBuilder){	
		this.instance = new Instance(fBuilder.getFeatrue(),
				fBuilder.getInst().getTarget());
		this.instance.setSource(fBuilder.getInst().getData());
	}
	
	public Instance getInstance(){
		return this.instance;
	}

	
}
