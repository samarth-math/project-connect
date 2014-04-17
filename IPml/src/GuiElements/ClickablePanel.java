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
import java.util.Random;

import javax.swing.border.MatteBorder;


public class ClickablePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel username = new JLabel();	
	private JLabel IPadd = new JLabel();//this holds the username
	private BufferedImage image;
	private JPanel colorbox;
	private Color colors[]=new Color[8];
	//private String html; //use html in tool tip
	
	 public ClickablePanel(){
		 defineColors();
		 setBorder(new MatteBorder(3, 0, 0, 0, (Color) new Color(70, 130, 180)));
		 setMaximumSize(new Dimension(30000,50));
		 setPreferredSize(new Dimension(265, 56));
		 setBackground(Color.WHITE);
		 setLayout(null);
		 
		 colorbox = new JPanel();
		 colorbox.setBounds(25, 12, 33, 32);
		 Random r = new Random();
		 colorbox.setBackground(colors[r.nextInt(8)]);
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
	 
	 private void defineColors()
	 {
		 colors[0]= new Color(0,128,255);
		 colors[1]= new Color(76,0,153);
		 colors[2]= new Color(204,0,0);
		 colors[3]= new Color(162,90,126);
		 colors[4]= new Color(102,0,102);
		 colors[5]= new Color(0,153,0);
		 colors[6]= new Color(51,153,255);
		 colors[7]= new Color(102,255,178);
	 }
	 public void setUser(Contact sperson)
	 {
		Contact person=sperson;		

		username.setAlignmentX(Component.CENTER_ALIGNMENT);
		username.setBounds(115, 12, 138, 20);
		username.setForeground(Color.BLACK);
		username.setText(person.getUserName());
		add(username);
		
		IPadd.setAlignmentX(Component.CENTER_ALIGNMENT);
		IPadd.setBounds(114, 30, 100, 14);
		IPadd.setForeground(Color.DARK_GRAY);
		IPadd.setText(person.getIP().getHostAddress());
		add(IPadd);
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