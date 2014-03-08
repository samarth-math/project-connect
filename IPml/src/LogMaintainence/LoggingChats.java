package LogMaintainence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class LoggingChats
{
	
	@SuppressWarnings("unchecked")
	public void logCreate(String log)
	{
		
		File path = new File(System.getProperty("user.dir"));
		File jsonFilePath = new File(path,""+log+".json");
		String fileName = ""+log+".json";
		
		//System.out.println(path);                                    //check file path
		
		if(jsonFilePath.exists())
		{
			try 
			{
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(new FileReader(fileName));
				JSONObject logInfo = (JSONObject)obj;
				long newSessionValue = (long)logInfo.get("lastUpdatedSession");
				System.out.println(newSessionValue);                    //print check
				newSessionValue++;
				System.out.println(newSessionValue);                    //print check
				logInfo.put("lastUpdatedSession", newSessionValue);  
				
				//JSONObject mainLog = new JSONObject();
				
				JSONObject sessNum = new JSONObject();
				JSONObject messageTuple = new JSONObject();
				JSONArray messageArray = new JSONArray();
				JSONObject messageObject = new JSONObject();
				JSONArray session = new JSONArray();
				JSONArray chat = new JSONArray();
				
				sessNum.put("sessionNumber", newSessionValue);
					
				messageTuple.put("timeStamp", ""+newSessionValue);
				messageTuple.put("userName", "shasuck"+newSessionValue);
				messageTuple.put("messageText", "No he doesnt");
				messageArray.add(messageTuple);
					
				messageObject.put("messages", messageArray);
				chat.add(sessNum);
				chat.add(messageObject);	
					
				session.add(chat);
				
				logInfo.put("session", session);
				
				System.out.println(logInfo.get("lastUpdatedSession"));   // print check
				jsonFilePath.createNewFile();
				FileWriter jsonFileWriter = new FileWriter(jsonFilePath);				
				jsonFileWriter.write(logInfo.toJSONString());
				jsonFileWriter.flush();
				jsonFileWriter.close();
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
		
		
		else
		{
			try 
			{
				jsonFilePath.createNewFile();
				FileWriter jsonFileWriter = new FileWriter(jsonFilePath);
				
				JSONObject emainLog = new JSONObject();
				emainLog.put("totalUsers", 4);
				//mainLog.put("lastUpdatedSession", 0);
				emainLog.put("groupId", "xxa56d");
				emainLog.put("groupName", "project x");
				
				JSONObject esessNum = new JSONObject();
				JSONObject emessageTuple = new JSONObject();
				JSONArray emessageArray = new JSONArray();
				JSONObject emessageObject = new JSONObject();
				
				//JSONArray eusers = new JSONArray();
				JSONArray esession = new JSONArray();
				
				JSONArray egroupUsers = new JSONArray();
				egroupUsers.add("mathur");
				egroupUsers.add("rajat");
				egroupUsers.add("shasuck");
				egroupUsers.add("baid");
				emainLog.put("users", egroupUsers);
				
				
				JSONArray echat = new JSONArray();
				esessNum.put("sessionNumber", 404);
					
					
				emessageTuple.put("timeStamp", ""+404);
				emessageTuple.put("userName", "shasuck"+404);
				emessageTuple.put("messageText", "No he doesnt");
				emessageArray.add(emessageTuple);
					
					
				emessageObject.put("messages", emessageArray);
				echat.add(esessNum);
				echat.add(emessageObject);	
					
				esession.add(echat);
				
				emainLog.put("session", esession);
				emainLog.put("lastUpdatedSession", 0);
				
				jsonFileWriter.write(emainLog.toJSONString());
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
