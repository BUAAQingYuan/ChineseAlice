package cn.buaaqingyuan.Chat.QuestionProcess;

import java.util.Map;

import bitoflife.chatterbean.AliceBot;
import cn.buaaqingyuan.Chat.Questions.Organization;
import cn.buaaqingyuan.Chat.Questions.People;
import cn.buaaqingyuan.DataSorce.DbApproach;
import cn.buaaqingyuan.NLP.EntityExtraction;

//获得结果
public class QuestionAnswer {

	//
	public static String  getAnswer(AliceBot bot,String input)
	{
		//将input解析为查询参数
		Map<String,String> paras=bot.getQuestionPara(input);
		
		String answer=null;
		
		if(paras!=null&&paras.size()>0)
		 {
			 //从数据源获取结果
			 //分类
			 String type=paras.get("type");
			 if(type.equals("organization"))
			 {
				 answer=Organization.process(bot, input, paras);
			 }else if(type.equals("people"))
			 {
				 answer=People.process(bot, input, paras);
			 }
			
		 }
		 else{
			 answer=bot.respond(input);
		 }
		
		return answer;
	}
	
	
	
	
	
}
