package cn.buaaqingyuan.Util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.ibatis.session.SqlSession;

import cn.buaaqingyuan.DataSorce.DbApproach;

public class FileUtil {

	public static List<String> readOrganizationSuffix(String path) 
	{
		List<String> suffixs=new ArrayList<String>();

		try {
			FileReader fr = new FileReader(path);
			BufferedReader br=new BufferedReader(fr);
		    String line="";
		    while ((line=br.readLine())!=null) {
		            suffixs.add(line);
		            //System.out.println(line);
		     }
		     br.close();
		     fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		return suffixs;
	}
	
	
	//得到数据库中的机构后缀
	public static void  getsuffixs() throws IOException
	{
		List<String> suffixs=readOrganizationSuffix("library\\中文机构后缀名.dic");
		
		SqlSession session=DbApproach.getSession();
		String state="SqlMapper.OrganizationMapper.getAllOrgName";
		List<String> names=session.selectList(state);
		
		Set<String> set=new HashSet<String>();
		
		for(String one:names)
		{
			List<Term>  terms=ToAnalysis.parse(one);
			if(terms.size()>1)
			{
				if(!suffixs.contains(terms.get(terms.size()-1).getRealName()))
				{
					//System.out.println(terms.get(terms.size()-1).getRealName());
					set.add(terms.get(terms.size()-1).getRealName());
				}
			}
		}
		
		for (String str : set) {  
		      System.out.println(str);  
		}  
		
	}
	
	
	public static void main(String[] args) throws IOException{
		//FileUtil.readOrganizationSuffix("library\\中文机构后缀名.dic");
		FileUtil.getsuffixs();
	}
}
