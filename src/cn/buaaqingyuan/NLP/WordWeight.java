package cn.buaaqingyuan.NLP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ansj.app.keyword.Keyword;
import org.ansj.domain.Nature;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.recognition.AsianPersonRecognition;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.FilterModifWord;
import org.nlpcn.commons.lang.tire.domain.Forest;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

public class WordWeight {
	
	private static final Map<String, Double> POS_SCORE = new HashMap<String, Double>();
	static {
		POS_SCORE.put("null", 0.0);
		POS_SCORE.put("w", 0.0);
		POS_SCORE.put("en", 0.0);
		POS_SCORE.put("m", 0.0);
		POS_SCORE.put("num", 0.0);
		POS_SCORE.put("ns", 3.0);
		POS_SCORE.put("nr", 3.0);
		POS_SCORE.put("nrf", 3.0);
		POS_SCORE.put("nw", 3.0);
		POS_SCORE.put("nt", 3.0);
		POS_SCORE.put("ntu", 3.0);
		POS_SCORE.put("nis", 1.0);
		POS_SCORE.put("l", 0.2);
		POS_SCORE.put("a", 0.2);
		POS_SCORE.put("nz", 3.0);
		POS_SCORE.put("v", 0.2);
		POS_SCORE.put("kw", 6.0); //关键词词性
	}
	
	/*
	 * 词性权重计算
	 */
	private double getWeight(Term term,int length,int titlelength)
	{
		//term长度不能小于2
		if (term.getName().trim().length() < 2) {
			return 0;
		}
		
		String pos = term.natrue().natureStr;
		Double posScore = POS_SCORE.get(pos);
		
		System.out.println(term.getName()+": "+posScore);
		if (posScore == null) {
			posScore = 1.0;
		} else if (posScore == 0) {
			return 0;
		}
		
		if (titlelength > term.getOffe()) {
			return 5 * posScore;
		}
		
		return (length - term.getOffe()) * posScore / (double) length;
	}
	
	
	//计算得分
	private double  getScore(double weight,double docFreq)
	{
		return  Math.sqrt(Math.log(10000 + 10000.0 / (docFreq + 1))*weight);
		
	}
	
	//计算句子的各个成分权重
	public  List<Keyword> computeKeywordAndWeight(String input)
	{
		List<Keyword> keys=new ArrayList<Keyword>();
		
		
		Map<String, Keyword> tm = new HashMap<String, Keyword>();
		
		/*
		List<Term> tokens=HanLP.newSegment().enableAllNamedEntityRecognize(true)
											.enableCustomDictionary(true)
											.enableOrganizationRecognize(true)
											.seg(input);
		*/									
		List<Term> tokens=ToAnalysis.parse(input);
		
		System.out.println(tokens);
		
		for(Term one:tokens)
		{
			double weight = getWeight(one,input.length(),0);
			if (weight == 0)
				continue;
			
			Keyword keyword = tm.get(one.getName());
			
			if (keyword == null) {
				keyword = new Keyword(one.getName(),getScore(weight,one.natrue().allFrequency));		
				tm.put(one.getName(), keyword);
			} else {
				keyword.updateWeight(1);
			}
		}
		
		for(Map.Entry<String,Keyword> entry : tm.entrySet())
		{
			keys.add(entry.getValue());
		}
		
		return keys;
	}
	
	public static void main(String[] args) {
		
		WordWeight ww=new WordWeight();
		String input="北京大学是一所著名的大学";
		System.out.println(ww.computeKeywordAndWeight(input));
		
		String input2="北京大学名列前茅";
		System.out.println(ww.computeKeywordAndWeight(input2));
		
	}
	
}
