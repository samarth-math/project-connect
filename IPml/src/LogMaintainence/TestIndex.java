package LogMaintainence;

public class TestIndex {

	public static void main(String args[])
	{ 
		System.out.println("In    :TestIndex.java  \nUsing :static methods of chatFileIndex.java\n\n");
		
		//chatFileIndex.createGroupEntry("foobar");
		chatFileIndex.displayAllGroupChatFiles();
		System.out.print("Hash Map containing all groups :\n"+ chatFileIndex.getGroups()); 
	}
}
  