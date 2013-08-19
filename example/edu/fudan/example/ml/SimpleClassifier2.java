package edu.fudan.example.ml;


import java.io.File;

import edu.fudan.data.reader.SimpleFileReader;
import edu.fudan.ml.classifier.linear.Linear;
import edu.fudan.ml.classifier.linear.OnlineTrainer;
import edu.fudan.ml.classifier.linear.inf.Inferencer;
import edu.fudan.ml.classifier.linear.inf.LinearMax;
import edu.fudan.ml.classifier.linear.update.LinearMaxPAUpdate;
import edu.fudan.ml.feature.Generator;
import edu.fudan.ml.feature.SFGenerator;
import edu.fudan.ml.loss.ZeroOneLoss;
import edu.fudan.ml.types.InstanceSet;
import edu.fudan.ml.types.alphabet.AlphabetFactory;
import edu.fudan.ml.types.alphabet.IFeatureAlphabet;
import edu.fudan.ml.types.alphabet.LabelAlphabet;
import edu.fudan.nlp.pipe.StringArray2IndexArray;
import edu.fudan.nlp.pipe.Pipe;
import edu.fudan.nlp.pipe.SeriesPipes;
import edu.fudan.nlp.pipe.Target2Label;

/**
 * 线性分类器使用示例
 * 
 * @author xpqiu
 * 
 */
public class SimpleClassifier2 {
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
		Pipe fpipe = new StringArray2IndexArray(factory, true);
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
		
		Generator featureGen = new SFGenerator();
		ZeroOneLoss loss = new ZeroOneLoss();
		LinearMaxPAUpdate update = new LinearMaxPAUpdate(loss);
		
		
		Inferencer msolver = new LinearMax(featureGen, al.size() );
		OnlineTrainer trainer = new OnlineTrainer(msolver, update, loss, af.size(), round,
				c);

		Linear classify = trainer.train(train, test);
		String modelFile = path+".m.gz";
		classify.saveTo(modelFile);

		long end = System.currentTimeMillis();
		System.out.println("Total Time: " + (end - start));
		System.out.println("End!");
		(new File(modelFile)).deleteOnExit();
		System.exit(0);
	}
}
