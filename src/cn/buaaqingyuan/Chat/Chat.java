package cn.buaaqingyuan.Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import cn.buaaqingyuan.Chat.QuestionProcess.QuestionAnswer;
import cn.buaaqingyuan.DataSorce.DbApproach;
import cn.buaaqingyuan.NLP.EntityExtraction;
import bitoflife.chatterbean.AliceBot;

public class Chat 
{
	public static final String END = "bye";
	
	public static String input()
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("you say>");
		String input = "";
		try 
		{
			input = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;
	}
	
	public static void main(String[] args) throws Exception
	{
		 AliceBotMother mother = new AliceBotMother();		  
	     mother.setUp();
	     AliceBot bot = mother.newInstance();
	     System.err.println("Kejso Alice>" + bot.respond("欢迎"));
		 while(true)
		 {
			 String input = Chat.input();
			 if(Chat.END.equalsIgnoreCase(input))
				 break;
			 
			 //获取回答 
			 String answer=QuestionAnswer.getAnswer(bot, input);
			 
			 System.err.println("Kejso Alice>" + answer);
			 
		 }
		 
	}
}
