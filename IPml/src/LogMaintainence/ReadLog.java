package LogMaintainence;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadLog 
	{
		public static void main(String args[])
		{
			JSONParser parser = new JSONParser();
		 
			try {
		 
					Object obj = parser.parse(new FileReader("log.json"));
					JSONObject logInfo = (JSONObject)obj;
					
					System.out.println("totalUsers : "+logInfo.get("totalUsers")+"\n");
					System.out.println("groupId : "+logInfo.get("groupId")+"\n");
					System.out.println("groupName : "+logInfo.get("groupName")+"\n");
				
					JSONArray sessionInfo = new JSONArray();
					sessionInfo = (JSONArray)logInfo.get("session");
					JSONArray messages = new JSONArray();
					messages = (JSONArray)sessionInfo.get(1);
					Iterator<JSONObject> sessionIterator = sessionInfo.iterator();
					Iterator<JSONObject> messageIterator = messages.iterator();
				
					while (sessionIterator.hasNext()) 
					{        
						//JSONObject snum = new JSONObject();
						//snum = (JSONObject)sessionIterator.next().get("sessionNumber");
						System.out.println(("sessionCount : "+sessionIterator.next().get("sessionNumber"))); // .get("sessionNumber") not working, see to it later
						while(messageIterator.hasNext())
							System.out.println(messageIterator.next().get("timeStamp")+" "+"  <"+messageIterator.next().get("userName")+">  "+messageIterator.next().get("message"));        
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
