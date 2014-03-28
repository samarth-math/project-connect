package LogMaintainence;
import globalfunctions.Contact;

import java.io.*;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import serverclient.Mainstart;

public class GettingChatLogs extends Object{
	
	@SuppressWarnings("unchecked")
	public void readLog(String userId)
	{
		String chatFileName = userId+".json";  // file name based on userId
		
		String myId = Mainstart.myid;    ///   <------- why is this giving null??
		
		System.out.println(myId);
		
		Contact person = Mainstart.people.get(userId);         //person needed to get the correct chat window
		
		
		long sessionTraversalCount = 0;
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
							System.out.println("           "+messageObject.get("userName")+" <"+messageObject.get("timeStamp")+"> : "+messageObject.get("messageText"));
							
						}
						else
						{
							System.out.println(""+messageObject.get("userName")+" <"+messageObject.get("timeStamp")+"> : "+messageObject.get("messageText"));

						}
					 
					}
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

}
