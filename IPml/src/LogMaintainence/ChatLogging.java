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
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import serverclient.MainStart;



public class ChatLogging implements Runnable
{
	private JSONObject mainObject;
	
	private File path;
	private File jsonFilePath;
	private String fileName;
	private String myId = MainStart.myID;
	private int totalUsers;
	private String userId;
	private String userName;
	
	private BlockingQueue<String> bq;
	public ChatLogging(String userId, String userName, BlockingQueue<String> bq) 
	{
		this.userId = userId;
		this.userName = userName;
		this.fileName = userId+".json";
		this.bq = bq;
	
	}

	
	@SuppressWarnings("unchecked")
	public void run() 
	{
		path = new File(System.getProperty("user.dir"));
		jsonFilePath = new File(path,fileName);
		
		if(!jsonFilePath.exists())
		{
			mainObject = new JSONObject();
			mainObject.put("totalUsers", totalUsers);
			mainObject.put("groupId", userId);
			mainObject.put("groupName", userName);
			mainObject.put("lineCount", 0l);
			
			JSONArray groupUsers = new JSONArray();
			groupUsers.add(userId);
			//groupUsers.add("rajat");
			//groupUsers.add("shasuck");
			//groupUsers.add("baid");
			mainObject.put("users", groupUsers);
					
			JSONArray chatArray = new JSONArray();
			
			JSONObject sessionObject = new JSONObject();
			sessionObject.put("1", chatArray);
			
			mainObject.put("session", sessionObject);
			mainObject.put("lastUpdatedSession", 1l);
			
		}
		else//if file exists
		{
			JSONParser parser = new JSONParser();
			Object obj = null;
			try 
			{
				obj = parser.parse(new FileReader(fileName));
			} 
			catch (IOException | ParseException e2)
			{
				e2.printStackTrace();
			}
		
			mainObject = (JSONObject)obj;
		}
		
		boolean on = true;
		while(on)
		{
			String qElement=null;
			String detailsArray[] = null;
			for(int i=0;i<15;i++)
			{
				try {
					qElement = bq.poll(1000, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if( qElement!=null && !qElement.isEmpty())
				{
					if(!qElement.equals("CLOSE"))
					{
						detailsArray = qElement.split("\\|");//details[0]=userId  [1]=userName [2]=timeStamp [3]=messageText
						if(detailsArray.length>4)
						{
							for(int j=4;j<detailsArray.length;j++)//Concatenating message with pipes into one place
								detailsArray[3]+="|"+detailsArray[j];
						}
						logCreate(detailsArray[0], detailsArray[1], detailsArray[3], detailsArray[2]);
					}
					else
					{
						on=false;
						break;
					}
				}
			}
			writeLogToFile();
		}	
	}
		
	
	@SuppressWarnings("unchecked")
	public void logCreate(String userId, String userName, String userMessage, String timeStamp)
	{
		//System.out.println("Main object in logCreate :"+mainObject);
		
		long lastSessionValue = (long)mainObject.get("lastUpdatedSession");
		JSONObject sessionObject = (JSONObject) mainObject.get("session");
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
		messageObject.put("userId", userId);
		
		JSONArray chatArray;
		
		if(!sessionchange)
		{
			System.out.println("Last session value" + lastSessionValue);
			chatArray = (JSONArray) sessionObject.get(""+lastSessionValue);
		}
		else
			chatArray = new JSONArray();
		
		chatArray.add(messageObject);	
		sessionObject.put(""+lastSessionValue, chatArray);
		mainObject.put("lineCount", lineCount);
		mainObject.put("lastUpdatedSession", lastSessionValue);	
	}
		
	
	public void writeLogToFile()
	{
		//System.out.println("Main object writelogtofile :"+mainObject);
		try 
		{
			//Write Code to write the main object to file
			jsonFilePath.createNewFile();
			//System.out.println(jsonFilePath);
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
	
	public void clearLog(String userId)
	{
		
		File path = new File(System.getProperty("user.dir"));
		File jsonFilePath = new File(path,""+userId+".json");
		String fileName = userId+".json";
		
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
