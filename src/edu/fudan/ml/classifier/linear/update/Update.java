package edu.fudan.ml.classifier.linear.update;

import edu.fudan.ml.types.Instance;

public interface Update {
	
	public float update(Instance inst, float[] weights, Object predictLabel,
			float c);

	public float update(Instance inst, float[] weights, Object predictLabel,
			Object goldenLabel, float c);

}
