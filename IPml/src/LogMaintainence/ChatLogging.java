package LogMaintainence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class ChatLogging
{
	private int totalUsers;
	Date date= new Date();
	
	@SuppressWarnings("unchecked")
	public void logCreate(String userId, String userName, String userMessage)
	{
		
		File path = new File(System.getProperty("user.dir"));
		File jsonFilePath = new File(path,""+userId+".json");
		String fileName = userId+".json";
		
		//System.out.println(path);
		//check file path
		
		if(jsonFilePath.exists())
		{
			try 
			{
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(new FileReader(fileName));
				
				JSONObject mainObject = (JSONObject)obj;
				
				Long lastSessionValue = (Long)mainObject.get("lastUpdatedSession");
				JSONObject sessionObject = (JSONObject)mainObject.get("session");
				long lineCount = (long)mainObject.get("lineCount");
				lineCount++;
				
				boolean sessionchange = false;
				if(lineCount%5 == 0)
				{
					lastSessionValue++;
					sessionchange = true;
				}
				
				JSONObject messageObject = new JSONObject();
				messageObject.put("timeStamp", ""+date.getTime());
				messageObject.put("userName", userName);
				messageObject.put("messageText", userMessage);
				
				JSONArray chatArray;
				
				if(!sessionchange)
				{
					chatArray = (JSONArray) sessionObject.get(lastSessionValue.toString());
				}
				else
					 chatArray = new JSONArray();
				
				chatArray.add(messageObject);	
				sessionObject.put(lastSessionValue.toString(), chatArray);
				mainObject.put("lineCount", lineCount);
				mainObject.put("lastUpdatedSession", lastSessionValue); 

				
				//Write Code to write the main object to file
				jsonFilePath.createNewFile();
				FileWriter jsonFileWriter = new FileWriter(jsonFilePath);				
				jsonFileWriter.write(mainObject.toJSONString());
				jsonFileWriter.flush();
				jsonFileWriter.close();
				
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
		else
		{
			try 
			{
				jsonFilePath.createNewFile();
				FileWriter jsonFileWriter = new FileWriter(jsonFilePath);
				
				JSONObject mainObject = new JSONObject();
				mainObject.put("totalUsers", totalUsers);
				mainObject.put("lastUpdatedSession", 0);
				mainObject.put("groupId", userId);
				mainObject.put("groupName", userName);
				mainObject.put("lineCount", 1);
				
				JSONArray groupUsers = new JSONArray();
				groupUsers.add(userId);
				//groupUsers.add("rajat");
				//groupUsers.add("shasuck");
				//groupUsers.add("baid");
				mainObject.put("users", groupUsers);
				
				JSONObject messageObject = new JSONObject();
				messageObject.put("timeStamp", ""+date.getTime());
				messageObject.put("userName", userName);
				messageObject.put("messageText", userMessage);
				
				JSONArray chatArray = new JSONArray();
				chatArray.add(messageObject);
				
				JSONObject sessionObject = new JSONObject();
				sessionObject.put(1, chatArray);
				
				mainObject.put("session", sessionObject);
				mainObject.put("lastUpdatedSession", 1);
				
				jsonFileWriter.write(mainObject.toJSONString());
				jsonFileWriter.flush();
				jsonFileWriter.close();
				
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}

}
