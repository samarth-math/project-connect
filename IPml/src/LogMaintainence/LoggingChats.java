package LogMaintainence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class LoggingChats
{
	
	static int lastUsedSessionNumber;
	
	@SuppressWarnings("unchecked")
	public void logCreate(String log)
	{
		
		File path = new File(System.getProperty("user.dir"));
		File jsonFilePath = new File(path,""+log+".json");
		
		//System.out.println(path);      //check file path
		// TODO Auto-generated method stub
		//int sessionNumber = 432;
		
		JSONObject mainLog = new JSONObject();
		mainLog.put("totalUsers", 4);
		mainLog.put("currentSession", lastUsedSessionNumber);
		mainLog.put("groupId", "xxa56d");
		mainLog.put("groupName", "project x");
		
		JSONObject sessNum = new JSONObject();
		JSONObject messageTuple = new JSONObject();
		JSONArray messageArray = new JSONArray();
		JSONObject messageObject = new JSONObject();
		
		JSONArray users = new JSONArray();
		JSONArray session = new JSONArray();
		
		JSONArray groupUsers = new JSONArray();
		groupUsers.add("mathur");
		groupUsers.add("rajat");
		groupUsers.add("shasuck");
		groupUsers.add("baid");
		mainLog.put("users", groupUsers);
		
		for(int temp=1; temp<3; temp++)
		{
			JSONArray chat = new JSONArray();
			sessNum.put("sessionNumber", 404);
			
			//for(int k=0;k<2;k++)
			//{
				messageTuple.put("timeStamp", ""+404);
				messageTuple.put("userName", "shasuck"+404);
				messageTuple.put("messageText", "No he doesnt");
			
				messageArray.add(messageTuple);
			//}
			
			messageObject.put("messages", messageArray);
			chat.add(sessNum);
			chat.add(messageObject);	
			
			session.add(chat);
		}
		
		mainLog.put("session", session);
			
		if(jsonFilePath.exists())
		{
			try 
			{
				FileWriter jsonFileWriter = new FileWriter(jsonFilePath);
				jsonFileWriter.write(mainLog.toJSONString());
				jsonFileWriter.flush();
				jsonFileWriter.close();
				System.out.println(mainLog);
				System.out.println();
				JSONArray yo = (JSONArray)mainLog.get("session");
				System.out.println(yo);
				
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
				jsonFileWriter.write(mainLog.toJSONString());
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
