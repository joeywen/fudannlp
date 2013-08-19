package edu.fudan.nlp.parser.dep;

import java.util.ArrayList;
import java.util.List;

import edu.fudan.ml.types.alphabet.AlphabetFactory;
import edu.fudan.ml.types.alphabet.IFeatureAlphabet;
import edu.fudan.ml.types.sv.HashSparseVector;
import edu.fudan.ml.types.sv.ISparseVector;
import edu.fudan.nlp.parser.Sentence;
import edu.fudan.nlp.parser.dep.ParsingState.Action;
import gnu.trove.list.array.TIntArrayList;

/**
 * 句法分析过程中的状态，及在此状态上的一系列操作
 * 
 * 句法分析由状态的转换完成，转换操作涉及在当前状态提取特征，动作执行。 动作的预测在Parser 中完成
 * 
 * @author xpqiu
 */
public class JointParsingState{

	
	private static final String END = "E*";
	private static final String START = "S*";
	
	private static final String CH_L_LEX = "/LL/";
	private static final String CH_R_LEX = "/RL/";
	private static final String NULL = "N*";

	private static final String CH_R_POS = "/RP/";
	
	private static final String CH_L_POS = "/LP/";
	
	private static final String LEX = "/L/";
	private static final String POS = "/P/";
	private int ysize = 0;

	/**
	 * 动作类型
	 * @author xpqiu
	 *
	 */
	public enum Action {
		SHIFT, LEFT, RIGHT
	}

	protected Sentence sent;
	protected List<DependencyTree> trees;
	protected int leftFocus;

	// 非SHIFT动作中概率较大的动作的概率
	protected float[] probsOfBuild;

	// 非SHIFT动作中概率较大的动作
	protected Action[] actionsOfBuild;

	// 是否执行过非SHIFT动作
	protected boolean isUpdated = false;

	protected boolean isFinal = false;

	private String[] depClassOfBuild;

	/**
	 * 构造函数
	 * 
	 * 由句子实例初始化状态
	 * 
	 * @param instance
	 *            句子实例
	 * @param factory2 
	 */

	public JointParsingState(Sentence instance) {
		trees = new ArrayList<DependencyTree>();
		for (int i = 0; i < instance.length(); i++) {
			String word = instance.getWordAt(i);
			String pos = instance.getTagAt(i);
			DependencyTree tree = new DependencyTree(i, word, pos);
			trees.add(tree);
		}
		this.sent = instance;
		if(trees.size()==0)
			return;

		probsOfBuild = new float[trees.size() - 1];
		actionsOfBuild = new Action[trees.size() - 1];
		depClassOfBuild = new String[trees.size()-1];
	}
	
	

	/**
	 * 得到当前状态的特征
	 * 
	 * @return 特征表
	 * @throws Exception
	 */
	public ArrayList<String> getFeatures() {
		if (isFinalState())
			return null;
		ArrayList<String> featurelist = new ArrayList<String>();

		int rightFocus = leftFocus + 1;

//		ISparseVector vec = new HashSparseVector();
		
		StringBuilder posFeature1 = new StringBuilder();
		posFeature1.append("+-2").append(POS).append(trees.get(leftFocus).pos)
		.append("/").append(trees.get(rightFocus).pos);
		featurelist.add(posFeature1.toString());
		
		StringBuilder lexFeature1 = new StringBuilder();
		lexFeature1.append("+-2").append(LEX).append(trees.get(leftFocus).word)
		.append("/").append(trees.get(rightFocus).word);
		featurelist.add(lexFeature1.toString());

		// 设定上下文窗口大小
		int l = 2;
		int r = 2;
		for (int i = 0; i <= l; i++) {
			// 特征前缀
			String posFeature = "-" + String.valueOf(i) + POS;
			String lexFeature = "-" + String.valueOf(i) + LEX;

			String lcLexFeature = "-" + String.valueOf(i)
					+ CH_L_LEX;
			String lcPosFeature = "-" + String.valueOf(i)
					+ CH_L_POS;
			String rcLexFeature = "-" + String.valueOf(i)
					+ CH_R_LEX;
			String rcPosFeature = "-" + String.valueOf(i)
					+ CH_R_POS;

			if (leftFocus - i < 0) {
				featurelist.add(lexFeature + START + String.valueOf(i - leftFocus));
				featurelist.add(posFeature + START + String.valueOf(i - leftFocus));
			} else {
				featurelist.add(lexFeature + sent.words[trees.get(leftFocus - i).id]);
				featurelist.add(posFeature + sent.tags[trees.get(leftFocus - i).id]);

				if (trees.get(leftFocus - i).leftChilds.size() != 0) {
					for (int j = 0; j < trees.get(leftFocus - i).leftChilds
							.size(); j++) {
						int leftChildIndex = trees.get(leftFocus - i).leftChilds
								.get(j).id;
						featurelist.add(lcLexFeature
								+ sent.words[leftChildIndex]);
						featurelist.add(lcPosFeature
								+ sent.tags[leftChildIndex]);
					}
				}else{
					featurelist.add(lcLexFeature + NULL);
					featurelist.add(lcPosFeature + NULL);
				}

				if (trees.get(leftFocus - i).rightChilds.size() != 0) {
					for (int j = 0; j < trees.get(leftFocus - i).rightChilds
							.size(); j++) {
						int rightChildIndex = trees.get(leftFocus - i).rightChilds
								.get(j).id;
						featurelist.add(rcLexFeature
								+ sent.words[rightChildIndex]);
						featurelist.add(rcPosFeature
								+ sent.tags[rightChildIndex]);
					}
				}else{
					featurelist.add(rcLexFeature + NULL);
					featurelist.add(rcPosFeature + NULL);
				}
			}
		}

		for (int i = 0; i <= r; i++) {
			String posFeature = "+" + String.valueOf(i) + POS;
			String lexFeature = "+" + String.valueOf(i) + LEX;

			String lcLexFeature = "+" + String.valueOf(i)
					+ CH_L_LEX;
			String lcPosFeature = "+" + String.valueOf(i)
					+ CH_L_POS;
			String rcLexFeature = "+" + String.valueOf(i)
					+ CH_R_LEX;
			String rcPosFeature = "+" + String.valueOf(i)
					+ CH_R_POS;

			if (rightFocus + i >= trees.size()) {
				featurelist.add(lexFeature+ END+ String.valueOf(rightFocus + i- trees.size() + 3));
				featurelist.add(posFeature+ END+ String.valueOf(rightFocus + i- trees.size() + 3));
			} else {
				featurelist.add(lexFeature+ sent.words[trees.get(rightFocus + i).id]);
				featurelist.add(posFeature+ sent.tags[trees.get(rightFocus + i).id]);

				if (trees.get(rightFocus + i).leftChilds.size() != 0) {
					for (int j = 0; j < trees.get(rightFocus + i).leftChilds
							.size(); j++) {
						int leftChildIndex = trees.get(rightFocus + i).leftChilds
								.get(j).id;
						featurelist.add(lcLexFeature+ sent.words[leftChildIndex]);
						featurelist.add(lcPosFeature+ sent.tags[leftChildIndex]);
					}
				}else{
					featurelist.add(lcLexFeature + NULL);
					featurelist.add(lcPosFeature + NULL);
				}

				if (trees.get(rightFocus + i).rightChilds.size() != 0) {
					for (int j = 0; j < trees.get(rightFocus + i).rightChilds
							.size(); j++) {
						int rightChildIndex = trees.get(rightFocus + i).rightChilds
								.get(j).id;
						featurelist.add(rcLexFeature+ sent.words[rightChildIndex]);
						featurelist.add(rcPosFeature+ sent.tags[rightChildIndex]);
					}
				}else{
					featurelist.add(rcLexFeature + NULL);
					featurelist.add(rcPosFeature + NULL);
				}
			}
		}
		
		
		return featurelist;
	}

