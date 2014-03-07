package LogMaintainence;

public class testLogMethods {

	public static void main(String args[])
	{
		ChatLogCreate updateLog = new ChatLogCreate();
		updateLog.logCreate();
		
		System.out.println("\n\n\n");
		
		getChatInfo newChat = new getChatInfo();
		newChat.writeLog("xyz");
	}
	
}
