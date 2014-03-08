package LogMaintainence;

public class testLogMethods {

	public static void main(String args[])
	{
		LoggingChats updateLog = new LoggingChats();
		updateLog.logCreate("log");   // group Id supplied as argument
		
		System.out.println("\n\n\n");
		
		//GettingChatLogs newChat = new GettingChatLogs();
		//newChat.writeLog("log");      // group Id supplied as argument
	}
	
}
