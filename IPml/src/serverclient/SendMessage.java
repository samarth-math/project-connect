package serverclient;


import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.*;
import GuiElements.ChatWindowPanelSender;
import GuiElements.FileTransferPanelS;
import globalfunctions.Contact;
import globalfunctions.FileTransfer;

public class SendMessage implements Runnable
{
	private Contact person = null;
	private String Message = null;
	private Path filePath;
	private String header;
	private BlockingQueue<Character> q;
	
	public SendMessage(Contact person, Path filePath)
	{
		this.person=person;
		this.filePath = filePath;	
	}
	public SendMessage(Contact person, String message)
	{
		this.person=person;
		this.Message= message;	
	}
	@Override
	public void run()
	{
		Thread.currentThread().setName("SendMessage");
		String threadnumber=Long.toString(Thread.currentThread().getId());
		q=new ArrayBlockingQueue<Character>(1);
		MainStart.threadsync.put(threadnumber, q);
		try
		{
			if(Message!=null)  {
				ChatWindowPanelSender MessagePane = new ChatWindowPanelSender(new String(MainStart.myUserName+":"+Message), "timestamp");
				person.getWindow().chatconsole(MessagePane);
				try
				{
					
					person.sendMessage(threadnumber+"|"+Message, MainStart.myID);
					if(q.poll(500, TimeUnit.MILLISECONDS)==null)
					{
						MessagePane.showMsg("No Confirmation Received");
						MainStart.threadsync.remove(threadnumber);
					}
					
					else
					{
						MessagePane.showMsg("Message Delivered");
						MainStart.threadsync.remove(threadnumber);
					}
				}
				catch(InterruptedException e)
				{
					MessagePane.showMsg("Message Delivered");
				}		
			}
				else {
					header = FileTransfer.getHeader(filePath);
					
					FileTransferPanelS ftPane = new FileTransferPanelS(filePath.getFileName().toString());
					int x = ftPane.getIndex();
					person.getWindow().chatconsole(ftPane);
					MainStart.fileSendPanels.put(x, ftPane);
					try
					{
						
						person.sendFile(threadnumber+"|"+x+"|"+ header, MainStart.myID);
						if(q.poll(500, TimeUnit.MILLISECONDS)==null)// Make this infinite maybe
						{
							ftPane.showMsg("No Confirmation Received");
							MainStart.threadsync.remove(threadnumber);
						}
						
						else
						{
							ftPane.showMsg("Message Delivered");
							MainStart.threadsync.remove(threadnumber);
						}
					}
					catch(InterruptedException e)
					{
						ftPane.showMsg("Message Delivered");
					}		
				}

		}
		catch(IOException e)
		{
			System.err.println("Unable to send message!");
		}
	}

}