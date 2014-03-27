package LogMaintainence;

import java.util.Date;

public class testLogMethods {

	public static void main(String args[])
	{
		Date date= new Date();                               
		String suffix= ""+date.getTime();
		String userId = "user1", userMessage= "the time right now is 500", userName = "user";
		
		//LoggingChats newLog = new LoggingChats();
		//.logCreate(userId, userName, userMessage);   // group Id supplied as argument
			//ChatLogging newLog = new ChatLogging();
			//newLog.logCreate(userId, userName, userMessage); 
		//System.out.println("\n\n\n");
		
		GettingChatLogs oldChat = new GettingChatLogs();
		oldChat.writeLog(userId);      // group Id supplied as argument
	}
	
}
