package edu.fudan.nlp.corpus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import edu.fudan.util.MyCollection;
import edu.fudan.util.MyFiles;
import gnu.trove.map.hash.TCharIntHashMap;

public class CorpusCount {
	TCharIntHashMap charfreq = new TCharIntHashMap();
	int charnum = 0;

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		CorpusCount wc = new CorpusCount();
		
		wc.countChar("D:/wordcluster/SogouCA","GBK");
		wc.toString();
	}

	private void countChar(String ifile, String enc) throws IOException {
		if((new File(ifile)).isDirectory()){
			List<File> filese = MyFiles.getAllFiles(ifile,null);
			for(File f:filese){
				countChar(f.toString(),enc);
			}
			return;
		}
				
		BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(ifile),enc));
		String line = null;	
		int count=0;
		while ((line = bfr.readLine()) != null) {
			if(line.length()==0)
				continue;
			if(!line.startsWith("<content"))
				continue;
			if(count%10000==0)
				System.out.println(count);
			count++;
			line.replace("<contenttitle>", "");
			line.replace("</contenttitle>", "");
			line.replace("<content>", "");
			line.replace("</content>", "");
			for(int i=0;i<line.length();i++){
				char c = line.charAt(i);
				charfreq.adjustOrPutValue(c, 1, 1);
				charnum++;	
			}
		}
		bfr.close();
		
	}
	public String toString(){
		String s = "";
		s += "char type number:\t" + charfreq.size()+"\n";
		s += "char number:\t" + charnum+"\n";
		return s;
		
	}

}
