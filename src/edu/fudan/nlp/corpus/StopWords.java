package edu.fudan.nlp.corpus;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.lang.String;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 本类主要功能是过滤停用词
 * @author ltian
 *
 */

public class StopWords {

	TreeSet<String> sWord = new TreeSet<String>();
	String dicPath;
	HashMap<String, Long> lastModTime = new HashMap<String, Long>();

	public StopWords(){	
	}
	public StopWords(String dicPath1,boolean b){
		this.dicPath = dicPath1;
		// 定期监视文件改动
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				read(dicPath);
			}


		}, new Date(System.currentTimeMillis() + 10000), 24*60*60*1000);
	}
	/**
	 * 构造函数
	 * @param dicPath
	 *        stopword所在地址
	 */

	public StopWords(String dicPath) {		
		this.dicPath = dicPath;
		read(dicPath);		
	}

	/**
	 * 读取stopword
	 * @param dicPath
	 *       stopword所在地址
	 * @throws FileNotFoundException
	 */

	public void read(String dicPath) {

		File path = new File(dicPath);
		if(path.isDirectory()){
			String[] subdir = path.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if(name.toLowerCase().endsWith("txt"))
						return true;
					else
						return false;
				}
			});
			for(int i=0;i<subdir.length;i++){
				read(path+"/"+subdir[i]);
			}
			return;
		}
		Long newTime = path.lastModified();
		Long lastTime = lastModTime.get(dicPath);
		if(lastTime ==null || !lastTime.equals(newTime)){
			//路径是文件
			try{
				InputStreamReader read = new InputStreamReader(new FileInputStream(path), "UTF-8");
				BufferedReader in = new BufferedReader(read);
				String s;
				while ((s = in.readLine()) != null){ 
					s = s.trim();
					if (!s.matches("^$"))
						sWord.add(s);
				}
				in.close();
			}catch (Exception e) {
				System.err.println("停用词文件路径错误");
			}
		}
	}

	/**
	 * 删除stopword
	 * 将string字符串转换为List类型，并返回
	 * @param words
	 *       要进行处理的字符串 
	 * @return
	 *       删除stopword后的List类型
	 */

	public List<String> phraseDel(String[] words){
		List<String> list = new ArrayList<String>(); 
		String s;
		int length= words.length;
		for(int i = 0; i < length; i++){
			s = words[i];
			if(!isStopWord(s))
				list.add(s);
		}
		return list;
	}

	Pattern noise = Pattern.compile(".*["+CharSets.allRegexPunc+"\\d]+.*");
	
	public boolean isStopWord(String word) {
		if (word.length() == 1 || word.length()>4)
			return true;

		if (noise.matcher(word).matches())
			return true;

		if (sWord.contains(word))
			return true;

		return false;
	}
}
