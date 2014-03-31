package LogMaintainence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import serverclient.MainStart;



public class ChatLogging implements Runnable
{
	private JSONObject mainObject, sessionObject, messageObject;
	private JSONArray chatArray, groupUsers;
	
	private File path;
	private File jsonFilePath;
	private String fileName;
	private String myId = MainStart.myID;
	private int totalUsers;
	
	private BlockingQueue<String> bq;
	public ChatLogging()         // BlockingQueue<String> bq as argument here 
	{
		this.bq = new ArrayBlockingQueue<String>(10);
		try 
		{
			bq.put("user1|y|z|w");
			bq.put("user1|y|z|wjsf");
			bq.put("user1|y|z|wsdkfjs");
			bq.put("user1|y|z|wsdfhgs");
			bq.put("user1|y|z|wsdufygsu");
		} 
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	@SuppressWarnings("unchecked")
	public void run() 
	{
		// TODO Auto-generated method stub
		
		String detailsArray[]=null;
		
		//   details =  0 userId  | 1  userName  | 2 timeStamp  | 3 messageText 
		
		try 
		{
			detailsArray = bq.take().split("\\|");
		} 
		catch (InterruptedException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(detailsArray.length>4)
		{
			for(int i=4;i<detailsArray.length;i++)
				detailsArray[3]+="|"+detailsArray[i];
				
		}
		
		String userId = detailsArray[0];
		String userName = detailsArray[1];
		String timeStamp = detailsArray[2];
		String messageText = detailsArray[3];
		
		path = new File(System.getProperty("user.dir"));
		jsonFilePath = new File(path,""+userId+".json");
		fileName = userId+".json";
		
		// this next code runs only for one time in beginning to create the file
		//*********************************************************************************
		
		if(!jsonFilePath.exists())
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
				messageObject.put("timeStamp", timeStamp);
				messageObject.put("userName", userName);
				messageObject.put("messageText", messageText);
				
				if(userId != myId)
					messageObject.put("userId", userId);
				else
					messageObject.put("userId", myId);
				
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
		//************************************************************************************************
		else
		{
			JSONParser parser = new JSONParser();
			Object obj = null;
			try 
			{
				obj = parser.parse(new FileReader(fileName));
			} 
			catch (IOException | ParseException e2)
			{
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
			mainObject = (JSONObject)obj;
		
			//System.out.println(obj);                   // print check
			//System.out.println(mainObject);            // print check
			
			logCreate(userId, userName, messageText, timeStamp);
		
			writeLogToFile();
		}
	}
	
	
	//-----------------------------------------------------------------------------------------------------------------
	
	
	@SuppressWarnings("unchecked")
	public void logCreate(String userId, String userName, String userMessage, String timeStamp)
	{
		Long lastSessionValue = (Long)mainObject.get("lastUpdatedSession");
		sessionObject = (JSONObject) mainObject.get("session");
		long lineCount = (long)mainObject.get("lineCount");
		lineCount++;
		
		boolean sessionchange = false;
		if(lineCount%5 == 0)
		{
			lastSessionValue++;
			sessionchange = true;
		}
		
		JSONObject messageObject = new JSONObject();
		messageObject.put("timeStamp", timeStamp);
		messageObject.put("userName", userName);
		messageObject.put("messageText", userMessage);
		if(userId != myId)
			messageObject.put("userId", userId);
		else
			messageObject.put("userId", myId);
		
		//JSONArray chatArray;
		
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
	}
	
	
	//----------------------------------------------------------------------------------------
	
	
	public void writeLogToFile()
	{
		if(jsonFilePath.exists())
		{
			try 
			{
				//Write Code to write the main object to file
				jsonFilePath.createNewFile();
				FileWriter jsonFileWriter = new FileWriter(jsonFilePath);				
				jsonFileWriter.write(mainObject.toJSONString());
				jsonFileWriter.flush();
				jsonFileWriter.close();
				
			}
			catch (IOException e)
			{
				e.printStackTrace();
			} 
		 }
		else
			System.out.println("File not found!");
	}
	
	
	
	//------------------------------------------------------------------------------------------------------
	
	
	
	
	public void clearLog(String userId)
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
				if(jsonFilePath.delete())
					System.out.println("file "+fileName+" is deleted\n");
				else
					System.out.println("delete failed\n");
			}
			
			catch (Exception e) 
			{
				e.printStackTrace();
			} 		
		 }
		else
		{
			System.out.println("file doesnt exist!\n");
		}
	}

	
	

}
