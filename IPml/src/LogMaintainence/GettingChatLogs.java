package LogMaintainence;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GettingChatLogs extends Object{
	
	@SuppressWarnings("unchecked")
	public void writeLog(String userId)
	{
		String chatFileName = userId+".json";                  // file name based on userId
		
		JSONParser parser = new JSONParser();
		 
		try {
				Object obj = parser.parse(new FileReader(chatFileName));
				JSONObject logInfo = (JSONObject)obj;	
				
				      																	       //  printing out info
				//System.out.println("groupId : "+logInfo.get("groupId"));                       // from the chat file
				//System.out.println("groupName : "+logInfo.get("groupName"));                   //  being read
				Long sessionValue = (Long)logInfo.get("lastUpdatedSession");
				//System.out.println("totalUsers : "+logInfo.get("totalUsers"));
				//System.out.println("users : ");
				
				JSONObject oldSessionObject = (JSONObject)logInfo.get("session");
				JSONArray oldMessageArray = (JSONArray)oldSessionObject.get("1");
				
				Iterator<JSONArray> oldMessageIterator = oldMessageArray.iterator();
				while (oldMessageIterator.hasNext()) 
				{        
					System.out.println((oldMessageIterator.next())); 
				}	
			
			}
		
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}	
		
	}

}
