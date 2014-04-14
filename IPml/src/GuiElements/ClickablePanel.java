package GuiElements;

import globalfunctions.Contact;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.border.MatteBorder;


public class ClickablePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel username = new JLabel();	
	private JLabel IPadd = new JLabel();//this holds the username
	private BufferedImage image;
	//private String html; //use html in tool tip
	
	 public ClickablePanel(){
		 setBorder(new MatteBorder(3, 0, 0, 0, (Color) new Color(70, 130, 180)));
		 setMaximumSize(new Dimension(30000,50));
		 setPreferredSize(new Dimension(265, 56));
		 setBackground(Color.WHITE);
		 setLayout(null);

	 }
	 
	 public void setUser(Contact sperson)
	 {
		Contact person=sperson;		

		username.setAlignmentX(Component.CENTER_ALIGNMENT);
		username.setBounds(115, 9, 70, 26);
		username.setForeground(Color.BLACK);
		username.setText(person.getUserName());
		add(username);
		
		IPadd.setAlignmentX(Component.CENTER_ALIGNMENT);
		IPadd.setBounds(115, 25, 100, 26);
		IPadd.setForeground(Color.DARK_GRAY);
		IPadd.setText(person.getIP().getHostAddress());
		add(IPadd);
	}
	 
	 public void setImage() 
	 	{
		 	String imagePath = System.getProperty("user.dir");
		 	String imageName = "cartman.jpg";
	        try 
	        {                
	           image = ImageIO.read(new File(imagePath, imageName));
	        } 
	        catch (IOException ex) 
	        {
	             // handle exception...
	        }
	        this.setAlignmentY(CENTER_ALIGNMENT);
	        
	        
	        
	 	}
	 	
	     @Override
	     protected void paintComponent(Graphics g) {
	         super.paintComponent(g);
	         g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
	     }
	 
	 
	 
	 
}