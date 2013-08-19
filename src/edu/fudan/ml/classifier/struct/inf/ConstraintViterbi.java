package edu.fudan.ml.classifier.struct.inf;

import edu.fudan.ml.types.Instance;
import edu.fudan.nlp.pipe.seq.templet.TempletGroup;

/**
 * 约束的一阶线性最优解码器，需要TokenNormalize Pipe配合使用
 * @author JiaYi Zhao, modified by Feng Ji
 *
 */

public class ConstraintViterbi extends LinearViterbi {
	private static final long serialVersionUID = -2587323918656008679L;
	
	private int newysize;

	public ConstraintViterbi(TempletGroup templets, int ysize) {
		super(templets, ysize);
		this.newysize = ysize;
	}

	/**
	 * 构造函数
	 * @param viterbi 一阶线性解码器
	 */
	public ConstraintViterbi(LinearViterbi viterbi) {
		this(viterbi.getTemplets(), viterbi.ysize);
		this.weights = viterbi.getWeights();
		
	}

	/**
	 * 构造函数
	 * @param viterbi 一阶线性解码器
	 */
	public ConstraintViterbi(LinearViterbi viterbi,int ysize) {
		this(viterbi.getTemplets(), viterbi.ysize);
		this.weights = viterbi.getWeights();
		this.newysize = ysize;
	}
	@Override
	public int ysize() {
		return newysize;
	}
	
	/**
	 * 构造约束网格，不经过的节点设置为NULL
	 */
	@Override
	protected Node[][] initialLattice(Instance carrier) {
		int[][] data = (int[][]) carrier.getData();

		int[][] dicData = (int[][]) carrier.getDicData();

		int length = carrier.length();

		Node[][] lattice = new Node[length][];

		for (int l = 0; l < length; l++) {
			lattice[l] = new Node[newysize];
			for (int c = 0; c < ysize; c++) {
				if (dicData[l][c] == 0) {
					lattice[l][c] = new Node(newysize);
					for (int i = 0; i < orders.length; i++) {
						if (data[l][i] == -1)
							continue;
						if (orders[i] == 0) {
							lattice[l][c].score += weights[data[l][i] + c];
						} else if (l > 0 && orders[i] == 1) {
							for (int p = 0; p < ysize; p++) {
								int offset = p * ysize + c;
								lattice[l][c].trans[p] += weights[data[l][i]
										+ offset];
							}
						}
					}
				}
			}
			for (int c = ysize; c < newysize; c++) {
				if (dicData[l][c] == 0) {
					lattice[l][c] = new Node(newysize);
				}
			}
		}

		return lattice;
	}

}
