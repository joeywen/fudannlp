package edu.fudan.nlp.parser.dep;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import edu.fudan.nlp.corpus.fnlp.FNLPSent;

public class TreeCache {

	public static LinkedList<TreeCacheSent> sents;

	public TreeCache(){
		sents = new LinkedList<TreeCacheSent>();
	}

	public void read(String file) throws IOException {
		BufferedReader bfr =null;
		try {

			FileInputStream in = new FileInputStream(file);
			bfr = new BufferedReader(new InputStreamReader(in,"utf-8"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = null;
		ArrayList<String> carrier = new ArrayList<String>();
		while ((line = bfr.readLine()) != null) {
			line = line.trim();
			if (line.matches("^$")){
				if(carrier.size()>0){
					TreeCacheSent sent = new TreeCacheSent();						
					sent.parse(carrier,0,true); //TODO: 需要根据不同语料修改

					sents.add(sent);
					carrier.clear();
				}
			}else
				carrier.add(line);
		}
		if(!carrier.isEmpty()){
			TreeCacheSent sent = new TreeCacheSent();				
			sent.parse(carrier,0,true); //TODO: 需要根据不同语料修改
			sents.add(sent);
			carrier.clear();
		}
	}

	public DependencyTree get(String[] words, String[] tags){
		for(int k=0;k<sents.size();k++){
			TreeCacheSent sent = sents.get(k);
			boolean match = true;
			if(sent.words.length!=words.length)
				continue;
			for(int i=0;i<words.length;i++){
				if((sent.words[i]!=null)&&(Arrays.binarySearch(sent.words[i],words[i])<0)){
					match = false;
					break;
				}
				if((sent.tags[i]!=null)&&(Arrays.binarySearch(sent.tags[i],tags[i])<0)){
					match = false;
					break;
				}				

			}
			if(match){
				FNLPSent newsent = new FNLPSent(words, tags, sent.heads, sent.relations);
				return newsent.toTree();
			}
		}
		return null;
	}


	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		TreeCache tc = new TreeCache();
		tc.read("./models/dict_dep.txt");		
		DependencyTree tree = tc.get(new String[]{"上海","的"}, new String[]{"地名","结构助词"});
		System.out.println(tree);
		
		tree = tc.get(new String[]{"上海","呢"}, new String[]{"地名","结构助词"});
		System.out.println(tree);
		tree = tc.get(new String[]{"上海"}, new String[]{"地名"});
		System.out.println(tree);

	}

}
