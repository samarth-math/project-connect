package serverclient;


import java.io.IOException;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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
			Timestamp t =new Timestamp(new Date().getTime());
			if(Message!=null)  {
				
				ChatWindowPanelSender MessagePane = new ChatWindowPanelSender(new String(MainStart.myUserName+":"+Message), new SimpleDateFormat("HH:mm:ss").format(t));
				person.getWindow().chatconsole(MessagePane);
				try
				{
					
					person.sendMessage(threadnumber+"|"+Message, MainStart.myID);
					if(q.poll(500, TimeUnit.MILLISECONDS)==null)
					{
						MessagePane.showDeliveryStatus(false);
					}
					
					else
					{
						MessagePane.showDeliveryStatus(true);
						person.getBlockingQ().put(MainStart.myID+"|"+MainStart.myUserName+"|"+ new SimpleDateFormat("HH:mm:ss").format(t)+"|"+Message);
					}
				}
				catch(InterruptedException e)
				{
					MessagePane.showDeliveryStatus(true);
				}		
			}
				else {
					header = FileTransfer.getHeader(filePath);
					
					FileTransferPanelS ftPane = new FileTransferPanelS(filePath.getFileName().toString(),new SimpleDateFormat("HH:mm:ss").format(t));
					int x = ftPane.getIndex();
					person.getWindow().chatconsole(ftPane);
					MainStart.fileSendPanels.put(x, ftPane);
					try
					{
						
						person.sendFile(threadnumber+"|"+x+"|"+ header, MainStart.myID);
						if(q.poll(500, TimeUnit.MILLISECONDS)==null)// Make this infinite maybe
						{
							//ftPane.showDeliveryStatus("No Confirmation Received");
							MainStart.threadsync.remove(threadnumber);
						}
						
						else
						{
							//ftPane.showDeliveryStatus("Message Delivered");
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