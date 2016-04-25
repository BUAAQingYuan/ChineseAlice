package cn.buaaqingyuan.NLP;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.ansj.app.keyword.KeyWordComputer;
import org.ansj.app.keyword.Keyword;



//相似度计算
public class Similarity {
	
	//从句子中获得关键词和评分
	public static Map<String,Double> getKeywordsFromSentence(String input) {
		
		Map<String,Double> keywords=new LinkedHashMap<String,Double>();
		
		KeyWordComputer kwc = new KeyWordComputer(20);
		List<Keyword> result=kwc.computeArticleTfidf(input);
		//List<Keyword> result=new WordWeight().computeKeywordAndWeight(input);
		System.out.println(result);
		for(Keyword one:result)
		{
			keywords.put(one.getName(),one.getScore());
		}
		
		return keywords;
	}
	
	private static Vector<String>  getKeywordString(Map<String,Double> keywords)
	{
		Vector<String> words = new Vector<String>();
		
		for (Map.Entry<String, Double> entry : keywords.entrySet())
        {
            words.add(entry.getKey());
        }
		
		return words;
	}
	
	private static Vector<Double>  getKeywordScore(Map<String,Double> keywords)
	{
		Vector<Double> scores = new Vector<Double>();
		
		for (Map.Entry<String, Double> entry : keywords.entrySet())
        {
            scores.add(entry.getValue());
        }
		
		return scores;
	}
	
	
	/*
	 * 计算句子的语义相似度
	 * 参考:http://www.open-open.com/lib/view/open1421978002609.html
	 */
	
	private static double getSimilarity(Vector<String> T1, Vector<String> T2,Vector<Double> T1score,Vector<Double> T2score) throws Exception {
		
		double YUZHI = 1.0;
		int size = 0 , size2 = 0 ;
	    if ( T1 != null && ( size = T1.size() ) > 0 && T2 != null && ( size2 = T2.size() ) > 0 ) {
	        
	    	Map<String, double[]> T = new HashMap<String, double[]>();
	        
	        //T1和T2的并集T
	    	String index = null ;
	        for ( int i = 0 ; i < size ; i++ ) {
	        	index = T1.get(i) ;
	            if( index != null){
	            	double[] c = T.get(index);
	                c = new double[2];
	                c[0] = T1score.get(i);	//T1的语义分数Ci,为分词的权重
	                c[1] = YUZHI;//T2的语义分数Ci，第一次检测到设置为阈值
	                T.put(index, c );
	            }
	        }
	 
	        for ( int i = 0; i < size2 ; i++ ) {
	        	index = T2.get(i) ;
	        	if( index != null ){
	        		double[] c = T.get(index);
	        		if( c != null && c.length == 2 ){
	        			//如果T中已经存在index的评分
	        			c[1] = T2score.get(i); //T2中也存在，T2的语义分数为分词的权重
	                }else {
	                    c = new double[2];
	                    c[0] = YUZHI; //T1的语义分数Ci
	                    c[1] = T2score.get(i); //T2的语义分数Ci
	                    T.put(index ,c);
	                }
	            }
	        }
	            
	        //开始计算，百分比
	        Iterator<String> it = T.keySet().iterator();
	        double s1 = 0 , s2 = 0, Ssum = 0;  //S1、S2
	        while( it.hasNext() ){
	        	double[] c = T.get( it.next() );
	        	Ssum += c[0]*c[1];
	        	s1 += c[0]*c[0];
	        	s2 += c[1]*c[1];
	        }
	        //百分比
	        return Ssum / Math.sqrt( s1*s2 );
	    } else {
	        throw new Exception("传入参数有问题！");
	    }
	}
	

	public static double  getSentenceSimilarity(String input1,String input2) throws Exception
	{
		Map<String,Double> words1=getKeywordsFromSentence(input1);
		Map<String,Double> words2=getKeywordsFromSentence(input2);
		
		System.out.println("vec1: "+getKeywordScore(words1));
		System.out.println("vec2: "+getKeywordScore(words2));
		
		double score=getSimilarity(getKeywordString(words1),getKeywordString(words2),getKeywordScore(words1),getKeywordScore(words2));
		return score;
	}
	
	
	
	public static void main(String[] args) throws Exception
	{
		/*
		KeyWordComputer kwc = new KeyWordComputer(20);
		String title = "中国的北京大学";
		String title2="美丽的北京大学";
		String content = "中新网北京3月10日电 10日上午，中国海上搜救中心组织召开马航失联客机海上搜救紧急会商会议，中国交通运输部副部长、中国海上搜救中心主任何建中对当前搜救工作做出部署：一要加强与马来西亚等多方搜救组织的沟通协调；二要根据搜救现场情况进一步完善搜救方案；三要加强信息交流共享，做好内外联动。 \n \n马航客机失联事件发生后，交通运输部启动一级应急响应，3月8日、9日4次召开马航失联客机应急反应领导小组工作会议，研判形势，部署搜寻工作。根据《国家海上搜救和重大海上溢油应急处置紧急会商工作制度》，交通运输部、国家海洋局、中国海警局、总参、海军等共同研究制定了中国船舶及航空器赴马航客机失联海域搜救方案，初步明确了“海巡31”、“南海救101”、“南海救115”、中国海警3411、海军528和999舰等6艘中国搜救船舶的海上搜救区域。 \n \n截至3月10日8时，中国海军528舰和中国海警3411舰已在相关区域开展搜救工作，预计交通运输部所属“南海救115”、“海巡31”轮、“南海救101”将先后于10日16时、11时17时、11日22时抵达马航客机疑似失联海域。中国海上搜救中心已将有关情况通报马来西亚海上搜救机构，并将与马来西亚、越南海上搜救机构保持密切联系，开展深度配合。同时，继续协调中国商船参与搜救。(完)(周音)";
		//Collection<Keyword> result = kwc.computeArticleTfidf(title, content);
		List<Keyword> result=kwc.computeArticleTfidf(title);
		List<Keyword> result2=kwc.computeArticleTfidf(title2);

		System.out.println(result);
		System.out.println(result2);
		*/
		
		String title = "中国的北京大学";
		String title2="北京大学";
		System.out.println(Similarity.getSentenceSimilarity(title, title2));
	}
	
}
