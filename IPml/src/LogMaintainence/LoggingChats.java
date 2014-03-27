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



public class LoggingChats
{
	Date date= new Date();
	
	@SuppressWarnings("unchecked")
	public void logCreate(String userId, String userName, String userMessage)
	{
		
		File path = new File(System.getProperty("user.dir"));
		File jsonFilePath = new File(path,""+userId+".json");
		String fileName = userId+".json";
		
		//System.out.println(path);                                    //check file path
		
		if(jsonFilePath.exists())
		{
			try 
			{
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(new FileReader(fileName));
				JSONObject logInfo = (JSONObject)obj;
				
				JSONArray newSession = (JSONArray)logInfo.get("session");
				
				long lineCount = (long)logInfo.get("lineCount");
				lineCount++;
				logInfo.put("lineCount", lineCount);
				
				long newSessionValue = (long)logInfo.get("lastUpdatedSession");
				System.out.println(newSessionValue);                    //print check
				
				boolean sessionChange = false;
				if(lineCount%5 == 0)
				{
					newSessionValue++;
					sessionChange = true;
				}
				System.out.println(newSessionValue);                    //print check
				logInfo.put("lastUpdatedSession", newSessionValue);  
				
				//JSONObject mainLog = new JSONObject();
				
				JSONArray chat = (JSONArray) logInfo.get("session");
				JSONObject messageObject = new JSONObject();
				messageObject.put("timeStamp", ""+date.getTime());
				messageObject.put("userName", userName);
				messageObject.put("messageText", userMessage);
				
				if(sessionChange)
				{
					JSONObject sessNum = new JSONObject();
					JSONObject messageTuple = new JSONObject();
					JSONArray messageArray = new JSONArray();
					JSONArray innerSessionArray = new JSONArray() ;
					
					
					
					messageArray.add(messageObject);
					messageTuple.put("messages", messageArray);
					
					sessNum.put("sessionNumber", newSessionValue);
				// next session to be updated is the conditionally increased (above) value of newSessionValue
					innerSessionArray.add(sessNum);
					innerSessionArray.add(messageTuple);
				//sessNum.put("sessionNumber", newSessionValue);
					chat.add(innerSessionArray);
				
				}
				//messageArray.add(messageObject);
				//--------------------------------------------------------------------------------------
				//messageObject.put("messages", messageArray);
				else
				{
					JSONObject msgs = new JSONObject();
						
					JSONArray oldMessageArray = new JSONArray();
					
					JSONArray newSessionArray = new JSONArray();
					JSONObject newSessionNumber = new JSONObject();
					JSONArray tempSession = null;
					
					Iterator<JSONArray> sIter = chat.iterator(); 
					boolean found=false;
					while(sIter.hasNext())
					{
						tempSession = (JSONArray) sIter.next();
						JSONObject sessionItem =  (JSONObject) tempSession.get(0);
						if((long)sessionItem.get("sessionNumber")==newSessionValue)
						{ 
							found=true;
							System.out.println(found);
							msgs = (JSONObject)tempSession.get(1);
							oldMessageArray = (JSONArray) msgs.get("messages");
							oldMessageArray.add(messageObject);
							
						}
						
					}
					chat.add(tempSession);
				}
				//
				//chat.add(msgs);	
			
				
				//System.out.println(logInfo.get("lastUpdatedSession"));   // print check
				jsonFilePath.createNewFile();
				FileWriter jsonFileWriter = new FileWriter(jsonFilePath);				
				jsonFileWriter.write(logInfo.toJSONString());
				jsonFileWriter.flush();
				jsonFileWriter.close();
				
				lineCount++;
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
				emainLog.put("totalUsers", 1);
				emainLog.put("lastUpdatedSession", 0);
				emainLog.put("groupId", userId);
				emainLog.put("groupName", userName);
				emainLog.put("lineCount", 1);
				
				JSONObject esessNum = new JSONObject();
				JSONObject emessageTuple = new JSONObject();
				JSONArray emessageArray = new JSONArray();
				JSONObject emessageObject = new JSONObject();
				
				//JSONArray eusers = new JSONArray();
				JSONArray esession = new JSONArray();
				
				JSONArray egroupUsers = new JSONArray();
				egroupUsers.add(userName);
				//egroupUsers.add("rajat");
				//egroupUsers.add("shasuck");
				//egroupUsers.add("baid");
				emainLog.put("users", egroupUsers);
				
				
				JSONArray echat = new JSONArray();
				esessNum.put("sessionNumber", 1);
					
					
				emessageTuple.put("timeStamp", ""+date.getTime());
				emessageTuple.put("userName", userName);
				emessageTuple.put("messageText", userMessage);
				emessageArray.add(emessageTuple);
					
					
				emessageObject.put("messages", emessageArray);
				echat.add(esessNum);
				echat.add(emessageObject);	
					
				esession.add(echat);
				
				emainLog.put("session", esession);
				emainLog.put("lastUpdatedSession", 1);
				
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
