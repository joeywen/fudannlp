package edu.fudan.nlp.corpus;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import edu.fudan.nlp.pipe.seq.String2Sequence;
import edu.fudan.util.MyStrings;

public class Unlabeled {
	public static void processUnLabeledData(String input,String output) throws Exception{
		FileInputStream is = new FileInputStream(input);
		//		is.skip(3); //skip BOM
		BufferedReader r = new BufferedReader(
				new InputStreamReader(is, "utf8"));
		OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(output), "utf8");
		while(true) {
			String sent = r.readLine();
			if(sent==null) break;
			String[][] ss = String2Sequence.genSequence(sent);
			String s = MyStrings.toString(ss, "\n");
			w.write(s);
		}
		w.close();
		r.close();
	}

}
