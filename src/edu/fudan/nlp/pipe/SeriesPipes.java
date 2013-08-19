package edu.fudan.nlp.pipe;

import java.io.Serializable;
import java.util.ArrayList;

import edu.fudan.ml.types.Instance;

/**
 * Pipe组合，按先后顺序进行数据类型转换
 * @author xpqiu
 *
 */
public class SeriesPipes extends Pipe  implements Serializable {


	private static final long serialVersionUID = -9080917611618077919L;
	private ArrayList<Pipe> pipes = null;

	public int size(){
		return pipes.size();
	}

	public SeriesPipes(Pipe[] pipes)	{
		this.pipes = new ArrayList<Pipe>(pipes.length);
		for(int i = 0; i < pipes.length; i++){
			if(pipes[i]!=null)
				this.pipes.add(pipes[i]);
		}
	}

	public ArrayList<Pipe> getPipes()	{
		return pipes;
	}

	@Override
	public void addThruPipe(Instance carrier) throws Exception {
		for(int i = 0; i < pipes.size(); i++)
			pipes.get(i).addThruPipe(carrier);
	}


	public Pipe getPipe(int id)	{
		if (id < 0 | id > pipes.size())
			return null;
		return pipes.get(id);
	}

	/**
	 * 删除使用类标签的Pipe
	 */
	public void removeTargetPipe() {
		for(int i = pipes.size()-1; i >=0 ; i--){
			Pipe p = pipes.get(i);
			if(p instanceof SeriesPipes) {
				((SeriesPipes) p).removeTargetPipe();				
			}
			else if(p.useTarget){
				pipes.remove(i);
				i--;
			}
		}

	}
	
	public void addPipe(Pipe pipe)	{
		if (pipes == null)
			pipes = new ArrayList<Pipe>();
		pipes.add(pipe);
	}
}
