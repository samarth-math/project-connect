package LogMaintainence;

import java.io.*;
import java.util.Iterator;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class chatFileIndex {
	
	protected static String createGroupEntry(String groupMakerId) 
	{
		File path = new File(System.getProperty("user.dir"));
		File jsonFilePath = new File(path,""+"index.json");
		
		String gId = null;
		try
		{ 
			JSONParser parser = new JSONParser();                                     // parsing index
			Object obj = parser.parse(new FileReader("index.json"));                  //   file here
			JSONObject indexInfo = (JSONObject)obj;	
			
			JSONArray files = (JSONArray)indexInfo.get("files");
			Date date= new Date();
			String date1= ""+date.getTime();
			gId = groupMakerId+date1;
				
			files.add(""+gId+".json");
			
			indexInfo.put("files", files);
			
			jsonFilePath.createNewFile();
			FileWriter jsonFileWriter = new FileWriter(jsonFilePath);				
			jsonFileWriter.write(indexInfo.toJSONString());
			jsonFileWriter.flush();
			jsonFileWriter.close();
			
			//System.out.println(indexInfo);  //checking
			
		}
		catch (ParseException e) 
		{
			e.printStackTrace();
		}	
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		return gId;
	}
	
	protected static void displayAllGroupChatFiles() 
	{
		
		try
		{
			JSONParser parser = new JSONParser();                                     // parsing index
			Object obj = parser.parse(new FileReader("index.json"));                  //   file here
			JSONObject indexInfo = (JSONObject)obj;	
			
			JSONArray files = (JSONArray)indexInfo.get("files");
			
			System.out.println("Group-chat Files:");
			Iterator<JSONArray> fileNameIterator = files.iterator();
			while (fileNameIterator.hasNext()) 
			{        
					System.out.println(fileNameIterator.next());        
			}	
		}
		catch (ParseException e) 
		{
			e.printStackTrace();
		}	
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		
	}
			
	
	
}
 