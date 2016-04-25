package cn.buaaqingyuan.Analyzer;

import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

public class AnsjAnalyzer implements Analyzer{
	
	@Override
	public String analyse(String input) {
		StringBuilder output=new StringBuilder();;
		List<Term> terms = ToAnalysis.parse(input);
		for(Term one:terms)
		{
			output.append(one.getRealName()+" ");
		}
		return output.toString();
	}
	
	public static void main(String[] args) {
		
		String str="优势学科";
		
		System.out.println(ToAnalysis.parse(str).toString());
		
		System.out.println(new AnsjAnalyzer().analyse(str));
	}

}
