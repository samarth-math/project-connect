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
import java.io.IOException;
import java.io.InputStream;

import javax.swing.border.MatteBorder;


public class ClickablePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel username = new JLabel();	
	private JLabel IPadd = new JLabel();//this holds the username
	private BufferedImage image;
	
	JPanel colorbox = new JPanel();
	//private String html; //use html in tool tip
	
	 public ClickablePanel(){
		 setBorder(new MatteBorder(3, 0, 0, 0, (Color) new Color(70, 130, 180)));
		 setMaximumSize(new Dimension(30000,50));
		 setPreferredSize(new Dimension(265, 56));
		 setBackground(Color.WHITE);
		 setLayout(null);
		 
		 
		 colorbox.setBounds(25, 12, 33, 32);
		 add(colorbox);
		 
		/*JLabel username= new JLabel();
		username.setAlignmentX(Component.CENTER_ALIGNMENT);
		username.setBounds(115, 12, 138, 20);
		username.setForeground(Color.BLACK);
		username.setText("Samarth");
		add(username);
		
		JLabel IPadd = new JLabel();
		IPadd.setAlignmentX(Component.CENTER_ALIGNMENT);
		IPadd.setBounds(114, 30, 100, 14);
		IPadd.setForeground(Color.DARK_GRAY);
		IPadd.setText("10.0.0.5");
		add(IPadd);*/

	 }
	 

	 public void setUser(Contact person)
	 {
		username.setAlignmentX(Component.CENTER_ALIGNMENT);
		username.setBounds(115, 12, 150, 20);
		username.setForeground(Color.BLACK);
		username.setText(person.getUserName());
		add(username);
		
		IPadd.setAlignmentX(Component.CENTER_ALIGNMENT);
		IPadd.setBounds(114, 30, 100, 14);
		IPadd.setForeground(Color.DARK_GRAY);
		IPadd.setText(person.getIP().getHostAddress());
		add(IPadd);
		
		colorbox.setBackground(person.getColour());
		repaint();
		revalidate();
	}
	 
	 public void setImage()
	 	{
		 	final String imageName = "/images/cartman.jpg";
		 	InputStream is = getClass( ).getResourceAsStream(imageName);
	        try 
	        {                
	           image = ImageIO.read(is);
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
	         g.drawImage(image, 3, 5, null); // see javadoc for more info on the parameters            
	     }
}