/*
 * The Layout of the chatroom-like broadcast window with message thread
 */
package GUIObjects;


import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import globalfunctions.Contact;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import java.awt.GridBagConstraints;
import serverclient.SendMessage;

public class BroadcastWindow extends JPanel{
	
	private JTextArea txtMessage;
	private Box history = Box.createVerticalBox();
	private Contact person;
	
	
	public BroadcastWindow() {
		
	    //panel properties
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[]{5,640, 5};
		gbl.rowHeights = new int[]{5, 440, 50};
		gbl.columnWeights = new double[]{0, 1.0, 0};
		gbl.rowWeights = new double[]{0, 1.0, 0};
		setLayout(gbl);
		
		
		//adding history to scrollpane
		JScrollPane scroll1 = new JScrollPane(history);
		scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints scrollConstraints = new GridBagConstraints();
		scrollConstraints.fill = GridBagConstraints.BOTH;
		scrollConstraints.gridx = 1;
		scrollConstraints.gridy = 1;
		add(scroll1, scrollConstraints);
		
		
		
		//text message properties
		txtMessage = new JTextArea();
		txtMessage.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("ENTER"), "SendMessage");
		txtMessage.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("shift ENTER"), "NewLine");
		
		txtMessage.getActionMap().put("NewLine", new AbstractAction(){
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				txtMessage.append("\n\r");
				txtMessage.setCaretPosition(txtMessage.getDocument().getLength());
			}
		});
		txtMessage.getActionMap().put("SendMessage", new AbstractAction(){
		
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e){
				
				String message = txtMessage.getText();
				if(!message.equals("")){
					
					if (message.length()>1003){
						
							String message1 = message.substring(0, 1003);
							SendMessage SM = new SendMessage(person, message1);
							new Thread(SM).start();
							message = message.substring(1003);
						}
					SendMessage SM = new SendMessage(person, message);
					new Thread(SM).start();
					txtMessage.setText("");
				}

			}
		});
		
		
		//adding textarea to scrollpane
		JScrollPane scroll2 = new JScrollPane(txtMessage);
		GridBagConstraints gbc_txtMessage = new GridBagConstraints();
		gbc_txtMessage.fill = GridBagConstraints.BOTH;
		gbc_txtMessage.gridx = 1;
		gbc_txtMessage.gridy = 2;
		add(scroll2, gbc_txtMessage);
		txtMessage.setColumns(10);
		setVisible(true);
		txtMessage.requestFocusInWindow();

	
	}
}
