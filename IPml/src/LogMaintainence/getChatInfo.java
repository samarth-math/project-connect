package LogMaintainence;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class getChatInfo extends Object{
	
	public void writeLog(String groupId)
	{
		/* Now we need to look up for the file having logs for the groupId */
		//String chatFileName = groupId.getChatFile();
		String currentGroupId = groupId;
		String chatFileName = "log.json";
		
		JSONParser parser = new JSONParser();
		 
		try {
				Object obj = parser.parse(new FileReader(chatFileName));
				JSONObject logInfo = (JSONObject)obj;	
				
				      																	//  printing out info
				System.out.println("groupId : "+logInfo.get("groupId")+"\n");            // from the chat file
				System.out.println("groupName : "+logInfo.get("groupName")+"\n");        //  being read
				System.out.println("currentSession : "+logInfo.get("currentSession")+"\n");
				System.out.println("totalUsers : "+logInfo.get("totalUsers")+"\n");
				System.out.println("users : ");
				Iterator<JSONArray> userIterator = ((ArrayList) logInfo.get("users")).iterator();
				while (userIterator.hasNext()) 
				{        
					System.out.println((userIterator.next())); 
				}	
				System.out.println("sessionNumber : "+logInfo.get("sessionNumber")+"\n");
				//JSONArray messages = new JSONArray();                                                     // code to
				//messages = (JSONArray) logInfo.get("message");											// display 
				//Iterator<JSONArray> messageIterator = ((ArrayList) logInfo.get("message")).iterator();	// messages
				//Iterator<JSONArray> msgItemIterator = ((ArrayList) logInfo.get("")).iterator();
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
