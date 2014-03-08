package GuiElements;

import globalfunctions.Contact;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ClickablePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel username = new JLabel();//this holds the username
	
	 public ClickablePanel(Contact person){
		
		//set all properties of the panel here
		this.setMaximumSize(new Dimension(30000,50));
		this.setPreferredSize(new Dimension(300,50));
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		//panel properties end here
		
		//panel event handling here
		//addMouseListener(new MouseAdapter() {
			//@Override
			//public void mouseClicked(MouseEvent e) {
				//TODO 
			//}
		//});
		
		
		/*components in the panel*/
		
			//username labels 
			this.username.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.username.setBounds(115, 12, 70, 26);
			this.username.setText(person.getHostname());
			this.add(username);
			
			//TODO user image labels
			
			//TODO options button
	}
}