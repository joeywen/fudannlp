package edu.fudan.ml.classifier.linear;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import edu.fudan.ml.types.Instance;
import edu.fudan.nlp.pipe.SeriesPipes;

public class ClassifierPool {

	private ExecutorService pool;
	private int numThread;
	private Linear classifier;
	ArrayList<Future> f ;
	private SeriesPipes pp;

	public ClassifierPool(int numThread2){
		numThread = numThread2;
		pool = Executors.newFixedThreadPool(numThread);
		f= new ArrayList<Future>();
	}

	public void classify(String c) throws Exception{
		Instance inst = new Instance(c);

		ClassifyTask t = new ClassifyTask(inst);
		f.add(pool.submit(t));		
	}

	public String getRes(int i) throws Exception {
		// TODO Auto-generated method stub
		return (String) f.get(i).get();
	}

	class ClassifyTask implements Callable {

		private Instance inst;
		public  ClassifyTask(Instance inst) {
			this.inst = inst;	
		}

		public String call() {

			try {
				pp.addThruPipe(inst);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String type = classifier.getStringLabel(inst);
			return type;

		}
	}

	public  void loadFrom(String modelfile) throws Exception {

		classifier= Linear.loadFrom(modelfile);
		pp = (SeriesPipes) classifier.getPipe();

	}

	public void reset() {
		pool.shutdownNow();
		pool=Executors.newFixedThreadPool(numThread);
		f= new ArrayList<Future>();
	}

}
