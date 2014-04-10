package GuiElements;

import globalfunctions.Contact;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.border.MatteBorder;


public class ClickablePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JLabel username = new JLabel();//this holds the username
	//private String html; //use html in tool tip
	
	 public ClickablePanel(){
		 setBorder(new MatteBorder(3, 0, 0, 0, (Color) new Color(70, 130, 180)));
		 setMaximumSize(new Dimension(30000,50));
		 setPreferredSize(new Dimension(250,50));
		 setBackground(Color.WHITE);
		 setLayout(null);
/*		html=	"<html><body>"+
				"<div>This is a tool tip</div>"+
				"<a href=\"http://www.google.co.in\" >click me</a>"+
				"</body></html>";
		this.setToolTipText(html);*/
	 }
	 
	 public void setUser(Contact sperson)
	 {
		Contact person=sperson;		

		username.setAlignmentX(Component.CENTER_ALIGNMENT);
		username.setBounds(115, 12, 70, 26);
		username.setText(person.getUserName());
		add(username);
			
			//TODO user image labels
			
			//TODO options button
	}
}