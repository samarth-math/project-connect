package serverclient;

import java.io.IOException;
import java.util.concurrent.*;
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
		System.out.println("Threadnumber:"+threadnumber);
		q=new ArrayBlockingQueue<Character>(1);
		Mainstart.threadsync.put(threadnumber, q);
		try
		{
			person.getWindow().chatconsole(Mainstart.myusername+":"+Message);
			try
			{
				
				person.SendMessage(Message+":"+threadnumber, Mainstart.myid);
				if(q.poll(500, TimeUnit.MILLISECONDS)==null)
				{
					person.getWindow().chatconsole("No Confirmation Received");
					Mainstart.threadsync.remove(threadnumber);
				}
				
				else
				{
					person.getWindow().chatconsole("Message Delivered");
					Mainstart.threadsync.remove(threadnumber);
				}
			}
			catch(InterruptedException e)
			{
				person.getWindow().chatconsole("No Confirmation Received Interrupted");
			}		
		}
		catch(IOException e)
		{
			System.err.println("Unable to send message!");
		}
	}
}