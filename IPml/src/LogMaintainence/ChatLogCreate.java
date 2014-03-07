package LogMaintainence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class ChatLogCreate 
{
	static File path = new File(System.getProperty("user.dir"));
	static File jsonFilePath = new File(path,"log.json");
	//static String jsonFilePath = "log.json";
	final static int currentSessionNumber = 0;
	@SuppressWarnings("unchecked")
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		int sessionNumber = 432;
		JSONObject mainLog = new JSONObject();
		mainLog.put("totalUsers", 4);
		mainLog.put("currentSession", currentSessionNumber);
		mainLog.put("groupId", "xxa56d");
		mainLog.put("groupName", "project x");
		
		JSONObject sessNum = new JSONObject();
		JSONObject messageTuple = new JSONObject();
		JSONArray messageArray = new JSONArray();
		JSONObject messageObject = new JSONObject();
		JSONArray chat = new JSONArray();
		JSONArray users = new JSONArray();
		JSONArray session = new JSONArray();
		
		JSONObject groupUsers = new JSONObject();
		groupUsers.put("user1", "mathur");
		groupUsers.put("user2", "rajat");
		groupUsers.put("user3", "shasuck");
		groupUsers.put("user4", "baid");
		users.add(groupUsers);
		mainLog.put("users", users);
		
		sessNum.put("sessionNumber", sessionNumber);
		
		for(int k=0;k<3;k++)
		{
			messageTuple.put("timeStamp", ""+k+":"+k);
			messageTuple.put("userName", "shasuck");
			messageTuple.put("messageText", "No he doesnt");
		
			messageArray.add(messageTuple);
		}
		
		messageObject.put("message", messageArray);
		chat.add(sessNum);
		chat.add(messageObject);
		session.add(chat);
		mainLog.put("session", session);
		
		if(jsonFilePath.exists())
		{
			try 
			{
				FileWriter jsonFileWriter = new FileWriter(jsonFilePath);
				jsonFileWriter.write(mainLog.toJSONString());
				jsonFileWriter.flush();
				jsonFileWriter.close();
				System.out.print(mainLog);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
	}

}
