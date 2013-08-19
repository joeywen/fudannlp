package edu.fudan.ml.types.featurecluster;

import java.util.HashMap;

public abstract class AbstractCluster {
    public abstract void process();
    public abstract HashMap<Integer, Integer> getMap();
}
