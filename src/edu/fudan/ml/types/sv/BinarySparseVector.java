package edu.fudan.ml.types.sv;

import gnu.trove.iterator.TIntFloatIterator;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.array.TIntArrayList;

public class BinarySparseVector implements ISparseVector {


	private static final long serialVersionUID = 3666569734894722449L;
	TIntArrayList data;

	public BinarySparseVector(){
		data = new TIntArrayList();
	}

	public BinarySparseVector(int len) {
		data = new TIntArrayList(len);
	}

	@Override
	public float dotProduct(float[] vector) {
		TIntIterator it = data.iterator();
		float sum = 0f;
		while(it.hasNext()){
			int i = it.next();
			sum += vector[i];
		}
		return sum;
	}

	@Override
	public void put(int i) {
		data.add(i);

	}
	@Override
	public void put(int[] idx) {
		for(int i=0;i<idx.length;i++){
			if(idx[i]!=-1)
			data.add(idx[i]);
		}
		
	}
	

	@Override
	public float dotProduct(HashSparseVector sv) {
		float v =0f;
		TIntIterator it = data.iterator();			
		while(it.hasNext()){
			int i = it.next();
			v += sv.get(i);
		}
		return v;
	}

	private int size() {
		return data.size();
	}
	/**
	 * 
	 */
	public float l2Norm2() {
		return	data.size();
	}
	

}
