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
		 
				JSONObject jsonObject = (JSONObject)obj;
		 
				System.out.println(""+jsonObject.get("session")+"\n");
				
		 
				// loop array
				JSONObject chat = (JSONObject) jsonObject.get("chat");
				System.out.println(""+chat.get("userName")+"<"+chat.get("timeStamp")+"> "+chat.get("message"));
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