	public boolean isFinalState() {
		return trees.size()==0||trees.size() == 1 || isFinal;
	}

	/**
	 * 状态转换，动作为SHIFT
	 * 
	 * 动作为SHIFT，但保存第二大可能的动作，当一列动作都是SHIFT时，执行概率最大的第二大动作
	 * 
	 * @param action
	 *            第二大可能的动作
	 * @param prob
	 *            第二大可能的动作的概率
	 */
	public void next(Action action, float prob,String depClass) {
		probsOfBuild[leftFocus] = prob;
		actionsOfBuild[leftFocus] = action;
		depClassOfBuild[leftFocus] = depClass;
		leftFocus++;

		if (leftFocus >= trees.size() - 1) {
			if (!isUpdated) {
				int maxIndex = 0;
				float maxValue = Float.NEGATIVE_INFINITY;
				for (int i = 0; i < probsOfBuild.length; i++)
					if (probsOfBuild[i] > maxValue) {
						maxValue = probsOfBuild[i];
						maxIndex = i;
					}
				leftFocus = maxIndex;
				next(actionsOfBuild[leftFocus],depClassOfBuild[leftFocus]);
			}

			back();
		}
	}

	/**
	 * 状态转换, 执行动作
	 * 
	 * @param action
	 *            要执行的动作
	 */
	public void next(Action action,String depClass) {

		assert (!isFinalState());

		// 左焦点词在句子中的位置
		int lNode = trees.get(leftFocus).id;
		int rNode = trees.get(leftFocus + 1).id;

		switch (action) {
		case LEFT:
			trees.get(leftFocus + 1).setDepClass(depClass);  			
			trees.get(leftFocus).addRightChild(trees.get(leftFocus + 1));
			trees.remove(leftFocus + 1);
			isUpdated = true;

			break;
		case RIGHT:
			trees.get(leftFocus).setDepClass(depClass);			
			trees.get(leftFocus + 1).addLeftChild(trees.get(leftFocus));
			trees.remove(leftFocus);
			isUpdated = true;
			break;
		default:
			leftFocus++;
		}

		if (leftFocus >= trees.size() - 1) {
			if (!isUpdated) {
				isFinal = true;
			}
			back();
		}
	}
	public int[] getFocusIndices() {
		assert (!isFinalState());

		int[] indices = new int[2];
		indices[0] = trees.get(leftFocus).id;
		indices[1] = trees.get(leftFocus + 1).id;
		return indices;
	}
	/**
	 * 将序列第一二个词设为焦点词
	 */
	protected void back() {
		isUpdated = false;
		leftFocus = 0;

		probsOfBuild = new float[trees.size() - 1];
		actionsOfBuild = new Action[trees.size() - 1];
		depClassOfBuild = new String[trees.size() - 1];
	}

}