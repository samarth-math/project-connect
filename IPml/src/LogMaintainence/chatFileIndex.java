package LogMaintainence;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class chatFileIndex {
	
	@SuppressWarnings("unchecked")
	protected static String createGroupEntry(String groupMakerId) 
	{
		File path = new File(System.getProperty("user.dir"));
		File jsonFilePath = new File(path,""+"index.json");
		
		String gId = null;
		try
		{ 
			JSONParser parser = new JSONParser();                                     // parsing index
			Object obj = parser.parse(new FileReader("index.json"));                  //   file here
			JSONObject indexInfo = (JSONObject)obj;	
			
			JSONArray groups = (JSONArray)indexInfo.get("groups");
			
			JSONArray group = new JSONArray();
			JSONObject groupName = new JSONObject();
			JSONObject groupId = new JSONObject();
			
			Date date= new Date();                                //generating new 
			String date1= ""+date.getTime();                      // groupId and
			gId = groupMakerId+date1;                             // groupName to put in index file
				
			groupId.put("groupId", gId);
			groupName.put("groupName",groupMakerId+"'s group");
			group.add(groupId);
			group.add(groupName);
			
			groups.add(group);
			
			indexInfo.put("groups", groups);
			
			jsonFilePath.createNewFile();
			FileWriter jsonFileWriter = new FileWriter(jsonFilePath);				
			jsonFileWriter.write(indexInfo.toJSONString());
			jsonFileWriter.flush();
			jsonFileWriter.close();
			
			//System.out.println(indexInfo);  //checking
			
		}
		catch (ParseException e) 
		{
			e.printStackTrace();
		}	
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		return gId;
	}
	
	protected static void displayAllGroupChatFiles() 
	{
		
		try
		{
			JSONParser parser = new JSONParser();                                     // parsing index
			Object obj = parser.parse(new FileReader("index.json"));                  //   file here
			JSONObject indexInfo = (JSONObject)obj;	
			
			JSONArray groups = (JSONArray)indexInfo.get("groups");
			
			System.out.println("Group-chat Files:");
			Iterator<JSONArray> groupIterator = groups.iterator();
			JSONArray temp = new JSONArray();
			while (groupIterator.hasNext()) 
			{        
					JSONArray gid = groupIterator.next();
					JSONObject id = (JSONObject) gid.get(0);
					JSONObject name = (JSONObject) gid.get(1);
					System.out.println(id.get("groupId")+" --> "+name.get("groupName"));        
			}	
			System.out.println();
		}
		catch (ParseException e) 
		{
			e.printStackTrace();
		}	
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		
	}
			
	
	  
			/*  The next method returns a hashmap pf the form <groupId, groupName>
			 *     to the user of the class
			 * 
			 */
	protected static HashMap<String, String> getGroups() 
	{
		HashMap<String, String>  map = new HashMap<String, String>();
		try
		{
			JSONParser parser = new JSONParser();                                     // parsing index
			Object obj = parser.parse(new FileReader("index.json"));                  //   file here
			JSONObject indexInfo = (JSONObject)obj;	
			
			JSONArray groups = (JSONArray)indexInfo.get("groups");
			
			System.out.println("Group-chat Files:");
			Iterator<JSONArray> groupIterator = groups.iterator();
			while (groupIterator.hasNext()) 
			{        
					JSONArray gid = groupIterator.next();
					JSONObject id = (JSONObject) gid.get(0);
					JSONObject name = (JSONObject) gid.get(1);
					map.put(""+id.get("groupId"),""+name.get("groupName")); 
			}	
		}
		catch (ParseException e) 
		{
			e.printStackTrace();
		}	
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		//System.out.println(map);                //checking
		return map;
	}
	
}
 