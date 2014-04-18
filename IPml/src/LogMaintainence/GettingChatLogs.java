package LogMaintainence;
import globalfunctions.Contact;

import java.io.*;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import GuiElements.ChatWindowPanelReceiver;
import GuiElements.ChatWindowPanelSender;
import serverclient.MainStart;

public class GettingChatLogs extends Object{
	
	@SuppressWarnings("unchecked")
	public static void readLog(String userId)
	{
		String chatFileName = userId+".json";             // file name based on userId
		
		String newPathString = MainStart.rootpath+"/chatlogs";
		
		File newPath = new File(newPathString);
		File chatFilePath = new File(newPath,chatFileName);

		String myId = MainStart.myID;		
		Contact person = MainStart.people.get(userId);         //person needed to get the correct chat window
		
		long sessionTraversalCount = 0;
		JSONParser parser = new JSONParser();
		 
		if(chatFilePath.exists())
		{
			try {
					Object obj = parser.parse(new FileReader(chatFilePath));
					JSONObject logInfo = (JSONObject)obj;											 
				               
					long sessionValue = (long)logInfo.get("lastUpdatedSession");
				
					sessionTraversalCount = sessionValue;
				
					JSONObject oldSessionObject = (JSONObject)logInfo.get("session");
					JSONArray oldMessageArray;
					int i=1;
					//For now setting to 5 session worth of chats
					while(i<=sessionTraversalCount && i <=5)
					{
						oldMessageArray = (JSONArray)oldSessionObject.get(""+i);
					
						Iterator<JSONObject> oldMessageIterator = oldMessageArray.iterator();
						while (oldMessageIterator.hasNext()) 
						{        
							JSONObject messageObject = (JSONObject)oldMessageIterator.next();
							if(messageObject.get("userId").equals(myId))
							{
								ChatWindowPanelSender cwsp = new ChatWindowPanelSender(messageObject.get("messageText").toString(), messageObject.get("timeStamp").toString());
								person.getWindow().chatconsole(cwsp);
							
							}
							else
							{
								ChatWindowPanelReceiver cwrp = new ChatWindowPanelReceiver(messageObject.get("messageText").toString(), messageObject.get("timeStamp").toString());
								person.getWindow().chatconsole(cwrp);

							}
					 
						}
						System.out.println();
						i++;
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
			else
			{
				//System.out.println("No chats exist\n");
			}
		}

}
