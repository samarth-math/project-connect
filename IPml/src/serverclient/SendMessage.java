package serverclient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.*;

import GuiElements.ChatWindowPanelSender;
import GuiElements.FileTransferPanel;
import fileSending.Sender;
import globalfunctions.Contact;
//import java.lang.ThreadGroup;

public class SendMessage implements Runnable
{
	private Contact person = null;
	private String Message = null;
	private String threadnumber;
	private Path filePath;
	private String header;
	private BlockingQueue<Character> q;
	
	public SendMessage(Contact person, Path filePath)
	{
		this.person=person;
		this.filePath = filePath;	
	}
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
			if(Message!=null)  {
				ChatWindowPanelSender MessagePane = new ChatWindowPanelSender(new String(Mainstart.myusername+":"+Message), "timestamp");
				person.getWindow().chatconsole(MessagePane);
				try
				{
					
					person.SendMessage(threadnumber+"|"+Message, Mainstart.myid);
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
				else {
					header = getHeader(filePath);
					
					ChatWindowPanelSender ftPane = new ChatWindowPanelSender(filePath.getFileName().toString(),"timestamp");
					person.getWindow().chatconsole(ftPane);
					try
					{
						
						person.SendFile(threadnumber+"|"+header, Mainstart.myid);
						if(q.poll(500, TimeUnit.MILLISECONDS)==null)
						{
							ftPane.showMsg("No Confirmation Received");
							Mainstart.threadsync.remove(threadnumber);
						}
						
						else
						{
							ftPane.showMsg("Message Delivered");
							Mainstart.threadsync.remove(threadnumber);
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

	public String getHeader(Path filePath) throws IOException  {
	    	String fileName = filePath.getFileName().toString();
			long fSize =  Files.size(filePath);
			long fileSize= fSize;
			int chunkSize = 1024*1024;

			System.out.println("\nFile Size is " + fileSize + " file path " + fileName);
			
			boolean flag=false;
			char pathType= ' ';
			
			flag = isPathValid(filePath.toString());
			if(flag==false) {
				Sender.displayError("Path Not Valid");
			}
			flag = isFile(filePath.toString());
			if(flag==true) {
				pathType = 'f';
			}
			if(flag==false) {
				flag = isDirectory(filePath.toString());
				if(flag==true) {
					pathType = 'd';
				}
			}
			System.out.println("Path type " + pathType);
			
			/* File Header is of the format 
			 * [f/d]*[file/directory Size]-[file/directory path][newline character]
			 * f represents a file
			 * d represents a directory
			 * */
					
			// header contains the content of file header which will be sent to receiver
			String header = pathType + "*" + Long.toString(fileSize) + "-" + filePath +  "\n" ;
			byte [] fileHeader  = new byte[header.length()*2];
			// Creation of file header
			fileHeader = header.getBytes("UTF-8");
			
			return header;
			
	    }
	public  boolean isPathValid(String filePath) {
			
			if(new File(filePath).exists())
				return true;
			else
				return false;
		}
	public  boolean isDirectory(String filePath) {
			if(new File(filePath).isDirectory())
				return true;
			else
				return false;
		}
	public  boolean isFile(String filePath) {
			if(new File(filePath).isFile())
				return true;
			else
				return false;
		}}