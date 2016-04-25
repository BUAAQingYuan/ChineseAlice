package cn.buaaqingyuan.Chat.Questions;

import java.util.Map;

import cn.buaaqingyuan.DataSorce.DbApproach;
import cn.buaaqingyuan.NLP.EntityExtraction;
import bitoflife.chatterbean.AliceBot;

public class People {
	
	//问题处理
	public static String  process(AliceBot bot,String input,Map<String,String> paras)
	{
		String answer="";
		
		String info=DbApproach.queryEntityProperty(paras.get("lib"),paras.get("targetfield"),paras.get("queryfield"),paras.get("value").replaceAll(" ", ""));
		
		if(info!=null&&!info.equals(""))
			 answer=bot.respond(input)+info;
		else
			 answer=bot.respond("信息为空");
		
		return answer;
	}
	
	
}
