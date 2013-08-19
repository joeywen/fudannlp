CHCP 936
@echo 分词实例
java -classpath fudannlp.jar;lib/commons-cli-1.2.jar;lib/trove.jar; edu.fudan.nlp.cn.tag.CWSTagger -s models/seg.m "自然语言是人类交流和思维的主要工具，是人类智慧的结晶。"
@echo 词性标注实例
java -classpath fudannlp.jar;lib/commons-cli-1.2.jar;lib/trove.jar; edu.fudan.nlp.cn.tag.POSTagger -s models/seg.m models/pos.m "周杰伦出生于台湾，生日为79年1月18日，他曾经的绯闻女友是蔡依林。"
@echo 实体名识别实例
java -classpath fudannlp.jar;lib/commons-cli-1.2.jar;lib/trove.jar; edu.fudan.nlp.cn.tag.NERTagger -s models/seg.m models/pos.m "詹姆斯·默多克和丽贝卡·布鲁克斯 鲁珀特·默多克旗下的美国小报《纽约邮报》的职员被公司律师告知，保存任何也许与电话窃听及贿赂有关的文件。 "

@pause>nul