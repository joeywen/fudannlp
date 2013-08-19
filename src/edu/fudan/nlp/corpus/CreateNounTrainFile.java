package edu.fudan.nlp.corpus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CreateNounTrainFile {
	//ctb_v6/processed/postagged.train -> ctb_v6/processed/nountagged.train
	//目前postagged.train和nountagged.train还有训练noun的template在example-data/ctb_v6下
	
	public static void main(String[] args) throws IOException {
		FileInputStream in = new FileInputStream("ctb_v6/processed/postagged.train");
		FileOutputStream out = new FileOutputStream("ctb_v6/processed/nountagged.train");
		Scanner scanner = new Scanner(in);
		PrintWriter pw = new PrintWriter(out);
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			if(line.trim().equals("")) {
				pw.println();
				continue;
			}
			
			String[] sa = line.split("\t");
			//System.out.println(sa[0] + " " + sa[1]);
			if(sa[1].indexOf("-N") != -1) {
				if(sa[1].indexOf("B-N") != -1)
					pw.println(sa[0] + "\t" + "B");
				else if(sa[1].indexOf("S-N") != -1)
					pw.println(sa[0] + "\t" + "S");
				else if(sa[1].indexOf("M-N") != -1)
					pw.println(sa[0] + "\t" + "M");
				else if(sa[1].indexOf("E-N") != -1)
					pw.println(sa[0] + "\t" + "E");
				else {
					System.out.println("error!");
					System.exit(1);
				}
			}
			else
				pw.println(sa[0] + "\t" + "O");
		}
		
		scanner.close();
		in.close();
		pw.close();
		out.close();
	}
}
