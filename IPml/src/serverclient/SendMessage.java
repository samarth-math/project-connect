package serverclient;

import java.io.IOException;
import java.util.concurrent.*;
import globalfunctions.Contact;
//import java.lang.ThreadGroup;

public class SendMessage implements Runnable
{
	protected Contact person = null;
	protected String Message = null;
	protected String threadnumber;
	protected BlockingQueue<Character> q;
	
	public SendMessage(Contact person, String Message)
	{
		this.person=person;
		this.Message= Message;
		
	}
	@Override
	public void run()
	{
		
		Thread.currentThread().setName("SendMessage");
		this.threadnumber=Long.toString(Thread.currentThread().getId());
		q=new ArrayBlockingQueue<Character>(1);
		Mainstart.threadsync.put(threadnumber, q);
		try
		{
			person.getWindow().chatconsole(person.getusername()+":"+Message);
			try
			{
				
				person.SendMessage(Message+":"+threadnumber, Mainstart.myid);
				if(q.poll(500, TimeUnit.MILLISECONDS)==null)
					person.getWindow().chatconsole("No Confirmation Received");
				else
					person.getWindow().chatconsole("Message Delivered");
			}
			catch(InterruptedException e)
			{
				person.getWindow().chatconsole("No Confirmation Received");
			}
			
			
		}
		catch(IOException e)
		{
			System.err.println("Unable to send message!");
		}
/*		catch (InterruptedException e)
		{
			System.out.print("No delivery status confirmation received");
		}
*/		
	}

}