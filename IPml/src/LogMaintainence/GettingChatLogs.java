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
		String chatFileName = userId+".json";  // file name based on userId
		File path = new File(System.getProperty("user.dir"));
		File chatFilePath = new File(path,chatFileName);

		String myId = MainStart.myID;    ///   <------- why is this giving null??
		
		System.out.println(" 'myId' in GettingChatLogs :"+myId+"\n");      // print check
		
		Contact person = MainStart.people.get(userId);         //person needed to get the correct chat window
		
		
		long sessionTraversalCount = 0;
		JSONParser parser = new JSONParser();
		 
		if(chatFilePath.exists())
		{
			try {
				Object obj = parser.parse(new FileReader(chatFileName));
				JSONObject logInfo = (JSONObject)obj;	
				
				      																	       //  printing out info
				//System.out.println("groupId : "+logInfo.get("groupId"));                       // from the chat file
				//System.out.println("groupName : "+logInfo.get("groupName"));                   //  being read
				long sessionValue = (long)logInfo.get("lastUpdatedSession");
				//System.out.println("totalUsers : "+logInfo.get("totalUsers"));
				//System.out.println("users : ");
				
				sessionTraversalCount = sessionValue;
				
				JSONObject oldSessionObject = (JSONObject)logInfo.get("session");
				JSONArray oldMessageArray;
				
				while(sessionTraversalCount > 0)
				{
					System.out.println("session : "+sessionTraversalCount);
					
					oldMessageArray = (JSONArray)oldSessionObject.get(""+sessionTraversalCount);
					//System.out.println(oldSessionObject);
					//System.out.println(oldMessageArray);					// print check
					Iterator<JSONObject> oldMessageIterator = oldMessageArray.iterator();
					
					while (oldMessageIterator.hasNext()) 
					{        
						JSONObject messageObject = (JSONObject)oldMessageIterator.next();
						if(messageObject.get("userId")==myId)
						{
							String s1 = new String(messageObject.get("userName")+": "+messageObject.get("messageText"));
							//ChatWindowPanelSender cwsp = new ChatWindowPanelSender(s1, messageObject.get("timeStamp").toString());
							//person.getWindow().chatconsole(cwsp);
							
							System.out.println(s1);             //print check
							
						}
						else
						{
							String s1 = new String(messageObject.get("userName")+": "+messageObject.get("messageText"));
							//ChatWindowPanelReceiver cwrp = new ChatWindowPanelReceiver(s1, messageObject.get("timeStamp").toString());
							//person.getWindow().chatconsole(cwrp);

							System.out.println(s1);             //print check
						}
					 
					}
					System.out.println();
					sessionTraversalCount--;
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
			System.out.println("No chats exist\n");
		}
	}

}
