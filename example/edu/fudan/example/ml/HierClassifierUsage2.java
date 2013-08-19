package edu.fudan.example.ml;


import java.io.File;

import edu.fudan.data.reader.SimpleFileReader;
import edu.fudan.ml.classifier.hier.Linear;
import edu.fudan.ml.classifier.hier.PATrainer;
import edu.fudan.ml.classifier.hier.inf.MultiLinearMax;
import edu.fudan.ml.classifier.linear.inf.Inferencer;
import edu.fudan.ml.feature.BaseGenerator;
import edu.fudan.ml.loss.ZeroOneLoss;
import edu.fudan.ml.types.InstanceSet;
import edu.fudan.ml.types.alphabet.AlphabetFactory;
import edu.fudan.ml.types.alphabet.IFeatureAlphabet;
import edu.fudan.ml.types.alphabet.LabelAlphabet;
import edu.fudan.nlp.pipe.Pipe;
import edu.fudan.nlp.pipe.SeriesPipes;
import edu.fudan.nlp.pipe.StringArray2SV;
import edu.fudan.nlp.pipe.Target2Label;

/**
 * 层次分类器使用示例
 * 
 * @author xpqiu
 * 
 */
public class HierClassifierUsage2 {
	static InstanceSet train;
	static InstanceSet test;
	static AlphabetFactory factory = AlphabetFactory.buildFactory();
	static LabelAlphabet al = factory.DefaultLabelAlphabet();
	static IFeatureAlphabet af = factory.DefaultFeatureAlphabet();
	static String path = null;

	public static void main(String[] args) throws Exception {

		
		long start = System.currentTimeMillis();

		path = "./example-data/data-classification.txt";

		Pipe lpipe = new Target2Label(al);
		Pipe fpipe = new StringArray2SV(factory, true);
		//构造转换器组
		Pipe pipe = new SeriesPipes(new Pipe[]{lpipe,fpipe});
		
		//构建训练集
		train = new InstanceSet(pipe, factory);
		SimpleFileReader reader = new SimpleFileReader (path,true);
		train.loadThruStagePipes(reader);
		al.setStopIncrement(true);
		
		//构建测试集
		test = new InstanceSet(pipe, factory);		
		reader = new SimpleFileReader (path,true);
		test.loadThruStagePipes(reader);	

		System.out.println("Train Number: " + train.size());
		System.out.println("Test Number: " + test.size());
		System.out.println("Class Number: " + al.size());

		float c = 1.0f;
		int round = 20;
		
		BaseGenerator featureGen = new BaseGenerator();
		ZeroOneLoss loss = new ZeroOneLoss();
		Inferencer msolver = new MultiLinearMax(featureGen, al, null,2);

		PATrainer trainer = new PATrainer(msolver, featureGen, loss, round,c, null);
		Linear pclassifier = trainer.train(train, null);
		
		String modelFile = path+".m.gz";
		pclassifier.saveTo(modelFile);

		long end = System.currentTimeMillis();
		System.out.println("Total Time: " + (end - start));
		System.out.println("End!");
		(new File(modelFile)).deleteOnExit();
		System.exit(0);
	}
}
