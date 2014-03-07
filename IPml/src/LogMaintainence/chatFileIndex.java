package LogMaintainence;

import java.io.*;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class chatFileIndex {
	
    protected static String findChatLogName(String user1, String user2, String user3, String user4)
	{
		String fileName=null;
		
		JSONParser parser = new JSONParser();
		 
		try {
	 
				Object obj = parser.parse(new FileReader("index.json"));
	 
				JSONArray response = (JSONArray)obj;
				
				Iterator<JSONObject> iterator = response.iterator();
		        while (iterator.hasNext()) 
		        {
		        	//System.out.println(user1);
		        	//System.out.print(""+iterator.next().get("user1"));
		        	if(user1.equals(""+iterator.next().get("user1"))) 
			        {                                                       // <--- figure out why this if condition		        		
		        		fileName = ""+iterator.next().get("filename");        // is screwing up
		        		System.out.println(fileName);
			        }
			            
		        }
		        //System.out.println(fileName);
		        
				/*if(response.get("user1")==user1)
				{
				fileName=""+response.get("filename");
				System.out.println(fileName);
				
				}*/
				
				//System.out.println(""+jsonObject.get("session")+"\n");
				// loop array
				//JSONObject chat = (JSONObject) jsonObject.get("chat");
				//System.out.println(""+chat.get("userName")+"<"+chat.get("timeStamp")+"> "+chat.get("message"));
			
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
		return fileName;
	}
	
	public static void main(String args[])
	{
		String u1 = null,u2 = null,u3 = null,u4 = null;
		
		u1="rajat";
		
		findChatLogName(u1, u2, u3, u4);
	}
}
