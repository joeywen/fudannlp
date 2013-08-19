package edu.fudan.nlp.pipe.templet;

import java.util.ArrayList;
import java.util.List;

import edu.fudan.ml.types.Instance;
import edu.fudan.ml.types.alphabet.AlphabetFactory;
import edu.fudan.ml.types.alphabet.IFeatureAlphabet;
import edu.fudan.ml.types.sv.BinarySparseVector;
import edu.fudan.nlp.pipe.Pipe;

public class TemplatePipe extends Pipe {

	private static final long serialVersionUID = -4863048529473614384L;
	private IFeatureAlphabet features;
	private ArrayList<RETemplate> group;
	public TemplatePipe(AlphabetFactory af,RETemplateGroup group){
		this.features = af.DefaultFeatureAlphabet();
		this.group = group.group;
	}
	@Override
	public void addThruPipe(Instance inst) throws Exception {
		String str = (String) inst.getSource();
		BinarySparseVector sv = (BinarySparseVector) inst.getData();
		List<RETemplate> templates = new ArrayList<RETemplate>();
		for(int i=0;i<group.size();i++){
			RETemplate qt = group.get(i);
			float w = qt.matches(str);
			if(w>0){
//				System.out.println(qt.comment);
				int id = features.lookupIndex("template:"+qt.comment);
				sv.put(id);
			}
		}
	}

}
