package cn.buaaqingyuan.Chat.Questions;

import java.util.Map;

import bitoflife.chatterbean.AliceBot;
import cn.buaaqingyuan.DataSorce.DbApproach;
import cn.buaaqingyuan.NLP.EntityExtraction;

public class Organization {
	
	//问题处理
	public static String  process(AliceBot bot,String input,Map<String,String> paras)
	{
		String answer="";
		
		if(paras.get("targetfield").equals("place")||paras.get("targetfield").equals("description")||paras.get("targetfield").equals("superfields"))
		 {
			 String org=EntityExtraction.ExtractOrgFromPhrase2(paras.get("value").replaceAll(" ", ""));
			 System.out.println(org);
			 
			 String info=DbApproach.queryEntityProperty(paras.get("lib"),paras.get("targetfield"),paras.get("queryfield"),org);
			 if(info!=null&&!info.equals(""))
				 answer=bot.respond(input)+info;
			 else
				 answer=bot.respond("信息为空");
		 }
		
		return answer;
		
	}
}
