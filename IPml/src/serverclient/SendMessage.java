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

public class SendMessage implements Runnable
{
	private Contact person = null;
	private String Message = null;
	private Path filepath;
	private BlockingQueue<Character> q;
	
	public SendMessage(Contact person, Path filepath)
	{
		this.person=person;
		this.filepath = filepath;	
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
					
					person.sendMessage(threadnumber+"|"+Message);
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
					MessagePane.showDeliveryStatus(false);
				}		
			}
			else {
				String filename = filepath.getFileName().toString();
				//long filesize = filepath.toFile().length();
				FileTransferPanelS ftPane = new FileTransferPanelS(filepath,new SimpleDateFormat("HH:mm:ss").format(t));
				int x = ftPane.getIndex();
				MainStart.fileSendPanels.put(x,ftPane);
				person.getWindow().chatconsole(ftPane);
				try
				{
					
					person.sendFile(threadnumber+"|"+x+"|"+filename);
					if(q.poll(500, TimeUnit.MILLISECONDS)==null)// Make this infinite maybe
					{
						ftPane.showDeliveryStatus(false);
					}
					
					else
					{
						ftPane.showDeliveryStatus(true);
					}
				}
				catch(InterruptedException e)
				{
					ftPane.showDeliveryStatus(false);
				}		
			}

		}
		catch(IOException e)
		{
			System.err.println("Unable to send message!");
		}
	}

}