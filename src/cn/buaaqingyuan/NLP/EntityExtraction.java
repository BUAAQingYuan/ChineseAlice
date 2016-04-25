package cn.buaaqingyuan.NLP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import cn.buaaqingyuan.Util.FileUtil;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.suggest.Suggester;
import com.hankcs.hanlp.summary.TextRankKeyword;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

//中文实体抽取
public class EntityExtraction {
	
	    //机构的词性标注集,排名有先后
		private static List<String>  orgNature=new ArrayList<String>(Arrays.asList(new String[]{"ntu","nt","j","n"}));
		
		//机构后缀名
		private static List<String>  orgSuffixs=new ArrayList<String>();
		
		//机构前缀词性
		private static List<String>  orgPrefixs=new ArrayList<String>(Arrays.asList(new String[]{"ns","nsf","nt","ntc"}));
		
		
		//读取机构后缀
		static{
			orgSuffixs=FileUtil.readOrganizationSuffix("library\\中文机构后缀名.dic");
		}

	    //从短句中抽取机构实体
		//使用ansj分词效果还可以
		public static String   ExtractOrgFromPhrase(String input)
		{
			
			List<Term>  terms=ToAnalysis.parse(input);
			
			System.out.println(terms);
			//挑选单词
			int pos=100;
			String word="";
			for(Term one:terms)
			{
				if(orgNature.contains(one.getNatureStr()))
				{
					int current=orgNature.indexOf(one.getNatureStr());
					if(current<pos)
					{
						pos=current;
						word=one.getRealName();
					}
				}
			}
			
			return word;
		}
		
		//抽取机构实体
		/*
		 * 算法:
		 * 
		 */
		public static String   ExtractOrgFromPhrase2(String input)
		{
			String orgname="";
			List<Term>  terms=ToAnalysis.parse(input);
			
			System.out.println(terms);
			
			int end=terms.size()-1,start=0;
			for(int i=0;i<terms.size();i++)
			{
				Term current=terms.get(i);
				if(orgPrefixs.contains(current.getNatureStr()))
				{
					start=i;
				}else if(orgSuffixs.contains(current.getRealName())){
					end=i;
				}
				
			}
			
			
			for(int j=start;j<=end;j++)
			{
				orgname=orgname+terms.get(j).getRealName();
			}
			
			return orgname;
		}
		
		//抽取人名
		public static List<String>  ExtractPersonFromPhrase(String input)
		{
			List<String> persons=new ArrayList<String>();
			
			Segment segment = HanLP.newSegment().enableNameRecognize(true);
			List<com.hankcs.hanlp.seg.common.Term> termList = segment.seg(input);
			
			for(com.hankcs.hanlp.seg.common.Term one:termList)
			{
				if(one.nature.toString().equals("nr"))
				{
					persons.add(one.word);
				}
			}
			return persons;
		}
		
		public static void  Phrase()
		{
			String text="目前国内从事算法研究的工程师不少，但是高级算法工程师却很少，是一个非常紧缺的专业工程师。算法工程师根据研究领域来分主要有音频/视频算法处理、图像技术方面的二维信息算法处理和通信物理层、雷达信号处理、生物医学信号处理等领域的一维信息算法处理。";
			List<String> phraseList = HanLP.extractPhrase(text, 10);
			System.out.println(phraseList);
			
			String a="苹果";
			String b="香蕉";
			System.out.println(CoreSynonymDictionary.distance(a, b));
		}
		
		public static void suggest()
		{
			 Suggester suggester = new Suggester();
		        String[] titleArray =
		        (
		                "威廉王子发表演说 呼吁保护野生动物\n" +
		                "《时代》年度人物最终入围名单出炉 普京马云入选\n" +
		                "“黑格比”横扫菲：菲吸取“海燕”经验及早疏散\n" +
		                "日本保密法将正式生效 日媒指其损害国民知情权\n" +
		                "英报告说空气污染带来“公共健康危机”"
		        ).split("\\n");
		        for (String title : titleArray)
		        {
		            suggester.addSentence(title);
		        }

		        System.out.println(suggester.suggest("发言", 1));       // 语义
		        System.out.println(suggester.suggest("危机公共", 1));   // 字符
		        System.out.println(suggester.suggest("mayun", 1));      // 拼音
		}
		
		public static void summary()
		{
			String document = "算法可大致分为基本算法、数据结构的算法、数论算法、计算几何的算法、图的算法、动态规划以及数值分析、加密算法、排序算法、检索算法、随机化算法、并行算法、厄米变形模型、随机森林算法。\n" +
			        "算法可以宽泛的分为三类，\n" +
			        "一，有限的确定性算法，这类算法在有限的一段时间内终止。他们可能要花很长时间来执行指定的任务，但仍将在一定的时间内终止。这类算法得出的结果常取决于输入值。\n" +
			        "二，有限的非确定算法，这类算法在有限的时间内终止。然而，对于一个（或一些）给定的数值，算法的结果并不是唯一的或确定的。\n" +
			        "三，无限的算法，是那些由于没有定义终止定义条件，或定义的条件无法由输入的数据满足而不终止运行的算法。通常，无限算法的产生是由于未能确定的定义终止条件。";
			List<String> sentenceList = HanLP.extractSummary(document, 3);
			System.out.println(sentenceList);
		}
		
		public static void main(String[] args)
		{
			
			
			String[] testCase = new String[]{
			        "我不在北大维信生物科技有限公司工作", 
			        "上海石油化工股份有限公司在哪里",
			        "谁在北京军区总医院工作过",
			        "绍兴市人民医院有多少人",
			        "今年中兴通讯公司总收入多少",
			        "你在常熟理工学院和北京大学做什么",
			        "你在常熟理工学院做什么",
			        "艾国祥院士"
			        };
			for(String one:testCase)
			{
				System.out.println(EntityExtraction.ExtractOrgFromPhrase2(one));
			}
			
			
			
			String[] testCase2 = new String[]{
			        "签约仪式前，秦光荣、李纪恒、仇和等一同会见了参加签约的企业家。",
			        "王国强、高峰、汪洋、张朝阳光着头、韩寒、小四",
			        "张浩和胡健康复员回家了",
			        "王总和小丽结婚了",
			        "编剧邵钧林和稽道青说",
			        "这里有关天培的有关事迹",
			        "龚学平等领导,邓颖超生前",
			        };
			
			for (String sentence : testCase2)
			{
			    List<String> termList =EntityExtraction.ExtractPersonFromPhrase(sentence);
			    System.out.println(termList);
			}
			
			
			String input="可以计算某个关键字在某篇文章里面的重要性，因而识别这篇文章的主要含义，实现计算机读懂文章的功能。";
			Map<String,Float> result2=new TextRankKeyword().getTermAndRank(input,5);
			for (Map.Entry<String, Float> entry : result2.entrySet())
			{
				System.out.println(entry.getKey()+" "+entry.getValue());
			}
			
			
			List<String> keywordList = HanLP.extractKeyword(input, 5);
			System.out.println(keywordList);
		}
		
}
