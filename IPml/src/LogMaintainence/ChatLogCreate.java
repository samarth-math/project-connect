package LogMaintainence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



public class ChatLogCreate {
	static File path = new File(System.getProperty("user.dir"));
	static File jsonFilePath = new File(path,"log.json");
	//static String jsonFilePath = "log.json";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject mainLog = new JSONObject();
		mainLog.put("session", 2);
		
		//JSONObject chat = new JSONObject();
		
		JSONObject messageTuple = new JSONObject();
		messageTuple.put("timeStamp", "22:50");
		messageTuple.put("userName", "shasuck");
		messageTuple.put("message", "No he doesnt");
		
		mainLog.put("chat",messageTuple);
		
		if(jsonFilePath.exists())
			{
				try 
				{
					FileWriter jsonFileWriter = new FileWriter(jsonFilePath);
					jsonFileWriter.write(mainLog.toJSONString());
					//jsonFileWriter.flush();
					jsonFileWriter.close();
					System.out.print(mainLog);
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		
	}

}
