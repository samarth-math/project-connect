package serverclient;

import java.io.IOException;
import java.util.concurrent.*;

import GuiElements.ChatWindowPanelSender;
import globalfunctions.Contact;
//import java.lang.ThreadGroup;

public class SendMessage implements Runnable
{
	private Contact person = null;
	private String Message = null;
	private String threadnumber;
	private BlockingQueue<Character> q;
	
	public SendMessage(Contact person, String Message)
	{
		this.person=person;
		this.Message= Message;	
	}
	@Override
	public void run()
	{
		Thread.currentThread().setName("SendMessage");
		threadnumber=Long.toString(Thread.currentThread().getId());
		q=new ArrayBlockingQueue<Character>(1);
		Mainstart.threadsync.put(threadnumber, q);
		try
		{
			ChatWindowPanelSender MessagePane = new ChatWindowPanelSender(new String(Mainstart.myusername+":"+Message), "timestamp");
			person.getWindow().chatconsole(MessagePane);
			try
			{
				
				person.SendMessage(Message+":"+threadnumber, Mainstart.myid);
				if(q.poll(500, TimeUnit.MILLISECONDS)==null)
				{
					MessagePane.showMsg("No Confirmation Received");
					Mainstart.threadsync.remove(threadnumber);
				}
				
				else
				{
					MessagePane.showMsg("Message Delivered");
					Mainstart.threadsync.remove(threadnumber);
				}
			}
			catch(InterruptedException e)
			{
				MessagePane.showMsg("Message Delivered");
			}		
		}
		catch(IOException e)
		{
			System.err.println("Unable to send message!");
		}
	}
}